package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.finddata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.application.ApplicationRecordAdapter;
import nts.uk.ctx.at.record.dom.adapter.application.ApplicationRecordImport;
import nts.uk.ctx.at.record.dom.adapter.company.StatusOfEmployeeExport;
import nts.uk.ctx.at.record.dom.adapter.company.SyCompanyRecordAdapter;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.ApprovalStatusAdapter;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApprovalRootOfEmployeeImport;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApproveRootStatusForEmpImport;
import nts.uk.ctx.at.record.dom.application.realitystatus.RealityStatusService;
import nts.uk.ctx.at.record.dom.approvalmanagement.ApprovalProcessingUseSetting;
import nts.uk.ctx.at.record.dom.approvalmanagement.repository.ApprovalProcessingUseSettingRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.confirm.EmployeeDateErrorOuput;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.ClosurePeriod;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.GetClosurePeriod;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.Identification;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.IdentityProcessUseSet;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.month.ConfirmationMonth;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.ConfirmationMonthRepository;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentificationRepository;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentityProcessUseSetRepository;
import nts.arc.time.calendar.period.DatePeriod;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class FindDataDCRecord implements IFindDataDCRecord {

	private static String KEY_SIMPLE = "|";

	private static Map<String, Optional<IdentityProcessUseSet>> identityProcessUseSetMap = new HashMap<>();

	private static Map<String, Optional<ApprovalProcessingUseSetting>> approvalUseSettingMap = new HashMap<>();

	private static Map<String, List<StatusOfEmployeeExport>> lstStatusOfEmpMap = new HashMap<>();

	private static Map<String, List<ClosurePeriod>> lstClosurePeriodMap = new HashMap<>();

	private static Map<String, List<Identification>> lstIdentificationMap = new HashMap<>();

	private static Map<String, List<ApproveRootStatusForEmpImport>> lstApprovalRootMap = new HashMap<>();

	private static Map<String, ApprovalRootOfEmployeeImport> lstApprovalRoot595Map = new HashMap<>();

	private static Map<String, List<ApplicationRecordImport>> lstApplicationMap = new HashMap<>();

	private static Map<String, List<ConfirmationMonth>> lstConfirmMonthMap = new HashMap<>();

	private static Map<String, List<EmployeeDateErrorOuput>> lstEmpErrorMap = new HashMap<>();

	@Inject
	private ApprovalProcessingUseSettingRepository approvalProcessingUseSettingRepository;

	@Inject
	private IdentityProcessUseSetRepository identityProcessUseSetRepository;

	@Inject
	private SyCompanyRecordAdapter syCompanyRecordAdapter;

	@Inject
	private IdentificationRepository identificationRepository;

	@Inject
	private ApprovalStatusAdapter approvalStatusAdapter;

	@Inject
	private ApplicationRecordAdapter applicationRecordAdapter;

	@Inject
	private ConfirmationMonthRepository confirmationMonthRepository;

	@Inject
	private RealityStatusService realityStatusService;
	@Inject 
	private RecordDomRequireService requireService;

	@Override
	public Optional<IdentityProcessUseSet> findIdentityByKey(String companyId) {
		if (identityProcessUseSetMap.containsKey(companyId)) {
			return identityProcessUseSetMap.get(companyId);
		} else {
			Optional<IdentityProcessUseSet> indentityOpt = identityProcessUseSetRepository.findByKey(companyId);
			identityProcessUseSetMap.put(companyId, indentityOpt);
			return indentityOpt;
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Optional<ApprovalProcessingUseSetting> findApprovalByCompanyId(String companyId) {
		if (approvalUseSettingMap.containsKey(companyId)) {
			return approvalUseSettingMap.get(companyId);
		} else {
			Optional<ApprovalProcessingUseSetting> approvalOpt = approvalProcessingUseSettingRepository
					.findByCompanyId(companyId);
			approvalUseSettingMap.put(companyId, approvalOpt);
			return approvalOpt;
		}
	}

	@Override
	public List<StatusOfEmployeeExport> getListAffComHistByListSidAndPeriod(Optional<String> keyRandom,
			List<String> sid, DatePeriod datePeriod) {
		if (keyRandom.isPresent() && lstStatusOfEmpMap.containsKey(keyRandom.get())) {
			return lstStatusOfEmpMap.get(keyRandom.get());
		} else {
			List<StatusOfEmployeeExport> lstResult = syCompanyRecordAdapter.getListAffComHistByListSidAndPeriod(sid,
					datePeriod);
			if (keyRandom.isPresent())
				lstStatusOfEmpMap.put(keyRandom.get(), lstResult);
			return lstResult;
		}
	}

	@Override
	public List<ClosurePeriod> fromPeriod(String employeeId, GeneralDate criteriaDate, DatePeriod period) {
		String key = createKey(employeeId, criteriaDate.toString(), period.start().toString(), period.end().toString());
		if (lstClosurePeriodMap.containsKey(key)) {
			return lstClosurePeriodMap.get(key);
		} else {
			List<ClosurePeriod> lstResult = GetClosurePeriod.fromPeriod(requireService.createRequire(),
					new CacheCarrier(), employeeId, period.end(), period);
			lstClosurePeriodMap.put(key, lstResult);
			return lstResult;
		}
	}

	@Override
	public List<Identification> findByEmployeeID(String employeeID, GeneralDate startDate, GeneralDate endDate) {
		String key = createKey(employeeID, startDate.toString(), startDate.toString());
		if (lstIdentificationMap.containsKey(key)) {
			return lstIdentificationMap.get(key);
		} else {
			List<Identification> lstResult = identificationRepository.findByEmployeeID(employeeID, startDate, endDate);
			lstIdentificationMap.put(key, lstResult);
			return lstResult;
		}
	}

	@Override
	public void clearAllStateless() {
		identityProcessUseSetMap = new HashMap<>();
		
		approvalUseSettingMap = new HashMap<>();
		
		lstStatusOfEmpMap = new HashMap<>();
		
		lstClosurePeriodMap = new HashMap<>();
		
		lstIdentificationMap = new HashMap<>();
		
		lstApprovalRootMap = new HashMap<>();
		
		lstApprovalRoot595Map = new HashMap<>();

		lstApplicationMap = new HashMap<>();

		lstConfirmMonthMap = new HashMap<>();

		lstEmpErrorMap = new HashMap<>();
	}

	private String createKey(Object... values) {
		StringBuilder buff = new StringBuilder();
		for (Object value : values) {
			if (value instanceof Object[]) {
				for (Object valueSimple : (Object[]) value) {
					buff.append(valueSimple.toString());
					buff.append(KEY_SIMPLE);
				}
			} else {
				buff.append(value.toString());
				buff.append(KEY_SIMPLE);
			}
		}
		return buff.toString();
	}

	@Override
	public List<ApproveRootStatusForEmpImport> getApprovalByListEmplAndListApprovalRecordDateNew(
			List<GeneralDate> approvalRecordDates, List<String> employeeID, Integer rootType) {
		String key = createKey(employeeID.toArray(), approvalRecordDates.toArray(), rootType.toString());
		if (lstApprovalRootMap.containsKey(key)) {
			return lstApprovalRootMap.get(key);
		} else {
			List<ApproveRootStatusForEmpImport> lstResult = approvalStatusAdapter
					.getApprovalByListEmplAndListApprovalRecordDateNew(approvalRecordDates, employeeID, rootType);
			lstApprovalRootMap.put(key, lstResult);
			return lstResult;
		}
	}

	@Override
	public ApprovalRootOfEmployeeImport getDailyApprovalStatus(String approverId, List<String> targetEmployeeIds,
			DatePeriod period) {
		String key = createKey(approverId, targetEmployeeIds.toArray(), period.start(), period.end());

		if (lstApprovalRoot595Map.containsKey(key)) {
			return lstApprovalRoot595Map.get(key);
		}
		
		ApprovalRootOfEmployeeImport result = approvalStatusAdapter.getDailyApprovalStatus(approverId,
				targetEmployeeIds, period);
		lstApprovalRoot595Map.put(key, result);
		return result;

	}

	@Override
	public List<ApplicationRecordImport> getApplicationBySID(List<String> employeeID, GeneralDate startDate,
			GeneralDate endDate) {
		String key = createKey(employeeID.toArray(), startDate, endDate);
		if (lstApplicationMap.containsKey(key)) {
			return lstApplicationMap.get(key);
		} else {
			List<ApplicationRecordImport> lstApplication = applicationRecordAdapter.getApplicationBySID(employeeID,
					startDate, endDate);
			lstApplicationMap.put(key, lstApplication);
			return lstApplication;
		}
	}

	@Override
	public List<ConfirmationMonth> findBySomeProperty(List<String> employeeIds, int processYM, int closureDate,
			boolean isLastDayOfMonth, int closureId) {
		String key = createKey(employeeIds.toArray(), processYM, closureDate, isLastDayOfMonth, closureId);
		if (lstConfirmMonthMap.containsKey(key)) {
			return lstConfirmMonthMap.get(key);
		} else {
			List<ConfirmationMonth> lstConfirmMonth = confirmationMonthRepository.findBySomeProperty(employeeIds,
					processYM, closureDate, isLastDayOfMonth, closureId);
			lstConfirmMonthMap.put(key, lstConfirmMonth);
			return lstConfirmMonth;
		}
	}

	@Override
	public List<EmployeeDateErrorOuput> checkEmployeeErrorOnProcessingDate(String employeeId, GeneralDate startDate,
			GeneralDate endDate) {
		String key = createKey(employeeId, startDate, endDate);
		if (lstEmpErrorMap.containsKey(key)) {
			return lstEmpErrorMap.get(key);
		} else {
			List<EmployeeDateErrorOuput> lstOut = realityStatusService
					.checkEmployeeErrorOnProcessingDate(employeeId, startDate, endDate).stream().map(x -> {
						return new EmployeeDateErrorOuput(employeeId, x.getDate(), x.getHasError());
					}).collect(Collectors.toList());

			lstEmpErrorMap.put(key, lstOut);
			return lstOut;
		}
	}

}
