package nts.uk.ctx.pereg.dom.person.info.stringitem;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;

@Getter
public class StringItem extends DataTypeState {

	private StringItemLength stringItemLength;
	private StringItemType stringItemType;
	private StringItemDataType stringItemDataType;

	private StringItem(int stringItemLength, int stringItemType, int stringItemDataType) {
		super();
		this.dataTypeValue = DataTypeValue.STRING;
		this.stringItemLength = new StringItemLength(stringItemLength);
		this.stringItemType = EnumAdaptor.valueOf(stringItemType, StringItemType.class);
		this.stringItemDataType = EnumAdaptor.valueOf(stringItemDataType, StringItemDataType.class);
	}

	public static StringItem createFromJavaType(int stringItemLength, int stringItemType, int stringItemDataType) {
		return new StringItem(stringItemLength, stringItemType, stringItemDataType);
	}
}
