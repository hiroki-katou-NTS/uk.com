package nts.uk.ctx.pereg.app.find.initsetting.item;

import java.math.BigDecimal;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.dom.person.additemdata.item.EmpInfoItemData;
import nts.uk.ctx.pereg.dom.person.info.dateitem.DateType;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;
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

	private ReferenceTypes selectionItemRefType;

	private String itemParentCd;

	private DateType dateType;

	private String SelectionItemRefCd;

	public SettingItemDto(String categoryCode, String itemDefId, String itemCode, String itemName, int isRequired,
			SaveDataDto saveData, DataTypeValue dataType, ReferenceTypes selectionItemRefType, String itemParentCd,
			DateType dateType, String SelectionItemRefCd) {
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
		this.SelectionItemRefCd = SelectionItemRefCd;
	}

	private static SaveDataDto createSaveDataDto(int saveDataValue, GeneralDate dateValue, BigDecimal intValue,
			String stringValue) {
		SaveDataDto resultDto = new SaveDataDto();

		SaveDataType saveDataType = EnumAdaptor.valueOf(saveDataValue, SaveDataType.class);

		switch (saveDataType) {
		case DATE:
			resultDto = new SaveDataDto(SaveDataType.DATE, dateValue);
			break;
		case NUMBERIC:
			resultDto = new SaveDataDto(SaveDataType.NUMBERIC, intValue);
			break;
		case STRING:
			resultDto = new SaveDataDto(SaveDataType.DATE, stringValue);
			break;
		}

		return resultDto;
	}
	
	private static SaveDataDto createSaveDataDto(Object value) {
		if (value == null ) {
			return new SaveDataDto(SaveDataType.STRING, null);
		}

		if (value.getClass().equals(Integer.class) || value.getClass().equals(BigDecimal.class)|| value.getClass().equals(Double.class)) {
			return new SaveDataDto(SaveDataType.NUMBERIC, value);
		}
		if (value.getClass().equals(String.class)) {
			return new SaveDataDto(SaveDataType.STRING, value);
		}

		if (value.getClass().equals(GeneralDate.class)) {
			return new SaveDataDto(SaveDataType.DATE, value);
		}
		return new SaveDataDto(SaveDataType.STRING, null);

	}
	
	public static SettingItemDto createFromJavaType(String categoryCode, String itemDefId, String itemCode,
			String itemName, int isRequired, int saveDataValue, GeneralDate dateValue, BigDecimal intValue,
			String stringValue, int dataType, int selectionItemRefType, String itemParentCd, int dateType,
			String SelectionItemRefCd) {
		SettingItemDto itemDto = new SettingItemDto(categoryCode, itemDefId, itemCode, itemName, isRequired,
				createSaveDataDto(saveDataValue, dateValue, intValue, stringValue),
				EnumAdaptor.valueOf(dataType, DataTypeValue.class),
				EnumAdaptor.valueOf(selectionItemRefType, ReferenceTypes.class), itemParentCd,
				EnumAdaptor.valueOf(dateType, DateType.class), SelectionItemRefCd);
		return itemDto;
	}
	
	public static SettingItemDto createFromJavaType1(String categoryCode, String itemDefId, String itemCode,
			String itemName, int isRequired, Object value, DataTypeValue dataType, ReferenceTypes selectionItemRefType,
			String itemParentCd, DateType dateType, String SelectionItemRefCd) {
		SettingItemDto itemDto = new SettingItemDto(categoryCode, itemDefId, itemCode, itemName, isRequired,
				createSaveDataDto(value), dataType, selectionItemRefType, itemParentCd, dateType, SelectionItemRefCd);
		return itemDto;
	}
	
	public static SettingItemDto fromInfoDataItem(EmpInfoItemData domain) {

		return SettingItemDto.createFromJavaType(domain.getPerInfoCtgCd(), domain.getPerInfoDefId(),
				domain.getItemCode().v(), domain.getItemName(), domain.getIsRequired().value,
				domain.getDataState().getDataStateType().value, domain.getDataState().getDateValue(),
				domain.getDataState().getNumberValue(), domain.getDataState().getStringValue(), domain.getDataType(),
				domain.getSelectionItemRefType(), domain.getItemParentCd(), domain.getDateType().value,
				domain.getSelectionItemRefCd());

	}

	public void setData(String value) {
		this.setSaveData(new SaveDataDto(SaveDataType.STRING, value));

	}
	
	public void setData(Object value) {
		
		if (value == null ) {
			this.setSaveData(new SaveDataDto(SaveDataType.STRING, ""));
			return;
		}
		
		if (value.getClass().equals(Integer.class)|| value.getClass().equals(BigDecimal.class)|| value.getClass().equals(Double.class)) {
			this.setSaveData(new SaveDataDto(SaveDataType.NUMBERIC, value));
		}
		if (value.getClass().equals(String.class)) {
			this.setSaveData(new SaveDataDto(SaveDataType.STRING, value));
		}

		if (value.getClass().equals(GeneralDate.class)) {
			this.setSaveData(new SaveDataDto(SaveDataType.DATE, value));
		}

	}
	
	

}
