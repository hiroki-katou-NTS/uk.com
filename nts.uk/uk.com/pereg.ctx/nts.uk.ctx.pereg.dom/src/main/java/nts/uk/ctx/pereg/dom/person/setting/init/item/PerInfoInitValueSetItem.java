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
import nts.uk.ctx.pereg.dom.person.info.item.IsRequired;

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
	
	private String itemParentCd;

	// thêm dataType của item defined
	private Integer dataType;

	// thêm itemType của item defined
	private Integer itemType;

	// thêm itemCode của item defined
	private String itemCode;

	// thêm categoryCode của CategoryInfo
	private String ctgCode;

	// thêm constraint để validate cho chính mình
	private String constraint;

	// thêm trường numberDecimalPart của bảng common
	private Integer numberDecimalPart;

	// thêm trường numberIntegerPart của bảng common
	private Integer numberIntegerPart;

	private Integer timeItemMin;

	private Integer timeItemMax;

	// trường này dùng để kết nối với bảng selectionItem
	private String selectionItemId;
	
	private Integer selectionItemRefType;
	
	private String selectionItemRefCd;
	
	
	private Map<Integer, Map<Integer, String>> selectionLst;

	// trường này để xác định xem là trường date thuộc
	// kiểu ngày tháng năm, năm tháng or năm
	private Integer dateType;
	
	// hai trường này dùng để xác định giá trị của item nắm
	// trong khoảng nào khi mà item là kiểu timepoint
	private Integer timepointItemMin;
	private Integer timepointItemMax;
	
	// hai trường này dùng để xác định giá trị của item nắm
	// trong khoảng nào khi mà item là kiểu numberic
	private BigDecimal numericItemMin;
	private BigDecimal numericItemMax;
	
	// hai trường này dùng để  validate item thuộc kiểu string
	
	private Integer stringItemType;
	
	private Integer stringItemLength;
	
	private Integer stringItemDataType;
	
	private boolean isFixedItem;


	/**
	 * constructor PerInfoInitValueSetItem
	 * 
	 * @param perInfoItemDefId
	 * @param settingId
	 * @param perInfoCtgId
	 * @param itemName
	 * @param isRequired
	 * @param refMethodType
	 * @param saveDataType
	 * @param stringValue
	 * @param intValue
	 * @param dateValue
	 * @param dataType
	 * @param itemType
	 * @param itemCode
	 * @param ctgCode
	 * @param constraint
	 * @param numberDecimalPart
	 * @param numberIntegerPart
	 */
	public PerInfoInitValueSetItem(String perInfoItemDefId, String settingId, String perInfoCtgId, String itemName,
			IsRequired isRequired, ReferenceMethodType refMethodType, SaveDataType saveDataType,
			StringValue stringValue, IntValue intValue, GeneralDate dateValue, Integer dataType, Integer itemType,
			String itemCode, String ctgCode, String constraint, Integer numberDecimalPart, Integer numberIntegerPart,
			Integer timeItemMin, Integer timeItemMax) {
		super();
		this.perInfoItemDefId = perInfoItemDefId;
		this.settingId = settingId;
		this.perInfoCtgId = perInfoCtgId;
		this.itemName = itemName;
		this.isRequired = isRequired;
		this.refMethodType = refMethodType;
		this.saveDataType = saveDataType;
		this.stringValue = stringValue;
		this.intValue = intValue;
		this.dateValue = dateValue;
		this.dataType = dataType;
		this.itemType = itemType;
		this.itemCode = itemCode;
		this.ctgCode = ctgCode;
		this.constraint = constraint;
		this.numberDecimalPart = numberDecimalPart;
		this.numberIntegerPart = numberIntegerPart;
		this.timeItemMax = timeItemMax;
		this.timeItemMin = timeItemMin;
	}

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

	/**
	 * constructor for loading item of cps009
	 * 
	 * @param perInfoItemDefId
	 * @param settingId
	 * @param perInfoCtgId
	 * @param itemName
	 * @param isRequired
	 * @param refMethodType
	 * @param saveDataType
	 * @param stringValue
	 * @param intValue
	 * @param dateValue
	 * @param dataType
	 * @param itemType
	 * @param itemCode
	 * @param ctgCode
	 */
	public PerInfoInitValueSetItem(String perInfoItemDefId, String settingId, String perInfoCtgId, String itemName,
			IsRequired isRequired, ReferenceMethodType refMethodType, SaveDataType saveDataType,
			StringValue stringValue, IntValue intValue, GeneralDate dateValue, Integer dataType, Integer itemType,
			String itemCode, String ctgCode) {
		super();
		this.perInfoItemDefId = perInfoItemDefId;
		this.settingId = settingId;
		this.perInfoCtgId = perInfoCtgId;
		this.itemName = itemName;
		this.isRequired = isRequired;
		this.refMethodType = refMethodType;
		this.saveDataType = saveDataType;
		this.stringValue = stringValue;
		this.intValue = intValue;
		this.dateValue = dateValue;
		this.dataType = dataType;
		this.itemType = itemType;
		this.itemCode = itemCode;
		this.ctgCode = ctgCode;
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

	public PerInfoInitValueSetItem(String perInfoItemDefId, String settingId, String perInfoCtgId,
			ReferenceMethodType refMethodType) {
		super();
		this.perInfoCtgId = perInfoCtgId;
		this.perInfoItemDefId = perInfoItemDefId;
		this.settingId = settingId;
		this.refMethodType = refMethodType;
	}

	public PerInfoInitValueSetItem(String perInfoItemDefId, String settingId, String perInfoCtgId,
			ReferenceMethodType refMethodType, SaveDataType saveDataType, StringValue stringValue) {
		super();
		this.perInfoCtgId = perInfoCtgId;
		this.perInfoItemDefId = perInfoItemDefId;
		this.settingId = settingId;
		this.refMethodType = refMethodType;
		this.saveDataType = saveDataType;
		this.stringValue = stringValue;
	}

	public PerInfoInitValueSetItem(String perInfoItemDefId, String settingId, String perInfoCtgId,
			ReferenceMethodType refMethodType, SaveDataType saveDataType, IntValue intValue) {
		super();
		this.perInfoCtgId = perInfoCtgId;
		this.perInfoItemDefId = perInfoItemDefId;
		this.settingId = settingId;
		this.refMethodType = refMethodType;
		this.saveDataType = saveDataType;
		this.intValue = intValue;
	}

	public PerInfoInitValueSetItem(String perInfoItemDefId, String settingId, String perInfoCtgId,
			ReferenceMethodType refMethodType, SaveDataType saveDataType, GeneralDate date) {
		super();
		this.perInfoCtgId = perInfoCtgId;
		this.perInfoItemDefId = perInfoItemDefId;
		this.settingId = settingId;
		this.refMethodType = refMethodType;
		this.saveDataType = saveDataType;
		this.dateValue = date;
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

	public static PerInfoInitValueSetItem convertFromJavaType(String perInfoItemDefId, String settingId,
			String perInfoCtgId, int refMethodType, int saveDataType, String stringValue) {

		return new PerInfoInitValueSetItem(perInfoItemDefId, settingId, perInfoCtgId,
				EnumAdaptor.valueOf(refMethodType, ReferenceMethodType.class),
				EnumAdaptor.valueOf(saveDataType, SaveDataType.class), new StringValue(stringValue));
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
	public static PerInfoInitValueSetItem convertFromJavaType(String perInfoItemDefId, String settingId,
			String perInfoCtgId, int refMethodType, int saveDataType, BigDecimal intValue) {

		return new PerInfoInitValueSetItem(perInfoItemDefId, settingId, perInfoCtgId,
				EnumAdaptor.valueOf(refMethodType, ReferenceMethodType.class),
				EnumAdaptor.valueOf(saveDataType, SaveDataType.class), new IntValue(intValue));
	}

	public static PerInfoInitValueSetItem convertFromJavaType(String perInfoItemDefId, String settingId,
			String perInfoCtgId, int refMethodType, int saveDataType, GeneralDate date) {

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
