package org.saul.datadefinition.model.datadefinition;

/**
 *
 */
public class SaulIdentity {

	private String name;
	private String source;
	private String shortName;

	public String getName() {
		return this.name;
	}

	public String getSource() {
		return source;
	}

	public String getShortName() {
		return shortName;
	}

	/**
	 *
	 */
	public static final class Builder {

		private SaulIdentity identity = new SaulIdentity();

		public Builder (){}

		public Builder (final SaulIdentity inIdentity){
			this.identity.name = inIdentity.name;
			this.identity.source = inIdentity.source;
			this.identity.shortName = inIdentity.shortName;
		}

		public Builder setName(final String inName) {
			this.identity.name = inName;
			return this;
		}

		public Builder setSource(final String inSource) {
			this.identity.source = inSource;
			return this;
		}

		public Builder setShortName(final String inShortName) {
			this.identity.shortName = inShortName;
			return this;
		}

		public SaulIdentity build() {
			return this.identity;
		}
	}
}
