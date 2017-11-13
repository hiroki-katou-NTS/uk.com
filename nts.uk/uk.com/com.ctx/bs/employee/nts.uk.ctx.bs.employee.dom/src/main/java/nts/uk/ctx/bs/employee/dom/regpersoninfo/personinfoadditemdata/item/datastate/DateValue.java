package nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.datastate;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.DataState;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.DataStateType;

public class DateValue extends DataState {

	public GeneralDate value;

	private DateValue(GeneralDate value) {
		super();
		this.dataStateType = DataStateType.Date;
		this.value = value;
	}

	public static DateValue createFromJavaType(GeneralDate value) {

		return new DateValue(value);

	}

}
