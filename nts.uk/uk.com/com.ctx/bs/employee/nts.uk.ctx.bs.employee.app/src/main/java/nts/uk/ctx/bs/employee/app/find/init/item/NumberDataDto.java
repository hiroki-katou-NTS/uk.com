package nts.uk.ctx.bs.employee.app.find.init.item;

import lombok.Getter;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.SaveDataType;

/**
 * @author sonnlb
 *
 */
public class NumberDataDto extends SaveDataDto {
	@Getter
	private int value;

	private NumberDataDto(int value) {
		super();
		this.value = value;
		this.saveDataType = SaveDataType.NUMBERIC.value;
	}

	public static NumberDataDto createFromJavaType(int value) {
		return new NumberDataDto(value);
	}

}
