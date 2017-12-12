package nts.uk.ctx.pereg.dom.person.info.dateitem;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;

@Getter
public class DateItem extends DataTypeState {

	private DateType dateItemType;

	private DateItem(int dateItemType) {
		super();
		this.dataTypeValue = DataTypeValue.DATE;
		this.dateItemType = EnumAdaptor.valueOf(dateItemType, DateType.class);
	}

	public static DateItem createFromJavaType(int dateItemType) {
		return new DateItem(dateItemType);
	}
}
