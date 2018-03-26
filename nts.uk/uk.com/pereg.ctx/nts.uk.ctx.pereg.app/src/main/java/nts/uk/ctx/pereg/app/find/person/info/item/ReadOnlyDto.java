package nts.uk.ctx.pereg.app.find.person.info.item;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
@Getter
public class ReadOnlyDto extends DataTypeStateDto{
	
	private String readText;
	
	public ReadOnlyDto( String readText) {
		super();
		this.dataTypeValue = DataTypeValue.READONLY.value;
		this.readText = readText;
	}
	
	public static ReadOnlyDto createFromJavaType(String readText) {
		return new ReadOnlyDto(readText);
	}

}
