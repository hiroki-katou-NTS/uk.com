/**
 * 8:56:53 AM Mar 22, 2018
 */
package nts.uk.ctx.at.record.dom.approvalmanagement.domainservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.auth.dom.employmentrole.EmployeeReferenceRange;
import nts.uk.ctx.at.record.dom.adapter.query.employee.RegulationInfoEmployeeQuery;
import nts.uk.ctx.at.record.dom.adapter.query.employee.RegulationInfoEmployeeQueryAdapter;
import nts.uk.ctx.at.record.dom.adapter.query.employee.RegulationInfoEmployeeQueryR;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.ApprovalStatusAdapter;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApprovalRootOfEmployeeImport;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApprovalRootSituation;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.enums.ApprovalActionByEmpl;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.enums.ApproverEmployeeState;
import nts.uk.ctx.at.record.dom.approvalmanagement.ApprovalProcessingUseSetting;
import nts.uk.ctx.at.record.dom.approvalmanagement.dtos.ApprovalEmployeeDto;
import nts.uk.ctx.at.record.dom.approvalmanagement.dtos.ClosureDto;
import nts.uk.ctx.at.record.dom.approvalmanagement.dtos.DateApprovalStatusDto;
import nts.uk.ctx.at.record.dom.approvalmanagement.dtos.OneMonthApprovalStatusDto;
import nts.uk.ctx.at.record.dom.approvalmanagement.repository.ApprovalProcessingUseSettingRepository;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * @author hungnm
 *
 */
@Stateless
public class OneMonthApprovalSttDomainServiceImpl implements OneMonthApprovalSttDomainService {

	@Inject
	private ApprovalProcessingUseSettingRepository approvalProcessingUseSettingRepository;

	@Inject
	private ClosureRepository closureRepository;

	@Inject
	private ClosureService closureService;

	@Inject
	private ApprovalStatusAdapter approvalStatusAdapter;

	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;

	@Inject
	private RegulationInfoEmployeeQueryAdapter regulationInfoEmployeeQueryAdapter;

	private RegulationInfoEmployeeQuery createQueryEmployee(List<String> employeeCodes, GeneralDate startDate,
			GeneralDate endDate) {
		RegulationInfoEmployeeQuery query = new RegulationInfoEmployeeQuery();
		query.setBaseDate(GeneralDate.today());
		query.setReferenceRange(EmployeeReferenceRange.ALL_EMPLOYEE.value);
		query.setFilterByEmployment(true);
		query.setEmploymentCodes(employeeCodes);
		query.setFilterByDepartment(false);
		query.setDepartmentCodes(Collections.emptyList());
		query.setFilterByWorkplace(false);
		query.setWorkplaceCodes(Collections.emptyList());
		query.setFilterByClassification(false);
		query.setClassificationCodes(Collections.emptyList());
		query.setFilterByJobTitle(false);
		query.setJobTitleCodes(Collections.emptyList());
		query.setFilterByWorktype(false);
		query.setWorktypeCodes(Collections.emptyList());
		query.setPeriodStart(startDate);
		query.setPeriodEnd(endDate);
		query.setIncludeIncumbents(true);
		query.setIncludeWorkersOnLeave(true);
		query.setIncludeOccupancy(true);
		query.setIncludeAreOnLoan(true);
		query.setIncludeGoingOnLoan(false);
		query.setIncludeRetirees(false);
		query.setRetireStart(GeneralDate.today());
		query.setRetireEnd(GeneralDate.today());
		return query;
	}

	private List<ApprovalEmployeeDto> buildApprovalEmployeeData(List<RegulationInfoEmployeeQueryR> lstEmployee,
			ApprovalRootOfEmployeeImport approvalRootOfEmployeeImport) {
		List<ApprovalEmployeeDto> lstApprovalEmployee = new ArrayList<>();
		for (int e = 0; e < lstEmployee.size(); e++) {
			ApprovalEmployeeDto approvalEmployee = new ApprovalEmployeeDto();
			approvalEmployee.setEmployeeId(lstEmployee.get(e).getEmployeeId());
			approvalEmployee.setEmployeeCode(lstEmployee.get(e).getEmployeeCode());
			approvalEmployee.setEmployeeName(lstEmployee.get(e).getEmployeeName());
			List<DateApprovalStatusDto> lstDateApprovalStatusDto = new ArrayList<>();
			List<ApprovalRootSituation> lstApproval = approvalRootOfEmployeeImport.getApprovalRootSituations();
			boolean isSameEmpId = false;
			for (int a = 0; a < lstApproval.size(); a++) {
				if (lstEmployee.get(e).getEmployeeId().equals(lstApproval.get(a).getTargetID())) {
					DateApprovalStatusDto dateApprovalStatusDto = new DateApprovalStatusDto();
					dateApprovalStatusDto.setDate(lstApproval.get(a).getAppDate());
					dateApprovalStatusDto.setStatus(3);
					if (lstApproval.get(a).getApprovalStatus()
							.getApprovalActionByEmpl() == ApprovalActionByEmpl.APPROVALED
							&& lstApproval.get(a).getApprovalAtr() == ApproverEmployeeState.PHASE_DURING) {
						dateApprovalStatusDto.setStatus(0);
					} else if (lstApproval.get(a).getApprovalStatus()
							.getApprovalActionByEmpl() == ApprovalActionByEmpl.APPROVAL_REQUIRE
							&& lstApproval.get(a).getApprovalAtr() == ApproverEmployeeState.PHASE_DURING) {
						dateApprovalStatusDto.setStatus(1);
					} else if (lstApproval.get(a).getApprovalStatus()
							.getApprovalActionByEmpl() == ApprovalActionByEmpl.NOT_APPROVAL
							&& lstApproval.get(a).getApprovalAtr() == ApproverEmployeeState.PHASE_LESS) {
						dateApprovalStatusDto.setStatus(2);
					} else if (lstApproval.get(a).getApprovalStatus()
							.getApprovalActionByEmpl() == ApprovalActionByEmpl.NOT_APPROVAL
							&& lstApproval.get(a).getApprovalAtr() == ApproverEmployeeState.PHASE_DURING) {
						dateApprovalStatusDto.setStatus(2);
					} else if (lstApproval.get(a).getApprovalStatus()
							.getApprovalActionByEmpl() == ApprovalActionByEmpl.NOT_APPROVAL
							&& lstApproval.get(a).getApprovalAtr() == ApproverEmployeeState.PHASE_PASS) {
						dateApprovalStatusDto.setStatus(0);
					} else if (lstApproval.get(a).getApprovalStatus()
							.getApprovalActionByEmpl() == ApprovalActionByEmpl.NOT_APPROVAL
							&& lstApproval.get(a).getApprovalAtr() == ApproverEmployeeState.COMPLETE) {
						dateApprovalStatusDto.setStatus(0);
					}
					lstDateApprovalStatusDto.add(dateApprovalStatusDto);
					isSameEmpId = true;
				}
			}
			if (isSameEmpId) {
				approvalEmployee.setLstStatus(lstDateApprovalStatusDto);
				lstApprovalEmployee.add(approvalEmployee);
			}
		}
		return lstApprovalEmployee;
	}

	public OneMonthApprovalStatusDto getDatePeriod(int closureId) {
		OneMonthApprovalStatusDto result = new OneMonthApprovalStatusDto();
		YearMonth currentYearMonth = GeneralDate.today().yearMonth();
		DatePeriod datePeriod = closureService.getClosurePeriod(closureId, currentYearMonth);
		result.setStartDate(datePeriod.start());
		result.setEndDate(datePeriod.end());
		return result;
	}

	public OneMonthApprovalStatusDto getDatePeriod(int closureId, int currentYearMonth) {
		OneMonthApprovalStatusDto result = new OneMonthApprovalStatusDto();
		DatePeriod datePeriod = closureService.getClosurePeriod(closureId, YearMonth.of(currentYearMonth));
		result.setStartDate(datePeriod.start());
		result.setEndDate(datePeriod.end());
		return result;
	}

	public OneMonthApprovalStatusDto getOneMonthApprovalStatus(Integer closureIdParam, GeneralDate startDateParam,
			GeneralDate endDateParam) {
		YearMonth currentYearMonth = GeneralDate.today().yearMonth();
		OneMonthApprovalStatusDto oneMonthApprovalStatusDto = new OneMonthApprovalStatusDto();
		// 対応するドメインモデル「承認処理の利用設定」を取得する
		Optional<ApprovalProcessingUseSetting> approvalProcUseSet = approvalProcessingUseSettingRepository
				.findByCompanyId(AppContexts.user().companyId());
		// 取得したドメインモデル「承認処理の利用設定．日の承認者確認を利用する」をチェックする
		boolean useDayApprovalComfirmCheck = false;
		if (approvalProcUseSet.isPresent()) {
			useDayApprovalComfirmCheck = approvalProcUseSet.get().getUseDayApproverConfirm();
		}
		if (useDayApprovalComfirmCheck) {
			List<YearMonth> lstYearMonth = new ArrayList<>();
			int currentClosure = 0;
			if (closureIdParam == null) {
				// ドメインモデル「締め」をすべて取得する
				List<Closure> lstClosure = closureRepository.findAllUse(AppContexts.user().companyId());
				// アルゴリズム「当月の名前を取得する」を実行する
				List<ClosureHistory> lstClosureHst = closureRepository
						.findByCurrentMonth(AppContexts.user().companyId(), currentYearMonth);
				// convert to closure dtos
				List<ClosureDto> lstClosureDto = new ArrayList<>();
				for (int c = 0; c < lstClosure.size(); c++) {
					for (int h = 0; h < lstClosureHst.size(); h++) {
						if (lstClosure.get(c).getClosureId().value == lstClosureHst.get(h).getClosureId().value) {
							lstClosureDto.add(new ClosureDto(lstClosureHst.get(h).getClosureId().value,
									lstClosureHst.get(h).getClosureName().v(),
									lstClosure.get(c).getClosureMonth().getProcessingYm().v().intValue()));
							lstYearMonth.add(lstClosure.get(c).getClosureMonth().getProcessingYm());
						}
					}
				}
				oneMonthApprovalStatusDto.setLstClosure(lstClosureDto);
				// 取得したドメインモデル「締め」の先頭を選択状態にする
				currentClosure = lstClosureDto.get(0).closureId;
			} else {
				currentClosure = closureIdParam;
			}
			DatePeriod datePeriod = null;
			if (startDateParam == null) {
				// アルゴリズム「当月の期間を算出する」を実行する
				datePeriod = closureService.getClosurePeriod(currentClosure,
						lstYearMonth.isEmpty() ? currentYearMonth : lstYearMonth.get(0));
			} else {
				datePeriod = new DatePeriod(startDateParam, endDateParam);
			}
			oneMonthApprovalStatusDto.setStartDate(datePeriod.start());
			oneMonthApprovalStatusDto.setEndDate(datePeriod.end());
			// Imported「（就業．勤務実績）基準社員の承認対象者」をすべて取得する
			ApprovalRootOfEmployeeImport approvalRootOfEmployeeImport = approvalStatusAdapter.getApprovalRootOfEmloyee(
					datePeriod.start(), datePeriod.end(), AppContexts.user().employeeId(),
					AppContexts.user().companyId(), 1);
			if (approvalRootOfEmployeeImport == null
					|| approvalRootOfEmployeeImport.getApprovalRootSituations().size() == 0) {
				throw new BusinessException("Msg_874");
			}
			// ドメインモデル「雇用に紐づく就業締め」を取得する
			List<ClosureEmployment> lstClosureEmployment = closureEmploymentRepository
					.findByClosureId(AppContexts.user().companyId(), currentClosure);
			List<String> lstEmployment = new ArrayList<>();
			lstEmployment.addAll(lstClosureEmployment.stream().map(closureEmployment -> {
				return closureEmployment.getEmploymentCD();
			}).collect(Collectors.toList()));
			/*
			// しぼり込んだ社員一覧が0件の場合
			if (lstEmployment.size() == 0) {
				throw new BusinessException("Msg_875");
			}
			*/
			// 対応するすべての社員を取得する
			// 【条件】 ・取得したドメインモデル「雇用に紐づく就業締め．雇用コード」に一致する基準日時点の所属雇用 ・在職している社員
			List<RegulationInfoEmployeeQueryR> lstEmployee = regulationInfoEmployeeQueryAdapter
					.search(createQueryEmployee(lstEmployment, datePeriod.start(), datePeriod.end()));
			List<ApprovalEmployeeDto> buildApprovalEmployeeData = buildApprovalEmployeeData(lstEmployee, approvalRootOfEmployeeImport);
			oneMonthApprovalStatusDto
			.setLstEmployee(buildApprovalEmployeeData);
			if (buildApprovalEmployeeData.isEmpty()) {
				throw new BusinessException("Msg_875");
			}
		} else {
			throw new BusinessException("Msg_873");
		}
		return oneMonthApprovalStatusDto;
	}

}
