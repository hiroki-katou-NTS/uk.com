package nts.uk.file.pr.infra.core.empinsqualifiinfo.empinsqualifinfo;

import com.aspose.cells.*;
import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.gul.text.KatakanaConverter;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInfor;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoEx;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.ForeignerResHistInfo;
import nts.uk.ctx.pr.core.dom.adapter.employee.employment.LaborContractHist;
import nts.uk.ctx.pr.core.dom.adapter.person.PersonExport;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.LaborInsuranceOffice;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.PublicEmploymentSecurityOffice;
import nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo.*;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.*;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.empinsofficeinfo.EmpInsOffice;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.AcquisitionAtr;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsGetInfo;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsHist;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsNumInfo;
import nts.uk.shr.com.time.japanese.JapaneseDate;
import nts.uk.shr.com.time.japanese.JapaneseEraName;
import nts.uk.shr.com.time.japanese.JapaneseEras;
import nts.uk.shr.com.time.japanese.JapaneseErasAdapter;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class EmpInsGetQualifAsposeCsvFileGenerator extends AsposeCellsReportGenerator implements EmpInsGetQualifRptCsvFileGenerator{

    private static final String REPORT_ID = "CSV_GENERATOR";
    private static final String FILE_NAME = "10101-shutoku.csv";

    @Inject
    private JapaneseErasAdapter jpErasAdapter;

    // row 1: A1_1 - A1_6
    private static final List<String> ROW_1_HEADERS = Arrays.asList(
            "都市区符号", "事業所記号", "ＦＤ通番", "作成年月日", "代表届書コード", "連記式項目バージョン"
    );

    // row 2
    private static final String A1_11 = "22223";
    private static final String A1_12 = "05";
    private static final int ROW_2_SIZE = 2;

    // row 3
    private static final String A1_13 = "[kanri]";
    private static final int ROW_3_SIZE = 1;

    // row 4
    private static final String A1_14 = "社会保険労務士氏名";
    private static final String A1_15 = "事業所情報数";
    private static final int ROW_4_SIZE = 2;

    // row 5
    private static final String A1_16 = "";
    private static final String A1_17 = "001";
    private static final int ROW_5_SIZE = 2;

    // row 6: A1_18 - A1_29
    private static final List<String> ROW_6_HEADERS = Arrays.asList(
            "都市区符号",          "事業所記号",                        "事業所番号",                        "親番号（郵便番号）",
            "子番号（郵便番号）",  "事業所所在地",                       "事業所名称",                       "事業主氏名",
            "電話番号",           "雇用保険適用事業所番号（安定所番号）", "雇用保険適用事業所番号（一連番号）", "雇用保険適用事業所番号（CD）"
    );

    // row 7
    private static final int ROW_7_SIZE = 12;

    // row 8
    private static final String A1_42 = "[data]";
    private static final int ROW_8_SIZE = 1;

    // row 9: A1_43 - A1_103
    private static final List<String> ROW_9_HEADERS = Arrays.asList(
            "帳票種別",                        "安定所番号",                  "個人番号",                  "被保険者番号4桁",                 "被保険者番号6桁",
            "被保険者番号CD",                  "取得区分",                    "被保険者氏名",              "被保険者氏名フリガナ（カタカナ）",  "変更後の氏名",
            "変更後の氏名フリガナ（カタカナ）", "性別",                        "生年月日（元号）",           "生年月日（年）",                  "生年月日（月）",
            "生年月日（日）",                  "事業所番号（安定所番号）",     "事業所番号（一連番号）",      "事業所番号（CD）",                "資格取得年月日（元号）",
            "資格取得年月日（年）",            "資格取得年月日（月）",         "資格取得年月日（日）",        "被保険者となったことの原因",       "賃金（支払の態様）",
            "賃金（賃金月額）",                "雇用形態",                    "職種",                      "就職経路",                        "取得時被保険者種類",
            "番号複数取得チェック不要",        "1週間の所定労働時間　（時間）", "1週間の所定労働時間　（分）", "契約期間の定め",                   "契約期間開始（元号）",
            "契約期間開始（年）",              "契約期間開始（月）",           "契約期間開始（日）",        "契約期間終了（元号）",              "契約期間終了（年）",
            "契約期間終了（月）",              "契約期間終了（日）",           "契約更新条項の有無",        "事業所名",                         "被保険者氏名（ローマ字）",
            "国籍・地域",                     "国籍地域コード",               "在留資格",                  "在留資格コード",                   "在留期間（年）",
            "在留期間（月）",                 "在留期間（日）",               "資格外活動許可の有無",       "派遣・請負就労区分",               "備考",
            "あて先",                         "備考欄（備考）",               "確認通知年月日（元号）",    "確認通知年月日（年）",              "確認通知年月日（月）",
            "確認通知年月日（日）"
    );

    // row 10
    private static final int ROW_10_SIZE = 61;
    private static final String A1_104 = "10101";
    private static final String A1_105 = "";
    private static final String A1_106 = "";
    private static final int LIMITTED_HOUR = 100;

    private int row;
    private int startColumn;
    private int lineFeedCode = 0;
    private String laborCode;

    @Override
    public void generate(FileGeneratorContext fileContext, ExportDataCsv data) {
        try (val reportContext = this.createEmptyContext(REPORT_ID)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            Worksheet worksheet = worksheets.get(0);
            row = 0;
            startColumn = 0;
            laborCode = "";
            lineFeedCode = (data.getReportTxtSettingExport().getLineFeedCode() == LineFeedCodeAtr.ADD.value || data.getReportTxtSettingExport().getLineFeedCode() == LineFeedCodeAtr.E_GOV.value) ? 1 : 0;
            fillFixedRows(worksheet, data);
            fillDataRows(worksheet, data);
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

    private void fillFixedRows(Worksheet worksheet, ExportDataCsv data) {

        GeneralDate fillingDate = data.getFillingDate();
        Map<String, LaborInsuranceOffice> laborInsuranceOffices = data.getLaborInsuranceOffices();
        CompanyInfor companyInfo = data.getCompanyInfo();

        List<SortObject> sortObjects = data.getSortObjects();
        EmpInsReportSetting reportSetting = data.getReportSetting();
        EmpInsReportTxtSetting reportTxtSetting = data.getReportTxtSetting();
        sortObjects = sort(reportSetting, sortObjects);

        EmpInsReportSettingExport reportSettingExport = data.getReportSettingExport();
        EmpInsRptTxtSettingExport reportTxtSettingExport = data.getReportTxtSettingExport();

        Map<String, EmpInsHist> empInsHists = data.getEmpInsHists();
        Map<String, EmpInsNumInfo> empInsNumInfos = data.getEmpInsNumInfos();
        Map<String, EmpInsOffice> empInsOffices = data.getEmpInsOffices();

        Cells cells = worksheet.getCells();
        String value = "";
        // row 1
        for (int c = 0; c < ROW_1_HEADERS.size(); c++) {
            value += ROW_1_HEADERS.get(c) + ",";
        }
        cells.get(row, 0).setValue(value.substring(0, value.length()-1));
        row += lineFeedCode;
        startColumn += row > 0 ? 0 : 1;

        // row 2
        // A1_7, A1_8
        List<String> empHistIds = sortObjects.stream().map(SortObject::getEmployeeId).collect(Collectors.toList());
        value = "";
        if (empInsHists.containsKey(empHistIds.get(0))) {
            val histId = empInsHists.get(empHistIds.get(0)).getHistoryItem().get(0).identifier();
            if (empInsNumInfos.containsKey(histId)) {
                if (empInsOffices.get(histId) != null) {
                    laborCode = empInsOffices.get(histId).getLaborInsCd().v();
                    if (laborInsuranceOffices.containsKey(laborCode)) {
                        value += laborInsuranceOffices.get(laborCode).getEmploymentInsuranceInfomation().getCityCode().map(x -> x.v()).orElse("") + "," + laborInsuranceOffices.get(laborCode).getEmploymentInsuranceInfomation().getOfficeCode().map(x -> x.v()).orElse("");
                    } else {
                        value += ",";
                    }
                } else {
                    value += ",";
                }
            } else {
                value += ",";
            }
        } else {
            value += ",";
        }


        // A1_9, A1_10, A1_11, A1_12
        value += "," + reportTxtSettingExport.getFdNumber() + "," + fillingDate.toString("yyyy/MM/dd").replace("/", "") + "," + A1_11 + "," + A1_12;

        cells.get(row, 0 + startColumn).setValue(value);
        row += lineFeedCode;
        startColumn += row > 0 ? 0 : 1;

        // row 3
        // A1_13
        cells.get(row, 0 + startColumn).setValue(A1_13);
        row += lineFeedCode;
        startColumn += row > 0 ? 0 : 1;

        // row 4
        // A1_14, A1_15
        value = "";
        value += A1_14 + "," + A1_15;
        cells.get(row, 0 + startColumn).setValue(value);
        row += lineFeedCode;
        startColumn += row > 0 ? 0 : 1;

        // row 5
        // A1_16, A1_17
        value = "";
        value += A1_16 + "," + A1_17;
        cells.get(row, 0 + startColumn).setValue(value);
        row += lineFeedCode;
        startColumn += row > 0 ? 0 : 1;

        // row 6
        // A1_18 - A1_129
        value = "";
        for (int c = 0; c < ROW_6_HEADERS.size(); c++) {
            value += c == ROW_6_HEADERS.size()-1 ? ROW_6_HEADERS.get(c) : ROW_6_HEADERS.get(c) + ",";
        }
        cells.get(row, 0 + startColumn).setValue(value);
        row += lineFeedCode;
        startColumn += row > 0 ? 0 : 1;

        // row 7
        value = "";
        if (!laborCode.equals("")) {
            // A1_130, A1_131, A1_132
            if (laborInsuranceOffices.containsKey(laborCode)) {
                value += laborInsuranceOffices.get(laborCode).getEmploymentInsuranceInfomation().getCityCode().map(x -> x.v()).orElse("") + ","
                        + laborInsuranceOffices.get(laborCode).getEmploymentInsuranceInfomation().getOfficeCode().map(x -> x.v()).orElse("") + ",";

                value += laborInsuranceOffices.get(laborCode).getEmploymentInsuranceInfomation().getOfficeNumber1().map(x -> x.v()).orElse("")
                        + laborInsuranceOffices.get(laborCode).getEmploymentInsuranceInfomation().getOfficeNumber2().map(x -> x.v()).orElse("")
                        + laborInsuranceOffices.get(laborCode).getEmploymentInsuranceInfomation().getOfficeNumber3().map(x -> x.v()).orElse("")
                        + ",";
            } else {
                value += ",,,";
            }

            // A1_133, A1_134, A1_135, A1_136, A1_137, A1_138, A1_139, A1_140, A1_141
            if (reportTxtSettingExport.getOfficeAtr() == OfficeCls.OUTPUT_COMPANY.value) {

                try {
                    value += (companyInfo.getPostCd().length() > 2 ? companyInfo.getPostCd().substring(0, 3) : companyInfo.getPostCd()) + ","
                            + companyInfo.getPostCd().substring(companyInfo.getPostCd().length() > 3 ? companyInfo.getPostCd().length() - 4 : 0) + ","
                            + formatTooLongText(companyInfo.getAdd_1() + companyInfo.getAdd_2(), 75) + ","
                            + formatTooLongText(companyInfo.getCompanyName(), 50) + ","
                            + formatTooLongText(companyInfo.getRepname(), 25) + ","

                            + (companyInfo.getPhoneNum().length() > 12 ? companyInfo.getPhoneNum().substring(0, 12) : companyInfo.getPhoneNum()) + ",";

                    value += ",,";
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else if (reportTxtSettingExport.getOfficeAtr() == OfficeCls.OUPUT_LABOR_OFFICE.value) {

                try {
                    if (laborInsuranceOffices.containsKey(laborCode)) {
                        value += laborInsuranceOffices.get(laborCode).getBasicInformation().getStreetAddress().getPostalCode().map(x -> x.v().length() > 2 ? x.v().substring(0, 3) : x.v()).orElse("") + ","
                                + laborInsuranceOffices.get(laborCode).getBasicInformation().getStreetAddress().getPostalCode().map(x -> x.v().length() > 3 ? x.v().substring(x.v().length() - 4) : x.v()).orElse("") + ","
                                + formatTooLongText(laborInsuranceOffices.get(laborCode).getBasicInformation().getStreetAddress().getAddress1().map(x -> x.v()).orElse("")
                                    + laborInsuranceOffices.get(laborCode).getBasicInformation().getStreetAddress().getAddress2().map(x -> x.v()).orElse(""), 75) + ","
                                + formatTooLongText(laborInsuranceOffices.get(laborCode).getLaborOfficeName().v(),50) + ","
                                + formatTooLongText(laborInsuranceOffices.get(laborCode).getBasicInformation().getRepresentativeName().map(x -> x.v()).orElse(""), 25) + ","

                                + laborInsuranceOffices.get(laborCode).getBasicInformation().getStreetAddress().getPhoneNumber().map(x -> x.v().length() > 12 ? x.v().substring(0, 12) : x.v()).orElse("") + ",";

                        value += laborInsuranceOffices.get(laborCode).getEmploymentInsuranceInfomation().getOfficeNumber1().map(x -> x.v()).orElse("") + ","
                                + laborInsuranceOffices.get(laborCode).getEmploymentInsuranceInfomation().getOfficeNumber2().map(x -> x.v()).orElse("") + ","
                                + laborInsuranceOffices.get(laborCode).getEmploymentInsuranceInfomation().getOfficeNumber3().map(x -> x.v()).orElse("");
                    } else {
                        value += ",,,,,,,,";
                    }

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

        } else {
            for (int i = 0; i < ROW_7_SIZE; i++) {
                value += ",";
            }
        }
        cells.get(row, 0 + startColumn).setValue(value);
        row += lineFeedCode;
        startColumn += row > 0 ? 0 : 1;

        // row 8
        // A1_142
        cells.get(row, 0 + startColumn).setValue(A1_42);
        row += lineFeedCode;
        startColumn += row > 0 ? 0 : 1;
    }

    private void fillDataRows(Worksheet worksheet, ExportDataCsv data) {
        Cells cells = worksheet.getCells();
        String value = "";
        // row 9
        // A1_143 -> A1_103
        for (int c = 0; c < ROW_9_HEADERS.size(); c++) {
            value += c == ROW_9_HEADERS.size()-1 ? ROW_9_HEADERS.get(c) : ROW_9_HEADERS.get(c) + ",";
        }
        cells.get(row, 0 + startColumn).setValue(value);
        row += lineFeedCode;
        startColumn += row > 0 ? 0 : 1;

        //row 10
        JapaneseEras jpEras = this.jpErasAdapter.getAllEras();
        Map<String, EmpInsHist> empInsHists = data.getEmpInsHists();
        Map<String, EmpInsNumInfo> empInsNumInfos = data.getEmpInsNumInfos();
        Map<String, EmpInsGetInfo> empInsGetInfos = data.getEmpInsGetInfos();
        Map<String, EmployeeInfoEx> employeeInfos = data.getEmployeeInfos();
        Map<String, PersonExport> personExports = data.getPersonExports();
        Map<String, LaborInsuranceOffice> laborInsuranceOffices = data.getLaborInsuranceOffices();
        Map<String, EmpInsOffice> empInsOffices = data.getEmpInsOffices();
        Map<String, PublicEmploymentSecurityOffice> pubEmpSecOffices = data.getPubEmpSecOffices();
        LaborContractHist dummyLaborContractHist = data.getDummyLaborContractHist();
        ForeignerResHistInfo dummyForResHistInfo = data.getDummyForResHistInfo();
        List<SortObject> sortObjects = data.getSortObjects();

        CompanyInfor companyInfo = data.getCompanyInfo();

        EmpInsReportSettingExport reportSettingExport = data.getReportSettingExport();
        EmpInsRptTxtSettingExport reportTxtSettingExport = data.getReportTxtSettingExport();
        EmpInsReportSetting reportSetting = data.getReportSetting();
        EmpInsReportTxtSetting reportTxtSetting = data.getReportTxtSetting();

        // sort
        sortObjects = sort(reportSetting, sortObjects);

        List<String> empHistIds = sortObjects.stream().map(SortObject::getEmployeeId).collect(Collectors.toList());

        value = "";
        for (String e: empHistIds) {
            // A1_104, A1_105, A1_106
            value += A1_104 + ","
                    + A1_105 + ","
                    + A1_106 + ",";

            // A1_107, A1_108, A1_109
            if (empInsHists.containsKey(e)) {
                val histId = empInsHists.get(e).getHistoryItem().get(0).identifier();
                if (empInsNumInfos.containsKey(histId)) {
                    String empInsNumber = empInsNumInfos.get(histId).getEmpInsNumber().v();

                    value += (empInsNumber.length() > 4 ? empInsNumber.substring(0, 4) : empInsNumber) + ","
                            + (empInsNumber.length() > 4 ? (empInsNumber.length() > 10 ? empInsNumber.substring(4, 10) : empInsNumber.substring(4)) : "") + ","
                            + (empInsNumber.length() > 10 ? empInsNumber.substring(10) : "") + ",";
                } else {
                    value += ",,,";
                }
            } else {
                value += ",,,";
            }

            // A1_110
            if (empInsGetInfos.containsKey(e)) {
                value += empInsGetInfos.get(e).getAcquisitionAtr().map(x -> x.value + 1).orElse(null) + ",";
            } else {
                value += ",";
            }

            if (employeeInfos.containsKey(e)) {
                val pId = employeeInfos.get(e).getPId();
                if (personExports.containsKey(pId)) {

                    if (((empInsGetInfos.containsKey(e) && (empInsGetInfos.get(e).getAcquisitionAtr().map(x -> x.value).orElse(null)) == AcquisitionAtr.REHIRE.value))
                            && reportSettingExport.getNameChangeClsAtr() == PrinfCtg.PRINT.value
                            && reportSettingExport.getSubmitNameAtr() == EmpSubNameClass.PERSONAL_NAME.value) {
                        // A1_111, A1_112
                        value += personExports.get(pId).getPersonNameGroup().getOldName().getFullName() + ",";

                        value += toHalfKana(personExports.get(pId).getPersonNameGroup().getOldName().getFullNameKana()) + ",";

                        // A1_113, A1_114
                        value += personExports.get(pId).getPersonNameGroup().getPersonName().getFullName() + ",";

                        value += toHalfKana(personExports.get(pId).getPersonNameGroup().getPersonName().getFullNameKana()) + ",";

                    } else if (((empInsGetInfos.containsKey(e) && (empInsGetInfos.get(e).getAcquisitionAtr().map(x -> x.value).orElse(null)) == AcquisitionAtr.REHIRE.value))
                            && reportSettingExport.getNameChangeClsAtr() == PrinfCtg.PRINT.value
                            && reportSettingExport.getSubmitNameAtr() == EmpSubNameClass.REPORTED_NAME.value) {
                        // A1_111, A1_112
                        value += personExports.get(pId).getPersonNameGroup().getOldName().getFullName() + ",";

                        value += toHalfKana(personExports.get(pId).getPersonNameGroup().getOldName().getFullNameKana()) + ",";
                        // A1_113, A1_114
                        value += personExports.get(pId).getPersonNameGroup().getTodokedeFullName().getFullName() + ",";

                        value += toHalfKana(personExports.get(pId).getPersonNameGroup().getTodokedeFullName().getFullNameKana()) + ",";

                    } else if (reportSettingExport.getSubmitNameAtr() == EmpSubNameClass.PERSONAL_NAME.value) {
                        // A1_111, A1_112
                        value += personExports.get(pId).getPersonNameGroup().getPersonName().getFullName() + ",";

                        value += toHalfKana(personExports.get(pId).getPersonNameGroup().getPersonName().getFullNameKana());
                        // A1_113, A1_114
                        value += ",,,";
                    } else if (reportSettingExport.getSubmitNameAtr() == EmpSubNameClass.REPORTED_NAME.value) {
                        // A1_111, A1_112
                        value += personExports.get(pId).getPersonNameGroup().getTodokedeFullName().getFullName() + ",";

                        value += toHalfKana(personExports.get(pId).getPersonNameGroup().getTodokedeFullName().getFullNameKana());
                        // A1_113, A1_114
                        value += ",,,";
                    } else {
                        // A1_111, A1_112, A1_113, A1_114
                        value += ",,,,";
                    }

                    val birthDateJp = toJapaneseDate(jpEras, personExports.get(pId).getBirthDate());
                    // A1_115, A1_116, A1_117, A1_118, A1_119
                    value += personExports.get(pId).getGender() + ","
                            + birthDateJp.era() + ","
                            + (String.valueOf(birthDateJp.year() + 1).length() < 2 ? "0" + (birthDateJp.year() + 1) : (birthDateJp.year() + 1)) + ","
                            + (String.valueOf(birthDateJp.month()).length() < 2 ? "0" + birthDateJp.month() : birthDateJp.month()) + ","
                            + (String.valueOf(birthDateJp.day()).length() < 2 ? "0" + birthDateJp.day() : birthDateJp.day()) + ",";
                }
            } else {
                // A1_111, A1_112, A1_113, A1_114, A1_115, A1_116, A1_117, A1_118, A1_119

                value += ",,,,,,,,,";
            }

            // A1_120, A1_121, A1_122
            if (empInsHists.containsKey(e)) {
                val histId = empInsHists.get(e).getHistoryItem().get(0).identifier();
                if (empInsNumInfos.containsKey(histId)) {
                    if (empInsOffices.get(histId) != null) {
                        String laborCode = empInsOffices.get(histId).getLaborInsCd().v();

                        if (reportTxtSettingExport.getOfficeAtr() == OfficeCls.OUTPUT_COMPANY.value ) {

                            value += ",,,";
                        } else if (reportTxtSettingExport.getOfficeAtr() == OfficeCls.OUPUT_LABOR_OFFICE.value) {
                            if (laborInsuranceOffices.containsKey(laborCode)) {
                                value += laborInsuranceOffices.get(laborCode).getEmploymentInsuranceInfomation().getOfficeNumber1().map(x -> x.v()).orElse("") + ","
                                        + laborInsuranceOffices.get(laborCode).getEmploymentInsuranceInfomation().getOfficeNumber2().map(x -> x.v()).orElse("") + ","
                                        + laborInsuranceOffices.get(laborCode).getEmploymentInsuranceInfomation().getOfficeNumber3().map(x -> x.v()).orElse("") + ",";
                            } else {
                                value += ",,,";
                            }
                        }
                    } else {
                        value += ",,,";
                    }
                }
            } else {

                value += ",,,";
            }

            // A1_123, A1_124, A1_125, A1_126
            if (empInsHists.containsKey(e)) {
                val qualificationDate = empInsHists.get(e).getHistoryItem().get(0).start();
                val qualificationDateJp = toJapaneseDate(jpEras, qualificationDate);

                value += qualificationDateJp.era() + ","
                        + (String.valueOf(qualificationDateJp.year() + 1).length() < 2 ? "0" + (qualificationDateJp.year() + 1) : (qualificationDateJp.year() + 1)) + ","
                        + (String.valueOf(qualificationDateJp.month()).length() < 2 ? "0" + qualificationDateJp.month() : qualificationDateJp.month()) + ","
                        + (String.valueOf(qualificationDateJp.day()).length() < 2 ? "0" + qualificationDateJp.day() : qualificationDateJp.day()) + ",";
            } else {

                value += ",,,,";
            }

            // A1_127, A1_128, A1_129, A1_130, A1_131, A1_132, A1_133, A1_134, A1_135, A1_136
            if (empInsGetInfos.containsKey(e)) {

                value += empInsGetInfos.get(e).getInsCauseAtr().map(x -> x.value == 4 ? String.valueOf(2*x.value) : String.valueOf(x.value + 1)).orElse("") + ","
                        + empInsGetInfos.get(e).getPaymentMode().map(x -> String.valueOf(x.value + 1)).orElse("") + ","
                        + empInsGetInfos.get(e).getPayWage().map(x -> String.valueOf(x.v())).orElse("") + ","
                        + empInsGetInfos.get(e).getEmploymentStatus().map(x -> String.valueOf(x.value + 1)).orElse("") + ","
                        + empInsGetInfos.get(e).getJobAtr().map(x -> String.valueOf(x.value + 1).length() > 1 ? String.valueOf(x.value + 1) : "0" + (x.value + 1)).orElse("") + ","
                        + empInsGetInfos.get(e).getJobPath().map(x -> String.valueOf(x.value + 1)).orElse("") + ","
                        + ",,"
                        + empInsGetInfos.get(e).getWorkingTime().map(x -> toHours(x.v()).substring(0, 2)).orElse("") + ","
                        + empInsGetInfos.get(e).getWorkingTime().map(x -> toHours(x.v()).substring(2)).orElse("") + ",";
            } else {

                value += ",,,,,,,,,,";
            }

            val startDateJP = toJapaneseDate(jpEras, dummyLaborContractHist.getStartDate());
            val endDateJP = toJapaneseDate(jpEras, dummyLaborContractHist.getEndDate());

            // A1_137, A1_138, A1_139, A1_140, A1_141, A1_142, A1_143, A1_144, A1_45, A1_46
            value +=  (empInsGetInfos.containsKey(e) ? empInsGetInfos.get(e).getPrintAtr().map(x -> (x.value == 0 ? 2 : String.valueOf(x.value))).orElse("") : "") + ","
                    + startDateJP.era() + ","
                    + (String.valueOf(startDateJP.year() + 1).length() < 2 ? "0" + (startDateJP.year() + 1) : String.valueOf((startDateJP.year() + 1))) + ","
                    + (String.valueOf(startDateJP.month()).length() < 2 ? "0" + startDateJP.month() : String.valueOf(startDateJP.month())) + ","
                    + (String.valueOf(startDateJP.day()).length() < 2 ? "0" + startDateJP.day() : String.valueOf(startDateJP.day())) + ","
                    + endDateJP.era() + ","
                    + (String.valueOf(endDateJP.year() + 1).length() < 2 ? "0" + (endDateJP.year() + 1) : (endDateJP.year() + 1)) + ","
                    + (String.valueOf(endDateJP.month()).length() < 2 ? "0" + endDateJP.month() : endDateJP.month()) + ","
                    + (String.valueOf(endDateJP.day()).length() < 2 ? "0" + endDateJP.day() : endDateJP.day()) + ","
                    + dummyLaborContractHist.getWorkingSystem() + ",";

            // A1_147
            if (empInsHists.containsKey(e)) {
                val histId = empInsHists.get(e).getHistoryItem().get(0).identifier();
                if (empInsNumInfos.containsKey(histId)) {
                    if (empInsOffices.get(histId) != null) {
                        String laborCode = empInsOffices.get(histId).getLaborInsCd().v();

                        if (reportTxtSettingExport.getOfficeAtr() == OfficeCls.OUTPUT_COMPANY.value) {

                            value += companyInfo.getCompanyName() + ",";
                        } else if (reportTxtSettingExport.getOfficeAtr() == OfficeCls.OUPUT_LABOR_OFFICE.value) {
                            if (laborInsuranceOffices.containsKey(laborCode)) {
                                value += laborInsuranceOffices.get(laborCode).getLaborOfficeName().v() + ",";
                            } else {
                                value += ",";
                            }
                        } else if (reportTxtSettingExport.getOfficeAtr() == OfficeCls.DO_NOT_OUTPUT.value) {

                            value += ",";
                        }
                    } else {
                        value += ",";
                    }
                } else {
                    value += ",";
                }
            } else {
                value += ",";
            }

            // A1_148
            if (employeeInfos.containsKey(e)) {
                val pId = employeeInfos.get(e).getPId();
                if (personExports.containsKey(pId)) {

                    value += personExports.get(pId).getPersonNameGroup().getPersonRomanji().getFullName() + ",";
                } else {

                    value += ",";
                }
            } else {

                value += ",";
            }

            val periodOfStay = toJapaneseDate(jpEras, dummyForResHistInfo.getEndDate());
            val periodStay = dummyForResHistInfo.getEndDate().toString("yyyy/MM/dd").split("/");

            // A1_149, A1_150, A1_151, A1_152, A1_153, A1_154, A1_155, A1_156, A1_157, A1_158
            value += dummyForResHistInfo.getNationalityRegion() + ","
                    + ","
                    + dummyForResHistInfo.getResidenceStatus() + ","
                    + ","
                    + (periodStay[0].length() < 2 ? "0" + periodStay[0] : periodStay[0]) + ","
                    + (periodStay[1].length() < 2 ? "0" + periodStay[1] : periodStay[1]) + ","
                    + (periodStay[2].length() < 2 ? "0" + periodStay[2] : periodStay[2]) + ","
                    + dummyForResHistInfo.getNonQualificationPermission() + ","
                    + dummyForResHistInfo.getContractWorkAtr() + ","
                    + ",";

            // A1_159
            if (empInsHists.containsKey(e)) {
                val histId = empInsHists.get(e).getHistoryItem().get(0).identifier();
                if (empInsNumInfos.containsKey(histId)) {
                    if (empInsOffices.get(histId) != null) {
                        String laborCode = empInsOffices.containsKey(histId) ? empInsOffices.get(histId).getLaborInsCd().v() : "";

                        if (laborInsuranceOffices.containsKey(laborCode)) {
                            String laborInsOffCode = laborInsuranceOffices.get(laborCode).getEmploymentInsuranceInfomation().getOfficeNumber1().map(x -> x.v()).orElse("")
                                    + laborInsuranceOffices.get(laborCode).getEmploymentInsuranceInfomation().getOfficeNumber2().map(x -> x.v()).orElse("")
                                    + laborInsuranceOffices.get(laborCode).getEmploymentInsuranceInfomation().getOfficeNumber3().map(x -> x.v()).orElse("");

                            if (pubEmpSecOffices.containsKey(laborInsOffCode.length() > 3 ? laborInsOffCode.substring(0, 4) : laborInsOffCode)) {

                                value += pubEmpSecOffices.get(laborInsOffCode.length() > 3 ? laborInsOffCode.substring(0, 4) : laborInsOffCode).getPublicEmploymentSecurityOfficeName() + ",";
                            } else {

                                value += ",";
                            }
                        } else {
                            value += ",";
                        }

                    } else {
                        value += ",";
                    }
                } else {
                    value += ",";
                }
            } else {
                value += ",";
            }

            // A1_160, A1_161, A1_162, A1_163, A1_164
            value += ",,,,,";

            cells.get(row, 0 + startColumn).setValue(value.substring(0, value.length()-1));
            value = "";

            row += lineFeedCode;
            startColumn += row > 0 ? 0 : 1;
        }
    }

    private List<SortObject> sort(EmpInsReportSetting reportSetting, List<SortObject> sortObjects) {
        switch (reportSetting.getOutputOrderAtr()) {
            case INSURANCE_NUMBER:
                sortObjects.sort(Comparator.comparing(SortObject::getInsuranceNumber, Comparator.nullsFirst(Comparator.naturalOrder())).thenComparing(SortObject::getEmployeeCode, Comparator.nullsFirst(Comparator.naturalOrder())));
                //listDataExport.sort(Comparator.comparing(EmpInsGetQualifReport::getInsuredNumber, Comparator.nullsFirst(Comparator.naturalOrder())).thenComparing(EmpInsGetQualifReport::getScd, Comparator.nullsFirst(Comparator.naturalOrder())));
                break;
            case DEPARTMENT_EMPLOYEE:
                sortObjects.sort(Comparator.comparing(SortObject::getEmployeeCode, Comparator.nullsFirst(Comparator.naturalOrder())).thenComparing(SortObject::getEmployeeCode, Comparator.nullsFirst(Comparator.naturalOrder())));
            case EMPLOYEE_CODE:
                sortObjects.sort(Comparator.comparing(SortObject::getEmployeeCode, Comparator.nullsFirst(Comparator.naturalOrder())).thenComparing(SortObject::getEmployeeCode, Comparator.nullsFirst(Comparator.naturalOrder())));
                //listDataExport.sort(Comparator.comparing(EmpInsGetQualifReport::getScd, Comparator.nullsFirst(Comparator.naturalOrder())).thenComparing(EmpInsGetQualifReport::getScd, Comparator.nullsFirst(Comparator.naturalOrder())));
                break;
            case EMPLOYEE:
                sortObjects.sort(Comparator.comparing(SortObject::getEmployeeNameKana, Comparator.nullsFirst(Comparator.naturalOrder())).thenComparing(SortObject::getEmployeeCode, Comparator.nullsFirst(Comparator.naturalOrder())));
                //listDataExport.sort(Comparator.comparing(EmpInsGetQualifReport::getPersonalNameKana, Comparator.nullsFirst(Comparator.naturalOrder())).thenComparing(EmpInsGetQualifReport::getScd, Comparator.nullsFirst(Comparator.naturalOrder())));
                break;
            default:
                break;
        }
        return sortObjects;
    }

    private String toHours(Integer minutes) {
        return minutes == null ? "" : (minutes >= LIMITTED_HOUR*60 ? "9959" : minutes < 60 ? ("00" + (minutes < 10 ? "0" + minutes : minutes)) : ((minutes/60 < 10 ? "0" + minutes/60 : minutes/60) + "" +(minutes%60 < 10 ? "0" + minutes%60 : minutes%60)));
    }

    private JapaneseDate toJapaneseDate(JapaneseEras jpEras, GeneralDate date) {
        Optional<JapaneseEraName> eraName = jpEras.eraOf(date);
        return eraName.map(japaneseEraName -> new JapaneseDate(date, japaneseEraName)).orElse(null);
    }

    private String formatTooLongText(String text, int maxByteAllowed) throws UnsupportedEncodingException {
        if (text == null) {
            return "";
        }
        if (text.getBytes("Shift_JIS").length <= maxByteAllowed) {
            return text;
        }
        int textLength = text.length();
        int subLength = maxByteAllowed / 2;
        while (subLength < textLength) {
            if (text.substring(0, subLength + 1).getBytes("Shift_JIS").length > maxByteAllowed) {
                break;
            }
            subLength++;
        }
        return text.substring(0, subLength);
    }

    private String toHalfKana(String value) {
        value = KatakanaConverter.hiraganaToKatakana(value);
        value = KatakanaConverter.fullKatakanaToHalf(value);
        return value;
    }
}
