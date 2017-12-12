package nts.uk.ctx.pereg.app.find.initsetting.item;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pereg.dom.person.setting.init.item.SaveDataType;

/**
 * @author sonnlb
 *
 */
public class StringDataDto extends SaveDataDto {
	@Getter
	@Setter
	private String value;

	private StringDataDto(String value) {
		super();
		this.saveDataType = SaveDataType.STRING;
		this.value = value;
	}

	public static StringDataDto createFromJavaType(String value) {

		return new StringDataDto(value);

	}

}
