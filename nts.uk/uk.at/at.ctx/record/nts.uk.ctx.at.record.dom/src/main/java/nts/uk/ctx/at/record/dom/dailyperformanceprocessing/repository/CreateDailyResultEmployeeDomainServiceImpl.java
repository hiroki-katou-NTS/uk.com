package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultDomainServiceImpl.ProcessState;
import nts.uk.ctx.at.record.dom.organization.EmploymentHistoryImported;
import nts.uk.ctx.at.record.dom.organization.adapter.EmploymentAdapter;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLock;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.DailyRecreateClassification;
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

	@Inject
	private ReflectWorkInforDomainService reflectWorkInforDomainService;

	@Override
	public ProcessState createDailyResultEmployee(AsyncCommandHandlerContext asyncContext, String employeeId,
			DatePeriod periodTime, String companyId, String empCalAndSumExecLogID, DailyRecreateClassification reCreateAttr) {
				
		// 正常終了 : 0
		// 中断 : 1
		ProcessState status = ProcessState.SUCCESS;

		GeneralDate processingDate = periodTime.start();

		// lits day between startDate and endDate
		List<GeneralDate> listDayBetween = this.getDaysBetween(periodTime.start(), periodTime.end());

		// Imported（就業）「所属雇用履歴」を取得する
		Optional<EmploymentHistoryImported> employmentHisOptional = this.employmentAdapter.getEmpHistBySid(companyId, employeeId, processingDate);
//		Optional<EmploymentHistoryImported> employmentHisOptional = this.employmentAdapter.getEmpHistBySid(companyId, "90000000-0000-0000-0000-000000000001", processingDate);
		String employmentCode = employmentHisOptional.get().getEmploymentCode();

		for (GeneralDate day : listDayBetween) {
			
			// 締めIDを取得する
			Optional<ClosureEmployment> closureEmploymentOptional = this.closureEmploymentRepository
					.findByEmploymentCD(companyId, employmentCode);

			if (day.afterOrEquals(employmentHisOptional.get().getPeriod().end())
					&& day.beforeOrEquals(employmentHisOptional.get().getPeriod().start())) {
				status = ProcessState.SUCCESS;
			} else {
				EmployeeAndClosure employeeAndClosureDto = new EmployeeAndClosure();
				if (employmentHisOptional.get().getEmploymentCode().equals(closureEmploymentOptional.get()
						.getEmploymentCD())) {
					employeeAndClosureDto.setClosureId(closureEmploymentOptional.get().getClosureId());
					employeeAndClosureDto.setEmployeeId(employeeId);
					employeeAndClosureDto.setPeriod(employmentHisOptional.get().getPeriod());
				}

				// アルゴリズム「実績ロックされているか判定する」を実行する
				EmployeeAndClosure employeeAndClosure = this.determineActualLocked(companyId, employeeAndClosureDto,
						day);

				if (employeeAndClosure.getLock() == 0) {
					this.reflectWorkInforDomainService.reflectWorkInformation(companyId, employeeId, day,
							empCalAndSumExecLogID, reCreateAttr);	
				} 
				if (asyncContext.hasBeenRequestedToCancel()) {
					asyncContext.finishedAsCancelled();
					status = ProcessState.INTERRUPTION;
					break;
				}
			}
//			processingDate = day;
		};

		// // 締めIDを取得する
		// Map<String, Integer> closureEmployments =
		// this.closureEmploymentRepository
		// .findListEmployment(companyId, emloymentCodes).stream()
		// .collect(Collectors.toMap(ClosureEmployment::getEmploymentCD, x ->
		// x.getClosureId()));
		//
		// // employeeID map with employmentCode and date period
		// List<EmployeeAndClosure> employeeAndClosures = new ArrayList<>();
		// Map<String, List<EmploymentHistoryImported>> employmentCodeMaps =
		// employmentHis.stream()
		// .collect(Collectors.groupingBy(item -> item.getEmployeeId()));
		// employmentCodeMaps.forEach((key, values) -> {
		// values.forEach(items -> {
		//
		// if (closureEmployments.containsKey(items.getEmploymentCode())) {
		// EmployeeAndClosure employeeAndClosure = new EmployeeAndClosure();
		// employeeAndClosure.setEmployeeId(key);
		// employeeAndClosure.setClosureId(closureEmployments.get(items.getEmploymentCode()));
		// employeeAndClosure.setPeriod(items.getPeriod());
		// employeeAndClosures.add(employeeAndClosure);
		// }
		// });
		// });
		//
		// List<String> employeeIdCanBeExecute = new ArrayList<>();
		//
		// employmentCodeMaps.forEach((key, value) -> {
		// listDayBetween.forEach(item -> {
		// value.forEach(emp -> {
		// if (item.afterOrEquals(emp.getPeriod().start()) &&
		// item.beforeOrEquals(emp.getPeriod().end())) {
		// EmployeeIdClosureIdStatusOutput employeeIdClosureIdStatusOutput = new
		// EmployeeIdClosureIdStatusOutput();
		// employeeIdClosureIdStatusOutput.setEmployeeId(key);
		// employeeIdClosureIdStatusOutput.setState(0);
		// statusOutput.add(employeeIdClosureIdStatusOutput);
		// } else {
		// employeeIdCanBeExecute.add(key);
		// }
		// });
		// });
		// });
		//
		// if (!employeeIdCanBeExecute.isEmpty()) {
		//
		// List<EmployeeAndClosure> employeeAndClosureCanBeExecute =
		// employeeAndClosures.stream()
		// .filter(item ->
		// employeeIdCanBeExecute.contains(item.getEmployeeId())).collect(Collectors.toList());

		// アルゴリズム「実績ロックされているか判定する」を実行する
		// List<EmployeeAndClosure> employeeAndCloseLists =
		// this.determineActualLocked(companyId, employeeAndClosureCanBeExecute,
		// periodTime);

		// List<String> employeeIdUnLocks =
		// employeeAndCloseLists.stream().filter(item -> item.getLock() == 0)
		// .map(ite -> ite.getEmployeeId()).collect(Collectors.toList());

		// 日別実績を作成する
		// ⑥１日の日別実績の作成処理
		// this.reflectWorkInforDomainService.reflectWorkInformation(companyId,
		// employeeIdUnLocks, periodTime,
		// empCalAndSumExecLogID, reCreateAttr);

		// 中断依頼が出されているかチェックする
		//  - interruption in E screen
		// status = 1;
		// EmployeeIdClosureIdStatusOutput statusInterruption = new
		// EmployeeIdClosureIdStatusOutput();
		// statusInterruption.setEmployeeId(employeeId);
		// statusInterruption.setState(1);
		// statusOutput.add(statusInterruption);
		// }

		return status;
	}

	/**
	 * 実績ロックされているか判定する
	 * 
	 * @param companyId
	 * @param employeeId
	 * @param processingDate
	 * @param closureId
	 * @return
	 */
	private EmployeeAndClosure determineActualLocked(String companyId, EmployeeAndClosure employeeAndClosure,
			GeneralDate day) {

		/**
		 * ロック : 1 , アンロック : 0
		 */

		EmployeeAndClosure employeeAndClosureDto = new EmployeeAndClosure();

		// // lits day between startDate and endDate
		// List<GeneralDate> listDay = this.getDaysBetween(periodTime.start(),
		// periodTime.end());

		// アルゴリズム「当月の期間を算出する」を実行する

		/**
		 * アルゴリズム「当月の実績ロックの取得」を実行する
		 */

		Integer closureId = employeeAndClosure.getClosureId();
		// 全てのドメインモデル「当月の実績ロック」を取得する
		// List<ActualLock> actualLockLists =
		// this.actualLockRepository.findByListId(companyId, closureIds);

		Optional<ActualLock> actualLock = this.actualLockRepository.findById(companyId, closureId);

		if (actualLock.get().getDailyLockState().value == 0) {
			employeeAndClosureDto.setLock(0);
		} else {
			Optional<Closure> closure = this.closureRepository.findById(companyId, closureId);
			DatePeriod closurePeriod = this.closureService.getClosurePeriod(closure.get().getClosureId().intValue(),
					closure.get().getClosureMonth().getProcessingYm());
			if (day.afterOrEquals(closurePeriod.start())
					&& day.beforeOrEquals(closurePeriod.end())) {
				employeeAndClosureDto.setLock(0);
			} else {
				employeeAndClosureDto.setLock(1);
			}
		}

		return employeeAndClosureDto;

		// List<Integer> closureIds = employeeAndClosures.stream().map(f -> {
		// return f.getClosureId();
		// }).collect(Collectors.toList());

		// List<ActualLock> closureIdUnLockMaps = actualLockLists.stream()
		// .filter(item -> item.getDailyLockState().value ==
		// 0).collect(Collectors.toList());
		//
		// closureIdUnLockMaps.forEach(f -> {
		// ClosureIdLockOutput lockDto = new
		// ClosureIdLockOutput(f.getClosureId().value, 0);
		// locks.add(lockDto);
		// });
		//
		// // 実績ロックされているかチェックする
		// List<ActualLock> closureIdLockMaps = actualLockLists.stream()
		// .filter(item -> item.getDailyLockState().value ==
		// 1).collect(Collectors.toList());
		//
		// List<Integer> closureIdLocks = closureIdLockMaps.stream().map(f -> {
		// return f.getClosureId().value;
		// }).collect(Collectors.toList());
		//
		// // ドメインモデル「締め」を取得する
		// List<Closure> listClosures =
		// this.closureRepository.findByListId(companyId, closureIdLocks);
		//
		// // アルゴリズム「当月の期間を算出する」を実行する
		// // closureId map with DatePeriod
		// Map<Integer, DatePeriod> DatePeriodMap = new HashMap<>();
		// // closureId map with current month
		// Map<Integer, YearMonth> currentMonthMap = listClosures.stream()
		// .collect(Collectors.toMap(Closure::getClosureId, x ->
		// x.getClosureMonth().getProcessingYm()));
		// currentMonthMap.forEach((key, value) -> {
		// DatePeriod closurePeriod = this.closureService.getClosurePeriod(key,
		// value);
		// DatePeriodMap.put(key, closurePeriod);
		// });
		//
		// // 基準日が当月かチェックする
		// DatePeriodMap.forEach((key, value) -> {
		// listDay.forEach(f -> {
		// if (f.afterOrEquals(value.start()) && f.beforeOrEquals(value.end()))
		// {
		// ClosureIdLockOutput lockDto = new ClosureIdLockOutput(key, 1);
		// locks.add(lockDto);
		// } else {
		// ClosureIdLockOutput lockDto = new ClosureIdLockOutput(key, 0);
		// locks.add(lockDto);
		// }
		// ;
		// });
		// });
		//
		// employeeAndClosures.stream().forEach(items -> {
		// locks.stream().forEach(lock -> {
		// if (items.getClosureId() == lock.getClosureId()) {
		// items.setLock(lock.getLock());
		// }
		// });
		// });
		//
		// return employeeAndClosures;
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
