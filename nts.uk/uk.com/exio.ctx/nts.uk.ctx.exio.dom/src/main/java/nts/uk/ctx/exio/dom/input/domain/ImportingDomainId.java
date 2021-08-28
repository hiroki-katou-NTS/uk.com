package nts.uk.ctx.exio.dom.input.domain;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 受入グループID
 */
@RequiredArgsConstructor
public enum ImportingDomainId{
	
	/** 個人基本情報 */
	EMPLOYEE_BASIC(100),

	/** 雇用履歴 */
	EMPLOYMENT_HISTORY(102),

	/** 所属職場履歴 **/
	AFF_WORKPLACE_HISTORY(103),
	
	/** 職位 */
	JOBTITLE_HISTORY(104),
	
	/** 分類 */
	CLASSIFICATION_HISTORY(105),
	
	/** 社員の特別休暇付与設定 */
	EMPLOYEE_SPECIAL_HOLIDAY_GRANT_SETTING(116),
	
	/** 特別休暇付与残数データ */
	SPECIAL_HORYDAY_GRANT_REMAIN(117),
	
	/** 振休管理データ */
	SUBSTITUTE_HOLIDAY(118),
	
	/** 振出管理データ */
	SUBSTITUTE_WORK(119),
	
	/** 代休管理データ */
	COMPENSATORY_HOLIDAY(120),
	
	/** 休出管理データ */
	HOLIDAY_WORK(121),
	
	/** 作業 */
	TASK(200);
	
	
	public final int value;
	
	public static ImportingDomainId valueOf(int value) {
		return EnumAdaptor.valueOf(value, ImportingDomainId.class);
	}
	
}
