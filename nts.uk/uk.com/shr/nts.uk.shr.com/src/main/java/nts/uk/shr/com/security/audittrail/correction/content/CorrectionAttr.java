package nts.uk.shr.com.security.audittrail.correction.content;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 修正区分
 */
@RequiredArgsConstructor
public enum CorrectionAttr {

	/** 手修正 */
	EDIT(0),
	
	/** 計算 */
	CALCULATE(1),

	/** 反映 */
	REFLECT(2),
	
	;
	public final int value;
	
	public static CorrectionAttr of(int value) {
		return EnumAdaptor.valueOf(value, CorrectionAttr.class);
	}
}
