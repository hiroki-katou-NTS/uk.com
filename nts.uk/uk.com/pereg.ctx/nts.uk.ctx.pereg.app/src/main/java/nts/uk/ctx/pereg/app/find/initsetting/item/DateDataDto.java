package nts.uk.ctx.pereg.app.find.initsetting.item;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.dom.person.setting.init.item.SaveDataType;

/**
 * @author sonnlb
 *
 */
public class DateDataDto extends SaveDataDto {

	@Getter
	@Setter
	private GeneralDate value;

	private DateDataDto(GeneralDate value) {
		super();
		this.saveDataType = SaveDataType.DATE;
		this.value = value;
	}

	public static DateDataDto createFromJavaType(GeneralDate value) {

		return new DateDataDto(value);

	}

}
