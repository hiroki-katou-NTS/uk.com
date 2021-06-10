package nts.uk.ctx.at.record.dom.stamp.application;

/**
 * 
 * @author chungnt
 *
 */

public class SettingsUsingEmbossingHelper {
	
	public static SettingsUsingEmbossing getSettingsUsingEmbossingDefault() {
		return new SettingsUsingEmbossing("000-0000000000001",
				true, false, false, false, false, false, false);
	}
	
	public static SettingsUsingEmbossing getSettingsUsingEmbossingFingerAuthc() {
		return new SettingsUsingEmbossing("000-0000000000001",
				false, true, false, false, false, false, false);
	}
	
	public static SettingsUsingEmbossing getSettingsUsingEmbossingIcCard() {
		return new SettingsUsingEmbossing("000-0000000000001",
				false, false, true, false, false, false, false);
	}
	
	public static SettingsUsingEmbossing getSettingsUsingEmbossingIndivition() {
		return new SettingsUsingEmbossing("000-0000000000001",
				false, false, false, true, false, false, false);
	}
	
	public static SettingsUsingEmbossing getSettingsUsingEmbossingPortal() {
		return new SettingsUsingEmbossing("000-0000000000001",
				false, true, false, false, true, false, false);
	}
	
	public static SettingsUsingEmbossing getSettingsUsingEmbossingSmartPhone() {
		return new SettingsUsingEmbossing("000-0000000000001",
				false, false, false, false, false, true, false);
	}
	
	public static SettingsUsingEmbossing getSettingsUsingEmbossingRicohStamp() {
		return new SettingsUsingEmbossing("000-0000000000001",
				false, false, false, false, false, false, true);
	}
}
