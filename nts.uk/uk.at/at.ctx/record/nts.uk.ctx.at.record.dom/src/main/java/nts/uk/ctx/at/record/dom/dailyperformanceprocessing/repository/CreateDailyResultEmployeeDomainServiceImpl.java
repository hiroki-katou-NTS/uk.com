package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.organization.EmploymentHistoryImported;
import nts.uk.ctx.at.record.dom.organization.adapter.EmploymentAdapter;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLock;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.CurrentMonth;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class CreateDailyResultEmployeeDomainServiceImpl implements CreateDailyResultEmployeeDomainService {

	@Inject
	private ClosureRepository closureRepository;

	@Inject
	private ActualLockRepository actualLockRepository;

	@Inject
	private EmploymentAdapter employmentAdapter;

	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;

	@Override
	public int createDailyResultEmployee(List<String> employeeIds, DatePeriod periodTime, int reCreateAttr,
			String empCalAndSumExecLogID) {

		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		/**
		 * 正常終了 : 0
		 */
		int endStatus = 0;

		// int days = endDate.day() - startDate.day();
		// GeneralDate processingDate = startDate;
		//
		// // lits day between startDate and endDate
		// List<GeneralDate> listDay = this.getDaysBetween(startDate, endDate);

		// Imported（就業）「所属雇用履歴」を取得する
		// TODO - waiting request list
		// param : List employeeIds, startDate, endDate
		// List<EmploymentHistoryImported> employmentHis =
		// this.employmentAdapter.getEmpHistBySid(companyId,
		// employeeIds, processingDate);

		List<EmploymentHistoryImported> employmentHis = new ArrayList<>();
		// Map<String, String> employmentCodeMaps =
		// employmentHis.stream().collect(Collectors.toMap(EmploymentHistoryImported::getEmployeeId,
		// EmploymentHistoryImported::getEmploymentCode));
		List<String> emloymentCodes = employmentHis.stream().map(f -> {
			return f.getEmploymentCode();
		}).collect(Collectors.toList());

		// 締めIDを取得する
		Map<String, Integer> closureEmployments = this.closureEmploymentRepository
				.findListEmployment(companyId, emloymentCodes).stream()
				.collect(Collectors.toMap(ClosureEmployment::getEmploymentCD, x -> x.getClosureId()));

		// employeeID map with employmentCode and date period
		List<EmployeeAndClosure> employeeAndClosures = new ArrayList<>();
		Map<String, List<EmploymentHistoryImported>> employmentCodeMaps = employmentHis.stream()
				.collect(Collectors.groupingBy(item -> item.getEmployeeId()));
		employmentCodeMaps.forEach((key, values) -> {
			values.forEach(items -> {
				if (closureEmployments.containsKey(items.getEmploymentCode())) {
					EmployeeAndClosure employeeAndClosure = new EmployeeAndClosure(key,
							closureEmployments.get(items.getEmploymentCode()), items.getPeriod());
					employeeAndClosures.add(employeeAndClosure);
				}
			});
		});

		// アルゴリズム「実績ロックされているか判定する」を実行する
		// TODO
		// this.determineActualLocked(companyId, employeeAndClosures);

		return endStatus;
	}

	/**
	 * 実績ロックされているか判定する
	 * 
	 * @param companyId
	 * @param employeeId
	 * @param periodTime
	 * @param closureIds
	 * @return
	 */
	private List<Integer> determineActualLocked(String companyId, List<EmployeeAndClosure> employeeAndClosures) {

		/**
		 * ロック : 0 , アンロック : 1
		 */
		List<Integer> locks = new ArrayList<>();

		// アルゴリズム「当月の期間を算出する」を実行する

		/**
		 * アルゴリズム「当月の実績ロックの取得」を実行する
		 */
		List<Integer> closureIds = employeeAndClosures.stream().map(f -> {return f.getClosureId();}).collect(Collectors.toList());
		// 全てのドメインモデル「当月の実績ロック」を取得する
		List<ActualLock> actualLockLists = this.actualLockRepository.findByListId(companyId, closureIds);
		//実績ロックされているかチェックする
		List<ActualLock> closureIdLockMaps = actualLockLists.stream()
				.filter(item -> item.getDailyLockState().value == 0).collect(Collectors.toList());
		
		List<Integer> closureIdLocks = closureIdLockMaps.stream().map(f -> {return f.getClosureId().value;}).collect(Collectors.toList());
		
		// ドメインモデル「締め」を取得する
		List<Closure> listClosures = this.closureRepository.findByListId(companyId,closureIdLocks);
		
		// アルゴリズム「当月の期間を算出する」を実行する	
		// closureId map with currentmonth
		Map<Integer, Integer> currentMonthMap =  listClosures.stream().collect(Collectors.toMap(Closure::getClosureId, x -> x.getClosureMonth().getProcessingYm().v()));
		// ドメインモデル「締め変更履歴」取得
		this.closureRepository.

		

		Map<Integer, Integer> closureIdLockOrNotMap = new HashMap<>();

		// actualLockLists.stream().filter(f -> {
		// f.getClosureId()
		// })

		// /**
		// * アルゴリズム「特定の日付の締めを取得する」を実行する
		// */
		// TODO fake
		// 期間．開始日を処理日にする (Ngày xử lý = Ngày bắt đầu)
		GeneralDate processingDate = GeneralDate.today();
		// TODO fake
		List<String> employmentCDs = new ArrayList<>();

		// list actual lock has un-locked
		List<ActualLock> unlockActualLockList = actualLockLists.stream().filter(f -> f.getDailyLockState().value == 0)
				.collect(Collectors.toList());
		// Map<ClosureId, ActualLock> unlockedActualLockMap =
		// actualLockLists.stream()
		// .filter(f -> f.getDailyLockState().value == 0)
		// .collect(Collectors.toMap(ActualLock::getClosureId, x -> x));

		// list actual lock has locked
		List<ActualLock> lockedActualLockList = actualLockLists.stream().filter(f -> f.getDailyLockState().value == 1)
				.collect(Collectors.toList());
		// Map<ClosureId, ActualLock> lockedActualLockMap =
		// actualLockLists.stream()
		// .filter(f -> f.getDailyLockState().value == 1)
		// .collect(Collectors.toMap(ActualLock::getClosureId, x -> x));

		/**
		 * アルゴリズム「当月の期間を算出する」を実行する
		 */
		// ドメインモデル「締め変更履歴」取得
		List<Integer> closeIdLocked = lockedActualLockList.stream().map(f -> {
			return f.getClosureId().value;
		}).collect(Collectors.toList());
		List<Integer> currentMonthLocked = listClosures.stream().map(f -> {
			return f.getClosureMonth().getProcessingYm().v();
		}).collect(Collectors.toList());
		Collections.sort(currentMonthLocked);

		List<ClosureHistory> closureHistories = new ArrayList<>();
		// = this.closureRepository.findByListClouseId(companyId,
		// closeIdLocked, currentMonthLocked.get(0),
		// currentMonthLocked.get(currentMonthLocked.size() - 1));

		if (!closureHistories.isEmpty()) {
			Map<ClosureId, ClosureDate> closureDayMap = closureHistories.stream()
					.collect(Collectors.toMap(ClosureHistory::getClosureId, x -> x.getClosureDate()));

			Map<ClosureId, YearMonth> startYearMonthMap = closureHistories.stream()
					.collect(Collectors.toMap(ClosureHistory::getClosureId, x -> x.getStartYearMonth()));

			Map<ClosureId, YearMonth> endYearMonthMap = closureHistories.stream()
					.collect(Collectors.toMap(ClosureHistory::getClosureId, x -> x.getEndYearMonth()));

			closeIdLocked.forEach(f -> {
				// if (closureDayMap.get(f).getLastDayOfMonth() ||
				// !DateUtil.isDateOfMonth(year, month, dayOfMonth)) {
				//
				// }
			});
		}

		return locks;
	}

	private List<GeneralDate> getDaysBetween(GeneralDate startDate, GeneralDate endDate) {
		List<GeneralDate> daysBetween = new ArrayList<>();

		while (startDate.before(endDate)) {
			daysBetween.add(startDate);
			GeneralDate temp = startDate.addDays(1);
			startDate = temp;
		}

		return daysBetween;
	}

}
