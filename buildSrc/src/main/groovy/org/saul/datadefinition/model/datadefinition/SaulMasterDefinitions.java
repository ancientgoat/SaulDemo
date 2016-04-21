package org.saul.datadefinition.model.datadefinition;

import com.beust.jcommander.internal.Maps;
import com.google.common.collect.Lists;
import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.saul.datadefinition.inf.SaulHasName;
import org.saul.datadefinition.model.datasource.SaulDataSource;

/**
 *
 */
public class SaulMasterDefinitions {

	public Set<SaulDataDefinition> dataDefinitionSet;
	public Set<SaulDataSource> dataSourceSet;

	public final Map<String, SaulDataDefinition> dataDefinitionMap = Maps.newHashMap();
	public final Map<String, SaulDataSource> dataSourceMap = Maps.newHashMap();

	public SaulMasterDefinitions(final Set<SaulDataDefinition> inDataDefinitionSet,
			final Set<SaulDataSource> inDataSourceSet) {

		this.dataDefinitionSet = inDataDefinitionSet;
		this.dataSourceSet = inDataSourceSet;

		buildMaps();
	}

	public Set<SaulDataDefinition> getDataDefinitionSet() {
		return dataDefinitionSet;
	}

	public Set<SaulDataSource> getDataSourceSet() {
		return dataSourceSet;
	}

	/**
	 *
	 */
	private void buildMaps() {
		if (null != this.dataDefinitionSet) {
			makeMap(this.dataDefinitionMap, this.dataDefinitionSet);
		}
		if (null != this.dataSourceSet) {
			makeMap(this.dataSourceMap, this.dataSourceSet);
		}
	}

	private <C extends SaulHasName> void makeMap(final Map<String, C> inMap, final Set<C> inSet) {
		inMap.putAll(inSet.stream().collect(Collectors.toMap(C::getName, d -> d)));
	}

	public SaulDataDefinition getDataDefinition(final String inName) {
		return get(inName, this.dataDefinitionMap);
	}

	public SaulDataSource getDataSource(final String inName) {
		return get(inName, this.dataSourceMap);
	}

	private <C extends SaulHasName> C get(final String inName, final Map<String, C> inMap) {
		final C entity = inMap.get(inName);
		if (null == entity) {
			String simpleName = "";
			if (0 < inMap.size()) {
			   final C value = Lists.newArrayList(inMap.values()).get(0);
				simpleName = value.getClass().getName();
			} else {
				simpleName = "Elements, as they are empty ";
			}
			throw new IllegalArgumentException(String.format("No such %s '%s'", simpleName, inName));
		}
		return entity;
	}
}
