package nts.uk.ctx.pereg.app.find.person.info.item;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;

@Getter
public class NumericButtonDto extends DataTypeStateDto{
      private String readText;
	
	private NumericButtonDto( String readText) {
		super();
		this.dataTypeValue = DataTypeValue.NUMBERIC_BUTTON.value;
		this.readText = readText;
	}

	public static NumericButtonDto createFromJavaType(String readText) {
		return new NumericButtonDto(readText);
	}
}
