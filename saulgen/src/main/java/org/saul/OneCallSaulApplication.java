package org.saul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import static org.springframework.boot.SpringApplication.run;

/**
 *
 */
@SpringBootApplication
@EnableConfigurationProperties
//@EnableAutoConfiguration(
//        exclude = [DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class])
class OneCallSaulApplication {

    public static final String DATA_DEFINITION_DIR = "dataDefinitions/";
    public static final String DATA_DEFINITION_FILE_PREFIX = "DD_";
    public static final String DATA_SOURCE_FILE_PREFIX = "DS_";

    public static void main(final String[] args) {
        SpringApplication                                 //
                .run(OneCallSaulApplication.class, args)  //
                .close();                                 // Close right away
    }

    //    void apply(Project project) {
    //
    //        // SaulDataSource ds = saulDataSource();
    //        //
    //        // def connection = ds.getConnection()
    //        // def statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS TEST ( ID Integer );")
    //        // def update = statement.executeUpdate()
    //
    //        final ClassPathResource resource = new ClassPathResource(DATA_DEFINITION_DIR);
    //
    //        final SaulMasterDefinitions masterDefinitions = readYamlSources(resource)
    //
    //        // project.task('hello') << { println "Hello from the GreetingPlugin" }
    //        // File dir = project.projectDir
    //        // masterDefinition.getDataDefinition("x");
    //
    //        flipThroughDataDefinitions(masterDefinitions);
    //
    //        System.out.println("*** === *** === *** === *** === *** === *** === *** === *** === *** === *** === ");
    //    }
    //
    //    /**
    //     * @param inMasterDefinition
    //     */
    //    private void flipThroughDataDefinitions(final SaulMasterDefinitions inMasterDefinitions) {
    //
    //        // Attach each Data Source to each Data Definition.
    //        for (final SaulDataDefinition dataDef : inMasterDefinitions.getDataDefinitionSet()) {
    //            def dataSourceName = dataDef.getDataSourceName()
    //            def dataSourceDef = inMasterDefinitions.getDataSource(dataSourceName)
    //            dataDef.setDataSource(dataSourceDef);
    //
    //            final SaulDdHelper helper = new SaulDdHelper(dataDef);
    //            helper.fillDataDefFromSql();
    //
    //        }
    //
    //        // Connect to the database and output a new completed Data Definition
    //        //  into an directory.
    //    }
    //
    //    /**
    //     *
    //     */
    //    def SaulMasterDefinitions readYamlSources(final ClassPathResource inResource) {
    //
    //        final Set<SaulDataSources> sourceSet = Sets.newHashSet()
    //        final Set<SaulDataDefinition> definitionSet = Sets.newHashSet()
    //
    //        def topDir = inResource.getFile()
    //
    //        if (!topDir.exists()) {
    //            throw new IllegalFormatCodePointException("The directory '${topDir.getName()}' doesn't exist");
    //        }
    //
    //        def files = topDir.listFiles()
    //
    //        for (final File file : files) {
    //
    //            if (file.getName().startsWith(DATA_SOURCE_FILE_PREFIX)) {
    //
    //                final SaulDataSources saulDataSources = yamlToBean(file, SaulDataSources.class);
    //                sourceSet.addAll(saulDataSources.getDataSourceSet());
    //
    //            } else if (file.getName().startsWith(DATA_DEFINITION_FILE_PREFIX)) {
    //
    //                final SaulDataDefinitions dataDefinitions = yamlToBean(file, SaulDataDefinitions.class);
    //                definitionSet.addAll(dataDefinitions.getSaulDataDefinitionSet());
    //            }
    //        }
    //
    //        SaulMasterDefinitions masterDefinitions = new SaulMasterDefinitions(definitionSet, sourceSet);
    //
    //        // Assign DataSources
    //        for (final SaulDataDefinition dataDef : masterDefinitions.getDataDefinitionSet()) {
    //            def dataSourceName = dataDef.getDataSourceName()
    //            if (null == dataSourceName) {
    //                throw new IllegalArgumentException(String.format("Data Source Name can NOT be null for DataDef '%s'" +
    //                        ".", dataDef.getName()))
    //            }
    //            dataDef.setDataSource(masterDefinitions.getDataSource(dataSourceName))
    //        }
    //        return masterDefinitions;
    //    }
    //
    //    /**
    //     *
    //     */
    //    private <C> C yamlToBean(final File inFile, final Class inClazz) {
    //        try {
    //            final ObjectMapper mapper = JsonMapperHelper.newInstanceYaml();
    //            final C yamlFile = mapper.readValue(inFile, inClazz);
    //            return yamlFile;
    //        } catch (Exception e) {
    //            println ExceptionHelper.toString(e)
    //            throw new IllegalArgumentException(e);
    //        }
    //    }
}


