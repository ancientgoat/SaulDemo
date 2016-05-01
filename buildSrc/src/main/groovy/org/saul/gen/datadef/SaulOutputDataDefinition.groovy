package org.saul.gen.datadef

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.saul.datadefinition.helper.DxSetup
import org.springframework.boot.context.properties.ConfigurationProperties

/**
 *
 */
// @org.springframework.stereotype.Component
// @ConfigurationProperties(locations = "classpath:application.yml")
public class SaulOutputDataDefinition implements Plugin<Project> {

     /**
     *
     * @param project
     */
    def void apply(Project project) {

        def filtered = ['class', 'active']
        println "Hello from the GreetingPlugin"
        println "************: SaulOutputDataDefinition :**********"
        println project.properties
                .sort { it.key }
                .collect { it }
                .findAll { !filtered.contains(it.key) }
                .join('\n')
        println "************: SaulOutputDataDefinition :**********"

        def properties = project.properties

        project.task('genDataDef') << {
            DxSetup.genDataDef(project);
        }
    }
}