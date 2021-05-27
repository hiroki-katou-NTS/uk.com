package nts.uk.ctx.exio.dom.input.validation;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.validation.classtype.ValidationEnum;
import nts.uk.ctx.exio.dom.input.validation.classtype.ValidationPrimitiveValue;

@Value
public class ImportableItem {
	private int itemNo;
	private String fqn;
	private ValidationClassType atr;
	
	public void validate(Object value) {
		if(this.getAtr() == ValidationClassType.PV) {
			ValidationPrimitiveValue.run(this.fqn, value);
		}
		else if(this.getAtr() == ValidationClassType.ENUM) {
			ValidationEnum.run(this.fqn, value);
		}
	}
}
