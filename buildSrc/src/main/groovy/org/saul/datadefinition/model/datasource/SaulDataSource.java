package org.saul.datadefinition.model.datasource;

import javax.sql.DataSource;
import org.saul.datadefinition.inf.SaulHasName;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;

/**
 *
 */
public class SaulDataSource implements SaulHasName {

	private String name;
	private String type;
	private String url;
	private String driverClassName;

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getUrl() {
		return url;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public DataSource getDataSource() {
		validateDataSource();
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName(this.driverClassName);
		dataSourceBuilder.url(this.url);
		return dataSourceBuilder.build();
	}

	private void validateDataSource() {
		final StringBuilder sb = new StringBuilder();
		if (null == this.driverClassName) {
			sb.append("Missing 'driverClassName', please fix.\n");
		}
		if (null == this.driverClassName) {
			sb.append("Missing 'url', please fix.\n");
		}
		if (0 < sb.length()) {
			throw new IllegalArgumentException(sb.toString());
		}

	}
}
