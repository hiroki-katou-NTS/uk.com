package nts.uk.ctx.bs.employee.app.find.init.item;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.SaveDataType;

/**
 * @author sonnlb
 *
 */
public class SaveDataDto {
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
