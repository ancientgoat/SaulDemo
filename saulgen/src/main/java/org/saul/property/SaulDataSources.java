package org.saul.property;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 *
 */
//@ConfigurationProperties(locations = "classpath:mail.properties", prefix = "datasource")
@Component
@ConfigurationProperties(prefix = "DataSources")
public class SaulDataSources {

	private String elmo;

	@NotNull
	@Valid
	private List<SaulDataSource> saulDataSource = Lists.newArrayList(); //	Sets.newHashSet();

	public List<SaulDataSource> getSaulDataSource() {
		return saulDataSource;
	}

	public void setSaulDataSource(List<SaulDataSource> inSaulDataSource) {
		this.saulDataSource = inSaulDataSource;
	}

	public String getElmo() {
		return elmo;
	}

	public void setElmo(String elmo) {
		this.elmo = elmo;
	}

	public final String dumpToString() {
		final StringBuilder sb = new StringBuilder();
		for (final SaulDataSource ds : this.saulDataSource) {
			sb.append(ds.dumpToString());
		}
		return sb.toString();
	}
}
