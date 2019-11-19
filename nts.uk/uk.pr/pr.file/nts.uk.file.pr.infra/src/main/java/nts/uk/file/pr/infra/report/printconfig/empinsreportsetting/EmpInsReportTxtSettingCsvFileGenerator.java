package nts.uk.file.pr.infra.report.printconfig.empinsreportsetting;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import com.aspose.cells.Cells;
import com.aspose.cells.Encoding;
import com.aspose.cells.SaveFormat;
import com.aspose.cells.TxtSaveOptions;
import com.aspose.cells.TxtValueQuoteType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.ExportDataCsv;
import nts.uk.file.pr.app.report.printconfig.empinsreportsetting.EmpInsReportTxtSettingCsvGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

public class EmpInsReportTxtSettingCsvFileGenerator extends AsposeCellsReportGenerator
		implements EmpInsReportTxtSettingCsvGenerator {

	private static final String REPORT_ID = "CSV_GENERATOR";

	private static final String FILE_NAME = "10191-soshitsu.csv";

	// row 1
	private static final List<String> ROW_1_HEADERS = Arrays.asList("都市区符号", "事業所記号", "ＦＤ通番", "作成年月日", "代表届書コード",
			"連記式項目バージョン");

	// row 2
	private static final String A1_11 = "22223";
	private static final String A1_12 = "03";

	// row 3
	private static final String A1_13 = "[kanri]";

	// row 4
	private static final String A1_14 = "社会保険労務士氏名";
	private static final String A1_15 = "事業所情報数";

	// row 5
	private static final String A1_16 = "";
	private static final String A1_17 = "001";

	// row 6
	private static final List<String> ROW_6_HEADERS = Arrays.asList("都市区符号", "事業所記号", "事業所番号", "親番号（郵便番号）", "子番号（郵便番号）",
			"事業所所在地", "事業所名称", "事業主氏名", "電話番号", "雇用保険適用事業所番号（安定所番号）", "雇用保険適用事業所番号（一連番号）", "雇用保険適用事業所番号（CD）");

	// row 8
	private static final String A1_42 = "[data]";

	@Override
	public void generate(FileGeneratorContext generatorContext, ExportDataCsv dataSource) {
		try (val reportContext = this.createEmptyContext(REPORT_ID)) {
			Workbook workbook = reportContext.getWorkbook();
			WorksheetCollection worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.get(0);
			int row = 0;
			this.fillFixedRows(worksheet, row);
			reportContext.getDesigner().setWorkbook(workbook);
			reportContext.processDesigner();
			this.saveAsCSV(this.createNewFile(generatorContext, FILE_NAME), workbook);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void saveAsCSV(OutputStream outputStream, Workbook workbook) {
		try {
			TxtSaveOptions opts = new TxtSaveOptions(SaveFormat.CSV);
			opts.setSeparator(',');
			opts.setEncoding(Encoding.getUTF8());
			opts.setQuoteType(TxtValueQuoteType.NEVER);
			workbook.save(outputStream, opts);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void fillFixedRows(Worksheet worksheet, int row) {
		Cells cells = worksheet.getCells();
		// row 1
		for (int c = 0; c < ROW_1_HEADERS.size(); c++) {
			String header = ROW_1_HEADERS.get(c);
			cells.get(row, c).setValue(header);
		}
		row++;

		// row 2
		for (int c = 0; c < ROW_1_HEADERS.size(); c++) {
			String value = "test";
			if (c == 0) {
				value = value + c;
			}
			if (c == 1) {
				value = value + c;
			}
			if (c == 2) {
				value = value + c;
			}
			if (c == 3) {
				value = value + c;
			}
			if (c == 4) {
				value = A1_11;
			}
			if (c == 5) {
				value = A1_12;
			}
			cells.get(row, c).setValue(value);
		}
		row++;

		// row 3
		cells.get(row, 0).setValue(A1_13);
		row++;

		// row 4
		cells.get(row, 0).setValue(A1_14);
		cells.get(row, 1).setValue(A1_15);
		row++;

		// row 5
		cells.get(row, 0).setValue(A1_16);
		cells.get(row, 1).setValue(A1_17);
		row++;

		// row 6
		for (int c = 0; c < ROW_6_HEADERS.size(); c++) {
			String header = ROW_6_HEADERS.get(c);
			cells.get(row, c).setValue(header);
		}
		row++;
		
		// row 7
		for (int c = 0; c < ROW_6_HEADERS.size(); c++) {
			String value = "test";
			cells.get(row, c).setValue(value);
		}
		row++;
		
		// row 8
		cells.get(row, 0).setValue(A1_42);
		row++;
	}

}
