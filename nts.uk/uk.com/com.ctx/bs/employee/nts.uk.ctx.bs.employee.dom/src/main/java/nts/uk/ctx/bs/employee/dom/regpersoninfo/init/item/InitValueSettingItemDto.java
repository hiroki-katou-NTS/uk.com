package nts.uk.ctx.bs.employee.dom.regpersoninfo.init.item;

import lombok.Value;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.PerInfoInitValueSetItem;

/**
 * @author sonnlb
 *
 */
@Value
public class InitValueSettingItemDto {

	private String itemName;

	private int isRequired;

	private SaveDataDto saveData;

	public static InitValueSettingItemDto fromDomain(PerInfoInitValueSetItem domain) {
		return new InitValueSettingItemDto(domain.getItemName(), domain.getIsRequired().value,
				createSaveDataDto(domain));
	}

	private static SaveDataDto createSaveDataDto(PerInfoInitValueSetItem domain) {

		SaveDataDto resultDto = new SaveDataDto();
		switch (domain.getSaveDataType()) {
		case DATE:
			resultDto = SaveDataDto.CreateDateDataDto(domain.getDateValue());
			break;
		case NUMBERIC:
			resultDto = SaveDataDto.CreateNumberDataDto(domain.getIntValue().v().intValueExact());
			break;
		case STRING:
			resultDto = SaveDataDto.CreateStringDataDto(domain.getStringValue().v());
			break;
		}

		return resultDto;
	}
}
