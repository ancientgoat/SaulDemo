package org.saul.datadefinition;

import com.beust.jcommander.internal.Lists;
import java.util.List;

/**
 *
 */
public class DataDefinitionsDto {

	private List<DataDefinitionDto> dataDefinition = Lists.newArrayList();

	public List<DataDefinitionDto> getDataDefinition() {
		return dataDefinition;
	}
}

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
//


