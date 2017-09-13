package nts.uk.file.com.infra.generate;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.com.app.EmployeeUnregisterOutputDataSoure;
import nts.uk.file.com.app.EmployeeUnregisterOutputGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposeEmployeeUnregisterOutputReportGenerator extends AsposeCellsReportGenerator
		implements EmployeeUnregisterOutputGenerator {

	private static final String TEMPLATE_FILE = "report/SampleReport.xlsx";

	private static final String REPORT_FILE_NAME = "サンプル帳票.xlsx";

	private static final String REPORT_ID = "ReportSample";

	@Override
	public void generate(FileGeneratorContext generatorContext, EmployeeUnregisterOutputDataSoure dataSource) {
		try (val reportContext = this.createContext(TEMPLATE_FILE, REPORT_ID)) {

			// set data source named "item"
			reportContext.setDataSource("item", dataSource.getEmployeeUnregisterOutputLst());

			// process data binginds in template
			reportContext.processDesigner();

			// save as PDF file
			reportContext.saveAsPdf(this.createNewFile(generatorContext, REPORT_FILE_NAME));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
