package nts.uk.ctx.at.function.infra.repository.holidaysremaining.report;

import javax.ejb.Stateless;

import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.HolidaysRemainingReportGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class HolidaysRemainingReportGeneratorImp extends AsposeCellsReportGenerator
	implements HolidaysRemainingReportGenerator{

	private static final String TEMPLATE_FILE = "report/休暇残数管理票_テンプレート.xlsx";
	private static final String REPORT_FILE_NAME = "休暇残数管理票.xlsx";
	
	@Override
	public void generate(FileGeneratorContext generatorContext) {
		try (val reportContext = this.createContext(TEMPLATE_FILE)) {

			val designer = this.createContext(TEMPLATE_FILE);
			Workbook workbook = designer.getWorkbook();
			WorksheetCollection worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.get(0);
			// set up page prepare print
			// this.printPage(worksheet);
			// this.printWorkplace(worksheets, dataSource);

			designer.getDesigner().setWorkbook(workbook);
			designer.processDesigner();

			designer.saveAsExcel(this.createNewFile(generatorContext, this.getReportName(REPORT_FILE_NAME)));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}		
	}
}
