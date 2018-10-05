package nts.uk.ctx.workflow.pubimp.resultrecord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.DailyConfirmAtr;
import nts.uk.ctx.workflow.dom.resultrecord.AppRootConfirm;
import nts.uk.ctx.workflow.dom.resultrecord.AppRootConfirmRepository;
import nts.uk.ctx.workflow.dom.resultrecord.AppRootInstance;
import nts.uk.ctx.workflow.dom.resultrecord.AppRootInstanceRepository;
import nts.uk.ctx.workflow.dom.resultrecord.RecordRootType;
import nts.uk.ctx.workflow.dom.resultrecord.service.AppRootInstanceContent;
import nts.uk.ctx.workflow.dom.resultrecord.service.CreateDailyApprover;
import nts.uk.ctx.workflow.dom.service.ApprovalRootStateStatusService;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRootStateStatus;
import nts.uk.ctx.workflow.dom.service.resultrecord.AppRootConfirmService;
import nts.uk.ctx.workflow.dom.service.resultrecord.AppRootInstancePeriod;
import nts.uk.ctx.workflow.dom.service.resultrecord.AppRootInstanceService;
import nts.uk.ctx.workflow.dom.service.resultrecord.ApprovalEmpStatus;
import nts.uk.ctx.workflow.dom.service.resultrecord.ApprovalPersonInstance;
import nts.uk.ctx.workflow.dom.service.resultrecord.ApproverEmployee;
import nts.uk.ctx.workflow.dom.service.resultrecord.ApproverToApprove;
import nts.uk.ctx.workflow.dom.service.resultrecord.RouteSituation;
import nts.uk.ctx.workflow.pub.resultrecord.ApproveDoneExport;
import nts.uk.ctx.workflow.pub.resultrecord.ApproverApproveExport;
import nts.uk.ctx.workflow.pub.resultrecord.ApproverEmpExport;
import nts.uk.ctx.workflow.pub.resultrecord.EmpPerformMonthParam;
import nts.uk.ctx.workflow.pub.resultrecord.EmployeePerformParam;
import nts.uk.ctx.workflow.pub.resultrecord.IntermediateDataPub;
import nts.uk.ctx.workflow.pub.resultrecord.export.AppEmpStatusExport;
import nts.uk.ctx.workflow.pub.resultrecord.export.AppFrameInsExport;
import nts.uk.ctx.workflow.pub.resultrecord.export.AppPhaseInsExport;
import nts.uk.ctx.workflow.pub.resultrecord.export.AppRootInsContentExport;
import nts.uk.ctx.workflow.pub.resultrecord.export.AppRootInsExport;
import nts.uk.ctx.workflow.pub.resultrecord.export.ApprovalStatusExport;
import nts.uk.ctx.workflow.pub.resultrecord.export.RouteSituationExport;
import nts.uk.ctx.workflow.pub.spr.export.AppRootStateStatusSprExport;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@RequestScoped
public class IntermediateDataPubImpl implements IntermediateDataPub {
	
	@Inject
	private AppRootInstanceService appRootInstanceService;
	
	@Inject
	private ApprovalRootStateStatusService approvalRootStateStatusService;
	
	@Inject
	private AppRootConfirmService appRootConfirmService;
	
	@Inject
	private CreateDailyApprover createDailyApprover;
	
	@Inject
	private AppRootConfirmRepository appRootConfirmRepository;
	
	@Inject
	private AppRootInstanceRepository appRootInstanceRepository;

	@Override
	public List<AppRootStateStatusSprExport> getAppRootStatusByEmpPeriod(String employeeID, DatePeriod period,
			Integer rootType) throws BusinessException {
		List<String> employeeIDLst = Arrays.asList(employeeID);
		return appRootInstanceService.getAppRootStatusByEmpsPeriod(employeeIDLst, period, EnumAdaptor.valueOf(rootType, RecordRootType.class))
				.stream().map(x -> convertStatusFromDomain(x)).collect(Collectors.toList());
	}

	@Override
	public List<AppRootStateStatusSprExport> getAppRootStatusByEmpsDates(List<String> employeeIDLst,
			List<GeneralDate> dateLst, Integer rootType) {
		List<AppRootStateStatusSprExport> result = new ArrayList<>();
		dateLst.forEach(date -> {
			DatePeriod period = new DatePeriod(date, date);
			result.addAll(appRootInstanceService.getAppRootStatusByEmpsPeriod(employeeIDLst, period, EnumAdaptor.valueOf(rootType, RecordRootType.class))
			.stream().map(x -> convertStatusFromDomain(x)).collect(Collectors.toList()));
		});
		return result;
	}

	@Override
	public List<AppRootStateStatusSprExport> getAppRootStatusByEmpsPeriod(List<String> employeeIDLst, DatePeriod period,
			Integer rootType) {
		return appRootInstanceService.getAppRootStatusByEmpsPeriod(employeeIDLst, period, EnumAdaptor.valueOf(rootType, RecordRootType.class))
			.stream().map(x -> convertStatusFromDomain(x)).collect(Collectors.toList());
	}
	
	private AppRootStateStatusSprExport convertStatusFromDomain(ApprovalRootStateStatus approvalRootStateStatus){
		return new AppRootStateStatusSprExport(
				approvalRootStateStatus.getDate(), 
				approvalRootStateStatus.getEmployeeID(), 
				approvalRootStateStatus.getDailyConfirmAtr().value);
	}

	@Override
	public List<ApproveDoneExport> checkDateApprovedStatus(String employeeID, DatePeriod period, Integer rootType) {
		RecordRootType rootTypeEnum = EnumAdaptor.valueOf(rootType, RecordRootType.class);
		List<String> employeeIDLst = Arrays.asList(employeeID);
		String companyID = AppContexts.user().companyId();
		List<ApproveDoneExport> approveDoneExportLst = new ArrayList<>();
		// 対象者と期間から承認ルート中間データを取得する
		List<AppRootInstancePeriod> appRootInstancePeriodLst = appRootInstanceService.getAppRootInstanceByEmpPeriod(employeeIDLst, period, rootTypeEnum);
		// INPUT．対象者社員IDの先頭から最後へループ
		employeeIDLst.forEach(employeeIDLoop -> {
			// INPUT．期間の開始日から終了日へループ
			for(GeneralDate loopDate = period.start(); loopDate.beforeOrEquals(period.end()); loopDate = loopDate.addDays(1)){
				// 対象日の承認ルート中間データを取得する
				AppRootInstance appRootInstance = appRootInstanceService.getAppRootInstanceByDate(loopDate, 
						appRootInstancePeriodLst.stream().filter(x -> x.getEmployeeID().equals(employeeIDLoop)).findAny().get().getAppRootInstanceLst());
				// 対象日の就業実績確認状態を取得する
				AppRootConfirm appRootConfirm = appRootInstanceService.getAppRootConfirmByDate(companyID, employeeIDLoop, loopDate, rootTypeEnum);
				// 中間データから承認ルートインスタンスに変換する
				ApprovalRootState approvalRootState = appRootInstanceService.convertFromAppRootInstance(appRootInstance, appRootConfirm);
				// 承認ルート状況を取得する
				List<ApprovalRootStateStatus> approvalRootStateStatusLst = approvalRootStateStatusService.getApprovalRootStateStatus(Arrays.asList(approvalRootState));
				// 承認状況が承認済ならtrue、それ以外ならfalseをセットする
				for(ApprovalRootStateStatus approvalRootStateStatus : approvalRootStateStatusLst) {
					boolean isApproved = false;
					if(approvalRootStateStatus.getDailyConfirmAtr().equals(DailyConfirmAtr.ALREADY_APPROVED)){
						isApproved = true;
					}
					approveDoneExportLst.add(new ApproveDoneExport(loopDate, isApproved));
				};
			}
		});
		return approveDoneExportLst;
	}

	@Override
	public void approve(String approverID, List<EmployeePerformParam> employeePerformLst, Integer rootType) {
		String companyID = AppContexts.user().companyId();
		RecordRootType rootTypeEnum = EnumAdaptor.valueOf(rootType, RecordRootType.class);
		employeePerformLst.forEach(employee -> {
			String employeeID = employee.getEmployeeID();
			GeneralDate date = employee.getDate();
			// 対象者と期間から承認ルート中間データを取得する
			List<AppRootInstancePeriod> appRootInstancePeriodLst = appRootInstanceService.getAppRootInstanceByEmpPeriod(
					Arrays.asList(employeeID), 
					new DatePeriod(date, date), 
					rootTypeEnum);
			// ループする社員の「承認ルート中間データ」を取得する
			AppRootInstance appRootInstance = appRootInstanceService.getAppRootInstanceByDate(date, 
					appRootInstancePeriodLst.stream().filter(x -> x.getEmployeeID().equals(employeeID)).findAny().get().getAppRootInstanceLst());
			if(appRootInstance == null){
				throw new BusinessException("Msg_1430", "承認者");
			}
			// 対象日の就業実績確認状態を取得する
			AppRootConfirm appRootConfirm = appRootInstanceService.getAppRootConfirmByDate(companyID, employeeID, date, rootTypeEnum);
			// (中間データ版)承認する
			appRootConfirmService.approve(approverID, employeeID, date, appRootInstance, appRootConfirm);
		});
	}

	@Override
	public boolean cancel(String approverID, List<EmployeePerformParam> employeePerformLst, Integer rootType) {
		boolean result = false;
		String companyID = AppContexts.user().companyId();
		RecordRootType rootTypeEnum = EnumAdaptor.valueOf(rootType, RecordRootType.class);
		for(EmployeePerformParam employee : employeePerformLst){
			String employeeID = employee.getEmployeeID();
			GeneralDate date = employee.getDate();
			// 対象者と期間から承認ルート中間データを取得する
			List<AppRootInstancePeriod> appRootInstancePeriodLst = appRootInstanceService.getAppRootInstanceByEmpPeriod(
					Arrays.asList(employeeID), 
					new DatePeriod(date, date), 
					rootTypeEnum);
			// ループする社員の「承認ルート中間データ」を取得する
			AppRootInstance appRootInstance = appRootInstanceService.getAppRootInstanceByDate(date, 
					appRootInstancePeriodLst.stream().filter(x -> x.getEmployeeID().equals(employeeID)).findAny().get().getAppRootInstanceLst());
			// 対象日の就業実績確認状態を取得する
			AppRootConfirm appRootConfirm = appRootInstanceService.getAppRootConfirmByDate(companyID, employeeID, date, rootTypeEnum);
			// (中間データ版)解除する
			result = appRootConfirmService.cleanStatus(approverID, employeeID, date, appRootInstance, appRootConfirm);
			if(!result){
				break;
			}
		};
		return result;
	}

	@Override
	public List<ApproverApproveExport> getApproverByPeriod(List<String> employeeIDLst, DatePeriod period, Integer rootType) {
		RecordRootType rootTypeEnum = EnumAdaptor.valueOf(rootType, RecordRootType.class);
		return appRootInstanceService.getApproverByPeriod(employeeIDLst, period, rootTypeEnum).stream()
			.map(x -> convertApproverApprove(x)).collect(Collectors.toList());
	}

	@Override
	public List<ApproverApproveExport> getApproverByDateLst(List<String> employeeIDLst, List<GeneralDate> dateLst, Integer rootType) {
		RecordRootType rootTypeEnum = EnumAdaptor.valueOf(rootType, RecordRootType.class);
		List<ApproverApproveExport> approverApproveExportLst = new ArrayList<>();
		dateLst.forEach(date -> {
			approverApproveExportLst.addAll(appRootInstanceService.getApproverByPeriod(employeeIDLst, new DatePeriod(date, date), rootTypeEnum)
					.stream().map(x -> convertApproverApprove(x)).collect(Collectors.toList()));
		});
		return approverApproveExportLst;
	}
	
	private ApproverApproveExport convertApproverApprove(ApproverToApprove approverToApprove){
		return new ApproverApproveExport(
				approverToApprove.getDate(), 
				approverToApprove.getEmployeeID(), 
				approverToApprove.getAuthorList().stream().map(x -> convertApproverEmployee(x)).collect(Collectors.toList()));
	}
	
	private ApproverEmpExport convertApproverEmployee(ApproverEmployee approverEmployee){
		return new ApproverEmpExport(
				approverEmployee.getEmployeeID(), 
				approverEmployee.getEmployeeCD(), 
				approverEmployee.getEmployeeName());
	}

	@Override
	public AppRootInsContentExport createDailyApprover(String employeeID, Integer rootType, GeneralDate recordDate) {
		AppRootInstanceContent appRootInstanceContent = createDailyApprover.createDailyApprover(employeeID, EnumAdaptor.valueOf(rootType, RecordRootType.class), recordDate);
		return new AppRootInsContentExport(
				new AppRootInsExport(
						appRootInstanceContent.getAppRootInstance().getRootID(), 
						appRootInstanceContent.getAppRootInstance().getCompanyID(), 
						employeeID, 
						appRootInstanceContent.getAppRootInstance().getDatePeriod(), 
						rootType, 
						appRootInstanceContent.getAppRootInstance().getListAppPhase()
							.stream().map(x -> new AppPhaseInsExport(
									x.getPhaseOrder(), 
									x.getApprovalForm().value, 
									x.getListAppFrame()
										.stream().map(y -> new AppFrameInsExport(
											y.getFrameOrder(), 
											y.isConfirmAtr(), 
											y.getListApprover()))
										.collect(Collectors.toList())))
							.collect(Collectors.toList())), 
				appRootInstanceContent.getErrorFlag().value, 
				appRootInstanceContent.getErrorMsgID());
	}

	@Override
	public boolean isDataExist(String approverID, DatePeriod period, Integer rootType) {
		return appRootInstanceService.isDataExist(
				approverID, 
				period, 
				EnumAdaptor.valueOf(rootType, RecordRootType.class));
	}

	@Override
	public AppEmpStatusExport getApprovalEmpStatus(String employeeID, DatePeriod period, Integer rootType) {
		ApprovalEmpStatus approvalEmpStatus = appRootInstanceService.getApprovalEmpStatus(employeeID, period, EnumAdaptor.valueOf(rootType, RecordRootType.class));
		return new AppEmpStatusExport(
				approvalEmpStatus.getEmployeeID(), 
				approvalEmpStatus.getRouteSituationLst().stream().map(x -> new RouteSituationExport(
						x.getDate(), 
						x.getEmployeeID(), 
						x.getApproverEmpState().value, 
						x.getApprovalStatus().map(y -> new ApprovalStatusExport(y.getReleaseAtr().value, y.getApprovalAction().value))))
				.collect(Collectors.toList()));
	}

	@Override
	public void cleanApprovalRootState(String employeeID, GeneralDate date, Integer rootType) {
		String companyID = AppContexts.user().companyId();
		appRootConfirmRepository.clearStatus(companyID, employeeID, date, EnumAdaptor.valueOf(rootType, RecordRootType.class));
	}

	@Override
	public void createApprovalStatus(String employeeID, GeneralDate date, Integer rootType) {
		
		String companyID = AppContexts.user().companyId();
		
		String rootID = IdentifierUtil.randomUniqueId();
		
		AppRootConfirm newDomain = new AppRootConfirm(rootID, companyID, employeeID, date,
				EnumAdaptor.valueOf(rootType, RecordRootType.class), Collections.emptyList(),
				Optional.empty(), Optional.empty(), Optional.empty());
		
		this.appRootConfirmRepository.insert(newDomain);
	}

	@Override
	public void deleteApprovalStatus(String employeeID, GeneralDate date, Integer rootType) {
		String companyID =  AppContexts.user().companyId();
		this.appRootConfirmRepository.deleteByRequestList424(companyID, employeeID, date, rootType);
	}

	@Override
	public void approveMonth(String approverID, List<EmpPerformMonthParam> empPerformMonthParamLst) {
		String companyID = AppContexts.user().companyId();
		RecordRootType rootTypeEnum = RecordRootType.CONFIRM_WORK_BY_MONTH;
		empPerformMonthParamLst.forEach(employee -> {
			String employeeID = employee.getEmployeeID();
			GeneralDate date = employee.getBaseDate();
			// 対象者と期間から承認ルート中間データを取得する
			List<AppRootInstancePeriod> appRootInstancePeriodLst = appRootInstanceService.getAppRootInstanceByEmpPeriod(
					Arrays.asList(employeeID), 
					new DatePeriod(date, date), 
					rootTypeEnum);
			// ループする社員の「承認ルート中間データ」を取得する
			AppRootInstance appRootInstance = appRootInstanceService.getAppRootInstanceByDate(date, 
					appRootInstancePeriodLst.stream().filter(x -> x.getEmployeeID().equals(employeeID)).findAny().get().getAppRootInstanceLst());
			if(appRootInstance == null){
				throw new BusinessException("Msg_1430", "承認者");
			}
			// ドメインモデル「就業実績確認状態」を取得する
			AppRootConfirm appRootConfirm = appRootInstanceService.getAppRootCFByMonth(companyID, employeeID, employee.getYearMonth(), 
					employee.getClosureID(), employee.getClosureDate(), rootTypeEnum);
			// (中間データ版)承認する
			appRootConfirmService.approve(approverID, employeeID, date, appRootInstance, appRootConfirm);
		});
	}

	@Override
	public void createApprovalStatusMonth(String employeeID, GeneralDate date, YearMonth yearMonth, Integer closureID,
			ClosureDate closureDate) {
		String companyID = AppContexts.user().companyId();
		
		String rootID = IdentifierUtil.randomUniqueId();
		
		AppRootConfirm newDomain = new AppRootConfirm(rootID, companyID, employeeID, date,
				RecordRootType.CONFIRM_WORK_BY_MONTH, Collections.emptyList(),
				Optional.of(yearMonth), Optional.of(closureID), Optional.of(closureDate));
		
		this.appRootConfirmRepository.insert(newDomain);
	}

	@Override
	public boolean cancelMonth(String approverID, List<EmpPerformMonthParam> empPerformMonthParamLst) {
		boolean result = false;
		String companyID = AppContexts.user().companyId();
		RecordRootType rootTypeEnum = RecordRootType.CONFIRM_WORK_BY_MONTH;
		for(EmpPerformMonthParam employee : empPerformMonthParamLst){
			String employeeID = employee.getEmployeeID();
			GeneralDate date = employee.getBaseDate();
			// 対象者と期間から承認ルート中間データを取得する
			List<AppRootInstancePeriod> appRootInstancePeriodLst = appRootInstanceService.getAppRootInstanceByEmpPeriod(
					Arrays.asList(employeeID), 
					new DatePeriod(date, date), 
					rootTypeEnum);
			// ループする社員の「承認ルート中間データ」を取得する
			AppRootInstance appRootInstance = appRootInstanceService.getAppRootInstanceByDate(date, 
					appRootInstancePeriodLst.stream().filter(x -> x.getEmployeeID().equals(employeeID)).findAny().get().getAppRootInstanceLst());
			// ドメインモデル「就業実績確認状態」を取得する
			AppRootConfirm appRootConfirm = appRootInstanceService.getAppRootCFByMonth(companyID, employeeID, employee.getYearMonth(), 
					employee.getClosureID(), employee.getClosureDate(), rootTypeEnum);
			// (中間データ版)解除する
			result = appRootConfirmService.cleanStatus(approverID, employeeID, date, appRootInstance, appRootConfirm);
			if(!result){
				break;
			}
		};
		return result;
	}

	@Override
	public List<AppRootStateStatusSprExport> getAppRootStatusByEmpPeriodMonth(String employeeID, DatePeriod period) {
		String companyID = AppContexts.user().employeeId();
		// ドメインモデル「就業実績確認状態」を取得する
		AppRootConfirm appRootConfirm = appRootConfirmRepository.findByEmpPeriodMonth(companyID, employeeID, period).get();
		// ドメインモデル「中間データ」を取得する
		AppRootInstance appRootInstance = appRootInstanceRepository.findByContainDate(companyID, employeeID, appRootConfirm.getRecordDate(), 
				RecordRootType.CONFIRM_WORK_BY_MONTH).get();
		// 中間データから承認ルートインスタンスに変換する
		ApprovalRootState approvalRootState = appRootInstanceService.convertFromAppRootInstance(appRootInstance, appRootConfirm);
		// 承認ルート状況を取得する
		return approvalRootStateStatusService.getApprovalRootStateStatus(Arrays.asList(approvalRootState))
				.stream().map(x -> convertStatusFromDomain(x)).collect(Collectors.toList());
	}

	@Override
	public List<AppRootStateStatusSprExport> getAppRootStatusByEmpsMonth(
			List<EmpPerformMonthParam> empPerformMonthParamLst) {
		String companyID = AppContexts.user().companyId();
		List<ApprovalRootStateStatus> appRootStatusLst = new ArrayList<>();
		// INPUT．対象者社員IDの先頭から最後へループ
		empPerformMonthParamLst.forEach(employee -> {
			// 対象者と期間から承認ルート中間データを取得する
			List<AppRootInstancePeriod> appRootInstancePeriodLst = appRootInstanceService.getAppRootInstanceByEmpPeriod(
					Arrays.asList(employee.getEmployeeID()), 
					new DatePeriod(employee.getBaseDate(), employee.getBaseDate()), 
					RecordRootType.CONFIRM_WORK_BY_MONTH);
			// 基準日の承認ルート中間データを取得する
			AppRootInstance appRootInstance = appRootInstanceService.getAppRootInstanceByDate(employee.getBaseDate(), 
					appRootInstancePeriodLst.stream().filter(x -> x.getEmployeeID().equals(employee.getEmployeeID())).findAny().get().getAppRootInstanceLst());
			if(appRootInstance==null){
				throw new BusinessException("Msg_1430", "承認者");
			}
			// ドメインモデル「就業実績確認状態」を取得する
			AppRootConfirm appRootConfirm = appRootInstanceService.getAppRootCFByMonth(companyID, employee.getEmployeeID(), employee.getYearMonth(), 
					employee.getClosureID(), employee.getClosureDate(), RecordRootType.CONFIRM_WORK_BY_MONTH);
			// 中間データから承認ルートインスタンスに変換する
			ApprovalRootState approvalRootState = appRootInstanceService.convertFromAppRootInstance(appRootInstance, appRootConfirm);
			// 承認ルート状況を取得する
			appRootStatusLst.addAll(approvalRootStateStatusService.getApprovalRootStateStatus(Arrays.asList(approvalRootState)));
		});
		return appRootStatusLst.stream().map(x -> convertStatusFromDomain(x)).collect(Collectors.toList());
	}

	@Override
	public AppEmpStatusExport getApprovalEmpStatusMonth(String approverID, YearMonth yearMonth, Integer closureID,
			ClosureDate closureDate, GeneralDate baseDate) {
		DatePeriod period = new DatePeriod(baseDate, baseDate);
		// 承認者(承認代行を含め)と期間から承認ルート中間データを取得する
		ApprovalPersonInstance approvalPersonInstance = appRootInstanceService.getApproverAndAgent(approverID, 
				period, RecordRootType.CONFIRM_WORK_BY_MONTH);
		List<String> agentLst = approvalPersonInstance.getAgentRoute().stream().map(x -> x.getAgentID().orElse(null))
				.filter(x -> Strings.isNotBlank(x)).collect(Collectors.toList());
		// output「基準社員の承認対象者」を初期化する
		ApprovalEmpStatus approvalEmpStatus = new ApprovalEmpStatus(approverID, new ArrayList<>());
		// 取得した「承認者としての承認ルート」．承認ルートの詳細の件数をチェックする
		List<RouteSituation> approverRouteLst = new ArrayList<>();
		if(!CollectionUtil.isEmpty(approvalPersonInstance.getApproverRoute())){
			// 承認者としてのルート状況を取得する
			approverRouteLst = appRootInstanceService.getApproverRouteSituation(period, approvalPersonInstance.getApproverRoute(), agentLst, RecordRootType.CONFIRM_WORK_BY_MONTH);
		}
		// 取得した「代行者としての承認ルート」．承認ルートの詳細の件数をチェックする
		List<RouteSituation> agentRouteLst = new ArrayList<>();
		if(!CollectionUtil.isEmpty(approvalPersonInstance.getAgentRoute())){
			// 代行者としてのルート状況を取得する
			agentRouteLst = appRootInstanceService.getAgentRouteSituation(period, approvalPersonInstance.getAgentRoute(), agentLst, RecordRootType.CONFIRM_WORK_BY_MONTH);
		}
		// outputの整合
		List<RouteSituation> mergeLst = appRootInstanceService.mergeRouteSituationLst(approverRouteLst, agentRouteLst);
		// 「ルート状況」リスト整合処理後をoutput「基準社員の承認対象者」に追加する
		approvalEmpStatus.getRouteSituationLst().addAll(mergeLst);
		return new AppEmpStatusExport(
				approvalEmpStatus.getEmployeeID(), 
				approvalEmpStatus.getRouteSituationLst().stream().map(x -> new RouteSituationExport(
						x.getDate(), 
						x.getEmployeeID(), 
						x.getApproverEmpState().value, 
						x.getApprovalStatus().map(y -> new ApprovalStatusExport(y.getReleaseAtr().value, y.getApprovalAction().value))))
				.collect(Collectors.toList()));
	}

	@Override
	public boolean isDataExistMonth(String approverID, DatePeriod period, YearMonth yearMonth) {
		// 承認者(承認代行を含め)と期間から承認ルート中間データを取得する
		ApprovalPersonInstance approvalPersonInstance = appRootInstanceService.getApproverAndAgent(approverID, period, RecordRootType.CONFIRM_WORK_BY_MONTH);
		// 取得した「承認者になる中間データ」．承認者としての承認ルートと代行者としての承認ルートの件数をチェックする
		if(CollectionUtil.isEmpty(approvalPersonInstance.getAgentRoute()) && 
				CollectionUtil.isEmpty(approvalPersonInstance.getApproverRoute())){
			return false;
		}
		// 承認者としての承認すべきデータがあるか
		if(appRootInstanceService.isDataApproverExistMonth(yearMonth, approvalPersonInstance.getApproverRoute())){
			return true;
		}
		// 代行者としての承認すべきデータがあるか
		if(appRootInstanceService.isDataAgentExistMonth(yearMonth, approvalPersonInstance.getAgentRoute())){
			return true;
		}
		return false;
	}
	
}
