package nts.uk.ctx.exio.dom.input.group;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * トランザクション単位
 */
@RequiredArgsConstructor
public enum TransactionUnit {

	/** 全データ一括 */
	ALL(1),
	
	/** １社員ずつ */
	EMPLOYEE(2),
	
	;
	
	public final int value;
	
	public static TransactionUnit valueOf(int value) {
		return EnumAdaptor.valueOf(value, TransactionUnit.class);
	}
}
