package nts.uk.ctx.pereg.dom.person.setting.init.category;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
/**
 * The AggregateRoot AggregateRoot
 * @author lanlt
 *
 */
@Getter
public class PerInfoInitValSetCtg  extends AggregateRoot {

	// 個人情報初期値設定ID
	private String initValueSettingId;

	// 個人情報カテゴリID
	private String perInfoCtgId;

	/**
	 * @Contructors PerInfoInitValSetCtg
	 * @param settingId
	 * @param perInfoCtgId
	 */
	public PerInfoInitValSetCtg(String settingId, String perInfoCtgId) {
		super();
		this.initValueSettingId = settingId;
		this.perInfoCtgId = perInfoCtgId;
	}

	/**
	 * createFromJavaType
	 * @param settingId
	 * @param perInfoCtgId
	 * @return PerInfoInitValSetCtg
	 */
	public static PerInfoInitValSetCtg createFromJavaType(String settingId, String perInfoCtgId) {
		return new PerInfoInitValSetCtg(settingId, perInfoCtgId);
	}
	
	public void updateInitSetId(String initValueSettingId){
		this.initValueSettingId = initValueSettingId;
	}
}
