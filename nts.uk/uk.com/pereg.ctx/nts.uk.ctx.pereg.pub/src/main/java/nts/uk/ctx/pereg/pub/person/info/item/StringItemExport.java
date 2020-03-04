package nts.uk.ctx.pereg.pub.person.info.item;

import lombok.Getter;

@Getter
public class StringItemExport extends DataTypeStateExport {

	private int stringItemLength;
	private int stringItemType;
	private int stringItemDataType;

	private StringItemExport(int stringItemLength, int stringItemType, int stringItemDataType) {
		super();
		this.dataTypeValue = DataTypeValueExport.STRING.value;
		this.stringItemLength = stringItemLength;
		this.stringItemType = stringItemType;
		this.stringItemDataType = stringItemDataType;
	}

	public static StringItemExport createFromJavaType(int stringItemLength, int stringItemType, int stringItemDataType) {
		return new StringItemExport(stringItemLength, stringItemType, stringItemDataType);
	}
	
	public void updateStringLength(int length) {
		this.stringItemLength = length;
	}

}
