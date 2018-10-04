package nts.uk.ctx.pereg.dom.person.setting.init.item;

import java.math.BigDecimal;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.dom.person.info.category.PersonCategoryItemData;
import nts.uk.ctx.pereg.dom.person.info.category.ReferenceStateData;

/**
 * The AggregateRoot 個人情報初期値設定項目 PerInfoInitValueSetItem
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
	 * constructor agrerate root of 個人情報初期値設定項目PerInfoInitValSettingItem
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

	public PerInfoInitValueSetItem(String perInfoItemDefId, String settingId,String perInfoCtgId, ReferenceMethodType refMethodType,
			SaveDataType saveDataType, StringValue stringValue, IntValue intValue, GeneralDate dateValue) {
		super();
		this.perInfoItemDefId = perInfoItemDefId;
		this.settingId = settingId;
		this.perInfoCtgId = perInfoCtgId;
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
	public static PerInfoInitValueSetItem createFromJavaType(String perInfoItemDefId, String settingId, String perInfoCtgId,
			int refMethodType, int saveDataType, String stringValue, int intValue, String dateValue) {
		return new PerInfoInitValueSetItem(perInfoItemDefId, settingId,perInfoCtgId,
				EnumAdaptor.valueOf(refMethodType, ReferenceMethodType.class),
				EnumAdaptor.valueOf(saveDataType, SaveDataType.class), new StringValue(stringValue),
				new IntValue(new BigDecimal(intValue)), GeneralDate.fromString(dateValue, "yyyy-mm-dd"));
	}

	public PerInfoInitValueSetItem(String perInfoItemDefId, String settingId, String perInfoCtgId, ReferenceMethodType refMethodType) {
		super();
		this.settingId = settingId;
		this.perInfoCtgId = perInfoCtgId;
		this.perInfoItemDefId = perInfoItemDefId;
		this.refMethodType = refMethodType;
		this.dateValue = null;
		this.intValue = null;
		this.stringValue = null;
	}

	public PerInfoInitValueSetItem(String perInfoItemDefId, String settingId, String perInfoCtgId,
			ReferenceMethodType refMethodType, SaveDataType saveDataType, StringValue stringValue) {
		super();
		this.perInfoItemDefId = perInfoItemDefId;
		this.settingId = settingId;
		this.perInfoCtgId = perInfoCtgId;
		this.refMethodType = refMethodType;
		this.saveDataType = saveDataType;
		this.stringValue = stringValue;
	}

	public PerInfoInitValueSetItem(String perInfoItemDefId, String settingId, String perInfoCtgId,
			ReferenceMethodType refMethodType, SaveDataType saveDataType, IntValue intValue) {
		super();
		this.perInfoItemDefId = perInfoItemDefId;
		this.settingId = settingId;
		this.perInfoCtgId = perInfoCtgId;
		this.refMethodType = refMethodType;
		this.saveDataType = saveDataType;
		this.intValue = intValue;
	}

	public PerInfoInitValueSetItem(String perInfoItemDefId, String settingId,  String perInfoCtgId,
			ReferenceMethodType refMethodType, SaveDataType saveDataType, GeneralDate date) {
		super();
		this.perInfoItemDefId = perInfoItemDefId;
		this.settingId = settingId;
		this.perInfoCtgId = perInfoCtgId;
		this.refMethodType = refMethodType;
		this.saveDataType = saveDataType;
		this.dateValue = date;
	}

	/**
	 * Constructor for diplay at screen A
	 * 
	 * @param perInfoItemDefId
	 * @param settingId
	 * @param refMethodType
	 * @param saveDataType
	 * @param stringValue
	 * @param intValue
	 * @param dateValue
	 * @return
	 */
	public static PerInfoInitValueSetItem createFromJavaType(String perInfoItemDefId, String settingId, String perInfoCtgId,
			 int refMethodType, int saveDataType,String dateValue) {
		return new PerInfoInitValueSetItem(perInfoItemDefId, settingId,  perInfoCtgId,
				EnumAdaptor.valueOf(refMethodType, ReferenceMethodType.class),
				EnumAdaptor.valueOf(saveDataType, SaveDataType.class), GeneralDate.fromString(dateValue, "yyyy-mm-dd"));
	}

	/**
	 * Hàm này dùng để lưu các kiểu referenceType không thuộc kiểu fixedValue
	 * PerInfoInitValueSetItem
	 * 
	 * @param perInfoItemDefId
	 * @param settingId
	 * @param perInfoCtgId
	 * @param refMethodType
	 * @return
	 */
	public static PerInfoInitValueSetItem convertFromJavaType(String perInfoItemDefId, String settingId, 
			String perInfoCtgId, int refMethodType) {

		return new PerInfoInitValueSetItem(perInfoItemDefId, settingId, perInfoCtgId,
				EnumAdaptor.valueOf(refMethodType, ReferenceMethodType.class));
	}

	/**
	 * convert to String type
	 * 
	 * @param perInfoItemDefId
	 * @param settingId
	 * @param perInfoCtgId
	 * @param refMethodType
	 * @param saveDataType
	 * @param stringValue
	 * @return
	 */

	public static PerInfoInitValueSetItem convertFromJavaType(String perInfoItemDefId, String settingId, String perInfoCtgId,
			int refMethodType, int saveDataType, String stringVal) {

		return new PerInfoInitValueSetItem(perInfoItemDefId, settingId, perInfoCtgId,
				EnumAdaptor.valueOf(refMethodType, ReferenceMethodType.class),
				EnumAdaptor.valueOf(saveDataType, SaveDataType.class), 
				new StringValue(stringVal));
	}

	/**
	 * convert to Int Type
	 * 
	 * @param perInfoItemDefId
	 * @param settingId
	 * @param perInfoCtgId
	 * @param refMethodType
	 * @param saveDataType
	 * @param intValue
	 * @return
	 */
	public static PerInfoInitValueSetItem convertFromJavaType(String perInfoItemDefId, String settingId, String perInfoCtgId,
			int refMethodType, int saveDataType, BigDecimal intValue) {

		return new PerInfoInitValueSetItem(perInfoItemDefId, settingId, perInfoCtgId,
				EnumAdaptor.valueOf(refMethodType, ReferenceMethodType.class),
				EnumAdaptor.valueOf(saveDataType, SaveDataType.class),
				new IntValue(intValue));
	}
	
	/**
	 * convert to Int Type
	 * 
	 * @param perInfoItemDefId
	 * @param settingId
	 * @param perInfoCtgId
	 * @param refMethodType
	 * @param saveDataType
	 * @param intValue
	 * @return
	 */
	public static PerInfoInitValueSetItem convertFromJavaType(String perInfoItemDefId, String settingId, String perInfoCtgId,
			int refMethodType, int saveDataType, GeneralDate date) {

		return new PerInfoInitValueSetItem(perInfoItemDefId, settingId, perInfoCtgId,
				EnumAdaptor.valueOf(refMethodType, ReferenceMethodType.class),
				EnumAdaptor.valueOf(saveDataType, SaveDataType.class), date);
	}

	/**
	 * update settingId
	 * 
	 * @param settingId
	 */

	public void updateInitSetId(String settingId) {
		this.settingId = settingId;
	}

	public static String processs(String categoryCode, String itemCode) {
		PersonCategoryItemData item = new PersonCategoryItemData();
		for (Map.Entry<String, Map<String, ReferenceStateData>> ctg : item.CategoryMap.entrySet()) {
			if (ctg.getKey() == categoryCode) {
				Map<String, ReferenceStateData> itemChild = (Map<String, ReferenceStateData>) ctg.getValue();
				for (Map.Entry<String, ReferenceStateData> itemSub : itemChild.entrySet()) {
					if (itemSub.getKey().equals(itemCode)) {
						ReferenceStateData data = (ReferenceStateData) itemSub.getValue();
						return data.getConstraint();

					}
				}

			}

		}
		return "";
	}

}
