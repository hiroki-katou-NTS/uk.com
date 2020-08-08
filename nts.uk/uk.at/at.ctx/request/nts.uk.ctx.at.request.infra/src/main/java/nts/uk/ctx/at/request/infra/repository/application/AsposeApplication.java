package nts.uk.ctx.at.request.infra.repository.application;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.PageSetup;
import com.aspose.cells.TextBox;
import com.aspose.cells.TextBoxCollection;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.service.print.ApplicationGenerator;
import nts.uk.ctx.at.request.dom.application.common.service.print.ApproverPrintDetails;
import nts.uk.ctx.at.request.dom.application.common.service.print.PrintContentOfApp;
import nts.uk.ctx.at.request.infra.repository.application.lateleaveearly.AsposeLateLeaveEarly;
import nts.uk.ctx.at.request.infra.repository.application.workchange.AsposeWorkChange;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AsposeApplication extends AsposeCellsReportGenerator implements ApplicationGenerator {

	@Inject
	private AsposeWorkChange asposeWorkChange;

	@Inject
	private AsposeLateLeaveEarly asposeLateLeaveEarly;

	@Override
	public void generate(FileGeneratorContext generatorContext, PrintContentOfApp printContentOfApp, ApplicationType appType) {
		try {

			val designer = this.createContext(this.getFileTemplate(appType));
			Workbook workbook = designer.getWorkbook();
			WorksheetCollection worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.get(0);
			printPageHeader(worksheet, printContentOfApp);
			printTopKAF000(worksheet, printContentOfApp);
			printEachAppContent(worksheet, printContentOfApp, appType);
			designer.getDesigner().setWorkbook(workbook);
			designer.processDesigner();

			//SaveOptions saveOptions = SaveOptions.;
			//designer.saveWithOtherOption(this.createNewFile(generatorContext, this.getReportName(this.getFileName(appType))), saveOptions);
			designer.saveAsExcel(this.createNewFile(generatorContext, this.getReportName(this.getFileName(appType))));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private void printEachAppContent(Worksheet worksheet, PrintContentOfApp printContentOfApp, ApplicationType appType) {
		Cell comboReasonLabel;
		Cell textReasonLabel;
		Cell comboReasonContent;
		Cell textReasonContent;

		switch (appType) {
		case OVER_TIME_APPLICATION:
			break;
		case ABSENCE_APPLICATION:
			break;
		case WORK_CHANGE_APPLICATION:
			asposeWorkChange.printWorkChangeContent(worksheet, printContentOfApp);
			comboReasonLabel = worksheet.getCells().get("B15");
			textReasonLabel = worksheet.getCells().get("B18");
			comboReasonContent = worksheet.getCells().get("D15");
			textReasonContent = worksheet.getCells().get("D18");
			printBottomKAF000(comboReasonLabel, textReasonLabel, comboReasonContent, textReasonContent, printContentOfApp);
			break;
		case BUSINESS_TRIP_APPLICATION:
			break;
		case GO_RETURN_DIRECTLY_APPLICATION:
			break;
		case HOLIDAY_WORK_APPLICATION:
			break;
		case STAMP_APPLICATION:
			break;
		case ANNUAL_HOLIDAY_APPLICATION:
			break;
		case EARLY_LEAVE_CANCEL_APPLICATION:
			asposeLateLeaveEarly.printLateEarlyContent(worksheet, printContentOfApp);
			comboReasonLabel = worksheet.getCells().get("B13");
			textReasonLabel = worksheet.getCells().get("B16");
			comboReasonContent = worksheet.getCells().get("D13");
			textReasonContent = worksheet.getCells().get("D16");
			printBottomKAF000(comboReasonLabel, textReasonLabel, comboReasonContent, textReasonContent,
					printContentOfApp);
			break;
		case COMPLEMENT_LEAVE_APPLICATION:
			break;
		case OPTIONAL_ITEM_APPLICATION:
			break;
		default:
			break;
		}
	}

	private String getFileTemplate(ApplicationType appType) {
		switch (appType) {
		case OVER_TIME_APPLICATION:
			return "";
		case ABSENCE_APPLICATION:
			return "";
		case WORK_CHANGE_APPLICATION:
			return "application/KAF007_template.xlsx";
		case BUSINESS_TRIP_APPLICATION:
			return "";
		case GO_RETURN_DIRECTLY_APPLICATION:
			return "";
		case HOLIDAY_WORK_APPLICATION:
			return "";
		case STAMP_APPLICATION:
			return "";
		case ANNUAL_HOLIDAY_APPLICATION:
			return "";
		case EARLY_LEAVE_CANCEL_APPLICATION:
			return "application/KAF004_template.xlsx";
		case COMPLEMENT_LEAVE_APPLICATION:
			return "";
		case OPTIONAL_ITEM_APPLICATION:
			return "";
		default:
			return "testAppTemplate";
		}
	}

	private String getFileName(ApplicationType appType) {
		switch (appType) {
		case OVER_TIME_APPLICATION:
			return "";
		case ABSENCE_APPLICATION:
			return "";
		case WORK_CHANGE_APPLICATION:
			return "KAF007_template.xlsx";
		case BUSINESS_TRIP_APPLICATION:
			return "";
		case GO_RETURN_DIRECTLY_APPLICATION:
			return "";
		case HOLIDAY_WORK_APPLICATION:
			return "";
		case STAMP_APPLICATION:
			return "";
		case ANNUAL_HOLIDAY_APPLICATION:
			return "";
		case EARLY_LEAVE_CANCEL_APPLICATION:
			return "遅刻早退取消申請.xlsx";
		case COMPLEMENT_LEAVE_APPLICATION:
			return "";
		case OPTIONAL_ITEM_APPLICATION:
			return "";
		default:
			return "testAppName";
		}
	}

	private void printPageHeader(Worksheet worksheet, PrintContentOfApp printContentOfApp) {
		PageSetup pageSetup = worksheet.getPageSetup();
		pageSetup.setFirstPageNumber(1);
		pageSetup.setHeader(0, "&9&\"ＭＳ ゴシック\"" + printContentOfApp.getCompanyName());
		pageSetup.setHeader(1, "&16&\"ＭＳ ゴシック\"" + "applicationName");
		pageSetup.setHeader(2, "&9&\"ＭＳ ゴシック\"" + GeneralDateTime.now().toString());
	}

	private void printTopKAF000(Worksheet worksheet, PrintContentOfApp printContentOfApp) {
		Cells cells = worksheet.getCells();
		TextBoxCollection textBoxCollection = worksheet.getTextBoxes();
		// resource text
		Cell cellB3 = cells.get("B3");
		cellB3.setValue(I18NText.getText("Com_Workplace"));
		Cell cellB4 = cells.get("B4");
		cellB4.setValue(I18NText.getText("Com_Person"));
		Cell cellB6 = cells.get("B6");
		cellB6.setValue(I18NText.getText("KAF000_49"));
		Cell cellB7 = cells.get("B7");
		cellB7.setValue(I18NText.getText("KAF000_46"));
		// value
		Cell cellC3 = cells.get("C3");
		cellC3.setValue(printContentOfApp.getCompanyName());
		Cell cellC4 = cells.get("C4");
		cellC4.setValue(printContentOfApp.getEmployeeInfoLst().get(0).getBussinessName());
		if(printContentOfApp.getApproverColumnContents().getApproverPrintDetailsLst().size() > 1) {
			ApproverPrintDetails approverPrintDetails1 = printContentOfApp.getApproverColumnContents().getApproverPrintDetailsLst().get(0);
			Cell cellG1 = cells.get("G1");
			cellG1.setValue(approverPrintDetails1.getAffJobTitleHistoryImport().getJobTitleName());
			TextBox textBoxName1 = textBoxCollection.get("NAME1");
			textBoxName1.setText(approverPrintDetails1.getEmployeeInfoImport().getBussinessName());
			TextBox textBoxDate1 = textBoxCollection.get("DATE1");
			textBoxDate1.setText(approverPrintDetails1.getOpApprovalDate().map(x -> x.toString()).orElse(null));
			TextBox textBoxStatus1 = textBoxCollection.get("STATUS1");
			textBoxStatus1.setText(approverPrintDetails1.getApprovalBehaviorAtr().name);
		}
		if(printContentOfApp.getApproverColumnContents().getApproverPrintDetailsLst().size() > 2) {
			ApproverPrintDetails approverPrintDetails2 = printContentOfApp.getApproverColumnContents().getApproverPrintDetailsLst().get(1);
			Cell cellH1 = cells.get("H1");
			cellH1.setValue(approverPrintDetails2.getAffJobTitleHistoryImport().getJobTitleName());
			TextBox textBoxName2 = textBoxCollection.get("NAME2");
			textBoxName2.setText(approverPrintDetails2.getEmployeeInfoImport().getBussinessName());
			TextBox textBoxDate2 = textBoxCollection.get("DATE2");
			textBoxDate2.setText(approverPrintDetails2.getOpApprovalDate().map(x -> x.toString()).orElse(null));
			TextBox textBoxStatus2 = textBoxCollection.get("STATUS2");
			textBoxStatus2.setText(approverPrintDetails2.getApprovalBehaviorAtr().name);
		}
		if(printContentOfApp.getApproverColumnContents().getApproverPrintDetailsLst().size() > 2) {
			ApproverPrintDetails approverPrintDetails3 = printContentOfApp.getApproverColumnContents().getApproverPrintDetailsLst().get(2);
			Cell cellI1 = cells.get("I1");
			cellI1.setValue(approverPrintDetails3.getAffJobTitleHistoryImport().getJobTitleName());
			TextBox textBoxName3 = textBoxCollection.get("NAME3");
			textBoxName3.setText(approverPrintDetails3.getEmployeeInfoImport().getBussinessName());
			TextBox textBoxDate3 = textBoxCollection.get("DATE3");
			textBoxDate3.setText(approverPrintDetails3.getOpApprovalDate().map(x -> x.toString()).orElse(null));
			TextBox textBoxStatus3 = textBoxCollection.get("STATUS3");
			textBoxStatus3.setText(approverPrintDetails3.getApprovalBehaviorAtr().name);
		}
		if(printContentOfApp.getApproverColumnContents().getApproverPrintDetailsLst().size() > 4) {
			ApproverPrintDetails approverPrintDetails4 = printContentOfApp.getApproverColumnContents().getApproverPrintDetailsLst().get(3);
			Cell cellJ1 = cells.get("J1");
			cellJ1.setValue(approverPrintDetails4.getAffJobTitleHistoryImport().getJobTitleName());
			TextBox textBoxName4 = textBoxCollection.get("NAME4");
			textBoxName4.setText(approverPrintDetails4.getEmployeeInfoImport().getBussinessName());
			TextBox textBoxDate4 = textBoxCollection.get("DATE4");
			textBoxDate4.setText(approverPrintDetails4.getOpApprovalDate().map(x -> x.toString()).orElse(null));
			TextBox textBoxStatus4 = textBoxCollection.get("STATUS4");
			textBoxStatus4.setText(approverPrintDetails4.getApprovalBehaviorAtr().name);
		}
		if(printContentOfApp.getApproverColumnContents().getApproverPrintDetailsLst().size() > 5) {
			ApproverPrintDetails approverPrintDetails5 = printContentOfApp.getApproverColumnContents().getApproverPrintDetailsLst().get(4);
			Cell cellK1 = cells.get("K1");
			cellK1.setValue(approverPrintDetails5.getAffJobTitleHistoryImport().getJobTitleName());
			TextBox textBoxName5 = textBoxCollection.get("NAME5");
			textBoxName5.setText(approverPrintDetails5.getEmployeeInfoImport().getBussinessName());
			TextBox textBoxDate5 = textBoxCollection.get("DATE5");
			textBoxDate5.setText(approverPrintDetails5.getOpApprovalDate().map(x -> x.toString()).orElse(null));
			TextBox textBoxStatus5 = textBoxCollection.get("STATUS5");
			textBoxStatus5.setText(approverPrintDetails5.getApprovalBehaviorAtr().name);
		}

		Cell cellD6 = cells.get("D6");
		GeneralDate startDate = printContentOfApp.getAppStartDate();
		GeneralDate endDate = printContentOfApp.getAppEndDate();
		if(startDate != null && endDate != null) {
			if(startDate.equals(endDate)) {
				cellD6.setValue(printContentOfApp.getAppDate().toString("yyyy年　MM月　dd日　(E)"));
			} else {
				String text = startDate.toString("yyyy年　MM月　dd日　(E)") + "～" + endDate.toString("yyyy年　MM月　dd日　(E)");
				cellD6.setValue(text);
			}
		} else {
			cellD6.setValue(printContentOfApp.getAppDate().toString("yyyy年　MM月　dd日　(E)"));
		}

		Cell cellD7 = cells.get("D7");
		cellD7.setValue(printContentOfApp.getPrePostAtr().name);
	}

	private void printBottomKAF000(Cell comboReasonLabel, Cell textReasonLabel, Cell comboReasonContent, Cell textReasonContent,
			PrintContentOfApp printContentOfApp) {
		comboReasonLabel.setValue(I18NText.getText("KAF000_52"));
		textReasonLabel.setValue(I18NText.getText("KAF000_59"));
		if(printContentOfApp.getAppReasonStandard() != null) {
			comboReasonContent.setValue(printContentOfApp.getAppReasonStandard().getReasonTypeItemLst().stream().findFirst()
					.map(x -> x.getOpReasonForFixedForm().map(y -> y.v()).orElse(null)).orElse(null));
		}
		if(printContentOfApp.getOpAppReason() != null) {
			textReasonContent.setValue(printContentOfApp.getOpAppReason().v());
		}
	}

}
