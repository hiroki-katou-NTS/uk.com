package nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item;

import java.math.BigDecimal;

import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.datastate.DateValue;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.datastate.NumberValue;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.datastate.StringValue;

public class DataState extends AggregateRoot {

	protected DataStateType dataStateType;

	public static DataState createFromStringValue(String value) {

		return StringValue.createFromJavaType(value);
	}

	public static DataState createFromDateValue(GeneralDate value) {

		return DateValue.createFromDateValue(value);
	}

	public static DataState createFromNumberValue(BigDecimal value) {
		return NumberValue.createFromJavaType(value);
	}

}
