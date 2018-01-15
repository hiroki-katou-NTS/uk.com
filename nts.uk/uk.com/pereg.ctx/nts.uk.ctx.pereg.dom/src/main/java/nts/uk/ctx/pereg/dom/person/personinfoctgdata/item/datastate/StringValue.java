package nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.datastate;

import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.DataState;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.DataStateType;

public class StringValue extends DataState {
	public String value;

	public StringValue(String value) {
		super();
		this.dataStateType = DataStateType.String;
		this.value = value;
	}

	public static StringValue createFromJavaType(String value) {
		return new StringValue(value);
	}

}
