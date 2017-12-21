package nts.uk.ctx.pereg.app.find.initsetting.item;

import java.math.BigDecimal;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.dom.person.additemdata.item.EmpInfoItemData;
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

	public SettingItemDto(String categoryCode, String itemDefId, String itemCode, String itemName, int isRequired,
			SaveDataDto saveData, DataTypeValue dataType, BigDecimal selectionItemRefType, String itemParentCd) {
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
	}

	private static SaveDataDto createSaveDataDto(int saveDataValue, GeneralDate dateValue, BigDecimal intValue,
			String stringValue) {
		SaveDataDto resultDto = new SaveDataDto();

		SaveDataType saveDataType = EnumAdaptor.valueOf(saveDataValue, SaveDataType.class);

		switch (saveDataType) {
		case DATE:
			resultDto = SaveDataDto.createDataDto(dateValue);
			break;
		case NUMBERIC:
			resultDto = SaveDataDto.createDataDto(intValue.intValueExact());
			break;
		case STRING:
			resultDto = SaveDataDto.createDataDto(stringValue);
			break;
		}

		return resultDto;
	}

	public static SaveDataDto createSaveDataDto(int saveDataValue, String value) {
		SaveDataDto resultDto = new SaveDataDto();

		SaveDataType saveDataType = EnumAdaptor.valueOf(saveDataValue, SaveDataType.class);

		switch (saveDataType) {
		case DATE:
			resultDto = SaveDataDto.createDataDto(value != "" && value != null
					? GeneralDate.fromString(value.toString(), "yyyy/MM/dd") : GeneralDate.min());
			break;
		case NUMBERIC:
			resultDto = SaveDataDto
					.createDataDto(value != "" && value != null ? Integer.parseInt(value.toString()) : 0);
			break;
		case STRING:
			resultDto = SaveDataDto.createDataDto(value != null ? value.toString() : "");
			break;
		}

		return resultDto;
	}

	public static SettingItemDto createFromJavaType(String categoryCode, String itemDefId, String itemCode,
			String itemName, int isRequired, int saveDataValue, GeneralDate dateValue, BigDecimal intValue,
			String stringValue, int dataType, BigDecimal selectionItemRefType, String itemParentCd) {
		SettingItemDto itemDto = new SettingItemDto(categoryCode, itemDefId, itemCode, itemName, isRequired,
				createSaveDataDto(saveDataValue, dateValue, intValue, stringValue),
				EnumAdaptor.valueOf(dataType, DataTypeValue.class), selectionItemRefType, itemParentCd);
		return itemDto;
	}

	public void setData(String value) {
		this.saveData = StringDataDto.createDataDto(value);
	}

	public void setData(int value) {
		this.saveData = NumberDataDto.createDataDto(value);
	}

	public void setData(GeneralDate value) {
		this.saveData = DateDataDto.createDataDto(value);
	}

	public static SettingItemDto fromInfoDataItem(EmpInfoItemData domain) {

		return SettingItemDto.createFromJavaType(domain.getPerInfoCtgCd(), domain.getPerInfoDefId(),
				domain.getItemCode().v(), domain.getItemName(), domain.getIsRequired().value,
				domain.getDataState().getDataStateType().value, domain.getDataState().getDateValue(),
				domain.getDataState().getNumberValue(), domain.getDataState().getStringValue(), domain.getDataType(),
				domain.getSelectionItemRefType(), domain.getItemParentCd());

	}

	public String getValueAsString() {

		switch (this.saveData.saveDataType) {
		case DATE:
			DateDataDto dateData = (DateDataDto) this.saveData;
			return dateData.getValue().toString("yyyy/MM/dd");
		case NUMBERIC:
			NumberDataDto numberData = (NumberDataDto) this.saveData;
			return new Integer(numberData.getValue()).toString();
		case STRING:
			StringDataDto stringData = (StringDataDto) this.saveData;
			return stringData.getValue();
		}
		return "";

	}

}
