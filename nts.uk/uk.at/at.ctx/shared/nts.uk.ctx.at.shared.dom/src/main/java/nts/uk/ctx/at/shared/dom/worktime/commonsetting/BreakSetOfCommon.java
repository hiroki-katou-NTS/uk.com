package nts.uk.ctx.at.shared.dom.worktime.commonsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 共通の休憩設定
 * @author keisuke_hoshina
 *
 */
@Getter
@AllArgsConstructor
public class BreakSetOfCommon {
	//休憩時間中に退勤した場合の計算方法
	private CalcMethodIfLeaveWorkDuringBreakTime leaveWorkDuringBreakTime;
	
	/*:
	 * 休憩時間中に退勤した場合の計算方法を変更する
	 */
	public void changeCalcMethodToRecordUntilLeaveWork() {
		this.leaveWorkDuringBreakTime = CalcMethodIfLeaveWorkDuringBreakTime.RecordUntilLeaveWork; 
	}
	
}
