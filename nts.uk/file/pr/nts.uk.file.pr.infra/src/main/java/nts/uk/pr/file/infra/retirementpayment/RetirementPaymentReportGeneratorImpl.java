/**
 * 
 */
package nts.uk.pr.file.infra.retirementpayment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;
import com.aspose.cells.WorkbookDesigner;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.retirementpayment.RetirementPaymentReportGenerator;
import nts.uk.file.pr.app.export.retirementpayment.data.RetirePayItemDto;
import nts.uk.file.pr.app.export.retirementpayment.data.RetirementPaymentReportData;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * @author hungnm
 *
 */
@Stateless
public class RetirementPaymentReportGeneratorImpl extends AsposeCellsReportGenerator
		implements RetirementPaymentReportGenerator {
	/** The Constant TEMPLATE_FILE. */
	private static final String TEMPLATE_FILE = "report/qrm009.xlsx";
	/** The Constant REPORT_FILE_NAME. */
	protected static final String REPORT_FILE_NAME = "退職金明細書_";

	@Override
	public void generate(FileGeneratorContext generatorContext, List<RetirementPaymentReportData> dataSource,
			List<RetirePayItemDto> lstRetirePayItemDto) {
		try {
			AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE);
			Worksheet mainWorkSheet = reportContext.getWorkbook().getWorksheets().get(0);

			Workbook workbook = new Workbook();
			workbook.getWorksheets().removeAt(0);
			WorkbookDesigner designer = new WorkbookDesigner();
			designer.setWorkbook(workbook);
			// get current date
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date date = new Date();
			String currentDate = dateFormat.format(date).toString();
			// filter pay item name
			String printNameT1001 = lstRetirePayItemDto.stream().filter(item -> item.getItemCode() == "T1001")
					.findFirst().get().getItemName();
			String printNameT2001 = lstRetirePayItemDto.stream().filter(item -> item.getItemCode() == "T2001")
					.findFirst().get().getItemName();
			String printNameT2002 = lstRetirePayItemDto.stream().filter(item -> item.getItemCode() == "T2002")
					.findFirst().get().getItemName();
			String printNameT2003 = lstRetirePayItemDto.stream().filter(item -> item.getItemCode() == "T2003")
					.findFirst().get().getItemName();
			String printNameT2004 = lstRetirePayItemDto.stream().filter(item -> item.getItemCode() == "T2004")
					.findFirst().get().getItemName();
			String printNameT2005 = lstRetirePayItemDto.stream().filter(item -> item.getItemCode() == "T2005")
					.findFirst().get().getItemName();
			String printNameT2006 = lstRetirePayItemDto.stream().filter(item -> item.getItemCode() == "T2006")
					.findFirst().get().getItemName();
			String printNameT2007 = lstRetirePayItemDto.stream().filter(item -> item.getItemCode() == "T2007")
					.findFirst().get().getItemName();
			String printNameT3001 = lstRetirePayItemDto.stream().filter(item -> item.getItemCode() == "T3001")
					.findFirst().get().getItemName();
			int sheetNumber = 0;
			for (RetirementPaymentReportData retirementPaymentReportData : dataSource) {
				workbook = addWorksheet(workbook, mainWorkSheet);

				Worksheet worksheet = workbook.getWorksheets().get(sheetNumber);
				worksheet.replace("data", String.valueOf("data" + sheetNumber));
				designer.setDataSource("data" + sheetNumber, retirementPaymentReportData);
				designer.setDataSource("currentDate", currentDate);
				designer.setDataSource("CTR_002", "12年7ヶ 月");
				designer.setDataSource("retirePayItemT1001", printNameT1001);
				designer.setDataSource("retirePayItemT2001", printNameT2001);
				designer.setDataSource("retirePayItemT2002", printNameT2002);
				designer.setDataSource("retirePayItemT2003", printNameT2003);
				designer.setDataSource("retirePayItemT2004", printNameT2004);
				designer.setDataSource("retirePayItemT2005", printNameT2005);
				designer.setDataSource("retirePayItemT2006", printNameT2006);
				designer.setDataSource("retirePayItemT2007", printNameT2007);
				designer.setDataSource("retirePayItemT3001", printNameT3001);
				sheetNumber++;
			}

			// save as PDF file
			PdfSaveOptions option = new PdfSaveOptions(SaveFormat.PDF);
			option.setAllColumnsInOnePagePerSheet(true);

			designer.getWorkbook().save(this.createNewFile(generatorContext, REPORT_FILE_NAME.concat(currentDate)),
					option);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Workbook addWorksheet(Workbook workbook, Worksheet worksheet) {
		// Workbookに宛名のsheetとテンプレートのsheetを追加
		try {
			WorksheetCollection worksheets = workbook.getWorksheets();

			int mainSheet = worksheets.add();
			worksheets.get(mainSheet).copy(worksheet);

		} catch (Exception e) {
			throw new RuntimeException(e);
			// LOGGER.error("Failed create workbook. " + e.getMessage());
		}

		return workbook;
	}

}
