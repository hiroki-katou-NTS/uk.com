package nts.uk.ctx.pereg.app.find.initsetting.item;

import java.math.BigDecimal;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.EmpInfoItemData;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.SaveDataType;

/**
 * @author sonnlb
 *
 */
@Data
public class SettingItemDto {

	private String perInfoCtgId;

	private String categoryCode;

	private String itemDefId;

	private String itemCode;

	private String itemName;

	private int isRequired;

	private SaveDataDto saveData;

	public SettingItemDto(String perInfoCtgId, String categoryCode, String itemDefId, String itemCode, String itemName,
			int isRequired, SaveDataDto saveData) {
		super();
		this.perInfoCtgId = perInfoCtgId;
		this.categoryCode = categoryCode;
		this.itemDefId = itemDefId;
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.isRequired = isRequired;
		this.saveData = saveData;
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

	public static SettingItemDto createFromJavaType(String perInfoCtgId, String categoryCode, String itemDefId,
			String itemCode, String itemName, int isRequired, int saveDataValue, GeneralDate dateValue,
			BigDecimal intValue, String stringValue) {

		return new SettingItemDto(perInfoCtgId, categoryCode, itemDefId, itemCode, itemName, isRequired,
				createSaveDataDto(saveDataValue, dateValue, intValue, stringValue));

	}

	public static SettingItemDto createFromJavaType(String perInfoCtgId, String categoryCode, String itemDefId,
			String itemCode, String itemName, int isRequired, GeneralDate dateValue) {

		return new SettingItemDto(perInfoCtgId, categoryCode, itemDefId, itemCode, itemName, isRequired,
				SaveDataDto.createDataDto(dateValue));

	}

	public static SettingItemDto createFromJavaType(String perInfoCtgId, String categoryCode, String itemDefId,
			String itemCode, String itemName, int isRequired, int intValue) {

		return new SettingItemDto(perInfoCtgId, categoryCode, itemDefId, itemCode, itemName, isRequired,
				SaveDataDto.createDataDto(intValue));

	}

	public static SettingItemDto createFromJavaType(String perInfoCtgId, String categoryCode, String itemDefId,
			String itemCode, String itemName, int isRequired, String stringValue) {

		return new SettingItemDto(perInfoCtgId, categoryCode, itemDefId, itemCode, itemName, isRequired,
				SaveDataDto.createDataDto(stringValue));

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

		return SettingItemDto.createFromJavaType(domain.getPerInfoCtgId(), domain.getPerInfoCtgCd(),
				domain.getPerInfoDefId(), domain.getItemCode().v(), domain.getItemName(), domain.getIsRequired().value,
				domain.getDataState().getDataStateType().value, domain.getDataState().getDateValue(),
				domain.getDataState().getNumberValue(), domain.getDataState().getStringValue());

	}

	public String getValueAsString() {

		switch (this.saveData.saveDataType) {
		case DATE:
			DateDataDto dateData = (DateDataDto) this.saveData;
			return dateData.getValue().toString("dd/MM/yyyy");
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
