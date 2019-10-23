package nts.uk.ctx.pereg.app.find.person.setting.init.item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.dom.person.setting.init.item.PerInfoInitValueSetItemDetail;
import nts.uk.shr.pereg.app.ComboBoxObject;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PerInfoInitValueSettingItemDto {
	private String perInfoItemDefId;
	private String settingId;
	private String perInfoCtgId;
	private String itemName;
	private int itemType;
	private int dataType;
	private int isRequired;
	private int refMethodType;
	private Integer saveDataType;
	private String stringValue;
	private BigDecimal intValue;
	private GeneralDate dateValue;
	private String itemCode;
	private String ctgCode;
	private String constraint;
	private Integer numberDecimalPart;
	private Integer numberIntegerPart;
	private Integer numberItemAmount;
	private Integer numberItemMinus;
	private Integer timeItemMin;
	private Integer timeItemMax;
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
	public static PerInfoInitValueSettingItemDto fromDomain(PerInfoInitValueSetItemDetail domain) {
		return new PerInfoInitValueSettingItemDto(domain.getPerInfoItemDefId(),
				domain.getSettingId(),
				domain.getPerInfoCtgId(),
				domain.getItemName(),
				domain.getItemType().intValue(),
				domain.getDataType().intValue(),
				domain.getIsRequired(),
				domain.getRefMethodType(), 
				domain.getSaveDataType(),
				domain.getStringValue(), 
				domain.getIntValue(),
                domain.getDateValue(), 
                domain.getItemCode(),
                domain.getCtgCode(),
                domain.getConstraint(),
                domain.getNumberDecimalPart(),
                domain.getNumberIntegerPart(),
                domain.getNumberItemAmount(),
                domain.getNumberItemMinus(),
                domain.getTimeItemMin(),
                domain.getTimeItemMax(),
                domain.getSelectionItemId(),
                domain.getSelectionItemRefType(),
                new ArrayList<>(),
                domain.getDateType(),
                domain.getTimepointItemMin(),
				domain.getTimepointItemMax(),
				domain.getNumericItemMin(),
				domain.getNumericItemMax(),
				domain.getStringItemType(),
                domain.getStringItemLength(),
                domain.getStringItemDataType(),
                domain.isFixedItem(), false, true);

	}

}
