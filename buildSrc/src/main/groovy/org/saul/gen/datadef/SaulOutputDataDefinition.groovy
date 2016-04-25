package org.saul.gen.datadef

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.saul.gen.datadef.property.DataGenProperties
import org.saul.util.ExceptionHelper
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.core.io.ClassPathResource

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

/**
 *
 */
@org.springframework.stereotype.Component
@ConfigurationProperties(locations = "classpath:application.yml")
public class SaulOutputDataDefinition implements Plugin<Project> {

    public static final String DATA_DEFINITION_NEW_DIR = "dataDef/new";
    public static final String DATA_DEFINITION_USED_DIR = "dataDef/used";
    public static final String PROPERTY_FILE_NAME = 'saul.properties';

    public static final String DATA_DEFINITION_FILE_PREFIX = "DD_";
    public static final String DATA_SOURCE_FILE_PREFIX = "DS_";

    private DataGenProperties propertyPacket;

    /**
     *
     * @param project
     */
    def void apply(Project project) {

        readProperties()

        println "elmo0 : ${this.propertyPacket.getNewDirectory()}"
        println "elmo0 : ${this.propertyPacket.getUsedDirectory()}"

        def filtered = ['class', 'active']


        println "111111111111111111111111111111111111111111111111";
        project.task('genDataDefReset') << {
            println "222222222222222222222222222222222222222222222222";
            resetUsedFiles(this.propertyPacket);
            genDataDefinitions(this.propertyPacket);
        }

        project.task('genDataDef') << {
            println "3333333333333333333333333333333333333333333333333";
            genDataDefinitions(this.propertyPacket);
        }

//        println "Hello from the GreetingPlugin"
//        println "************: SaulOutputDataDefinition :**********"
//        println project.propertyPacket
//                .sort { it.key }
//                .collect { it }
//                .findAll { !filtered.contains(it.key) }
//                .join('\n')
//        println "************: SaulOutputDataDefinition 9999999999999999 :**********"
//        println project.propertyPacket.getProperties()
//        println "************: SaulOutputDataDefinition 9999999999999999 :**********"
//        println "************: ++++++++++++++++++++++++ :**********"
//        println "************: ++++++++++++++++++++++++ :**********"
//        println "************: ++++++++++++++++++++++++ :**********"
//        println "************: ++++++++++++++++++++++++ :**********"
//        println System.getenv()
//                .sort { it.key }
//                .collect { it }
//                .findAll { !filtered.contains(it.key) }
//                .join('\n')
//        println "************: ++++++++++++++++++++++++ :**********"
//        println "************: ++++++++++++++++++++++++ :**********"
//        println "************: ++++++++++++++++++++++++ :**********"
    }

    def void readProperties() {
        try {
            propertyPacket = new DataGenProperties.Builder(PROPERTY_FILE_NAME).build();
        } catch (Exception e) {
            println ExceptionHelper.toString(e);
            throw new IllegalArgumentException(e);
        }
    }

    def void resetUsedFiles(final DataGenProperties inPropertyPacket) {
        // Move all files from the usedDirectory back to the newDirectory
        println "+++++++++++++++++++++ : resetUsedFiles"
        println "+++++++++++++++++++++ : resetUsedFiles"

        moveFiles(inPropertyPacket.getUsedDirectoryPath(), inPropertyPacket.getNewDirectoryPath());
        //Files.move(inPropertyPacket.getUsedDirectoryPath(), inPropertyPacket.getNewDirectoryPath());
        println "+++++++++++++++++++++ : resetUsedFiles"
    }

    def void genDataDefinitions(final DataGenProperties inPropertyPacket) {

        println "+++++++++++++++++++++ : genDataDefinitions"
        println "+++++++++++++++++++++ : genDataDefinitions"

        try {
            // Move all files from the newDirectory back to the usedDirectory
            moveFiles(inPropertyPacket.getNewDirectoryPath(), inPropertyPacket.getUsedDirectoryPath());
            //Files.move(inPropertyPacket.getNewDirectoryPath(), inPropertyPacket.getUsedDirectoryPath());
        } catch (Exception e) {
            println ExceptionHelper.toString(e);
            throw new IllegalArgumentException(e);
        }
        println "+++++++++++++++++++++ : genDataDefinitions"
    }

    def moveFiles(Path fromPath, Path toPath) {

        Files.list(fromPath).parallel().forEach() {
            p ->
                if (Files.isRegularFile(p)) {
                    def resolve = toPath.resolve(
                            p.getFileName()
                    )
                    println "############################################################"
                    println "########## : ${p} : ${resolve}"
                    Path newPath = Files.move(p, resolve, StandardCopyOption.REPLACE_EXISTING,
                            StandardCopyOption.ATOMIC_MOVE);
//                    Path newPath = Files.move(p, resolve, StandardCopyOption.REPLACE_EXISTING,
//                            StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.ATOMIC_MOVE);
                    println "########## : ${newPath}"
                    println "############################################################"
                }
        }
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

//        for (final File file : files) {
//
//            final List<DataDefinitionDto> dataDefinitionDtos = outputYaml(file);
//
//            if (0 < dataDefinitionDtos.size()) {
//
//                final DataDefinitionDto dataDefinitionDto = dataDefinitionDtos.get(0);
//                final SqlPacket sqlPacket = new SqlPacket(dataDefinitionDto.getSql());
//
//                System.out.println("***********************************");
//
//            }
//        }
//
        System.out.println("***********************************");
        System.out.println("***********************************");
    }

//    /**
//     *
//     */
//    private List<DataDefinitionDto> outputYaml(final File inFile) {
//        try {
//            final ObjectMapper mapper = JsonMapperHelper.newInstanceYaml();
//            final DataDefinitionsDto definitionsDto = mapper.readValue(inFile, DataDefinitionsDto.class);
//            final List<DataDefinitionDto> dataDefinitionList = definitionsDto.getDataDefinition();
//
//            return dataDefinitionList;
//        } catch (Exception e) {
//            println ExceptionHelper.toString(e)
//            throw new IllegalArgumentException(e);
//        }
//    }
}

//        println "Hello from the GreetingPlugin"
//        println "************: SaulOutputDataDefinition :**********"
//        println project.propertyPacket
//                .sort { it.key }
//                .collect { it }
//                .findAll { !filtered.contains(it.key) }
//                .join('\n')
//        println "************: SaulOutputDataDefinition :**********"

//        //DataSource ds = dataSource();
//        final Connection connection;
//        try {
//            connection = ds.getConnection();
//            final Statement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS TEST ( ID Integer );");
//            final Integer updatedCount = statement.executeUpdate();
//
//            final ClassPathResource resource = new ClassPathResource(DATA_DEFINITION_DIR);
//
//            tryThis(resource)
//
//            project.task('hello') << {println"Hello from the GreetingPlugin"}
//            File dir = project.projectDir
//
//
//            System.out.println("package org.saul;");
//            System.out.println("package org.saul;");
//            System.out.println("package org.saul;");
//            System.out.println("package org.saul;");
//            System.out.println("package org.saul;");
//            System.out.println("package org.saul;");
//            System.out.println("package org.saul;");
//            System.out.println("package org.saul;");
//            System.out.println("package org.saul;");
//            System.out.println("package org.saul;");
//            System.out.println("package org.saul;");
//        } catch (SQLException e) {
//            throw new IllegalArgumentException(e);
//        }
