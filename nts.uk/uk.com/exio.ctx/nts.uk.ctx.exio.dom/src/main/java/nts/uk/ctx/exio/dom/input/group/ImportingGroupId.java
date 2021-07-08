package nts.uk.ctx.exio.dom.input.group;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 受入グループID
 */
@RequiredArgsConstructor
public enum ImportingGroupId{

	/** 雇用履歴 */
	EMPLOYMENT_HISTORY(100),
	
	/** 作業 */
	TASK(200),
	
	;
	
	public final int value;
	
	public static ImportingGroupId valueOf(int value) {
		return EnumAdaptor.valueOf(value, ImportingGroupId.class);
	}
	
}
