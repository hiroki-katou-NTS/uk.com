package nts.uk.ctx.at.record.pubimp.monthly.vacation.reserveleave;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.RsvLeaRemNumEachMonthRepository;
import nts.uk.ctx.at.record.pub.monthly.vacation.reserveleave.GetConfirmedReserveLeave;
import nts.uk.ctx.at.record.pub.monthly.vacation.reserveleave.ReserveLeaveUsageExport;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveUsedDayNumber;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

/**
 * 実装：社員の月毎の確定済み年休を取得する
 * @author shuichu_ishida
 */
@Stateless
public class GetConfirmedReserveLeaveImpl implements GetConfirmedReserveLeave {

	/** 積立年休月別残数データ */
	@Inject
	private RsvLeaRemNumEachMonthRepository rsvLeaRemNumEachMonthRepo;
	
	/** 社員の月毎の確定済み積立年休を取得する */
	@Override
	public List<ReserveLeaveUsageExport> algorithm(String employeeId, YearMonthPeriod period) {
		
		// 年月期間を取得
		val yearMonths = ConvertHelper.yearMonthsBetween(period);

		// 「積立年休月別残数データ」を取得
		List<String> employeeIds = new ArrayList<>();
		employeeIds.add(employeeId);
		val datas = this.rsvLeaRemNumEachMonthRepo.findBySidsAndYearMonths(employeeIds, yearMonths);

		Map<YearMonth, ReserveLeaveUsageExport> results = new HashMap<>();
		Map<YearMonth, GeneralDate> saveDates = new HashMap<>();
		for (val data : datas){
			
			// 「締め済」でないデータは、除く
			//if (data.getClosureStatus() != ClosureStatus.PROCESSED) continue;
			
			val yearMonth = data.getYearMonth();
			val reserveLeave = data.getReserveLeave();
			val usedNumber = reserveLeave.getUsedNumber();
			val remNumber = reserveLeave.getRemainingNumber();
			
			ReserveLeaveUsedDayNumber usedDays =
					new ReserveLeaveUsedDayNumber(usedNumber.getUsedDays().v());
			ReserveLeaveRemainingDayNumber remainingDays =
					new ReserveLeaveRemainingDayNumber(remNumber.getTotalRemainingDays().v());
			
			// 同じ年月が複数ある時、合算する
			if (results.containsKey(yearMonth)){
				val oldResult = results.get(yearMonth);
				val oldDate = saveDates.get(yearMonth);
				
				usedDays = new ReserveLeaveUsedDayNumber(usedDays.v() + oldResult.getUsedDays().v());
				
				// 積立年休残数に限り、締め期間．終了日の遅い方を保持する
				if (data.getClosurePeriod().end().before(oldDate)){
					remainingDays = new ReserveLeaveRemainingDayNumber(oldResult.getRemainingDays().v());
				}
				else {
					saveDates.put(yearMonth, data.getClosurePeriod().end());
				}
			}
			
			results.put(yearMonth, new ReserveLeaveUsageExport(
					yearMonth,
					usedDays,
					remainingDays));
			
			saveDates.putIfAbsent(yearMonth, data.getClosurePeriod().end());
		}
		
		// 積立年休月別残数データリストを返す
		val resultList = results.values().stream().collect(Collectors.toList());
		resultList.sort((a, b) -> a.getYearMonth().compareTo(b.getYearMonth()));
		return resultList;
	}
}
