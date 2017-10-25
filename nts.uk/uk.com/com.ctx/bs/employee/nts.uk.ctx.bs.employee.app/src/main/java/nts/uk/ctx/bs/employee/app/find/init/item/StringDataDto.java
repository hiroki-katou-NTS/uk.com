package nts.uk.ctx.bs.employee.app.find.init.item;

import lombok.Getter;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.SaveDataType;

/**
 * @author sonnlb
 *
 */
public class StringDataDto extends SaveDataDto {
	@Getter
	private String value;

	private StringDataDto(String value) {
		super();
		this.saveDataType = SaveDataType.STRING.value;
		this.value = value;
	}

	public static StringDataDto createFromJavaType(String value) {

		return new StringDataDto(value);

	}

}
