/**
 * 
 */
package nts.uk.ctx.hr.notice.app.command.report.regis.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
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

	
	@Override
	protected String handle(CommandHandlerContext<AddDocumentReportCommand> context) {
		AddDocumentReportCommand command = context.getCommand();
		GeneralDateTime fileStorageDate = GeneralDateTime.now();
		
		String sid = AppContexts.user().employeeId();
		String cid = AppContexts.user().companyId();
		
		if (command.reportID == null) {
			Integer reportIdNew = repoPersonReport.getMaxReportId(cid) + 1;
			AttachmentPersonReportFile domain = AttachmentPersonReportFile.createFromJavaType(cid, reportIdNew,
					command.docID, command.docName, command.fileId, command.fileName.length() > 49 ? command.fileName.substring(0, 48) : command.fileName ,
					command.fileAttached == 1 ? true : false, fileStorageDate, command.mimeType, command.fileTypeName,
							command.fileSize, command.delFlg == 1 ? true : false, command.sampleFileID, command.sampleFileName);
			
			repoReportFile.add(domain);
			
			saveDraft(command.dataLayout, reportIdNew, command.missingDocName);
			
			return reportIdNew.toString();
		} else {
			AttachmentPersonReportFile domain = AttachmentPersonReportFile.createFromJavaType(cid, Integer.valueOf(command.reportID),
					command.docID, command.docName, command.fileId, command.fileName.length() > 50 ? command.fileName.substring(0, 49) : command.fileName ,
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
	

	public void saveDraft(SaveReportInputContainer data, Integer reportIDNew, String missingDocName) {
		String sid = AppContexts.user().employeeId();
		String pid = AppContexts.user().personId();
		String scd = AppContexts.user().employeeCode();
		String cid = AppContexts.user().companyId();
		
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
				.inputPid(pid)
				.inputSid(sid)
				.inputScd(scd)
				.inputBussinessName("")
				.inputDate(GeneralDateTime.now())
				.appPid(pid)
				.appSid(sid)
				.appScd(scd)
				.appBussinessName("")
				.appDate(GeneralDateTime.now())
				.appDevId(sid)
				.appDevCd(scd)
				.appDevName("")
				.appPosId(sid)
				.appPosCd(scd)
				.appPosName("")
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
		
		for (int i = 0; i < data.inputs.size(); i++) {
			ItemsByCategory itemsByCtg = data.inputs.get(i);
			List<ItemValue> items = itemsByCtg.getItems();
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

}
