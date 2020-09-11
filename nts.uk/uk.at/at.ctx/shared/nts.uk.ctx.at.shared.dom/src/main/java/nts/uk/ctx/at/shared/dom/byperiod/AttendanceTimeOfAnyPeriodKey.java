package nts.uk.ctx.at.shared.dom.byperiod;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.byperiod.anyaggrperiod.AnyAggrFrameCode;

/**
 * キー値：任意期間別実績の勤怠時間
 * @author shuichu_ishida
 */
@Value
public class AttendanceTimeOfAnyPeriodKey {
	/** 社員ID */
	String employeeId;
	/** 任意集計枠コード */
	AnyAggrFrameCode anyAggrFrameCode;
}
