package org.saul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
//@Configuration
//@EnableAutoConfiguration
//@ComponentScan
@SpringBootApplication
@EnableConfigurationProperties
		//@EnableAutoConfiguration(
		//        exclude = [DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class])
class OneCallSaulDtoApplication {

	public static final String DATA_DEFINITION_DIR = "dataDefinitions/";
	public static final String DATA_DEFINITION_FILE_PREFIX = "DD_";
	public static final String DATA_SOURCE_FILE_PREFIX = "DS_";

	public static void main(final String[] args) {
		SpringApplication                                   //
				.run(OneCallSaulDtoApplication.class, args) //
				.close();                                   // Close right away
	}
}



