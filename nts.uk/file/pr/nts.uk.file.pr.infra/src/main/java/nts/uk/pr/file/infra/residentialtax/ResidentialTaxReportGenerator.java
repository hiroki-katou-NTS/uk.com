package nts.uk.pr.file.infra.residentialtax;

import java.util.concurrent.TimeUnit;

import javax.ejb.Stateless;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.residentialtax.ResidentialTaxGenerator;
import nts.uk.file.pr.app.export.residentialtax.data.ResidentTaxReportData;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class ResidentialTaxReportGenerator extends AsposeCellsReportGenerator implements ResidentialTaxGenerator {
	/** The Constant TEMPLATE_FILE. */
	private static final String TEMPLATE_FILE = "report/qpp011a.xlsx";
	/** The Constant REPORT_FILE_NAME. */
	protected static final String REPORT_FILE_NAME = "テストQPP011.pdf";
	
	@Override
	public void generate(FileGeneratorContext fileContext, ResidentTaxReportData reportData) {
		try {
			AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE);
			
			reportContext.getDesigner().setDataSource("DBD_001", "Dfsdfsdfsdf");
			reportContext.getDesigner().setDataSource("DBD_002", "Dfsdfsdfsdf");
			reportContext.getDesigner().setDataSource("DBD_002", "Dfsdfsdfsdf");
			reportContext.getDesigner().setDataSource("DBD_002", "Dfsdfsdfsdf");
			reportContext.getDesigner().setDataSource("DBD_002", "Dfsdfsdfsdf");
			reportContext.getDesigner().setDataSource("DBD_002", "Dfsdfsdfsdf");
			reportContext.getDesigner().setDataSource("DBD_002", "Dfsdfsdfsdf");
			reportContext.getDesigner().setDataSource("DBD_002", "Dfsdfsdfsdf");
			reportContext.getDesigner().setDataSource("DBD_002", "Dfsdfsdfsdf");
			reportContext.getDesigner().setDataSource("DBD_002", "Dfsdfsdfsdf");
			reportContext.getDesigner().setDataSource("DBD_002", "Dfsdfsdfsdf");
			reportContext.getDesigner().setDataSource("DBD_002", "Dfsdfsdfsdf");
			
			// process data binginds in template
			reportContext.getDesigner().getWorkbook().calculateFormula(true);
			reportContext.getDesigner().process(false);

			// save as PDF file
			reportContext.saveAsPdf(this.createNewFile(fileContext, REPORT_FILE_NAME));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
