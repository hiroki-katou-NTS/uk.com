package nts.uk.ctx.at.request.dom.application.common.service.print;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTrip;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripRepository;
import org.apache.logging.log4j.util.Strings;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.jobtitle.AtJobTitleAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.jobtitle.dto.AffJobTitleHistoryImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalFrameImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppReasonStandard;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppReasonStandardRepository;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceConfigInfoAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class CommonAppPrintImpl implements CommonAppPrint {
	
	@Inject
	private CompanyAdapter companyAdapter;
	
	@Inject
	private WorkplaceConfigInfoAdapter workplaceConfigInfoAdapter;
	
	@Inject
	private AppReasonStandardRepository appReasonStandardRepository;
	
	@Inject
	private EmployeeRequestAdapter employeeAdaptor;
	
	@Inject
	private AtEmployeeAdapter atEmployeeAdapter;
	
	@Inject
	private AtJobTitleAdapter atJobTitleAdapter;

	@Inject
	private BusinessTripRepository businessTripRepo;

	
	@Override
	public PrintContentOfApp print(String companyID, String appID, AppDispInfoStartupOutput appDispInfoStartupOutput,
			Optional<PrintContentOfEachApp> opPrintContentOfEachApp) {
		PrintContentOfApp printContentOfApp = new PrintContentOfApp();
		Application application = appDispInfoStartupOutput.getAppDetailScreenInfo().get().getApplication();
		// [RQ622]会社IDから会社情報を取得する([RQ622] Lấy companyInfo từ companyID)
		CompanyInfor companyInfo = companyAdapter.getCurrentCompany().orElseGet(() -> {
			throw new RuntimeException("System Error: Company Info");
		});
		printContentOfApp.setCompanyName(companyInfo.getCompanyName());
		// メニューの表示名を取得する
		// xử lý ở đoạn sau
		String applicationName = Strings.EMPTY;
		printContentOfApp.setApplicationName(applicationName);
		//INPUT．申請表示情報．申請詳細画面情報．申請から事前事後区分、申請日、申請開始日、申請終了日、申請理由を取得する
		printContentOfApp.setPrePostAtr(application.getPrePostAtr());
		printContentOfApp.setAppDate(application.getAppDate().getApplicationDate());
		printContentOfApp.setAppStartDate(application.getOpAppStartDate().map(x -> x.getApplicationDate()).orElse(null));
		printContentOfApp.setAppEndDate(application.getOpAppEndDate().map(x -> x.getApplicationDate()).orElse(null));
		printContentOfApp.setOpAppReason(application.getOpAppReason().orElse(null));
		// INPUT．申請表示情報．申請詳細画面情報．申請から事前事後区分、申請日、申請開始日、申請終了日、申請理由を取得する
		ApplicationType appType = application.getAppType();
		// INPUT．申請表示情報．申請詳細画面情報．申請．申請種類をチェックする
		switch(appType) {
			default: 
				break;
		}
		printContentOfApp.setOpPrintContentOfWorkChange(opPrintContentOfEachApp.map(x -> x.getOpPrintContentOfWorkChange()).orElse(Optional.empty()));
		printContentOfApp.setOpAppStampOutput(opPrintContentOfEachApp.map(x -> x.getOpAppStampOutput()).orElse(Optional.empty()));
		printContentOfApp.setOpArrivedLateLeaveEarlyInfo(opPrintContentOfEachApp.map(x -> x.getOpArrivedLateLeaveEarlyInfo()).orElse(Optional.empty()));
		printContentOfApp.setOpInforGoBackCommonDirectOutput(opPrintContentOfEachApp.map(x -> x.getOpInforGoBackCommonDirectOutput()).orElse(Optional.empty()));
		printContentOfApp.setOpBusinessTripPrintContent(opPrintContentOfEachApp.map(x -> x.getOpBusinessTrip()).orElse(Optional.empty()));
		printContentOfApp.setOpDetailOutput(opPrintContentOfEachApp.map(PrintContentOfEachApp::getOpDetailOutput).orElse(Optional.empty()));
		// INPUT．申請表示情報．申請表示情報(基準日関係なし)．社員情報リストの一つ目を取得する
		printContentOfApp.setEmployeeInfoLst(appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getEmployeeInfoLst());
		// 社員と基準日から所属職場履歴項目を取得する
		String wkpID = employeeAdaptor.getAffWkpHistItemByEmpDate(
				application.getEmployeeID(), 
				appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getBaseDate());
		// [No.560]職場IDから職場の情報をすべて取得する
		List<WorkplaceInfor> workplaceInforLst = workplaceConfigInfoAdapter.getWorkplaceInforByWkpIds(
				companyID, 
				Arrays.asList(wkpID), 
				appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getBaseDate());
		printContentOfApp.setWorkPlaceName(workplaceInforLst.stream().findFirst().map(x -> x.getWorkplaceName()).orElse(null));
		// 指定する定型理由コードから定型理由を取得する
		Optional<AppReasonStandard> opAppReasonStandard = appReasonStandardRepository.findByCD(
				appType, 
				application.getOpAppStandardReasonCD().orElse(null));
		printContentOfApp.setAppReasonStandard(opAppReasonStandard.orElse(null));
		// 印字する承認者を取得する
		ApproverColumnContents approverColumnContents = this.getApproverToPrint(
				appDispInfoStartupOutput.getAppDetailScreenInfo().get().getApprovalLst(), 
				GeneralDate.today());
		printContentOfApp.setApproverColumnContents(approverColumnContents);
		// INPUT．申請表示情報．申請設定（基準日関係なし）．複数回勤務の管理を取得する
		printContentOfApp.setManagementMultipleWorkCycles(appDispInfoStartupOutput.getAppDispInfoNoDateOutput().isManagementMultipleWorkCycles());
		return printContentOfApp;
	}

	@Override
	public ApproverColumnContents getApproverToPrint(List<ApprovalPhaseStateImport_New> approvalLst, GeneralDate date) {
		List<ApproverPrintDetails> approverPrintDetailsLst = new ArrayList<>();
		// INPUT．承認ルートインスタンス．承認フェーズ１～５をループする
		for(ApprovalPhaseStateImport_New approvalPhaseStateImport : approvalLst) {
			// ループ中の承認フェーズインスタンス．承認枠１～５をループする
			for(ApprovalFrameImport_New approvalFrameImport : approvalPhaseStateImport.getListApprovalFrame()) {
				for(ApproverStateImport_New approverStateImport_New : approvalFrameImport.getListApprover()) {
					// ループ中の承認枠．承認者を取得する
					String approverID = "";
					if(Strings.isBlank(approverStateImport_New.getAgentID())) {
						approverID = approverStateImport_New.getApproverID();
					} else {
						approverID = approverStateImport_New.getAgentID();
					}
					ApproverPrintDetails approverPrintDetails = new ApproverPrintDetails();
					approverPrintDetails.setApproverID(approverID);
					approverPrintDetails.setApprovalBehaviorAtr(approverStateImport_New.getApprovalAtr());
					if(approverStateImport_New.getApprovalDate() != null) {
						approverPrintDetails.setOpApprovalDate(Optional.of(approverStateImport_New.getApprovalDate()));
					}
					approverPrintDetailsLst.add(approverPrintDetails);
					// ５人取得したかチェックする
					if(approverPrintDetailsLst.size() >= 5) {
						break;
					}
				}
				if(approverPrintDetailsLst.size() >= 5) {
					break;
				}
			}
			if(approverPrintDetailsLst.size() >= 5) {
				break;
			}
		}
		
		for(ApproverPrintDetails approverPrintDetails : approverPrintDetailsLst) {
			// 社員ID（List）から社員コードと表示名を取得
			List<EmployeeInfoImport> employeeInfoImportLst = atEmployeeAdapter.getByListSID(Arrays.asList(approverPrintDetails.getApproverID()));
			if(!CollectionUtil.isEmpty(employeeInfoImportLst)) {
				approverPrintDetails.setEmployeeInfoImport(employeeInfoImportLst.get(0));
			}
			// 社員IDと基準日から職位情報を取得
			Optional<AffJobTitleHistoryImport> opAffJobTitleHistoryImport = atJobTitleAdapter.getJobTitlebBySIDAndDate(approverPrintDetails.getApproverID(), date);
			if(opAffJobTitleHistoryImport.isPresent()) {
				approverPrintDetails.setAffJobTitleHistoryImport(opAffJobTitleHistoryImport.get());
			}
		}
		return new ApproverColumnContents(approverPrintDetailsLst);
	}
	
}
