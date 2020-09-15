package nts.uk.file.at.infra.statement.stamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.ejb.Stateless;

import com.aspose.cells.Cells;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.at.app.export.statement.stamp.StampEdittingExport;
import nts.uk.file.at.app.export.statement.stamp.StampEdittingExportDatasource;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * 
 * @author chungnt
 *
 */
@Stateless
public class AsposeStampEdittingExport extends AsposeCellsReportGenerator implements StampEdittingExport {

	private static final String TEMPLATE_FILE = "report/KMP001.xlsx";
	private static final String REPORT_FILE_EXTENSION = ".xlsx";

	@Override
	public void export(FileGeneratorContext generatorContext, StampEdittingExportDatasource input) {
		try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
			Workbook workbook = reportContext.getWorkbook();
			WorksheetCollection worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.get("KMP001");

			this.printContent(worksheet, input);
			reportContext.getDesigner().setWorkbook(workbook);
			reportContext.processDesigner();
			reportContext.saveAsExcel(this.createNewFile(generatorContext,
					"KMP001_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss", Locale.JAPAN))
							+ REPORT_FILE_EXTENSION));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void printContent(Worksheet worksheet, StampEdittingExportDatasource dataSource) throws Exception {
		Cells cells = worksheet.getCells();

		switch (dataSource.getStampMethod()) {
		case 1:
			cells.get(13, 1).setValue(TextResource.localize("KMP001_42"));
			break;
		case 2:
			cells.get(13, 1).setValue(TextResource.localize("KMP001_43"));
			break;
		case 3:
			cells.get(13, 1).setValue(TextResource.localize("KMP001_44"));
			break;
		case 4:
			cells.get(13, 1).setValue(TextResource.localize("KMP001_45"));
			break;
		default:
			break;
		}

		cells.get(12, 1).setValue(dataSource.getDigitsNumber());
	}
}
