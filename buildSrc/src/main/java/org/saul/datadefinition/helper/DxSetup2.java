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

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 *
 */
public class DxSetup2 {

	private static final Logger LOG = LoggerFactory.getLogger(DxSetup2.class);

	private static DataGenProperties propertyPacket;

	private DxSetup2() {
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

				// Gradle build directory for this project
				String buildDir = inPropertyPacket.getProject()
						.getBuildDir()
						.getAbsolutePath();
				String fileName = newDataDef.getFileName();
				// Get the generated-sources/.../resources/ path
				String outputDirectory = dataDef.getOutputDirectory();
				String fs = File.separator;
				String outputFileDir =
						String.format("%s%s%s%s%s%s", buildDir, fs, outputDirectory, fs, dataDef.getPathSuffix(), fs);
				outputFileDir = outputFileDir.replace(fs + fs, fs);

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

			Path topPath = inPropertyPacket.getDdDirPath();
			WalkDirForDataDef walkDirForDataDef = new WalkDirForDataDef(topPath.toFile());
			Files.walkFileTree(topPath, walkDirForDataDef);

			Set<SaulDataDefinition> definitionSet = walkDirForDataDef.getDataDefSet();

			//Set<SaulDataDefinition> definitionSet = readAllDefinitions(inPropertyPacket);
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

	/**
	 * We walk a directory tree and save the relative path , from the top path, with each dataDefinition.
	 */
	public static class WalkDirForDataDef implements FileVisitor<Path> {

		private Set<SaulDataDefinition> dataDefSet = Sets.newHashSet();
		private String topDirAbsolutePath;

		private WalkDirForDataDef() {
		}

		public WalkDirForDataDef(File inTopDir) {
			if (!inTopDir.exists()) {
				throw new IllegalArgumentException(
						String.format("The directory '%s' doesn't exist", inTopDir.getAbsolutePath()));
			}
			this.topDirAbsolutePath = inTopDir.getAbsolutePath();
		}

		@Override
		public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
			return CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(final Path inPath, final BasicFileAttributes attrs) throws IOException {
			readAllDefinitions(inPath.toFile());
			return CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(final Path file, final IOException exc) throws IOException {
			return CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
			return CONTINUE;
		}

		/**
		 *
		 */
		public Set<SaulDataDefinition> getDataDefSet() {
			return dataDefSet;
		}

		/**
		 *
		 */
		private void readAllDefinitions(File inDirectory) {

			String localAbcolutePath = inDirectory.getParentFile()
					.getAbsolutePath();

			// For storage turn local Path separator into a forward slash
			// We'll convert back later, just in case this is a windoze system.
			String fileRelativePath = localAbcolutePath.replace(this.topDirAbsolutePath, "")
					.replace(File.separator, "/");
			;

			Set<SaulDataDefinition> localSet = Sets.newHashSet();
			localSet.addAll(readDefinitions(inDirectory));

			// Save the location of relative directory with each DataDefinition.
			localSet.forEach(dd -> {
				dd.setPathSuffix(fileRelativePath);
			});

			this.dataDefSet.addAll(localSet);
		}

		/**
		 *
		 */
		private Set<SaulDataDefinition> readDefinitions(File inFile) {
			try {
				final ObjectMapper mapper = JsonMapperHelper.newInstanceYaml();
				final SaulDataDefinitions dataDefinitions = mapper.readValue(inFile, SaulDataDefinitions.class);
				List<SaulDataDefinition> localList = dataDefinitions.getSaulDataDefinitionList();
				return Sets.newHashSet(localList);
			} catch (Exception e) {
				LOG.error(ExceptionHelper.toString(e));
				throw new IllegalArgumentException(e);
			}
		}
	}
}
