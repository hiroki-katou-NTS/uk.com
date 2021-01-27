/**
 * 11:38:58 AM Nov 2, 2017
 */
package nts.uk.ctx.at.shared.dom.alarmList.extractionResult.worktime;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.enums.WorkCheckResult;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.enums.FilterByCompare;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.snapshot.SnapShot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;

/**
 * @author hungnm
 *
 */
// 就業時間帯の条件
public class WorkTimeCondition extends DomainObject {

	// 勤務種類の条件を使用する
	private boolean useAtr;

	// 予実比較による絞り込み方法
	@Getter
	private FilterByCompare comparePlanAndActual;

	/* Constructor */
	protected WorkTimeCondition(Boolean useAtr, FilterByCompare comparePlanAndActual) {
		super();
		this.useAtr = useAtr;
		this.comparePlanAndActual = comparePlanAndActual;
	}

	/** 就業時間帯をチェックする */
	public WorkCheckResult checkWorkTime(WorkInfoOfDailyAttendance workInfo, Optional<SnapShot> snapshot) {
		return WorkCheckResult.NOT_CHECK;
	}
	
	public boolean isUse() {
		return this.useAtr;
	}
	
	public void clearDuplicate() { }
	
	public void addWorkTime(WorkTimeCode plan, WorkTimeCode actual) { } 
	
	public void addWorkTime(List<WorkTimeCode> plan, List<WorkTimeCode> actual) { }
	
	public void setupWorkTime(boolean usePlan, boolean useActual) { }
	
	public WorkTimeCondition chooseOperator(int operator) {
		return this;
	}
}
