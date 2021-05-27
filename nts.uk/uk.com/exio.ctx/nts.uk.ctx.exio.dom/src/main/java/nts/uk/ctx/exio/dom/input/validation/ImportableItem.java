package nts.uk.ctx.exio.dom.input.validation;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.validation.classtype.ValidationEnum;
import nts.uk.ctx.exio.dom.input.validation.classtype.ValidationPrimitiveValue;

@Value
public class ImportableItem {
	private int itemNo;
	private String fqn;
	private CheckMethod atr;
	
	public void validate(Object value) {
		switch(this.atr) {
			case PRIMITIVE_VALUE:
				ValidationPrimitiveValue.run(this.fqn, value);
				break;
			case ENUM:
				ValidationEnum.run(this.fqn, value);
				break;
			case NO_CHECK:
				break;
			default:
				throw new RuntimeException("チェック方法が定義されていません。:" + this.atr);
		}
	}
}
