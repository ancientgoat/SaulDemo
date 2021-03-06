package org.saul.dto;

<#assign CLASSNAME = "${className}DtoBuilder">

<#list imports as imp>
${imp}
</#list>

/**
 * Generated by some old goat.  Streamlined Builder DTO.
 */
public class ${CLASSNAME} {

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

    /**
     *
     */
    public class Builder {

        private ${CLASSNAME} ${classNameVariable};

        <#-- add class setters. -->
        <#list fields as p>
        public Builder ${p.setterName}( ${p.javaTypeName} ${p.inName} ){
            this.${classNameVariable}.${p.propertyName} = ${p.inName};
            return this;
        }

        </#list>
        public ${CLASSNAME} build(){
            return this.${classNameVariable};
        }
    }
}
