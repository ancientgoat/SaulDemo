package org.saul.property;

import java.io.File;
import org.gradle.api.Project;
import org.springframework.core.io.ClassPathResource;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 */
public class DataGenProperties {

	private static final String DD_NEW = "src/main/resources/definitions/datadef/";
	private static final String DS_NEW = "src/main/resources/definitions/datasource/";

	private Project project;
	private String ddDir;
	private Path ddDirPath;
	private String dsDir;
	private Path dsDirPath;

	private DataGenProperties() {
	}

	public Project getProject() {
		return project;
	}

	public String getDdDir() {
		return ddDir;
	}

	public Path getDdDirPath() {
		return ddDirPath;
	}

	public String getDsDir() {
		return dsDir;
	}

	public Path getDsDirPath() {
		return dsDirPath;
	}

	/**
	 *
	 */
	public static class Builder {

		private DataGenProperties dataGenProperties = new DataGenProperties();

		/**
		 *
		 */
		public Builder(Project inProject) {

			this.dataGenProperties.project = inProject;

			try {
				File buildRootDir = inProject.getBuildDir()
						.getParentFile();

				String topDir = String.format("%s%s", buildRootDir.getAbsolutePath(), File.separator);

				this.dataGenProperties.ddDir = String.format("%s%s", topDir, DD_NEW);
				this.dataGenProperties.dsDir = String.format("%s%s", topDir, DS_NEW);

			} catch (Exception e) {
				throw new IllegalArgumentException(e);
			}
			setPaths();
		}

		/**
		 *
		 */
		private File getFromClasspath(final String inName) {
			File returnFile = null;
			try {
				final ClassPathResource resource = new ClassPathResource(inName);
				returnFile = resource.getFile();
			} catch (Exception e) {
				if (null == returnFile || !returnFile.exists()) {
					returnFile = new File(inName);
				}
				if (!returnFile.exists()) {
					throw new IllegalArgumentException(
							String.format("File/Dir '%s' does NOT exist in the Classpath.", inName));
				}
			}
			return returnFile;
		}

		/**
		 *
		 */
		private void setPaths() {

			this.dataGenProperties.ddDirPath = Paths.get(this.dataGenProperties.ddDir);
			this.dataGenProperties.dsDirPath = Paths.get(this.dataGenProperties.dsDir);

			exists(this.dataGenProperties.ddDirPath.toFile());
			exists(this.dataGenProperties.dsDirPath.toFile());
		}

		public void exists(File inFile) {
			if (!inFile.exists())
				throw new IllegalArgumentException(
						String.format("Path '%s' does NOT exist.", inFile.getAbsolutePath()));
		}

		public DataGenProperties build() {
			return this.dataGenProperties;
		}
	}
}
