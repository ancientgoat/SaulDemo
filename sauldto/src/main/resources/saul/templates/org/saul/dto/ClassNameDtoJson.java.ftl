package org.saul.dto;

<#assign CLASSNAME = "${className}DtoJson">

<#list imports as imp>
${imp}
</#list>

/**
* Generated by some old goat.  A JSON DTO has no setters.
*/
public class ${className}DtoJson {

    <#-- add class property definitions. -->
    <#list fields as p>
    private ${p.javaTypeName} ${p.propertyName};
    </#list>

    <#-- add class getters. -->
    <#list fields as p>
    public ${p.javaTypeName} ${p.getterName}(){
        return this.${p.propertyName};
    }

    </#list>
}
