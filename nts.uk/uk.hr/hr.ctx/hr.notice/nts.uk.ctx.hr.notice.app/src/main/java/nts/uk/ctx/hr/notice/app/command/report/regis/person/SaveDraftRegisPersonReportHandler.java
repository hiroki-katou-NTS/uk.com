/**
 * 
 */
package nts.uk.ctx.hr.notice.app.command.report.regis.person;

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
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReport;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReportRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ReportItem;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ReportItemRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.ApprovalStatusForRegis;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.LayoutItemType;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.RegistrationStatus;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.ReportType;
import nts.uk.ctx.hr.notice.dom.report.valueImported.DateRangeItemImport;
import nts.uk.ctx.hr.notice.dom.report.valueImported.HumanItemPub;
import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInfo;
import nts.uk.ctx.hr.shared.dom.approval.rootstate.ApprovalRootStateHrRepository;
import nts.uk.ctx.hr.shared.dom.employee.EmployeeInformationAdaptor;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.command.ItemsByCategory;

/**
 * @author laitv
 * 下書き保存する (Lưu bản nháp)
 *
 */
@Stateless
public class SaveDraftRegisPersonReportHandler extends CommandHandler<SaveReportInputContainer>{
	
	@Inject
	private RegistrationPersonReportRepository repo;
	
	@Inject
	private ReportItemRepository reportItemRepo;
	
	@Inject
	private EmployeeInformationAdaptor empInfoAdaptor;
	
	@Inject
	private ApprovalRootStateHrRepository approvalRootStateAdaptor;
	
	@Inject
	private HumanItemPub humanItemPub;
	
	/** The Constant TIME_DAY_START. */
	public static final String TIME_DAY_START = " 00:00:00";

	/** The Constant DATE_TIME_FORMAT. */
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static final String MAX_DATE = "9999/12/31";
	public static final String MIN_DATE = "1900/01/01";
	
	
	// アルゴリズム「届出情報を下書き保存」を実行する (Thực hiên thuật toán "Lưu bản nháp thông tin report")
	@Override
	protected void handle(CommandHandlerContext<SaveReportInputContainer> context) {
		SaveReportInputContainer command = context.getCommand();
		ValidateDataCategoryHistory.validate(command);
		if (command.reportID == null) {
			// insert
			insertData(command);
		}else {
			// update
			updateData(command);
		}
	}
	
	public void insertData(SaveReportInputContainer data) {
		String sid = AppContexts.user().employeeId();
		String cid = AppContexts.user().companyId();
		String rootSateId = data.rootSateId;
		Integer reportIDNew = repo.getMaxReportId(cid) + 1;
		
		if (rootSateId == null) {
			// 届出IDを採番する(Đánh số report ID)
			rootSateId = IdentifierUtil.randomUniqueId();
		}
		
		EmployeeInfo employeeInfo = this.getPersonInfo();
		 
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
				.inputPid(employeeInfo.inputPid)
				.inputSid(employeeInfo.inputSid)
				.inputScd(employeeInfo.inputScd)
				.inputBussinessName(employeeInfo.inputBussinessName)
				.inputDate(GeneralDateTime.now())
				.appPid(employeeInfo.appliPerId)
				.appSid(employeeInfo.appliPerSid)
				.appScd(employeeInfo.appliPerScd)
				.appBussinessName(employeeInfo.appliPerBussinessName)
				.appDate(GeneralDateTime.now())
				.appDevId(employeeInfo.appDevId)
				.appDevCd(employeeInfo.appDevCd)
				.appDevName(employeeInfo.appDevName)
				.appPosId(employeeInfo.appPosId)
				.appPosCd(employeeInfo.appPosCd)
				.appPosName(employeeInfo.appPosName)
				.reportType(EnumAdaptor.valueOf(data.reportType, ReportType.class))
				.sendBackSID(data.sendBackSID)
				.sendBackComment(data.sendBackComment)
				.delFlg(false)
				.build();
		
		List<ReportItem> listReportItem = creatDataReportItem(data, reportIDNew);
		
		// 届出IDをキーに届出パネルの入力内容を「人事届出の登録」、「届出の項目」に登録する
		//(Đăng ký nội dung đã nhập ở panel report với key là reportID vào 「人事届出の登録」、「届出の項目」)
		repo.add(personReport);
	    reportItemRepo.addAll(listReportItem);
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
		Map<String, DateRangeItemImport> mapCtgWithDateRangeItem = listDateRangeItem.stream().collect(Collectors.toMap(DateRangeItemImport::getPersonInfoCtgId, x -> x));	
		
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
		Optional<RegistrationPersonReport> domainReportOpt = repo.getDomainByReportId(cid, reportId);
		if (!domainReportOpt.isPresent()) {
			return;
		}
		
		EmployeeInfo employeeInfo = this.getPersonInfo();
		
		RegistrationPersonReport domainReport = domainReportOpt.get();
		domainReport.setCid(cid);
		domainReport.setReportLayoutID(data.reportLayoutID);
		domainReport.setReportCode(data.reportCode);
		domainReport.setReportName(data.reportName);
		domainReport.setRegStatus(data.isSaveDraft == 1 ? RegistrationStatus.Save_Draft : RegistrationStatus.Registration);
		domainReport.setDraftSaveDate(GeneralDateTime.now());
		domainReport.setMissingDocName(data.missingDocName);
		
		domainReport.setInputPid(employeeInfo.inputPid);
		domainReport.setInputSid(employeeInfo.inputSid);
		domainReport.setInputScd(employeeInfo.inputScd);
		domainReport.setInputBussinessName(employeeInfo.inputBussinessName);
		domainReport.setInputDate(GeneralDateTime.now());
		
		domainReport.setAppSid(employeeInfo.appliPerSid);
		domainReport.setAppScd(employeeInfo.appliPerScd);
		domainReport.setAppPid(employeeInfo.appliPerId);
		domainReport.setAppBussinessName(employeeInfo.appliPerBussinessName);
		domainReport.setAppDate(GeneralDateTime.now());
		
		domainReport.setAppDevId(employeeInfo.appDevId);
		domainReport.setAppDevCd(employeeInfo.appDevCd);
		domainReport.setAppDevName(employeeInfo.appDevName);
		
		domainReport.setAppPosId(employeeInfo.appPosId);
		domainReport.setAppPosCd(employeeInfo.appPosCd);
		domainReport.setAppPosName(employeeInfo.appPosName);
		
		domainReport.setReportType(EnumAdaptor.valueOf(data.reportType, ReportType.class));
		domainReport.setSendBackSID(data.sendBackSID);
		domainReport.setSendBackComment(data.sendBackComment);
		
		domainReport.setDelFlg(false);
		
		// remove list reportItem
		reportItemRepo.deleteByReportId(cid, reportId);
		
		List<ReportItem> listReportItem = creatDataReportItem(data, reportId);
		
		// 届出IDをキーに届出パネルの入力内容を「人事届出の登録」、「届出の項目」に登録する
		//(Đăng ký nội dung đã nhập ở panel report với key là reportID vào 「人事届出の登録」、「届出の項目」)
		repo.update(domainReport);
	    reportItemRepo.addAll(listReportItem);
	}
	
	private EmployeeInfo getPersonInfo(){
		String sid = AppContexts.user().employeeId();
		String cid = AppContexts.user().companyId();
		GeneralDate baseDate = GeneralDate.today();
		EmployeeInfo empInfo = empInfoAdaptor.getInfoEmp(sid, cid, baseDate);
		return empInfo;
	}
}
