package nts.uk.ctx.exio.dom.input.validation;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.exio.dom.input.validation.systemrange.ValidationEnum;
import nts.uk.ctx.exio.dom.input.validation.systemrange.ValidationPrimitiveValue;

/**
 * 受入可能項目の定義
 */
@Getter
public class ImportableItem implements DomainAggregate{

	private int groupId;
	private int itemNo;
	private String fqn;
	private CheckMethod atr;
	
	
	public ImportableItem(int groupId, int itemNo, String fqn, CheckMethod atr) {
		super();
		this.groupId = groupId;
		this.itemNo = itemNo;
		this.fqn = fqn;
		this.atr = atr;
	}

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
