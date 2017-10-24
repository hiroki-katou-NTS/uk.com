package nts.uk.ctx.bs.person.dom.person.setting.init.item;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.info.item.IsRequired;

/**
 * PerInfoInitValueSetItem
 * 
 * @author lanlt
 *
 */
@NoArgsConstructor
@Getter
@Setter
public class PerInfoInitValueSetItem extends AggregateRoot {

	// 個人情報項目定義ID
	private String perInfoItemDefId;

	private String settingId;

	// 個人情報カテゴリID
	private String perInfoCtgId;

	private String itemName;

	private IsRequired isRequired;

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
	 * 
	 * @param perInfoItemDefId
	 * @param settingId
	 * @param perInfoCtgId
	 * @param refMethodType
	 * @param saveDataType
	 * @param stringValue
	 * @param intValue
	 * @param dateValue
	 */

	public PerInfoInitValueSetItem(String perInfoItemDefId, String settingId, String perInfoCtgId,
			ReferenceMethodType refMethodType, SaveDataType saveDataType, StringValue stringValue, IntValue intValue,
			GeneralDate dateValue) {
		super();
		this.perInfoCtgId = perInfoCtgId;
		this.perInfoItemDefId = perInfoItemDefId;
		this.settingId = settingId;
		this.refMethodType = refMethodType;
		this.saveDataType = saveDataType;
		this.stringValue = stringValue;
		this.intValue = intValue;
		this.dateValue = dateValue;
	}

	public PerInfoInitValueSetItem(String perInfoItemDefId, String settingId, String perInfoCtgId, String itemName,
			IsRequired isRequired, ReferenceMethodType refMethodType, SaveDataType saveDataType,
			StringValue stringValue, IntValue intValue, GeneralDate dateValue) {
		super();
		this.perInfoCtgId = perInfoCtgId;
		this.perInfoItemDefId = perInfoItemDefId;
		this.settingId = settingId;
		this.itemName = itemName;
		this.isRequired = isRequired;
		this.refMethodType = refMethodType;
		this.saveDataType = saveDataType;
		this.stringValue = stringValue;
		this.intValue = intValue;
		this.dateValue = dateValue;
	}

	/**
	 * Constructor as domain
	 * 
	 * @param perInfoItemDefId
	 * @param settingId
	 * @param perInfoCtgId
	 * @param refMethodType
	 * @param saveDataType
	 * @param stringValue
	 * @param intValue
	 * @param dateValue
	 * @return
	 */
	public static PerInfoInitValueSetItem createFromJavaType(String perInfoItemDefId, String settingId,
			String perInfoCtgId, int refMethodType, int saveDataType, String stringValue, int intValue,
			String dateValue) {
		return new PerInfoInitValueSetItem(perInfoItemDefId, settingId, perInfoCtgId,
				EnumAdaptor.valueOf(refMethodType, ReferenceMethodType.class),
				EnumAdaptor.valueOf(saveDataType, SaveDataType.class), new StringValue(stringValue),
				new IntValue(new BigDecimal(intValue)), GeneralDate.fromString(dateValue, "yyyy-mm-dd"));
	}

	/**
	 * Constructor for diplay at screen A
	 * 
	 * @param perInfoItemDefId
	 * @param settingId
	 * @param perInfoCtgId
	 * @param itemName
	 * @param isRequired
	 * @param isFixed
	 * @param refMethodType
	 * @param saveDataType
	 * @param stringValue
	 * @param intValue
	 * @param dateValue
	 * @return
	 */
	public static PerInfoInitValueSetItem createFromJavaType(String perInfoItemDefId, String settingId,
			String perInfoCtgId, String itemName, int isRequired, int isFixed, int refMethodType, int saveDataType,
			String stringValue, int intValue, String dateValue) {
		return new PerInfoInitValueSetItem(perInfoItemDefId, settingId, perInfoCtgId, itemName,
				EnumAdaptor.valueOf(isRequired, IsRequired.class),
				EnumAdaptor.valueOf(refMethodType, ReferenceMethodType.class),
				EnumAdaptor.valueOf(saveDataType, SaveDataType.class), new StringValue(stringValue),
				new IntValue(new BigDecimal(intValue)), GeneralDate.fromString(dateValue, "yyyy-mm-dd"));
	}

	public void updateInitSetId(String settingId) {
		this.settingId = settingId;
	}
}
