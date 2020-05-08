package com.macro.mall;

import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl;

/**
 * @Project : mall
 * @Package Name : com.macro.mall
 * @Description : 1、写个MyIntrospectedTableImpl类，继承IntrospectedTableMyBatis3Impl，
 *                  重写getMyBatis3SqlMapNamespace方法改变生成后的mapper.xml的命名空间
 * @Author : xiekun
 * @Create Date : 2020年05月08日 15:04
 * @ModificationHistory Who   When     What
 * ------------    --------------    ---------------------------------
 */
public class MyIntrospectedTableImpl extends IntrospectedTableMyBatis3Impl {

    @Override
    public String getMyBatis3SqlMapNamespace() {
        //获取mapper.java包名
        String targetPackage = context.getJavaClientGeneratorConfiguration().getTargetPackage();
        StringBuffer stringBuffer = new StringBuffer();
        String namespace = getMyBatis3JavaMapperType();
        if (namespace == null) {
            namespace = getMyBatis3FallbackSqlMapNamespace();
        }
        //获取最后一个名字
        String daoName = namespace.substring(namespace.lastIndexOf("."));
        //修改命名空间为对应扩展文件
        stringBuffer.append(targetPackage).append(daoName).append("Ext");
        return stringBuffer.toString();
    }

}
