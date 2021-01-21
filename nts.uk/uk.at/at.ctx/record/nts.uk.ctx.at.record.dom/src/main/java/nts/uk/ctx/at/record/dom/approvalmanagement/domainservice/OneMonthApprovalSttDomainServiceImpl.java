/**
 * 8:56:53 AM Mar 22, 2018
 */
package nts.uk.ctx.at.record.dom.approvalmanagement.domainservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
//import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.company.AffComHistItemImport;
import nts.uk.ctx.at.record.dom.adapter.company.AffCompanyHistImport;
import nts.uk.ctx.at.record.dom.adapter.company.SyCompanyRecordAdapter;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeAdapter;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeDto;
import nts.uk.ctx.at.record.dom.adapter.employee.RegularSortingTypeImport;
import nts.uk.ctx.at.record.dom.adapter.employee.SortingConditionOrderImport;
import nts.uk.ctx.at.record.dom.adapter.employment.EmploymentHistAdapter;
import nts.uk.ctx.at.record.dom.adapter.employment.EmploymentHistImport;
import nts.uk.ctx.at.record.dom.adapter.initswitchsetting.DateProcessedRecord;
import nts.uk.ctx.at.record.dom.adapter.initswitchsetting.InitSwitchSetAdapter;
import nts.uk.ctx.at.record.dom.adapter.initswitchsetting.InitSwitchSetDto;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.ApprovalStatusAdapter;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApprovalRootOfEmployeeImport;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApprovalRootSituation;
import nts.uk.ctx.at.record.dom.approvalmanagement.ApprovalProcessingUseSetting;
import nts.uk.ctx.at.record.dom.approvalmanagement.dailyperformance.algorithm.common.DPCorrectStateParam;
import nts.uk.ctx.at.record.dom.approvalmanagement.dailyperformance.algorithm.common.GenDPCorrectStateService;
import nts.uk.ctx.at.record.dom.approvalmanagement.dtos.ApprovalEmployeeDto;
import nts.uk.ctx.at.record.dom.approvalmanagement.dtos.ClosureDto;
import nts.uk.ctx.at.record.dom.approvalmanagement.dtos.DateApprovalStatusDto;
import nts.uk.ctx.at.record.dom.approvalmanagement.dtos.EmployeeAffiliationInforDto;
import nts.uk.ctx.at.record.dom.approvalmanagement.dtos.OneMonthApprovalStatusDto;
import nts.uk.ctx.at.record.dom.approvalmanagement.repository.ApprovalProcessingUseSettingRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.ApprovalStatusActualResult;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.ConfirmStatusActualResult;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.ModeData;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.approval.ApprovalStatusActualDayChange;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.confirm.ConfirmStatusActualDayChange;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.finddata.IFindDataDCRecord;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.EmployeeGeneralInfoService;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.Identification;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentificationRepository;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.EmployeeGeneralInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExClassificationHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExJobTitleHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExWorkPlaceHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.jobtitle.JobTitleInfoAdapter;
import nts.uk.ctx.at.shared.dom.adapter.jobtitle.JobTitleInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.jobtitle.SequenceMasterImport;
import nts.uk.ctx.at.shared.dom.adapter.jobtitle.WorkplaceExportAdapter;
import nts.uk.ctx.at.shared.dom.adapter.jobtitle.WorkplaceExportImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.JobTitleExport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.JobTitleHistoryExport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceConfigInfoAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;

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
	private ApprovalStatusAdapter approvalStatusAdapter;

	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;

	@Inject
	private SyCompanyRecordAdapter syCompanyRecordAdapter;

	// @Inject
	// private ShareEmploymentAdapter shareEmploymentAdapter;

	@Inject
	private EmployeeAdapter atEmployeeAdapter;

	@Inject
	private EmploymentHistAdapter employmentHistAdapter;
	
	@Inject 
	private RecordDomRequireService requireService;

	@Inject
	private EmployeeGeneralInfoService employeeGeneralInfoService;

	@Inject
	private WorkplaceConfigInfoAdapter configInfoAdapter;

	@Inject
	private JobTitleInfoAdapter infoAdapter;

	@Inject
	private WorkplaceExportAdapter workplaceExportAdapter;

	@Inject
	private InitSwitchSetAdapter initSwitchSetAdapter;

	@Inject
	private GenDPCorrectStateService genDPCorrectStateService;

	@Inject
	private ApprovalStatusActualDayChange approvalStatusActualDayChange;

	@Inject
	private ConfirmStatusActualDayChange confirmStatusActualDayChange;
	
	@Inject
	private IFindDataDCRecord iFindDataDCRecord;

	public List<ApprovalEmployeeDto> buildApprovalEmployeeData(List<EmployeeDto> lstEmployee,
			List<ApprovalStatusActualResult> lstApprovalData, List<ConfirmStatusActualResult> lstConfirmData) {
		List<ApprovalEmployeeDto> lstApprovalEmployee = new ArrayList<>();
		for (EmployeeDto empQ : lstEmployee) {
			ApprovalEmployeeDto approvalEmployee = new ApprovalEmployeeDto();

			approvalEmployee.setEmployeeId(empQ.getSid());
			approvalEmployee.setEmployeeCode(empQ.getScd());
			approvalEmployee.setEmployeeName(empQ.getBussinessName());

			List<DateApprovalStatusDto> lstDateApprovalStatusDto = lstApprovalData.stream()
					.filter(f -> empQ.getSid().equals(f.getEmployeeId())).map(apv -> {
						String employeeIdAdd = apv.getEmployeeId();
						GeneralDate dateAdd = apv.getDate();

						DateApprovalStatusDto dateApvS = new DateApprovalStatusDto();

						dateApvS.setStatus(3);
						dateApvS.setDate(apv.getDate());
						boolean check = false;
						for (ConfirmStatusActualResult identification : lstConfirmData) {
							if (dateAdd.equals(identification.getDate())
									&& employeeIdAdd.equals(identification.getEmployeeId())) {
								check = identification.isStatus();
								break;
							}
						}
						if (apv.isStatus()) {
							dateApvS.setStatus(0);
						} else {
							if (apv.notDisableApproval()) {
								dateApvS.setStatus(1);
							} else {
								if (check)
									dateApvS.setStatus(2);
								else
									dateApvS.setStatus(4);
							}
						}
						return dateApvS;
					}).collect(Collectors.toList());

			if (!lstDateApprovalStatusDto.isEmpty()) {
				approvalEmployee.setLstStatus(lstDateApprovalStatusDto);
				lstApprovalEmployee.add(approvalEmployee);
			}
		}

		return lstApprovalEmployee;
	}

	public OneMonthApprovalStatusDto getDatePeriod(int closureId) {
		OneMonthApprovalStatusDto result = new OneMonthApprovalStatusDto();
		YearMonth currentYearMonth = GeneralDate.today().yearMonth();
		DatePeriod datePeriod = ClosureService.getClosurePeriod(requireService.createRequire(), 
				closureId, currentYearMonth);
		result.setStartDate(datePeriod.start());
		result.setEndDate(datePeriod.end());
		return result;
	}

	/**
	 * 期間を変更する
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public OneMonthApprovalStatusDto getDatePeriod(int closureId, int currentYearMonth) {
		OneMonthApprovalStatusDto result = new OneMonthApprovalStatusDto();
		// [No.609]ログイン社員のシステム日時点の処理対象年月を取得する
		InitSwitchSetDto initSwitchSetDto = this.initSwitchSetAdapter.targetDateFromLogin();
		// 画面項目「A1_5：期間」に取得した期間を取得する
		DatePeriod datePeriod = initSwitchSetDto.getListDateProcessed().stream()
				.filter(x -> x.getClosureID() == closureId).findFirst().get().getDatePeriod();
		result.setStartDate(datePeriod.start());
		result.setEndDate(datePeriod.end());
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public OneMonthApprovalStatusDto getOneMonthApprovalStatus(Integer closureIdParam, Integer yearMonth) {
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		YearMonth currentYearMonth = GeneralDate.today().yearMonth();
		// List<Identification> listIdentification = new ArrayList<>();
		OneMonthApprovalStatusDto oneMonthApprovalStatusDto = new OneMonthApprovalStatusDto();
		// 対応するドメインモデル「承認処理の利用設定」を取得する
		Optional<ApprovalProcessingUseSetting> approvalProcUseSet = approvalProcessingUseSettingRepository
				.findByCompanyId(AppContexts.user().companyId());
		// 取得したドメインモデル「承認処理の利用設定．日の承認者確認を利用する」をチェックする
		boolean useDayApprovalComfirmCheck = false;
		if (approvalProcUseSet.isPresent()) {
			useDayApprovalComfirmCheck = approvalProcUseSet.get().getUseDayApproverConfirm();
		}

		if (!useDayApprovalComfirmCheck) {
			throw new BusinessException("Msg_873");
		}

		List<YearMonth> lstYearMonth = new ArrayList<>();
		int currentClosure = 0;
		// ドメインモデル「締め」をすべて取得する
		List<Closure> lstClosure = closureRepository.findAllUse(companyId);
		// アルゴリズム「当月の名前を取得する」を実行する
		List<ClosureHistory> lstClosureHst = closureRepository.findByCurrentMonth(companyId, currentYearMonth);
		// convert to closure dtos
		List<ClosureDto> lstClosureDto = new ArrayList<>();
		for (int c = 0; c < lstClosure.size(); c++) {
			for (int h = 0; h < lstClosureHst.size(); h++) {
				if (lstClosure.get(c).getClosureId().value == lstClosureHst.get(h).getClosureId().value) {
					lstClosureDto.add(new ClosureDto(lstClosureHst.get(h).getClosureId().value,
							lstClosureHst.get(h).getClosureId().value + " : " + lstClosureHst.get(h).getClosureName().v(),
							lstClosure.get(c).getClosureMonth().getProcessingYm().v().intValue()));
					lstYearMonth.add(lstClosure.get(c).getClosureMonth().getProcessingYm());
				}
			}
		}
		oneMonthApprovalStatusDto.setLstClosure(lstClosureDto);
		// INPUT．「締めID」をチェックする
		if (closureIdParam == null) {
			// 社員に対応する処理締めを取得する
			Closure closure = ClosureService.getClosureDataByEmployee(requireService.createRequire(),
					new CacheCarrier(), employeeId, GeneralDate.today());
			currentClosure = closure.getClosureId().value;
		} else {
			// パラメータ「対象締め」をセットする
			currentClosure = closureIdParam;
		}
		DatePeriod datePeriod = null;
		// INPUT．「対象期間」をチェックする
		// startDateParam ma null thi endDateParam cung null
		if (yearMonth == null) {
			// [No.609]ログイン社員のシステム日時点の処理対象年月を取得する
			InitSwitchSetDto initSwitchSetDto = this.initSwitchSetAdapter.targetDateFromLogin();
			// 画面項目「A1_5：期間」に取得した期間を取得する
			int closureId = currentClosure;
			DateProcessedRecord record = initSwitchSetDto.getListDateProcessed().stream()
					.filter(x -> x.getClosureID() == closureId).findFirst().orElse(null);
			datePeriod = record.getDatePeriod();
			oneMonthApprovalStatusDto.setYearMonth(record.getTargetDate().v());
		} else {
			// 画面項目「A1_5：期間」にINPUT．「対象期間」の期間をセットする
			Optional<DPCorrectStateParam> dpCorrectOpt = genDPCorrectStateService.genStateParamClosureId(
					new DPCorrectStateParam(), ClosureId.valueOf(closureIdParam), YearMonth.of(yearMonth));
			if (!dpCorrectOpt.isPresent())
				return new OneMonthApprovalStatusDto(Collections.emptyList(), null, null, Collections.emptyList(), "", 0);
			datePeriod = dpCorrectOpt.get().getDatePeriod();
			oneMonthApprovalStatusDto.setYearMonth(yearMonth);
		}
		oneMonthApprovalStatusDto.setStartDate(datePeriod.start());
		oneMonthApprovalStatusDto.setEndDate(datePeriod.end());
		return processGetData(Optional.of(oneMonthApprovalStatusDto), currentClosure, datePeriod);
	}

	@Inject
	private IdentificationRepository identificationRepo;

	/** 日の本人確認を取得する */
	public List<Identification> getIdentification(List<String> employeeId, DatePeriod datePeriod) {
		List<Identification> listIdentificationRepo = identificationRepo.findByListEmployeeID(employeeId,
				datePeriod.start(), datePeriod.end());
		return listIdentificationRepo;
	}

	// 並び替え条件 =<職場(inlevel)、1>、<分類コード(ASC)、2>、<職位(序列)、3>、<社員コード(ASC)、4>
	public List<SortingConditionOrderImport> createListConditions() {
		List<SortingConditionOrderImport> lstCondition = new ArrayList<>();
		lstCondition.add(new SortingConditionOrderImport(1, RegularSortingTypeImport.WORKPLACE));
		lstCondition.add(new SortingConditionOrderImport(2, RegularSortingTypeImport.CLASSIFICATION));
		lstCondition.add(new SortingConditionOrderImport(3, RegularSortingTypeImport.POSITION));
		// fix bug 101289
		// lstCondition.add(new
		// SortingConditionOrderImport(4,RegularSortingTypeImport.EMPLOYMENT));
		return lstCondition;
	}

	// convert from Date to DateTime
	/*
	 * private GeneralDateTime convertFromDateToDateTime(GeneralDate date) { return
	 * GeneralDateTime.ymdhms(date.year(), date.month(), date.day(), 0, 0, 0); }
	 */

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public OneMonthApprovalStatusDto changeConditionExtract(Integer closureIdParam, Integer year) {
		Optional<DPCorrectStateParam> dpCorrectOpt = genDPCorrectStateService.genStateParamClosureId(
				new DPCorrectStateParam(), ClosureId.valueOf(closureIdParam), YearMonth.of(year));
		if (!dpCorrectOpt.isPresent())
			return new OneMonthApprovalStatusDto(Collections.emptyList(), null, null, Collections.emptyList(), "", 0);

		return processGetData(Optional.empty(), dpCorrectOpt.get().getClosureId().value,
				dpCorrectOpt.get().getDatePeriod());
	}

	public OneMonthApprovalStatusDto processGetData(Optional<OneMonthApprovalStatusDto> approvalStatusOpt,
			Integer closureId, DatePeriod datePeriod) {
		OneMonthApprovalStatusDto oneMonthApprovalStatusDto = approvalStatusOpt.isPresent() ? approvalStatusOpt.get()
				: new OneMonthApprovalStatusDto();
		oneMonthApprovalStatusDto.setStartDate(datePeriod.start());
		oneMonthApprovalStatusDto.setEndDate(datePeriod.end());
		// Imported「（就業．勤務実績）基準社員の承認対象者」をすべて取得する
		// Imported（就業）「基準社員の承認対象者」を取得する
		ApprovalRootOfEmployeeImport approvalRootOfEmployeeImport = approvalStatusAdapter.getApprovalRootOfEmloyee(
				datePeriod.start(), datePeriod.end(), AppContexts.user().employeeId(), AppContexts.user().companyId(),
				1);

		List<ApprovalRootSituation> approvalRootSituations = new ArrayList<>();
		List<String> lstEmploymentCd = new ArrayList<>();
		// fix bug 91363
		List<String> lstEmployees = new ArrayList<>();
		if (approvalRootOfEmployeeImport == null
				|| approvalRootOfEmployeeImport.getApprovalRootSituations().size() == 0) {
			// oneMonthApprovalStatusDto.setMessageID("Msg_874");
			// エラーメッセージ（Msg_916）を表示する
			oneMonthApprovalStatusDto.setMessageID("Msg_916");
			return oneMonthApprovalStatusDto;
			// throw new BusinessException("Msg_874");
		} else {
			List<ApprovalRootSituation> listApp = approvalRootOfEmployeeImport.getApprovalRootSituations();
			Set<String> listAppId = approvalRootOfEmployeeImport.getApprovalRootSituations().stream()
					.map(c -> c.getTargetID()).collect(Collectors.toSet());
			// 対象期間に在職しているかチェックする
			// 社員ID（List）と指定期間から所属会社履歴項目を取得 【Request：No211】
			List<AffCompanyHistImport> listAffCompanyHistImport = this.syCompanyRecordAdapter
					.getAffCompanyHistByEmployee(new ArrayList<>(listAppId), datePeriod);
			if (listAffCompanyHistImport.isEmpty() || listAffCompanyHistImport.stream()
					.flatMap(x -> x.getLstAffComHistItem().stream()).collect(Collectors.toList()).isEmpty()) {
				oneMonthApprovalStatusDto.setMessageID("Msg_875");
				return oneMonthApprovalStatusDto;
			}
			// 日の本人確認を取得する
			// 取得した「所属会社履歴項目」に当てはまらない対象者、対象日の「ルート状況」を取り除く
			for (String approvalId : listAppId) {
				// loop find approvalID
				for (AffCompanyHistImport affCompanyHistImport : listAffCompanyHistImport) {

					if (approvalId.equals(affCompanyHistImport.getEmployeeId())) {
						List<GeneralDate> listDate = new ArrayList<>();
						// loop list period
						List<AffComHistItemImport> listAffComHistItemImport = affCompanyHistImport
								.getLstAffComHistItem();
						for (AffComHistItemImport affComHistItem : listAffComHistItemImport) {
							GeneralDate startDate = affComHistItem.getDatePeriod().start();
							GeneralDate endDate = affComHistItem.getDatePeriod().end();
							if (startDate.after(datePeriod.end()) || endDate.before(datePeriod.start())) {
								break;
							}
							GeneralDate dateS = datePeriod.start();
							GeneralDate dateE = datePeriod.end();
							if (startDate.afterOrEquals(datePeriod.start())) {
								dateS = startDate;
							}
							if (endDate.beforeOrEquals(datePeriod.end())) {
								dateE = endDate;
							}

							for (GeneralDate date = dateS; date.beforeOrEquals(dateE);) {
								listDate.add(date);
								date = date.addDays(1);
							}
						}
						// get item by date and emp id
						for (ApprovalRootSituation approval : listApp) {
							for (GeneralDate date : listDate) {
								if (approval.getTargetID().equals(approvalId) && approval.getAppDate().equals(date)) {
									approvalRootSituations.add(approval);
								}
							}
						}
						break;
					}
				}
			}
		}

		approvalRootOfEmployeeImport.setApprovalRootSituations(approvalRootSituations);

		// クエリ「社員を並び替える(任意)」を実行する (Sort employee)
		String companyId = AppContexts.user().companyId();
		List<String> employeeList = approvalRootOfEmployeeImport.getApprovalRootSituations().stream().map(item -> {
			return item.getTargetID();
		}).collect(Collectors.toList());
		// list order conditions
		// lstEmployees =
		// this.regulationInfoEmployeeAdapter.sortEmployees(companyId,
		// employeeList,
		// this.createListConditions(),
		// this.convertFromDateToDateTime(datePeriod.end()));
		// [No.401]社員ID（List）と期間から個人情報を取得する - fix bug 107962
		EmployeeGeneralInfoImport employeeGeneralInfoImport = this.employeeGeneralInfoService
				.getEmployeeGeneralInfo(employeeList, new DatePeriod(datePeriod.end(), datePeriod.end()));

		// 社員ID（List）から社員コードと表示名を取得
		// RequestList228
		List<EmployeeDto> listEmployeeInfo = atEmployeeAdapter.getByListSID(employeeList);

		// 取得した社員所属情報と社員コード情報から「社員所属情報」＜List＞を作成する
		// 取得した社員所属情報と社員コード情報から「社員所属情報」＜List＞を作成する Start ↓
		List<EmployeeAffiliationInforDto> listEmpAffInfo = new ArrayList<>();
		for (EmployeeDto empQ : listEmployeeInfo) {
			EmployeeAffiliationInforDto approvalEmployee = new EmployeeAffiliationInforDto();

			approvalEmployee.setEmployeeCode(empQ.getScd());
			approvalEmployee.setEmployeeID(empQ.getSid());
			ExEmploymentHistoryImport exEmploymentHistoryImport = employeeGeneralInfoImport
					.getEmploymentHistoryImports().stream().filter(o -> o.getEmployeeId().equals(empQ.getSid()))
					.findFirst().get();
			approvalEmployee
					.setEmploymentInforCode(exEmploymentHistoryImport.getEmploymentItems().get(0).getEmploymentCode());
			ExClassificationHistoryImport classificationHistoryImport = employeeGeneralInfoImport
					.getExClassificationHistoryImports().stream().filter(o -> o.getEmployeeId().equals(empQ.getSid()))
					.findFirst().get();
			approvalEmployee.setClassificationCode(
					classificationHistoryImport.getClassificationItems().get(0).getClassificationCode());
			ExJobTitleHistoryImport exJobTitleHistoryImport = employeeGeneralInfoImport.getExJobTitleHistoryImports()
					.stream().filter(o -> o.getEmployeeId().equals(empQ.getSid())).findFirst().get();
			approvalEmployee.setPositionID(exJobTitleHistoryImport.getJobTitleItems().get(0).getJobTitleId());
			ExWorkPlaceHistoryImport exWorkPlaceHistoryImport = employeeGeneralInfoImport.getExWorkPlaceHistoryImports()
					.stream().filter(o -> o.getEmployeeId().equals(empQ.getSid())).findFirst().get();
			approvalEmployee.setWorkPlaceID(exWorkPlaceHistoryImport.getWorkplaceItems().get(0).getWorkplaceId());

			listEmpAffInfo.add(approvalEmployee);
		}
		// End ↑

		// 社員を並び替える

		// [No.560]職場IDから職場の情報をすべて取得する
		// hien tai dang goi den xu ly chung gan giong voi RQ560
		// 職場IDから職場の階層コードを取得する
		List<String> lstWorkplaceIds = new ArrayList<>();
		for (EmployeeAffiliationInforDto approvalId : listEmpAffInfo) {
			if (!lstWorkplaceIds.contains(approvalId.workPlaceID)) {
				lstWorkplaceIds.add(approvalId.workPlaceID);
			}
		}

		List<WorkplaceExportImport> exportImports = workplaceExportAdapter.getAllWkpConfig(companyId, lstWorkplaceIds,
				datePeriod.end());

		// 職位IDから職位を取得する
		// ドメインモデル「職位」を取得する Start ↓
		List<String> lstPositionIds = new ArrayList<>();
		for (EmployeeAffiliationInforDto approvalId : listEmpAffInfo) {
			if (!lstPositionIds.contains(approvalId.positionID)) {
				lstPositionIds.add(approvalId.positionID);
			}
		}
		List<JobTitleExport> jobTitleExport = this.configInfoAdapter.findAllById(companyId, lstPositionIds,
				datePeriod.end());
		// End ↑

		// ドメインモデル「職位情報」を取得する Start ↓
		List<JobTitleHistoryExport> lstJobHis = new ArrayList<>();
		jobTitleExport.forEach(x -> {
			lstJobHis.addAll(x.getJobTitleHistories());
		});
		List<JobTitleInfoImport> jobTitleInfoImports = new ArrayList<>();
		for (JobTitleHistoryExport jobtitle : lstJobHis) {
			String historyId = jobtitle.getHistoryId();
			List<JobTitleInfoImport> jobTitleInfos = this.infoAdapter.findByJobIds(companyId, lstPositionIds,
					historyId);
			jobTitleInfoImports.addAll(jobTitleInfos);
		}
		// End ↑

		// ドメインモデル「序列」をすべて取得する(Lấy tất cả Domain Model 「序列」) Start ↓
		if (!jobTitleInfoImports.isEmpty()) {
			Map<String, Integer> lstMasterImports = new HashMap<>();
			for (JobTitleInfoImport jobTitleInfo : jobTitleInfoImports) {
				String sequenceCode = jobTitleInfo.getSequenceCode();
				Map<String, Integer> masterImports = this.infoAdapter.findAll(companyId, sequenceCode).stream().collect(
						Collectors.toMap(SequenceMasterImport::getSequenceCode, SequenceMasterImport::getOrder));
				lstMasterImports.putAll(masterImports);
			}
			// End ↑
			// 取得したドメインモデル「職位情報」をドメインモデル「序列．並び順」で並び替える
			jobTitleInfoImports.forEach(e -> {
				if (lstMasterImports.containsKey(e.getSequenceCode()) && e.getSequenceCode() != null) {
					e.setOrder(lstMasterImports.get(e.getSequenceCode()));
				} else {
					// If can't find then set order is last
					e.setOrder(999);
				}
			});
			jobTitleInfoImports.sort((e1, e2) -> e1.getOrder() - e2.getOrder());
			// this.sortByOder(jobTitleInfoImports, masterImports);
			// Set SequenceCode from positionID
			List<String> lstSequenceCode = new ArrayList<>();
			for (EmployeeAffiliationInforDto affiliationInforDto : listEmpAffInfo) {
				String sequenceCd = affiliationInforDto.getSequenceCode();
				if (sequenceCd != null) {
					lstSequenceCode.add(sequenceCd);
				}
			}

			Map<String, List<JobTitleInfoImport>> sequenceCode = jobTitleInfoImports.stream()
					.collect(Collectors.groupingBy(x -> x.getJobTitleId()));
			listEmpAffInfo.forEach(e -> {
				e.setSequenceCode(sequenceCode.get(e.getPositionID()).get(0).getSequenceCode());
			});

			listEmpAffInfo.forEach(e -> {
				if (lstMasterImports.containsKey(e.getSequenceCode()) && e.getSequenceCode() != null) {
					e.setOrder(lstMasterImports.get(e.getSequenceCode()));
				} else {
					e.setOrder(999);
				}
			});
			listEmpAffInfo.sort((e1, e2) -> e1.getOrder() - e2.getOrder());
			// Set PositionCode from positionID
			Map<String, String> positionCode = jobTitleInfoImports.stream()
					.collect(Collectors.toMap(JobTitleInfoImport::getJobTitleId, JobTitleInfoImport::getJobTitleCode));
			listEmpAffInfo.forEach(e -> {
				if (positionCode.containsKey(e.getPositionID())) {
					e.setPositionCd(positionCode.get(e.getPositionID()));
				}
			});
		}

		// Get HierarchyCD
		List<String> lsthierarchyCD = new ArrayList<>();
		for (WorkplaceExportImport hierarchyCD : exportImports) {
			String positionId = hierarchyCD.getHierarchyCd();
			if (positionId != null) {
				lsthierarchyCD.add(positionId);
			}
		}

		if (!lsthierarchyCD.isEmpty()) {
			Map<String, List<WorkplaceExportImport>> hierarchyCD = exportImports.stream()
					.collect(Collectors.groupingBy(x -> x.getWorkplaceId()));
			listEmpAffInfo.forEach(e -> {
				e.setHierarchyCd(hierarchyCD.get(e.getWorkPlaceID()).get(0).getHierarchyCd());
			});
		}
		Comparator<String> strcmp = Comparator.nullsLast(Comparator.naturalOrder());
		// INPUT．「並び替える条件」を並び替える
		// 1.職場：階層コード（ASC）
		// 2.雇用：雇用コード（ASC）
		// 3.分類：分類コード（ASC）
		// 4.職位：序列．並び順（ASC） ⇒ 職位コード（ASC）
		// 5.社員コード：社員コード（ASC）
		List<EmployeeAffiliationInforDto> listEmpAffInfos = listEmpAffInfo.stream()
				.sorted(Comparator.comparing(EmployeeAffiliationInforDto::getHierarchyCd, strcmp)
						.thenComparing(EmployeeAffiliationInforDto::getEmploymentInforCode, strcmp)
						.thenComparing(EmployeeAffiliationInforDto::getClassificationCode, strcmp)
						.thenComparing(EmployeeAffiliationInforDto::getOrder)
						.thenComparing(EmployeeAffiliationInforDto::getPositionCd, strcmp)
						.thenComparing(EmployeeAffiliationInforDto::getEmployeeCode, strcmp))
				.collect(Collectors.toList());
		// 並び替えた後の「社員所属情報」を返す
		listEmpAffInfos.forEach(x -> {
			lstEmployees.add(x.getEmployeeID());
		});

		// アルゴリズム「表示する承認者の集計」を実行する
		// 対応するImported「（就業）社員」をすべて取得する -requestList31-2
		// Map<String, String> mapEmp =
		// this.shareEmploymentAdapter.findEmpHistoryVer2(companyId,
		// lstEmployees, datePeriod.end())
		// .entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
		// e -> e.getValue().getEmploymentCode()));
		// アルゴリズム「社員をしぼり込む」を実行する
		// ドメインモデル「雇用に紐づく就業締め」を取得する
		List<ClosureEmployment> lstClosureEmployment = closureEmploymentRepository
				.findByClosureId(AppContexts.user().companyId(), closureId);
		lstEmploymentCd.addAll(lstClosureEmployment.stream().map(closureEmployment -> {
			return closureEmployment.getEmploymentCD();
		}).collect(Collectors.toList()));

		// 対応するすべての社員を取得する
		// List<String> listSid = mapEmp.entrySet().stream().filter(x ->
		// lstEmploymentCd.contains(x.getValue()))
		// .map(x -> {
		// return x.getKey();
		// }).collect(Collectors.toList());

		// 対象期間に対象の締めに紐付いた雇用に属しているかチェックする
		List<EmploymentHistImport> lstEmpHist = employmentHistAdapter.findBySidDatePeriod(lstEmployees, datePeriod)
				.stream().filter(x -> lstEmploymentCd.contains(x.getEmploymentCode())).collect(Collectors.toList());
		ApprovalRootOfEmployeeImport approvalRootOfEmployeeImportTemp = new ApprovalRootOfEmployeeImport(
				approvalRootOfEmployeeImport.getEmployeeStandard(), approvalRootSituations);
		List<ApprovalRootSituation> approvalRootSituationsTemp = new ArrayList<>();
		approvalRootOfEmployeeImport.getApprovalRootSituations().stream().forEach(x -> {
			val lstFilter = lstEmpHist.stream().filter(y -> {
				return y.getEmployeeId().equals(x.getTargetID()) && x.getAppDate().afterOrEquals(y.getPeriod().start())
						&& x.getAppDate().beforeOrEquals(y.getPeriod().end());
			}).collect(Collectors.toList());
			if (!lstFilter.isEmpty()) {
				approvalRootSituationsTemp.add(x);
			}
		});
		approvalRootOfEmployeeImportTemp.setApprovalRootSituations(approvalRootSituationsTemp);
		// Fix bug 107962 - comment vi da goi 228 o phia tren
		// RequestList228
		// List<EmployeeDto> listEmployeeInfo =
		// atEmployeeAdapter.getByListSID(lstEmployees);
		String empLogin = AppContexts.user().employeeId();
		List<String> lstEmpId = approvalRootSituationsTemp.stream().map(x -> x.getTargetID()).distinct().collect(Collectors.toList());
		iFindDataDCRecord.clearAllStateless();
		List<ApprovalStatusActualResult> lstApproval = approvalStatusActualDayChange.processApprovalStatus(companyId,
				empLogin, lstEmpId, Optional.of(datePeriod), Optional.empty(), ModeData.APPROVAL.value,false);
		List<ConfirmStatusActualResult> lstConfirm = confirmStatusActualDayChange.processConfirmStatus(companyId,
				empLogin, lstEmpId, Optional.of(datePeriod), Optional.empty(),false);
		iFindDataDCRecord.clearAllStateless();
		List<ApprovalEmployeeDto> buildApprovalEmployeeData = buildApprovalEmployeeData(listEmployeeInfo, lstApproval,
				lstConfirm);
		if (buildApprovalEmployeeData.isEmpty()) {
			oneMonthApprovalStatusDto.setMessageID("Msg_875");
			return oneMonthApprovalStatusDto;
			// throw new BusinessException("Msg_875");
		}
		// fix bug 91363
		List<ApprovalEmployeeDto> buildApprovalEmployeeDataResult = new ArrayList<>();
		lstEmployees.forEach(item -> {
			if (buildApprovalEmployeeData.stream().filter(o -> o.getEmployeeId().equals(item)).findFirst()
					.isPresent()) {
				buildApprovalEmployeeDataResult.add(buildApprovalEmployeeData.stream()
						.filter(o -> o.getEmployeeId().equals(item)).findFirst().get());
			}
		});
		oneMonthApprovalStatusDto.setLstEmployee(buildApprovalEmployeeDataResult);

		return oneMonthApprovalStatusDto;
	}
}
