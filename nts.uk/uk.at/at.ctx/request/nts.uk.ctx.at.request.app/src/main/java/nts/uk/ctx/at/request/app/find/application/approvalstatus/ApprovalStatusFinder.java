package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.i18n.I18NText;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.request.app.command.application.approvalstatus.ApprovalStatusMailTempCommand;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.applist.service.AppCompltLeaveSync;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppContentDetailCMM045;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.ScreenAtr;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailTemp;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailType;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.AggregateApprovalStatus;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.ApprovalStatusService;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.InitDisplayOfApprovalStatus;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApplicationsListOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttEmp;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprovalStatusEmployeeOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprovalSttAppDetail;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprovalSttAppOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprovalSttByEmpListOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApproverOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.DisplayWorkplace;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.SendMailResultOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.UnApprovalSendMail;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.UnConfrSendMailParam;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.WorkplaceInfor;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalBehaviorAtrImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailholidayinstruction.Content;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailholidayinstruction.Subject;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ApprovalComfirmDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureHistoryForComDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosuresDto;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.WorkPlaceConfigImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.WorkplaceConfigAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceConfigInfoAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceHierarchyImport;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
/**
 * 承認状況メールテンプレート
 * 
 * @author dat.lh
 */
public class ApprovalStatusFinder {

	/** The repository. */
	@Inject
	private ClosureRepository closureRepository;

	@Inject
	ClosureEmploymentRepository closureEmpRepo;

	@Inject
	private ApprovalStatusService appSttService;

	@Inject
	private ApprovalStatusService approvalStatusSv;
	
	@Inject
	private WorkplaceConfigAdapter configAdapter;
	@Inject
	private WorkplaceConfigInfoAdapter configInfoAdapter;
	
	@Inject
	private AppContentDetailCMM045 contentDtail;
	@Inject
	private WorkTypeRepository repoWorkType;
	@Inject
	private WorkTimeSettingRepository repoworkTime;
	
	/**
	 * refactor 5
	 * アルゴリズム「承認状況本文起動」を実行する
	 */
	public List<ApprovalStatusMailTempDto> getMailTemp() {
		// 会社ID
		String cid = AppContexts.user().companyId();
		List<ApprovalStatusMailTempDto> listMail = new ArrayList<ApprovalStatusMailTempDto>();

		listMail.add(this.getApprovalStatusMailTemp(cid, ApprovalStatusMailType.APP_APPROVAL_UNAPPROVED.value));
		listMail.add(this.getApprovalStatusMailTemp(cid, ApprovalStatusMailType.DAILY_UNCONFIRM_BY_PRINCIPAL.value));
		listMail.add(this.getApprovalStatusMailTemp(cid, ApprovalStatusMailType.DAILY_UNCONFIRM_BY_CONFIRMER.value));
		listMail.add(this.getApprovalStatusMailTemp(cid, ApprovalStatusMailType.MONTHLY_UNCONFIRM_BY_PRINCIPAL.value));
		listMail.add(this.getApprovalStatusMailTemp(cid, ApprovalStatusMailType.MONTHLY_UNCONFIRM_BY_CONFIRMER.value));
		listMail.add(this.getApprovalStatusMailTemp(cid, ApprovalStatusMailType.WORK_CONFIRMATION.value));
		return listMail;
	}
	
	/**
	 * refactor 5
	 * @param cid
	 * @param mailType
	 * @return
	 */
	private ApprovalStatusMailTempDto getApprovalStatusMailTemp(String cid, int mailType) {
		// アルゴリズム「承認状況メール本文取得」を実行する
		ApprovalStatusMailTemp domain = appSttService.getApprovalStatusMailTemp(mailType);
		// ドメインの取得
		if (Objects.isNull(domain)) {
			// ドメインが取得できなかった場合
			// 画面モード ＝ 新規
			return new ApprovalStatusMailTempDto(mailType, null, null, null, "", "", 0);
		}
		// ドメインが取得できた場合(lấy được)
		// 画面モード ＝ 更新
		return ApprovalStatusMailTempDto.fromDomain(domain, mailType);
	}

	/**
	 * 承認状況メールテスト送信
	 */
	public String confirmSenderMail() {
		// アルゴリズム「承認状況送信者メール確認」を実行する
		return appSttService.confirmApprovalStatusMailSender();
	}

	public SendMailResultOutput sendTestMail(int mailType) {
		// アルゴリズム「承認状況メールテスト送信実行」を実行する
		return appSttService.sendTestMail(mailType);
	}

	
	/**
	 * アルゴリズム「承認状況指定締め日取得」を実行する Acquire approval situation designated closing
	 * 承認状況起動
	 * date
	 * 
	 * @return approval situation
	 */
	public ApprovalComfirmDto findAllClosure() {
		// Get companyID.
		String companyId = AppContexts.user().companyId();
		GeneralDate startDate = null;
		GeneralDate endDate = null;
		int processingYm = 0;
		// ドメインモデル「就業締め日」を取得する <shared>
		List<Closure> closureList = this.closureRepository.findAllActive(companyId, UseClassification.UseClass_Use);
		int selectedClosureId = 0;
		List<ClosuresDto> closureDto = this.getClosure(closureList);

		// 就業締め日（リスト）の先頭の締めIDを選択
		List<String> listEmpCode = new ArrayList<>();   
		Optional<ClosuresDto> closure = closureDto.stream().findFirst();
		if (closure.isPresent()) {
			val closureId = closure.get().getClosureId();
			selectedClosureId = closureId;
			val closureOpt = this.closureRepository.findById(companyId, closureId);
			if (closureOpt.isPresent()) {
				val closureItem = closureOpt.get();
				// 当月の期間を算出する
				val yearMonth = closureItem.getClosureMonth().getProcessingYm();
				processingYm = yearMonth.v();
				// アルゴリズム「承認状況指定締め期間設定」を実行する
				// アルゴリズム「当月の期間を算出する」を実行する
				// DatePeriod closurePeriod = this.closureService.getClosurePeriod(closureId, yearMonth);
				DatePeriod closurePeriod = null;
				startDate = closurePeriod.start();
				endDate = closurePeriod.end();
				// ドメインモデル「雇用に紐づく就業締め」より、雇用コードと締めIDを取得する
				List<ClosureEmployment> listEmployee = closureEmpRepo.findByClosureId(companyId, closureId);
				for (ClosureEmployment emp : listEmployee) {
					listEmpCode.add(emp.getEmploymentCD());
				}
			} else {
				throw new RuntimeException("Could not find closure");
			}
		}
		return new ApprovalComfirmDto(selectedClosureId, closureDto, startDate, endDate, processingYm, listEmpCode);
	}
	/**
	 * アルゴリズム「承認状況指定締め期間設定」を実行する
	 * 
	 * @param closureId
	 * @param closureDate
	 * @return
	 */
	public ApprovalStatusPeriorDto getApprovalStatusPerior(int closureId) {
		// Get companyID.
		String companyId = AppContexts.user().companyId();
		GeneralDate startDate = null;
		GeneralDate endDate = null;
		int processingYmNew = 0;
		List<ClosureEmployment> listEmployee = new ArrayList<>();
		Optional<Closure> closure = closureRepository.findById(companyId, closureId);
		if (!closure.isPresent()) {
			throw new RuntimeException("Could not find closure");
		}
		List<Closure> closureList = new ArrayList<>();
		closureList.add(closure.get());
		List<ClosuresDto> lstClosureDto = this.getClosure(closureList);
		String closureName = lstClosureDto.stream().findFirst().get().getCloseName();
		val yearMonth = closure.get().getClosureMonth().getProcessingYm();
		processingYmNew = yearMonth.v();
		// 当月の期間を算出する
		YearMonth processingYm = new YearMonth(processingYmNew);
		// アルゴリズム「当月の期間を算出する」を実行する
		// DatePeriod closurePeriod = this.closureService.getClosurePeriod(closureId, processingYm);
		DatePeriod closurePeriod = null;
		startDate = closurePeriod.start();
		endDate = closurePeriod.end();
		// ドメインモデル「雇用に紐づく就業締め」より、雇用コードと締めIDを取得する
		listEmployee = closureEmpRepo.findByClosureId(companyId, closureId);
		List<String> listEmpCode = new ArrayList<>();
		for (ClosureEmployment emp : listEmployee) {
			listEmpCode.add(emp.getEmploymentCD());
		}
		return new ApprovalStatusPeriorDto(startDate, endDate, listEmpCode, processingYmNew, closureName);
	}
	//EA2137 - hoatt
	//※締めIDごとの最新状態を取得
	//締め.当月＞＝締め変更履歴.開始年月
	//締め.当月＜＝締め変更履歴.終了年月
	//の締め変更履歴を取得
	private List<ClosuresDto> getClosure(List<Closure> closureList) {
		List<ClosuresDto> lstResult = new ArrayList<>();
		for (Closure closure : closureList) {
			int closureId = closure.getClosureId().value;
			List<ClosureHistoryForComDto> lstHistDto = new ArrayList<>();
			List<ClosureHistory> closureHistoriesList = closure.getClosureHistories();
			//for theo history
			for (ClosureHistory hist : closureHistoriesList) {
				if(hist.getStartYearMonth().lessThanOrEqualTo(closure.getClosureMonth().getProcessingYm()) &&
						hist.getEndYearMonth().greaterThanOrEqualTo(closure.getClosureMonth().getProcessingYm())){
					lstHistDto.add(new ClosureHistoryForComDto(hist.getClosureName().v(), hist.getClosureId().value,
							hist.getEndYearMonth().v().intValue(), hist.getClosureDate().getClosureDay().v().intValue(),
							hist.getStartYearMonth().v().intValue()));
				}
			}
			if(!lstHistDto.isEmpty()){
				lstResult.add(new ClosuresDto(closureId, lstHistDto.get(0).getCloseName(), lstHistDto.get(0).getClosureDate()));
			}
		}
		return lstResult;
		
//		List<ClosuresDto> lstClosureDto = closureList.stream().map(x -> {
//			int closureId = x.getClosureId().value;
//			List<ClosureHistoryForComDto> closureHistoriesList = x.getClosureHistories().stream().map(x1 -> {
//				return new ClosureHistoryForComDto(x1.getClosureName().v(), x1.getClosureId().value,
//						x1.getEndYearMonth().v().intValue(), x1.getClosureDate().getClosureDay().v().intValue(),
//						x1.getStartYearMonth().v().intValue());
//			}).collect(Collectors.toList());
//			ClosureHistoryForComDto closureHistories = closureHistoriesList.stream()
//					.filter(x2 -> x2.getClosureId() == closureId).findFirst().orElse(null);
//			return new ClosuresDto(closureId, closureHistories.getCloseName(), closureHistories.getClosureDate());
//		}).collect(Collectors.toList());
//		return lstClosureDto;
	}
	
	@Inject
	private AggregateApprovalStatus aggregateApprovalStatus;
	
	@Inject
	private ManagedParallelWithContext parallel;
	
	/**
	 * アルゴリズム「承認状況職場別起動」を実行する
	 * 
	 * @param appStatus
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ApprovalSttAppOutput> getAppSttByWorkpace(ApprovalStatusActivityData appStatus) {
		List<ApprovalSttAppOutput> listAppSttApp = Collections.synchronizedList(new ArrayList<>());
		GeneralDate startDate = appStatus.getStartDate();
		GeneralDate endDate = appStatus.getEndDate();
		String companyId = AppContexts.user().companyId();
		List<WorkplaceInfor> listWorkPlaceInfor = appStatus.getListWorkplace();
		
		
		this.parallel.forEach(listWorkPlaceInfor, wkp -> {
			// アルゴリズム「承認状況取得職場社員」を実行する
			List<ApprovalStatusEmployeeOutput> listAppStatusEmp = appSttService.getApprovalStatusEmployee(wkp.getCode(),
					startDate, endDate, appStatus.getListEmpCd());
			
			// アルゴリズム「承認状況取得申請承認」を実行する
			ApprovalSttAppOutput approvalSttApp = this.aggregateApprovalStatus.aggregate(companyId, wkp, listAppStatusEmp);
			listAppSttApp.add(approvalSttApp);
		});
		//vì sử dụng parallel (bất đồng bộ) nên phải sắp xếp sau
		// 「職場IDから階層コードを取得する」を実行する
		// List<String> wPIDs = listWorkPlaceInfor.stream().map(WorkplaceInfor::getCode).collect(Collectors.toList());
		// List<WorkplaceHierarchyImport> wpHis = GetHCodeByWorkPlaceID(companyId, wPIDs, GeneralDate.today());
		
		// [No.560]職場IDから職場の情報をすべて取得する
		List<String> wPIDs = listWorkPlaceInfor.stream().map(WorkplaceInfor::getCode).collect(Collectors.toList());
		List<WorkplaceHierarchyImport> wpHis = this.configInfoAdapter.getWorkplaceInforByWkpIds(companyId, wPIDs, GeneralDate.today())
				.stream().map(item -> new WorkplaceHierarchyImport(item.getWorkplaceId(), item.getHierarchyCode())).collect(Collectors.toList());
		
		// 取得した「職場ID、職場階層コード」を階層コード順に並び替える
		List<ApprovalSttAppOutput> result = sortList(wpHis, listAppSttApp);

		return result;
	}

	public List<ApprovalSttAppOutput> sortList(List<WorkplaceHierarchyImport> wpHis, List<ApprovalSttAppOutput> listAppSttApp) {
		List<ApprovalSttAppOutput> result = new ArrayList<>();
		List<WorkplaceHierarchyImport> sortedList = new ArrayList<>();
		List<WorkplaceHierarchyImport> HCodeList = wpHis.stream()
				.filter(x -> !StringUtil.isNullOrEmpty(x.getHierarchyCode(), false)).collect(Collectors.toList());
		List<WorkplaceHierarchyImport> HCodeEmptyList = wpHis.stream()
				.filter(x -> StringUtil.isNullOrEmpty(x.getHierarchyCode(), false)).collect(Collectors.toList());
		HCodeList = HCodeList.stream().sorted(Comparator.comparing(WorkplaceHierarchyImport::getHierarchyCode))
				.collect(Collectors.toList());
		HCodeEmptyList = HCodeEmptyList.stream().sorted(Comparator.comparing(WorkplaceHierarchyImport::getWorkplaceId))
				.collect(Collectors.toList());
		sortedList.addAll(HCodeList);
		sortedList.addAll(HCodeEmptyList);
		
		sortedList.forEach(x -> {
			listAppSttApp.stream().filter(appStt -> appStt.getWorkplaceId().equals(x.getWorkplaceId())).findFirst()
					.ifPresent(item -> {
						result.add(new ApprovalSttAppOutput(item.getWorkplaceId(), item.getWorkplaceName(),
								item.isEnabled(), item.isChecked(), item.getNumOfApp(), item.getApprovedNumOfCase(),
								item.getNumOfUnreflected(), item.getNumOfUnapproval(), item.getNumOfDenials()));
					});
		});
		
		return result;
	}

	/**
	 * 職場IDから階層コードを取得する
	 * @param baseDate 
	 * @param wPIDs 
	 * @param companyId 
	 * @return 
	 */
	public List<WorkplaceHierarchyImport> GetHCodeByWorkPlaceID(String companyId, List<String> wPIDs,
			GeneralDate baseDate) {

		List<WorkplaceHierarchyImport> result = new ArrayList<>();
		// ドメインモデル「職場構成」を取得する
		Optional<WorkPlaceConfigImport> configOpt = this.configAdapter.findByBaseDate(companyId, baseDate);
		if (configOpt.isPresent()) {
			// ドメインモデル「職場構成情報」を取得する
			WorkPlaceConfigImport config = configOpt.get();
			if (!CollectionUtil.isEmpty(config.getWkpConfigHistory())) {
				String historyId = config.getWkpConfigHistory().get(0).getHistoryId();
				result = this.configInfoAdapter.findByHistoryIdsAndWplIds(companyId, Arrays.asList(historyId), wPIDs)
						.stream().flatMap(x -> x.getLstWkpHierarchy().stream()).collect(Collectors.toList());
			}
		}
		return result;

	}

	/**
	 * アルゴリズム「承認状況未承認メール送信」を実行する
	 */
	public List<String> getAppSttSendingUnapprovedMail(List<UnApprovalSendMail> listAppSttApp) {
		return appSttService.getAppSttSendingUnapprovedMail(listAppSttApp);

	}
	/**
	 * @author hoatt
	 * KAF018 - E
	 * 承認状況未確認メール送信
	 */
	public boolean checkSendUnConfMail(List<UnConfrSendMailParam> listWkp) {
		//hoatt - 2018.10.24
//		2018/10/24　EA2864
//		#102263
		// アルゴリズム「承認状況メール本文取得」を実行する
		//input： ・メール種類　＝　日別未確認(本人)
		ApprovalStatusMailTemp domain = approvalStatusSv.getApprovalStatusMailTemp(ApprovalStatusMailType.DAILY_UNCONFIRM_BY_PRINCIPAL.value);
		//対象が存在しない場合
		if(domain == null){
			//メッセージ（#Msg_1458）を表示する
			throw new BusinessException("Msg_1458");
		}
		// アルゴリズム「承認状況未確認メール送信実行チェック」を実行する
		int count = 0;
		for (UnConfrSendMailParam wkp : listWkp) {
			count = count + wkp.getIsCheckOn();
		}
		if (count == 0) {
			throw new BusinessException("Msg_794");
		}
		return false;

	}
	/**
	 * アルゴリズム「承認状況未承認メール送信実行」を実行する
	 */
	public SendMailResultOutput exeSendUnconfirmedMail(UnAppMailTransmisDto unAppMail) {
		return appSttService.exeSendUnconfirmedMail(unAppMail.getListWkpId(), unAppMail.getClosureStart(),
				unAppMail.getClosureEnd(), unAppMail.getListEmpCd());
	}

	/**
	 * アルゴリズム「承認状況社員別起動」を実行する
	 */
	public List<ApprovalSttByEmpListOutput> initApprovalSttByEmployee(ApprovalStatusByIdDto appSttById) {
		return appSttService.getApprovalSttById(appSttById.getSelectedWkpId(), appSttById.getListWkpId(),
				appSttById.getStartDate(), appSttById.getEndDate(), appSttById.getListEmpCode());
	}

	/**
	 * アルゴリズム「承認状況申請内容表示」を実行する
	 */
	public ApplicationListDto initApprovalSttRequestContentDis(ApprovalSttRequestContentDis appSttContent) {
		String companyID = AppContexts.user().companyId();
		ApplicationsListOutput appList = appSttService
				.initApprovalSttRequestContentDis(appSttContent.getListStatusEmp());
//		HdAppSetDto hdAppSetDto = HdAppSetDto.convertToDto(appList.getLstHdAppSet().get());
		List<ApplicationDetailDto> listApplicationDetail = new ArrayList<>();
		List<ApprovalSttAppDetail> listAppSttDetail = appList.getApprovalSttAppDetail();
		List<AppCompltLeaveSync> lstCompltLeaveSync = appList.getListSync();
		
		//Lay List workType/workTime
		List<WorkType> lstWkType = new ArrayList<>();
		List<WorkTimeSetting> lstWkTime = new ArrayList<>();
		//Chi 6,7,10,11 moi su dung den workType va workTime
		if(!listAppSttDetail.isEmpty()){
			lstWkType = repoWorkType.findListByCid(companyID);
			lstWkTime =  repoworkTime.findByCId(companyID);
		}
		
		for (ApprovalSttAppDetail app : listAppSttDetail) {
			ApplicationDetailDto detail = new ApplicationDetailDto();
			
			int detailSet = app.getDetailSet();
			ApplicationType appType = app.getAppDispName().getAppType();
			ApplicationDto_New applicaton_N = ApplicationDto_New.fromDomain(app.getAppContent().getApplication());
			//listApp.add(applicaton_N);
			String appId = applicaton_N.getApplicationID();
			detail.setAppType(appType.value);
			detail.setAppStartDate(applicaton_N.getStartDate());
			detail.setAppEndDate(applicaton_N.getEndDate());
			detail.setAppName(app.getAppDispName().getDispName().v());
			detail.setPrePostAtr(applicaton_N.getPrePostAtr());
			detail.setApplicationDate(applicaton_N.getApplicationDate());
			detail.setApplicationID(applicaton_N.getApplicationID());
			List<ApprovalPhaseStateImport_New> listApprovalPhase = app.getAppContent().getApprRootContentExport()
					.getApprovalRootState().getListApprovalPhaseState();
			listApprovalPhase.sort((ApprovalPhaseStateImport_New x1,
					ApprovalPhaseStateImport_New x2) -> x1.getPhaseOrder() - x2.getPhaseOrder());
			List<Integer> appStatus = new ArrayList<>();
			for (ApprovalPhaseStateImport_New appPhase : listApprovalPhase) {
				ApprovalBehaviorAtrImport_New appBehavior = appPhase.getApprovalAtr();
				switch (appBehavior) {
				case UNAPPROVED:
					appStatus.add(0);
					break;
				case APPROVED:
					appStatus.add(1);
					break;
				case REMAND:
					appStatus.add(0);
					break;
				case DENIAL:
					appStatus.add(2);
					break;
				default:
					break;
				}
			}
			detail.setApprovalStatus(appStatus);
			List<ApproverOutput> listApprover = app.getListApprover();
			for (ApproverOutput approver : listApprover) {
				int phase = approver.getPhase();
				String numOfPerson = approver.getNumOfPeople() > 0
						? ("確定者" + I18NText.getText("KAF018_47", approver.getNumOfPeople().toString())) : "";
				String others = approver.getEmpName() + numOfPerson;
				switch (phase) {
				case 1:
					detail.setPhase1(others);
					break;
				case 2:
					detail.setPhase2(others);
					break;
				case 3:
					detail.setPhase3(others);
					break;
				case 4:
					detail.setPhase4(others);
					break;
				case 5:
					detail.setPhase5(others);
					break;
				default:
					break;
				}
			}
			String appContent = "";
			switch (appType) {
			// 残業申請
			case OVER_TIME_APPLICATION:
				appContent = contentDtail.getContentOverTimeBf(null, companyID, appId, detailSet, 0, "", ScreenAtr.KAF018.value);
				break;
			// 休暇申請
			case ABSENCE_APPLICATION:
				Application_New a = app.getAppContent().getApplication();
				Integer day = 0;
				if(a.getStartDate().isPresent()&& a.getEndDate().isPresent()){
					day = a.getStartDate().get().daysTo(a.getEndDate().get()) + 1;
				}
				appContent = contentDtail.getContentAbsence(null, companyID, appId, 0, "", day, ScreenAtr.KAF018.value, lstWkType, lstWkTime);
				break;
			// 勤務変更申請
			case WORK_CHANGE_APPLICATION:
//				appContent = contentDtail.getContentWorkChange(null, companyID, appId, 0, "", ScreenAtr.KAF018.value, lstWkType, lstWkTime);
				appContent = "";
				// có endDate
				break;
			// 出張申請
			case BUSINESS_TRIP_APPLICATION:
				// TODO
				// có endDate
				break;
			// 直行直帰申請
			case GO_RETURN_DIRECTLY_APPLICATION:
//				appContent = contentDtail.getContentGoBack(null, companyID, appId, 0, "", ScreenAtr.KAF018.value);
				appContent = "";
				break;
			// 休出時間申請
			case HOLIDAY_WORK_APPLICATION:
				appContent = contentDtail.getContentHdWorkBf(null, companyID, appId, 0, "", ScreenAtr.KAF018.value, lstWkType, lstWkTime);
				break;
			// 打刻申請
			case STAMP_APPLICATION:
				// TODO
				break;
			// 時間年休申請
			case ANNUAL_HOLIDAY_APPLICATION:

				break;
			// 遅刻早退取消申請
			case EARLY_LEAVE_CANCEL_APPLICATION:
				// TODO
				break;
			// 振休振出申請
			case COMPLEMENT_LEAVE_APPLICATION:
				appContent = contentDtail.getContentComplt(null, companyID, appId, 0, "", ScreenAtr.KAF018.value, lstWkType);
				break;

			default:
				break;
			}
			detail.setAppContent(appContent);
			int reflectState = applicaton_N.getReflectPerState();
			detail.setReflectState(reflectState);
			listApplicationDetail.add(detail);
		}
		return new ApplicationListDto(listApplicationDetail, lstCompltLeaveSync, appList.isDisplayPrePostFlg());
	}
	
	// refactor 5
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.A:状況照会条件入力.アルゴリズム.A:承認状況起動.A:承認状況起動
	 */
	public ApprSttSpecDeadlineDto getApprovalStatusActivation(Integer selectClosureId) {
		String companyID = AppContexts.user().companyId();
		// 「新規起動」か「戻り起動」か判別
		// xử lý trên UI
		// ドメインモデル「就業締め日」を取得する　<shared>(Lấy domain 「就業締め日」)
		List<Closure> closureList = this.closureRepository.findAllActive(companyID, UseClassification.UseClass_Use);
		// アルゴリズム「承認状況指定締め日取得」を実行する(Thực hiện thuật toán[lấy ngày chốt chỉ định trạng thái approval])
		ApprSttSpecDeadlineDto apprSttSpecDeadlineDto = this.getApprovalStatusSpecDeadline(selectClosureId, closureList);
		// アルゴリズム「承認状況日別実績利用確認」を実行する(Thực hiện[confirm sử dụng 日別実績 trạng thái approval ])
		// context khác, xử lý trên UI
		// ユーザ固有情報「承認状況照会の初期表示」をチェックする
		// xử lý trên UI
		return apprSttSpecDeadlineDto;
	}
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.A:状況照会条件入力.ユースケース.締め区分を変更する
	 * @param selectClosureId
	 * @return
	 */
	public ApprSttSpecDeadlineSetDto changeClosure(Integer selectClosureId) {
		String companyId = AppContexts.user().companyId();
		// ドメインモデル「締め」を取得する(get domain[closure])
		Closure closure = closureRepository.findClosureHistory(companyId, selectClosureId, UseClassification.UseClass_Use.value).get();
		// アルゴリズム「承認状況指定締め期間設定」を実行する(Thực hiện thuật toán 「承認状況指定締め期間設定」)
		return this.getApprovalStatusSpecDeadlineSet(selectClosureId, closure);
	}
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.A:状況照会条件入力.アルゴリズム.A:承認状況指定締め日取得.A:承認状況指定締め日取得
	 * @return
	 */
	public ApprSttSpecDeadlineDto getApprovalStatusSpecDeadline(Integer selectClosureId, List<Closure> closureList) {
		// ユーザー固有情報「選択中の就業締め」を取得する(lấy thông tin user đã có 「選択中の就業締め」)
		// 締めIDが取得ができた(Lấy được closeID)
		Closure closure = null;
		if(selectClosureId!=null) {
			// 就業締め日（リスト）より取得した締めIDと同じ締めID内容を選択(Chọn nội dung closeID ứng với closeID đã lấy từ list close Date)
			closure = closureList.stream().filter(x -> x.getClosureId().value==selectClosureId).findAny().orElse(closureList.get(0));
		} else {
			// 就業締め日（リスト）の先頭の締めIDを選択(Chọn closeID trên cùng trong list CloseDate)
			closure = closureList.get(0);
		}
		// アルゴリズム「承認状況指定締め期間設定」を実行する(Thực hiện thuật toán[setting thời gian close chỉ định trạng thái approval])
		ApprSttSpecDeadlineSetDto apprSttSpecDeadlineSetDto = this.getApprovalStatusSpecDeadlineSet(closure.getClosureId().value, closure);
		return new ApprSttSpecDeadlineDto(
				closureList.stream().map(x -> ClosureDto.fromDomain(x)).collect(Collectors.toList()), 
				apprSttSpecDeadlineSetDto.getStartDate(),
				apprSttSpecDeadlineSetDto.getEndDate(),
				apprSttSpecDeadlineSetDto.getListEmploymentCD());
	}
	
	/**
	 * refactor 5
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.A:状況照会条件入力.アルゴリズム.A:承認状況指定締め期間設定.A:承認状況指定締め期間設定
	 * @param closureId 締めID
	 * @param closure 就業締め日（ドメインモデル）
	 * @return
	 */
	public ApprSttSpecDeadlineSetDto getApprovalStatusSpecDeadlineSet(int closureId, Closure closure) {
		String companyId = AppContexts.user().companyId();
		// アルゴリズム「当月の期間を算出する」を実行する(Thực hiện[tính thời gian this month])
		DatePeriod datePeriodClosure = ClosureService.getClosurePeriod(
				ClosureService.createRequireM1(closureRepository, closureEmpRepo),
				closure.getClosureId().value, closure.getClosureMonth().getProcessingYm());
		// ドメインモデル「雇用に紐づく就業締め」より、雇用コードと締めIDを取得する
		List<String> listEmploymentCD = closureEmpRepo.findByClosureId(companyId, closureId)
				.stream().map(x -> x.getEmploymentCD()).collect(Collectors.toList());
		return new ApprSttSpecDeadlineSetDto(
				datePeriodClosure.start().toString(),
				datePeriodClosure.end().toString(),
				listEmploymentCD);
	}
	
	
	// refactor 5
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.B:承認・確認状況の照会.アルゴリズム.B:状況取得_表示処理.B:状況取得_表示処理
	 * @param param
	 * @return
	 */
	public List<ApprSttExecutionDto> getStatusExecution(ApprSttExecutionParam param) {
		ClosureId closureId = EnumAdaptor.valueOf(param.getClosureId(), ClosureId.class);
		YearMonth processingYm = new YearMonth(param.getProcessingYm());
		DatePeriod period = new DatePeriod(GeneralDate.fromString(param.getStartDate(), "yyyy/MM/dd"), GeneralDate.fromString(param.getEndDate(), "yyyy/MM/dd"));
		InitDisplayOfApprovalStatus initDisplayOfApprovalStatus = param.getInitDisplayOfApprovalStatus();
		List<DisplayWorkplace> displayWorkplaceLst = param.getWkpInfoLst();
		List<String> employmentCDLst = param.getEmploymentCDLst();
		return appSttService.getStatusExecution(closureId, processingYm, period, initDisplayOfApprovalStatus, displayWorkplaceLst, employmentCDLst)
				.stream().map(x -> ApprSttExecutionDto.fromDomain(x)).collect(Collectors.toList());
	}
	
	public List<ApprSttEmp> getApprSttStartByEmp(ApprSttEmpParam param) {
		return appSttService.getApprSttStartByEmp(
				param.getWkpID(),
				new DatePeriod(GeneralDate.fromString(param.getStartDate(), "yyyy/MM/dd"), GeneralDate.fromString(param.getEndDate(), "yyyy/MM/dd")),
				param.getEmpPeriodLst().stream().map(x -> x.toDomain()).collect(Collectors.toList()));
	}
	
	public ApprSttSendMailInfoDto getApprSttSendMailInfo(ApprSttSendMailInfoParam param) {
		ApprovalStatusMailType mailType = EnumAdaptor.valueOf(param.getMailType(), ApprovalStatusMailType.class);
		ClosureId closureId = EnumAdaptor.valueOf(param.getClosureId(), ClosureId.class);
		YearMonth processingYm = new YearMonth(param.getProcessingYm());
		DatePeriod period = new DatePeriod(GeneralDate.fromString(param.getStartDate(), "yyyy/MM/dd"), GeneralDate.fromString(param.getEndDate(), "yyyy/MM/dd"));
		List<DisplayWorkplace> displayWorkplaceLst = param.getWkpInfoLst();
		List<String> employmentCDLst = param.getEmploymentCDLst();
		return ApprSttSendMailInfoDto.fromDomain(
				appSttService.getApprSttSendMailInfo(mailType, closureId, processingYm, period, displayWorkplaceLst, employmentCDLst), 
				param.getMailType());
	}
	
	public SendMailResultOutput sendMailToDestination(ApprSttMailDestParam param) {
		String companyId = AppContexts.user().companyId();
		ApprovalStatusMailTempCommand command = param.getCommand();
		ApprovalStatusMailTemp approvalStatusMailTemp = new ApprovalStatusMailTemp(
				companyId, 
				EnumAdaptor.valueOf(command.getMailType(), ApprovalStatusMailType.class), 
				command.getUrlApprovalEmbed()==0 ? NotUseAtr.NOT_USE : NotUseAtr.USE, 
				command.getUrlDayEmbed()==0 ? NotUseAtr.NOT_USE : NotUseAtr.USE, 
				command.getUrlMonthEmbed()==0 ? NotUseAtr.NOT_USE : NotUseAtr.USE, 
				new Subject(command.getMailSubject()), 
				new Content(command.getMailContent()));
		return appSttService.sendMailToDestination(approvalStatusMailTemp, param.getWkpEmpMailLst());
	}
}
