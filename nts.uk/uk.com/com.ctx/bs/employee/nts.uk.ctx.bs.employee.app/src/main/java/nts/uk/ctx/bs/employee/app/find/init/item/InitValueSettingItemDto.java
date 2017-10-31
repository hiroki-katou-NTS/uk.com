package nts.uk.ctx.bs.employee.app.find.init.item;

import java.math.BigDecimal;

import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.SaveDataType;

/**
 * @author sonnlb
 *
 */
@Value
public class InitValueSettingItemDto {

	private String itemCode;

	private String itemName;

	private int isRequired;

	private SaveDataDto saveData;

	private static SaveDataDto createSaveDataDto(int saveDataValue, GeneralDate dateValue, BigDecimal intValue,
			String stringValue) {
		SaveDataDto resultDto = new SaveDataDto();

		SaveDataType saveDataType = EnumAdaptor.valueOf(saveDataValue, SaveDataType.class);

		switch (saveDataType) {
		case DATE:
			resultDto = SaveDataDto.CreateDateDataDto(dateValue);
			break;
		case NUMBERIC:
			resultDto = SaveDataDto.CreateNumberDataDto(intValue.intValueExact());
			break;
		case STRING:
			resultDto = SaveDataDto.CreateStringDataDto(stringValue);
			break;
		}

		return resultDto;
	}

	public static InitValueSettingItemDto createFromJavaType(String itemCode, String itemName, int isRequired,
			int saveDataValue, GeneralDate dateValue, BigDecimal intValue, String stringValue) {

		return new InitValueSettingItemDto(itemCode, itemName, isRequired,
				createSaveDataDto(saveDataValue, dateValue, intValue, stringValue));

	}

	public void setData(String value) {
		StringDataDto saveData = (StringDataDto) this.saveData;
		saveData.setValue(value);
	}

	public void setData(int value) {
		NumberDataDto saveData = (NumberDataDto) this.saveData;
		saveData.setValue(value);
	}

	public void setData(GeneralDate value) {
		DateDataDto saveData = (DateDataDto) this.saveData;
		saveData.setValue(value);
	}

}
