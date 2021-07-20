package nts.uk.ctx.at.function.dom.processexecution.updateprocessexecsetting.changepersionlistforsche;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.AffWorkplaceHistoryImport;
import nts.uk.ctx.at.function.dom.adapter.workplace.SyWorkplaceAdapter;
import nts.uk.ctx.at.function.dom.processexecution.ReExecutionCondition;
import nts.uk.ctx.at.function.dom.processexecution.UpdateProcessAutoExecution;
import nts.uk.ctx.at.shared.dom.adapter.temporaryabsence.SharedTempAbsenceAdapter;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployeeHistory;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.repository.BusinessTypeEmpOfHistoryRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;

/**
 * 異動者・勤務種別変更者リスト作成処理（スケジュール用） ( old :対象社員を絞り込み)
 * 
 * @author tutk
 *
 */
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class ChangePersionListForSche {
	@Inject
	private ClosureRepository closureRepo;

	@Inject
	private SyWorkplaceAdapter syWorkplaceAdapter;
	
	@Inject
	private BusinessTypeEmpOfHistoryRepository businessTypeEmpOfHistoryRepository;
	
	@Inject
	private SharedTempAbsenceAdapter sharedTempAbsenceAdapter;

	public EmployeeDataDto filterEmployeeList(UpdateProcessAutoExecution procExec, List<String> employeeIdList) {
		String companyId = AppContexts.user().companyId();
		// 社員ID（異動者）（List）
    	List<String> transferList = new ArrayList<>();
    	// 社員ID（勤務種別変更者）（List）
    	List<String> changeWorktypeList = new ArrayList<>();
    	// 社員ID（休職者・休業者）（List）
    	List<String> absenceList = new ArrayList<>();
		/** 作成対象の判定 */
		// ドメインモデル「就業締め日」を取得する
		List<Closure> closureList = this.closureRepo.findAllActive(companyId, UseClassification.UseClass_Use);
		DatePeriod closurePeriod = this.findClosureMinMaxPeriod(companyId, closureList);
		DatePeriod newClosurePeriod = new DatePeriod(closurePeriod.start(), GeneralDate.ymd(9999, 12, 31));
		
		ReExecutionCondition setting = procExec.getReExecCondition();
		
		// 異動者を再作成するか判定
		// 異動者の絞り込み
		if (setting.getRecreateTransfer().isUse()) {
			// 基幹．社員RQ「指定した基準日以降の所属職場履歴を取得する」を実行する。
			List<AffWorkplaceHistoryImport> workplaceHistList = this.syWorkplaceAdapter
					.getWorkplaceBySidsAndBaseDate(employeeIdList, closurePeriod.start());
			// 取得した社員IDのListを返す
			transferList = workplaceHistList.stream().map(AffWorkplaceHistoryImport::getSid).collect(Collectors.toList());
		}
		
		// 勤務種別変更者を再作成するか判定
		// 勤務種別の絞り込み
		if (setting.getRecreatePersonChangeWkt().isUse()) {
			// ドメインモデル「社員の勤務種別の履歴」を取得する
			List<BusinessTypeOfEmployeeHistory> businessTypeHistList = this.businessTypeEmpOfHistoryRepository
					.findByEmployeeDesc(companyId, employeeIdList).stream()
					.filter(data -> data.getHistory().stream()
							.anyMatch(hist -> hist.start().afterOrEquals(closurePeriod.start())))
					.collect(Collectors.toList());
			// 勤務種別の絞り込み
			changeWorktypeList = businessTypeHistList.stream().map(BusinessTypeOfEmployeeHistory::getEmployeeId)
					.collect(Collectors.toList());
		}
		
		// 休職者・休業者を再作成するか判定
		// 休職者・休業者の絞り込み
		if (setting.getRecreateLeave().isUse()) {
			// Imported「休職休業履歴」を取得する
			// 取得した「休職休業履歴」.社員IDを休職者・休業者とする
			absenceList = this.sharedTempAbsenceAdapter.getAbsenceEmpsByPeriod(employeeIdList, newClosurePeriod);
		}
		
		// 社員ID（異動者）（List）と社員ID（勤務種別変更者）（List）と社員ID（休職者・休業者）（List）を合わせる
		// 社員ID（異動者、勤務種別変更者、休職者・休業者）（List）から重複している社員IDを1つになるよう削除する
		employeeIdList = new ArrayList<>(Stream.of(transferList, changeWorktypeList, absenceList)
				.flatMap(Collection::stream)
				.collect(Collectors.toSet()));
		return new EmployeeDataDto(employeeIdList, closurePeriod.start());
	}

	private DatePeriod findClosureMinMaxPeriod(String companyId, List<Closure> closureList) {
		GeneralDate startYearMonth = null;
		GeneralDate endYearMonth = null;
		for (Closure closure : closureList) {
			DatePeriod datePeriod = ClosureService.getClosurePeriod(closure.getClosureId().value,
					closure.getClosureMonth().getProcessingYm(), Optional.of(closure));

			if (startYearMonth == null || datePeriod.start().before(startYearMonth)) {
				startYearMonth = datePeriod.start();
			}

			if (endYearMonth == null || datePeriod.end().after(endYearMonth)) {
				endYearMonth = datePeriod.end();
			}
		}

		return new DatePeriod(startYearMonth, endYearMonth);
	}
	
	@Data
	@AllArgsConstructor
	public class EmployeeDataDto {
		private List<String> employeeIds;
		private GeneralDate startDate;
	}
}
