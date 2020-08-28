package nts.uk.ctx.at.request.infra.repository.application;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.PageSetup;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.AppScreenGenerator;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.applist.service.param.AppListInfo;
import nts.uk.ctx.at.request.dom.application.applist.service.param.ListOfApplication;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * @author anhnm
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AsposeApplicationScreen extends AsposeCellsReportGenerator implements AppScreenGenerator {

	@Inject
	private CompanyAdapter companyAdapter;

	private final String TEMPLATE_FILE = "application/CMM045_template.xlsx";
	private final String OUTPUT_FILE = "申請一覧.xlsx";

	@Override
	public void generate(FileGeneratorContext context, AppListInfo appLst) {
		try {
			val designer = this.createContext(this.TEMPLATE_FILE);

			Workbook workbook = designer.getWorkbook();
			WorksheetCollection worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.get(0);

			String companyId = AppContexts.user().companyId();

			this.printHeader(worksheet, companyId);
			this.printTopR1(worksheet, appLst);
			this.printContent(worksheet, appLst);

			designer.getDesigner().setWorkbook(workbook);
			designer.processDesigner();

			designer.saveAsExcel(this.createNewFile(context, this.getReportName(OUTPUT_FILE)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void printContent(Worksheet worksheet, AppListInfo appLst) {
		List<ListOfApplication> lstApp = appLst.getAppLst();
		Cells cells = worksheet.getCells();

		for(int i = 0; i < lstApp.size(); i++) {
			// Cell of ApplicantName
			Cell cellB = cells.get("B" + i + 3);
			cellB.setValue(lstApp.get(i).getApplicantName());
			// Cell of Application Name
			Cell cellC = cells.get("C" + i + 3);
			cellC.setValue(lstApp.get(i).getAppType().name);
			// Cell of prepost type
			Cell cellD = cells.get("D" + i + 3);
			cellD.setValue(EnumAdaptor.valueOf(lstApp.get(i).getPrePostAtr(), PrePostAtr.class).name);
			// Cell of start date - end date
			Cell cellE = cells.get("E" + i + 3);
			cellE.setValue(lstApp.get(i).getOpAppStartDate());
			// Cell of application content
			Cell cellF = cells.get("F" + i + 3);
			cellF.setValue(lstApp.get(i).getAppContent());
			// Cell of input date
			Cell cellG = cells.get("G" + i + 3);
			cellG.setValue(lstApp.get(i).getInputDate().toString());
			// Cell approval status
			Cell cellH = cells.get("H" + i + 3);
			cellH.setValue(lstApp.get(i).getReflectionStatus());
			// Cell of phase approval
			Cell cellI = cells.get("I" + i + 3);
			cellI.setValue(lstApp.get(i).getOpApprovalStatusInquiry());
		}
	}

	private void printTopR1(Worksheet worksheet, AppListInfo appLst) {
		Cells cells = worksheet.getCells();
		Cell cellB1 = cells.get("B1");
		cellB1.setValue(I18NText.getText("CMM045_29"));
		Cell cellC1 = cells.get("C1");
		cellC1.setValue(new StringBuilder().append(appLst.getDisplaySet().getStartDateDisp()).append("～")
				.append(appLst.getDisplaySet().getEndDateDisp()).toString());

		// Column header background color
		// Style headerBackground = new Style();
		// headerBackground.setBackgroundColor(Color.a("#CFF1A5"));

		Cell cellB2 = cells.get("B2");
		cellB2.setValue(I18NText.getText("CMM045_51"));
		// cellB2.setStyle(headerBackground);
		Cell cellC2 = cells.get("C2");
		cellC2.setValue(I18NText.getText("CMM045_52"));
		// cellC2.setStyle(headerBackground);
		Cell cellD2 = cells.get("D2");
		cellD2.setValue(I18NText.getText("CMM045_53"));
		// cellD2.setStyle(headerBackground);
		Cell cellE2 = cells.get("E2");
		cellE2.setValue(I18NText.getText("CMM045_54"));
		// cellE2.setStyle(headerBackground);
		Cell cellF2 = cells.get("F2");
		cellF2.setValue(I18NText.getText("CMM045_55"));
		// cellF2.setStyle(headerBackground);
		Cell cellG2 = cells.get("G2");
		cellG2.setValue(I18NText.getText("CMM045_56"));
		// cellG2.setStyle(headerBackground);
		Cell cellH2 = cells.get("H2");
		cellH2.setValue(I18NText.getText("CMM045_57"));
		// cellH2.setStyle(headerBackground);
		Cell cellI2 = cells.get("I2");
		cellI2.setValue(I18NText.getText("CMM045_58"));
		// cellI2.setStyle(headerBackground);
	}

	private void printHeader(Worksheet worksheet, String cID) {
		PageSetup pageSetup = worksheet.getPageSetup();
		pageSetup.setFirstPageNumber(1);

		pageSetup.setHeader(0, this.companyAdapter.getCurrentCompany().get().getCompanyName());
		pageSetup.setHeader(1, "applicationName");
		pageSetup.setHeader(2, GeneralDateTime.now().toString());
	}
}
