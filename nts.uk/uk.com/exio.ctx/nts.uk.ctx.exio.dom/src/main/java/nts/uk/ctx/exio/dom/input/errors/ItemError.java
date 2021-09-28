package nts.uk.ctx.exio.dom.input.errors;

import lombok.Value;

/**
 * 単一項目のエラー
 */
@Value
public class ItemError {

	int itemNo;
	String message;
	
}
