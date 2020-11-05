package nts.uk.file.at.infra.employmentinfoterminal.infoterminal;

import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.aspose.cells.Cells;
import com.aspose.cells.Worksheet;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.layer.infra.file.export.WorkingFile;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSetting;
import nts.uk.ctx.at.function.dom.alarm.export.AlarmExportDto;
import nts.uk.file.at.app.export.employmentinfoterminal.infoterminal.EmpInfoTerminalExport;
import nts.uk.file.at.app.export.employmentinfoterminal.infoterminal.EmpInfoTerminalExportDataSource;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposeEmpInfoTerminalExport extends AsposeCellsReportGenerator implements EmpInfoTerminalExport{
	
	private static final String TEMPLATE_FILE = "report/KNR001.xlsx";
	private static final String PGID ="KNR001";
	private static final String PG ="就業情報端末の登録";
	private static final String SHEET_NAME ="マスタリスト";
	private static final String COMPANY_ERROR = "Company is not found!!!!";
	
	private final int ROW_COMPANY = 0;
	private final int ROW_TYPE = 1;
	private final int ROW_DATE_TIME = 2;
	private final int ROW_SHEET_NAME = 3;
	private final int COLUMN_DATA = 1;
	private final int COLUMN_MAC_ADDRESS = 3;
//	private final int COLUMN_IP_ADDRESS = 4;
	private final int PADDING_ROWS = 10;
	
	@Inject
	private CompanyAdapter companyAdapter;

	@Override
	public void export(FileGeneratorContext generatorContext, List<EmpInfoTerminalExportDataSource> dataSource) {
		try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
			Worksheet worksheet = reportContext.getWorkbook().getWorksheets().get(0);
			
			setHeaderAndHeaderColumn(worksheet, reportContext);
			
			// set data source named "item"
			reportContext.setDataSource("item", dataSource);
			// process data binginds in template
			reportContext.processDesigner();
			
			mergeMacAndIp(worksheet, dataSource);

			// save as Excel file
			GeneralDateTime dateNow = GeneralDateTime.now();
			String dateTime = dateNow.toString("yyyyMMddHHmmss");
			String fileName = PGID+PG+"_" + dateTime + ".xlsx";
			OutputStream outputStream = this.createNewFile(generatorContext, fileName);
			reportContext.saveAsExcel(outputStream);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private void setHeaderAndHeaderColumn(Worksheet worksheet, AsposeCellsReportContext reportContext) {
		String companyCode = companyAdapter.getCurrentCompany().orElseThrow(() -> new RuntimeException(COMPANY_ERROR))
				.getCompanyCode();
		String companyName = companyAdapter.getCurrentCompany().orElseThrow(() -> new RuntimeException(COMPANY_ERROR))
				.getCompanyName();
		
		worksheet.setName(SHEET_NAME);
		
		Cells cells = worksheet.getCells();
		cells.get(ROW_COMPANY, COLUMN_DATA).setValue(companyCode + " " + companyName);
		cells.get(ROW_TYPE, COLUMN_DATA).setValue(PGID + PG);
		cells.get(ROW_DATE_TIME, COLUMN_DATA).setValue(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
		cells.get(ROW_SHEET_NAME, COLUMN_DATA).setValue(SHEET_NAME);
	}

	private void mergeMacAndIp(Worksheet worksheet, List<EmpInfoTerminalExportDataSource> dataSource) {
		Cells cells = worksheet.getCells();
		
		for (int i=0; i<dataSource.size(); i++) {
			EmpInfoTerminalExportDataSource data = dataSource.get(i);
			if(data.getIpAddress()==null || data.getIpAddress().isEmpty()) {
				cells.merge(i+PADDING_ROWS, COLUMN_MAC_ADDRESS, 1, 2);
			}
		}
	}
}
