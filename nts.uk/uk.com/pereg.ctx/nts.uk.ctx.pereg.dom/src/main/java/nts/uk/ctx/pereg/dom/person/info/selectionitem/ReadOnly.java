package nts.uk.ctx.pereg.dom.person.info.selectionitem;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
@Getter

public class ReadOnly  extends DataTypeState {
	
	private ReadText readText;
	private ReadOnly(String readText) {
		super();
		this.dataTypeValue = DataTypeValue.READONLY;
		this.readText = new ReadText(readText);
	}

	public static ReadOnly createFromJavaType(String readText) {
		return new ReadOnly(readText);
	}
}
