package org.saul.datadefinition;

import com.beust.jcommander.internal.Lists;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 *
 */
//@JsonRootName("dataDefinition")
//@JsonIgnoreProperties(ignoreUnknown = true)
public class DataDefinitionDto {

	private Identity identity;
	private String source;
	private String name;
	private String shortName;
	private String dataSource;
	private String sql;

	private List<DdField> fields = Lists.newArrayList();



	//	- identity    :
	//		source      : TD
	//		name        : MILK_TABLE
	//		short_name  : MILK_TABLE
	//		data_source : TD_ORACLE
	//		cache       :
	//			duration_time : 10
	//			duration_type : minutes
	//		fields :
	//			- column      : Td_Alert_Id
	//			  alias       : Alert_Id
	//			  data_type   : long
	//		sql : |
	//			SELECT  Td_Alert_Id
	//			FROM    TDDATA.TD_ALERT

	public Identity getIdentity() {
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

	public String getDataSource() {
		return dataSource;
	}

	public String getSql() {
		return sql;
	}

	public List<DdField> getFields() {
		return fields;
	}
}
