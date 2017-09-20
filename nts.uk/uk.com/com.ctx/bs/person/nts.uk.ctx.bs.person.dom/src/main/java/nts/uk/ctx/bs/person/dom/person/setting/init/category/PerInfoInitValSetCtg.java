package nts.uk.ctx.bs.person.dom.person.setting.init.category;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
public class PerInfoInitValSetCtg extends AggregateRoot {

	// 個人情報初期値設定ID
	private String initValueSettingId;

	// 個人情報カテゴリID
	private String initValueSettingCtgId;

	/**
	 * @Contructors PerInfoInitValSetCtg
	 * @param initValueSettingId
	 * @param initValueSettingCtgId
	 */
	public PerInfoInitValSetCtg(String initValueSettingId, String initValueSettingCtgId) {
		super();
		this.initValueSettingId = initValueSettingId;
		this.initValueSettingCtgId = initValueSettingCtgId;
	}

	/**
	 * createFromJavaType
	 * @param initValueSettingId
	 * @param initValueSettingCtgId
	 * @return PerInfoInitValSetCtg
	 */
	public static PerInfoInitValSetCtg createFromJavaType(String initValueSettingId, String initValueSettingCtgId) {
		return new PerInfoInitValSetCtg(initValueSettingId, initValueSettingCtgId);
	}

}
