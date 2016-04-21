package org.saul.datadefinition.helper;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.saul.datadefinition.model.datadefinition.SaulDataDefinition;
import org.saul.datadefinition.model.datadefinition.SaulDdField;
import org.saul.datadefinition.model.datasource.SaulDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 */
public class SaulDdHelper {

	private final SaulDataDefinition definitionDto;

	/**
	 *
	 * @param inSaulDataDefinition
	 */
	public SaulDdHelper(final SaulDataDefinition inSaulDataDefinition) {
		this.definitionDto = inSaulDataDefinition;
	}

	/**
	 *
	 */
	public boolean needsFilling() {
		return null == definitionDto.getIdentity() //
				|| null == definitionDto.getSource()//
				|| null == definitionDto.getName()//
				|| null == definitionDto.getShortName()//
				|| null == definitionDto.getDataSourceName()//
				;
	}

	/**
	 *
	 */
	public boolean canFillFromSql() {
		return null != definitionDto.getDataSourceName() && null != definitionDto.getSql();
	}

	/**
	 *
	 */
	public SaulDataDefinition fillDataDefFromSql(final SaulDataDefinition inDef) {

		final SaulDataDefinition.Builder builder = new SaulDataDefinition.Builder();

		// private SaulIdentity identity;
		// private String source;
		// private String name;
		// private String shortName;

		final String sql = inDef.getSql();

		try {
			final SaulDataSource saulDataSource = inDef.getDataSource();
			final DataSource dataSource = saulDataSource.getDataSource();
			final Connection connection = dataSource.getConnection();
			final PreparedStatement preparedStatement = connection.prepareStatement(sql);
			final ResultSetMetaData metaData = preparedStatement.getMetaData();

			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				final SaulDdField field = new SaulDdField.Builder()           	//
						.setColumn(metaData.getColumnName(i))                   //
						.setJavaDataType(metaData.getColumnType(i))             //
						.setColumnDisplaySize(metaData.getColumnDisplaySize(i)) //
						.setPrecision(metaData.getPrecision(i))                 //
						.setScale(metaData.getScale(i))                         //
						.build();
			}

		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		return builder.build();
	}
}
