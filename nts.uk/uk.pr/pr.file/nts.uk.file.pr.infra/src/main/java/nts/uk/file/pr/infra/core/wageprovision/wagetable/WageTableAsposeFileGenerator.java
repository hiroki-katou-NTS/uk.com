package nts.uk.file.pr.infra.core.wageprovision.wagetable;

import com.aspose.cells.*;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementSetting;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementType;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.MasterNumericAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationPaymentMethod;
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

    private static final String REPORT_FILE_NAME = "QMM016賃金テーブルの登録.xlsx";

    private static final int COLUMN_START = 1;

    private static final String TITLE = "賃金テーブルの登録";

    private static final String SHEET_NAME = "マスタリスト";

    @Inject
    private CompanyAdapter company;

    @Override
    public void generate(FileGeneratorContext fileContext, List<WageTablelData> exportData, List<ItemDataNameExport> dataName, List<ItemDataNameExport> dataNameMaster) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {

            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            worksheets.get(0).setName(SHEET_NAME);
            settingPage(worksheets.get(0), this.company.getCurrentCompany().map(CompanyInfor::getCompanyName).orElse(""));
            fillData(worksheets, exportData, dataName, dataNameMaster);
            worksheets.setActiveSheetIndex(0);
            reportContext.processDesigner();
            reportContext.saveAsExcel(this.createNewFile(fileContext, this.getReportName(REPORT_FILE_NAME)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* get list item name*/
    private String getItemName(List<ItemDataNameExport> data, String codeOp) {
        Optional<ItemDataNameExport> dataName = data.stream().filter(e -> e.getCode().equals(codeOp)).findFirst();
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

    private String getNameMaster(List<ItemDataNameExport> dataNameMaster, String type, String masterCd){
        Optional<ItemDataNameExport> item = dataNameMaster.stream().filter(i -> masterCd.equals(i.getCode().trim()) && type.equals(i.getType())).findFirst();
        return item.isPresent() ? item.get().getName() : "";
    }

    /*QualificationPaymentMethod*/
    private String enumQualificationPaymentMethod(WageTablelData e, int index) {
        if(e.getQualificationName().isEmpty()) {
            return "";
        }

        return TextResource.localize(EnumAdaptor.valueOf(index, QualificationPaymentMethod.class).nameId);
    }

    private String getFixedValue1(WageTablelData e, List<ItemDataNameExport> dataName) {
        if(e.getElementSet() == ElementSetting.QUALIFICATION.value) {
            return TextResource.localize("QMM016_49");
        }
        if(e.getElementSet() == ElementSetting.FINE_WORK.value) {
            return TextResource.localize("QMM016_53");
        }
        if(!e.getFixElement1().isEmpty()) {
            return enumElementType(e.getFixElement1());
        }
        if(!e.getOptAddElement1().isEmpty()) {
            return getItemName(dataName, e.getOptAddElement1());
        }
        return "";
    }

    private String getFixedValue2(WageTablelData e, List<ItemDataNameExport> dataName) {
        if(e.getElementSet() == ElementSetting.QUALIFICATION.value) {
            return TextResource.localize("QMM016_50");
        }
        if(e.getElementSet() == ElementSetting.FINE_WORK.value) {
            return "欠勤日数";
        }
        if(!e.getFixElement2().isEmpty()) {
            return enumElementType(e.getFixElement2());
        }
        if(!e.getOptAddElement2().isEmpty()) {
            return getItemName(dataName, e.getOptAddElement2());
        }
        return "";
    }

    private String getFixedValue3(WageTablelData e, List<ItemDataNameExport> dataName) {
        if(e.getElementSet() == ElementSetting.FINE_WORK.value) {
            return "遅刻・早退回数";
        }
        if(!e.getFixElement3().isEmpty()) {
            return enumElementType(e.getFixElement3());
        }
        if(!e.getOptAddElement3().isEmpty()) {
            return getItemName(dataName, e.getOptAddElement3());
        }
        return "";
    }

    private void chageValue(WageTablelData e){
        String tempFix = e.getFixElement1();
        e.setFixElement1(e.getFixElement3());
        e.setFixElement3(e.getFixElement2());
        e.setFixElement2(tempFix);

        String tempOpt = e.getOptAddElement1();
        e.setOptAddElement1(e.getOptAddElement3());
        e.setOptAddElement3(e.getOptAddElement2());
        e.setOptAddElement2(tempOpt);

        int tempNumAtr = e.getMasterNumAtr1();
        e.setMasterNumAtr1(e.getMasterNumAtr3());
        e.setMasterNumAtr3(e.getMasterNumAtr2());
        e.setMasterNumAtr2(tempNumAtr);

        String tempLower = e.getLowerLimit1();
        e.setLowerLimit1(e.getLowerLimit3());
        e.setLowerLimit3(e.getLowerLimit2());
        e.setLowerLimit2(tempLower);

        String tempUper = e.getUpperLimit1();
        e.setUpperLimit1(e.getUpperLimit3());
        e.setUpperLimit3(e.getUpperLimit2());
        e.setUpperLimit2(tempUper);

        String tempMasterCd = e.getMasterCd1();
        e.setMasterCd1(e.getMasterCd3());
        e.setMasterCd3(e.getMasterCd2());
        e.setMasterCd2(tempMasterCd);

        String tempFramNumber = e.getFrameNumber1();
        e.setFrameNumber1(e.getFrameNumber3());
        e.setFrameNumber3(e.getFrameNumber2());
        e.setFrameNumber2(tempFramNumber);
    }

    private String getR2_8(List<ItemDataNameExport> data, WageTablelData e) {
        if(ElementType.FINE_WORK.value.equals(e.getFixElement1()) || ElementType.FINE_WORK.value.equals(e.getOptAddElement1())){
            return e.getMasterCd1();
        }
        if(e.getElementSet() == ElementSetting.QUALIFICATION.value) {
            return  "".equals(e.getQualifiGroupName()) ? "".equals(e.getQualificationName()) ? "" : "なし" : e.getQualifiGroupName();
        }
        if(e.getMasterNumAtr1() == MasterNumericAtr.MASTER_ITEM.value) {
            return getNameMaster(data, e.getFixElement1() , e.getMasterCd1().trim());
        }
        if(e.getElementSet() == ElementSetting.ONE_DIMENSION.value && e.getFixElement1().startsWith("M")) {
            return enumElementType(e.getFixElement1());
        }
        if(!e.getLowerLimit1().isEmpty()) {
            return e.getLowerLimit1() + TextResource.localize("QMM016_31") + e.getUpperLimit1();
        }
        return "";
    }

    private String getR2_9(List<ItemDataNameExport> data, WageTablelData e) {
        if(ElementType.FINE_WORK.value.equals(e.getFixElement2()) || ElementType.FINE_WORK.value.equals(e.getOptAddElement2())){
            return e.getMasterCd2();
        }
        if(e.getElementSet() == ElementSetting.QUALIFICATION.value) {
            return e.getQualificationName();
        }
        if(e.getMasterNumAtr2() == MasterNumericAtr.MASTER_ITEM.value) {
            return getNameMaster(data, e.getFixElement2() , e.getMasterCd2().trim());
        }
        if(e.getFixElement2().startsWith("M")) {
            return enumElementType(e.getFixElement2());
        }
        if(!e.getLowerLimit2().isEmpty()) {
            return e.getLowerLimit2() + TextResource.localize("QMM016_31") + e.getUpperLimit2();
        }
        return "";
    }

    private String getR2_10(List<ItemDataNameExport> data, WageTablelData e) {
        if(ElementType.FINE_WORK.value.equals(e.getFixElement3()) || ElementType.FINE_WORK.value.equals(e.getOptAddElement3())){
            return e.getMasterCd3();
        }
        if(e.getMasterNumAtr3() == MasterNumericAtr.MASTER_ITEM.value) {
            return getNameMaster(data, e.getFixElement3() , e.getMasterCd3().trim());
        }
        if(e.getFixElement3().startsWith("M")) {
            return enumElementType(e.getFixElement3());
        }
        if(!e.getLowerLimit3().isEmpty()) {
            return e.getLowerLimit3() + TextResource.localize("QMM016_31") + e.getUpperLimit3();
        }
        return "";
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

    private void fillData(WorksheetCollection worksheets, List<WageTablelData> data, List<ItemDataNameExport> dataName, List<ItemDataNameExport> dataMasterName) {
        try {
            int rowStart = 3;
            int lineCopy = 3;
            Worksheet sheet = worksheets.get(0);
            Cells cells = sheet.getCells();
            for (int i = 0; i < data.size(); i++) {
                if(i % 2 == 0) {
                    cells.copyRows(cells, rowStart, rowStart + lineCopy - 1, lineCopy);
                }
                if(i == data.size() - 1) {
                    cells.deleteRows(rowStart, data.size() % 2 == 0 ? 3 : 4);
                }
                WageTablelData e = data.get(i);
                if(e.getElementSet() == ElementSetting.THREE_DIMENSION.value || e.getElementSet() == ElementSetting.FINE_WORK.value) {
                    chageValue(e);
                }
                cells.get(rowStart, COLUMN_START).setValue(e.getWageTableCode());
                cells.get(rowStart, COLUMN_START + 1).setValue(e.getWageTableName());
                cells.get(rowStart, COLUMN_START + 2).setValue(convertYearMonth(e.getWageHisStartYm()));
                cells.get(rowStart, COLUMN_START + 3).setValue(convertYearMonth(e.getWageHisEndYm()));
                cells.get(rowStart, COLUMN_START + 4).setValue(getFixedValue1(e, dataName));
                cells.get(rowStart, COLUMN_START + 5).setValue(e.getElementSet() == ElementSetting.ONE_DIMENSION.value ? "" : getFixedValue2(e, dataName));
                cells.get(rowStart, COLUMN_START + 6).setValue(e.getElementSet() == ElementSetting.THREE_DIMENSION.value
                        || e.getElementSet() == ElementSetting.FINE_WORK.value ? getFixedValue3(e, dataName) : "");
                cells.get(rowStart, COLUMN_START + 7).setValue(getR2_8(dataMasterName, e));
                cells.get(rowStart, COLUMN_START + 8).setValue(e.getElementSet() == ElementSetting.ONE_DIMENSION.value ? "" : getR2_9(dataMasterName, e));
                cells.get(rowStart, COLUMN_START + 9).setValue(e.getElementSet() == ElementSetting.THREE_DIMENSION.value
                        || e.getElementSet() == ElementSetting.FINE_WORK.value ? getR2_10(dataMasterName, e) : "");
                cells.get(rowStart, COLUMN_START + 10).setValue(e.getPayAmount() != null ? e.getPayAmount() : "");
                cells.get(rowStart, COLUMN_START + 11).setValue(e.getElementSet() == ElementSetting.QUALIFICATION.value ? enumQualificationPaymentMethod(e,Integer.parseInt(e.getPayMethod())) : "");
                rowStart++;
            }
            if(data.size() == 0) {
                cells.deleteRows(rowStart, 2);
            }
            if(data.size() > 1 && data.size() % 2 == 0) {
                int totalColumn = 12;
                for(int column = COLUMN_START; column < totalColumn +  COLUMN_START; column++) {
                    Style style = worksheets.get(0).getCells().get(rowStart - 1, column).getStyle();
                    style.setForegroundColor(Color.fromArgb(216,228, 188));
                    worksheets.get(0).getCells().get(rowStart - 1, column).setStyle(style);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
