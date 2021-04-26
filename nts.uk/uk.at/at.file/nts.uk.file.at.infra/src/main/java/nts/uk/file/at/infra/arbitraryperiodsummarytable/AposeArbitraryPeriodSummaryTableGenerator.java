package nts.uk.file.at.infra.arbitraryperiodsummarytable;

import com.aspose.cells.*;
import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.app.query.arbitraryperiodsummarytable.*;
import nts.uk.ctx.at.function.dom.arbitraryperiodsummarytable.OutputSettingOfArbitrary;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.PrimitiveValueOfAttendanceItem;
import nts.uk.ctx.sys.gateway.dom.adapter.company.CompanyBsImport;
import nts.uk.file.at.app.export.arbitraryperiodsummarytable.ArbitraryPeriodSummaryDto;
import nts.uk.file.at.app.export.arbitraryperiodsummarytable.ArbitraryPeriodSummaryTableGenerator;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AposeArbitraryPeriodSummaryTableGenerator extends AsposeCellsReportGenerator implements ArbitraryPeriodSummaryTableGenerator {
    private static final String TEMPLATE_FILE_ADD = "report/KWR07.xlsx";
    private static final String EXCEL_EXT = ".xlsx";
    private static final String PRINT_AREA = "";
    private static final int MAX_ITEM_ONE_LINE = 20;
    private static final String FORMAT_DATE = "yyyy/MM/dd";
    private static final int MAX_LINE_IN_PAGE = 24;


    @Override
    public void generate(FileGeneratorContext generatorContext, ArbitraryPeriodSummaryDto dataSource) {
        try {
            AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE_ADD);
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            OutputSettingOfArbitrary ofArbitrary = dataSource.getOfArbitrary();
            String title = ofArbitrary != null ? ofArbitrary.getName().v() : "";

            Worksheet worksheetTemplate = worksheets.get(0);
            Worksheet worksheet = worksheets.get(1);

            worksheet.setName(title);

            settingPage(worksheet, dataSource, title);
            printContents(worksheetTemplate, worksheet, dataSource);
            worksheets.removeAt(0);
            worksheets.setActiveSheetIndex(0);
            reportContext.processDesigner();
            String fileName = title + "_" + GeneralDateTime.now().toString("yyyyMMddHHmmss");
            reportContext.saveAsExcel(this.createNewFile(generatorContext, fileName + EXCEL_EXT));


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void settingPage(Worksheet worksheet,
                             ArbitraryPeriodSummaryDto dataSource, String title) {
        CompanyBsImport companyBsImport = dataSource.getCompanyInfo();
        String companyName = companyBsImport.getCompanyName();
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setPaperSize(PaperSizeType.PAPER_A_4);
        pageSetup.setOrientation(PageOrientationType.LANDSCAPE);

        pageSetup.setHeader(0, "&7&\"ＭＳ フォントサイズ\"" + companyName);
        pageSetup.setHeader(1, "&9&\"ＭＳ フォントサイズ\"" + title);

        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter
                .ofPattern("yyyy/MM/dd  H:mm", Locale.JAPAN);
        pageSetup.setHeader(2,
                "&9&\"MS フォントサイズ\"" + LocalDateTime.now().format(fullDateTimeFormatter) + "\n" +
                        TextResource.localize("page") + " &P");
        pageSetup.setZoom(100);
    }

    private void printContents(Worksheet worksheetTemplate, Worksheet worksheet, ArbitraryPeriodSummaryDto dataSource) {
        try {
            HorizontalPageBreakCollection pageBreaks = worksheet.getHorizontalPageBreaks();
            DetailOfArbitrarySchedule data = dataSource.getContent();
            if (data != null) {
                val query = dataSource.getQuery();
                List<AttendanceItemDisplayContents> contentsList = data.getContentsList();
                Map<Integer, CommonAttributesOfForms> mapIdAnAttribute =
                        contentsList.stream()
                                .filter(distinctByKey(AttendanceItemDisplayContents::getAttendanceItemId))
                                .collect(Collectors.toMap(AttendanceItemDisplayContents::getAttendanceItemId, AttendanceItemDisplayContents::getCommonAttributesOfForms));
                List<AttendanceDetailDisplayContents> detailDisplayContents = data.getDetailDisplayContents();
                List<WorkplaceTotalDisplayContent> totalDisplayContents = data.getTotalDisplayContents()
                        .stream().sorted(Comparator.comparing(WorkplaceTotalDisplayContent::getHierarchyCode)).collect(Collectors.toList());
                List<DisplayContent> totalAll = data.getTotalAll();
                List<CumulativeWorkplaceDisplayContent> cumulativeWorkplaceDisplayContents =
                        data.getCumulativeWorkplaceDisplayContents();
                DatePeriod period = dataSource.getPeriod();
                int count = 0;
                Cells cellsTemplate = worksheetTemplate.getCells();
                Cells cells = worksheet.getCells();
                int itemOnePage = 0;
                boolean isBreak = false;
                boolean isPageBreakByWpl = query.isPageBreakByWpl();
                    // C1_1
                printInfo(worksheetTemplate, worksheet, contentsList, period);
                count += 5;
                itemOnePage += 5;
                for (int i = 0; i < detailDisplayContents.size(); i++) {

                    val content = detailDisplayContents.get(i);
                    int wplHierarchy = content.getHierarchyCode().length() / 3;
                    int pageBreakWplHierarchy = query.getPageBreakWplHierarchy();
                    if ((isPageBreakByWpl && i >= 1) && !isBreak) {
                        if (wplHierarchy > pageBreakWplHierarchy) {
                            pageBreaks.add(count);
                            cells.copyRows(cells, 0, count, 5);
                            count += 5;
                            itemOnePage = 5;
                            isBreak = true;
                            isPageBreakByWpl = false;
                        }
                    }
                    val listDisplaySid = content.getListDisplayedEmployees();
                    val tComparator = Comparator
                            .comparing(DisplayedEmployee::getEmployeeId);
                    val listDisplayedEmployees = listDisplaySid.stream()
                            .sorted(tComparator).collect(Collectors.toList());
                    if ((MAX_LINE_IN_PAGE - count) <= 5) {
                        pageBreaks.add(count);
                        cells.copyRows(cells, 0, count, 5);
                        count += 5;
                        itemOnePage = 5;
                    }
                    cells.copyRow(cellsTemplate, 5, count);
                    itemOnePage += 1;
                    //D1_1
                    cells.get(count, 0).setValue(TextResource.localize("KWR007_303")
                            + " " + content.getWorkplaceCd() + " " + content.getWorkplaceName());
                    count += 1;
                    for (int j = 0; j < listDisplayedEmployees.size(); j++) {
                        val item = listDisplayedEmployees.get(j);
                        if (j % 2 == 0) {
                            if (!checkLine(itemOnePage, MAX_LINE_IN_PAGE)) {
                                pageBreaks.add(count);
                                cells.copyRows(cells, 0, count, 5);
                                count += 5;
                                itemOnePage = 5;
                            }
                            cells.copyRows(cellsTemplate, 6, count, 2);

                        } else {
                            if (!checkLine(itemOnePage, MAX_LINE_IN_PAGE)) {
                                pageBreaks.add(count);
                                cells.copyRows(cells, 0, count, 5);
                                itemOnePage = 5;
                                count += 5;
                            }
                            cells.copyRows(cellsTemplate, 8, count, 2);
                        }
                        cells.get(count, 0).setValue(
                                item.getEmployeeCode() + " " + item.getEmployeeName());
                        itemOnePage += 2;
                        val contents = item.getContentList();
                        for (int k = 0; k < contents.size(); k++) {
                            if (k < 20) {
                                val itemLine1 = contents.get(k);
                                cells.get(count, 1 + k).setValue(formatValue(itemLine1.getValue()
                                        , mapIdAnAttribute.getOrDefault(itemLine1.getAttendanceItemId(), null), query.isZeroDisplay()));
                            } else if (k >= 20 && k < 40) {
                                val itemLine2 = contents.get(k);
                                cells.get(count + 1, 1 + k - 20).setValue(formatValue(itemLine2.getValue()
                                        , mapIdAnAttribute.getOrDefault(itemLine2.getAttendanceItemId(), null), query.isZeroDisplay()));
                            }

                        }
                        count += 2;
                    }
                    if (query.isWorkplaceTotal()) {
                        val sumByWplOpt = totalDisplayContents
                                .stream().filter(e -> e.getWorkplaceId().equals(content.getWorkplaceId())).findFirst();
                        if (sumByWplOpt.isPresent()) {
                            val sumByWpl = sumByWplOpt.get();
                            if (!checkLine(itemOnePage, MAX_LINE_IN_PAGE)) {
                                pageBreaks.add(count);
                                itemOnePage = 5;
                                cells.copyRows(cells, 0, count, 5);
                                count += 5;
                            }
                            cells.copyRows(cellsTemplate, 10, count, 2);
                            itemOnePage += 2;
                            // cells.clearContents(10, count, cells.getMaxRow(), cells.getMaxColumn());
                            cells.get(count, 0).setValue(TextResource.localize("KWR007_304"));
                            val listSum = sumByWpl.getListOfWorkplaces();
                            if (listSum != null) {
                                int k = 0;
                                while (k < listSum.size()) {
                                    // Line 01:
                                    if (k < 20) {
                                        val itemline1 = listSum.get(k);
                                        cells.get(count, 1 + k).setValue(formatValue(itemline1.getValue()
                                                , mapIdAnAttribute.getOrDefault(itemline1.getAttendanceItemId(), null), query.isZeroDisplay()));
                                    } else if (k >= 20 && k < 40) {
                                        val itemline2 = listSum.get(k);
                                        cells.get(count + 1, 1 + k - 20).setValue(formatValue(itemline2.getValue()
                                                , mapIdAnAttribute.getOrDefault(itemline2.getAttendanceItemId(), null), query.isZeroDisplay()));
                                    }

                                    k++;
                                }
                            }
                            count += 2;
                        }
                    }
                }

                if (query.isCumulativeWorkplace()) {
                    for (CumulativeWorkplaceDisplayContent item : cumulativeWorkplaceDisplayContents) {
                        val listValue = item.getListOfWorkplaces();
                        if (!checkLine(itemOnePage, MAX_LINE_IN_PAGE)) {
                            pageBreaks.add(count);
                            itemOnePage = 5;
                            cells.copyRows(cells, 0, count, 5);
                            count += 5;
                        }
                        cells.copyRows(cellsTemplate, 10, count, 2);
                        cells.get(count, 0).setValue(TextResource.localize("KWR007_305"," " +String.valueOf(item.getHierarchy())));
                        int k = 0;
                        itemOnePage += 2;
                        while (k < listValue.size()) {
                            if (k < 20) {
                                val itemLine1 = listValue.get(k);

                                // Line 01:
                                cells.get(count, 1 + k).setValue(formatValue(itemLine1.getValue()
                                        , mapIdAnAttribute.getOrDefault(itemLine1.getAttendanceItemId(), null), query.isZeroDisplay()));
                            } else if (k >= 20 && k < 40) {
                                val itemLine2 = listValue.get(k);
                                cells.get(count + 1, 1 + k -20).setValue(formatValue(itemLine2.getValue()
                                        , mapIdAnAttribute.getOrDefault(itemLine2.getAttendanceItemId(), null), query.isZeroDisplay()));
                            }

                            k++;
                        }
                        count += 2;
                    }

                }
                if (query.isTotal()) {
                    if (!checkLine(itemOnePage, MAX_LINE_IN_PAGE)) {
                        pageBreaks.add(count);
                        cells.copyRows(cells, 0, count, 5);
                        count += 5;
                    }
                    cells.copyRows(cellsTemplate, 10, count, 2);
                    cells.get(count, 0).setValue(TextResource.localize("KWR007_306"));
                    for (int k = 0; k < totalAll.size(); k++)
                        if (k < 20) {
                            // Line 01:
                            val itemLine1 = totalAll.get(k);
                            cells.get(count, 1 + k).setValue(formatValue(itemLine1.getValue()
                                    , mapIdAnAttribute.getOrDefault(itemLine1.getAttendanceItemId(), null), query.isZeroDisplay()));
                        } else if (k >= 20 && k < 40) {
                            val itemLine2 = totalAll.get(k);
                            cells.get(count + 1, 1 + k -20).setValue(formatValue(itemLine2.getValue()
                                    , mapIdAnAttribute.getOrDefault(itemLine2.getAttendanceItemId(), null), query.isZeroDisplay()));
                        }


                }
                PageSetup pageSetup = worksheet.getPageSetup();
                pageSetup.setPrintArea(PRINT_AREA + count);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    private void printInfo(Worksheet worksheetTemplate, Worksheet worksheet,
                           List<AttendanceItemDisplayContents> contentsList, DatePeriod datePeriod) throws Exception {
        Cells cellsTemplate = worksheetTemplate.getCells();
        Cells cells = worksheet.getCells();
        cells.copyRows(cellsTemplate, 0, 0, 5);
        cells.clearContents(0, 0, cells.getMaxRow(), cells.getMaxColumn());

        cells.get(0, 0).setValue(TextResource.localize("KWR007_301")
                + datePeriod.start().toString(FORMAT_DATE) + TextResource.localize("KWR007_307")
                + datePeriod.end().toString(FORMAT_DATE));

        cells.get(1, 0).setValue(TextResource.localize("KWR007_302"));
        for (int i = 0; i < contentsList.size(); i++)
            if (i < 20) {
                cells.get(1, 1 + i).setValue(contentsList.get(i).getAttendanceName());
            } else if (i >= 20 && i < 40) {
                cells.get(3, 1 + i - 20).setValue(contentsList.get(i).getAttendanceName());
            }
    }

    /**
     * Format value
     */
    private String formatValue(Double valueDouble, CommonAttributesOfForms attributes, Boolean isZeroDisplay) {
        String rs = "";
        switch (attributes) {
            case TIME_OF_DAY:
                if (valueDouble != null) {
                    rs = convertToTime((int) valueDouble.intValue());
                }

                break;
            case DAYS:
                if (valueDouble != null) {
                    if (valueDouble != 0 || isZeroDisplay) {
                        DecimalFormat formatter2 = new DecimalFormat("#.#");
                        rs = formatter2.format(valueDouble) + TextResource.localize("KWR_1");
                    }
                }
                break;
            case TIME:
                if (valueDouble != null) {
                    val minute = (int) valueDouble.intValue();
                    if (minute != 0 || isZeroDisplay) {
                        rs = convertToTime(minute);
                    }
                }
                break;
            case AMOUNT_OF_MONEY:
                if (valueDouble != null) {
                    if (valueDouble != 0 || isZeroDisplay) {
                        DecimalFormat formatter3 = new DecimalFormat("#,###");
                        rs = formatter3.format((int) valueDouble.intValue()) + TextResource.localize("KWR_3");
                    }
                }
                break;
            case NUMBER_OF_TIMES:
                if (valueDouble != null) {
                    if (valueDouble != 0 || isZeroDisplay) {
                        DecimalFormat formatter1 = new DecimalFormat("#.#");
                        rs = formatter1.format(valueDouble) + TextResource.localize("KWR_2");
                    }
                }

                break;
        }
        return rs;
    }

    /**
     * Convert minute to HH:mm
     */
    private String convertToTime(int minute) {
        val minuteAbs = Math.abs(minute);
        int hours = minuteAbs / 60;
        int minutes = minuteAbs % 60;
        return (minute < 0 ? "-" : "") + String.format("%d:%02d", hours, minutes);
    }

    private boolean checkCode(boolean isCode, Integer primitive) {
        val listAtt = Arrays.asList(
                PrimitiveValueOfAttendanceItem.WORKPLACE_CD,
                PrimitiveValueOfAttendanceItem.POSITION_CD,
                PrimitiveValueOfAttendanceItem.CLASSIFICATION_CD,
                PrimitiveValueOfAttendanceItem.EMP_CTG_CD,
                PrimitiveValueOfAttendanceItem.WORK_TYPE_DIFFERENT_CD);

        return primitive != null && isCode && listAtt.stream().anyMatch(x -> x.value == primitive);
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    private boolean checkLine(int count, int maxItem) {
        return (maxItem - count) >= 2;
    }

}
