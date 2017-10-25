package nts.uk.ctx.bs.employee.dom.regpersoninfo.init.item;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.SaveDataType;

/**
 * @author sonnlb
 *
 */
public class DateDataDto extends SaveDataDto {

	@Getter
	private GeneralDate value;

	private DateDataDto(GeneralDate value) {
		super();
		this.saveDataType = SaveDataType.DATE.value;
		this.value = value;
	}

	public static DateDataDto createFromJavaType(GeneralDate value) {

		return new DateDataDto(value);

	}

}
