package de.pennychecker.kata;

public class Country {
	private final String country;
	private final String isoCode;

	public Country(String country, String isoCode) {
		this.country = country;
		this.isoCode = isoCode;
	}

	public String getCountry() {
		return country;
	}

	public String getIsoCode() {
		return isoCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isoCode == null) ? 0 : isoCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (isoCode == null) {
			if (other.isoCode != null)
				return false;
		} else if (!isoCode.equals(other.isoCode))
			return false;
		return true;
	}

}
