package org.saul.gen

import com.fasterxml.jackson.databind.ObjectMapper
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.saul.datadefinition.DataDefinitionDto
import org.saul.datadefinition.DataDefinitionsDto
import org.saul.datadefinition.JsonMapperHelper
import org.saul.datadefinition.SqlPacket
import org.saul.util.ExceptionHelper
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource

//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.SpringApplication
//import org.springframework.boot.autoconfigure.SpringBootApplication
//import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder
//import org.springframework.context.annotation.Bean
//import org.springframework.core.io.ClassPathResource

import javax.sql.DataSource

/**
 *
 */
//@Component
//@SpringBootApplication
class SaulOutput implements Plugin<Project> {

    public static final String DATA_DEFINITION_DIR = "dataDefinitions/";

//    @Autowired
//    private DataSource dataSource;


//    public static void main(String[] args) {
//        SpringApplication.run(SaulOutput.class, args);
//    }

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.sqlite.JDBC");
        dataSourceBuilder.url("jdbc:sqlite:TEST.db");
        return dataSourceBuilder.build();
    }

    void apply(Project project) {

        DataSource ds = dataSource();

        def connection = ds.getConnection()
        def statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS TEST ( ID Integer );")
        def update = statement.executeUpdate()

        final ClassPathResource resource = new ClassPathResource(DATA_DEFINITION_DIR);

        tryThis(resource)

        project.task('hello') << { println "Hello from the GreetingPlugin" }
        File dir = project.projectDir


        System.out.println("package org.saul;");
        System.out.println("package org.saul;");
        System.out.println("package org.saul;");
        System.out.println("package org.saul;");
        System.out.println("package org.saul;");
        System.out.println("package org.saul;");
        System.out.println("package org.saul;");
        System.out.println("package org.saul;");
        System.out.println("package org.saul;");
        System.out.println("package org.saul;");
        System.out.println("package org.saul;");
    }

    /**
     *
     */
    def tryThis(final ClassPathResource inResource) {

        System.out.println("***********************************");
        System.out.println("***********************************");
        System.out.println("***********************************");

        def topDir = inResource.getFile()

        if (!topDir.exists()) {
            throw new IllegalFormatCodePointException("The directory '${topDir.getName()}' doesn't exist");
        }

        def files = topDir.listFiles()

        for ( final File file : files){

            final List<DataDefinitionDto> dataDefinitionDtos = outputYaml(file);

            if (0 < dataDefinitionDtos.size()) {

                final DataDefinitionDto dataDefinitionDto = dataDefinitionDtos.get(0);
                final SqlPacket sqlPacket = new SqlPacket(dataDefinitionDto.getSql());

                System.out.println("***********************************");

            }
        }

        System.out.println("***********************************");
        System.out.println("***********************************");
    }

    /**
     *
     */
    private List<DataDefinitionDto> outputYaml(final File inFile) {
        try {
            final ObjectMapper mapper = JsonMapperHelper.newInstanceYaml();
            final DataDefinitionsDto definitionsDto = mapper.readValue(inFile, DataDefinitionsDto.class);
            final List<DataDefinitionDto> dataDefinitionList = definitionsDto.getDataDefinition();

            return dataDefinitionList;
        } catch (Exception e) {
            println ExceptionHelper.toString(e)
            throw new IllegalArgumentException(e);
        }
    }
}


