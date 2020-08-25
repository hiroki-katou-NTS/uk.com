package nts.uk.file.at.infra.bento;

import com.aspose.cells.*;
import nts.arc.error.BusinessException;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.*;
import nts.uk.file.at.app.export.bento.CreateOrderInfoGenerator;
import nts.uk.file.at.app.export.bento.OrderInfoExportData;
import nts.uk.file.at.app.export.bento.OutputExtension;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AposeCreateOrderInfo extends AsposeCellsReportGenerator implements CreateOrderInfoGenerator {

    private static final String TEMPLATE_TOTAL_FILE = "report/KMR004_Total.xlsx";
    private static final String TEMPLATE_DETAIL_FILE = "report/KMR004_Detail.xlsx";
    private static final String FILE_NAME = "KMR004予約確認一覧_";
    private static final String TOTAL_BOOK = "合計書";
    private static final String STATEMENT_SLIP = "明細票";
    private static final String REPORT_FILE_EXTENSION_PDF = ".pdf";
    private static final String REPORT_FILE_EXTENSION_EXCEL = ".xlsx";


    private static final String PERIOD_LABEL = TextResource.localize("KMR004_27");
    private static final String TOTAL_LABEL = TextResource.localize("KMR004_32");

    /**
     * Total
     */
    private static final String TOTAL_ORDINAL = TextResource.localize("KMR004_28");
    private static final String TOTAL_BENTO_NAME = TextResource.localize("KMR004_29");
    private static final String TOTAL_AMOUNT = TextResource.localize("KMR004_30");
    private static final String TOTAL_QUANTITY = TextResource.localize("KMR004_31");

    /**
     * Detail
     */

    private static final String EMP_CODE = TextResource.localize("KMR004_33");
    private static final String EMP_NAME = TextResource.localize("KMR004_34");
    private static final String QUANTITY = TextResource.localize("KMR004_35");
    private static final String IS_CHECK = TextResource.localize("KMR004_36");

    //Work Place
    private static final String WOKR_PLACE_LABEL = TextResource.localize("KMR004_38");
    ////Work Location
    private static final String WOKR_LOCATION_LABEL = TextResource.localize("KMR004_38");
    private static final String NUMBER_FORMAT = "\"¥\"#,##0;[RED]\"¥\"-#,##0";
    private static final String FONT_NAME = "ＭＳ ゴシック";

    private static final int ROW_PER_PAGE_TOTAL = 40;
    private static final int ROW_PER_PAGE_DETAIL = 55;

    private static Style headerTotal;
    private static Style bodyTotal1;
    private static Style bodyTotal2;
    private static Style bodyTotalNumber1;
    private static Style bodyTotalNumber2;
    private static Style footer;
    private static Style footerNumber;
    private static Style footerLabel;

    static {
        headerTotal = new Style();
        headerTotal.setBorder(BorderType.TOP_BORDER, CellBorderType.MEDIUM, Color.getBlack());
        headerTotal.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.MEDIUM, Color.getBlack());
        headerTotal.setForegroundColor(Color.getBlue());
        headerTotal.setHorizontalAlignment(TextAlignmentType.CENTER);
        headerTotal.setVerticalAlignment(TextAlignmentType.CENTER);
        Font headerTotalFont = headerTotal.getFont();
        headerTotalFont.setSize(9);
        headerTotalFont.setName(FONT_NAME);
        headerTotal.update();
        //headerTotal.set

        bodyTotal1 = new Style();
        bodyTotal1.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
        Font bodyTotalFont1 = bodyTotal1.getFont();
        bodyTotalFont1.setSize(9);
        bodyTotalFont1.setName(FONT_NAME);
        bodyTotal1.update();

        bodyTotal2 = new Style();
        bodyTotal2.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
        Font bodyTotalFont2 = bodyTotal2.getFont();
        bodyTotalFont2.setSize(9);
        bodyTotalFont2.setName(FONT_NAME);
        bodyTotal2.update();

        bodyTotalNumber1 = new Style();
        bodyTotalNumber1.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
        bodyTotalNumber1.setCustom(NUMBER_FORMAT);
        Font bodyTotalNumberFont1 = bodyTotalNumber1.getFont();
        bodyTotalNumberFont1.setSize(9);
        bodyTotalNumberFont1.setName(FONT_NAME);
        bodyTotalNumber1.update();

        bodyTotalNumber2 = new Style();
        bodyTotalNumber2.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
        bodyTotalNumber2.setCustom(NUMBER_FORMAT);
        Font bodyTotalNumberFont2 = bodyTotalNumber2.getFont();
        bodyTotalNumberFont2.setSize(9);
        bodyTotalNumberFont2.setName(FONT_NAME);
        bodyTotalNumber2.update();

        footer = new Style();
        footer.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
        footer.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.MEDIUM, Color.getBlack());
        Font footerFont = footer.getFont();
        footerFont.setSize(9);
        footerFont.setName(FONT_NAME);
        footer.update();

        footerNumber = new Style();
        footerNumber.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
        footerNumber.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.MEDIUM, Color.getBlack());
        footerNumber.setCustom(NUMBER_FORMAT);
        Font footerNumberFont = footerNumber.getFont();
        footerNumberFont.setSize(9);
        footerNumberFont.setName(FONT_NAME);
        footerNumber.update();

        footerLabel = new Style();
        footerLabel.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
        footerLabel.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.MEDIUM, Color.getBlack());
        footerLabel.setHorizontalAlignment(TextAlignmentType.RIGHT);
        Font footerLabelFont = footerLabel.getFont();
        footerLabelFont.setSize(9);
        footerLabelFont.setName(FONT_NAME);
        footerLabel.update();
    }

    private static Style headerDetail1;
    private static Style headerDetail2;
    private static Style headerDetailCheck2;
    private static Style bodyDetailEmp1;
    private static Style bodyDetailQuantity1;
    private static Style bodyDetailCheck1;
    private static Style bodyDetailEmp2;
    private static Style bodyDetailQuantity2;
    private static Style bodyDetailCheck2;

    static {
        headerDetail1 = new Style();
        headerDetail1.setBorder(BorderType.TOP_BORDER, CellBorderType.MEDIUM, Color.getBlack());
        headerDetail1.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
        Font headerDetail1Font1 = headerDetail1.getFont();
        headerDetail1Font1.setSize(9);
        headerDetail1Font1.setName(FONT_NAME);
        headerDetail1.update();

        headerDetail2 = new Style();
        headerDetail2.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.MEDIUM, Color.getBlack());
        headerDetail2.setBorder(BorderType.RIGHT_BORDER, CellBorderType.HAIR, Color.getBlack());
        Font headerDetail1Font2 = headerDetail2.getFont();
        headerDetail1Font2.setSize(9);
        headerDetail1Font2.setName(FONT_NAME);
        headerDetail2.update();

        headerDetailCheck2 = new Style();
        headerDetailCheck2.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.MEDIUM, Color.getBlack());
        headerDetailCheck2.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
        Font headerDetailCheckFont2 = headerDetailCheck2.getFont();
        headerDetailCheckFont2.setSize(9);
        headerDetailCheckFont2.setName(FONT_NAME);
        headerDetailCheck2.update();

        bodyDetailEmp1 = new Style();
        bodyDetailEmp1.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
        bodyDetailEmp1.setBorder(BorderType.RIGHT_BORDER, CellBorderType.HAIR, Color.getBlack());
        Font bodyDetailEmpFont1 = bodyDetailEmp1.getFont();
        bodyDetailEmpFont1.setSize(9);
        bodyDetailEmpFont1.setName(FONT_NAME);
        bodyDetailEmp1.update();

        bodyDetailQuantity1 = new Style();
        bodyDetailQuantity1.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
        bodyDetailQuantity1.setBorder(BorderType.RIGHT_BORDER, CellBorderType.HAIR, Color.getBlack());
        bodyDetailQuantity1.setHorizontalAlignment(TextAlignmentType.RIGHT);
        Font bodyDetailQuantityFont1 = bodyDetailQuantity1.getFont();
        bodyDetailQuantityFont1.setSize(9);
        bodyDetailQuantityFont1.setName(FONT_NAME);
        bodyDetailQuantity1.update();

        bodyDetailCheck1 = new Style();
        bodyDetailCheck1.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
        bodyDetailCheck1.setBorder(BorderType.RIGHT_BORDER, CellBorderType.MEDIUM, Color.getBlack());
        Font bodyDetailCheckFont1 = bodyDetailCheck1.getFont();
        bodyDetailCheckFont1.setSize(9);
        bodyDetailCheckFont1.setName(FONT_NAME);
        bodyDetailCheck1.update();

        bodyDetailEmp2 = new Style();
        bodyDetailEmp2.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
        bodyDetailEmp2.setBorder(BorderType.RIGHT_BORDER, CellBorderType.HAIR, Color.getBlack());
        Font bodyDetailEmpFont2 = bodyDetailEmp2.getFont();
        bodyDetailEmpFont2.setSize(9);
        bodyDetailEmpFont2.setName(FONT_NAME);
        bodyDetailEmp2.update();

        bodyDetailQuantity2 = new Style();
        bodyDetailQuantity2.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
        bodyDetailQuantity2.setBorder(BorderType.RIGHT_BORDER, CellBorderType.HAIR, Color.getBlack());
        bodyDetailQuantity2.setHorizontalAlignment(TextAlignmentType.RIGHT);
        Font bodyDetailQuantityFont2 = bodyDetailQuantity2.getFont();
        bodyDetailQuantityFont2.setSize(9);
        bodyDetailQuantityFont2.setName(FONT_NAME);
        bodyDetailQuantity2.update();

        bodyDetailCheck2 = new Style();
        bodyDetailCheck2.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
        bodyDetailCheck2.setBorder(BorderType.RIGHT_BORDER, CellBorderType.MEDIUM, Color.getBlack());
        Font bodyDetailCheckFont2 = bodyDetailCheck2.getFont();
        bodyDetailCheckFont2.setSize(9);
        bodyDetailCheckFont2.setName(FONT_NAME);
        bodyDetailCheck2.update();
    }


    @Override
    public void generate(FileGeneratorContext generatorContext, OrderInfoExportData data) {
        if(data.getOutputExt() == null)
            throw new BusinessException("Msg_1641");
        if (!CollectionUtil.isEmpty(data.getOrderInfoDto().getTotalOrderInfoDtoList()))
            handleTotalTemplate(generatorContext, data);
        if(!CollectionUtil.isEmpty(data.getOrderInfoDto().getDetailOrderInfoDtoList()))
            handleDetailTemplate(generatorContext, data);
    }

    private void handleTotalTemplate(FileGeneratorContext generatorContext, OrderInfoExportData data) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_TOTAL_FILE)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            printDataTotalFormat(worksheets, data);
            worksheets.setActiveSheetIndex(0);
            reportContext.processDesigner();
            if(data.getOutputExt().equals(OutputExtension.PDF))
                reportContext.saveAsPdf(this.createNewFile(generatorContext,
                    FILE_NAME + TOTAL_BOOK + GeneralDateTime.now().toString("yyyyMMddHHmmss") + REPORT_FILE_EXTENSION_PDF));
            else if(data.getOutputExt().equals(OutputExtension.EXCEL))
                reportContext.saveAsExcel(this.createNewFile(generatorContext,
                        FILE_NAME + TOTAL_BOOK + GeneralDateTime.now().toString("yyyyMMddHHmmss") + REPORT_FILE_EXTENSION_EXCEL));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void handleDetailTemplate(FileGeneratorContext generatorContext, OrderInfoExportData data) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_DETAIL_FILE)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            printDataDetailFormat(worksheets, data);
            worksheets.setActiveSheetIndex(0);
            reportContext.processDesigner();
            if(data.getOutputExt().equals(OutputExtension.PDF))
                reportContext.saveAsPdf(this.createNewFile(generatorContext,
                        FILE_NAME + STATEMENT_SLIP + GeneralDateTime.now().toString("yyyyMMddHHmmss") + REPORT_FILE_EXTENSION_PDF));
            else if(data.getOutputExt().equals(OutputExtension.EXCEL))
                reportContext.saveAsExcel(this.createNewFile(generatorContext,
                        FILE_NAME + STATEMENT_SLIP + GeneralDateTime.now().toString("yyyyMMddHHmmss") + REPORT_FILE_EXTENSION_EXCEL));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void settingPage(Worksheet worksheet, String companyName, String Tittle) {
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setPaperSize(PaperSizeType.PAPER_A_4);
        pageSetup.setOrientation(PageOrientationType.PORTRAIT);
        pageSetup.setHeader(0, "&\"ＭＳ ゴシック\"&10 " + companyName);
        pageSetup.setHeader(1, "&\"ＭＳ ゴシック\"&16 " + Tittle);
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d  H:mm:ss", Locale.JAPAN);
        String currentFormattedDate = LocalDateTime.now().format(fullDateTimeFormatter);
        pageSetup.setHeader(2, "&\"ＭＳ ゴシック\"&10 " + currentFormattedDate+"\npage&P");
        pageSetup.setFitToPagesTall(0);
        pageSetup.setFitToPagesWide(1);
    }

    private void printDataTotalFormat(WorksheetCollection worksheets, OrderInfoExportData exportData) {
        try {
            OrderInfoDto orderInfoDto = exportData.getOrderInfoDto();
            Worksheet worksheet = worksheets.get(0);
            Worksheet tempSheet = worksheets.get(1);
            settingPage(worksheet, orderInfoDto.getCompanyName(), TOTAL_BOOK);
            Cells cells = worksheet.getCells();
            StringBuilder printArea = new StringBuilder();
            printArea.append("A0:");
            int startIndex = 5;
            printHeadData(cells, exportData);
            List<TotalOrderInfoDto> dataPrint = orderInfoDto.getTotalOrderInfoDtoList();
            for (TotalOrderInfoDto dataRow : dataPrint)
                startIndex = handleBodyTotalFormat(worksheet, dataRow, startIndex, cells, tempSheet);

            worksheet.getVerticalPageBreaks().add(10);
            printArea.append("J"+(startIndex - 2));
            worksheets.removeAt(1);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void printHeadData(Cells cells, OrderInfoExportData exportData){
        if(!CollectionUtil.isEmpty(exportData.getOrderInfoDto().getTotalOrderInfoDtoList())){
            printOutputExt(cells, exportData, 0,2,5);
        }else if(!CollectionUtil.isEmpty(exportData.getOrderInfoDto().getDetailOrderInfoDtoList())){
            printOutputExt(cells, exportData, 0,1,3);
        }
    }

    private void printOutputExt(Cells cells, OrderInfoExportData exportData,
                                int labelRow, int startDataRow, int endDataRow){
        List<PlaceOfWorkInfoDto> placeOfWorkInfoDtos = CollectionUtil.isEmpty(exportData.getOrderInfoDto().getDetailOrderInfoDtoList())
                ? exportData.getOrderInfoDto().getTotalOrderInfoDtoList().get(0).getPlaceOfWorkInfoDto()
                : exportData.getOrderInfoDto().getDetailOrderInfoDtoList().get(0).getPlaceOfWorkInfoDtos();
        if (exportData.isWorkLocationExport()) {
            cells.get(0, labelRow).setValue(WOKR_LOCATION_LABEL);
            cells.get(0, startDataRow).setValue(placeOfWorkInfoDtos.get(0).getPlaceCode());
            cells.get(0, endDataRow).setValue(placeOfWorkInfoDtos.get(0).getPlaceName());
        } else {
            cells.get(0, labelRow).setValue(WOKR_PLACE_LABEL);
            cells.get(0, startDataRow).setValue(placeOfWorkInfoDtos.get(0).getPlaceCode());
            cells.get(0, endDataRow).setValue(placeOfWorkInfoDtos.get(0).getPlaceName());
            cells.get(1, startDataRow).setValue(placeOfWorkInfoDtos.get(placeOfWorkInfoDtos.size()-1).getPlaceCode());
            cells.get(1, endDataRow).setValue(placeOfWorkInfoDtos.get(placeOfWorkInfoDtos.size()-1).getPlaceName());
        }
        cells.get(2, labelRow).setValue(PERIOD_LABEL);
        cells.get(2, startDataRow).setValue(exportData.getReservationTimeZone());
    }

    private int handleBodyTotalFormat(Worksheet worksheet, TotalOrderInfoDto dataRow, int startIndex, Cells cells,
                                      Worksheet template) {
        int total = 0;
        double height = cells.getRowHeight(0);

        //copy Header
        copyRowFromTemplateSheet(cells,template,0,startIndex - 1);

        cells.get(startIndex - 1, 3).setValue(TOTAL_ORDINAL);
        cells.get(startIndex - 1, 5).setValue(TOTAL_BENTO_NAME);
        cells.get(startIndex - 1, 6).setValue(TOTAL_AMOUNT);
        cells.get(startIndex - 1, 7).setValue(TOTAL_QUANTITY);

        if (dataRow.getBentoTotalDto().size() == 0){
            //copy Footer
            copyRowFromTemplateSheet(cells,template,0,startIndex + 1);

            cells.get(startIndex + dataRow.getBentoTotalDto().size(), 5).setValue(TOTAL_LABEL);
            startIndex += 4;
            breakPage("J", startIndex - 1, worksheet);
            return startIndex;
        }

        for (int i = 0; i < dataRow.getBentoTotalDto().size(); ++i) {
            BentoTotalDto bentoTotalDto = dataRow.getBentoTotalDto().get(i);
            total += setBodyDataTotalFormat(cells, startIndex, i, bentoTotalDto, height, template);
        }
        startIndex += dataRow.getBentoTotalDto().size();
        copyRowFromTemplateSheet(cells, template, 3, startIndex);
        cells.get(startIndex, 5).setValue(TOTAL_LABEL);
        cells.get(startIndex, 6).setValue(total);
        startIndex += 4 - 1 ;
        // page break
        breakPage("J", startIndex - 1, worksheet);
        return startIndex;
    }

    private int setBodyDataTotalFormat(Cells cells, int startIndex, int index, BentoTotalDto bentoTotalDto, double height, Worksheet template) {
        if (index % 2 == 0){
            copyRowFromTemplateSheet(cells,template, 2, startIndex + index);
        }
        else{
            copyRowFromTemplateSheet(cells,template, 1, startIndex + index);
        }

        int amount = bentoTotalDto.getAmount() * bentoTotalDto.getQuantity();
        cells.get(startIndex + index, 3).setValue(index + 1);
        cells.get(startIndex + index, 5).setValue(bentoTotalDto.getName());
        cells.get(startIndex + index, 6).setValue(amount);
        cells.get(startIndex + index, 7).setValue(bentoTotalDto.getQuantity());
        cells.get(startIndex + index, 8).setValue(bentoTotalDto.getUnit());

        return amount;
    }

    private void printDataDetailFormat(WorksheetCollection worksheets, OrderInfoExportData orderInfoExportData) {
        try {
            OrderInfoDto orderInfoDto = orderInfoExportData.getOrderInfoDto();
            Worksheet worksheet = worksheets.get(0);
            Worksheet tempSheet = worksheets.get(1);
            settingPage(worksheet, orderInfoDto.getCompanyName(), STATEMENT_SLIP);
            Cells cells = worksheet.getCells();
            int startIndex = 6;
            StringBuilder printArea = new StringBuilder();
            printArea.append("A0:");
            printHeadData(cells, orderInfoExportData);
            for (DetailOrderInfoDto i : orderInfoDto.getDetailOrderInfoDtoList())
                for (BentoReservedInfoDto item : i.getBentoReservedInfoDtos())
                    startIndex = handleBodyDetailFormat(worksheet, item, startIndex, cells, orderInfoExportData.isBreakPage(), tempSheet, orderInfoExportData.getOutputExt());
            printArea.append("M"+(startIndex-1));
            worksheet.getVerticalPageBreaks().add(12);
            worksheets.removeAt(1);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    private int handleBodyDetailFormat(Worksheet worksheet, BentoReservedInfoDto bentoReservedInfoDto, int startIndex,
                                       Cells cells, boolean isPageBreak, Worksheet tempSheet, OutputExtension outputExtension) {
        setHeaderDetail(cells, startIndex, bentoReservedInfoDto.getBentoName(), tempSheet);
        List<BentoReservationInfoForEmpDto> bodyData = bentoReservedInfoDto.getBentoReservationInfoForEmpList();
        int total = 0;
        if(CollectionUtil.isEmpty(bodyData)){
            copyRowFromTemplateSheet(cells, tempSheet, 3, startIndex);
            cells.deleteRow(startIndex + 1);
            setFooterDataDetailFormat(cells,tempSheet, startIndex + 1, total,"");
            startIndex += 5;
            if(isPageBreak)
                breakPage("M", startIndex - 2, worksheet);
        }
        else {
            int row = bodyData.size() / 3;
            int mod = bodyData.size() % 3;
            for (int i = 0; i < row; i++) {
                if (i % 2 == 0)
                    copyRowFromTemplateSheet(cells, tempSheet, 3, startIndex + i);
                else
                    copyRowFromTemplateSheet(cells, tempSheet, 4, startIndex + i);
                for (int j = 0; j < 3; ++j)
                    total += setBodyDataDetailFormat(cells, startIndex + i, j * 4, bodyData.get(i * 3 + j));
                if(i % ROW_PER_PAGE_DETAIL == 0 & i > 0){
                    breakPage("M", startIndex + i + 1, worksheet);
                    if(OutputExtension.PDF.equals(outputExtension)){
                        copyRowFromTemplateSheet(cells, tempSheet, 9,startIndex + i);
                        copyRowFromTemplateSheet(cells, worksheet, startIndex - 2, startIndex + i + 1);
                        copyRowFromTemplateSheet(cells, worksheet, startIndex - 1, startIndex + i + 2);
                        startIndex += 2;
                    }
                }
            }
            int modIndex = row * 3;
            startIndex += row ;
            if(mod > 0){
                if (modIndex % 2 == 0)
                    copyRowFromTemplateSheet(cells, tempSheet, 3, startIndex);
                else
                    copyRowFromTemplateSheet(cells, tempSheet, 4, startIndex);
                for (int i = 0; i < mod; ++i) {
                    total += setBodyDataDetailFormat(cells, startIndex + i, i * 4, bodyData.get(modIndex + i));
                }
                startIndex += 1;
            }

            setFooterDataDetailFormat(cells,tempSheet, startIndex, total, bentoReservedInfoDto.getUnit());
            startIndex += 5;
        }

        if(isPageBreak)
            breakPage("M", startIndex - 2, worksheet);

        return startIndex;
    }

    private void setHeaderDetail(Cells cells, int startIndex, String bentoName, Worksheet tempSheet){
        //head line 1
        copyRowFromTemplateSheet(cells, tempSheet, 1, startIndex - 2);
        cells.get(startIndex - 2, 0).setValue(bentoName);
        //head line 2
        copyRowFromTemplateSheet(cells, tempSheet, 2, startIndex - 1);
        for (int j = 0; j < 3; ++j) {
            //head line 1
            cells.get(startIndex - 1, 0 + j * 4).setValue(EMP_CODE);
            cells.get(startIndex - 1, 1 + j * 4).setValue(EMP_NAME);
            cells.get(startIndex - 1, 2 + j * 4).setValue(QUANTITY);
            cells.get(startIndex - 1, 3 + j * 4).setValue(IS_CHECK);
        }
    }

    private int setBodyDataDetailFormat(Cells cells, int startIndex, int colStart, BentoReservationInfoForEmpDto data) {
        cells.get(startIndex,colStart).setValue(data.getEmpCode());
        cells.get(startIndex,colStart + 1).setValue(data.getEmpName());
        cells.get(startIndex,colStart + 2).setValue(data.getQuantity());
        return data.getQuantity();
    }

    private void setFooterDataDetailFormat(Cells cells,Worksheet template, int row, int total, String unit){
        //style
        copyRowFromTemplateSheet(cells, template, 5,row);
        copyRowFromTemplateSheet(cells, template, 6,row+1);
        //data
        cells.get(row,9).setValue(TOTAL_LABEL);
        cells.get(row,10).setValue(total);
        cells.get(row,11).setValue(unit);
    }

    private void breakPage(String col, int row, Worksheet worksheet){
        HorizontalPageBreakCollection pageBreaksH = worksheet.getHorizontalPageBreaks();
        pageBreaksH.add(col + row);
    }

    private void copyRowFromTemplateSheet(Cells cells, Worksheet template, int from, int to){
        try {
            cells.copyRow(template.getCells(),from,to);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
