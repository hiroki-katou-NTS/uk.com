package nts.uk.ctx.bs.person.dom.person.info.dateitem;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.DataTypeObject;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.DataTypeState;

@Getter
public class DateItem extends DataTypeObject {

	private DateType dateItemType;

	public DateItem(int dateItemType) {
		super();
		this.dataTypeState = DataTypeState.DATE;
		this.dateItemType = EnumAdaptor.valueOf(dateItemType, DateType.class);
	}
}
