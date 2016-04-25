package org.saul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

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
}


