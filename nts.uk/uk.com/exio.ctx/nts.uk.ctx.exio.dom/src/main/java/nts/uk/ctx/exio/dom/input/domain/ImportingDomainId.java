package nts.uk.ctx.exio.dom.input.domain;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 受入グループID
 */
@RequiredArgsConstructor
public enum ImportingDomainId{

	/** 雇用履歴 */
	EMPLOYMENT_HISTORY(102),

	/** 所属職場履歴 **/
	AFF_WORKPLACE_HISTORY(103),
	
	/** 職位 */
	JOBTITLE_HISTORY(104),
	
	/** 分類 */
	CLASSIFICATION_HISTORY(105),
	
	/** 作業 */
	TASK(200);
	
	
	public final int value;
	
	public static ImportingDomainId valueOf(int value) {
		return EnumAdaptor.valueOf(value, ImportingDomainId.class);
	}
	
}
