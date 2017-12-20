package nts.uk.ctx.at.shared.dom.worktime.commonsetting;

import lombok.Value;

/**
 * 共通の休憩設定
 * @author keisuke_hoshina
 *
 */
@Value
public class BreakSetOfCommon {
	private boolean TreatAsBreakTImeDuringWorkToWork;
	private CalcMethodIfLeaveWorkDuringBreakTime leaveWorkDuringBreakTime;
}
