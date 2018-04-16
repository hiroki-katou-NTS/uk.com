package nts.uk.ctx.pereg.dom.person.info.selectionitem;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
@Getter
public class NumericButton extends DataTypeState {
	private ReadText readText;

	private NumericButton(String readText) {
		super();
		this.dataTypeValue = DataTypeValue.NUMBERIC_BUTTON;
		this.readText = new ReadText(readText);
	}

	public static NumericButton createFromJavaType(String readText) {
		return new NumericButton(readText);
	}
}
