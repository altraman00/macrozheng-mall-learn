package com.macro.mall;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

import java.util.List;

/**
 * @Project : mall
 * @Package Name : com.macro.mall
 * @Description : TODO
 * @Author : xiekun
 * @Create Date : 2020年05月08日 14:52
 * @ModificationHistory Who   When     What
 * ------------    --------------    ---------------------------------
 */
public class DaoExtPlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> warnings) {
        return false;
    }



    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable){



        return null;
    }


}
