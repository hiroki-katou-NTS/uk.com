package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.EmployeeAndClosureOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultDomainServiceImpl.ProcessState;
import nts.uk.ctx.at.record.dom.organization.EmploymentHistoryImported;
import nts.uk.ctx.at.record.dom.organization.adapter.EmploymentAdapter;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLock;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionType;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureGetMonthDay;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
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
	private ReflectWorkInforDomainService reflectWorkInforDomainService;

	@Override
	public ProcessState createDailyResultEmployee(AsyncCommandHandlerContext asyncContext, String employeeId,
			DatePeriod periodTime, String companyId, String empCalAndSumExecLogID, ExecutionType reCreateAttr) {
				
		// 正常終了 : 0
		// 中断 : 1
		ProcessState status = ProcessState.SUCCESS;

		GeneralDate processingDate = periodTime.start();

		// lits day between startDate and endDate
		List<GeneralDate> listDayBetween = this.getDaysBetween(periodTime.start(), periodTime.end());

		// Imported（就業）「所属雇用履歴」を取得する
		Optional<EmploymentHistoryImported> employmentHisOptional = this.employmentAdapter.getEmpHistBySid(companyId, employeeId, processingDate);
		if (!employmentHisOptional.isPresent()) {
			throw new RuntimeException("Employment History not exist");
		} 
		String employmentCode = employmentHisOptional.get().getEmploymentCode();

		for (GeneralDate day : listDayBetween) {
			
			// 締めIDを取得する
			Optional<ClosureEmployment> closureEmploymentOptional = this.closureEmploymentRepository
					.findByEmploymentCD(companyId, employmentCode);

			if (day.afterOrEquals(employmentHisOptional.get().getPeriod().end())
					&& day.beforeOrEquals(employmentHisOptional.get().getPeriod().start())) {
				status = ProcessState.SUCCESS;
			} else {
				EmployeeAndClosureOutput employeeAndClosureDto = new EmployeeAndClosureOutput();
				if (employmentHisOptional.get().getEmploymentCode().equals(closureEmploymentOptional.get()
						.getEmploymentCD())) {
					employeeAndClosureDto.setClosureId(closureEmploymentOptional.get().getClosureId());
					employeeAndClosureDto.setEmployeeId(employeeId);
					employeeAndClosureDto.setPeriod(employmentHisOptional.get().getPeriod());
				}

				// アルゴリズム「実績ロックされているか判定する」を実行する
				EmployeeAndClosureOutput employeeAndClosure = this.determineActualLocked(companyId, employeeAndClosureDto,
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
		};

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
	private EmployeeAndClosureOutput determineActualLocked(String companyId, EmployeeAndClosureOutput employeeAndClosure,
			GeneralDate day) {

		/**
		 * ロック : 1 , アンロック : 0
		 */

		/**
		 * アルゴリズム「当月の実績ロックの取得」を実行する
		 */

		Integer closureId = employeeAndClosure.getClosureId();
		// 全てのドメインモデル「当月の実績ロック」を取得する
		// List<ActualLock> actualLockLists =
		// this.actualLockRepository.findByListId(companyId, closureIds);

		Optional<ActualLock> actualLock = this.actualLockRepository.findById(companyId, closureId);

		DatePeriod period = new DatePeriod(GeneralDate.min(), GeneralDate.min());
		if (actualLock.get().getDailyLockState().value == 0) {
			employeeAndClosure.setLock(0);
		} else {
			Optional<Closure> closure = this.closureRepository.findById(companyId, closureId);
			
			List<ClosureHistory> closureHistories = this.closureRepository.findByClosureId(companyId,
					closureId);
			
			// exist data
			if (closure.isPresent()) {
				
				// to data
				closure.get().setClosureHistories(closureHistories);
				
				Optional<ClosureHistory> closureHisory = this.closureRepository.findBySelectedYearMonth(
						companyId, closureId, closure.get().getClosureMonth().getProcessingYm().v());
				
				ClosureGetMonthDay closureGetMonthDay = new ClosureGetMonthDay();
				period = closureGetMonthDay.getDayMonth(closureHisory.get().getClosureDate(),
						closure.get().getClosureMonth().getProcessingYm().v());
			}
			if (day.afterOrEquals(period.start())
					&& day.beforeOrEquals(period.end())) {
				employeeAndClosure.setLock(1);
			} else {
				employeeAndClosure.setLock(0);
			}
		}
		return employeeAndClosure;
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
