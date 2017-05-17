package nts.uk.pr.file.infra.banktransfer;

import java.util.List;
import java.util.stream.Collectors;

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
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class BankTransferReportAGenerator extends AsposeCellsReportGenerator implements BankTransferRpAGenerator {

	/** The Constant TEMPLATE_FILE. */
	private static final String TEMPLATE_FILE = "report/qpp014a.xlsx";
	/** The Constant REPORT_FILE_NAME. */
	protected static final String REPORT_FILE_NAME = "テストQPP014_{0}.pdf";

	@Override
	public void generator(FileGeneratorContext fileGeneratorContext, BankTransferAReport report) {
		try {
			AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE);
			List<BankTransferARpData> rpData = report.getData();

			if (!addSheet(fileGeneratorContext, reportContext, report)) {
				// set datasource
				reportContext.setDataSource("header", report.getHeader());
				reportContext.setDataSource("list", rpData);
		
				PageSetup pageSetup = reportContext.getWorkbook().getWorksheets().get(0).getPageSetup();
				pageSetup.setHeader(0, report.getHeader().getCompanyName());
				pageSetup.setHeader(2, "&D &T");
				
				// process data binginds in template
				reportContext.getWorkbook().calculateFormula(true);
				reportContext.getDesigner().process(false);
				
				// set color for row
				BankTranferReportUtil.rowColor(reportContext.getWorkbook(), rpData.size(), 0);
				
				// save as PDF file
				PdfSaveOptions option = new PdfSaveOptions(SaveFormat.PDF);
				option.setAllColumnsInOnePagePerSheet(true);
	
				reportContext.getWorkbook().save(this.createNewFile(fileGeneratorContext, BankTranferReportUtil.getFileName(REPORT_FILE_NAME)), option);
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
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
			// get data
			List<BankTransferARpData> rpData = report.getData().stream()
					.filter(x->item.value == x.getSparePayAtr())
					.collect(Collectors.toList());
			
			workbook = BankTranferReportUtil.addWorksheet(workbook, mainWorkSheet);
			
			Worksheet worksheet = workbook.getWorksheets().get(sheetNumber);
			worksheet.replace("header", String.valueOf("header_" + sheetNumber));
			worksheet.replace("list", String.valueOf("list_" + sheetNumber));
			 
			// set datasource
			designer.setDataSource("header_" + sheetNumber, report.getHeader());
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
			
			designer.getWorkbook().save(this.createNewFile(fileGeneratorContext, BankTranferReportUtil.getFileName(REPORT_FILE_NAME)), option);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return true;		
	}
	
}
