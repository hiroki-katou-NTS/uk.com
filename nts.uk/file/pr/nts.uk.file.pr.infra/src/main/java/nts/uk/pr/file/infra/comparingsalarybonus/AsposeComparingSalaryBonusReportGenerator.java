package nts.uk.pr.file.infra.comparingsalarybonus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.comparingsalarybonus.ComparingSalaryBonusGenerator;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.ComparingSalaryBonusReportData;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.DeparmentInf;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposeComparingSalaryBonusReportGenerator extends AsposeCellsReportGenerator
		implements ComparingSalaryBonusGenerator {
	/** The Constant TEMPLATE_FILE. */
	private static final String TEMPLATE_FILE = "report/ComparingSalaryBonusTemplate.xlsx";

	/** The Constant REPORT_FILE_NAME. */
	private static final String REPORT_FILE_NAME = "QPP008_";
	/** The Constant REPORT_FILE_NAME. */
	private static final String EXTENSION = ".pdf";
	/** The Constant DATE_TIME_FORMAT. */
	private static final String DATE_TIME_FORMAT = "yyyyMMddHHmmss";
	/** The Constant HEADER. */
	private static final String HEADER = "HEADER";
	
	/** The Constant HEADER TABLE. */
	private static final String HEADERTABLE = "HEADERTABLE";
	
	/** The Constant DEPARTMENT. */
	private static final String DEPARTMENT = "DEPARTMENT";
	
	/** The Constant EMPLOYEE. */
	private static final String EMPLOYEE = "EMPLOYEE";


	@Override
	public void generate(FileGeneratorContext fileContext, ComparingSalaryBonusReportData reportData, DeparmentInf depInf) {
		try (val reportContext = this.createContext(TEMPLATE_FILE)) {
			reportContext.setDataSource(HEADER, Arrays.asList(reportData.getHeaderData()));
			reportContext.processDesigner();
			reportContext.setDataSource(DEPARTMENT, Arrays.asList(depInf));
			reportContext.processDesigner();
//			reportContext.setDataSource(EMPLOYEE, Arrays.asList(reportData.getEmployeeInf()));
//			reportContext.processDesigner();
			DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
			Date date = new Date();
			String fileName = REPORT_FILE_NAME.concat(dateFormat.format(date).toString()).concat(EXTENSION);
			reportContext.saveAsPdf(this.createNewFile(fileContext, fileName));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
