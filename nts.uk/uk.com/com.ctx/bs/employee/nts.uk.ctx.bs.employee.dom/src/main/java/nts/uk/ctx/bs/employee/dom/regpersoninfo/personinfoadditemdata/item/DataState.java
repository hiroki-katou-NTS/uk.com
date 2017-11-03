package nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.datastate.DateValue;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.datastate.NumberValue;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item.datastate.StringValue;

public class DataState extends AggregateRoot {
	// 保存データ状態
	@Getter
	protected DataStateType dataStateType;

	public String getStringValue() {
		StringValue sValue = (StringValue) this;
		return sValue.value;
	}

	public GeneralDate getDateValue() {
		DateValue dateValue = (DateValue) this;
		return dateValue.value;
	}

	public BigDecimal getNumberValue() {
		NumberValue numberValue = (NumberValue) this;
		return numberValue.value;
	}

	public static DataState createFromStringValue(String value) {

		return StringValue.createFromJavaType(value);
	}

	public static DataState createFromDateValue(GeneralDate value) {

		return DateValue.createFromJavaType(value);
	}

	public static DataState createFromNumberValue(BigDecimal value) {
		return NumberValue.createFromJavaType(value);
	}

}
