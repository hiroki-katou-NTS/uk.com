package nts.uk.cnv.core.dom.conversiontable.pattern;

public enum ConversionFileType {
	/** 顔写真 **/
	ProfilePhoto("PROFILE_PHOTO"),
	/** 地図 **/
	Map("MAP"),
	/** 電子書類 **/
	PersonDoc("DOCUMENT");

	private final String id;

	private ConversionFileType(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public static ConversionFileType parse(String type) {
		for (ConversionFileType v : values()) {
			if(v.getId().equals(type)) {
				return v;
			}
		}
		throw new IllegalArgumentException("undefined : " + type);
	}
}
