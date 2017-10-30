package nts.uk.ctx.bs.employee.app.find.init.item;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.SaveDataType;

/**
 * @author sonnlb
 *
 */
public class SaveDataDto {
	protected SaveDataType saveDataType;

	public static SaveDataDto CreateStringDataDto(String value) {
		return StringDataDto.createFromJavaType(value);
	}

	public static SaveDataDto CreateNumberDataDto(int value) {
		return NumberDataDto.createFromJavaType(value);
	}

	public static SaveDataDto CreateDateDataDto(GeneralDate value) {
		return DateDataDto.createFromJavaType(value);
	}

}
