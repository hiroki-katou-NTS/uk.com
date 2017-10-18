package nts.uk.ctx.bs.person.dom.person.setting.init.category;

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
	 * @param initValueSettingId
	 * @param perInfoCtgId
	 */
	public PerInfoInitValSetCtg(String initValueSettingId, String perInfoCtgId) {
		super();
		this.initValueSettingId = initValueSettingId;
		this.perInfoCtgId = perInfoCtgId;
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
