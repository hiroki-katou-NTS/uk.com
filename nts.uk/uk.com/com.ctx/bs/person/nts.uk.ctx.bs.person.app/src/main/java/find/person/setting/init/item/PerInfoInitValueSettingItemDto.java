package find.person.setting.init.item;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.PerInfoInitValueSetItem;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PerInfoInitValueSettingItemDto {

	// 個人情報項目定義ID
	private String perInfoItemDefId;

	private String settingId;

	// 個人情報カテゴリID
	private String perInfoCtgId;

	private String itemName;

	private int itemType;

	private int dataType;

	private int isRequired;

	// 参照方法
	private int refMethodType;

	// 保存データ型
	private Integer saveDataType;

	// 文字列
	private String stringValue;

	// 数値
	private BigDecimal intValue;

	// 日付
	private GeneralDate dateValue;

	// itemCode
	private String itemCode;

	// categoryCode
	private String ctgCode;

	// constraint
	private String constraint;

	// thêm trường numberDecimalPart của bảng common
	private Integer numberDecimalPart;

	// thêm trường numberIntegerPart của bảng common
	private Integer numberIntegerPart;
	
	private Integer timeItemMin;
	
	private Integer timeItemMax;
	
	private String selectionItemId;
	
	private Integer dateType;
	
	private Integer timepointItemMin;
	
	private Integer timepointItemMax;

	public static PerInfoInitValueSettingItemDto fromDomain(PerInfoInitValueSetItem domain) {
		return new PerInfoInitValueSettingItemDto(domain.getPerInfoItemDefId(), domain.getSettingId(),
				domain.getPerInfoCtgId(), domain.getItemName(), domain.getItemType(), domain.getDataType(),
				domain.getIsRequired().value, domain.getRefMethodType().value, domain.getSaveDataType() == null ? null : domain.getSaveDataType().value,
				domain.getStringValue().v(), domain.getIntValue() == null ? null : domain.getIntValue().v(), domain.getDateValue(),
				domain.getItemCode(), domain.getCtgCode(), domain.getConstraint(), domain.getNumberDecimalPart(),
				domain.getNumberIntegerPart(), domain.getTimeItemMin(), domain.getTimeItemMax(),
				domain.getSelectionItemId(), domain.getDateType(), 
				domain.getTimepointItemMin(), domain.getTimepointItemMax());

	}

}
