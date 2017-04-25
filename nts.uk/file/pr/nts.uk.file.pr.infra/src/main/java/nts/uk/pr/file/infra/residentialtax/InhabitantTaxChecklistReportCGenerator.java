package nts.uk.pr.file.infra.residentialtax;

import java.util.List;

import javax.ejb.Stateless;

import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.SaveFormat;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.residentialtax.InhabitantTaxChecklistCGenerator;
import nts.uk.file.pr.app.export.residentialtax.data.InhabitantTaxChecklistCReport;
import nts.uk.file.pr.app.export.residentialtax.data.InhabitantTaxChecklistCRpData;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class InhabitantTaxChecklistReportCGenerator extends AsposeCellsReportGenerator
		implements InhabitantTaxChecklistCGenerator {

	/** The Constant TEMPLATE_FILE. */
	private static final String TEMPLATE_FILE = "report/qpp011c.xlsx";
	/** The Constant REPORT_FILE_NAME. */
	protected static final String REPORT_FILE_NAME = "テストQPP011.pdf";

	@Override
	public void generate(FileGeneratorContext fileContext, InhabitantTaxChecklistCReport dataExport) {
		try {
			AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE);

			List<InhabitantTaxChecklistCRpData> reportData = dataExport.getData();

			// set datasource
			reportContext.setDataSource("header", dataExport.getHeader());
			reportContext.setDataSource("list", reportData);

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
