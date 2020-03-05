/**
 * 
 */
package nts.uk.ctx.hr.notice.app.command.report.regis.agent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.hr.notice.app.command.report.regis.person.ItemDfCommand;
import nts.uk.ctx.hr.notice.app.command.report.regis.person.SaveReportInputContainer;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ApprovalPersonReport;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ApprovalPersonReportRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReport;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReportRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ReportItem;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ReportItemRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.ApprovalActivity;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.ApprovalStatus;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.ApprovalStatusForRegis;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.EmailTransmissionClass;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.LayoutItemType;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.RegistrationStatus;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.ReportType;
import nts.uk.ctx.hr.notice.dom.report.valueImported.DateRangeItemImport;
import nts.uk.ctx.hr.notice.dom.report.valueImported.HumanItemPub;
import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInfo;
import nts.uk.ctx.hr.shared.dom.approval.rootstate.ApprovalFrameHrExport;
import nts.uk.ctx.hr.shared.dom.approval.rootstate.ApprovalPhaseStateHrExport;
import nts.uk.ctx.hr.shared.dom.approval.rootstate.ApprovalRootContentHrExport;
import nts.uk.ctx.hr.shared.dom.approval.rootstate.ApprovalRootStateHrRepository;
import nts.uk.ctx.hr.shared.dom.approval.rootstate.ApproverStateHrExport;
import nts.uk.ctx.hr.shared.dom.employee.EmployeeInformationAdaptor;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.command.ItemsByCategory;

/**
 * @author laitv
 *
 */
@Stateless
public class SaveAgentRegisPersonReportHandler extends CommandHandler<SaveReportInputContainer>{
	
	@Inject
	private RegistrationPersonReportRepository repo;
	
	@Inject
	private ReportItemRepository reportItemRepo;
	
	@Inject
	private ApprovalPersonReportRepository approvalPersonReportRepo;
	
	@Inject
	private EmployeeInformationAdaptor empInfoAdaptor;
	
	@Inject
	private ApprovalRootStateHrRepository approvalRootStateRepo;
	
	@Inject
	private HumanItemPub humanItemPub;
	

	// アルゴリズム「届出情報を登録」を実行する (Thực hiện thuật toán "Đăng ký thông tin report")
	@Override
	protected void handle(CommandHandlerContext<SaveReportInputContainer> context) {
		SaveReportInputContainer command = context.getCommand();
		if (command.reportID == null) {
			// insert
			insertData(command);
		}else {
			// update
			updateData(command);
		}
	}
	
	public void insertData(SaveReportInputContainer data) {
		String inputSid = AppContexts.user().employeeId();
		String applicantSid = data.appSid; 
		String cid = AppContexts.user().companyId();
		
		// アルゴリズム[社員IDから社員情報全般を取得する]を実行する
		//(Thực hiện thuật toán "Lấy Thông tin chung của nhân viên từ employee ID")
		EmployeeInfo empApplicantInfo  = this.getPersonInfo( applicantSid, cid );
		
		// 届出IDを採番する(Đánh số report ID)
		Integer reportIDNew = repo.getMaxReportId(cid) + 1;
		
		// アルゴリズム[社員IDから社員情報全般を取得する]を実行する
		// (Thực hiện thuật toán "[Lấy thông tin chung của nhân viên từ ID nhân viên]")
		EmployeeInfo empInputInfo = this.getPersonInfo(inputSid, cid);
		
		String	rootSateId = saveInfoApporver(data, inputSid, applicantSid , cid, reportIDNew);
		 
		RegistrationPersonReport personReport = RegistrationPersonReport.builder()
				.cid(cid)
				.rootSateId(data.isSaveDraft == 1 ? null : rootSateId)
				.workId(data.workId)
				.reportID(reportIDNew)
				.reportLayoutID(data.reportLayoutID)
				.reportCode(data.reportCode)
				.reportName(data.reportName)
				.reportDetail(null) // chưa đặt hàng lần này
				.regStatus(data.isSaveDraft == 1 ? RegistrationStatus.Save_Draft : RegistrationStatus.Registration)
				.aprStatus(ApprovalStatusForRegis.Not_Started)
				.draftSaveDate(GeneralDateTime.now())
				.missingDocName(data.missingDocName)
				.inputPid(empInputInfo.inputPid)
				.inputSid(empInputInfo.inputSid)
				.inputScd(empInputInfo.inputScd)
				.inputBussinessName(empInputInfo.inputBussinessName)
				.inputDate(GeneralDateTime.now())
				
				.appPid(empApplicantInfo.appliPerId)
				.appSid(empApplicantInfo.appliPerSid)
				.appScd(empApplicantInfo.appliPerScd)
				.appBussinessName(empApplicantInfo.appliPerBussinessName)
				.appDate(GeneralDateTime.now())
				.appDevId(empApplicantInfo.appDevId)
				.appDevCd(empApplicantInfo.appDevCd)
				.appDevName(empApplicantInfo.appDevName)
				.appPosId(empApplicantInfo.appPosId)
				.appPosCd(empApplicantInfo.appPosCd)
				.appPosName(empApplicantInfo.appPosName)
				.reportType(EnumAdaptor.valueOf(data.reportType, ReportType.class))
				.sendBackSID(data.sendBackSID)
				.sendBackComment(data.sendBackComment)
				.delFlg(false)
				.build();
		
		List<ReportItem> listReportItem = creatDataReportItem(data, reportIDNew);
		
		// 社員情報全般と届出IDをキーに届出パネルの入力内容を「人事届出の登録」、「届出の項目」に登録する
		// (Đăng ký nội dung nhập ở panel report với key là reportID và Thông tin chung của nhân viên vào 「人事届出の登録」、「届出の項目」)
		repo.add(personReport);
	    reportItemRepo.addAll(listReportItem);
	    
	    // アルゴリズム「[RQ631]申請書の承認者と状況を取得する」を実行する 
	    // Thực hiện thuật toán"[RQ631]Lấy trạng thái và người phê duyệt Application form)
	    // chưa làm chức năng gửi mail => chưa cần gọi [RQ631]
	}

	private String saveInfoApporver(SaveReportInputContainer data, String sid, String applicantSid, String cid, Integer reportIDNew) {
		String rootSateId;
		// アルゴリズム[GUIDを生成する]を実行する (Thực hiện thuật toán "Tạo GUID")
		rootSateId = IdentifierUtil.randomUniqueId();
		
		// アルゴリズム[[No.309]承認ルートを取得する/1.社員の対象申請の承認ルートを取得する]を実行する
		//(Thực hiện thuật toán [[No.309] Get approval route/1. Get Approval route for employee application])
		ApprovalRootContentHrExport export = approvalRootStateRepo.getApprovalRootHr(cid, sid, String.valueOf(data.reportLayoutID), GeneralDate.today(), Optional.empty());
		
		// アルゴリズム[[RQ637]承認ルートインスタンスを新規作成する]を実行する(Thực hiện thuật toán [[RQ637] Create new approval route instance])
		approvalRootStateRepo.createApprStateHr(export, rootSateId, sid, GeneralDate.today());
		
		// [人事届出の登録.ルートインスタンスID]、[人事届出の承認.ルートインスタンスID]を登録する。[人事届出の承認．メール送信区分]=メールするに設定
		//(Đăng ký [Registration of HR report. Root instance ID], [Approval of HR report. Root instance ID]. [Approval of HR report. Category gửi mail] = Setting mail)
		List<ApprovalPersonReport> listDomainApproval = new ArrayList<>();
		if (export.getApprovalRootState() != null && !export.getApprovalRootState().getListApprovalPhaseState().isEmpty()) {
			List<ApprovalPhaseStateHrExport> listApprovalPhaseState = export.getApprovalRootState().getListApprovalPhaseState();
			for (int i = 0; i < listApprovalPhaseState.size(); i++) {
				ApprovalPhaseStateHrExport approvalPhaseStateHrExport = listApprovalPhaseState.get(i); 
				if (!approvalPhaseStateHrExport.getListApprovalFrame().isEmpty()) {
					List<ApprovalFrameHrExport> listApprovalFrame = approvalPhaseStateHrExport.getListApprovalFrame();
					for (int j = 0; j < listApprovalFrame.size(); j++) {
						ApprovalFrameHrExport approvalFrameHrExport = listApprovalFrame.get(j);
						if (!approvalFrameHrExport.getListApprover().isEmpty()) {
							List<ApproverStateHrExport> listApprover = approvalFrameHrExport.getListApprover();
							for (int k = 0; k < listApprover.size(); k++) {
								ApproverStateHrExport approverStateHrExport = listApprover.get(k);
								ApprovalPersonReport domain = ApprovalPersonReport.builder()
										.cid(cid)
										.rootSatteId(rootSateId)
										.reportID(reportIDNew)
										.reportName(data.reportName)
										.refDate(GeneralDateTime.now())
										.inputDate(GeneralDateTime.now())
										.appDate(GeneralDateTime.now())
										.aprDate(GeneralDateTime.now())
										.aprSid(approverStateHrExport.getApproverID())
										.aprBussinessName(approverStateHrExport.getApproverName())
										.emailAddress(null)
										.phaseNum(approvalPhaseStateHrExport.getPhaseOrder())
										.aprStatus(ApprovalStatus.Not_Acknowledged)
										.aprNum(approvalFrameHrExport.getFrameOrder())
										.arpAgency(approverStateHrExport.getRepresenterID() == null || approverStateHrExport.getRepresenterID() == "" ? false : true)
										.comment(null)
										.aprActivity(ApprovalActivity.Activity)
										.emailTransmissionClass(EmailTransmissionClass.DoNotSend)
										.appSid(applicantSid)
										.inputSid(sid)
										.reportLayoutID(data.reportLayoutID)
										.sendBackSID(Optional.empty())
										.sendBackClass(Optional.empty())
										.build();
								listDomainApproval.add(domain);
							}
						}
					}
				}
			}
		}

		if (!listDomainApproval.isEmpty()) {
			approvalPersonReportRepo.addAll(listDomainApproval);
		}
		return rootSateId;
	}
	
	
	public List<ReportItem> creatDataReportItem(SaveReportInputContainer data, Integer reportId) {

		List<ReportItem> listReportItem = new ArrayList<ReportItem>();
		List<ItemDfCommand> listItemDf = data.listItemDf;
		String cid = AppContexts.user().companyId();
		String contractCode = AppContexts.user().contractCode();
		List<ItemsByCategory> listCategory = data.inputs;
		
		List<String> categoryIds = listCategory.stream().map(ctg -> ctg.getCategoryId()).collect(Collectors.toList());
		if (categoryIds.isEmpty()) {
			return listReportItem;
		}
		
		List<DateRangeItemImport> listDateRangeItem = humanItemPub.getDateRangeItemByListCtgId(categoryIds);
		Map<String, DateRangeItemImport> mapCtgWithDateRangeItem = listDateRangeItem.stream().collect(Collectors.toMap(DateRangeItemImport :: getPersonInfoCtgId, x -> x));
		
		for (int i = 0; i < data.inputs.size(); i++) {
			ItemsByCategory itemsByCtg = data.inputs.get(i);
			List<ItemValue> items = itemsByCtg.getItems();
			if (itemsByCtg.getCategoryType() == 3 || itemsByCtg.getCategoryType() == 6) {
				// truong hop la CONTINUOUSHISTORY || CONTINUOUS_HISTORY_FOR_ENDDATE
				DateRangeItemImport dateRangeItem = mapCtgWithDateRangeItem.get(itemsByCtg.getCategoryId());
				String startDateItemId = dateRangeItem.getStartDateItemId();
				String endDateItemId   = dateRangeItem.getEndDateItemId(); 
				
				Optional<ItemValue> itemValueEndDate = items.stream().filter(item -> item.definitionId().equals(endDateItemId)).findFirst();
				if (itemValueEndDate.isPresent()) {
					if (itemValueEndDate.get().value() == null) {
						items.stream().filter(item -> item.definitionId().equals(endDateItemId)).findFirst().get().setValue(GeneralDate.max());
					}
				}
				
				Optional<ItemValue> itemValueStartDate = items.stream().filter(item -> item.definitionId().equals(startDateItemId)).findFirst();
				if (itemValueStartDate.isPresent()) {
					if (itemValueStartDate.get().value() == null) {
						items.stream().filter(item -> item.definitionId().equals(startDateItemId)).findFirst().get().setValue(GeneralDate.min());
					}
				}
			}
			
			
			for (int j = 0; j < items.size(); j++) { 
				ItemValue itemValue = items.get(j);
				Optional<ItemDfCommand> itemDfCommandOpt = listItemDf.stream().filter(it -> it.itemDefId.equals(itemValue.definitionId())).findFirst();
				
				ItemDfCommand itemDfCommand = itemDfCommandOpt.get();
				
				int layoutItemType = 0;
				switch (itemDfCommand.layoutItemType) {
				case "ITEM":
					layoutItemType = 0;
					break;
				case "LIST":
					layoutItemType = 1;
					break;
				case "SeparatorLine":
					layoutItemType = 2;
					break;
				}
				
				ReportItem reportItem = ReportItem.builder().cid(cid).workId(0).reportID(reportId)
						.reportLayoutID(data.reportLayoutID).reportName(data.reportName)
						.layoutItemType(EnumAdaptor.valueOf(layoutItemType, LayoutItemType.class))
						.categoryId(itemDfCommand.categoryId).ctgCode(itemDfCommand.categoryCode)
						.ctgName(itemDfCommand.categoryName).fixedAtr(true).itemId(itemDfCommand.itemDefId)
						.itemCd(itemDfCommand.itemCode).itemName(itemDfCommand.itemName)
						.saveDataAtr(itemValue.saveDataType().value).dspOrder(itemDfCommand.dispOrder)
						.layoutDisOrder(itemDfCommand.layoutDisOrder).contractCode(contractCode).reflectID(0).build();

				switch (itemValue.saveDataType().value) {
				case 1:
					reportItem.setStringVal(itemValue.value());
					break;
				case 2:
					reportItem.setIntVal(itemValue.value());
					break;
				case 3:
					reportItem.setDateVal(itemValue.value());
					break;
				}

				listReportItem.add(reportItem);
				
			}
		}
		return listReportItem;
	}
	
	public void updateData(SaveReportInputContainer data) {
		Integer reportId = data.reportID;
		String cid = AppContexts.user().companyId();
		String inputSid = AppContexts.user().employeeId();
		String applicantSid = data.appSid; 
		String rootSateId = data.rootSateId;
		
		Optional<RegistrationPersonReport> domainReportOpt = repo.getDomainByReportId(cid, reportId);
		if (!domainReportOpt.isPresent()) {
			return;
		}
		
		if (rootSateId == null) {
			rootSateId = saveInfoApporver(data, inputSid, applicantSid, cid, data.reportID);
		}
		
		// アルゴリズム[社員IDから社員情報全般を取得する]を実行する
		// (Thực hiện thuật toán "Lấy Thông tin chung của nhân viên từ employeeID")
		EmployeeInfo empApplicantInfo = this.getPersonInfo(applicantSid, cid);
		
		// アルゴリズム[社員IDから社員情報全般を取得する]を実行する
		// (Thực hiện thuật toán "[Lấy thông tin chung của nhân viên từ ID nhân viên]")
		EmployeeInfo empInfoInput = this.getPersonInfo(inputSid, cid);
		
		
		
		RegistrationPersonReport domainReport = domainReportOpt.get();
		domainReport.setCid(cid);
		domainReport.setRootSateId(rootSateId);
		domainReport.setReportLayoutID(data.reportLayoutID);
		domainReport.setReportCode(data.reportCode);
		domainReport.setReportName(data.reportName);
		domainReport.setRegStatus(data.isSaveDraft == 1 ? RegistrationStatus.Save_Draft : RegistrationStatus.Registration);
		domainReport.setDraftSaveDate(GeneralDateTime.now());
		domainReport.setMissingDocName(data.missingDocName);
		domainReport.setRootSateId(domainReport.getRootSateId() == null ? IdentifierUtil.randomUniqueId() : domainReport.getRootSateId());
		
		domainReport.setInputPid(empInfoInput.inputPid);
		domainReport.setInputSid(empInfoInput.inputSid);
		domainReport.setInputScd(empInfoInput.inputScd);
		domainReport.setInputBussinessName(empInfoInput.inputBussinessName);
		domainReport.setInputDate(GeneralDateTime.now());
		
		domainReport.setAppSid(empApplicantInfo.appliPerSid);
		domainReport.setAppScd(empApplicantInfo.appliPerScd);
		domainReport.setAppPid(empApplicantInfo.appliPerId);
		domainReport.setAppBussinessName(empApplicantInfo.appliPerBussinessName);
		domainReport.setAppDate(GeneralDateTime.now());
		
		domainReport.setAppDevId(empApplicantInfo.appDevId);
		domainReport.setAppDevCd(empApplicantInfo.appDevCd);
		domainReport.setAppDevName(empApplicantInfo.appDevName);
		
		domainReport.setAppPosId(empApplicantInfo.appPosId);
		domainReport.setAppPosCd(empApplicantInfo.appPosCd);
		domainReport.setAppPosName(empApplicantInfo.appPosName);
		
		domainReport.setReportType(EnumAdaptor.valueOf(data.reportType, ReportType.class));
		domainReport.setSendBackSID(data.sendBackSID);
		domainReport.setSendBackComment(data.sendBackComment);
		
		domainReport.setDelFlg(false);
		
		// remove list reportItem
		reportItemRepo.deleteByReportId(cid, reportId);
		
		List<ReportItem> listReportItem = creatDataReportItem(data, reportId);
		
		// 社員情報全般と届出IDをキーに届出パネルの入力内容を「人事届出の登録」、「届出の項目」に登録する
		// (Đăng ký nội dung nhập ở panel report với key là reportID và Thông tin chung của nhân viên vào 「人事届出の登録」、「届出の項目」)
		repo.update(domainReport);
	    reportItemRepo.addAll(listReportItem);
	}
	
	private EmployeeInfo getPersonInfo(String sid, String cid){
		GeneralDate baseDate = GeneralDate.today();
		EmployeeInfo empInfo = empInfoAdaptor.getInfoEmp(sid, cid, baseDate);
		return empInfo;
	}
}
