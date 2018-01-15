package nts.uk.ctx.pereg.dom.person.setting.init;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.text.IdentifierUtil;

@Getter
public class PerInfoInitValueSetting extends AggregateRoot {

	// 個人情報初期値設定ID
	private String initValueSettingId;
	
	private String companyId;
	
	//個人情報初期値設定コード
	private ValueSettingCode settingCode;
	
	//個人情報初期値設定名称
	private ValueSettingName settingName;

	/**
	 * @constructor InitValueSetting
	 * @param initValueSettingId
	 * @param companyId
	 * @param settingCode
	 * @param settingName
	 */
	public PerInfoInitValueSetting(String companyId, ValueSettingCode settingCode,
			ValueSettingName settingName) {
		super();
		this.initValueSettingId = IdentifierUtil.randomUniqueId();
		this.companyId = companyId;
		this.settingCode = settingCode;
		this.settingName = settingName;
	}
	
	/**
	 * @constructor InitValueSetting
	 * @param initValueSettingId
	 * @param companyId
	 * @param settingCode
	 * @param settingName
	 */
	public PerInfoInitValueSetting(String initValueSettingId, String companyId, ValueSettingCode settingCode,
			ValueSettingName settingName) {
		super();
		this.initValueSettingId = initValueSettingId;
		this.companyId = companyId;
		this.settingCode = settingCode;
		this.settingName = settingName;
	}

	public static PerInfoInitValueSetting createFromJavaType(String initValueSettingId, String companyId,
			String settingCode, String settingName) {
		return new PerInfoInitValueSetting(initValueSettingId, companyId,
					new ValueSettingCode(settingCode),
			    	new ValueSettingName(settingName));
	}

}
