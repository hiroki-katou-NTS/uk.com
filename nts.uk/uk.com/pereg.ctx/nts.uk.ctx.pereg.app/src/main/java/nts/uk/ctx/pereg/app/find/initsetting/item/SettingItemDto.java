package nts.uk.ctx.pereg.app.find.initsetting.item;

import java.math.BigDecimal;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.dom.person.additemdata.item.EmpInfoItemData;
import nts.uk.ctx.pereg.dom.person.info.dateitem.DateType;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
import nts.uk.ctx.pereg.dom.person.setting.init.item.SaveDataType;

/**
 * @author sonnlb
 *
 */
@Data
public class SettingItemDto {

	private String categoryCode;

	private String itemDefId;

	private String itemCode;

	private String itemName;

	private int isRequired;

	private SaveDataDto saveData;

	private DataTypeValue dataType;

	private BigDecimal selectionItemRefType;

	private String itemParentCd;

	private DateType dateType;

	public SettingItemDto(String categoryCode, String itemDefId, String itemCode, String itemName, int isRequired,
			SaveDataDto saveData, DataTypeValue dataType, BigDecimal selectionItemRefType, String itemParentCd,
			DateType dateType) {
		super();
		this.categoryCode = categoryCode;
		this.itemDefId = itemDefId;
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.isRequired = isRequired;
		this.saveData = saveData;
		this.dataType = dataType;
		this.selectionItemRefType = selectionItemRefType;
		this.itemParentCd = itemParentCd;
		this.dateType = dateType;
	}

	private static SaveDataDto createSaveDataDto(int saveDataValue, GeneralDate dateValue, BigDecimal intValue,
			String stringValue, DateType dateType) {
		SaveDataDto resultDto = new SaveDataDto();

		SaveDataType saveDataType = EnumAdaptor.valueOf(saveDataValue, SaveDataType.class);

		switch (saveDataType) {
		case DATE:
			resultDto.setValue(getDateString(dateType, dateValue));
			break;
		case NUMBERIC:
			resultDto.setValue(intValue.toString());
			break;
		case STRING:
			resultDto.setValue(stringValue);
			break;
		}

		return resultDto;
	}

	public static SettingItemDto createFromJavaType(String categoryCode, String itemDefId, String itemCode,
			String itemName, int isRequired, int saveDataValue, GeneralDate dateValue, BigDecimal intValue,
			String stringValue, int dataType, BigDecimal selectionItemRefType, String itemParentCd, int dateType) {
		SettingItemDto itemDto = new SettingItemDto(categoryCode, itemDefId, itemCode, itemName, isRequired,
				createSaveDataDto(saveDataValue, dateValue, intValue, stringValue,
						EnumAdaptor.valueOf(dateType, DateType.class)),
				EnumAdaptor.valueOf(dataType, DataTypeValue.class), selectionItemRefType, itemParentCd,
				EnumAdaptor.valueOf(dateType, DateType.class));
		return itemDto;
	}

	public static SettingItemDto fromInfoDataItem(EmpInfoItemData domain) {

		return SettingItemDto.createFromJavaType(domain.getPerInfoCtgCd(), domain.getPerInfoDefId(),
				domain.getItemCode().v(), domain.getItemName(), domain.getIsRequired().value,
				domain.getDataState().getDataStateType().value, domain.getDataState().getDateValue(),
				domain.getDataState().getNumberValue(), domain.getDataState().getStringValue(), domain.getDataType(),
				domain.getSelectionItemRefType(), domain.getItemParentCd(), domain.getDateType().value);

	}

	public void setData(String value) {
		this.setSaveData(new SaveDataDto(SaveDataType.STRING, value));

	}

	public void setData(GeneralDate value) {
		String stringValue = getDateString(this.dateType, value);
		this.setSaveData(new SaveDataDto(SaveDataType.DATE, stringValue));

	}

	private static String getDateString(DateType dateType, GeneralDate value) {
		String formatString = "yyyy/MM/dd";
		switch (dateType) {
		case YEARMONTHDAY:
			formatString = "yyyy/MM/dd";
			break;
		case YEARMONTH:
			formatString = "yyyy/MM";
			break;
		case YEAR:
			formatString = "yyyy";
			break;
		}
		return value.toString(formatString);
	}

}
