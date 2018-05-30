package nts.uk.shr.com.security.audittrail.correction.content;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 対象データ種類
 */
@RequiredArgsConstructor
public enum TargetDataType {

	/** スケジュール */
	SCHEDULE(0),
	
	/** 日別実績 */
	DAILY_RECORD(1),
	
	/** 月別実績 */
	MONTHLY_RECORD(2),
	
	/** 任意期間集計 */
	
	/** 補助月次 */
	
	/** 申請承認 */
	REQUEST_APPROVAL(5),
	
	/** 給与明細 */
	
	/** 賞与明細 */
	
	/** 年末調整 */
	
	/** 届出 */
	
	;
	public final int value;
	
	public static TargetDataType of(int value) {
		return EnumAdaptor.valueOf(value, TargetDataType.class);
	}
}
