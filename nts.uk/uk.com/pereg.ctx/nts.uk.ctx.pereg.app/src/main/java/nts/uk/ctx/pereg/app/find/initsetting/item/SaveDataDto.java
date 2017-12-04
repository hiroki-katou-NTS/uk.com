package nts.uk.ctx.pereg.app.find.initsetting.item;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.dom.person.setting.init.item.SaveDataType;

/**
 * @author sonnlb
 *
 */

public class SaveDataDto {
	@Getter
	protected SaveDataType saveDataType;

	public static SaveDataDto createDataDto(String value) {
		return StringDataDto.createFromJavaType(value);
	}

	public static SaveDataDto createDataDto(int value) {
		return NumberDataDto.createFromJavaType(value);
	}

	public static SaveDataDto createDataDto(GeneralDate value) {
		return DateDataDto.createFromJavaType(value);
	}

}
