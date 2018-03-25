package nts.uk.ctx.pereg.app.find.person.info.item;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
@Getter
public class ReadOnlyButtonDto extends DataTypeStateDto{
	private String readText;
	
	public ReadOnlyButtonDto( String readText) {
		super();
		this.dataTypeValue = DataTypeValue.READONLY_BUTTON.value;
		this.readText = readText;
	}
	
	public static ReadOnlyButtonDto createFromJavaType(String readText) {
		return new ReadOnlyButtonDto(readText);
	}

}
