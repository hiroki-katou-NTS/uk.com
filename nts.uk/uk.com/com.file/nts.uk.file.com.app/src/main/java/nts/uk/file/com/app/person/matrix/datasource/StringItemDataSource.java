package nts.uk.file.com.app.person.matrix.datasource;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;

/**
 * StringItemDataSource
 * 
 * @author lanlt
 *
 */
@Getter
public class StringItemDataSource extends DataTypeStateDataSource {

	private int stringItemLength;
	private int stringItemType;
	private int stringItemDataType;

	private StringItemDataSource(int stringItemLength, int stringItemType, int stringItemDataType) {
		super();
		this.dataTypeValue = DataTypeValue.STRING.value;
		this.stringItemLength = stringItemLength;
		this.stringItemType = stringItemType;
		this.stringItemDataType = stringItemDataType;
	}

	public static StringItemDataSource createFromJavaType(int stringItemLength, int stringItemType,
			int stringItemDataType) {
		return new StringItemDataSource(stringItemLength, stringItemType, stringItemDataType);
	}

	public void updateStringLength(int length) {
		this.stringItemLength = length;
	}

}