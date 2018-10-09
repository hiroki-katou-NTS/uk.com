package nts.uk.ctx.at.record.dom.remainingnumber.dayoff.temp;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.AttendanceDaysMonthToTal;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.RemainDataDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata.DayOffDayAndTimes;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata.DayOffRemainDayAndTimes;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata.MonthlyDayoffRemainData;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffManagementQuery;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 実装：（仮対応用）代休
 * @author shuichu_ishida
 */
@Stateless
public class TempDayoffServiceImpl implements TempDayoffService {

	/** 暫定代休・休出管理データ */
	@Inject
	private InterimBreakDayoffService interimBreakDayoffService;
	/** 代休・休出残数アルゴリズム */
	@Inject
	private BreakDayOffManagementQuery breakDayoffMngQuery;

	/** （仮対応用）代休 */
	@Override
	public MonthlyDayoffRemainData algorithm(String companyId, String employeeId, YearMonth yearMonth,
			DatePeriod period, ClosureId closureId, ClosureDate closureDate) {
		
		// 月初の代休残数を取得　→　繰越数
		Double carryforwardDays = this.breakDayoffMngQuery.getDayOffRemainOfBeginMonth(companyId, employeeId);
		if (carryforwardDays == null) carryforwardDays = 0.0;
		
		// 暫定データの作成
		this.interimBreakDayoffService.create(companyId, employeeId, period, Optional.empty(), Optional.empty());
		
		// 月度別代休残数集計を取得
		val interimRemAggrOutputs = this.breakDayoffMngQuery.getInterimRemainAggregate(
				employeeId, period.end(), yearMonth, yearMonth);
		
		// 残日数を計算
		Double occurDays = 0.0;
		Double usedDays = 0.0;
		Double remainDays = carryforwardDays;
		if (interimRemAggrOutputs.size() > 0){
			val interimRemAggrOutput = interimRemAggrOutputs.get(0);
			occurDays = interimRemAggrOutput.getMonthOccurrence();
			usedDays = interimRemAggrOutput.getMonthUse();
			if (occurDays == null) occurDays = 0.0;
			if (usedDays == null) usedDays = 0.0;
			remainDays = carryforwardDays + occurDays - usedDays;
		}
		// 暫定代休・休出管理データを削除
		this.interimBreakDayoffService.remove(employeeId, period);
		// 代休月別残数データを返す
		return new MonthlyDayoffRemainData(
				employeeId,
				yearMonth,
				closureId.value,
				closureDate.getClosureDay().v(),
				closureDate.getLastDayOfMonth(),
				ClosureStatus.UNTREATED,
				period.start(),
				period.end(),
				new DayOffDayAndTimes(
						new RemainDataDaysMonth(occurDays),
						Optional.empty()),
				new DayOffDayAndTimes(
						new RemainDataDaysMonth(usedDays),
						Optional.empty()),
				new DayOffRemainDayAndTimes(
						new AttendanceDaysMonthToTal(remainDays),
						Optional.empty()),
				new DayOffRemainDayAndTimes(
						new AttendanceDaysMonthToTal(carryforwardDays),
						Optional.empty()),
				new DayOffDayAndTimes(
						new RemainDataDaysMonth(0.0),
						Optional.empty()));
	}
}
