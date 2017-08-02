package nts.uk.ctx.bs.person.dom.person.info.stringitem;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.DataTypeValue;

@Getter
public class StringItem extends DataTypeState {

	private StringItemLeng stringItemLeng;
	private StringItemType stringItemType;
	private StringItemDataType stringItemDataType;

	private StringItem(int stringItemLeng, int stringItemType, int stringItemDataType) {
		super();
		this.dataTypeValue = DataTypeValue.STRING;
		this.stringItemLeng = new StringItemLeng(stringItemLeng);
		this.stringItemType = EnumAdaptor.valueOf(stringItemType, StringItemType.class);
		this.stringItemDataType = EnumAdaptor.valueOf(stringItemDataType, StringItemDataType.class);
	}

	public static StringItem createFromJavaType(int stringItemLeng, int stringItemType, int stringItemDataType) {
		return new StringItem(stringItemLeng, stringItemType, stringItemDataType);
	}
}
