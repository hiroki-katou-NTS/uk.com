package nts.uk.pr.file.infra.company;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;
import com.aspose.cells.WorkbookDesigner;

import nts.arc.layer.infra.file.export.FileGenerator;
import nts.arc.layer.infra.file.export.FileGeneratorContext;


@Stateless
public class CompanyPrintFileGenerator extends FileGenerator {

	private final String fileName = "report/company.xlsx";
	
	@Override
	protected void generate(FileGeneratorContext context) {
		try {
			List<CompanyDto> companies = new ArrayList<>();
			OutputStream output = this.createNewFile(context, "company.xlsx");
			for (int i = 0; i < 1000; i++) {
				CompanyDto company = new CompanyDto();
				company.setCode("Code " + (i + 1));
				company.setName("Name " + (i + 1));
				company.setCount(i);
				companies.add(company);
			}
	        WorkbookDesigner designer = new WorkbookDesigner();
			Workbook workbook = null;
	        try {
	            workbook = new Workbook(Thread.currentThread()
	                    .getContextClassLoader().getResourceAsStream(fileName));
	        } catch (Exception e) {
	            throw new RuntimeException("Report template not found");
	        }

	        designer.setWorkbook(workbook);
	        String header = "Company Report";
	        designer.setDataSource("Header", header);
	        designer.setDataSource("Company", companies);
	        designer.process(true);
			designer.getWorkbook().save(output, SaveFormat.XLSX);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
