package org.saul;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.snakeyaml.DumperOptions;
import com.fasterxml.jackson.dataformat.yaml.snakeyaml.Yaml;
import com.google.common.collect.Sets;
import org.saul.datadefinition.JsonMapperHelper;
import org.saul.datadefinition.helper.SaulDdHelper;
import org.saul.datadefinition.model.datadefinition.SaulDataDefinition;
import org.saul.datadefinition.model.datadefinition.SaulDataDefinitions;
import org.saul.datadefinition.model.datadefinition.SaulMasterDefinitions;
import org.saul.property.PropTestProp;
import org.saul.property.SaulDataSources;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.StringReader;
import java.util.Set;

import static com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature.CANONICAL_OUTPUT;

/**
 *
 */
@Component
public class SaulInit implements InitializingBean {

    private static final String DATA_DEFINITION_DIR = "dataDefinitions/";

    @Autowired
    private SaulDataSources saulDataSources;

    /**
     *
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    /**
     *
     */
    void init() {
        final ClassPathResource resource = new ClassPathResource(DATA_DEFINITION_DIR);
        final SaulMasterDefinitions masterDefinitions = readYamlSources(resource);

        for (final SaulDataDefinition dataDef : masterDefinitions.getDataDefinitionSet()) {
            // Write Data Definitions out to a set of beans.
            final SaulDdHelper helper = new SaulDdHelper(dataDef);
            final SaulDataDefinition newDataDef = helper.fillDataDefFromSql();

            // final SaulDataSource dataSource = newDataDef.getDataSource();

            JsonMapperHelper.writeBeanToYamlFile(newDataDef.getOutputFileName(), newDataDef);
            System.out.println("********************************************");
            System.out.println("********************************************");
            System.out.println("********************************************");
            System.out.println(JsonMapperHelper.writeBeanToYaml(newDataDef));
            System.out.println("********************************************");
            System.out.println("********************************************");
            System.out.println("********************************************");
        }
    }

    /**
     *
     */

    private SaulMasterDefinitions readYamlSources(final ClassPathResource inResource) {

        System.out.println("saulDataSources : " + saulDataSources.dumpToString());
        System.out.println("saulDataSources : " + saulDataSources.dumpToString());
        System.out.println("saulDataSources : " + saulDataSources.dumpToString());
        System.out.println("saulDataSources : " + saulDataSources.dumpToString());

        SaulMasterDefinitions masterDefinitions = null;

        try {
            final Set<SaulDataSources> sourceSet = Sets.newHashSet();
            final Set<SaulDataDefinition> definitionSet = Sets.newHashSet();

            final File topDir = inResource.getFile();

            if (!topDir.exists()) {
                throw new IllegalArgumentException(
                        String.format("The directory '%s' doesn't exist", topDir.getName()));
            }

            final File[] files = topDir.listFiles();

            for (final File file : files) {
                final SaulDataDefinitions dataDefinitions = yamlToBean(file, SaulDataDefinitions.class);
                definitionSet.addAll(dataDefinitions.getSaulDataDefinitionSet());
            }

            masterDefinitions = new SaulMasterDefinitions(definitionSet, saulDataSources.getSaulDataSource());

            // Assign DataSources
            for (final SaulDataDefinition dataDef : masterDefinitions.getDataDefinitionSet()) {
                String dataSourceName = dataDef.getDataSourceName();
                if (null == dataSourceName) {
                    throw new IllegalArgumentException(
                            String.format("Data Source Name can NOT be null for DataDef '%s'.",
                                    dataDef.getName()));
                }
                dataDef.setDataSource(masterDefinitions.getDataSource(dataSourceName));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        return masterDefinitions;
    }

    /**
     *
     */
    private <C> C yamlToBean(final File inFile, final Class inClazz) {
        try {
            final ObjectMapper mapper = JsonMapperHelper.newInstanceYaml();
            final C yamlFile = (C) mapper.readValue(inFile, inClazz);
            return yamlFile;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
            // System.out.println ExceptionHelper.toString(e)
        }
    }
}
