package nts.uk.ctx.at.record.dom.remainingnumber.absenceleave.temp;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.AbsenceLeaveRemainData;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.AttendanceDaysMonthToTal;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.RemainDataDaysMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentManaQuery;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 実装：（仮対応用）振休
 * @author shuichu_ishida
 */
@Stateless
public class TempAbsenceLeaveServiceImpl implements TempAbsenceLeaveService {

	/** 暫定振休・振出管理データ */
	@Inject
	private InterimAbsenceRecruitService interimAbsRecService;
	/** 振休・振出残数アルゴリズム */
	@Inject
	private AbsenceReruitmentManaQuery absRecMngQuery;
	
	/** （仮対応用）振休 */
	@Override
	public AbsenceLeaveRemainData algorithm(String companyId, String employeeId, YearMonth yearMonth,
			DatePeriod period, ClosureId closureId, ClosureDate closureDate) {
		
		
		
		// 月初の振休残数を取得　→　繰越数
		Double carryforwardDays = this.absRecMngQuery.useDays(employeeId);
		if (carryforwardDays == null) carryforwardDays = 0.0;
		
		// 暫定データの作成
		this.interimAbsRecService.create(companyId, employeeId, period, Optional.empty());
		
		// 月度別振休残数集計を取得
		val interimRemAggrOutputs = this.absRecMngQuery.getAbsRecRemainAggregate(
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
		// 暫定振休・振出管理データを削除
		this.interimAbsRecService.remove(employeeId, period);
		// 振休月別残数データを返す
		return new AbsenceLeaveRemainData(
				employeeId,
				yearMonth,
				closureId.value,
				closureDate.getClosureDay().v(),
				closureDate.getLastDayOfMonth(),
				ClosureStatus.UNTREATED,
				period.start(),
				period.end(),
				new RemainDataDaysMonth(occurDays),
				new RemainDataDaysMonth(usedDays),
				new AttendanceDaysMonthToTal(remainDays),
				new AttendanceDaysMonthToTal(carryforwardDays),
				new RemainDataDaysMonth(0.0));
	}
}
