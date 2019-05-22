package nts.uk.file.pr.infra.core.wageprovision.wagetable;

import com.aspose.cells.*;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.file.app.core.wageprovision.wagetable.ItemDataNameExport;
import nts.uk.ctx.pr.file.app.core.wageprovision.wagetable.WageTableFileGenerator;
import nts.uk.ctx.pr.file.app.core.wageprovision.wagetable.WageTablelData;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Stateless
public class WageTableAsposeFileGenerator extends AsposeCellsReportGenerator
        implements WageTableFileGenerator {

    private static final String TEMPLATE_FILE = "report/QMM016.xlsx";

    private static final String REPORT_FILE_NAME = "QMM016単価名の登録.xlsx";

    private static final int COLUMN_START = 1;

    private static final int MAX_ROWS = 71;

    private static final int LINE_IN_PAGE = 76;

    private static final String TITLE = "賃金テーブルの登録";

    private static final String SHEET_NAME = "マスタリスト";

    @Inject
    private CompanyAdapter company;

    @Override
    public void generate(FileGeneratorContext fileContext, List<WageTablelData> exportData, List<ItemDataNameExport> dataName) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {

            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            worksheets.get(0).setName(SHEET_NAME);
            int page = exportData.size() == MAX_ROWS ? 0 : exportData.size() / MAX_ROWS;
            createTable(worksheets, page, this.company.getCurrentCompany().map(CompanyInfor::getCompanyName).orElse(""));
            fillData(worksheets, exportData, dataName);
            worksheets.setActiveSheetIndex(0);
            reportContext.processDesigner();
            reportContext.saveAsExcel(this.createNewFile(fileContext, this.getReportName(REPORT_FILE_NAME)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* get list item name*/
    private String getListItemName(List<ItemDataNameExport> data, String codeOp) {
        Optional<ItemDataNameExport> dataName = data.stream().filter(e -> e.equals(codeOp)).findFirst();
        return dataName.isPresent() ? dataName.get().getName() : "";
    }

    /*ElementType*/
    private String enumElementType(String code) {
        switch (code) {
            case "M001": {
                return TextResource.localize("Enum_Element_Type_M001");
            }
            case "M002": {
                return TextResource.localize("Enum_Element_Type_M002");
            }
            case "M003": {
                return TextResource.localize("Enum_Element_Type_M003");
            }
            case "M004": {
                return TextResource.localize("Enum_Element_Type_M004");
            }
            case "M005": {
                return TextResource.localize("Enum_Element_Type_M005");
            }
            case "M006": {
                return TextResource.localize("Enum_Element_Type_M006");
            }
            case "M007": {
                return TextResource.localize("Enum_Element_Type_M007");
            }
            case "N001": {
                return TextResource.localize("Enum_Element_Type_N001");
            }
            case "N002": {
                return TextResource.localize("Enum_Element_Type_N002");
            }
            case "N003": {
                return TextResource.localize("Enum_Element_Type_N003");
            }
            default:
                return "";
        }
    }

    /*QualificationPaymentMethod*/
    private String enumQualificationPaymentMethod(int index) {
        if(index == 0) {
            return TextResource.localize("Enum_Qualify_Pay_Method_Add_Multiple");
        }
        return TextResource.localize("Enum_Qualify_Pay_Method_Only_One_Highest");
    }


    private String getFixedValue1(WageTablelData e, List<ItemDataNameExport> dataName) {
        if (e.getElementSet() == 3) {
            return TextResource.localize("QMM016_49");
        }
        if(e.getElementSet() == 4) {
            return "欠勤日数";
        }
        if (!e.getFixElement1().isEmpty()) {
            return enumElementType(e.getFixElement1());
        }
        if (!e.getOptAddElement1().isEmpty()) {
            return getListItemName(dataName, e.getOptAddElement1());
        }
        return "";

    }

    private String getFixedValue2(WageTablelData e, List<ItemDataNameExport> dataName) {
        if (e.getElementSet() == 2) {
            return TextResource.localize("QMM016_50");
        }
        if(e.getElementSet() == 4) {
            return "欠勤日数";
        }
        if (!e.getFixElement2().isEmpty()) {
            return enumElementType(e.getFixElement2());
        }
        if (!e.getOptAddElement2().isEmpty()) {
            return getListItemName(dataName, e.getOptAddElement2());
        }

        return "";
    }

    private String getFixedValue3(WageTablelData e, List<ItemDataNameExport> dataName) {
        if(e.getElementSet() == 4) {
            return "遅刻・早退回数";
        }
        if (!e.getFixElement3().isEmpty()) {
            return enumElementType(e.getFixElement3());
        }
        if (!e.getOptAddElement3().isEmpty()) {
            return getListItemName(dataName, e.getOptAddElement3());
        }
        return "";
    }

    private String getR2_8(WageTablelData e) {
        if (!e.getFixElement3().isEmpty()) {
            return enumElementType(e.getFixElement3());
        }
        if (!e.getLowerLimit1().isEmpty()) {
            return e.getLowerLimit1() + TextResource.localize("QMM016_31") + e.getUpperLimit1();
        }
        return "";
    }

    private String getR2_9(WageTablelData e) {
        if (!e.getFixElement2().isEmpty()) {
            return enumElementType(e.getFixElement2());
        }
        if (!e.getLowerLimit2().isEmpty()) {
            return e.getLowerLimit2() + TextResource.localize("QMM016_31") + e.getUpperLimit2();
        }
        return "";
    }

    private String getR2_10(WageTablelData e) {
        if (!e.getFixElement3().isEmpty()) {
            return enumElementType(e.getFixElement3());
        }
        if (!e.getLowerLimit3().isEmpty()) {
            return e.getLowerLimit3() + TextResource.localize("QMM016_31") + e.getUpperLimit3();
        }
        return "";
    }

    // R_11
    private void createTable(WorksheetCollection worksheets, int pageMonth, String companyName) throws Exception {
        Worksheet worksheet = worksheets.get(0);
        settingPage(worksheet, companyName);
        for (int i = 0; i < pageMonth; i++) {
            worksheet.getCells().copyRows(worksheet.getCells(), 0, LINE_IN_PAGE * (i + 1), LINE_IN_PAGE + 1);
        }
    }

    private void settingPage(Worksheet worksheet, String companyName) {
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setHeader(1, "&\"ＭＳ ゴシック\"&16 " + TITLE);
        pageSetup.setHeader(0, "&\"ＭＳ ゴシック\"&10 " + companyName);
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d  H:mm:ss", Locale.JAPAN);
        String currentFormattedDate = LocalDateTime.now().format(fullDateTimeFormatter);
        pageSetup.setHeader(2, "&\"ＭＳ ゴシック\"&10 " + currentFormattedDate + "\npage&P");
    }

    private String convertYearMonth(Object startYearMonth){
        return startYearMonth.toString().substring(0,4) + "/" + startYearMonth.toString().substring(4,6);
    }

    private void fillData(WorksheetCollection worksheets, List<WageTablelData> data, List<ItemDataNameExport> dataName) {
        try {
            int rowStart = 3;
            Worksheet sheet = worksheets.get(0);
            Cells cells = sheet.getCells();
            for (int i = 0; i < data.size(); i++) {
                if (i % MAX_ROWS == 0 && i > 0) {
                    rowStart += 5;
                }

                WageTablelData e = data.get(i);
                cells.get(rowStart, COLUMN_START).setValue(e.getWageTableCode());
                cells.get(rowStart, COLUMN_START + 1).setValue(e.getWageTableName());
                cells.get(rowStart, COLUMN_START + 2).setValue(convertYearMonth(e.getWageHisStartYm()));
                cells.get(rowStart, COLUMN_START + 3).setValue(convertYearMonth(e.getWageHisEndYm()));

                cells.get(rowStart, COLUMN_START + 4).setValue(getFixedValue1(e, dataName));
                cells.get(rowStart, COLUMN_START + 5).setValue(e.getElementSet() == 0 ? "" : getFixedValue2(e, dataName));
                cells.get(rowStart, COLUMN_START + 6).setValue(e.getElementSet() == 2 || e.getElementSet() == 4 ? getFixedValue3(e, dataName) : "");

                cells.get(rowStart, COLUMN_START + 7).setValue(getR2_8(e));
                cells.get(rowStart, COLUMN_START + 8).setValue(e.getElementSet() == 0 ? "" : getR2_9(e));
                cells.get(rowStart, COLUMN_START + 9).setValue(e.getElementSet() == 2 || e.getElementSet() == 4 ? getR2_10(e) : "");

                cells.get(rowStart, COLUMN_START + 10).setValue(e.getPayAmount() != null ? e.getPayAmount() : "");
                cells.get(rowStart, COLUMN_START + 11).setValue(e.getElementSet() == 3 ? enumQualificationPaymentMethod(Integer.parseInt(e.getPayMethod())) : "");
                rowStart++;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
