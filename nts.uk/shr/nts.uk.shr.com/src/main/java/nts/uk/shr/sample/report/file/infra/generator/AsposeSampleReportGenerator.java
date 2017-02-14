package nts.uk.shr.sample.report.file.infra.generator;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;
import nts.uk.shr.sample.report.app.SampleReportDataSource;
import nts.uk.shr.sample.report.app.SampleReportGenerator;

@Stateless
public class AsposeSampleReportGenerator extends AsposeCellsReportGenerator implements SampleReportGenerator {
	
	private static final String TEMPLATE_FILE = "report/SampleReport.xlsx";
	
	private static final String REPORT_FILE_NAME = "サンプル帳票.pdf";

	@Override
	public void generate(FileGeneratorContext generatorContext, SampleReportDataSource dataSource) {
		
		try (val reportContext = this.createContext(TEMPLATE_FILE)) {
			
			// set data source named "item"
			reportContext.setDataSource("item", dataSource.getItems());
			
			// process data binginds in template
			reportContext.processDesigner();
			
			// save as PDF file
			reportContext.saveAsPdf(this.createNewFile(generatorContext, REPORT_FILE_NAME));
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
