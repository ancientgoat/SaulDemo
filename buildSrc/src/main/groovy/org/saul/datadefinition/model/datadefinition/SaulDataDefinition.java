package org.saul.datadefinition.model.datadefinition;

import com.beust.jcommander.internal.Lists;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import org.saul.datadefinition.helper.SaulDdHelper;
import org.saul.datadefinition.inf.SaulHasName;
import org.saul.datadefinition.model.datasource.SaulDataSource;

/**
 *
 */
public class SaulDataDefinition implements SaulHasName {

	private SaulIdentity identity;
	private String source;
	private String name;
	private String shortName;
	private String dataSourceName;
	private String sql;
	private List<SaulDdField> fields = Lists.newArrayList();

	@JsonIgnore
	private SaulFieldMaster fieldMaster;

	@JsonIgnore
	private SaulDataSource dataSource;

	@JsonIgnore
	private SaulSqlPacket sqlPacket;

	@JsonIgnore
	private final SaulDdHelper ddHelper;

	public SaulDataDefinition() {
		this.ddHelper = new SaulDdHelper(this);
	}

	public void setSql(final String inSql) {
		if (null == inSql) {
			throw new IllegalArgumentException("Can not enter SQL that is NULL.");
		}
		this.sql = inSql;
		sqlPacket = new SaulSqlPacket(this.getSql());
	}

	public void setDataSource(final SaulDataSource inDataSource) {
		dataSource = inDataSource;
	}

	public SaulDataSource getDataSource() {
		return dataSource;
	}

	public SaulDdHelper getDdHelper() {
		return ddHelper;
	}

	public SaulSqlPacket getSqlPacket() {
		return sqlPacket;
	}

	public SaulIdentity getIdentity() {
		return identity;
	}

	public String getSource() {
		return source;
	}

	public String getName() {
		return name;
	}

	public String getShortName() {
		return shortName;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public String getSql() {
		return sql;
	}

	public List<SaulDdField> getFields() {
		return fields;
	}

	/**
	 *
	 */
	public static final class Builder {

		final SaulDataDefinition dataDefinition = new SaulDataDefinition();

		public Builder() {
		}

		public Builder(final SaulDataDefinition inDataDef) {
			this.dataDefinition.identity = inDataDef.identity;
			this.dataDefinition.source = inDataDef.source;
			this.dataDefinition.name = inDataDef.name;
			this.dataDefinition.shortName = inDataDef.shortName;
			this.dataDefinition.dataSourceName = inDataDef.dataSourceName;
			this.dataDefinition.sql = inDataDef.sql;
		}

		public Builder setIdentity(final SaulIdentity inIdentity) {
			this.dataDefinition.identity = inIdentity;
			return this;
		}

		public Builder setSource(final String inSource) {
			this.dataDefinition.source = inSource;
			return this;
		}

		public Builder setName(final String inName) {
			this.dataDefinition.name = inName;
			return this;
		}

		public Builder setShortName(final String inShortName) {
			this.dataDefinition.shortName = inShortName;
			return this;
		}

		public Builder setDataSource(final String inDataSource) {
			this.dataDefinition.dataSourceName = inDataSource;
			return this;
		}

		public Builder setSqlPacket(final SaulSqlPacket inSqlPacket) {
			this.dataDefinition.sqlPacket = inSqlPacket;
			return this;
		}

		public Builder setFields(final List<SaulDdField> inFields) {
			this.dataDefinition.fields = inFields;
			return this;
		}

		public SaulDataDefinition build() {
			return this.dataDefinition;
		}
	}
}
