package org.saul.datadefinition.model.datadefinition;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import java.util.Set;

/**
 *
 */
@JsonRootName("DataDefinitions")
public class SaulDataDefinitions {

	@JsonProperty("dataDefinition")
	private Set<SaulDataDefinition> saulDataDefinitionSet;

	public Set<SaulDataDefinition> getSaulDataDefinitionSet() {
		return saulDataDefinitionSet;
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


