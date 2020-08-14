package nts.uk.ctx.at.record.dom.workrecord.stampmanagement;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workplace.SWkpHistRcImported;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.ConfirmStatusActualResult;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.ReleasedAtr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.confirm.DailyLock;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.confirm.StatusActualDay;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;

/**
 * DS : 日の実績の確認状況を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.実績の状況管理.日の実績の確認状況を取得する
 * @author tutk
 *
 */
public class ConfirmStatusOfDayService {
	
	/**
	 *  取得する
	 * @param require
	 * @param companyId
	 * @param employeeId
	 * @param date
	 * @return
	 */
	public static ConfirmStatusActualResult get(Require require,String companyId,String employeeId,GeneralDate baseDate) {
		Closure closure = require.getClosureDataByEmployee(employeeId, baseDate);
		if(closure == null) {
			return new ConfirmStatusActualResult(employeeId, baseDate, false, ReleasedAtr.CAN_NOT_IMPLEMENT, ReleasedAtr.CAN_NOT_IMPLEMENT);
		}
		Optional<SWkpHistRcImported> optSWkpHistRcImported = require.findBySid( employeeId, baseDate);
		if(!optSWkpHistRcImported.isPresent()) {
			throw new BusinessException("Msg_427");
		}
		StatusActualDay statusActualDay = new StatusActualDay(employeeId, baseDate, optSWkpHistRcImported.get().getWorkplaceId(), closure.getClosureId().value);
		DailyLock dailyLock = require.getDailyLock(statusActualDay);
		List<ConfirmStatusActualResult> data = require.processConfirmStatus(companyId, employeeId,
				Arrays.asList(employeeId), Optional.of(new DatePeriod(baseDate, baseDate)), Optional.empty(),
				Optional.of(dailyLock));
		if(data.isEmpty()) {
			return new ConfirmStatusActualResult();
		}
		return data.get(0);
	}
	
	
	public static interface Require {
		/**
		 * 	[R-1] 社員の締めを取得する   ClosureService
		 * @param employeeId
		 * @param baseDate
		 * @return
		 */
		Closure getClosureDataByEmployee(String employeeId, GeneralDate baseDate);
		
		/**
		 * [R-2] 社員所属職場履歴を取得   SyWorkplaceAdapter
		 * @param employeeId
		 * @param baseDate
		 * @return
		 */
		Optional<SWkpHistRcImported> findBySid(String employeeId, GeneralDate baseDate);
		
		/**
		 * [R-3] 日の実績の状況を取得する   IGetDailyLock
		 * @param satusActual
		 * @return
		 */
		DailyLock getDailyLock(StatusActualDay satusActual);
		
		/**
		 * [R-4] 日の実績の確認状況を取得する   ConfirmStatusActualDayChange
		 * @param companyId
		 * @param empTarget
		 * @param employeeIds
		 * @param periodOpt
		 * @param yearMonthOpt
		 * @return
		 */
		List<ConfirmStatusActualResult> processConfirmStatus(String companyId, String empTarget,
				List<String> employeeIds, Optional<DatePeriod> periodOpt, Optional<YearMonth> yearMonthOpt,
				Optional<DailyLock> dailyLockOpt);
	}
}
