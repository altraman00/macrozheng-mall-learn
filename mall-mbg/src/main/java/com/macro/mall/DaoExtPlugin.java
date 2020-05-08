package com.macro.mall;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.XmlConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Project : mall
 * @Package Name : com.macro.mall
 * @Description :   2.自定义插件DaoExtPlugin继承PluginAdapter，重写validate方法进行数据校验
 *                  3.重写contextGenerateAdditionalJavaFiles方法，新增dao层java扩展文件
 *                  4、重写contextGenerateAdditionalXmlFiles方法，新增mapper.xml扩展文件
 *                  4.1、GeneratedXmlFile构造xml文件对象的isMergeable参数是表示执行generator逆向工程前后的文件是否合并，如果为false则我们写的扩展sql不会被覆盖
 * @Author : xiekun
 * @Create Date : 2020年05月08日 14:52
 * @ModificationHistory Who   When     What
 * ------------    --------------    ---------------------------------
 */
public class DaoExtPlugin extends PluginAdapter {

    private String daoTargetProject = null;
    private String daoTargetPackage = null;
    private String mapperTargetProject = null;
    private String mapperTargetPackage = null;


    /**
     * 2.自定义插件DaoExtPlugin继承PluginAdapter，重写validate方法进行数据校验
     *
     * @param warnings
     * @return
     */
    @Override
    public boolean validate(List<String> warnings) {
        daoTargetProject = context.getJavaClientGeneratorConfiguration().getTargetProject();
        daoTargetPackage = context.getJavaClientGeneratorConfiguration().getTargetPackage();
        mapperTargetProject = context.getSqlMapGeneratorConfiguration().getTargetProject();
        mapperTargetPackage = context.getSqlMapGeneratorConfiguration().getTargetPackage();
        return true;
    }

    private static Boolean fileIsNotExists(String filePath, String fileName) {
        String fileStr = filePath + File.separator + fileName;
        File file = new File(fileStr);
        return !file.exists();
    }


    /**
     * 3.重写contextGenerateAdditionalJavaFiles方法，新增dao层java扩展文件
     *
     * @param introspectedTable
     * @return
     */
    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {

        List<GeneratedJavaFile> answer = new ArrayList<>();

        context.getTableConfigurations().stream().forEach(t -> {
            String daoName = t.getDomainObjectName();
            String filePath = daoTargetPackage;
            String file = daoTargetProject + "." + filePath;
            String fileName = daoName + "MapperExt.java";
            String supperFilePath = daoTargetPackage + "." + daoName + "MapperExt";

            if (fileIsNotExists(file, fileName)) {
                //定义一个接口
                Interface face = new Interface(filePath + "." + daoName + "MapperExt");

                //此接口继承父类
                face.addSuperInterface(new FullyQualifiedJavaType(supperFilePath));

                //设置接口的修饰符
                face.setVisibility(JavaVisibility.PUBLIC);
                face.addAnnotation(String.valueOf(properties.setProperty("annotation", "@Resource")));

                //导包
                face.addImportedType(new FullyQualifiedJavaType(properties.getProperty("import", "javax.annotation.Resource")));
                face.addImportedType(new FullyQualifiedJavaType(supperFilePath));

                //构造自定义java文件对象
                GeneratedJavaFile jf = new GeneratedJavaFile(face, daoTargetProject, context.getJavaFormatter());
                answer.add(jf);
            }
        });

        return answer;
    }


    /**
     * 4、重写contextGenerateAdditionalXmlFiles方法，新增mapper.xml扩展文件
     * GeneratedXmlFile构造xml文件对象的isMergeable参数是表示执行generator逆向工程前后的文件是否合并，如果为false则我们写的扩展sql不会被覆盖
     *
     * @return
     */
    @Override
    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles() {

        List<GeneratedXmlFile> list = new ArrayList<>();

        context.getTableConfigurations().stream().forEach(t -> {
            String daoName = t.getDomainObjectName();
            String nameSpace = daoTargetPackage + "." + daoName + "MapperExt";
            String fileName = daoName + "MapperExt.xml";
            String filePath = mapperTargetProject + "." + mapperTargetPackage;

            if (fileIsNotExists(filePath, fileName)) {
                Document document = new Document(XmlConstants.MYBATIS3_MAPPER_PUBLIC_ID
                        , XmlConstants.MYBATIS3_MAPPER_SYSTEM_ID);
                XmlElement root = new XmlElement("mapper");

//                Utils.setAttribute(root,"namespace",nameSpace);

                document.setRootElement(root);
                //创建mapper扩展文件
                GeneratedXmlFile gxf = new GeneratedXmlFile(document, fileName, mapperTargetPackage, mapperTargetProject, false, context.getXmlFormatter());
                list.add(gxf);
            }

        });


        return list;

    }


    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        System.out.println(File.separator);
        String filePath = "/Users/admin/Documents/workspace/open_source_project";
        String fileName = "aaa.java";
        String fileStr = filePath + File.separator + fileName;
        System.out.println(fileStr);
    }

}
