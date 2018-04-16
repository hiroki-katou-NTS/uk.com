package nts.uk.ctx.pereg.dom.person.info.selectionitem;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
@Getter
public class ReadOnlyButton  extends DataTypeState {
	private ReadText readText;
	private ReadOnlyButton(String readText) {
		super();
		this.dataTypeValue = DataTypeValue.READONLY_BUTTON;
		this.readText = new ReadText(readText);
	}

	public static ReadOnlyButton createFromJavaType(String readText) {
		return new ReadOnlyButton(readText);
	}
}
