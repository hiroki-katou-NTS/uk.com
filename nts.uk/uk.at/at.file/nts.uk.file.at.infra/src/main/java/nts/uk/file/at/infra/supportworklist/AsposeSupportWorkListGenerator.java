package nts.uk.file.at.infra.supportworklist;

import com.aspose.cells.*;
import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.at.function.dom.adapter.workplace.WorkPlaceInforExport;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportAggregationUnit;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportWorkDetails;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.EmployeeExtractCondition;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.OutputItem;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.outputdata.SupportDetail;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.outputdata.SupportWorkDataOfDay;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.outputdata.TotalValueDetail;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.outputdata.WorkplaceSupportWorkData;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.file.at.app.export.supportworklist.SupportWorkListDataSource;
import nts.uk.file.at.app.export.supportworklist.SupportWorkListGenerator;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AsposeSupportWorkListGenerator extends AsposeCellsReportGenerator implements SupportWorkListGenerator {
    private static final String CSV_EXT = ".csv";
    private static final String EXCEL_EXT = ".xlsx";
    private final String SPACE = "　";
    private final String EMPTY = "";
    private final String FONT_NAME = "ＭＳ ゴシック";
    private final int FONT_SIZE = 9;
    private int maxColumnInHeader = 0;

    @Override
    public void generate(FileGeneratorContext context, SupportWorkListDataSource dataSource, boolean exportCsv) {
        maxColumnInHeader = 0;
        try {
            AsposeCellsReportContext reportContext = this.createEmptyContext("SupportWorkListReport");
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            Worksheet worksheet = worksheets.get(0);
            worksheet.setName(dataSource.getSupportWorkOutputSetting().getName().v());
            worksheet.setGridlinesVisible(false);
            this.printHeader(worksheet, dataSource);
            this.printContent(worksheet, dataSource, exportCsv);
            reportContext.processDesigner();
            this.pageSetting(worksheet, dataSource);

            if (exportCsv) {
                reportContext.saveAsCSV(this.createNewFile(context, this.getReportName(dataSource.getSupportWorkOutputSetting().getName().v() + CSV_EXT)));
            } else {
                worksheet.setViewType(ViewType.PAGE_LAYOUT_VIEW);
                worksheet.getCells().setStandardWidth(11);
                worksheet.getCells().setColumnWidth(0, 9.5);
                worksheet.getCells().setColumnWidth(1, 14);
                worksheet.getCells().setColumnWidth(2, 12);
                worksheet.getCells().setColumnWidth(maxColumnInHeader - 1, 5.4);
                reportContext.saveAsExcel(this.createNewFile(context, this.getReportName(dataSource.getSupportWorkOutputSetting().getName().v() + EXCEL_EXT)));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void pageSetting(Worksheet worksheet, SupportWorkListDataSource dataSource) {
        val companyName = dataSource.getCompanyInfo().isPresent() ? dataSource.getCompanyInfo().get().getCompanyName().v() : EMPTY;
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setPaperSize(PaperSizeType.PAPER_A_4);
        pageSetup.setOrientation(PageOrientationType.LANDSCAPE);
        pageSetup.setFitToPagesTall(0);
        pageSetup.setFitToPagesWide(1);
        pageSetup.setTopMarginInch(2.5);
        pageSetup.setBottomMarginInch(1);
        pageSetup.setLeftMarginInch(1);
        pageSetup.setRightMarginInch(1);
        pageSetup.setHeaderMarginInch(1);
        pageSetup.setFooterMarginInch(0.8);
//        pageSetup.setCenterHorizontally(true);
        pageSetup.setHeader(0, "&9&\"" + FONT_NAME + "\"" + companyName);
        pageSetup.setHeader(1, "&16&\"" + FONT_NAME + ",Bold\"" + dataSource.getSupportWorkOutputSetting().getName());
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd  HH:mm", Locale.JAPAN);
        pageSetup.setHeader(2, "&9&\"" + FONT_NAME + "\"" + LocalDateTime.now().format(fullDateTimeFormatter) + "\npage&P ");
//        pageSetup.setPrintTitleRows("$3");
    }

    private void printHeader(Worksheet worksheet, SupportWorkListDataSource dataSource) {
        val headerInfo = dataSource.getLstHeaderInfo().get(0);
        Cells cells = worksheet.getCells();
        // B2_1
        cells.get(0, 0).setValue(getText("KAF002_81") + "　：");
        this.setBasicStyle(cells.get(0, 0), false, false, false);
        cells.get(0, 1).setValue(headerInfo.getFirstLineCode() + SPACE + headerInfo.getFirstLineName() + getText("KHA002_100") +
                headerInfo.getLastLineCode() + SPACE + headerInfo.getLastLineName()
                + TextResource.localize(dataSource.getAggregationUnit() == SupportAggregationUnit.WORKPLACE.value ? "KHA002_101" : "KHA002_102", String.valueOf(headerInfo.getNumberOfSelect())));
        this.setBasicStyle(cells.get(0, 1), false, false, false);
        // B3_1
        cells.get(1, 0).setValue(getText("KHA002_103"));
        this.setBasicStyle(cells.get(1, 0), false, false, false);
        cells.get(1, 1).setValue(dataSource.getPeriod().start().toString("yyyy/MM/dd") + getText("KHA002_104") + dataSource.getPeriod().end().toString("yyyy/MM/dd"));
        this.setBasicStyle(cells.get(1, 1), false, false, false);
        // B4_1
        cells.get(2, 0).setValue(getText("KHA002_105"));
        // B4_2
        cells.get(2, 1).setValue(getText("KHA002_106"));
        // B4_3
        cells.get(2, 2).setValue(this.getSupportWkpLabel(dataSource));
        // B4_4 ~ B4_10
        int nextColumn = 3;
        val outputItems = dataSource.getSupportWorkOutputSetting().getDetailLayoutSetting().getDetailDisplaySetting().getOutputItems()
                .stream().sorted(Comparator.comparing(OutputItem::getDisplayOrder)).collect(Collectors.toList());
        for (OutputItem attendance : outputItems) {
            val attendanceName = dataSource.getAttendanceItems().stream().filter(x -> x.getAttendanceItemId() == attendance.getAttendanceItemId())
                    .findFirst();
            cells.get(2, nextColumn).setValue(attendanceName.isPresent() ? attendanceName.get().getAttendanceItemName() : EMPTY);
            nextColumn += 1;
        }
        // B4_11
        cells.get(2, nextColumn).setValue(getText("KHA002_109"));
        for (int i = 0; i <= nextColumn; i++) {
            this.setHeaderStyle(cells.get(2, i), i == 0, i == nextColumn);
        }
        maxColumnInHeader = nextColumn + 1;
    }

    private void printContent(Worksheet worksheet, SupportWorkListDataSource dataSource, boolean isCsv) {
        val detailLayoutSetting = dataSource.getSupportWorkOutputSetting().getDetailLayoutSetting();
        val displaySetting = detailLayoutSetting.getDetailDisplaySetting();
        val outputItems = displaySetting.getOutputItems().stream().sorted(Comparator.comparing(OutputItem::getDisplayOrder)).collect(Collectors.toList());
        val wkpTotalDisplaySetting = dataSource.getSupportWorkOutputSetting().getDetailLayoutSetting().getWorkplaceTotalDisplaySetting();
        val grandTotalDisplay = detailLayoutSetting.getGrandTotalDisplaySetting();

        boolean isPageBreak = detailLayoutSetting.getPageBreak().isUse();
        HorizontalPageBreakCollection hPageBreaks = worksheet.getHorizontalPageBreaks();
        int pageIndex = 0;
        int itemPerPage = 0;

        Cells cells = worksheet.getCells();
        int startRow = 3;
        val workplaceSortedList = dataSource.getWorkplaceInfoList().stream().sorted(Comparator.comparing(WorkPlaceInforExport::getWorkplaceCode))
                .map(WorkPlaceInforExport::getWorkplaceId).collect(Collectors.toList());
        val supportWorkDataList = dataSource.getSupportWorkOutputData().getSupportWorkDataList();
        supportWorkDataList.sort(Comparator.comparing(v -> workplaceSortedList.indexOf(v.getWorkplace())));
        for (int i = 0; i < supportWorkDataList.size(); i++) {   // loop for each workplace
            WorkplaceSupportWorkData workDataByWkp = supportWorkDataList.get(i);
            // C5_1
            cells.get(startRow, 0).setValue(this.getNameC51(dataSource) + "　：");
            this.setBasicStyle(cells.get(startRow, 0), false, i == 0, supportWorkDataList.size() > 1 && i > 0);
            // C5_2
            cells.get(startRow, 1).setValue(this.getWorkplaceInfo(dataSource.getAggregationUnit(), dataSource.getWorkplaceInfoList(), workDataByWkp.getWorkplace(), true));
            for (int v = 1; v < maxColumnInHeader; v++) {
                this.setBasicStyle(cells.get(startRow, v), false, i == 0, supportWorkDataList.size() > 1 && i > 0);
            }
            cells.merge(startRow, 1, 1, this.maxColumnInHeader - 1, true);
            startRow += 1;
            itemPerPage += 1;

            val detailSorted = workDataByWkp.getSupportWorkDetails().stream().sorted(Comparator.comparing(SupportWorkDataOfDay::getDate)).collect(Collectors.toList());
            for (int j = 0; j < detailSorted.size(); j++) {  // loop day
                SupportWorkDataOfDay dataOfDay = detailSorted.get(j);
                boolean isEventLine = false;

                if (displaySetting.getDisplayDetail() == NotUseAtr.USE) {
                    // C6_1
                    cells.get(startRow, 0).setValue(dataOfDay.getDate().toString("MM/dd"));
                    this.setDetailStyle(cells.get(startRow, 0), isEventLine, true, true, false, true, false);

                    int rowStartMerge = startRow;
                    for (int k = 0; k < dataOfDay.getSupportWorkDetailsList().size(); k++) {  // loop detail
                        SupportWorkDetails detail = dataOfDay.getSupportWorkDetailsList().get(k);

                        // C6_2
                        val empInfo = dataSource.getEmployeeInfoList().stream().filter(emp -> emp.getSid().equals(detail.getEmployeeId())).findFirst();
                        cells.get(startRow, 1).setValue(empInfo.isPresent() ? empInfo.get().getEmployeeName() : EMPTY);
                        this.setDetailStyle(cells.get(startRow, 1), isEventLine, true, k == 0, false, false, false);

                        // C6_3
                        if (detail.isSupportWork()) {
                            cells.get(startRow, 2).setValue(this.getWorkplaceInfo(
                                    dataSource.getAggregationUnit(),
                                    dataSource.getWorkplaceInfoList(),
                                    detailLayoutSetting.getExtractCondition() == EmployeeExtractCondition.EXTRACT_EMPLOYEES_GO_TO_SUPPORT ? detail.getWorkInfo() : detail.getAffiliationInfo(),
                                    false
                            ));
                        }
                        this.setDetailStyle(cells.get(startRow, 2), isEventLine, true, k == 0, false, false, false);

                        // C6_4 ~ C6_9
                        int startColumnOfC6 = 3;
                        for (OutputItem outputItem : outputItems) {
                            Optional<ItemValue> itemValue = detail.getItemList().stream().filter(item -> item.getItemId() == outputItem.getAttendanceItemId()).findFirst();
                            if (itemValue.isPresent()) {
                                // If the "AttendanceItemID" is 928 or less, find the work name from the "Value".
                                if (outputItem.getAttendanceItemId() <= 928) {
                                    cells.get(startRow, startColumnOfC6).setValue(this.getWorkName(
                                            outputItem.getAttendanceItemId(),
                                            itemValue.get().getValue(),
                                            dataSource
                                    ));
                                    this.setDetailStyle(cells.get(startRow, startColumnOfC6), isEventLine, true, k == 0, k == dataOfDay.getSupportWorkDetailsList().size(), false, false);
                                } else {   // If the "AttendanceItemID" is 929 or higher, set the "Value" in Excel as it is.
                                    cells.get(startRow, startColumnOfC6).setValue(this.formatValue(itemValue.get().getValue(), itemValue.get().getValueType(), isCsv));
                                    this.setDetailStyle(cells.get(startRow, startColumnOfC6), isEventLine, false, k == 0, k == dataOfDay.getSupportWorkDetailsList().size(), false, false);
                                }
                            }
                            // next column
                            startColumnOfC6 += 1;
                        }
                        this.setDetailStyle(cells.get(startRow, startColumnOfC6), isEventLine, false, k == 0, k == dataOfDay.getSupportWorkDetailsList().size(), false, true);
                        startRow += 1;  // next row
                        itemPerPage += 1;
                        isEventLine = !isEventLine;
                    } // end loop detail

                    cells.merge(rowStartMerge, 0, dataOfDay.getSupportWorkDetailsList().size(), 1, true);
                    Cell cellDate = cells.get(rowStartMerge, 0);
                    Style styleDate = cellDate.getStyle();
                    styleDate.setVerticalAlignment(TextAlignmentType.TOP);
                    styleDate.setForegroundColor(Color.getEmpty());
                    cellDate.setStyle(styleDate);
                }

                /** Daily total */
                if (wkpTotalDisplaySetting.getDisplayOneDayTotal() == NotUseAtr.USE) {
                    cells.get(startRow, 0).setValue(getText("KHA002_110"));
                    // C7_1_2
                    if (displaySetting.getDisplayDetail() != NotUseAtr.USE) {
                        cells.get(startRow, 1).setValue(dataOfDay.getDate().toString("MM/dd"));
                    }
                    this.setTotalStyle(cells.get(startRow, 0), true, true, true, true,false);
                    this.setTotalStyle(cells.get(startRow, 1), true, false, true, true, false);
                    this.setTotalStyle(cells.get(startRow, 2), false, false, true, true, false);

                    // C7_2 ~ C7_9
                    int startColumnOfC7 = 3;
                    this.printTotal(cells, outputItems, dataOfDay.getTotalDetailOfDay(), startRow, startColumnOfC7, true, isCsv, false);

                    startRow += 1; // next row
                    itemPerPage += 1;
                }
            } // end loop day

            if (wkpTotalDisplaySetting.getDisplayWorkplaceSupportMeter() == NotUseAtr.USE) {
                if (detailLayoutSetting.getExtractCondition() == EmployeeExtractCondition.EXTRACT_ALL_WORKING_EMPLOYEES
                        || detailLayoutSetting.getExtractCondition() == EmployeeExtractCondition.EXTRACT_EMPLOYEES_GO_TO_SUPPORT) {
                    /** Affiliation work total */
                    // C8_1
                    cells.get(startRow, 0).setValue(getText("KHA002_141"));
                    this.setTotalStyle(cells.get(startRow, 0), true, false, false, true, true);
                    this.setTotalStyle(cells.get(startRow, 1), false, false, false, true, true);
                    cells.merge(startRow, 0, 1, 2, true);
                    this.setTotalStyle(cells.get(startRow, 2), false, false, false, true, true);

                    // C8_2 ~ C8_9
                    this.printTotal(cells, outputItems, workDataByWkp.getTotalAffiliation(), startRow, 3, false, isCsv, true);
                    startRow += 1;  // next row
                    itemPerPage += 1;
                }

                if (detailLayoutSetting.getExtractCondition() == EmployeeExtractCondition.EXTRACT_ALL_WORKING_EMPLOYEES
                        || detailLayoutSetting.getExtractCondition() == EmployeeExtractCondition.EXTRACT_EMPLOYEES_COME_TO_SUPPORT) {
                    /** support detail total */
                    // C8_1_2
                    cells.get(startRow, 0).setValue(getText("KHA002_142"));
                    this.setTotalStyle(cells.get(startRow, 0), true, false, false, true, false);
                    this.setTotalStyle(cells.get(startRow, 1), false, false, false, true, false);
                    cells.merge(startRow, 0, 1, 2, true);
                    this.setTotalStyle(cells.get(startRow, 2), false, false, false, true, false);
                    // C8_2_2 ~ C8_2_9
                    this.printTotal(cells, outputItems, workDataByWkp.getTotalSupport(), startRow, 3, false, isCsv, false);
                    startRow += 1;
                    itemPerPage += 1;
                }
            }


            /** Support breakdown workplace */
            if (wkpTotalDisplaySetting.getDisplaySupportDetail() == NotUseAtr.USE) {
                List<SupportDetail> supportWkpDetail = workDataByWkp.getSupportDetails().stream().sorted(Comparator.comparing(SupportDetail::getSupportDestination)).collect(Collectors.toList());
                for (SupportDetail sp : supportWkpDetail) {
                    if (dataSource.getAggregationUnit() == SupportAggregationUnit.WORKPLACE.value) {
                        this.setTotalStyleCustom(cells.get(startRow, 0), true, false, true, false, false, false);
                        this.setTotalStyleCustom(cells.get(startRow, 1), false, false, true, false, false, false);
                        val wkpInfo = dataSource.getWorkplaceInfoList().stream().filter(x -> x.getWorkplaceId().equals(sp.getSupportDestination())).findFirst();
                        if (wkpInfo.isPresent()) {
                            // C9_1
                            cells.get(startRow, 2).setValue(wkpInfo.get().getWorkplaceName());
                            this.setTotalStyleCustom(cells.get(startRow, 2), true, false, true, false, false, false);
                        }
                        // C9_2 ~
                        this.printTotalCustom(cells, outputItems, Optional.of(sp.getTotalValueDetail()), startRow, 3, isCsv, false, false, false);
                    }
                    startRow += 1;  // next row
                    itemPerPage += 1;
                }
            }

            /** workplace total */
            if (wkpTotalDisplaySetting.getDisplayWorkplaceTotal() == NotUseAtr.USE) {
                // C10_1
                cells.get(startRow, 0).setValue(getText("KHA002_143"));
                this.setTotalStyle(cells.get(startRow, 0), true, false, true, true, false);
                this.setTotalStyle(cells.get(startRow, 1), false, false, true, true, false);
                this.setTotalStyle(cells.get(startRow, 2), false, false, true, true, false);
                // C10_2 ~ C10_9
                this.printTotal(cells, outputItems, workDataByWkp.getTotalWorkplace(), startRow, 3, true, isCsv, false);
                startRow += 1;
                itemPerPage += 1;
            }

            // page break by workplace
            if (isPageBreak) {
                if (supportWorkDataList.size() > 1 && i != supportWorkDataList.size() - 1) {
                    hPageBreaks.add(startRow);
                    for (int c = 0; c < maxColumnInHeader; c++) {
                        this.setBorder(cells.get(startRow - 1, c), true, false);
                    }
                    pageIndex += 1;
                    itemPerPage = 0;
                }
            } else {
                for (int c = 0; c < maxColumnInHeader; c++) {
                    this.setBorder(cells.get(startRow - 1, c), true, false);
                }
            }

        } // end loop workplace

        if (grandTotalDisplay.getDisplayWorkplaceSupportMeter().isUse()) {
            /** affiliation work total all */
            if (detailLayoutSetting.getExtractCondition() == EmployeeExtractCondition.EXTRACT_ALL_WORKING_EMPLOYEES
                    || detailLayoutSetting.getExtractCondition() == EmployeeExtractCondition.EXTRACT_EMPLOYEES_GO_TO_SUPPORT) {
                // C11_1
                cells.get(startRow, 0).setValue(getText("KHA002_141"));
                this.setTotalStyleCustom(cells.get(startRow, 0), true, false, true, false, false, true);
                this.setTotalStyleCustom(cells.get(startRow, 1), false, false, true, false, false, true);
                cells.merge(startRow, 0, 1, 2, true);
                this.setTotalStyleCustom(cells.get(startRow, 2), false, false, true, false, false, true);

                // C11_2 ~ C11_9
                this.printTotalCustom(cells, outputItems, dataSource.getSupportWorkOutputData().getTotalAffiliation(), startRow, 3, isCsv, false, false, true);
                startRow += 1;
                itemPerPage += 1;
            }

            if (detailLayoutSetting.getExtractCondition() == EmployeeExtractCondition.EXTRACT_ALL_WORKING_EMPLOYEES
                    || detailLayoutSetting.getExtractCondition() == EmployeeExtractCondition.EXTRACT_EMPLOYEES_COME_TO_SUPPORT) {
                /** support total all */
                // C11_1_2
                cells.get(startRow, 0).setValue(getText("KHA002_142"));
                this.setTotalStyleCustom(cells.get(startRow, 0), true, false, true, false, true, false);
                this.setTotalStyleCustom(cells.get(startRow, 1), false, false, true, false, true, false);
                cells.merge(startRow, 0, 1, 2, true);
                this.setTotalStyleCustom(cells.get(startRow, 2), false, false, true, false, true, false);

                // C11_2_2 ~ C11_2_2
                this.printTotalCustom(cells, outputItems, dataSource.getSupportWorkOutputData().getTotalSupport(), startRow, 3, isCsv, false, true, false);

                startRow += 1;
                itemPerPage += 1;
            }
        }

        /** grand total */
        if (grandTotalDisplay.getDisplayGrandTotal() == NotUseAtr.USE) {
            // C12_1_2
            cells.get(startRow, 0).setValue(getText("KHA002_111"));
            this.setTotalStyle(cells.get(startRow, 0), true, false, true, true, false);
            this.setTotalStyle(cells.get(startRow, 1), false, false, true, false, false);
            this.setTotalStyle(cells.get(startRow, 2), false, false, true, false, false);
            // C12_2_2 ~
            this.printTotal(cells, outputItems, dataSource.getSupportWorkOutputData().getGrandTotal(), startRow, 3, true, isCsv, false);
            for (int c = 0; c < maxColumnInHeader; c++) {
                this.setBorder(cells.get(startRow, c), true, false);
            }
        }
    }

    private void pageBreakSetting(Worksheet worksheet, SupportWorkListDataSource dataSource) {
        Cells cells = worksheet.getCells();
        HorizontalPageBreakCollection hPageBreaks = worksheet.getHorizontalPageBreaks();
    }

    private void printTotal(Cells cells, List<OutputItem> outputItems, Optional<TotalValueDetail> totalOpt, int startRow, int startColumn, boolean isFillBgColor, boolean isCsv, boolean topBorderDouble) {
        for (OutputItem item : outputItems) {
            if (totalOpt.isPresent()) {
                val total = totalOpt.get().getItemValues().stream()
                        .filter(x -> x.getItemId() == item.getAttendanceItemId()).findFirst();
                if (total.isPresent()) {
                    cells.get(startRow, startColumn).setValue(this.formatValue(total.get().getValue(), item.getAttendanceItemId() == 1309 ? ValueType.AMOUNT_NUM : total.get().getValueType(), isCsv));
                }
            }
            if (item.getAttendanceItemId() <= 928)
                this.setTotalStyle(cells.get(startRow, startColumn), false, false, isFillBgColor, true, topBorderDouble);
            else {
                if (item.getAttendanceItemId() == 1309) {
                    this.setDisplayFormat(cells.get(startRow, startColumn));
                }

                this.setTotalStyle(cells.get(startRow, startColumn), false, false, isFillBgColor, false, topBorderDouble);
            }
            startColumn += 1;
        }
        if (totalOpt.isPresent()) {
            cells.get(startRow, startColumn).putValue(this.formatValue(String.valueOf(totalOpt.get().getPeopleCount()), ValueType.AMOUNT_NUM, isCsv), false);
            this.setDisplayFormat(cells.get(startRow, startColumn));
        }
        this.setTotalStyle(cells.get(startRow, startColumn), false, true, isFillBgColor, false, topBorderDouble);
    }

    private void printTotalCustom(Cells cells, List<OutputItem> outputItems, Optional<TotalValueDetail> totalOpt, int startRow, int startColumn, boolean isCsv, boolean topBorder, boolean botBorder, boolean topBorderDouble) {
        for (OutputItem item : outputItems) {
            if (totalOpt.isPresent()) {
                val total = totalOpt.get().getItemValues().stream()
                        .filter(x -> x.getItemId() == item.getAttendanceItemId()).findFirst();
                if (total.isPresent()) {
                    cells.get(startRow, startColumn).setValue(this.formatValue(total.get().getValue(), item.getAttendanceItemId() == 1309 ? ValueType.AMOUNT_NUM : total.get().getValueType(), isCsv));
                }
            }
            if (item.getAttendanceItemId() <= 928) {
                this.setTotalStyleCustom(cells.get(startRow, startColumn), false, false, true, topBorder, botBorder, topBorderDouble);
            } else {
                if (item.getAttendanceItemId() == 1309) {
                    this.setDisplayFormat(cells.get(startRow, startColumn));
                }
                this.setTotalStyleCustom(cells.get(startRow, startColumn), false, false, false, topBorder, botBorder, topBorderDouble);
            }
            startColumn += 1;
        }
        if (totalOpt.isPresent()) {
            cells.get(startRow, startColumn).setValue(this.formatValue(String.valueOf(totalOpt.get().getPeopleCount()), ValueType.AMOUNT_NUM, isCsv));
            this.setDisplayFormat(cells.get(startRow, startColumn));
        }
        this.setTotalStyleCustom(cells.get(startRow, startColumn), false, true, false, topBorder, botBorder, topBorderDouble);
    }

    private String getWorkName(int attendanceId, String code, SupportWorkListDataSource dataSource) {
        switch (attendanceId) {
            case 924:
                return dataSource.getWorkList1().stream().filter(w -> w.getCode().v().equals(code)).findFirst().map(w -> w.getDisplayInfo().getTaskName().v()).orElse("");
            case 925:
                return dataSource.getWorkList2().stream().filter(w -> w.getCode().v().equals(code)).findFirst().map(w -> w.getDisplayInfo().getTaskName().v()).orElse("");
            case 926:
                return dataSource.getWorkList3().stream().filter(w -> w.getCode().v().equals(code)).findFirst().map(w -> w.getDisplayInfo().getTaskName().v()).orElse("");
            case 927:
                return dataSource.getWorkList4().stream().filter(w -> w.getCode().v().equals(code)).findFirst().map(w -> w.getDisplayInfo().getTaskName().v()).orElse("");
            case 928:
                return dataSource.getWorkList5().stream().filter(w -> w.getCode().v().equals(code)).findFirst().map(w -> w.getDisplayInfo().getTaskName().v()).orElse("");
            default:
                return "";
        }
    }

    private String getWorkplaceInfo(int aggregationUnit, List<WorkPlaceInforExport> workplaceInfoList, String workplaceId, boolean isGetAll) {
        String code = null, name = null;
        if (aggregationUnit == SupportAggregationUnit.WORKPLACE.value) {
            val wkpFiltered = workplaceInfoList.stream().filter(wkp -> wkp.getWorkplaceId().equals(workplaceId)).findFirst();
            if (wkpFiltered.isPresent()) {
                code = wkpFiltered.get().getWorkplaceCode();
                name = wkpFiltered.get().getWorkplaceName();
            }
        }
        return isGetAll ? code + SPACE + name : name;
    }

    private String getNameC51(SupportWorkListDataSource dataSource) {
        String textLabelC51 = null;
        if (dataSource.getAggregationUnit() == SupportAggregationUnit.WORKPLACE.value) {
            if (dataSource.getSupportWorkOutputSetting().getDetailLayoutSetting().getExtractCondition() == EmployeeExtractCondition.EXTRACT_ALL_WORKING_EMPLOYEES
                    || dataSource.getSupportWorkOutputSetting().getDetailLayoutSetting().getExtractCondition() ==
                    EmployeeExtractCondition.EXTRACT_EMPLOYEES_COME_TO_SUPPORT) {
                textLabelC51 = getText("KHA002_137");
            }
            if (dataSource.getSupportWorkOutputSetting().getDetailLayoutSetting().getExtractCondition() == EmployeeExtractCondition.EXTRACT_EMPLOYEES_GO_TO_SUPPORT) {
                textLabelC51 = getText("KHA002_138");
            }
        }
        return textLabelC51;
    }

    private String getSupportWkpLabel(SupportWorkListDataSource dataSource) {
        String textLabelB43 = null;
        if (dataSource.getAggregationUnit() == SupportAggregationUnit.WORKPLACE.value) {
            if (dataSource.getSupportWorkOutputSetting().getDetailLayoutSetting().getExtractCondition() == EmployeeExtractCondition.EXTRACT_ALL_WORKING_EMPLOYEES
                    || dataSource.getSupportWorkOutputSetting().getDetailLayoutSetting().getExtractCondition() ==
                    EmployeeExtractCondition.EXTRACT_EMPLOYEES_COME_TO_SUPPORT) {
                textLabelB43 = getText("KHA002_130");
            }
            if (dataSource.getSupportWorkOutputSetting().getDetailLayoutSetting().getExtractCondition() == EmployeeExtractCondition.EXTRACT_EMPLOYEES_GO_TO_SUPPORT) {
                textLabelB43 = getText("KHA002_131");
            }
        }
        return textLabelB43;
    }

    private String getText(String resourceId) {
        return TextResource.localize(resourceId);
    }

    private String convertNumberToTime(Integer totalMinute) {
        if (totalMinute == null) return "0:00";
        int hour = totalMinute / 60;
        int minute = totalMinute % 60;

        return (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute);
    }

    private String formatValue(String value, ValueType type, boolean isCsv) {
        if (StringUtils.isEmpty(value)) return Strings.EMPTY;

        String targetValue = null;
        switch (type.value) {
            case 1:
            case 15:
                Integer srcValueT = Integer.valueOf(value);
                targetValue = convertNumberToTime(srcValueT);
                break;
            case 16:
                if (isCsv){
                    targetValue = value;
                    break;
                }
                Double srcValueA = Double.valueOf(value);
                DecimalFormat df = new DecimalFormat("#,###");
                targetValue = df.format(srcValueA).replace(".", ",");
                break;
        }

        return targetValue;
    }

    private void setBasicStyle(Cell cell, boolean rightBorder, boolean topBotBorder, boolean topBorderDouble) {
        Style style = new Style();
        style.setHorizontalAlignment(TextAlignmentType.LEFT);
        style.setVerticalAlignment(TextAlignmentType.TOP);
        style.getFont().setName(FONT_NAME);
        style.getFont().setSize(FONT_SIZE);

        if (topBotBorder) {
            style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.THIN);
            style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.THIN);
        } else if (topBorderDouble) {
            style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.THIN);
            style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.THIN);
        }

        if (rightBorder) {
            style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.DOTTED);
        }
        cell.setStyle(style);
    }

    private void setDetailStyle(Cell cell, boolean isEvenLine, boolean isLeftAlignment, boolean firstRow, boolean lastRow, boolean firstColumn, boolean lastColumn) {
        Style style = commonStyle();
        style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.DOTTED);
        style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.DOTTED);

        if (isLeftAlignment)
            style.setHorizontalAlignment(TextAlignmentType.LEFT);
        else
            style.setHorizontalAlignment(TextAlignmentType.RIGHT);

        if (firstRow) {
            style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.THIN);
        }

        if (lastRow) {
            style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.THIN);
        }

        if (firstColumn) {
            style.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.NONE);
        }

        if (lastColumn) {
            style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.NONE);
        }

        if (isEvenLine)
            style.setForegroundColor(Color.fromArgb(221, 235, 247));

        cell.setStyle(style);
    }

    private void setBorder(Cell cell, boolean botBorderDouble, boolean botBorderDot) {
        Style style = cell.getStyle();
        Border border = style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER);
        if (botBorderDouble)
            border.setLineStyle(CellBorderType.DOUBLE);
        else if (botBorderDot) {
            border.setLineStyle(CellBorderType.DOTTED);
        }
        else
            border.setLineStyle(CellBorderType.THIN);
        cell.setStyle(style);
    }

    private void setHeaderStyle(Cell cell, boolean firstColumn, boolean lastColumn) {
        Style style = this.commonStyle();
        style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.MEDIUM);
        style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.MEDIUM);
        style.setForegroundColor(Color.fromArgb(155, 194, 230));
        if (firstColumn) {
            style.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.NONE);
        }
        if (lastColumn) {
            style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.NONE);
        }
        cell.setStyle(style);
    }

    private void setTotalStyle(Cell cell, boolean firstColumn, boolean lastColumn, boolean isFillBgColor, boolean isLeftAlignment, boolean topBorderDouble) {
        Style style = this.commonStyle();
        style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.THIN);

        if (topBorderDouble)
            style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.DOUBLE);
        else
            style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.THIN);

        if (isLeftAlignment)
            style.setHorizontalAlignment(TextAlignmentType.LEFT);
        else
            style.setHorizontalAlignment(TextAlignmentType.RIGHT);

        if (isFillBgColor) {
            style.setForegroundColor(Color.fromArgb(155, 194, 230));
        }
        if (firstColumn) {
            style.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.NONE);
        }
        if (lastColumn) {
            style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.NONE);
        }
        cell.setStyle(style);
    }

    public void setDisplayFormat(Cell cell){
        Style style = cell.getStyle();
        style.setNumber(4);
        cell.setStyle(style);
    }

    private void setTotalStyleCustom(Cell cell, boolean firstColumn, boolean lastColumn, boolean isLeftAlignment, boolean topBorder, boolean botBorder, boolean topBorderDouble) {
        Style style = this.commonStyle();
        if (topBorderDouble)
            style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.DOUBLE);
        else if (topBorder)
            style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.THIN);

        if (botBorder)
            style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.THIN);
        style.setHorizontalAlignment(isLeftAlignment ? TextAlignmentType.LEFT : TextAlignmentType.RIGHT);

        if (firstColumn)
            style.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.NONE);
        if (lastColumn)
            style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.NONE);
        cell.setStyle(style);
    }

    private Style commonStyle() {
        Style commonStyle = new Style();
        commonStyle.getFont().setName(FONT_NAME);
        commonStyle.getFont().setSize(FONT_SIZE);
        commonStyle.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.DASHED);
        commonStyle.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.DASHED);
        commonStyle.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.DOTTED);
        commonStyle.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.DOTTED);
        commonStyle.setShrinkToFit(true);
        commonStyle.setPattern(BackgroundType.SOLID);
        commonStyle.setVerticalAlignment(TextAlignmentType.CENTER);
        commonStyle.setHorizontalAlignment(TextAlignmentType.CENTER);
        return commonStyle;
    }
}
