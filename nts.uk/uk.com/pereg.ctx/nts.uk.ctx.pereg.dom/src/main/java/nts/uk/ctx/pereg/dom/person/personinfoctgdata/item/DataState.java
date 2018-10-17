package nts.uk.ctx.pereg.dom.person.personinfoctgdata.item;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.datastate.DateValue;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.datastate.NumberValue;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.datastate.StringValue;

public class DataState extends AggregateRoot {
	
	@Getter
	protected DataStateType dataStateType;
	
	public String getStringValue(){
		StringValue sValue = (StringValue) this;
		return sValue.value;
	}

	public GeneralDate getDateValue(){
		DateValue dateValue = (DateValue) this;
		return dateValue.value;
	}
	
	public BigDecimal getNumberValue(){
		NumberValue numberValue = (NumberValue) this;
		return numberValue.value;
	}
	
	public Object getValue() {
		switch (dataStateType) {
		case String:
			StringValue sValue = (StringValue) this;
			return sValue.value;

		case Numeric:
			NumberValue numberValue = (NumberValue) this;
			return numberValue.value;
		case Date:
			DateValue dateValue = (DateValue) this;
			return dateValue.value;
		default:
			return null;
		}
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
