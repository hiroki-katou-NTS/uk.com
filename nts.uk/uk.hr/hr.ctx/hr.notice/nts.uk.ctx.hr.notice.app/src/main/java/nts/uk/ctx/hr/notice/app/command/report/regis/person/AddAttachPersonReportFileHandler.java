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
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.hr.notice.dom.report.registration.person.AttachPersonReportFileRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.AttachmentPersonReportFile;
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
import nts.uk.ctx.hr.shared.dom.employee.EmployeeInformationAdaptor;
import nts.uk.ctx.hr.shared.dom.primitiveValue.AttachedFileName;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.command.ItemsByCategory;

/**
 * @author laitv
 * アルゴリズム「添付ファイル登録処理」を実行する(Thực hiện thuật toán "Xử lý đăng ký file đính kèm")
 */
@Stateless
public class AddAttachPersonReportFileHandler extends CommandHandlerWithResult<AddDocumentReportCommand, String> {

	@Inject
	private AttachPersonReportFileRepository repoReportFile;
	
	@Inject
	private RegistrationPersonReportRepository repoPersonReport;
	
	@Inject
	private ReportItemRepository reportItemRepo;
	
	@Inject
	private EmployeeInformationAdaptor empInfoAdaptor;
	
	@Inject
	private HumanItemPub humanItemPub;

	
	@Override
	protected String handle(CommandHandlerContext<AddDocumentReportCommand> context) {
		AddDocumentReportCommand command = context.getCommand();
		GeneralDateTime fileStorageDate = GeneralDateTime.now();
		Boolean fromJhn002 = command.fromJhn002;
		String agentName   = command.agentName;
		 String agentSid   = command.agentSid;
		
		String cid = AppContexts.user().companyId();
		try {
			AttachedFileName validateFileName = new AttachedFileName(command.fileName);
			validateFileName.validate();
		} catch (Exception e) {
			throw new BusinessException("MsgJ_JHN001_1");
		}
		
		if (command.reportID == null) {
			Integer reportIdNew = repoPersonReport.getMaxReportId(cid) + 1;
			AttachmentPersonReportFile domain = AttachmentPersonReportFile.createFromJavaType(cid, reportIdNew,
					command.docID, command.docName, command.fileId, command.fileName ,
					command.fileAttached == 1 ? true : false, fileStorageDate, command.mimeType, command.fileTypeName,
							command.fileSize, command.delFlg == 1 ? true : false, command.sampleFileID, command.sampleFileName);
			
			repoReportFile.add(domain);
			
			saveDraft(command.dataLayout, reportIdNew, command.missingDocName, fromJhn002, agentName, agentSid);
			
			return reportIdNew.toString();
		} else {
			AttachmentPersonReportFile domain = AttachmentPersonReportFile.createFromJavaType(cid, Integer.valueOf(command.reportID),
					command.docID, command.docName, command.fileId, command.fileName ,
					command.fileAttached == 1 ? true : false, fileStorageDate, command.mimeType, command.fileTypeName,
							command.fileSize, command.delFlg == 1 ? true : false, command.sampleFileID, command.sampleFileName);
			
			repoReportFile.add(domain);
			
			updateMissingDocName(command);
			                           
			return command.reportID;
		}
	}
	
	private void updateMissingDocName(AddDocumentReportCommand command) {
		String cid = AppContexts.user().companyId();
		this.repoPersonReport.updateMissingDocName(cid, Integer.valueOf(command.getReportID()), command.getMissingDocName());
	}
	

	public void saveDraft(SaveReportInputContainer data, Integer reportIDNew, String missingDocName, Boolean fromJhn002,String agentName,String agentSid) {
		String cid = AppContexts.user().companyId();
		
		// アルゴリズム[社員IDから社員情報全般を取得する]を実行する
		// (Thực hiện thuật toán "[Lấy thông tin chung của nhân viên từ ID nhân viên]")
		EmployeeInfo employeeInfo = this.getPersonInfo();
		EmployeeInfo empInfoAgent = null;
		
		if (agentSid != null && agentSid != "") {
			empInfoAgent = empInfoAdaptor.getInfoEmp(agentSid, cid, GeneralDate.today());
		}
		
		RegistrationPersonReport personReport = RegistrationPersonReport.builder()
				.cid(cid)
				.rootSateId(null)
				.workId(data.workId)
				.reportID(reportIDNew)
				.reportLayoutID(data.reportLayoutID)
				.reportCode(data.reportCode)
				.reportName(data.reportName)
				.reportDetail(null) // chưa đặt hàng lần này
				.regStatus(RegistrationStatus.Save_Draft)
				.aprStatus(ApprovalStatusForRegis.Not_Started)
				.draftSaveDate(GeneralDateTime.now())
				.missingDocName(missingDocName)
				.inputPid(employeeInfo.inputPid)
				.inputSid(employeeInfo.inputSid)
				.inputScd(employeeInfo.inputScd)
				.inputBussinessName(employeeInfo.inputBussinessName)
				.inputDate(GeneralDateTime.now())
				
				.appPid(fromJhn002 ? (empInfoAgent == null ? null : empInfoAgent.appliPerId)  : (employeeInfo.appliPerId))
				.appSid(fromJhn002 ? (empInfoAgent == null ? null : empInfoAgent.appliPerSid) : (employeeInfo.appliPerSid))
				.appScd(fromJhn002 ? (empInfoAgent == null ? null : empInfoAgent.appliPerScd) : (employeeInfo.appliPerScd))
				.appBussinessName(fromJhn002 ? (empInfoAgent == null ? null : empInfoAgent.appliPerBussinessName) : (employeeInfo.appliPerBussinessName))
				.appDate(GeneralDateTime.now())
				.appDevId(fromJhn002 ? (empInfoAgent == null ? null : empInfoAgent.appDevId) : (employeeInfo.appDevId))
				.appDevCd(fromJhn002 ? (empInfoAgent == null ? null : empInfoAgent.appDevCd) : (employeeInfo.appDevCd))
				.appDevName(fromJhn002 ? (empInfoAgent == null ? null : empInfoAgent.appDevName) : (employeeInfo.appDevName))
				.appPosId(fromJhn002 ? (empInfoAgent == null ? null : empInfoAgent.appPosId) : (employeeInfo.appPosId))
				.appPosCd(fromJhn002 ? (empInfoAgent == null ? null : empInfoAgent.appPosCd) : (employeeInfo.appPosCd))
				.appPosName(fromJhn002 ? (empInfoAgent == null ? null : empInfoAgent.appPosName) : (employeeInfo.appPosName))
				.reportType(EnumAdaptor.valueOf(data.reportType, ReportType.class))
				.sendBackSID(data.sendBackSID)
				.sendBackComment(data.sendBackComment)
				.delFlg(false)
				.build();
		
		List<ReportItem> listReportItem = creatDataReportItem(data, reportIDNew);
		
		repoPersonReport.add(personReport);
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
		Map<String, DateRangeItemImport> mapCtgWithDateRangeItem = listDateRangeItem.stream().collect(Collectors.toMap(DateRangeItemImport :: getPersonInfoCtgId, x -> x));
		
		for (int i = 0; i < data.inputs.size(); i++) {
			ItemsByCategory itemsByCtg = data.inputs.get(i);
			List<ItemValue> items = itemsByCtg.getItems();
			if (itemsByCtg.getCategoryType() == 3 || itemsByCtg.getCategoryType() == 6) {
				// truong hop la CONTINUOUSHISTORY || CONTINUOUS_HISTORY_FOR_ENDDATE
				DateRangeItemImport dateRangeItem = mapCtgWithDateRangeItem.get(itemsByCtg.getCategoryId());
				String startDateItemId = dateRangeItem.getStartDateItemId();
				String endDateItemId   = dateRangeItem.getEndDateItemId(); 
				
				// trương hợp startD được nhập thì set endDate là maxDate. còn case startD == null thì khong lam gì cả.
				Optional<ItemValue> itemValueStartDate = items.stream().filter(item -> item.definitionId().equals(startDateItemId)).findFirst();
				if (itemValueStartDate.isPresent()) {
					if (itemValueStartDate.get().value() != null) {
						Optional<ItemValue> itemValueEndDate = items.stream().filter(item -> item.definitionId().equals(endDateItemId)).findFirst();
						if (itemValueEndDate.isPresent()) {
							if (itemValueEndDate.get().value() == null) {
								items.stream().filter(item -> item.definitionId().equals(endDateItemId)).findFirst().get().setValue(GeneralDate.max());
							}
						}
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
	
	private EmployeeInfo getPersonInfo(){
		String sid = AppContexts.user().employeeId();
		String cid = AppContexts.user().companyId();
		GeneralDate baseDate = GeneralDate.today();
		EmployeeInfo empInfo = empInfoAdaptor.getInfoEmp(sid, cid, baseDate);
		return empInfo;
	}

}
