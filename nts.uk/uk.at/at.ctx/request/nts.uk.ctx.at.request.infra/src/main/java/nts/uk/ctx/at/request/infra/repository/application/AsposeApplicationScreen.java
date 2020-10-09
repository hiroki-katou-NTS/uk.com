package nts.uk.ctx.at.request.infra.repository.application;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.aspose.cells.BackgroundType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.PageSetup;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.WorkbookDesigner;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.at.request.dom.application.AppScreenGenerator;
import nts.uk.ctx.at.request.dom.application.applist.service.param.AppListInfo;
import nts.uk.ctx.at.request.dom.application.applist.service.param.AppLstApprovalLstDispSet;
import nts.uk.ctx.at.request.dom.application.applist.service.param.ListOfApplication;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * @author anhnm
 *
 */
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
	private final String OUTPUT_FILE_EXTENSION = ".xlsx";

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * nts.uk.ctx.at.request.dom.application.AppScreenGenerator#generate(nts.arc.
	 * layer.infra.file.export.FileGeneratorContext, int,
	 * nts.uk.ctx.at.request.dom.application.applist.service.param.AppListInfo)
	 */
	@Override
	public void generate(FileGeneratorContext context, int appListAtr, AppListInfo appLst, String programName) {
		try {
			val designer = this.createContext(this.TEMPLATE_FILE);

			Workbook workbook = designer.getWorkbook();
			WorksheetCollection worksheets = workbook.getWorksheets();

			Worksheet worksheet;
			if (appListAtr == 0) {
				worksheet = worksheets.get(1);
			} else {
				worksheet = worksheets.get(0);
			}

			Cells cells = worksheet.getCells();

			Cell dataB = cells.get("B3");
			Cell dataC = cells.get("C3");
			Cell dataD = cells.get("D3");
			Cell dataE = cells.get("E3");
			Cell dataF = cells.get("F3");
			Cell dataG = cells.get("G3");
			Cell dataH = cells.get("H3");
			Cell dataI = cells.get("I3");

			dataB.setValue("&=dataSource.applicantName");
			dataC.setValue("&=dataSource.appType");
			dataD.setValue("&=dataSource.prePostAtr");
			dataE.setValue("&=dataSource.appStartDate");
			dataF.setValue("&=dataSource.appContent");
			dataG.setValue("&=dataSource.inputDate");
			dataH.setValue("&=dataSource.reflectionStatus");
			dataI.setValue("&=dataSource.approvalStatusInquiry");


			String companyId = AppContexts.user().companyId();

			this.printHeader(worksheet, companyId, programName);
			this.printTopR1(worksheet, appLst);
			this.printContent(workbook, appLst);
			this.applyStyle(worksheet, appLst);

			for (int i = 2; i < appLst.getAppLst().size() + 2; i++) {
				worksheet.autoFitRow(i);
				worksheet.getCells().deleteRow(appLst.getAppLst().size() + 2);
			}

			if (appListAtr == 0) {
				workbook.getWorksheets().removeAt(0);
			} else {
				workbook.getWorksheets().removeAt(1);
			}

			designer.getDesigner().setWorkbook(workbook);
			designer.processDesigner();

			designer.saveAsExcel(this.createNewFile(context, this.getReportName(programName + OUTPUT_FILE_EXTENSION)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param worksheet
	 * @param appLst
	 */
	private void applyStyle(Worksheet worksheet, AppListInfo appLst) {
		Cells cells = worksheet.getCells();

		for (int i = 3; i < appLst.getAppLst().size() + 3; i++) {

			// Chua xu ly voi app type = 10
			// Column E
			Cell cellE = cells.get("E" + i);
			String contentE = cellE.getStringValue();
			this.colorSatSun(cellE, contentE, "ー");

			// Column F

			// Column G
			Cell cellG = cells.get("G" + i);
			String contentG = cellG.getStringValue();
			this.colorSatSun(cellG, contentG, null);

			// Column H
			Cell cellH = cells.get("H" + i);
			String contextH = cellH.getStringValue();
			if (contextH.equals(I18NText.getText("CMM045_63"))) {
				this.backgroundColor(cellH, 191, 234, 96);
			}
			if (contextH.equals(I18NText.getText("CMM045_64"))) {
				this.backgroundColor(cellH, 206, 230, 255);
			}
			if (contextH.equals(I18NText.getText("CMM045_65"))) {
				this.backgroundColor(cellH, 253, 77, 77);
			}
			if (contextH.equals(I18NText.getText("CMM045_62"))) {
				this.backgroundColor(cellH, 255, 255, 255);
			}
			if (contextH.equals(I18NText.getText("CMM045_66"))) {
				this.backgroundColor(cellH, 253, 77, 77);
			}
			if (contextH.equals(I18NText.getText("CMM045_67"))) {
				this.backgroundColor(cellH, 246, 246, 54);
			}
		}
	}

	/**
	 * @param cell
	 *            Cell
	 * @param i
	 *            Red
	 * @param j
	 *            Green
	 * @param k
	 *            Blue
	 * @return
	 */
	private void backgroundColor(Cell cell, int i, int j, int k) {
		Style style = cell.getStyle();
		style.setPattern(BackgroundType.SOLID);
		style.setForegroundColor(Color.fromArgb(i, j, k));
		cell.setStyle(style);
	}

	/**
	 * @param cell
	 * @param content
	 * @param separator
	 */
	private void colorSatSun(Cell cell, String content, String separator) {
		Color colorSaturday = Color.fromArgb(0, 0, 255);
		Color colorSunday = Color.fromArgb(255, 0, 0);

		if (separator != null) {
			String[] datas = content.split(separator);
			for (int j = 0; j < datas.length; j++) {
				if (datas[j].contains("土")) {
					if (j == 0) {
						cell.characters(j, datas[j].length()).getFont().setColor(colorSaturday);
					} else {
						cell.characters(j + datas[0].length(), datas[j].length() + datas[0].length() + 1).getFont()
								.setColor(colorSaturday);
					}
				}
				if (datas[j].contains("日")) {
					if (j == 0) {
						cell.characters(j, datas[j].length()).getFont().setColor(colorSunday);
					} else {
						cell.characters(j + datas[0].length(), datas[j].length() + datas[0].length() + 1).getFont()
								.setColor(colorSunday);
					}
				}
			}
		} else {
			if (content.contains("土")) {
				cell.characters(0, content.length()).getFont().setColor(colorSaturday);
			}
			if (content.contains("日")) {
				cell.characters(0, content.length()).getFont().setColor(colorSunday);
			}
		}
	}

	/**
	 * @param workbook
	 * @param appLst
	 */
	private void printContent(Workbook workbook, AppListInfo appLst) {
		List<ListOfApplication> lstApp = appLst.getAppLst();

		AppLstApprovalLstDispSet dispSet = appLst.getDisplaySet();
		int dispWplName = dispSet.getWorkplaceNameDisp();

		List<AsposeAppScreenDto> dataSource = lstApp.stream()
				.map(x -> AsposeAppScreenDto.fromDomainPrint(x, dispWplName))
				.collect(Collectors.toList());

		WorkbookDesigner designer = new WorkbookDesigner();
		designer.setWorkbook(workbook);

		designer.setDataSource("dataSource", dataSource);

		try {
			designer.process(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param worksheet
	 * @param appLst
	 */
	private void printTopR1(Worksheet worksheet, AppListInfo appLst) {
		Cells cells = worksheet.getCells();
		Cell cellB1 = cells.get("B1");
		cellB1.setValue(I18NText.getText("CMM045_29"));
		Cell cellC1 = cells.get("C1");
		cellC1.setValue(new StringBuilder().append(appLst.getDisplaySet().getStartDateDisp()).append("～")
				.append(appLst.getDisplaySet().getEndDateDisp()).toString());

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

	private void printHeader(Worksheet worksheet, String cID, String programName) {
		PageSetup pageSetup = worksheet.getPageSetup();
		pageSetup.setFirstPageNumber(1);

		pageSetup.setHeader(0, this.companyAdapter.getCurrentCompany().get().getCompanyName());
		pageSetup.setHeader(1, programName);
		// pageSetup.setHeader(2, GeneralDateTime.now().toString());
	}
}
