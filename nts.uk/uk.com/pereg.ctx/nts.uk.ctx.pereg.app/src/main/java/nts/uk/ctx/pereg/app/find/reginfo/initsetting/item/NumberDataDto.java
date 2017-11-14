package nts.uk.ctx.pereg.app.find.reginfo.initsetting.item;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.SaveDataType;

/**
 * @author sonnlb
 *
 */
public class NumberDataDto extends SaveDataDto {
	@Getter
	@Setter
	private int value;

	private NumberDataDto(int value) {
		super();
		this.value = value;
		this.saveDataType = SaveDataType.NUMBERIC;
	}

	public static NumberDataDto createFromJavaType(int value) {
		return new NumberDataDto(value);
	}

}
