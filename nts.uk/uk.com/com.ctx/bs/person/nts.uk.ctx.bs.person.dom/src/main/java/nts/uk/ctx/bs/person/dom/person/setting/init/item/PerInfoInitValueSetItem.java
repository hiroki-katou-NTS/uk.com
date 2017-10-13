package nts.uk.ctx.bs.person.dom.person.setting.init.item;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

/**
 * PerInfoInitValueSetItem
 * @author lanlt
 *
 */
@Getter
public class PerInfoInitValueSetItem extends AggregateRoot {

	// 個人情報項目定義ID
	private String perInfoItemDefId;

	private String settingId;
	
	// 個人情報カテゴリID
	private String perInfoCtgId;
	
	
	private String itemName;

	// 参照方法
	private ReferenceMethodType refMethodType;

	// 保存データ型
	private SaveDataType saveDataType;

	// 文字列
	private StringValue stringValue;

	// 数値
	private IntValue intValue;

	// 日付
	private GeneralDate dateValue;

	/**
	 * Constructor
	 * 
	 * @param perInfoItemDefId
	 * @param initValueSettingCtgId
	 * @param refMethodType
	 * @param saveDataType
	 * @param stringValue
	 * @param intValue
	 * @param dateValue
	 */
	public PerInfoInitValueSetItem(String perInfoItemDefId, String initValueSettingCtgId,
			ReferenceMethodType refMethodType, SaveDataType saveDataType, StringValue stringValue, IntValue intValue,
			GeneralDate dateValue) {
		super();
		this.perInfoItemDefId = perInfoItemDefId;
		this.settingId = initValueSettingCtgId;
		this.refMethodType = refMethodType;
		this.saveDataType = saveDataType;
		this.stringValue = stringValue;
		this.intValue = intValue;
		this.dateValue = dateValue;
	}

	public static PerInfoInitValueSetItem createFromJavaType(String perInfoItemDefId, String initValueSettingCtgId,
			int refMethodType, int saveDataType, String stringValue, int intValue, String dateValue) {
		return new PerInfoInitValueSetItem(perInfoItemDefId, initValueSettingCtgId,
				EnumAdaptor.valueOf(refMethodType, ReferenceMethodType.class),
				EnumAdaptor.valueOf(saveDataType, SaveDataType.class),
				new StringValue(stringValue),
				new IntValue(new BigDecimal(intValue)), GeneralDate.fromString(dateValue, "yyyy-mm-dd"));
	}

}
