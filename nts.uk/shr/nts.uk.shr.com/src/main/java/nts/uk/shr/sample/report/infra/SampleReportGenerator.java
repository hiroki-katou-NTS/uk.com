package nts.uk.shr.sample.report.infra;

import java.io.IOException;

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
		try (val templateFile = this.getClass().getClassLoader().getResourceAsStream("report/SampleReport.xlsx")) {
			
			Workbook workBook = new Workbook(templateFile);
			
			val items = this.repository.getItems();
			
			WorkbookDesigner designer = new WorkbookDesigner(workBook);
			designer.setDataSource("item", items);
			
			designer.process();
			
			workBook.save(this.createNewFile(context, "サンプル帳票.pdf"), SaveFormat.PDF);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
