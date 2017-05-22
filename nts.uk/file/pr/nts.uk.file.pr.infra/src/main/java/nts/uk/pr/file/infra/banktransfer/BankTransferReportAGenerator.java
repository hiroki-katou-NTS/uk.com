package nts.uk.pr.file.infra.banktransfer;

import java.util.List;

import javax.ejb.Stateless;

import com.aspose.cells.PageSetup;
import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;
import com.aspose.cells.WorkbookDesigner;
import com.aspose.cells.Worksheet;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.core.dom.enums.SparePayAtr;
import nts.uk.file.pr.app.export.banktransfer.BankTransferRpAGenerator;
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferAReport;
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferARpData;
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferARpHeader;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class BankTransferReportAGenerator extends AsposeCellsReportGenerator implements BankTransferRpAGenerator {

	/** The Constant TEMPLATE_FILE. */
	private static final String TEMPLATE_FILE = "report/qpp014a.xlsx";
	/** The Constant REPORT_FILE_NAME. */
	protected static final String REPORT_FILE_NAME = "テストQPP014_{0}.pdf";

	@Override
	public void generator(FileGeneratorContext fileGeneratorContext, List<BankTransferARpData> report) {
		try {
			AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE);
			
			Worksheet mainWorkSheet = reportContext.getWorkbook().getWorksheets().get(0);
			Workbook workbook = new Workbook();
			workbook.getWorksheets().removeAt(0);
			WorkbookDesigner designer = new WorkbookDesigner();
			designer.setWorkbook(workbook);
			
			int sheetNumber = 0;
			for (BankTransferARpData item : report) {
				workbook = BankTranferReportUtil.addWorksheet(workbook, mainWorkSheet);
				
				Worksheet worksheet = workbook.getWorksheets().get(sheetNumber);
				worksheet.replace("header", String.valueOf("header_" + sheetNumber));
				worksheet.replace("list", String.valueOf("list_" + sheetNumber));
				
				// set datasource
				designer.setDataSource("header_" + sheetNumber, item.getHeader());
				designer.setDataSource("list_" + sheetNumber, item.getDataSalaryList());
				
				// set color for row
				BankTranferReportUtil.rowColor(workbook, item.getDataSalaryList().size(), sheetNumber);
				
				PageSetup pageSetup = designer.getWorkbook().getWorksheets().get(sheetNumber).getPageSetup();
				pageSetup.setHeader(0, item.getHeader().getCompanyName());
				pageSetup.setHeader(2, "&D &T");
				
				// process data binginds in template
//				reportContext.getWorkbook().calculateFormula(true);
//				reportContext.getDesigner().process(false);
				
//				// save as PDF file
//				PdfSaveOptions option = new PdfSaveOptions(SaveFormat.PDF);
//				option.setAllColumnsInOnePagePerSheet(true);
//	
//				reportContext.getWorkbook().save(this.createNewFile(fileGeneratorContext, BankTranferReportUtil.getFileName(REPORT_FILE_NAME)), option);
				
				sheetNumber ++;
			}
			
			// process data binginds in template
			designer.getWorkbook().calculateFormula(true);
			designer.process(false);
			
			// save as PDF file
			PdfSaveOptions option = new PdfSaveOptions(SaveFormat.PDF);
			option.setAllColumnsInOnePagePerSheet(true);
			
			String fileName = BankTranferReportUtil.getFileName(REPORT_FILE_NAME);
			designer.getWorkbook().save(this.createNewFile(fileGeneratorContext, fileName), option);
			
//			List<BankTransferARpData> rpData = selectDataSource(report);
//
//			if (!addSheet(fileGeneratorContext, reportContext, report)) {
//				
//			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private List<BankTransferARpData> selectDataSource(BankTransferAReport report) {
		if ("1".equals(report.getSparePayAtr())) {
			return report.getSalaryPortionList();
		} else if ("2".equals(report.getSparePayAtr())) {
			return report.getSalaryPreliminaryMonthList();
		} else {
			return null;
		}
	}
	
	private boolean addSheet(FileGeneratorContext fileGeneratorContext, AsposeCellsReportContext reportContext, BankTransferAReport report) {
		if (report.getSparePayAtr() != 3) {
			return false;
		}
		
		Worksheet mainWorkSheet = reportContext.getWorkbook().getWorksheets().get(0);
		Workbook workbook = new Workbook();
		workbook.getWorksheets().removeAt(0);
		WorkbookDesigner designer = new WorkbookDesigner();
		designer.setWorkbook(workbook);
							
		int sheetNumber = 0;
        for(SparePayAtr item: SparePayAtr.values()) {
			workbook = BankTranferReportUtil.addWorksheet(workbook, mainWorkSheet);
			
			Worksheet worksheet = workbook.getWorksheets().get(sheetNumber);
			worksheet.replace("header", String.valueOf("header_" + sheetNumber));
			worksheet.replace("list", String.valueOf("list_" + sheetNumber));
			
			BankTransferARpHeader header = report.getHeader();

			header.setState(SparePayAtr.NORMAL.equals(item) ? "【給与分】" : "【給与予備月】");	
						
			List<BankTransferARpData> rpData = SparePayAtr.NORMAL.equals(item) 
					? report.getSalaryPortionList() 
					: report.getSalaryPreliminaryMonthList();
			
			// set datasource
			designer.setDataSource("header_" + sheetNumber, header);
			designer.setDataSource("list_" + sheetNumber, rpData);
			
			// set color for row
			BankTranferReportUtil.rowColor(workbook, rpData.size(), sheetNumber);
			
			PageSetup pageSetup = designer.getWorkbook().getWorksheets().get(sheetNumber).getPageSetup();
			pageSetup.setHeader(0, report.getHeader().getCompanyName());
			pageSetup.setHeader(2, "&D &T");
			
			sheetNumber ++;
		}
		
		try {
			
			// process data binginds in template
			designer.getWorkbook().calculateFormula(true);
			designer.process(false);
			
			// save as PDF file
			PdfSaveOptions option = new PdfSaveOptions(SaveFormat.PDF);
			option.setAllColumnsInOnePagePerSheet(true);
			
			String fileName = BankTranferReportUtil.getFileName(REPORT_FILE_NAME);
			designer.getWorkbook().save(this.createNewFile(fileGeneratorContext, fileName), option);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return true;		
	}
	
}
