package nts.uk.ctx.pereg.pub.person.info.item;

import lombok.Getter;
@Getter
public class ReadOnlyButtonExport extends DataTypeStateExport{
	private String readText;
	
	public ReadOnlyButtonExport( String readText) {
		super();
		this.dataTypeValue = DataTypeValueExport.READONLY_BUTTON.value;
		this.readText = readText;
	}
	
	public static ReadOnlyButtonExport createFromJavaType(String readText) {
		return new ReadOnlyButtonExport(readText);
	}

}
