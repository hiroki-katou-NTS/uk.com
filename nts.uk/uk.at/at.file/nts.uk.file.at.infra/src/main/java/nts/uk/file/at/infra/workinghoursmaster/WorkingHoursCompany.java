package nts.uk.file.at.infra.workinghoursmaster;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.aspose.cells.Cells;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.file.at.app.export.workinghours.CompanyTimeWorkExport;
import nts.uk.file.at.app.export.workinghours.YearsInput;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * 
 * @author chungnt
 *
 */
@Stateless
public class WorkingHoursCompany extends AsposeCellsReportGenerator implements CompanyTimeWorkExport {

	@Inject
	private CompanyRepository companyRepository;

	private static final String TEMPLATE_FILE = "report/KMP004.xlsx";
	private static final String REPORT_FILE_EXTENSION = ".xlsx";
	
	private final int ROW_TITLE = 9;
	
	private final int COLUMN_R7_3 = 0;
	private final int COLUMN_R7_4 = 1;
	private final int COLUMN_R7_5 = 2;
	private final int COLUMN_R7_6 = 3;
	private final int COLUMN_R7_7 = 4;
	private final int COLUMN_R7_8 = 5;
	private final int COLUMN_R7_9 = 6;
	private final int COLUMN_R7_10 = 7;
	private final int COLUMN_R7_11 = 8;
	private final int COLUMN_R7_12 = 9;
	private final int COLUMN_R7_13 = 10;
	private final int COLUMN_R7_14 = 11;
	private final int COLUMN_R7_15 = 12;
	private final int COLUMN_R7_16 = 13;
	private final int COLUMN_R7_17 = 14;
	private final int COLUMN_R7_18 = 15;
	private final int COLUMN_R7_19 = 16;
	private final int COLUMN_R7_20 = 17;
	private final int COLUMN_R7_21 = 18;
	private final int COLUMN_R7_22 = 19;
	private final int COLUMN_R7_23 = 20;
	private final int COLUMN_R7_24 = 21;
	private final int COLUMN_R7_25 = 22;
	private final int COLUMN_R7_26 = 23;
	private final int COLUMN_R7_27 = 24;
	private final int COLUMN_R7_28 = 25;
	private final int COLUMN_R7_29 = 26;
	private final int COLUMN_R7_30 = 27;
	private final int COLUMN_R7_31 = 28;
	private final int COLUMN_R7_32 = 29;
	private final int COLUMN_R7_33 = 30;
	private final int COLUMN_R7_34 = 31;
	private final int COLUMN_R7_35 = 32;
	private final int COLUMN_R7_36 = 33;
	private final int COLUMN_R7_37 = 34;
	private final int COLUMN_R7_38 = 35;
	private final int COLUMN_R7_39 = 36;

	

	@Override
	public void export(FileGeneratorContext generatorContext, YearsInput input) {
		try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
			Workbook workbook = reportContext.getWorkbook();
			WorksheetCollection worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.get(0);

			this.printContent(worksheet);
			reportContext.getDesigner().setWorkbook(workbook);
			reportContext.processDesigner();
			reportContext.saveAsExcel(this.createNewFile(generatorContext,
					"KMK004_" + "法定労働時間マスタ" + "_"
							+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss", Locale.JAPAN))
							+ REPORT_FILE_EXTENSION));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private void printContent(Worksheet worksheet) throws Exception {
		Cells cells = worksheet.getCells();
		String companyId = AppContexts.user().companyId();
		String companyName = companyRepository.find(companyId).get().getCompanyName().v();
		
		cells.get(ROW_TITLE, COLUMN_R7_3).setValue(TextResource.localize("KMK004_372"));
		cells.get(ROW_TITLE, COLUMN_R7_4).setValue(TextResource.localize("KMK004_373"));
		cells.get(ROW_TITLE, COLUMN_R7_5).setValue(TextResource.localize("KMK004_374"));
		cells.get(ROW_TITLE, COLUMN_R7_6).setValue(TextResource.localize("KMK004_375"));
		cells.get(ROW_TITLE, COLUMN_R7_7).setValue(TextResource.localize("KMK004_376"));
		cells.get(ROW_TITLE, COLUMN_R7_8).setValue(TextResource.localize("KMK004_377"));
		cells.get(ROW_TITLE, COLUMN_R7_9).setValue(TextResource.localize("KMK004_378"));
		cells.get(ROW_TITLE, COLUMN_R7_10).setValue(TextResource.localize("KMK004_379"));
		cells.get(ROW_TITLE, COLUMN_R7_11).setValue(TextResource.localize("KMK004_380"));
		cells.get(ROW_TITLE, COLUMN_R7_12).setValue(TextResource.localize("KMK004_381"));
		cells.get(ROW_TITLE, COLUMN_R7_13).setValue(TextResource.localize("KMK004_382"));
		cells.get(ROW_TITLE, COLUMN_R7_14).setValue(TextResource.localize("KMK004_383"));
		cells.get(ROW_TITLE, COLUMN_R7_15).setValue(TextResource.localize("KMK004_384"));
		cells.get(ROW_TITLE, COLUMN_R7_16).setValue(TextResource.localize("KMK004_385"));
		cells.get(ROW_TITLE, COLUMN_R7_17).setValue(TextResource.localize("KMK004_386"));
		cells.get(ROW_TITLE, COLUMN_R7_18).setValue(TextResource.localize("KMK004_387"));
		cells.get(ROW_TITLE, COLUMN_R7_19).setValue(TextResource.localize("KMK004_388"));
		cells.get(ROW_TITLE, COLUMN_R7_20).setValue(TextResource.localize("KMK004_389"));
		cells.get(ROW_TITLE, COLUMN_R7_21).setValue(TextResource.localize("KMK004_390"));
		cells.get(ROW_TITLE, COLUMN_R7_22).setValue(TextResource.localize("KMK004_391"));
		cells.get(ROW_TITLE, COLUMN_R7_23).setValue(TextResource.localize("KMK004_392"));
		cells.get(ROW_TITLE, COLUMN_R7_24).setValue(TextResource.localize("KMK004_393"));
		cells.get(ROW_TITLE, COLUMN_R7_25).setValue(TextResource.localize("KMK004_394"));
		cells.get(ROW_TITLE, COLUMN_R7_26).setValue(TextResource.localize("KMK004_395"));
		cells.get(ROW_TITLE, COLUMN_R7_27).setValue(TextResource.localize("KMK004_396"));
		cells.get(ROW_TITLE, COLUMN_R7_28).setValue(TextResource.localize("KMK004_397"));
		cells.get(ROW_TITLE, COLUMN_R7_29).setValue(TextResource.localize("KMK004_375"));
		cells.get(ROW_TITLE, COLUMN_R7_30).setValue(TextResource.localize("KMK004_376"));
		cells.get(ROW_TITLE, COLUMN_R7_31).setValue(TextResource.localize("KMK004_398"));
		cells.get(ROW_TITLE, COLUMN_R7_32).setValue(TextResource.localize("KMK004_399"));
		cells.get(ROW_TITLE, COLUMN_R7_33).setValue(TextResource.localize("KMK004_400"));
		cells.get(ROW_TITLE, COLUMN_R7_34).setValue(TextResource.localize("KMK004_377"));
		cells.get(ROW_TITLE, COLUMN_R7_35).setValue(TextResource.localize("KMK004_378"));
		cells.get(ROW_TITLE, COLUMN_R7_36).setValue(TextResource.localize("KMK004_379"));
		cells.get(ROW_TITLE, COLUMN_R7_37).setValue(TextResource.localize("KMK004_380"));
		cells.get(ROW_TITLE, COLUMN_R7_38).setValue(TextResource.localize("KMK004_381"));
		cells.get(ROW_TITLE, COLUMN_R7_39).setValue(TextResource.localize("KMK004_382"));
		
		

//		cells.get(ROW_NAME_COMPANY, COLUMN_DATA).setValue(AppContexts.user().companyCode() + " " + companyName);
	}
}
