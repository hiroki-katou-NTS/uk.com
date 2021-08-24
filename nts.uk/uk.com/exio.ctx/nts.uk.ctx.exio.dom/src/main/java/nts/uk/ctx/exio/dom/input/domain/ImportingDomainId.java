package nts.uk.ctx.exio.dom.input.domain;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 受入グループID
 */
@RequiredArgsConstructor
public enum ImportingDomainId{

	/** 雇用履歴 */
	EMPLOYMENT_HISTORY(100),
	
	/** 作業 */
	TASK(200),
	
	/** 分類 */
	CLASSIFICATION_HISTORY(300),

	/** 職位 */
	JOBTITLE_HISTORY(400),
	
	/** 所属職場履歴 **/
	AFF_WORKPLACE_HISTORY(500)
	;
	
	public final int value;
	
	public static ImportingDomainId valueOf(int value) {
		return EnumAdaptor.valueOf(value, ImportingDomainId.class);
	}
	
}
