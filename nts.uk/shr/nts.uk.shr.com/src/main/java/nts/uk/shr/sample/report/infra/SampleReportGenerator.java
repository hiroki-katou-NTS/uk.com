package nts.uk.shr.sample.report.infra;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;
import com.aspose.cells.WorkbookDesigner;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGenerator;
import nts.arc.layer.infra.file.export.FileGeneratorContext;

@Stateless
public class SampleReportGenerator extends FileGenerator {
	
	@Inject
	private SampleReportDataRepository repository;

	@Override
	protected void generate(FileGeneratorContext context) {
		
		// String parameter = context.getParameterAt(0); -> "this is parameter"
		
		try (val templateFile = this.getResourceAsStream("report/SampleReport.xlsx")) {
			
			val workBook = new Workbook(templateFile);
			
			val items = this.repository.getItems();
			
			val designer = new WorkbookDesigner(workBook);
			designer.setDataSource("item", items);
			
			designer.process();
			
			workBook.save(this.createNewFile(context, "サンプル帳票.pdf"), SaveFormat.PDF);
			
		} catch (Exception e) {
			// rethrow exceptions from Aspose
			throw new RuntimeException(e);
		}
	}

}
