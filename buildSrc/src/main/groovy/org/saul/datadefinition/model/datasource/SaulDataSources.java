package org.saul.datadefinition.model.datasource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import java.util.Set;

/**
 *
 */
@JsonRootName("DataSources")
public class SaulDataSources {

	@JsonProperty("dataSource")
	private Set<SaulDataSource> saulDataSourceSet;

	public Set<SaulDataSource> getDataSourceList() {
		return saulDataSourceSet;
	}
}
