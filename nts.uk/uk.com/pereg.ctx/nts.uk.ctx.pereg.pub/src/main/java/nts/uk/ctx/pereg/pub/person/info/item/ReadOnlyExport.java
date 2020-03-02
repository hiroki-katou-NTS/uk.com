package nts.uk.ctx.pereg.pub.person.info.item;

import lombok.Getter;
@Getter
public class ReadOnlyExport extends DataTypeStateExport{
	
	private String readText;
	
	public ReadOnlyExport( String readText) {
		super();
		this.dataTypeValue = DataTypeValueExport.READONLY.value;
		this.readText = readText;
	}
	
	public static ReadOnlyExport createFromJavaType(String readText) {
		return new ReadOnlyExport(readText);
	}

}
