package nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.export;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata.MonthlyDayoffRemainData;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata.MonthlyDayoffRemainDataRepository;
@Stateless
public class MonthlyDayoffRemainExportImpl implements MonthlyDayoffRemainExport{
	@Inject
	private MonthlyDayoffRemainDataRepository remainDataRepos;
	@Override
	public List<DayoffCurrentMonthOfEmployee> lstDayoffCurrentMonthOfEmployee(String employeeId, YearMonth startMonth,
			YearMonth endMonth) {
		List<DayoffCurrentMonthOfEmployee> lstOutput = new ArrayList<DayoffCurrentMonthOfEmployee>();
		//年月期間．開始年月から終了年月まで1か月ずつループ
		for (YearMonth ym = startMonth; ym.greaterThanOrEqualTo(endMonth); ym.addMonths(1)) {			
			//ドメインモデル「代休月別残数データ」を取得
			List<MonthlyDayoffRemainData> getDayOffDataBySidYmStatus = remainDataRepos.getDayOffDataBySidYmStatus(employeeId, ym, ClosureStatus.PROCESSED);
			if(getDayOffDataBySidYmStatus.isEmpty()) {
				continue;
			}
			DayoffCurrentMonthOfEmployee dataOutput = new DayoffCurrentMonthOfEmployee(employeeId, ym, null, null, null, null, null, null, null, null, null, null);
			GeneralDate endDateRemainingMax = GeneralDate.ymd(ym.year(), ym.month(), 1);
			GeneralDate endDatecarryMax = GeneralDate.ymd(ym.year(), ym.month(), 1);
			for (MonthlyDayoffRemainData data : getDayOffDataBySidYmStatus) {
				//残数は締め期間．終了日が遅い方だけ返し、
				if(data.getEndDate().afterOrEquals(endDateRemainingMax)) {
					endDateRemainingMax = data.getEndDate();
					dataOutput.setRemainingDays(data.getRemainingDays().v());
					dataOutput.setRemainingTimes(data.getRemainingTimes().v());
				}
				//繰越数は、締め期間．終了日が早い方だけ返します。
				if(data.getEndDate().after(endDatecarryMax)) {
					endDatecarryMax = data.getEndDate();
					dataOutput.setCarryForWardDays(data.getCarryForWardDays().v());
					dataOutput.setCarryForWordTimes(data.getCarryForWordTimes().v());
				}
				dataOutput.setOccurrenceDays(dataOutput.getOccurrenceDays() + data.getOccurrenceDays().v());
				dataOutput.setOccurrenceTimes(dataOutput.getOccurrenceTimes() + data.getOccurrenceTimes().v());
				dataOutput.setUseDays(dataOutput.getUseDays() + data.getUseDays().v());
				dataOutput.setUseTimes(dataOutput.getUseTimes() + data.getUseTimes().v());
				dataOutput.setUnUsedDays(dataOutput.getUnUsedDays() + data.getUnUsedDays().v());
				dataOutput.setUnUsedTimes(dataOutput.getUnUsedTimes() + data.getUnUsedTimes().v());
			}
			lstOutput.add(dataOutput);
		}
		return lstOutput;
	}

}
