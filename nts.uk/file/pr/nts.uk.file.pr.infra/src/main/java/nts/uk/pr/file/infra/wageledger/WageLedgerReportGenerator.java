package nts.uk.pr.file.infra.wageledger;

import java.io.OutputStream;

import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGenerator;
import nts.arc.layer.infra.file.export.FileGeneratorContext;

public class WageLedgerReportGenerator  extends FileGenerator{

	@Override
	protected void generate(FileGeneratorContext context) {
		OutputStream os = this.createNewFile(context, "test.xlsx");
		// Sample generate report file.
		try {
			val workbook = new Workbook();
			workbook.getWorksheets().get(0).getCells().get("A1").setValue("Hello Aspose!");
			workbook.save(os, SaveFormat.XLSX);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

}
