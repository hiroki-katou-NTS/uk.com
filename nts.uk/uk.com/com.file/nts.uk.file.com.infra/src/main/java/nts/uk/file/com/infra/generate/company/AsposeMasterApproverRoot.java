package nts.uk.file.com.infra.generate.company;

import javax.ejb.Stateless;

import com.aspose.cells.PageSetup;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.com.app.company.approval.MasterApproverRootOutputDataSource;
import nts.uk.file.com.app.company.approval.MasterApproverRootOutputGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposeMasterApproverRoot extends AsposeCellsReportGenerator implements MasterApproverRootOutputGenerator {
	private static final String TEMPLATE_FILE = "report/マスタリストのEXCEL出力.xlsx";

	private static final String REPORT_FILE_NAME = "マスタリストのEXCEL出力.xlsx";

	private static final int[] COLUMN_INDEX = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };

	@Override
	public void generate(FileGeneratorContext generatorContext, MasterApproverRootOutputDataSource dataSource) {
		try (val reportContext = this.createContext(TEMPLATE_FILE)) {

			val designer = this.createContext(TEMPLATE_FILE);
			Workbook workbook = designer.getWorkbook();
			WorksheetCollection worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.get(0);
			// set up page prepare print
			this.printPage(worksheet);

			designer.getDesigner().setWorkbook(workbook);
			designer.processDesigner();

			designer.saveAsExcel(this.createNewFile(generatorContext, this.getReportName(REPORT_FILE_NAME)));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * PRINT PAGE
	 * 
	 * @param worksheet
	 * @param lstDeparmentInf
	 */
	private void printPage(Worksheet worksheet) {
		// Set print page
		PageSetup pageSetup = worksheet.getPageSetup();
		pageSetup.setFirstPageNumber(1);
		pageSetup.setPrintArea("A1:N");
	}

}
