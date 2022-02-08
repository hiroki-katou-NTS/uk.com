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
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private static final int MAX_ROW_IN_PAGE = 40;
    private static final int MAX_ROW_TITLE_IN_PAGE = 2;
    private static final int MAX_ROW_HEADER_IN_PAGE = 1;
    private static final int MAX_EMPLOYEE_PER_PAGE = 30;
    private final String SPACE = "　";
    private final String EMPTY = "";
    private static final String PRINT_AREA = "A1:BE";
    private final String FONT_NAME = "ＭＳ ゴシック";
    private final int FONT_SIZE = 9;
    private int maxColumnInHeader = 0;

    @Override
    public void generate(FileGeneratorContext context, SupportWorkListDataSource dataSource, boolean exportCsv) {
        try {
            AsposeCellsReportContext reportContext = this.createEmptyContext("SupportWorkListReport");
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            Worksheet worksheet = worksheets.get(0);
            worksheet.setName(dataSource.getSupportWorkOutputSetting().getName().v());
            worksheet.setGridlinesVisible(false);
            this.printHeader(worksheet, dataSource);
            this.printContent(worksheet, dataSource, exportCsv);
            this.pageBreakSetting(worksheet, dataSource);
            reportContext.processDesigner();
            this.pageSetting(worksheet, dataSource);

            if (exportCsv) {
                reportContext.saveAsCSV(this.createNewFile(context, this.getReportName(dataSource.getSupportWorkOutputSetting().getName().v() + CSV_EXT)));
            } else {
                worksheet.setViewType(ViewType.PAGE_LAYOUT_VIEW);
                worksheet.getCells().setStandardWidth(12);
                worksheet.getCells().setColumnWidth(0, 11);
                worksheet.getCells().setColumnWidth(1, 13.5);
                worksheet.getCells().setColumnWidth(2, 13.5);
                worksheet.getCells().setColumnWidth(maxColumnInHeader, 11);
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
        pageSetup.setTopMarginInch(0.98);
        pageSetup.setBottomMarginInch(0.39);
        pageSetup.setLeftMarginInch(0.39);
        pageSetup.setRightMarginInch(0.39);
        pageSetup.setHeaderMarginInch(0.39);
        pageSetup.setFooterMarginInch(0.31);
        pageSetup.setCenterHorizontally(true);
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
        this.setBasicStyle(cells.get(0, 0), false);
        cells.get(0, 1).setValue(headerInfo.getFirstLineCode() + SPACE + headerInfo.getFirstLineName() + getText("KHA002_100") +
                headerInfo.getLastLineCode() + SPACE + headerInfo.getLastLineName()
                + TextResource.localize(dataSource.getAggregationUnit() == SupportAggregationUnit.WORKPLACE.value ? "KHA002_101" : "KHA002_102", String.valueOf(headerInfo.getNumberOfSelect())));
        this.setBasicStyle(cells.get(0, 1), false);
        // B3_1
        cells.get(1, 0).setValue(getText("KHA002_103"));
        this.setBasicStyle(cells.get(1, 0), false);
        cells.get(1, 1).setValue(dataSource.getPeriod().start().toString("yyyy/MM/dd") + getText("KHA002_104") + dataSource.getPeriod().end().toString("yyyy/MM/dd"));
        this.setBasicStyle(cells.get(1, 1), false);
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
        val supportWorkDataList = dataSource.getSupportWorkOutputData().getSupportWorkDataList();
        for (int i = 0; i < supportWorkDataList.size(); i++) {   // loop for each workplace
            WorkplaceSupportWorkData workDataByWkp = supportWorkDataList.get(i);
            // C5_1
            cells.get(startRow, 0).setValue(this.getNameC51(dataSource));
            this.setBasicStyle(cells.get(startRow, 0), true);
            // C5_2
            cells.get(startRow, 1).setValue(this.getWorkplaceInfo(dataSource.getAggregationUnit(), dataSource.getWorkplaceInfoList(), workDataByWkp.getWorkplace(), true));
            this.setBasicStyle(cells.get(startRow, 1), false);
            cells.merge(startRow, 1, 1, this.maxColumnInHeader, true);
            startRow += 1;
            itemPerPage += 1;

            boolean isEventLine = false;
            val detailSorted = workDataByWkp.getSupportWorkDetails().stream().sorted(Comparator.comparing(SupportWorkDataOfDay::getDate)).collect(Collectors.toList());
            for (int j = 0; j < detailSorted.size(); j++) {  // loop day
                SupportWorkDataOfDay dataOfDay = detailSorted.get(j);

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
                        this.setDetailStyle(cells.get(startRow, 1), isEventLine, true, k == 0, k == dataOfDay.getSupportWorkDetailsList().size() - 1, false, false);

                        // C6_3
                        if (detail.isSupportWork()) {
                            cells.get(startRow, 2).setValue(this.getWorkplaceInfo(dataSource.getAggregationUnit(), dataSource.getWorkplaceInfoList(), detail.getAffiliationInfo(), false));
                        }
                        this.setDetailStyle(cells.get(startRow, 2), isEventLine, true, k == 0, k == dataOfDay.getSupportWorkDetailsList().size() - 1, false, false);

                        // C6_4 ~ C6_9
                        int startColumnOfC6 = 3;
                        for (OutputItem outputItem : outputItems) {
                            // If the "AttendanceItemID" is 928 or less, find the work name from the "Value".
                            if (outputItem.getAttendanceItemId() <= 928) {
                                cells.get(startRow, startColumnOfC6).setValue(this.getWorkName(outputItem.getAttendanceItemId(), dataSource.getWorkList1(), dataSource.getWorkList2(),
                                        dataSource.getWorkList3(), dataSource.getWorkList4(), dataSource.getWorkList5()));
                                this.setDetailStyle(cells.get(startRow, startColumnOfC6), isEventLine, true, k == 0, k == dataOfDay.getSupportWorkDetailsList().size(), false, false);
                            } else {   // If the "AttendanceItemID" is 929 or higher, set the "Value" in Excel as it is.
                                Optional<ItemValue> itemValue = detail.getItemList().stream().filter(item -> item.getItemId() == outputItem.getAttendanceItemId()).findFirst();
                                if (itemValue.isPresent()) {
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
                    cellDate.setStyle(styleDate);
                }

                /** Daily total */
                if (wkpTotalDisplaySetting.getDisplayOneDayTotal() == NotUseAtr.USE) {
                    // C7_1_2
                    if (displaySetting.getDisplayDetail() == NotUseAtr.NOT_USE) {
                        cells.get(startRow, 0).setValue(getText("KHA002_110") + SPACE + dataOfDay.getDate().toString("MM/dd"));
                    } else {
                        // C7_1
                        cells.get(startRow, 0).setValue(getText("KHA002_110"));
                    }
                    cells.merge(startRow, 0, 1, 2, true);
                    this.setTotalStyle(cells.get(startRow, 0), true, false, true, true);

                    this.setTotalStyle(cells.get(startRow, 1), false, false, true, true);
                    this.setTotalStyle(cells.get(startRow, 2), false, false, true, true);

                    // C7_2 ~ C7_9
                    int startColumnOfC7 = 3;
                    this.printTotal(cells, outputItems, dataOfDay.getTotalDetailOfDay(), startRow, startColumnOfC7, true, isCsv);

                    startRow += 1; // next row
                    itemPerPage += 1;
                }

                if (isPageBreak) {
                    if (itemPerPage == MAX_ROW_IN_PAGE) {
                        hPageBreaks.add(startRow);
                        this.setBorderBottom(cells, startRow);
                        pageIndex += 1;
                        itemPerPage = 0;
                    }
//                    if (pageIndex <= 0) {
//                        if (startRow - 2 >= MAX_ROW_IN_PAGE - (MAX_ROW_HEADER_IN_PAGE + MAX_ROW_TITLE_IN_PAGE)) {
//                            hPageBreaks.add(startRow);
//                            this.setBorderBottom(cells, startRow);
//                            pageIndex += 1;
//                        }
//                    } else {
//                        if (startRow - 2 - (pageIndex * MAX_ROW_IN_PAGE) >= MAX_ROW_IN_PAGE - MAX_ROW_HEADER_IN_PAGE) {
//                            hPageBreaks.add(startRow);
//                            this.setBorderBottom(cells, startRow);
//                            pageIndex += 1;
//                        }
//                    }
                }

            } // end loop day

            if (wkpTotalDisplaySetting.getDisplayWorkplaceSupportMeter() == NotUseAtr.USE) {
                if (detailLayoutSetting.getExtractCondition() == EmployeeExtractCondition.EXTRACT_ALL_WORKING_EMPLOYEES
                        || detailLayoutSetting.getExtractCondition() == EmployeeExtractCondition.EXTRACT_EMPLOYEES_GO_TO_SUPPORT) {
                    /** Affiliation work total */
                    // C8_1
                    cells.get(startRow, 0).setValue(getText("KHA002_141"));
                    this.setTotalStyle(cells.get(startRow, 0), true, false, false, true);
                    this.setTotalStyle(cells.get(startRow, 1), false, false, false, true);
                    this.setTotalStyle(cells.get(startRow, 2), false, false, false, true);

                    // C8_2 ~ C8_9
                    this.printTotal(cells, outputItems, workDataByWkp.getTotalAffiliation(), startRow, 3, false, isCsv);
                    startRow += 1;  // next row
                    itemPerPage += 1;
                }

                if (detailLayoutSetting.getExtractCondition() == EmployeeExtractCondition.EXTRACT_ALL_WORKING_EMPLOYEES
                        || detailLayoutSetting.getExtractCondition() == EmployeeExtractCondition.EXTRACT_EMPLOYEES_COME_TO_SUPPORT) {
                    /** support detail total */
                    // C8_1_2
                    cells.get(startRow, 0).setValue(getText("KHA002_142"));
                    this.setTotalStyle(cells.get(startRow, 0), true, false, false, true);
                    this.setTotalStyle(cells.get(startRow, 1), false, false, false, true);
                    this.setTotalStyle(cells.get(startRow, 2), false, false, false, true);
                    // C8_2_2 ~ C8_2_9
                    this.printTotal(cells, outputItems, workDataByWkp.getTotalSupport(), startRow, 3, false, isCsv);
                    startRow += 1;
                    itemPerPage += 1;
                }
            }


            /** Support breakdown workplace */
            if (wkpTotalDisplaySetting.getDisplaySupportDetail() == NotUseAtr.USE) {
                this.setTotalStyle(cells.get(startRow, 0), true, false, false, true);
                this.setTotalStyle(cells.get(startRow, 1), false, false, false, true);
//            val supportWkpDetail = workDataByWkp.getSupportDetails().stream().sorted(Comparator.comparing(SupportDetail::getSupportDestination)).collect(Collectors.toList());
                for (SupportDetail sp : workDataByWkp.getSupportDetails()) {
                    if (dataSource.getAggregationUnit() == SupportAggregationUnit.WORKPLACE.value) {
                        val wkpInfo = dataSource.getWorkplaceInfoList().stream().filter(x -> x.getWorkplaceId().equals(sp.getSupportDestination())).findFirst();
                        if (wkpInfo.isPresent()) {
                            // C9_1
                            cells.get(startRow, 2).setValue(getText(wkpInfo.get().getWorkplaceName()));
                            this.setTotalStyle(cells.get(startRow, 2), true, false, false, true);
                            // C9_2 ~
                            this.printTotal(cells, outputItems, Optional.of(sp.getTotalValueDetail()), startRow, 3, false, isCsv);
                        }
                    }
                    startRow += 1;  // next row
                    itemPerPage += 1;
                }
            }

            /** workplace total */
            if (wkpTotalDisplaySetting.getDisplayWorkplaceTotal() == NotUseAtr.USE) {
                // C10_1
                cells.get(startRow, 0).setValue(getText("KHA002_143"));
                this.setTotalStyle(cells.get(startRow, 0), true, false, true, true);
                this.setTotalStyle(cells.get(startRow, 1), false, false, true, true);
                this.setTotalStyle(cells.get(startRow, 2), false, false, true, true);
                // C10_2 ~ C10_9
                this.printTotal(cells, outputItems, workDataByWkp.getTotalWorkplace(), startRow, 3, true, isCsv);
                startRow += 1;
                itemPerPage += 1;
            }

            // page break by workplace
            if (isPageBreak) {
                if (i != supportWorkDataList.size() - 1 || supportWorkDataList.size() > 1) {
                    hPageBreaks.add(startRow);
                    this.setBorderBottom(cells, startRow);
                    pageIndex += 1;
                }
            }
        } // end loop workplace

        if (grandTotalDisplay.getDisplayWorkplaceSupportMeter().isUse()) {
            /** affiliation work total all */
            if (detailLayoutSetting.getExtractCondition() == EmployeeExtractCondition.EXTRACT_ALL_WORKING_EMPLOYEES
                    || detailLayoutSetting.getExtractCondition() == EmployeeExtractCondition.EXTRACT_EMPLOYEES_GO_TO_SUPPORT) {
                // C11_1
                cells.get(startRow, 0).setValue(getText("KHA002_141"));
                this.setTotalStyle(cells.get(startRow, 0), true, false, false, true);
                this.setTotalStyle(cells.get(startRow, 1), false, false, false, true);
                this.setTotalStyle(cells.get(startRow, 2), false, false, false, true);

                // C11_2 ~ C11_9
                this.printTotal(cells, outputItems, dataSource.getSupportWorkOutputData().getTotalAffiliation(), startRow, 3, false, isCsv);
                startRow += 1;
                itemPerPage += 1;
            }

            if (detailLayoutSetting.getExtractCondition() == EmployeeExtractCondition.EXTRACT_ALL_WORKING_EMPLOYEES
                    || detailLayoutSetting.getExtractCondition() == EmployeeExtractCondition.EXTRACT_EMPLOYEES_COME_TO_SUPPORT) {
                /** support total all */
                // C11_1_2
                cells.get(startRow, 0).setValue(getText("KHA002_142"));
                this.setTotalStyle(cells.get(startRow, 0), true, false, false, true);
                this.setTotalStyle(cells.get(startRow, 1), false, false, false, true);
                this.setTotalStyle(cells.get(startRow, 2), false, false, false, true);

                // C11_2_2 ~ C11_2_2
                this.printTotal(cells, outputItems, dataSource.getSupportWorkOutputData().getTotalSupport(), startRow, 3, false, isCsv);
                startRow += 1;
                itemPerPage += 1;
            }
        }

        /** grand total */
        if (grandTotalDisplay.getDisplayGrandTotal() == NotUseAtr.USE) {
            // C12_1_2
            cells.get(startRow, 0).setValue(getText("KHA002_111"));
            this.setTotalStyle(cells.get(startRow, 0), true, false, true, true);
            this.setTotalStyle(cells.get(startRow, 1), false, false, true, false);
            this.setTotalStyle(cells.get(startRow, 2), false, false, true, false);
            // C12_2_2 ~
            this.printTotal(cells, outputItems, dataSource.getSupportWorkOutputData().getGrandTotal(), startRow, 3, true, isCsv);
        }
    }

    private void pageBreakSetting(Worksheet worksheet, SupportWorkListDataSource dataSource) {
        Cells cells = worksheet.getCells();
        HorizontalPageBreakCollection hPageBreaks = worksheet.getHorizontalPageBreaks();
    }

    private void printTotal(Cells cells, List<OutputItem> outputItems, Optional<TotalValueDetail> totalOpt, int startRow, int startColumn, boolean isFillBgColor, boolean isCsv) {
        for (OutputItem item : outputItems) {
            if (totalOpt.isPresent()) {
                val total = totalOpt.get().getItemValues().stream()
                        .filter(x -> x.getItemId() == item.getAttendanceItemId()).findFirst();
                if (total.isPresent()) {
                    cells.get(startRow, startColumn).putValue(this.formatValue(total.get().getValue(), item.getAttendanceItemId() == 1309 ? ValueType.AMOUNT : ValueType.TIME, isCsv), false);
                }
            }
            if (item.getAttendanceItemId() <= 928)
                this.setTotalStyle(cells.get(startRow, startColumn), false, false, isFillBgColor, true);
            else
                this.setTotalStyle(cells.get(startRow, startColumn), false, false, isFillBgColor, false);
            startColumn += 1;
        }
        if (totalOpt.isPresent()) {
            cells.get(startRow, startColumn).putValue(this.formatValue(String.valueOf(totalOpt.get().getPeopleCount()), ValueType.AMOUNT, isCsv), true);
        }
        this.setTotalStyle(cells.get(startRow, startColumn), false, true, isFillBgColor, false);
    }

    private String getWorkName(int attendanceId, List<Task> workList1, List<Task> workList2, List<Task> workList3, List<Task> workList4, List<Task> workList5) {
        switch (attendanceId) {
            case 924:
                return !workList1.isEmpty() ? workList1.get(0).getDisplayInfo().getTaskName().v() : "";
            case 925:
                return !workList2.isEmpty() ? workList2.get(0).getDisplayInfo().getTaskName().v() : "";
            case 926:
                return !workList3.isEmpty() ? workList3.get(0).getDisplayInfo().getTaskName().v() : "";
            case 927:
                return !workList4.isEmpty() ? workList4.get(0).getDisplayInfo().getTaskName().v() : "";
            case 928:
                return !workList5.isEmpty() ? workList5.get(0).getDisplayInfo().getTaskName().v() : "";
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
        return isGetAll ? code + name : name;
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
        switch (type) {
            case TIME:
                Integer srcValueT = Integer.valueOf(value);
                targetValue = convertNumberToTime(srcValueT);
                break;
            case AMOUNT:
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

    private void setBasicStyle(Cell cell, boolean rightBorder) {
        Style style = new Style();
        style.setHorizontalAlignment(TextAlignmentType.LEFT);
        style.setVerticalAlignment(TextAlignmentType.TOP);
        style.getFont().setName(FONT_NAME);
        style.getFont().setSize(FONT_SIZE);
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

    private void setBorderBottom(Cells cells, int row){
        for (int c = 0; c <= maxColumnInHeader; c++) {
            Style style = cells.get(row - 1, c).getStyle();
            style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.THICK);
//            style.setForegroundColor(Color.fromArgb(255, 0, 0));
            cells.get(row, c).setStyle(style);
        }
    }

    private void setHeaderStyle(Cell cell, boolean firstColumn, boolean lastColumn) {
        Style style = this.commonStyle();
        style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.THICK);
        style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.THICK);
        style.setForegroundColor(Color.fromArgb(155, 194, 230));
        if (firstColumn) {
            style.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.NONE);
        }
        if (lastColumn) {
            style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.NONE);
        }
        cell.setStyle(style);
    }

    private void setTotalStyle(Cell cell, boolean firstColumn, boolean lastColumn, boolean isFillBgColor, boolean isLeftAlignment) {
        Style style = this.commonStyle();
        style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.THIN);
        style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.THIN);

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

    private Style commonStyle() {
        Style commonStyle = new Style();
        commonStyle.getFont().setName(FONT_NAME);
        commonStyle.getFont().setSize(FONT_SIZE);
        commonStyle.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.DOTTED);
        commonStyle.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.DOTTED);
        commonStyle.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.DOTTED);
        commonStyle.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.DOTTED);
        commonStyle.setShrinkToFit(true);
        commonStyle.setPattern(BackgroundType.SOLID);
        commonStyle.setVerticalAlignment(TextAlignmentType.CENTER);
        commonStyle.setHorizontalAlignment(TextAlignmentType.CENTER);
        return commonStyle;
    }
}
