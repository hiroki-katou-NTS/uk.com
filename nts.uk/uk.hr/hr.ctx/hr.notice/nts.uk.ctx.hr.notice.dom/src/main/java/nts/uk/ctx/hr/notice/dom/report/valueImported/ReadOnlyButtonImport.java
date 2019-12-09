package nts.uk.ctx.hr.notice.dom.report.valueImported;

import lombok.Getter;
@Getter
public class ReadOnlyButtonImport extends DataTypeStateImport{
	private String readText;
	
	public ReadOnlyButtonImport( String readText) {
		super();
		this.dataTypeValue = DataTypeValueImport.READONLY_BUTTON.value;
		this.readText = readText;
	}
	
	public static ReadOnlyButtonImport createFromJavaType(String readText) {
		return new ReadOnlyButtonImport(readText);
	}

}
