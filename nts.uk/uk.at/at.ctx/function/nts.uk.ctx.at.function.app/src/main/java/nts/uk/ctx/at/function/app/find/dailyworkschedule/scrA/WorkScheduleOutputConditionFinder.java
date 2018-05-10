package nts.uk.ctx.at.function.app.find.dailyworkschedule.scrA;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleRepository;
import nts.uk.ctx.at.function.dom.dailyworkschedule.scrA.SEmpHistExportAdapter;
import nts.uk.ctx.at.function.dom.dailyworkschedule.scrA.SEmpHistExportImported;
import nts.uk.ctx.at.function.dom.holidaysremaining.PermissionOfEmploymentForm;
import nts.uk.ctx.at.function.dom.holidaysremaining.repository.PermissionOfEmploymentFormRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class WorkScheduleOutputConditionFinder {
	
	@Inject
	private PermissionOfEmploymentFormRepository permissionOfEmploymentFormRepository;
	
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;
	
	@Inject
	private SEmpHistExportAdapter sEmpHistExportAdapter;
	
	@Inject
	private ClosureRepository closureRepository;
	
	@Inject
	private ClosureService closureService;
	
	@Inject
	private OutputItemDailyWorkScheduleRepository outputItemDailyWorkScheduleRepository;
	
	private static final String STRING_EMPTY = "";
	private static final Integer FUNCTION_NO = 2;
	
	public WorkScheduleOutputConditionDto startScr(boolean isExistWorkScheduleOutputCondition, String keyRestoreDomain) {
		
		WorkScheduleOutputConditionDto dto = new WorkScheduleOutputConditionDto();
		
		String companyId = AppContexts.user().companyId();
		String roleId = AppContexts.user().roles().forAttendance();
		int functionNo = FUNCTION_NO;
		GeneralDate systemDate = GeneralDate.today();
		String employeeId = AppContexts.user().employeeId();
		
		// ドメインモデル「就業帳票の権限」を取得する(Acquire the domain model "authority of employment form")
		Optional<PermissionOfEmploymentForm> optPermissionOfEmploymentForm = permissionOfEmploymentFormRepository.find(companyId, roleId, functionNo);
		if (optPermissionOfEmploymentForm.isPresent()) {
			if (!optPermissionOfEmploymentForm.get().isAvailable()) {
				optPermissionOfEmploymentForm = Optional.empty();
			}
		}
		
		// アルゴリズム「社員に対応する締め期間を取得する」を実行する(Execute the algorithm "Acquire closing period corresponding to employee")
		Closure closure = getDomClosure(employeeId, systemDate).get();
//		List<ClosureHistory> lstClosureHistory = closure.getClosureHistories();
		
		// アルゴリズム「当月の期間を算出する」を実行する(Execute the algorithm "Calculate the period of the current month")
//		execute(closure.getClosureId(), closure.getClosureMonth());
		
		DatePeriod datePeriod = closureService.getClosurePeriodNws(closure.getClosureId().value, closure.getClosureMonth().getProcessingYm());
		dto.setStartDate(datePeriod.start());
		dto.setEndDate(datePeriod.end());
		
		// ドメインモデル「日別勤務表の出力項目」をすべて取得する(Acquire all domain model "Output items of daily work schedule")
		List<OutputItemDailyWorkSchedule> lstOutputItemDailyWorkSchedule = outputItemDailyWorkScheduleRepository.findByCid(companyId);
		if (lstOutputItemDailyWorkSchedule.isEmpty()) {
			if (isExistWorkScheduleOutputCondition) {
				dto.setStrReturn(keyRestoreDomain);
			} else {
				dto.setStrReturn(STRING_EMPTY);
			}
		} else {
			// 「就業帳票の権限」が取得できたか ( 「............」 đã được acquire chưa?)
			if (optPermissionOfEmploymentForm.isPresent()) {
				dto.setStrReturn("Open_ScrC");
			} else {
				throw new BusinessException("Msg_1141");
			}
		}
		return dto;
	}
	
	// アルゴリズム「社員に対応する締め期間を取得する」を実行する(Execute the algorithm "Acquire closing period corresponding to employee")
	private Optional<Closure> getDomClosure(String employeeId, GeneralDate systemDate) {
		
		String companyId = AppContexts.user().companyId();
		
		// Imported「（就業）所属雇用履歴」を取得する (Lấy Imported「（就業）所属雇用履歴」)
		Optional<SEmpHistExportImported> optSEmpHistExportImported = sEmpHistExportAdapter.getSEmpHistExport(companyId, employeeId, systemDate);
		if (!optSEmpHistExportImported.isPresent()) {
			return Optional.empty();
		}
		SEmpHistExportImported sEmpHistExportImported = optSEmpHistExportImported.get();
		
		// Get domain 対応するドメインモデル「雇用に紐づく就業締め」を取得する (Lấy về domain model "Thuê" tương ứng)
		Optional<ClosureEmployment> optClosureEmployment = closureEmploymentRepository.findByEmploymentCD(companyId, sEmpHistExportImported.getEmploymentCode());
		
		// Get domain 対応するドメインモデル「締め」を取得する (Lấy về domain model "Hạn định" tương ứng)
		return closureRepository.findById(companyId, optClosureEmployment.get().getClosureId());
	}
	
	// アルゴリズム「当月の期間を算出する」を実行する(Execute the algorithm "Calculate the period of the current month")
	/*private DatePeriod execute(ClosureId closureId, CurrentMonth currentMonth) {
		String companyId = AppContexts.user().companyId();
		
		ClosureHistory closureHistory = closureRepository.findByClosureIdAndCurrentMonth(companyId, closureId.value, currentMonth.getProcessingYm().month()).get();

		GeneralDate startDate;
		GeneralDate endDate;
		
		// 締め変更履歴と当月をチェックする Check closing change history and current month
		if (currentMonth.getProcessingYm().equals(closureHistory.getStartYearMonth())) {
			
			ClosureHistory closureHistoryMinus = closureRepository.findByClosureIdAndCurrentMonth(companyId, closureId.value, currentMonth.getProcessingYm().month()-1).get();
			// アルゴリズム「締め日変更時の期間を算出」を実行する
			return calculatePeriodWhenChangingClosingDate(closureHistory, closureHistoryMinus, currentMonth);
		} else {
			// check is the last day of month
			if (closureHistory.getClosureDate().getLastDayOfMonth()) {
				YearMonth yearMonth = currentMonth.getProcessingYm();
				startDate = GeneralDate.ymd(yearMonth.year(), yearMonth.month(), 1);
				endDate = GeneralDate.ymd(yearMonth.year(), yearMonth.month(), closureHistory.getClosureDate().getClosureDay().v());
				return new DatePeriod(startDate, endDate);
			} else {
				// thực hiện xử lý 「日付の存在チェック」
				startDate = checkExistDate(currentMonth.getProcessingYm().previousMonth(), closureHistory.getClosureDate().getClosureDay().v()+1);
				endDate = checkExistDate(currentMonth.getProcessingYm(), closureHistory.getClosureDate().getClosureDay().v());
				return new DatePeriod(startDate, endDate);
			}
		}
	}*/

	/*private DatePeriod calculatePeriodWhenChangingClosingDate(ClosureHistory closureHistory, ClosureHistory closureHistoryMinus, CurrentMonth currentMonth) {
		
		// current month - 1 [当月-1]
		YearMonth yearMonthMinus = currentMonth.getProcessingYm().previousMonth();
		GeneralDate startDate;
		GeneralDate endDate;
		
		// 「当月」の締め変更履歴．締め日と「当月-1」の締め変更履歴を比較する 
		if (closureHistory.getClosureDate().getClosureDay().v() <= closureHistoryMinus.getClosureDate().getClosureDay().v()) {
			// アルゴリズム「日付の存在チェック」を実行する(thực hiện xử lý 「日付の存在チェック」)
			if (closureHistory.getClosureDate().getLastDayOfMonth() 
					|| isExistDayInMonth(currentMonth.getProcessingYm(), closureHistory.getClosureDate().getClosureDay().v())) {
				startDate = checkExistDate(currentMonth.getProcessingYm(), 1);
			} else {
				startDate = checkExistDate(yearMonthMinus, closureHistory.getClosureDate().getClosureDay().v() + 1);
			}
			
			// アルゴリズム「日付の存在チェック」を実行する(thực hiện xử lý 「日付の存在チェック」)
			endDate = checkExistDate(currentMonth.getProcessingYm(), closureHistory.getClosureDate().getClosureDay().v());
			return new DatePeriod(startDate, endDate);
		} else {
			// 締め日変更前期間 Period before closing date change
			if (currentMonth.getClosureClassification().get().value == ClosureClassification.ClassificationClosingBefore.value) {
				startDate = GeneralDate.ymd(2018, 1, 1);
				endDate = GeneralDate.ymd(2018, 1, 1);
			} 
			// 締め日変更後期間 Period after closing date change
			else {
				startDate = GeneralDate.ymd(2018, 1, 1);
				endDate = GeneralDate.ymd(2018, 1, 1);
			}
			return new DatePeriod(startDate, endDate);
		}
	}*/
	
	/*private GeneralDate checkExistDate(YearMonth yearMonth, Integer day) {
		int year = yearMonth.year();
		int month = yearMonth.month();
		
        if (isExistDayInMonth(yearMonth, day)) {
        	return GeneralDate.ymd(year, month, day);
        } else {
        	return GeneralDate.ymd(year, month, getNumberDaysOfMonth(yearMonth));
        }
	}*/
	
	/*private boolean isExistDayInMonth(YearMonth yearMonth, int day) {
		Calendar calendar = Calendar.getInstance();
        int year = yearMonth.year();
        int month = yearMonth.month()-1; // Janaury is 0
        int date = 1;
        calendar.set(year, month, date);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (day <= maxDay) {
        	return true;
        } else {
        	return false;
        }
	}*/
	
	/*private int getNumberDaysOfMonth(YearMonth yearMonth) {
		Calendar calendar = Calendar.getInstance();
        int year = yearMonth.year();
        int month = yearMonth.month()-1; // Janaury is 0
        int date = 1;
        calendar.set(year, month, date);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return maxDay;
	}*/
}
