package nts.uk.ctx.bs.person.dom.person.info.stringitem;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.DataTypeObject;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.DataTypeState;

@Getter
public class StringItem extends DataTypeObject {

	private StringItemLeng stringItemLeng;
	private StringItemType stringItemType;
	private StringItemDataType stringItemDataType;

	public StringItem(int stringItemLeng, int stringItemType, int stringItemDataType) {
		super();
		this.dataTypeState = DataTypeState.STRING;
		this.stringItemLeng = new StringItemLeng(stringItemLeng);
		this.stringItemType = EnumAdaptor.valueOf(stringItemType, StringItemType.class);
		this.stringItemDataType = EnumAdaptor.valueOf(stringItemDataType, StringItemDataType.class);
	}
}
