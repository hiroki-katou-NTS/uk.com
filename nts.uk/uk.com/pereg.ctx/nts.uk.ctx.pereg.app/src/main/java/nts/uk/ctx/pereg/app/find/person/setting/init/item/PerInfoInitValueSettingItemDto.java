package nts.uk.ctx.pereg.app.find.person.setting.init.item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.dom.person.setting.init.item.PerInfoInitValueSetItem;
import nts.uk.shr.pereg.app.ComboBoxObject;

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
	
	// item thuoc kieu selection
	
	private String selectionItemId;
	
	private Integer selectionItemRefType;
	
	private List<ComboBoxObject> selection = new ArrayList<>();
	
	private Integer dateType;
	
	private Integer timepointItemMin;
	
	private Integer timepointItemMax;
	
	private BigDecimal numericItemMin;
	
	private BigDecimal numericItemMax;
	
	private Integer stringItemType;
	
	private Integer stringItemLength;
	
	private Integer stringItemDataType;
	
	private boolean isFixedItem;
	
	private boolean disableCombox;
	
	private boolean enableControl;

	public static PerInfoInitValueSettingItemDto fromDomain(PerInfoInitValueSetItem domain) {
		
		return new PerInfoInitValueSettingItemDto(domain.getPerInfoItemDefId(), 
				domain.getSettingId(),
				domain.getPerInfoCtgId(), domain.getItemName(), 
				domain.getItemType(), domain.getDataType(),
				domain.getIsRequired().value, 
				domain.getRefMethodType() == null ? 0 : domain.getRefMethodType().value,
				domain.getSaveDataType() == null ? null : domain.getSaveDataType().value,
				domain.getStringValue() == null ? null : domain.getStringValue().v(), 
				domain.getIntValue() == null ? null : domain.getIntValue().v(), domain.getDateValue(),
				domain.getItemCode(), 
				domain.getCtgCode(), 
				domain.getConstraint(), 
				domain.getNumberDecimalPart(),
				domain.getNumberIntegerPart(), domain.getTimeItemMin(), domain.getTimeItemMax(),
				domain.getSelectionItemId(), 
				domain.getSelectionItemRefType() == null? null: domain.getSelectionItemRefType() ,
			    new ArrayList<>(),
				domain.getDateType(), 
				domain.getTimepointItemMin(), domain.getTimepointItemMax(),
				domain.getNumericItemMin(), domain.getNumericItemMax(),
				domain.getStringItemType(), domain.getStringItemLength(),
				domain.getStringItemDataType(), domain.isFixedItem(),
				false, true);

	}

}
