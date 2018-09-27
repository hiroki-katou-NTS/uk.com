package nts.uk.ctx.at.function.dom.adapter.sysfixedcheckcondition;

import java.util.Optional;

import nts.uk.ctx.at.function.dom.alarm.alarmdata.ValueExtractAlarm;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

public interface SysFixedCheckConMonAdapter {
	//5:36協定のチェック
	public Optional<ValueExtractAlarm>  checkAgreement(String employeeID,int yearMonth,int closureId,ClosureDate closureDate);
	//1:月次未確認
	public Optional<ValueExtractAlarm>  checkMonthlyUnconfirmed(String employeeID,int yearMonth);
	//6：代休の消化期限チェック
	public Optional<ValueExtractAlarm>  checkDeadlineCompensatoryLeaveCom(String employeeID, Closure closing, CompensatoryLeaveComSetting compensatoryLeaveComSetting );
}
