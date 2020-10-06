package nts.uk.file.at.infra.statement.stamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.aspose.cells.Cells;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.file.at.app.export.statement.stamp.StampEdittingExport;
import nts.uk.file.at.app.export.statement.stamp.StampEdittingExportDatasource;
import nts.uk.shr.com.context.AppContexts;
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

	@Inject
	private CompanyRepository companyRepository;
	
	private static final String TEMPLATE_FILE = "report/KMP001.xlsx";
	private static final String REPORT_FILE_EXTENSION = ".xlsx";

	@Override
	public void export(FileGeneratorContext generatorContext, StampEdittingExportDatasource input) {
		try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
			Workbook workbook = reportContext.getWorkbook();
			WorksheetCollection worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.get(0);

			this.printContent(worksheet, input);
			reportContext.getDesigner().setWorkbook(workbook);
			reportContext.processDesigner();
			reportContext.saveAsExcel(this.createNewFile(generatorContext,
					"KMP001_" + "カードNOの登録" + "_" +  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss", Locale.JAPAN))
							+ REPORT_FILE_EXTENSION));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void printContent(Worksheet worksheet, StampEdittingExportDatasource dataSource) throws Exception {
		Cells cells = worksheet.getCells();
		String companyId = AppContexts.user().companyId();
		String companyName = companyRepository.find(companyId).get().getCompanyName().v();

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

		cells.get(2, 1).setValue(AppContexts.user().companyCode() + " " + companyName);
		cells.get(12, 1).setValue(dataSource.getDigitsNumber());
		cells.get(4, 1).setValue(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss a")));
	}
}
