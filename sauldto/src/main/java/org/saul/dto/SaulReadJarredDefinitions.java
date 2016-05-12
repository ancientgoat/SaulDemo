package org.saul.dto;

import com.google.common.collect.Sets;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.apache.commons.io.IOUtils;
import org.saul.gradle.datadefinition.model.datadefinition.SaulDataDefinition;
import org.springframework.core.io.ClassPathResource;

import static org.saul.gradle.datadefinition.helper.SaulSetup.readDefinition;

/**
 *
 */
public class SaulReadJarredDefinitions {
	private SaulReadJarredDefinitions() {
	}

	public static final String SAUL_DATADEF_DIR = "/saul/datadef/generated/";
	public static final String SAUL_DATADEF_DIR2 = SAUL_DATADEF_DIR.substring(1);

	/**
	 *
	 */
	public static Set<SaulDataDefinition> readAllDefinitions() {

		Set<SaulDataDefinition> definitionSet = Sets.newHashSet();

		SaulDataDefinition dataDefinition;

		try {
			ClassPathResource resource = new ClassPathResource(SAUL_DATADEF_DIR);
			System.out.println("======================================");
			System.out.println(resource);
			System.out.println("======================================");
			InputStream resourceAsStream = SaulReadJarredDefinitions.class.getResourceAsStream(SAUL_DATADEF_DIR);
			URL resource1 = SaulReadJarredDefinitions.class.getResource(SAUL_DATADEF_DIR);

			URI uri = resource1.toURI();

			System.out.println("======================================");
			System.out.println(resource1.toString());
			System.out.println(resource1);
			System.out.println(resourceAsStream);
			System.out.println("======================================");

			String fileString = resource1.getFile();
			System.out.println("======================================");
			System.out.println("File String : " + fileString);
			System.out.println("======================================");

			String first = resource1.toString();
			first = first.replace("jar:", "");

			System.out.println("======================================");
			System.out.println("First : " + first);
			System.out.println("======================================");

			HashMap<String, String> map = new HashMap<>();

			FileSystem fs = FileSystems.newFileSystem(uri, map);
			JarFile jarFile = new JarFile(fs.toString());

			Enumeration<JarEntry> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				JarEntry e = entries.nextElement();
				String name = e.getName();

				if (name.startsWith(SAUL_DATADEF_DIR2) && !e.isDirectory()) {
					System.out.println("Entry : " + name);
					InputStream inputStream = jarFile.getInputStream(e);
					String theString = IOUtils.toString(inputStream, "UTF-8");
					SaulDataDefinition localDataDef = readDefinition(theString);
					System.out.println("======================================");
					System.out.println(theString);
					System.out.println("======================================");
					definitionSet.add(localDataDef);
				}
			}
			System.out.println("======================================");

		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		return definitionSet;
	}
}
