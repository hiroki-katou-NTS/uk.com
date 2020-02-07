package nts.uk.ctx.hr.notice.dom.report.valueImported;

import lombok.Getter;
@Getter
public class ReadOnlyImport extends DataTypeStateImport{
	
	private String readText;
	
	public ReadOnlyImport( String readText) {
		super();
		this.dataTypeValue = DataTypeValueImport.READONLY.value;
		this.readText = readText;
	}
	
	public static ReadOnlyImport createFromJavaType(String readText) {
		return new ReadOnlyImport(readText);
	}

}
