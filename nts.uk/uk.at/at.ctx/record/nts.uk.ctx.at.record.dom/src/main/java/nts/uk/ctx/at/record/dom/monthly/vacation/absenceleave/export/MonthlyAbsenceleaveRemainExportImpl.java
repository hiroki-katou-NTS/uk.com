package nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.export;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.AbsenceLeaveRemainData;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.AbsenceLeaveRemainDataRepository;
@Stateless
public class MonthlyAbsenceleaveRemainExportImpl implements MonthlyAbsenceleaveRemainExport{
	@Inject
	private AbsenceLeaveRemainDataRepository absenceLeaveRepos;
	@Override
	public List<AbsenceleaveCurrentMonthOfEmployee> getDataCurrentMonthOfEmployee(String employeeId, YearMonth startMonth, YearMonth endMonth) {
		List<AbsenceleaveCurrentMonthOfEmployee> lstOutputData = new ArrayList<AbsenceleaveCurrentMonthOfEmployee>();
		for(YearMonth ym = startMonth; ym.lessThanOrEqualTo(endMonth); ym = ym.addMonths(1)) {
			//ドメインモデル「振休月別残数データ」を取得
			List<AbsenceLeaveRemainData> lstAbsenData = absenceLeaveRepos.findByYearMonthOrderByStartYmd(employeeId, ym);
			if(lstAbsenData.isEmpty()) {
				continue;
			}
			AbsenceleaveCurrentMonthOfEmployee dataOutput = new AbsenceleaveCurrentMonthOfEmployee(employeeId, ym, (double)0, (double)0, (double)0, (double)0, (double)0);
			GeneralDate endDateRemainingMax = GeneralDate.ymd(ym.year(), ym.month(), 1);
			GeneralDate endDatecarryMax = GeneralDate.ymd(ym.year(), ym.month(), 1);
			//同じ属性同士の値を合算
			for (AbsenceLeaveRemainData data : lstAbsenData) {
				//残数は締め期間．終了日が遅い方だけ返し、
				if(data.getEndDate().afterOrEquals(endDateRemainingMax)) {
					endDateRemainingMax = data.getEndDate();
					dataOutput.setRemainingDays(data.getRemainingDays().v());
				}
				//繰越数は、締め期間．終了日が早い方だけ返します。
				if(data.getEndDate().before(endDatecarryMax)) {
					endDatecarryMax = data.getEndDate();
					dataOutput.setCarryforwardDays(data.getCarryforwardDays().v());
				}
				dataOutput.setOccurredDay(dataOutput.getOccurredDay() + data.getOccurredDay().v());
				dataOutput.setUsedDays(dataOutput.getUsedDays() + data.getUsedDays().v());
				dataOutput.setUnUsedDays(dataOutput.getUnUsedDays() + data.getUnUsedDays().v());
			}
			lstOutputData.add(dataOutput);
		}
		return lstOutputData;
	}

}
