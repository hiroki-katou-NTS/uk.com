package nts.uk.ctx.bs.employee.dom.regpersoninfo.init.item;

import nts.arc.time.GeneralDate;

/**
 * @author sonnlb
 *
 */
public class SaveDataDto {
	protected int saveDataType;

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
