package org.saul.datadefinition.model.datadefinition;

/**
 *
 */
public class SaulDdField {

	private String columnName;
	private String alias;
	private String dataType;
	private Integer javaDataType;
	private Integer precision;
	private Integer scale;
	private Integer columnDisplaySize;

	public String getColumnName() {
		return columnName;
	}

	public String getAlias() {
		return alias;
	}

	public String getDataType() {
		return dataType;
	}

	public Integer getJavaDataType() {
		return javaDataType;
	}

	public Integer getPrecision() {
		return precision;
	}

	public Integer getScale() {
		return scale;
	}

	public Integer getColumnDisplaySize() {
		return columnDisplaySize;
	}

	/**
	 *
	 */
	public static final class Builder {

		private SaulDdField field = new SaulDdField();

		public Builder setPrecision(final Integer inPrecision) {
			this.field.precision = inPrecision;
			return this;
		}

		public Builder setScale(final Integer inScale) {
			this.field.scale = inScale;
			return this;
		}

		public Builder setColumnDisplaySize(final Integer inColumnDisplaySize) {
			this.field.columnDisplaySize = inColumnDisplaySize;
			return this;
		}

		public Builder setColumn(final String inColumn) {
			this.field.columnName = inColumn;
			return this;
		}

		public Builder setAlias(final String inAlias) {
			this.field.alias = inAlias;
			return this;
		}

		public Builder setDataType(final String inDataType) {
			this.field.dataType = inDataType;
			return this;
		}

		public Builder setJavaDataType(final Integer inJavaDataType) {
			this.field.javaDataType = inJavaDataType;
			return this;
		}

		public final SaulDdField build() {
			return this.field;
		}
	}
}
