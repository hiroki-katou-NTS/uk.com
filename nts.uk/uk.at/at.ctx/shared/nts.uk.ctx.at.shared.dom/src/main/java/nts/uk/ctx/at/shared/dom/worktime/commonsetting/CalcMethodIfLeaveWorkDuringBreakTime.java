package nts.uk.ctx.at.shared.dom.worktime.commonsetting;

import lombok.Getter;

/**
 * 休憩時間中に退勤した場合の計算方法
 * @author keisuke_hoshina
 *
 */
@Getter
public enum CalcMethodIfLeaveWorkDuringBreakTime {
	RecordAll,
	NotRecordAll,
	RecordUntilLeaveWork;
}
