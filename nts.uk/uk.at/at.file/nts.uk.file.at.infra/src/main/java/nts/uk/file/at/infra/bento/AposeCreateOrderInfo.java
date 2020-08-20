package nts.uk.file.at.infra.bento;

import com.aspose.cells.*;
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
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AposeCreateOrderInfo extends AsposeCellsReportGenerator implements CreateOrderInfoGenerator {

    private static final String TEMPLATE_TOTAL_FILE = "report/KMR004_Temp_Total.xlsx";
    private static final String TEMPLATE_DETAIL_FILE = "report/KMR004_Template_Detail.xlsx";
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
//    private static final String EMP_CODE = "社員コード";
//    private static final String EMP_NAME = "社員名";
//    private static final String QUANTITY = "数量";
//    private static final String IS_CHECK = "チェック";
    private static final String NUMBER_FORMAT = "\"¥\"#,##0;[RED]\"¥\"-#,##0";

    private static final String FONT_NAME = "ＭＳ ゴシック";

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
        headerTotal.setBackgroundColor(Color.getBlue());
        headerTotal.setBorder(BorderType.TOP_BORDER, CellBorderType.MEDIUM, Color.getBlack());
        headerTotal.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.MEDIUM, Color.getBlack());
        headerTotal.setForegroundColor(Color.getBlue());
        headerTotal.setHorizontalAlignment(TextAlignmentType.CENTER);
        headerTotal.setVerticalAlignment(TextAlignmentType.CENTER);
        //headerTotal.set

        bodyTotal1 = new Style();
        bodyTotal1.setBackgroundColor(Color.getAliceBlue());
        bodyTotal1.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());

        bodyTotal2 = new Style();
        bodyTotal2.setBackgroundColor(Color.getWhite());
        bodyTotal2.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());

        bodyTotalNumber1 = new Style();
        bodyTotalNumber1.setBackgroundColor(Color.getAliceBlue());
        bodyTotalNumber1.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
        bodyTotalNumber1.setCustom(NUMBER_FORMAT);

        bodyTotalNumber2 = new Style();
        bodyTotalNumber2.setBackgroundColor(Color.getWhite());
        bodyTotalNumber2.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
        bodyTotalNumber2.setCustom(NUMBER_FORMAT);

        footer = new Style();
        footer.setBackgroundColor(Color.getLightBlue());
        footer.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
        footer.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.MEDIUM, Color.getBlack());

        footerNumber = new Style();
        footerNumber.setBackgroundColor(Color.getLightBlue());
        footerNumber.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
        footerNumber.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.MEDIUM, Color.getBlack());
        footerNumber.setCustom(NUMBER_FORMAT);

        footerLabel = new Style();
        footerLabel.setBackgroundColor(Color.getLightBlue());
        footerLabel.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
        footerLabel.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.MEDIUM, Color.getBlack());
        footerLabel.setHorizontalAlignment(TextAlignmentType.RIGHT);
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
        //headerDetail1.setBackgroundColor(Color.fromArgb(51,153,255));
        headerDetail1.setForegroundColor(Color.fromArgb(51,153,255));
        //headerDetail1.setBackgroundArgbColor();
        headerDetail1.setBorder(BorderType.TOP_BORDER, CellBorderType.MEDIUM, Color.getBlack());
        headerDetail1.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
        Font headerDetail1Font1 = headerDetail1.getFont();
        headerDetail1Font1.setSize(9);
        headerDetail1Font1.setName(FONT_NAME);
        headerDetail1.update();

        headerDetail2 = new Style();
        headerDetail2.setBackgroundColor(Color.getBlue());
        headerDetail2.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.MEDIUM, Color.getBlack());
        headerDetail2.setBorder(BorderType.RIGHT_BORDER, CellBorderType.HAIR, Color.getBlack());
        Font headerDetail1Font2 = headerDetail2.getFont();
        headerDetail1Font2.setSize(9);
        headerDetail1Font2.setName(FONT_NAME);
        headerDetail2.update();

        headerDetailCheck2 = new Style();
        headerDetailCheck2.setBackgroundColor(Color.getBlue());
        headerDetailCheck2.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.MEDIUM, Color.getBlack());
        headerDetailCheck2.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
        Font headerDetailCheckFont2 = headerDetailCheck2.getFont();
        headerDetailCheckFont2.setSize(9);
        headerDetailCheckFont2.setName(FONT_NAME);
        headerDetailCheck2.update();

        bodyDetailEmp1 = new Style();
        bodyDetailEmp1.setBackgroundColor(Color.getAliceBlue());
        bodyDetailEmp1.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
        bodyDetailEmp1.setBorder(BorderType.RIGHT_BORDER, CellBorderType.HAIR, Color.getBlack());
        Font bodyDetailEmpFont1 = bodyDetailEmp1.getFont();
        bodyDetailEmpFont1.setSize(9);
        bodyDetailEmpFont1.setName(FONT_NAME);
        bodyDetailEmp1.update();

        bodyDetailQuantity1 = new Style();
        bodyDetailQuantity1.setBackgroundColor(Color.getAliceBlue());
        bodyDetailQuantity1.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
        bodyDetailQuantity1.setBorder(BorderType.RIGHT_BORDER, CellBorderType.HAIR, Color.getBlack());
        bodyDetailQuantity1.setHorizontalAlignment(TextAlignmentType.RIGHT);
        Font bodyDetailQuantityFont1 = bodyDetailQuantity1.getFont();
        bodyDetailQuantityFont1.setSize(9);
        bodyDetailQuantityFont1.setName(FONT_NAME);
        bodyDetailQuantity1.update();

        bodyDetailCheck1 = new Style();
        bodyDetailCheck1.setBackgroundColor(Color.getAliceBlue());
        bodyDetailCheck1.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
        bodyDetailCheck1.setBorder(BorderType.RIGHT_BORDER, CellBorderType.MEDIUM, Color.getBlack());
        Font bodyDetailCheckFont1 = bodyDetailCheck1.getFont();
        bodyDetailCheckFont1.setSize(9);
        bodyDetailCheckFont1.setName(FONT_NAME);
        bodyDetailCheck1.update();

        bodyDetailEmp2 = new Style();
        bodyDetailEmp2.setBackgroundColor(Color.getWhite());
        bodyDetailEmp2.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
        bodyDetailEmp2.setBorder(BorderType.RIGHT_BORDER, CellBorderType.HAIR, Color.getBlack());
        Font bodyDetailEmpFont2 = bodyDetailEmp2.getFont();
        bodyDetailEmpFont2.setSize(9);
        bodyDetailEmpFont2.setName(FONT_NAME);
        bodyDetailEmp2.update();

        bodyDetailQuantity2 = new Style();
        bodyDetailQuantity2.setBackgroundColor(Color.getWhite());
        bodyDetailQuantity2.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
        bodyDetailQuantity2.setBorder(BorderType.RIGHT_BORDER, CellBorderType.HAIR, Color.getBlack());
        bodyDetailQuantity2.setHorizontalAlignment(TextAlignmentType.RIGHT);
        Font bodyDetailQuantityFont2 = bodyDetailQuantity2.getFont();
        bodyDetailQuantityFont2.setSize(9);
        bodyDetailQuantityFont2.setName(FONT_NAME);
        bodyDetailQuantity2.update();

        bodyDetailCheck2 = new Style();
        bodyDetailCheck2.setBackgroundColor(Color.getWhite());
        bodyDetailCheck2.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
        bodyDetailCheck2.setBorder(BorderType.RIGHT_BORDER, CellBorderType.MEDIUM, Color.getBlack());
        Font bodyDetailCheckFont2 = bodyDetailCheck2.getFont();
        bodyDetailCheckFont2.setSize(9);
        bodyDetailCheckFont2.setName(FONT_NAME);
        bodyDetailCheck2.update();
    }


    @Override
    public void generate(FileGeneratorContext generatorContext, OrderInfoExportData data) {
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
                        FILE_NAME + TOTAL_BOOK + GeneralDateTime.now().toString("yyyyMMddHHmmss") + REPORT_FILE_EXTENSION_PDF));
            else if(data.getOutputExt().equals(OutputExtension.EXCEL))
                reportContext.saveAsExcel(this.createNewFile(generatorContext,
                        FILE_NAME + TOTAL_BOOK + GeneralDateTime.now().toString("yyyyMMddHHmmss") + REPORT_FILE_EXTENSION_EXCEL));
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
        pageSetup.setFitToPagesTall(1);
        pageSetup.setFitToPagesWide(1);
    }

    private void printDataTotalFormat(WorksheetCollection worksheets, OrderInfoExportData exportData) {
        try {
            OrderInfoDto orderInfoDto = exportData.getOrderInfoDto();
            Worksheet worksheet = worksheets.get(0);
            settingPage(worksheet, orderInfoDto.getCompanyName(), TOTAL_BOOK);
            Cells cells = worksheet.getCells();
            int startIndex = 5;
            printHeadData(cells, exportData);
            List<TotalOrderInfoDto> dataPrint = orderInfoDto.getTotalOrderInfoDtoList();
            for (TotalOrderInfoDto dataRow : dataPrint)
                startIndex = handleBodyTotalFormat(worksheet, dataRow, startIndex, cells, exportData.isBreakPage());
            worksheet.getVerticalPageBreaks().add(10);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void printHeadData(Cells cells, OrderInfoExportData exportData){
        List<PlaceOfWorkInfoDto> placeOfWorkInfoDtos = CollectionUtil.isEmpty(exportData.getOrderInfoDto().getDetailOrderInfoDtoList())
                ? exportData.getOrderInfoDto().getTotalOrderInfoDtoList().get(0).getPlaceOfWorkInfoDto()
                : exportData.getOrderInfoDto().getDetailOrderInfoDtoList().get(0).getPlaceOfWorkInfoDtos();

        if (exportData.isWorkLocationExport()) {
            cells.get(0, 0).setValue(WOKR_LOCATION_LABEL);
            cells.get(0, 2).setValue(placeOfWorkInfoDtos.get(0).getPlaceCode());
            cells.get(0, 5).setValue(placeOfWorkInfoDtos.get(0).getPlaceName());
        } else {
            cells.get(0, 0).setValue(WOKR_PLACE_LABEL);
            cells.get(0, 2).setValue(placeOfWorkInfoDtos.get(0).getPlaceCode() + placeOfWorkInfoDtos.get(0).getPlaceName());
            cells.get(1, 2).setValue(placeOfWorkInfoDtos.get(placeOfWorkInfoDtos.size()-1).getPlaceCode() + placeOfWorkInfoDtos.get(0).getPlaceName());
        }
        cells.get(0, 2).setValue(PERIOD_LABEL);
        cells.get(0, 4).setValue(exportData.getReservationTimeZone());
    }

    private int handleBodyTotalFormat(Worksheet worksheet, TotalOrderInfoDto dataRow, int startIndex, Cells cells, boolean isPageBreak) {
        int total = 0, endPage = startIndex + 2;
        double height = cells.getRowHeight(0);
        cells.get(startIndex - 1, 3).setValue(TOTAL_ORDINAL);
        cells.get(startIndex - 1, 5).setValue(TOTAL_BENTO_NAME);
        cells.get(startIndex - 1, 6).setValue(TOTAL_AMOUNT);
        cells.get(startIndex - 1, 7).setValue(TOTAL_QUANTITY);
        setHeaderStyleTotalFormat(cells, startIndex - 1, height);

        if (dataRow.getBentoTotalDto().size() == 0){
            cells.deleteRow(startIndex + 1);
            setFooterStyleTotalFormat(cells,startIndex + 1, height);
            cells.get(startIndex + dataRow.getBentoTotalDto().size(), 5).setValue(TOTAL_LABEL);
            startIndex += 4;
            return startIndex;
        }

        if (dataRow.getBentoTotalDto().size() < 2) {
            BentoTotalDto bentoTotalDto = dataRow.getBentoTotalDto().get(0);
            total += setBodyDataTotalFormat(cells, startIndex, 0, bentoTotalDto, height);
            cells.deleteRow(startIndex + 1);
            for (int i = 3; i < 9; ++i)
                cells.get(startIndex + 1, i).setStyle(footer);

            cells.get(startIndex + 1, 6).setStyle(footerNumber);
            cells.get(startIndex + 1, 5).setValue(TOTAL_LABEL);
            cells.get(startIndex + 1, 6).setValue(total);
            endPage = startIndex + 2;
            //page break
            if(isPageBreak){
                HorizontalPageBreakCollection pageBreaksH = worksheet.getHorizontalPageBreaks();
                pageBreaksH.add("J" + (endPage+1));
                VerticalPageBreakCollection pageBreaksV = worksheet.getVerticalPageBreaks();
                pageBreaksV.add("J" + (endPage+1));
            }

            return startIndex;
        }

        for (int i = 0; i < 2; ++i) {
            BentoTotalDto bentoTotalDto = dataRow.getBentoTotalDto().get(i);
            total += setBodyDataTotalFormat(cells, startIndex, i, bentoTotalDto, height);
        }

        for (int i = 2; i < dataRow.getBentoTotalDto().size(); ++i) {
            BentoTotalDto bentoTotalDto = dataRow.getBentoTotalDto().get(i);
            total += setBodyDataTotalFormat(cells, startIndex, i, bentoTotalDto, height);
        }
        setFooterStyleTotalFormat(cells,startIndex + dataRow.getBentoTotalDto().size(),height);
        cells.get(startIndex + dataRow.getBentoTotalDto().size(), 5).setValue(TOTAL_LABEL);
        cells.get(startIndex + dataRow.getBentoTotalDto().size(), 6).setValue(total);
        endPage += dataRow.getBentoTotalDto().size() ;
        // page break
        if(isPageBreak){
            HorizontalPageBreakCollection pageBreaksH = worksheet.getHorizontalPageBreaks();
            pageBreaksH.add("J" + (endPage+1));
            VerticalPageBreakCollection pageBreaksV = worksheet.getVerticalPageBreaks();
            pageBreaksV.add("J" + (endPage+1));
        }

        startIndex += dataRow.getBentoTotalDto().size() - 1 + 4;
        return startIndex;
    }

    private int setBodyDataTotalFormat(Cells cells, int startIndex, int index, BentoTotalDto bentoTotalDto, double height) {
        if (index % 2 == 0)
            setBodyStyleTotalFormat(cells, startIndex + index, bodyTotal2, bodyTotalNumber2, height);
        else
            setBodyStyleTotalFormat(cells, startIndex + index, bodyTotal1, bodyTotalNumber1, height);

        int amount = bentoTotalDto.getAmount() * bentoTotalDto.getQuantity();
        cells.get(startIndex + index, 3).setValue(index + 1);
        cells.get(startIndex + index, 5).setValue(bentoTotalDto.getName());
        cells.get(startIndex + index, 6).setValue(amount);
        cells.get(startIndex + index, 7).setValue(bentoTotalDto.getQuantity());
        cells.get(startIndex + index, 8).setValue(bentoTotalDto.getUnit());

        return amount;
    }

    private void setHeaderStyleTotalFormat(Cells cells, int row, double height){
        for(int i = 3; i < 9; ++i)
            cells.get(row, i).setStyle(headerTotal);
        cells.setRowHeight(row, height);
    }

    private void setBodyStyleTotalFormat(Cells cells, int row, Style style, Style bodyNumber, double height) {
        for(int i = 3; (i < 9); ++i)
            cells.get(row, i).setStyle(style);
        cells.get(row, 6).setStyle(bodyNumber);
        cells.setRowHeight(row, height);
    }

    private void setFooterStyleTotalFormat(Cells cells, int row, double height) {
        for(int i = 3; i < 9; ++i)
            cells.get(row, i).setStyle(footer);
        cells.get(row, 5).setStyle(footerLabel);
        cells.get(row, 6).setStyle(footerNumber);
        cells.setRowHeight(row, height);
    }

    private void printDataDetailFormat(WorksheetCollection worksheets, OrderInfoExportData orderInfoExportData) {
        try {
            OrderInfoDto orderInfoDto = orderInfoExportData.getOrderInfoDto();
            Worksheet worksheet = worksheets.get(0);
            settingPage(worksheet, orderInfoDto.getCompanyName(), STATEMENT_SLIP);
            Cells cells = worksheet.getCells();
            int startIndex = 5;

            printHeadData(cells, orderInfoExportData);

            for (DetailOrderInfoDto i : orderInfoDto.getDetailOrderInfoDtoList())
                for (BentoReservedInfoDto item : i.getBentoReservedInfoDtos())
                    startIndex = handleBodyDetailFormat(worksheet, item, startIndex, cells, orderInfoExportData.isBreakPage());
            worksheet.getVerticalPageBreaks().add(12);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    private int handleBodyDetailFormat(Worksheet worksheet, BentoReservedInfoDto bentoReservedInfoDto, int startIndex, Cells cells, boolean isPageBreak) {
        setHeaderDetail(cells, startIndex,bentoReservedInfoDto.getBentoName());
        List<BentoReservationInfoForEmpDto> bodyData = bentoReservedInfoDto.getBentoReservationInfoForEmpList();
        int total = 0;
        if(CollectionUtil.isEmpty(bodyData)){
            for(int i = 0; i < bodyData.size(); ++i)
                setBodyStyleDetailFormat(cells, startIndex,i*4,bodyDetailEmp2, bodyDetailQuantity2, bodyDetailCheck2);

            cells.deleteRow(startIndex + 1);
            setFooterDataDetailFormat(cells, startIndex + 1, total,"");
            startIndex += 5;
            if(isPageBreak){
                HorizontalPageBreakCollection pageBreaksH = worksheet.getHorizontalPageBreaks();
                pageBreaksH.add("M" + (startIndex - 2));
                VerticalPageBreakCollection pageBreaksV = worksheet.getVerticalPageBreaks();
                pageBreaksV.add("M" + (startIndex - 2));
            }
        }
        else {
            int row = bodyData.size() / 3;
            int mod = bodyData.size() % 3;

            for (int i = 0; i < row; ++i) {
                if (i % 2 == 0)
                    for (int j = 0; j < 3; ++j)
                        setBodyStyleDetailFormat(cells, startIndex + i, j * 4, bodyDetailEmp2, bodyDetailQuantity2, bodyDetailCheck2);
                else
                    for (int j = 0; j < 3; ++j)
                        setBodyStyleDetailFormat(cells, startIndex + i, j * 4, bodyDetailEmp1, bodyDetailQuantity1, bodyDetailCheck1);
                for (int j = 0; j < 3; ++j)
                    total += setBodyDataDetailFormat(cells, startIndex + i, j * 4, bodyData.get(i * 3 + j));
            }
            int modIndex = row * 3;
            startIndex += row ;
            for (int i = 0; i < mod; ++i) {
                if (modIndex % 2 == 1)
                    setBodyStyleDetailFormat(cells, startIndex, i * 4, bodyDetailEmp2, bodyDetailQuantity2, bodyDetailCheck2);
                else
                    setBodyStyleDetailFormat(cells, startIndex, i * 4, bodyDetailEmp1, bodyDetailQuantity1, bodyDetailCheck1);

                total += setBodyDataDetailFormat(cells, startIndex, i * 4, bodyData.get(modIndex + i));
            }
            if(mod > 0)
                startIndex += 1;
            setFooterDataDetailFormat(cells, startIndex, total, bentoReservedInfoDto.getUnit());
            startIndex += 5;
        }

        if(isPageBreak){
            HorizontalPageBreakCollection pageBreaksH = worksheet.getHorizontalPageBreaks();
            pageBreaksH.add("M" + (startIndex - 2));
            VerticalPageBreakCollection pageBreaksV = worksheet.getVerticalPageBreaks();
            pageBreaksV.add("M" + (startIndex - 2));
        }
        return startIndex;
    }

    private void setHeaderDetail(Cells cells, int startIndex, String bentoName){
        cells.get(startIndex - 2, 0).setValue(bentoName);
        for (int j = 0; j < 3; ++j) {
            //head line 1
            cells.get(startIndex - 2, 0 + j * 4).setStyle(headerDetail1);
            cells.get(startIndex - 2, 1 + j * 4).setStyle(headerDetail1);
            cells.get(startIndex - 2, 2 + j * 4).setStyle(headerDetail1);
            cells.get(startIndex - 2, 3 + j * 4).setStyle(headerDetail1);

            //head line 2
            cells.get(startIndex - 1, 0 + j * 4).setValue(EMP_CODE);
            cells.get(startIndex - 1, 1 + j * 4).setValue(EMP_NAME);
            cells.get(startIndex - 1, 2 + j * 4).setValue(QUANTITY);
            cells.get(startIndex - 1, 3 + j * 4).setValue(IS_CHECK);

            cells.get(startIndex - 1, 0 + j * 4).setStyle(headerDetail2);
            cells.get(startIndex - 1, 1 + j * 4).setStyle(headerDetail2);
            cells.get(startIndex - 1, 2 + j * 4).setStyle(headerDetail2);
            cells.get(startIndex - 1, 3 + j * 4).setStyle(headerDetailCheck2);

        }
    }

    private int setBodyDataDetailFormat(Cells cells, int startIndex, int colStart, BentoReservationInfoForEmpDto data) {
        cells.get(startIndex,colStart).setValue(data.getEmpCode());
        cells.get(startIndex,colStart + 1).setValue(data.getEmpName());
        cells.get(startIndex,colStart + 2).setValue(data.getQuantity());
        return data.getQuantity();
    }

    private void setBodyStyleDetailFormat(Cells cells, int row,int colStart, Style empStyle, Style quantityStyle, Style checkStyle) {
        cells.get(row,colStart).setStyle(empStyle);
        cells.get(row,colStart+1).setStyle(empStyle);
        cells.get(row,colStart+2).setStyle(quantityStyle);
        cells.get(row,colStart+3).setStyle(checkStyle);
    }

    private void setFooterStyleDetailFormat(Cells cells, int row) {
        for(int i = 0; i < 12 ; ++i)
            cells.get(row,i).setStyle(footer);
        cells.get(row,9).setStyle(footerLabel);
    }

    private void setFooterDataDetailFormat(Cells cells, int row, int total, String unit){
        //style
        setFooterStyleDetailFormat(cells,row);
        //data
        cells.get(row,9).setValue(TOTAL_LABEL);
        cells.get(row,10).setValue(total);
        cells.get(row,11).setValue(unit);
    }

    private void setBackgroundWhite(Cell cell){
        Style style = cell.getStyle();
        style.setBackgroundColor(Color.getWhite());
        cell.setStyle(style);
    }
    private void setBackgroundBlue(Cell cell){
        Style style = cell.getStyle();
        style.setBackgroundColor(Color.getBlue());
        cell.setStyle(style);
    }
    private void setBackgroundAliceBlue(Cell cell){
        Style style = cell.getStyle();
        style.setBackgroundColor(Color.getAliceBlue());
        cell.setStyle(style);
    }
    private void setBorderTopMedium(Cell cell){
        Style style = cell.getStyle();
        style.setBorder(BorderType.TOP_BORDER, CellBorderType.MEDIUM, Color.getBlack());
        cell.setStyle(style);
    }
    private void setBorderBotMedium(Cell cell){
        Style style = cell.getStyle();
        style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.MEDIUM, Color.getBlack());
        cell.setStyle(style);
    }
    private void setBorderTopThin(Cell cell){
        Style style = cell.getStyle();
        style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
        cell.setStyle(style);
    }
    private void setBorderBotThin(Cell cell){
        Style style = cell.getStyle();
        style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
        cell.setStyle(style);
    }
    private void setBorderVertHair(Cell cell){
        Style style = cell.getStyle();
        style.setBorder(BorderType.VERTICAL, CellBorderType.HAIR, Color.getBlack());
        cell.setStyle(style);
    }
    private void setTextAlignRight(Cell cell){
        Style style = cell.getStyle();
        style.setHorizontalAlignment(TextAlignmentType.RIGHT);
        cell.setStyle(style);
    }
    private void setNumberCustome(Cell cell){
        Style style = cell.getStyle();
        style.setCustom(NUMBER_FORMAT);
        footerLabel.setHorizontalAlignment(TextAlignmentType.RIGHT);
        cell.setStyle(style);
    }

}
