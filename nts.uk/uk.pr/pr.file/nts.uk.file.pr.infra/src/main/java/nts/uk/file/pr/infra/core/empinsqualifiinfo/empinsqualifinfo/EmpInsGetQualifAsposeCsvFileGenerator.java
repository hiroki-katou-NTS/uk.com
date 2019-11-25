package nts.uk.file.pr.infra.core.empinsqualifiinfo.empinsqualifinfo;

import com.aspose.cells.*;
import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInfor;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoEx;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.ForeignerResHistInfo;
import nts.uk.ctx.pr.core.dom.adapter.employee.employment.LaborContractHist;
import nts.uk.ctx.pr.core.dom.adapter.person.PersonExport;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.LaborInsuranceOffice;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.LaborInsuranceOfficeCode;
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
import java.util.*;
import java.util.function.Function;
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

    // row 10
    private static final int ROW_10_SIZE = 61;
    private static final String A1_104 = "10101";
    private static final String A1_105 = "";
    private static final String A1_106 = "";

    private int row;
    private int startColumn;
    private int lineFeedCode = 0;

    @Override
    public void generate(FileGeneratorContext fileContext, ExportDataCsv data) {
        try (val reportContext = this.createEmptyContext(REPORT_ID)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            Worksheet worksheet = worksheets.get(0);
            row = 0;
            startColumn = 0;
            lineFeedCode = (data.getReportTxtSetting().getLineFeedCode() == LineFeedCode.ADD.value || data.getReportTxtSetting().getLineFeedCode() == LineFeedCode.E_GOV.value) ? 1 : 0;;
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

        EmpInsReportSettingExport reportSetting = data.getReportSetting();
        EmpInsRptTxtSettingExport reportTxtSetting = data.getReportTxtSetting();

        Cells cells = worksheet.getCells();

        // row 1
        for (int c = 0; c < ROW_1_HEADERS.size(); c++) {
            String header = ROW_1_HEADERS.get(c);
            cells.get(row, c).setValue(header);
        }
        row += lineFeedCode;
        startColumn += row > 0 ? 0 : ROW_1_HEADERS.size();

        // row 2
        Map<String, LaborInsuranceOffice> laborInsuranceOfficeMap = laborInsuranceOffices.entrySet().stream().filter(e -> e.getValue().getCompanyId().equals(companyInfo.getCompanyId())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        List<LaborInsuranceOfficeCode> laborInsOfficeCodes = laborInsuranceOfficeMap.values().stream().map(LaborInsuranceOffice::getLaborOfficeCode).collect(Collectors.toList());
        /*if (!laborInsOfficeCodes.isEmpty()) {
            cells.get(row, 0 + startColumn).setValue(laborInsuranceOffices.get(laborInsOfficeCodes.get(0)).getEmploymentInsuranceInfomation().getCityCode().map(x -> x.v()).orElse(null));
            cells.get(row, 1  + startColumn).setValue(laborInsuranceOffices.get(laborInsOfficeCodes.get(0)).getEmploymentInsuranceInfomation().getOfficeCode().map(x -> x.v()).orElse(null));
        }*/

        cells.get(row, 0  + startColumn).setValue("");
        cells.get(row, 1  + startColumn).setValue("");
        cells.get(row, 2  + startColumn).setValue(reportTxtSetting.getFdNumber());
        cells.get(row, 3  + startColumn).setValue(fillingDate.toString("yyyy/MM/dd").replace("/", ""));
        cells.get(row, 4  + startColumn).setValue(A1_11);
        cells.get(row, 5 + startColumn).setValue(A1_12);

        row += lineFeedCode;
        startColumn += row > 0 ? 0 : ROW_2_SIZE;

        // row 3
        cells.get(row, 0 + startColumn).setValue(A1_13);
        row += lineFeedCode;
        startColumn += row > 0 ? 0 : ROW_3_SIZE;

        // row 4
        cells.get(row, 0 + startColumn).setValue(A1_14);
        cells.get(row, 1 + startColumn).setValue(A1_15);
        row += lineFeedCode;
        startColumn += row > 0 ? 0 : ROW_4_SIZE;

        // row 5
        cells.get(row, 0 + startColumn).setValue(A1_16);
        cells.get(row, 1 + startColumn).setValue(A1_17);
        row += lineFeedCode;
        startColumn += row > 0 ? 0 : ROW_5_SIZE;

        // row 6
        for (int c = 0; c < ROW_6_HEADERS.size(); c++) {
            String header = ROW_6_HEADERS.get(c);
            cells.get(row, c + startColumn).setValue(header);
        }
        row += lineFeedCode;
        startColumn += row > 0 ? 0 : ROW_6_HEADERS.size();

        // row 7
        for (int c = 0; c < 12; c++) {
            cells.get(row, c + startColumn).setValue("test");
        }
        /*if (!laborInsOfficeCodes.isEmpty()) {
            cells.get(row, 0 + startColumn).setValue(laborInsuranceOffices.get(laborInsOfficeCodes.get(0)).getEmploymentInsuranceInfomation().getCityCode().map(x -> x.v().substring(0, 2)).orElse(null));
            cells.get(row, 1 + startColumn).setValue(laborInsuranceOffices.get(laborInsOfficeCodes.get(0)).getEmploymentInsuranceInfomation().getCityCode().map(x -> x.v().substring(3)).orElse(null));
            cells.get(row, 2 + startColumn).setValue(laborInsuranceOffices.get(laborInsOfficeCodes.get(0)).getLaborOfficeCode());

            if (reportSetting.getOfficeClsAtr() == OfficeCls.OUTPUT_COMPANY.value) {
                cells.get(row, 3 + startColumn).setValue(companyInfo.getPostCd().substring(0, 3));
                cells.get(row, 4 + startColumn).setValue(companyInfo.getPostCd().substring(companyInfo.getPostCd().length() - 4));
                cells.get(row, 5 + startColumn).setValue(companyInfo.getAdd_1() + companyInfo.getAdd_2());
                cells.get(row, 6 + startColumn).setValue(companyInfo.getCompanyName());
                cells.get(row, 7 + startColumn).setValue(companyInfo.getRepname());
                cells.get(row, 8 + startColumn).setValue(companyInfo.getPhoneNum());
            } else if (reportSetting.getOfficeClsAtr() == OfficeCls.OUPUT_LABOR_OFFICE.value) {
                cells.get(row, 3 + startColumn).setValue(laborInsuranceOffices.get(laborInsOfficeCodes.get(0)).getBasicInformation().getStreetAddress().getPostalCode().map(x -> x.v().substring(0, 3)).orElse(null));
                cells.get(row, 4 + startColumn).setValue(laborInsuranceOffices.get(laborInsOfficeCodes.get(0)).getBasicInformation().getStreetAddress().getPostalCode().map(x -> x.v().substring(x.v().length() - 4)).orElse(null));
                cells.get(row, 5 + startColumn).setValue(laborInsuranceOffices.get(laborInsOfficeCodes.get(0)).getBasicInformation().getStreetAddress().getAddress1().map(x -> x.v()).orElse(null)
                                                 + laborInsuranceOffices.get(laborInsOfficeCodes.get(0)).getBasicInformation().getStreetAddress().getAddress2().map(x -> x.v()).orElse(null));
                cells.get(row, 6 + startColumn).setValue(laborInsuranceOffices.get(laborInsOfficeCodes.get(0)).getLaborOfficeName());
                cells.get(row, 7 + startColumn).setValue(laborInsuranceOffices.get(laborInsOfficeCodes.get(0)).getBasicInformation().getRepresentativeName().map(x -> x.v()).orElse(null));
                cells.get(row, 8 + startColumn).setValue(laborInsuranceOffices.get(laborInsOfficeCodes.get(0)).getBasicInformation().getStreetAddress().getPhoneNumber().map(x -> x.v()).orElse(null));

            } else if (reportSetting.getOfficeClsAtr() == OfficeCls.DO_NOT_OUTPUT.value) {

            }

            cells.get(row, 9 + startColumn).setValue(laborInsOfficeCodes.get(0).v().substring(0, 4));
            cells.get(row, 10 + startColumn).setValue(laborInsOfficeCodes.get(0).v().substring(5, 10));
            cells.get(row, 11 + startColumn).setValue(laborInsOfficeCodes.get(0).v().substring(10));
        }*/
        row += lineFeedCode;
        startColumn += row > 0 ? 0 : ROW_7_SIZE;

        // row 8
        cells.get(row, 0 + startColumn).setValue(A1_42);
        row += lineFeedCode;
        startColumn += row > 0 ? 0 : ROW_8_SIZE;
    }

    private void fillDataRows(Worksheet worksheet, ExportDataCsv data) {
        Cells cells = worksheet.getCells();
        // row 9
        for (int c = 0; c < ROW_9_HEADERS.size(); c++) {
            String header = ROW_9_HEADERS.get(c);
            cells.get(row, c + startColumn).setValue(header);
        }
        row += lineFeedCode;
        startColumn += row > 0 ? 0 : ROW_9_HEADERS.size();

        //row 10
        JapaneseEras jpEras = this.jpErasAdapter.getAllEras();
        List<String> empIds = data.getEmpIds();
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

        CompanyInfor companyInfo = data.getCompanyInfo();

        EmpInsReportSettingExport reportSetting = data.getReportSetting();
        EmpInsRptTxtSettingExport reportTxtSetting = data.getReportTxtSetting();

        for (String e: new ArrayList<>(empInsHists.keySet())) {
            cells.get(row, 0 + startColumn).setValue(A1_104);
            cells.get(row, 1 + startColumn).setValue(A1_105);
            cells.get(row, 2 + startColumn).setValue(A1_106);

            if (empInsHists.containsKey(e)) {
                val histId = empInsHists.get(e).getHistoryItem().get(0).identifier();
                if (empInsNumInfos.containsKey(histId)) {

                    String empInsNumber = empInsNumInfos.get(histId).getEmpInsNumber().v();
                    cells.get(row, 3 + startColumn).setValue(empInsNumber.length() > 4 ? empInsNumber.substring(0, 4) : empInsNumber.substring(0));
                    cells.get(row, 4 + startColumn).setValue(empInsNumber.length() > 4 ? (empInsNumber.length() > 10 ? empInsNumber.substring(5, 10) : empInsNumber.substring(5)) : "");
                    cells.get(row, 5 + startColumn).setValue(empInsNumber.length() > 10 ? empInsNumber.substring(10) : "");
                }
            }

            if (empInsGetInfos.containsKey(e)) {
                cells.get(row, 6 + startColumn).setValue(empInsGetInfos.get(e).getAcquisitionAtr().map(x -> x.value + 1).orElse(null));
            }

            if (employeeInfos.containsKey(e)) {
                val pId = employeeInfos.get(e).getPId();
                if (personExports.containsKey(pId)) {

                    if (reportSetting.getSubmitNameAtr() == EmpSubNameClass.PERSONAL_NAME.value) {
                        cells.get(row, 7 + startColumn).setValue(personExports.get(pId).getPersonNameGroup().getPersonName().getFullName());
                        cells.get(row, 8 + startColumn).setValue(personExports.get(pId).getPersonNameGroup().getPersonName().getFullNameKana());
                    } else if (reportSetting.getSubmitNameAtr() == EmpSubNameClass.REPORTED_NAME.value) {
                        cells.get(row, 7 + startColumn).setValue(personExports.get(pId).getPersonNameGroup().getTodokedeFullName().getFullName());
                        cells.get(row, 8 + startColumn).setValue(personExports.get(pId).getPersonNameGroup().getTodokedeFullName().getFullNameKana());
                    } else if (((empInsGetInfos.containsKey(e) && (empInsGetInfos.get(e).getAcquisitionAtr().map(x -> x.value + 1).orElse(null)) == AcquisitionAtr.REHIRE.value))
                            && reportSetting.getNameChangeClsAtr() == PrinfCtg.PRINT.value) {
                        cells.get(row, 7 + startColumn).setValue(personExports.get(pId).getPersonNameGroup().getOldName().getFullName());
                        cells.get(row, 8 + startColumn).setValue(personExports.get(pId).getPersonNameGroup().getOldName().getFullNameKana());
                    }

                    if (((empInsGetInfos.containsKey(e) && (empInsGetInfos.get(e).getAcquisitionAtr().map(x -> x.value + 1).orElse(null)) == AcquisitionAtr.REHIRE.value))
                       && reportSetting.getNameChangeClsAtr() == PrinfCtg.PRINT.value
                       && !personExports.get(pId).getPersonNameGroup().getOldName().getFullName().equals(null)) {

                        if (reportSetting.getSubmitNameAtr() == EmpSubNameClass.PERSONAL_NAME.value) {
                            cells.get(row, 9 + startColumn).setValue(personExports.get(pId).getPersonNameGroup().getPersonName().getFullName());
                            cells.get(row, 10 + startColumn).setValue(personExports.get(pId).getPersonNameGroup().getPersonName().getFullName());
                        } else if (reportSetting.getSubmitNameAtr() == EmpSubNameClass.REPORTED_NAME.value) {
                            cells.get(row, 9 + startColumn).setValue(personExports.get(pId).getPersonNameGroup().getTodokedeFullName().getFullName());
                            cells.get(row, 10 + startColumn).setValue(personExports.get(pId).getPersonNameGroup().getTodokedeFullName().getFullName());
                        }
                    }

                    cells.get(row, 11 + startColumn).setValue(personExports.get(pId).getGender());
                    val birthDateJp = toJapaneseDate(jpEras, personExports.get(pId).getBirthDate());
                    cells.get(row, 12 + startColumn).setValue(birthDateJp.era());
                    cells.get(row, 13 + startColumn).setValue(birthDateJp.year());
                    cells.get(row, 14 + startColumn).setValue(birthDateJp.month());
                    cells.get(row, 15 + startColumn).setValue(birthDateJp.day());
                }
            }

            if (empInsHists.containsKey(e)) {
                val histId = empInsHists.get(e).getHistoryItem().get(0).identifier();
                if (empInsNumInfos.containsKey(histId)) {
                    if (empInsOffices.get(histId) != null) {
                        String laborCode = empInsOffices.get(histId).getLaborInsCd().v();
                        cells.get(row, 16 + startColumn).setValue(laborInsuranceOffices.get(laborCode).getLaborOfficeCode().v().substring(0, 4));
                        cells.get(row, 17 + startColumn).setValue(laborInsuranceOffices.get(laborCode).getLaborOfficeCode().v().substring(4, 10));
                        cells.get(row, 18 + startColumn).setValue(laborInsuranceOffices.get(laborCode).getLaborOfficeCode().v().substring(10));
                    }
                }
            }

            if (empInsHists.containsKey(e)) {

                val qualificationDate = empInsHists.get(e).getHistoryItem().get(0).start();
                val qualificationDateJp = toJapaneseDate(jpEras, qualificationDate);
                cells.get(row, 19 + startColumn).setValue(qualificationDateJp.toString());
                cells.get(row, 20 + startColumn).setValue(qualificationDateJp.year());
                cells.get(row, 21 + startColumn).setValue(qualificationDateJp.month());
                cells.get(row, 22 + startColumn).setValue(qualificationDateJp.day());
            }

            if (empInsGetInfos.containsKey(e)) {
                cells.get(row, 23 + startColumn).setValue(empInsGetInfos.get(e).getInsCauseAtr().map(x -> x.value).orElse(null));
                cells.get(row, 24 + startColumn).setValue(empInsGetInfos.get(e).getPaymentMode().map(x -> x.value).orElse(null));
                cells.get(row, 25 + startColumn).setValue(empInsGetInfos.get(e).getPayWage().map(x -> x.v()).orElse(null));
                cells.get(row, 26 + startColumn).setValue(empInsGetInfos.get(e).getEmploymentStatus().map(x -> x.value).orElse(null));
                cells.get(row, 27 + startColumn).setValue(empInsGetInfos.get(e).getJobAtr().map(x -> x.value).orElse(null));
                cells.get(row, 28 + startColumn).setValue(empInsGetInfos.get(e).getJobPath().map(x -> x.value).orElse(null));

                cells.get(row, 29 + startColumn).setValue("");
                cells.get(row, 30 + startColumn).setValue("");
                cells.get(row, 31 + startColumn).setValue(empInsGetInfos.get(e).getWorkingTime().map(x -> toHours(x.v())).orElse(null));
                cells.get(row, 32 + startColumn).setValue(empInsGetInfos.get(e).getWorkingTime().map(x -> x.v()).orElse(null));
            }

            cells.get(row, 33 + startColumn).setValue("");

            val startDateJP = toJapaneseDate(jpEras, dummyLaborContractHist.getStartDate());
            val endDateJP = toJapaneseDate(jpEras, dummyLaborContractHist.getEndDate());
            cells.get(row, 34 + startColumn).setValue(startDateJP.toString());
            cells.get(row, 35 + startColumn).setValue(String.valueOf(startDateJP.year()).length() < 2 ? "0" + startDateJP.year() : String.valueOf(startDateJP.year()));
            cells.get(row, 36 + startColumn).setValue(String.valueOf(startDateJP.month()).length() < 2 ? "0" + startDateJP.month() : String.valueOf(startDateJP.month()));
            cells.get(row, 37 + startColumn).setValue(String.valueOf(startDateJP.day()).length() < 2 ? "0" + startDateJP.day() : String.valueOf(startDateJP.day()));
            cells.get(row, 38 + startColumn).setValue(endDateJP.toString());
            cells.get(row, 39 + startColumn).setValue(String.valueOf(endDateJP.year()));
            cells.get(row, 40 + startColumn).setValue(String.valueOf(endDateJP.month()));
            cells.get(row, 41 + startColumn).setValue(String.valueOf(endDateJP.day()));

            cells.get(row, 42 + startColumn).setValue(dummyLaborContractHist.getWorkingSystem());

            if (empInsHists.containsKey(e)) {
                val histId = empInsHists.get(e).getHistoryItem().get(0).identifier();
                if (empInsNumInfos.containsKey(histId)) {
                    if (empInsOffices.get(histId) != null) {
                        String laborCode = empInsOffices.get(histId).getLaborInsCd().v();

                        if (reportTxtSetting.getOfficeAtr() == OfficeCls.OUTPUT_COMPANY.value) {
                            cells.get(row, 43 + startColumn).setValue(companyInfo.getCompanyName());
                        } else if (reportTxtSetting.getOfficeAtr() == OfficeCls.OUPUT_LABOR_OFFICE.value && laborInsuranceOffices.containsKey(laborCode)) {
                            cells.get(row, 43 + startColumn).setValue(laborInsuranceOffices.get(laborCode).getLaborOfficeName().v());
                        } else if (reportTxtSetting.getOfficeAtr() == OfficeCls.DO_NOT_OUTPUT.value) {
                            cells.get(row, 43 + startColumn).setValue("");
                        }
                    }
                }
            }

            if (employeeInfos.containsKey(e)) {
                val pId = employeeInfos.get(e).getPId();
                if (personExports.containsKey(pId)) {
                    cells.get(row, 44 + startColumn).setValue(personExports.get(pId).getPersonNameGroup().getPersonRomanji().getFullName());
                }
            }

            cells.get(row, 45 + startColumn).setValue(dummyForResHistInfo.getNationalityRegion());
            cells.get(row, 46 + startColumn).setValue("");
            cells.get(row, 47 + startColumn).setValue(dummyForResHistInfo.getResidenceStatus());
            cells.get(row, 48 + startColumn).setValue("");

            val periodOfStay = toJapaneseDate(jpEras, dummyForResHistInfo.getEndDate());
            cells.get(row, 49 + startColumn).setValue(periodOfStay.year());
            cells.get(row, 50 + startColumn).setValue(periodOfStay.month());
            cells.get(row, 51 + startColumn).setValue(periodOfStay.day());

            cells.get(row, 52 + startColumn).setValue(dummyForResHistInfo.getNonQualifPermission());
            cells.get(row, 53 + startColumn).setValue(dummyForResHistInfo.getContractWorkAtr());

            cells.get(row, 54 + startColumn).setValue("");

            if (empInsHists.containsKey(e)) {
                val histId = empInsHists.get(e).getHistoryItem().get(0).identifier();
                if (empInsNumInfos.containsKey(histId)) {
                    if (empInsOffices.get(histId) != null) {
                        String laborCode = empInsOffices.get(histId).getLaborInsCd().v();
                        if (pubEmpSecOffices.get(laborCode) != null) {
                            cells.get(row, 55 + startColumn).setValue(pubEmpSecOffices.get(laborCode).getPublicEmploymentSecurityOfficeName().v());
                        }
                    }
                }
            }
            cells.get(row, 56 + startColumn).setValue("");
            cells.get(row, 57 + startColumn).setValue("");
            cells.get(row, 58 + startColumn).setValue("");
            cells.get(row, 59 + startColumn).setValue("");
            cells.get(row, 60 + startColumn).setValue("");

            row += lineFeedCode;
        }
    }

    private String toHours(int minutes) {
        String hour = "";
        if (minutes < 60) {
            hour = "00:" + (minutes < 10 ? "0" + minutes : minutes);
        } else {
            hour = (minutes/60 < 10 ? "0" + minutes/60 : minutes/60) + ":" +((minutes - 60*(minutes/60)) < 10 ? "0" + (minutes - 60*(minutes/60)) : (minutes - 60*(minutes/60)));
        }
        return hour;
    }

    private JapaneseDate toJapaneseDate(JapaneseEras jpEras, GeneralDate date) {
        Optional<JapaneseEraName> eraName = jpEras.eraOf(date);
        return eraName.map(japaneseEraName -> new JapaneseDate(date, japaneseEraName)).orElse(null);
    }
}
