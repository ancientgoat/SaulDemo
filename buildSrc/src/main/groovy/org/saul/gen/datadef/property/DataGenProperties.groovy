package org.saul.gen.datadef.property

import org.springframework.core.io.ClassPathResource

import java.nio.file.Files
import java.nio.file.Path;
import java.nio.file.Paths

/**
 *
 */
public class DataGenProperties {
    private String newDirectory;
    private String usedDirectory;
    private Path newDirectoryPath;
    private Path usedDirectoryPath;

    private DataGenProperties() {
    }

    public String getNewDirectory() {
        return newDirectory;
    }

    public String getUsedDirectory() {
        return usedDirectory;
    }

    public Path getNewDirectoryPath() {
        return newDirectoryPath;
    }

    public Path getUsedDirectoryPath() {
        return usedDirectoryPath;
    }

    /**
     *
     */
    public static class Builder {

        public static final String NEW_PATH_PROPERTY_NAME = "directory.datadef.newdir";
        public static final String USED_PATH_PROPERTY_NAME = "directory.datadef.useddir";

        private DataGenProperties properties = new DataGenProperties();

        public Builder(final String inPropertyFile) {

            // 'saul.properties.groovy'

            File propFile = getFromClasspath(inPropertyFile);

            println new File(".").getAbsolutePath()
            println new File(".").getAbsolutePath()
            println new File(".").getAbsolutePath()
            println new File(".").getAbsolutePath()
            println new File(".").getAbsolutePath()
            println new File(".").getAbsolutePath()
            println new File(".").getAbsolutePath()
            println new File(".").getAbsolutePath()
            println new File(".").getAbsolutePath()

            try {
                final Properties props = new Properties();
                props.load(new FileReader(propFile));

                this.properties.newDirectory = props.getProperty(NEW_PATH_PROPERTY_NAME);
                this.properties.usedDirectory = props.getProperty(USED_PATH_PROPERTY_NAME);
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }

            validateProperties();
            setPaths();

        }

        /**
         *
         */

        private void validateProperties() {
            if (null == this.properties.newDirectory)
                throw new IllegalArgumentException(
                        String.format("Missing property '%s'.", NEW_PATH_PROPERTY_NAME));

            if (null == this.properties.usedDirectory)
                throw new IllegalArgumentException(String.format(
                        "Missing property '%s'.", USED_PATH_PROPERTY_NAME));
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
                    returnFile = new File(inName)
                }
                if (!returnFile.exists()) {
                    throw new IllegalArgumentException(String.format(
                            "File/Dir '%s' does NOT exist in the Classpath.", inName));
                }
            }
            return returnFile;
        }

        /**
         *
         */
        private void setPaths() {

            final File newfile = getFromClasspath(this.properties.newDirectory);
            final File usedfile = getFromClasspath(this.properties.usedDirectory);

            this.properties.newDirectoryPath = Paths.get(newfile.getAbsolutePath());
            this.properties.usedDirectoryPath = Paths.get(usedfile.getAbsolutePath());

            if (!this.properties.newDirectoryPath.toFile().exists())
                throw new IllegalArgumentException(String.format("Path '%s' does NOT exist.",
                        this.properties.newDirectory));

            if (!this.properties.usedDirectoryPath.toFile().exists())
                throw new IllegalArgumentException(String.format("Path '%s' does NOT exist.",
                        this.properties.usedDirectory));
        }

        public Builder setNewDirectory(String newDirectory) {
            this.properties.newDirectory = newDirectory;
            return this;
        }

        public Builder setUsedDirectory(String usedDirectory) {
            this.properties.usedDirectory = usedDirectory;
            return this;
        }

        public Builder setNewDirectoryPath(Path newDirectoryPath) {
            this.properties.newDirectoryPath = newDirectoryPath;
            return this;
        }

        public Builder setUsedDirectoryPath(Path usedDirectoryPath) {
            this.properties.usedDirectoryPath = usedDirectoryPath;
            return this;
        }

        public DataGenProperties build() {

            return this.properties;
        }
    }
}
