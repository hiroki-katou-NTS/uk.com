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
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
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

	@Inject
	private ClosureService closureService;

	@Override
	public List<ClosureIdLockDto> createDailyResultEmployee(List<String> employeeIds, DatePeriod periodTime, int reCreateAttr,
			String empCalAndSumExecLogID) {

		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		// int days = endDate.day() - startDate.day();
		// GeneralDate processingDate = startDate;

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
		List<ClosureIdLockDto> closureIdLockDtos = this.determineActualLocked(companyId, employeeAndClosures, periodTime);

		return closureIdLockDtos;
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
	private List<ClosureIdLockDto> determineActualLocked(String companyId, List<EmployeeAndClosure> employeeAndClosures, DatePeriod periodTime) {

		/**
		 * ロック : 0 , アンロック : 1
		 */
		List<ClosureIdLockDto> locks = new ArrayList<>();

		// lits day between startDate and endDate
		List<GeneralDate> listDay = this.getDaysBetween(periodTime.start(), periodTime.end());

		// アルゴリズム「当月の期間を算出する」を実行する

		/**
		 * アルゴリズム「当月の実績ロックの取得」を実行する
		 */
		List<Integer> closureIds = employeeAndClosures.stream().map(f -> {
			return f.getClosureId();
		}).collect(Collectors.toList());
		// 全てのドメインモデル「当月の実績ロック」を取得する
		List<ActualLock> actualLockLists = this.actualLockRepository.findByListId(companyId, closureIds);

		List<ActualLock> closureIdUnLockMaps = actualLockLists.stream()
				.filter(item -> item.getDailyLockState().value == 0).collect(Collectors.toList());

		closureIdUnLockMaps.forEach(f -> {
			ClosureIdLockDto lockDto = new ClosureIdLockDto(f.getClosureId().value, 0);
			locks.add(lockDto);
		});

		// 実績ロックされているかチェックする
		List<ActualLock> closureIdLockMaps = actualLockLists.stream()
				.filter(item -> item.getDailyLockState().value == 1).collect(Collectors.toList());

		List<Integer> closureIdLocks = closureIdLockMaps.stream().map(f -> {
			return f.getClosureId().value;
		}).collect(Collectors.toList());

		// ドメインモデル「締め」を取得する
		List<Closure> listClosures = this.closureRepository.findByListId(companyId, closureIdLocks);

		// アルゴリズム「当月の期間を算出する」を実行する
		// closureId map with DatePeriod
		Map<Integer, DatePeriod> DatePeriodMap = new HashMap<>();
		// closureId map with current month
		Map<Integer, YearMonth> currentMonthMap = listClosures.stream()
				.collect(Collectors.toMap(Closure::getClosureId, x -> x.getClosureMonth().getProcessingYm()));
		currentMonthMap.forEach((key, value) -> {
			DatePeriod closurePeriod = this.closureService.getClosurePeriod(key, value);
			DatePeriodMap.put(key, closurePeriod);
		});

		// 基準日が当月かチェックする
		DatePeriodMap.forEach((key, value) -> {
			listDay.forEach(f -> {
				if (f.afterOrEquals(value.start()) && f.beforeOrEquals(value.end())) {
					ClosureIdLockDto lockDto = new ClosureIdLockDto(key, 1);
					locks.add(lockDto);
				} else {
					ClosureIdLockDto lockDto = new ClosureIdLockDto(key, 0);
					locks.add(lockDto);
				}
				;
			});
		});

		return locks;
	}

	private List<GeneralDate> getDaysBetween(GeneralDate startDate, GeneralDate endDate) {
		List<GeneralDate> daysBetween = new ArrayList<>();

		while (startDate.beforeOrEquals(endDate)) {
			daysBetween.add(startDate);
			GeneralDate temp = startDate.addDays(1);
			startDate = temp;
		}

		return daysBetween;
	}

}
