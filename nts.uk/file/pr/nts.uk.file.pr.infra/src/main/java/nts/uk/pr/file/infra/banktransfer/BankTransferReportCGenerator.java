package nts.uk.pr.file.infra.banktransfer;

import java.util.List;

import javax.ejb.Stateless;

import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.SaveFormat;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.banktransfer.BankTransferRpCGenerator;
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferCReport;
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferCRpData;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class BankTransferReportCGenerator extends AsposeCellsReportGenerator implements BankTransferRpCGenerator {

	/** The Constant TEMPLATE_FILE. */
	private static final String TEMPLATE_FILE = "report/qpp014c.xlsx";
	/** The Constant REPORT_FILE_NAME. */
	protected static final String REPORT_FILE_NAME = "テストQPP014C.pdf";

	@Override
	public void generate(FileGeneratorContext fileContext, BankTransferCReport reportData) {
		try {
			AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE);
			List<BankTransferCRpData> rpData = reportData.getData();

			// set datasource
			reportContext.setDataSource("header", reportData.getHeader());
			reportContext.setDataSource("list", rpData);

			// process data binginds in template
			reportContext.getWorkbook().calculateFormula(true);
			reportContext.getDesigner().process(false);

			// save as PDF file
			PdfSaveOptions option = new PdfSaveOptions(SaveFormat.PDF);
			option.setAllColumnsInOnePagePerSheet(true);

			reportContext.getWorkbook().save(this.createNewFile(fileContext, REPORT_FILE_NAME), option);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
