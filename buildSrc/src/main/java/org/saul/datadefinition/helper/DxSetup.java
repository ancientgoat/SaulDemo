package org.saul.datadefinition.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.gradle.api.Project;
import org.saul.datadefinition.JsonMapperHelper;
import org.saul.datadefinition.model.datadefinition.SaulDataDefinition;
import org.saul.datadefinition.model.datadefinition.SaulDataDefinitions;
import org.saul.datadefinition.model.datadefinition.SaulMasterDefinitions;
import org.saul.property.DataGenProperties;
import org.saul.property.SaulDataSource;
import org.saul.property.SaulDataSources;
import org.saul.util.ExceptionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class DxSetup {

	private static final Logger LOG = LoggerFactory.getLogger(DxSetup.class);

	private static DataGenProperties propertyPacket;

	private DxSetup() {
	}

	/**
	 *
	 */
	public static void genDataDef(Project inProject) {
		readProperties(inProject);
		genDataDefinitions(propertyPacket);
	}

	/**
	 *
	 */
	private static void readProperties(Project inProject) {
		try {
			propertyPacket = new DataGenProperties.Builder(inProject).build();
		} catch (Exception e) {
			LOG.error(ExceptionHelper.toString(e));
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 *
	 * @param fromPath
	 * @param toPath
	 */
	private static void moveFiles(Path fromPath, Path toPath) {

		Stream<Path> list = null;

		try {
			list = Files.list(fromPath);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}

		list.forEach(p -> {
			if (Files.isRegularFile(p)) {
				Path resolve = toPath.resolve(p.getFileName());
				try {
					Path newPath =
							Files.move(p, resolve, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
				} catch (Exception e) {
					throw new IllegalArgumentException(e);
				}
			}
		});
	}

	/**
	 *
	 */
	private static void genDataDefinitions(final DataGenProperties inPropertyPacket) {
		try {
			final SaulMasterDefinitions masterDefinitions = readYamlSources(inPropertyPacket);

			for (final SaulDataDefinition dataDef : masterDefinitions.getDataDefinitionSet()) {

				// Write Data Definitions out to a set of beans.
				final SaulDdHelper helper = new SaulDdHelper(dataDef);
				final SaulDataDefinition newDataDef = helper.fillDataDefFromSql();

				String buildDir = inPropertyPacket.getProject()
						.getBuildDir()
						.getAbsolutePath();
				String fileName = newDataDef.getFileName();
				String outputDirectory = dataDef.getOutputDirectory();
				String outputFileDir =
						String.format("%s%s%s%s", buildDir, File.separator, outputDirectory, File.separator);

				String fullFilename = JsonMapperHelper.writeBeanToYamlFile(outputFileDir, fileName, newDataDef);

				System.out.println("********************************************");
				System.out.println("********************************************");
				System.out.println("********************************************");
				System.out.println(fileName);
				System.out.println(outputFileDir);
				System.out.println(outputDirectory);
				System.out.println(fullFilename);
				System.out.println("********************************************");
				System.out.println(JsonMapperHelper.writeBeanToYaml(newDataDef));
				System.out.println("********************************************");
				System.out.println("********************************************");
				System.out.println("********************************************");
			}
		} catch (Exception e) {
			LOG.error(ExceptionHelper.toString(e));
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 *
	 */
	private static SaulMasterDefinitions readYamlSources(DataGenProperties inPropertyPacket) {

		SaulMasterDefinitions masterDefinitions = null;

		try {
			Set<SaulDataSource> sourceSet = readAllSources(inPropertyPacket);
			Set<SaulDataDefinition> definitionSet = readAllDefinitions(inPropertyPacket);
			masterDefinitions = new SaulMasterDefinitions(definitionSet, sourceSet);

			for (final SaulDataDefinition dataDef : masterDefinitions.getDataDefinitionSet()) {
				String dataSourceName = dataDef.getDataSourceName();
				if (null == dataSourceName) {
					throw new IllegalArgumentException(
							String.format("Data Source Name can NOT be null for DataDef '%s'.", dataDef.getName()));
				}
				dataDef.setDataSource(masterDefinitions.getDataSource(dataSourceName));
			}
		} catch (Exception e) {
			LOG.error(ExceptionHelper.toString(e));
			throw new IllegalArgumentException(e);
		}
		return masterDefinitions;
	}

	/**
	 *
	 */
	private static Set<SaulDataSource> readAllSources(DataGenProperties inPropertyPacket) {

		Set<SaulDataSource> SourceSet = Sets.newHashSet();
		File topDir = inPropertyPacket.getDsDirPath()
				.toFile();
		File[] files = topDir.listFiles();

		if (!topDir.exists()) {
			throw new IllegalArgumentException(String.format("The directory '%s' doesn't exist", topDir.getName()));
		}

		for (final File file : files) {
			SourceSet.addAll(readSources(file));
		}
		return SourceSet;
	}

	/**
	 *
	 */
	private static Set<SaulDataSource> readSources(File inFile) {
		try {
			final ObjectMapper mapper = JsonMapperHelper.newInstanceYaml();
			final SaulDataSources dataSources = mapper.readValue(inFile, SaulDataSources.class);
			return Sets.newHashSet(dataSources.getSaulDataSources());
		} catch (Exception e) {
			LOG.error(ExceptionHelper.toString(e));
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 *
	 */
	private static Set<SaulDataDefinition> readAllDefinitions(DataGenProperties inPropertyPacket) {

		Set<SaulDataDefinition> definitionSet = Sets.newHashSet();
		File topDir = inPropertyPacket.getDdDirPath()
				.toFile();
		File[] files = topDir.listFiles();

		if (!topDir.exists()) {
			throw new IllegalArgumentException(String.format("The directory '%s' doesn't exist", topDir.getName()));
		}

		for (final File file : files) {
			definitionSet.addAll(readDefinitions(file));
		}
		return definitionSet;
	}

	/**
	 *
	 */
	private static Set<SaulDataDefinition> readDefinitions(File inFile) {
		try {
			final ObjectMapper mapper = JsonMapperHelper.newInstanceYaml();
			final SaulDataDefinitions dataDefinitions = mapper.readValue(inFile, SaulDataDefinitions.class);
			return Sets.newHashSet(dataDefinitions.getSaulDataDefinitionList());
		} catch (Exception e) {
			LOG.error(ExceptionHelper.toString(e));
			throw new IllegalArgumentException(e);
		}
	}
}

