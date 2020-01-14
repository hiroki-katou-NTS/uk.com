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
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
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
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.command.ItemsByCategory;

/**
 * @author laitv
 *
 */
@Stateless
public class SaveDraftRegisPersonReportHandler extends CommandHandler<SaveReportInputContainer>{
	
	@Inject
	private RegistrationPersonReportRepository repo;
	
	@Inject
	private ReportItemRepository reportItemRepo;
	
	/** The Constant TIME_DAY_START. */
	public static final String TIME_DAY_START = " 00:00:00";

	/** The Constant DATE_TIME_FORMAT. */
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static final String MAX_DATE = "9999/12/31";
	public static final String MIN_DATE = "1900/01/01";

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
	
	private void insertData(SaveReportInputContainer data) {
		String sid = AppContexts.user().employeeId();
		String cid = AppContexts.user().companyId();
		String rootSateId = IdentifierUtil.randomUniqueId();
		Integer reportIDMax = repo.getMaxReportId(sid, cid);
		 
		RegistrationPersonReport personReport = RegistrationPersonReport.builder()
				.cid(cid)
				.rootSateId(rootSateId)
				.workId(data.workId)
				.reportID(reportIDMax + 1)
				.reportLayoutID(data.reportLayoutID)
				.reportCode(data.reportCode)
				.reportName(data.reportName)
				.reportDetail("") // chưa đặt hàng lần này
				.regStatus(RegistrationStatus.Save_Draft)
				.aprStatus(ApprovalStatusForRegis.Not_Started)
				.draftSaveDate(GeneralDateTime.now())
				.missingDocName(data.missingDocName)
				.inputPid("")
				.inputSid(sid)
				.inputScd("")
				.inputBussinessName("")
				.inputDate(GeneralDateTime.now())
				.appPid("")
				.appSid(sid)
				.appScd("")
				.appBussinessName("")
				.appDate(GeneralDateTime.now())
				.appDevId(sid)
				.appDevCd("")
				.appDevName("")
				.appPosId("")
				.appPosCd("")
				.appPosName("")
				.reportType(ReportType.EMPLOYEE_REGISTRATION) // tam thoi
				.sendBackSID(data.sendBackSID)
				.sendBackComment(data.sendBackComment)
				.delFlg(false)
				.build();
		
		List<ReportItem> listReportItem = creatDataReportItem(data, reportIDMax);
		
		repo.add(personReport);
	    reportItemRepo.addAll(listReportItem);
	}
	
	private List<ReportItem> creatDataReportItem(SaveReportInputContainer data, Integer reportId) {

		List<ReportItem> listReportItem = new ArrayList<ReportItem>();
		List<ItemDfCommand> listItemDf = data.listItemDf;
		String cid = AppContexts.user().companyId();
		String contractCode = AppContexts.user().contractCode();
		
		for (int i = 0; i < data.inputs.size(); i++) {
			ItemsByCategory itemsByCtg = data.inputs.get(i);
			List<ItemValue> items = itemsByCtg.getItems();
			for (int j = 0; j < items.size(); j++) { // COM1_000000000000000_CS00001_IS00001
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
				
				ReportItem reportItem = ReportItem.builder().cid(cid).workId(0).reportID(reportId + 1)
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
	

	private void updateData(SaveReportInputContainer data) {
		Integer reportId = data.reportID;
		String sid = AppContexts.user().employeeId();
		String cid = AppContexts.user().companyId();
		Optional<RegistrationPersonReport> domainReportOpt = repo.getDomainByReportId(cid, reportId);
		if (!domainReportOpt.isPresent()) {
			return;
		}
		
		RegistrationPersonReport domainReport = domainReportOpt.get();
		domainReport.setReportLayoutID(data.reportLayoutID);
		domainReport.setReportCode(data.reportCode);
		domainReport.setReportName(data.reportName);
		domainReport.setDraftSaveDate(GeneralDateTime.now());
		domainReport.setMissingDocName(data.missingDocName);
		domainReport.setInputPid("");
		domainReport.setInputSid(sid);
		domainReport.setInputScd("");
		domainReport.setInputBussinessName("");
		domainReport.setInputDate(GeneralDateTime.now());
		domainReport.setAppSid(sid);
		domainReport.setAppScd("");
		domainReport.setAppPid("");
		domainReport.setAppBussinessName("");
		domainReport.setAppDate(GeneralDateTime.now());
		domainReport.setAppDevId(sid);
		domainReport.setAppDevCd("");
		domainReport.setAppDevName("");
		domainReport.setAppPosId(sid);
		domainReport.setAppPosCd("");
		domainReport.setAppPosName("");
		domainReport.setSendBackSID(data.sendBackSID);
		domainReport.setSendBackComment(data.sendBackComment);
		
		// remove list reportItem
		reportItemRepo.deleteByReportId(cid, reportId);
		
		List<ReportItem> listReportItem = creatDataReportItem(data, reportId);
		
		repo.update(domainReport);
	    reportItemRepo.addAll(listReportItem);
		
	}
}
