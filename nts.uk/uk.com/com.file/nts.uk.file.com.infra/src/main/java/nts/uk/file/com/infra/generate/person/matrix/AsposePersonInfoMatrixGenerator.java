package nts.uk.file.com.infra.generate.person.matrix;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.com.app.person.matrix.PersonInfoMatrixGenerator;
import nts.uk.file.com.app.person.matrix.datasource.PersonInfoMatrixDataSource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;
@Stateless
public class AsposePersonInfoMatrixGenerator extends AsposeCellsReportGenerator implements PersonInfoMatrixGenerator{

	private static final String REPORT_ID = "ReportSample";
	
	@Override
	public void generate(FileGeneratorContext generatorContext, PersonInfoMatrixDataSource dataSource) {
		val reportContext = this.createEmptyContext(REPORT_ID);
		reportContext.processDesigner();
		reportContext.saveAsExcel(this.createNewFile(generatorContext, "AAAAAAA.xlsx"));
	}

}
