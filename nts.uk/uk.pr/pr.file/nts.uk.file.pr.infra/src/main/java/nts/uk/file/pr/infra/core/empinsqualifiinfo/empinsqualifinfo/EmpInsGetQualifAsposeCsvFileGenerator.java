package nts.uk.file.pr.infra.core.empinsqualifiinfo.empinsqualifinfo;

import com.aspose.cells.*;
import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo.EmpInsGetQualifReport;
import nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo.EmpInsGetQualifRptCsvFileGenerator;
import nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo.ExportDataCsv;
import nts.uk.shr.com.time.japanese.JapaneseDate;
import nts.uk.shr.com.time.japanese.JapaneseEraName;
import nts.uk.shr.com.time.japanese.JapaneseErasAdapter;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Stateless
public class EmpInsGetQualifAsposeCsvFileGenerator extends AsposeCellsReportGenerator implements EmpInsGetQualifRptCsvFileGenerator{

    private static final String REPORT_ID = "CSV_GENERATOR";

    private static final String FILE_NAME = "10101-shutoku.csv";

    @Inject
    private JapaneseErasAdapter adapter;

    // row 1: A1_1 - A1_6
    private static final List<String> ROW_1_HEADERS = Arrays.asList(
            "都市区符号", "事業所記号", "ＦＤ通番", "作成年月日", "代表届書コード", "連記式項目バージョン"
    );

    // row 2
    private static final String A1_11 = "22223";
    private static final String A1_12 = "05";

    // row 3
    private static final String A1_13 = "[kanri]";

    // row 4
    private static final String A1_14 = "社会保険労務士氏名";
    private static final String A1_15 = "事業所情報数";

    // row 5
    private static final String A1_16 = "";
    private static final String A1_17 = "001";

    // row 6: A1_18 - A1_29
    private static final List<String> ROW_6_HEADERS = Arrays.asList(
            "都市区符号",          "事業所記号",                        "事業所番号",                        "親番号（郵便番号）",
            "子番号（郵便番号）",  "事業所所在地",                       "事業所名称",                       "事業主氏名",
            "電話番号",           "雇用保険適用事業所番号（安定所番号）", "雇用保険適用事業所番号（一連番号）", "雇用保険適用事業所番号（CD）"
    );

    // row 8
    private static final String A1_42 = "[data]";

    // row 9: A1_43 - A1_103
    private static final List<String> ROW_9_HEADERS = Arrays.asList(
            "帳票種別",                        "安定所番号",                  "個人番号",                 "被保険者番号4桁",                "被保険者番号6桁",
            "被保険者番号CD",                  "取得区分",                    "被保険者氏名",             "被保険者氏名フリガナ（カタカナ）", "変更後の氏名",
            "変更後の氏名フリガナ（カタカナ）", "性別",                        "生年月日（元号）",          "生年月日（年）",                  "生年月日（月）",
            "生年月日（日）",                  "事業所番号（安定所番号）",     "事業所番号（一連番号）",     "事業所番号（CD）",                "資格取得年月日（元号）",
            "資格取得年月日（年）",            "資格取得年月日（月）",         "資格取得年月日（日）",       "被保険者となったことの原因",       "賃金支払形態",
            "賃金（賃金月額）",                "雇用形態",                    "職種",                     "就職経路",                        "取得時被保険者種類",
            "番号複数取得チェック不要",        "1週間の所定労働時間　（時間）", "1週間の所定労働時間　（分", "契約期間の定め",                   "契約期間開始（元号）",
            "契約期間開始（年）",              "契約期間開始（月）",           "契約期間開始（日）",       "契約期間終了（元号）",              "契約期間終了（年）",
            "契約期間終了（月）",              "契約期間終了（日）",           "契約更新条項の有無",       "事業所名",                         "被保険者氏名（ローマ字）",
            "国籍・地域",                     "国籍地域コード",               "在留資格",                 "在留資格コード",                   "在留期間（年）",
            "在留期間（月）",                 "在留期間（日）",               "資格外活動許可の有無",      "派遣・請負就労区分",               "備考",
            "あて先",                         "備考欄（備考）",               "確認通知年月日（元号）",   "確認通知年月日（年）",              "確認通知年月日（月）",
            "確認通知年月日（日）"
    );

    // row 4
    private static final String A1_104 = "10101";

    @Override
    public void generate(FileGeneratorContext fileContext, ExportDataCsv data) {
        try (val reportContext = this.createEmptyContext(REPORT_ID)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            Worksheet worksheet = worksheets.get(0);
            int row = 0;
            this.fillFixedRows(worksheet, row, data);
            this.fillDataRows(worksheet, row, data);
            reportContext.getDesigner().setWorkbook(workbook);
            reportContext.processDesigner();
            this.saveAsCSV(this.createNewFile(fileContext, FILE_NAME), workbook);
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

    private void fillFixedRows(Worksheet worksheet, int row, ExportDataCsv data) {
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

            if (c == 2){
                value = data.getReportTxtSetting().getFdNumber().v().toString();
            } else if (c == 3){
                //JapaneseDate dateJp = toJapaneseDate(GeneralDate.fromString(data.get().substring(0, 10), "yyyy-MM-dd"));
                value = "test";
            }else if (c == 4) {
                value = A1_11;
            }else if (c == 5) {
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

            if (c == 0) {

            } else if (c == 1) {

            }
            cells.get(row, c).setValue(value);
        }
        row++;

        // row 8
        cells.get(row, 0).setValue(A1_42);
        row++;
    }

    private void fillDataRows(Worksheet worksheet, int row, ExportDataCsv data) {
        Cells cells = worksheet.getCells();
        // row 9
        for (int c = 0; c < ROW_9_HEADERS.size(); c++) {
            String header = ROW_9_HEADERS.get(c);
            cells.get(row, c).setValue(header);
        }
        row++;

    }

    private JapaneseDate toJapaneseDate(GeneralDate date) {
        Optional<JapaneseEraName> era = this.adapter.getAllEras().eraOf(date);
        return new JapaneseDate(date, era.get());
    }
}
