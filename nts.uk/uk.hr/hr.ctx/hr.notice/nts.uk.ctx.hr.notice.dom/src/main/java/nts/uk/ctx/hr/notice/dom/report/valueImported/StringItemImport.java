package nts.uk.ctx.hr.notice.dom.report.valueImported;

import lombok.Getter;

@Getter
public class StringItemImport extends DataTypeStateImport {

	private int stringItemLength;
	private int stringItemType;
	private int stringItemDataType;

	private StringItemImport(int stringItemLength, int stringItemType, int stringItemDataType) {
		super();
		this.dataTypeValue = DataTypeValueImport.STRING.value;
		this.stringItemLength = stringItemLength;
		this.stringItemType = stringItemType;
		this.stringItemDataType = stringItemDataType;
	}

	public static StringItemImport createFromJavaType(int stringItemLength, int stringItemType, int stringItemDataType) {
		return new StringItemImport(stringItemLength, stringItemType, stringItemDataType);
	}
	
	public void updateStringLength(int length) {
		this.stringItemLength = length;
	}

}
