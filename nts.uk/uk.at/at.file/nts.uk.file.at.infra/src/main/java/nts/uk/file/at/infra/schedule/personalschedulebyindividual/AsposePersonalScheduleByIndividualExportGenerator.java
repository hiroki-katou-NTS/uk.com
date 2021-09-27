package nts.uk.file.at.infra.schedule.personalschedulebyindividual;

import com.aspose.cells.BackgroundType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellArea;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.CopyOptions;
import com.aspose.cells.HorizontalPageBreakCollection;
import com.aspose.cells.PageSetup;
import com.aspose.cells.PasteOptions;
import com.aspose.cells.PasteType;
import com.aspose.cells.ShapeCollection;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;
import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.management.DateInformation;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.LegalWorkTimeOfEmployee;
import nts.uk.file.at.app.export.schedule.personalscheduleindividual.PersonalScheduleByIndividualExportGenerator;
import nts.uk.file.at.app.export.schedule.personalscheduleindividual.PersonalScheduleByIndividualQuery;
import nts.uk.file.at.app.export.schedule.personalscheduleindividual.PersonalScheduleIndividualDataSource;
import nts.uk.file.at.app.export.schedule.personalscheduleindividual.WeeklyAgreegateResult;
import nts.uk.file.at.app.export.schedule.personalscheduleindividual.dto.WorkScheduleWorkInforDto;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Stateless
public class AsposePersonalScheduleByIndividualExportGenerator extends AsposeCellsReportGenerator implements PersonalScheduleByIndividualExportGenerator {
    private final String FONT_NAME = "ＭＳ ゴシック";
    private final String EXCEL_EXT = ".xlsx";
    private static final String TEMPLATE_FILE = "report/KSU002.xlsx";
    private static final int NUMBER_ROW_OF_PAGE = 37;
    private final String SPACE = "　";
    private final String COLON = "　：　";
    private static final String PGID = "KNR001";
    private static final String PG = "就業情報端末の登録";
    private static final String SHEET_NAME = "マスタリスト";
    private static final String COMPANY_ERROR = "Company is not found!!!!";

    private final int BG_COLOR_SPECIFIC_DAY = Integer.parseInt("ffc0cb", 16);
    private final int TEXT_COLOR_SUNDAY = Integer.parseInt("ff0000", 16);
    private static final String PRINT_AREA = "A1:AN";
    private static final int MAX_ROW_IN_PAGE = 37;


    @Override
    public void generate(FileGeneratorContext context, PersonalScheduleIndividualDataSource dataSource, PersonalScheduleByIndividualQuery query) {

        try {
            long startTime = System.nanoTime();
            AsposeCellsReportContext reportContext = createContext(TEMPLATE_FILE);
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            Worksheet wsSource = worksheets.get(0);
            if (query.isTotalDisplay()) {
                wsSource = worksheets.get(1);
            }

            //  Worksheet wsDestination = worksheets.get(1);
            pageSetting(wsSource, dataSource);
            printHeader(wsSource, dataSource, query);
            printContent(wsSource, wsSource, dataSource, query);
            worksheets.removeAt(2);
            worksheets.setActiveSheetIndex(1);
            reportContext.processDesigner();

            // Save as excel file
            reportContext.saveAsExcel(createNewFile(context, getReportName(dataSource.getCompanyName() + EXCEL_EXT)));

            long estimatedTime = (System.nanoTime() - startTime) / 1000000000;
            System.out.println("Thoi gian export excel la: " + estimatedTime + " seconds");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * ページヘッダ① : Area A
     */
    private void pageSetting(Worksheet worksheet, PersonalScheduleIndividualDataSource dataSource) {
        PageSetup pageSetup = worksheet.getPageSetup();
        // A1_1
        pageSetup.setHeader(0, "&9&\"MS ゴシック\"" + dataSource.getCompanyName());
        // A1_2
        pageSetup.setHeader(1, "&16&\"MS ゴシック\"" + getText("KSU002_56"));
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm", Locale.JAPAN);
        // A1_3, A1_4
        pageSetup.setHeader(2, "&9&\"MS ゴシック\"" + LocalDateTime.now().format(fullDateTimeFormatter) + "\npage &P");
    }

    private void printHeader(Worksheet worksheet, PersonalScheduleIndividualDataSource dataSource, PersonalScheduleByIndividualQuery query) {
        String b11 = TextResource.localize("KSU002_57");
        Cells cells = worksheet.getCells();
        // B1_1
        cells.get(0, 0).setValue(getText("KSU002_57"));
        // B1_2
        cells.get(0, 3).setValue(dataSource.getWorkplaceInfo().getWorkplaceCode());
        // B1_3
        cells.get(0, 7).setValue(dataSource.getWorkplaceInfo().getWorkplaceName());
        // B2_1
        cells.get(2, 0).setValue(getText("KSU002_59"));
        // B2_2
        cells.get(2, 3).setValue(query.getEmployeeCode());
        // B2_3
        cells.get(2, 7).setValue(query.getEmployeeName());
        // B3_1
        cells.get(2, 7).setValue(GeneralDate.fromString(query.getDate(), "yyyy/MM/dd") .yearMonth());
        if (query.isTotalDisplay()) {
            cells.get(5, 36).setValue(getText("KSU002_68"));
        }
    }


    private void printContent(Worksheet wsDestination, Worksheet wsSource, PersonalScheduleIndividualDataSource dataSource, PersonalScheduleByIndividualQuery query) throws Exception {
        Cells cells = wsDestination.getCells();
        Cells cellsTemplate = wsSource.getCells();
        ShapeCollection shapes = wsDestination.getShapes();
        HorizontalPageBreakCollection hPageBreaks = wsDestination.getHorizontalPageBreaks();
        cells.deleteRows(7, 38);
        List<PersonalScheduleByIndividualFormat> dataBuildList = this.buildData(dataSource);
        // Set CopyOptions.ReferToDestinationSheet to true
        CopyOptions options = new CopyOptions();
        options.setReferToDestinationSheet(true);
//        // Set PasteOptions
        PasteOptions pasteOptions = new PasteOptions();
        pasteOptions.setPasteType(PasteType.ALL);
        pasteOptions.setOnlyVisibleCells(true);

        int rowCount = 7; // start from row index 9
        int pageIndex = 0;
        String divider = getText("KSU002_67");
        for (PersonalScheduleByIndividualFormat item : dataBuildList) {
            cells.copyRows(cellsTemplate, 7, rowCount, 5);
            cells.clearContents(CellArea.createCellArea(rowCount, 0, cells.getMaxRow(), cells.getMaxColumn()));
            int secondLieOfCalender = rowCount + 2;
            int thirdLieOfCalender = rowCount + 4;

            //calender item first for each row
            cells.get(rowCount, 0).setValue(item.getColn1C21());
            cells.get(rowCount, 2).setValue(item.getColn1C22());
            cells.get(secondLieOfCalender, 0).setValue(item.getColn1C231());
            cells.get(secondLieOfCalender, 3).setValue(item.getColn1C232());
            cells.get(thirdLieOfCalender, 0).setValue(item.getColn1C233());
            cells.get(thirdLieOfCalender, 2).setValue(divider);
            cells.get(thirdLieOfCalender, 3).setValue(item.getColn1C234());

            //calender item second for each row
            cells.get(rowCount, 5).setValue(item.getColn2C21());
            cells.get(rowCount, 7).setValue(item.getColn2C22());
            cells.get(secondLieOfCalender, 5).setValue(item.getColn2C231());
            cells.get(secondLieOfCalender, 8).setValue(item.getColn2C232());
            cells.get(thirdLieOfCalender, 5).setValue(item.getColn2C233());
            cells.get(thirdLieOfCalender, 7).setValue(divider);
            cells.get(thirdLieOfCalender, 8).setValue(item.getColn2C234());

            //calender item third for each row
            cells.get(rowCount, 11).setValue(item.getColn3C21());
            cells.get(rowCount, 13).setValue(item.getColn3C22());
            cells.get(secondLieOfCalender, 11).setValue(item.getColn3C231());
            cells.get(secondLieOfCalender, 14).setValue(item.getColn3C232());
            cells.get(thirdLieOfCalender, 11).setValue(item.getColn3C233());
            cells.get(thirdLieOfCalender, 13).setValue(divider);
            cells.get(thirdLieOfCalender, 14).setValue(item.getColn3C234());

            //calender item four for each row
            cells.get(rowCount, 16).setValue(item.getColn4C21());
            cells.get(rowCount, 18).setValue(item.getColn4C22());
            cells.get(secondLieOfCalender, 16).setValue(item.getColn4C231());
            cells.get(secondLieOfCalender, 19).setValue(item.getColn4C232());
            cells.get(thirdLieOfCalender, 16).setValue(item.getColn4C233());
            cells.get(thirdLieOfCalender, 18).setValue(divider);
            cells.get(thirdLieOfCalender, 19).setValue(item.getColn4C234());

            //calender item five for each row
            cells.get(rowCount, 21).setValue(item.getColn5C21());
            cells.get(rowCount, 23).setValue(item.getColn5C22());
            cells.get(secondLieOfCalender, 21).setValue(item.getColn5C231());
            cells.get(secondLieOfCalender, 24).setValue(item.getColn5C232());
            cells.get(thirdLieOfCalender, 21).setValue(item.getColn5C233());
            cells.get(thirdLieOfCalender, 23).setValue(divider);
            cells.get(thirdLieOfCalender, 24).setValue(item.getColn5C234());

            //calender item six for each row
            cells.get(rowCount, 26).setValue(item.getColn6C21());
            cells.get(rowCount, 28).setValue(item.getColn6C22());
            cells.get(secondLieOfCalender, 26).setValue(item.getColn6C231());
            cells.get(secondLieOfCalender, 29).setValue(item.getColn6C232());
            cells.get(thirdLieOfCalender, 26).setValue(item.getColn6C233());
            cells.get(thirdLieOfCalender, 28).setValue(divider);
            cells.get(thirdLieOfCalender, 29).setValue(item.getColn6C234());

            //calender item seven for each row
            cells.get(rowCount, 31).setValue(item.getColn7C21());
            cells.get(rowCount, 33).setValue(item.getColn7C22());
            cells.get(secondLieOfCalender, 31).setValue(item.getColn7C231());
            cells.get(secondLieOfCalender, 34).setValue(item.getColn7C232());
            cells.get(thirdLieOfCalender, 31).setValue(item.getColn7C233());
            cells.get(thirdLieOfCalender, 33).setValue(divider);
            cells.get(thirdLieOfCalender, 34).setValue(item.getColn7C234());
            if (query.isTotalDisplay()) {
                //calender item seven for each row
                cells.get(rowCount, 36).setValue(item.getD21());
                cells.get(rowCount + 1, 36).setValue(item.getD22());
                cells.get(rowCount + 1, 39).setValue(item.getD23());
                cells.get(rowCount + 2, 36).setValue(item.getD24());
                cells.get(rowCount + 2, 39).setValue(item.getD25());
                cells.get(rowCount + 3, 36).setValue(item.getD26());
                cells.get(rowCount + 3, 39).setValue(item.getD27());
            }
            rowCount += 5;
            // Check paging
            if (isNextPage(rowCount, pageIndex)) {
                PasteOptions opts = new PasteOptions();
                opts.setPasteType(PasteType.FORMATS);
                cells.copyRows(cellsTemplate, 36, rowCount, 1, options);  // copy close ruler
                removeTopBorder(cells.get(rowCount, cells.getMaxColumn()));
                rowCount += 1;     // close ruler
                hPageBreaks.add(rowCount);
                pageIndex += 1;
            }
        }
        PageSetup pageSetup = wsDestination.getPageSetup();
        pageSetup.setPrintArea(PRINT_AREA + rowCount);
    }

    private void removeTopBorder(Cell cell) {
        Style style = cell.getStyle();
        style.setBorder(BorderType.TOP_BORDER, CellBorderType.NONE, Color.getEmpty());
        style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.NONE);
        cell.setStyle(style);
    }

    private boolean isNextPage(int rowCount, int pageIndex) {
        return (rowCount - (MAX_ROW_IN_PAGE * pageIndex)) - 8 > MAX_ROW_IN_PAGE;
    }

    private List<PersonalScheduleByIndividualFormat> buildData(PersonalScheduleIndividualDataSource dataSource) {
        List<DateInformation> dateInfolist = dataSource.getDateInformationList();
        List<WorkScheduleWorkInforDto> workInforDtoList = dataSource.getWorkInforDtoList();
        List<WeeklyAgreegateResult> weeklyAgreegateResults = dataSource.getAgreegateResults();
        Optional<LegalWorkTimeOfEmployee> legalWorktime = dataSource.getLegalWorkTimeOfEmployee();
        int count = 0;
        boolean isFirst = true;
        List<PersonalScheduleByIndividualFormat> dataList = new ArrayList<>();
        PersonalScheduleByIndividualFormat format = new PersonalScheduleByIndividualFormat();
        String divider = getText("KSU002_67");
        String d11 = getText("KSU002_68");
        String d12 = getText("KSU002_69");
        String d21 = getText("KSU002_70") + divider + getText("KSU002_75");
        String d22 = getText("KSU002_76");
        String d26 = getText("KSU002_78");
        String d24 = getText("KSU002_77");
        int weekCount = 0;
        for (DateInformation dateInfo : dateInfolist) {
            if (count == 0) {
                format.setColn1C21(getDate(dateInfo.getYmd(), isFirst));
                format.setColn1C22(getEvenCompany(dateInfo));
                val workDetail = getWorkDetails(dateInfo, workInforDtoList);
                format.setColn1C231(workTypeCodeAndName(workDetail));
                format.setColn1C232(workHourCode(workDetail));
                if (workDetail.isPresent()) {
                    format.setColn1C233(workDetail.get().getStartTime().isPresent() ? workDetail.get().getStartTime().get() : null);
                    format.setColn1C234(workDetail.get().getEndTime().isPresent() ? workDetail.get().getEndTime().get() : null);
                }
                isFirst = false;
            }
            if (count == 1) {
                format.setColn2C21(getDate(dateInfo.getYmd(), false));
                format.setColn2C22(getEvenCompany(dateInfo));
                val workDetail = getWorkDetails(dateInfo, workInforDtoList);
                format.setColn2C231(workTypeCodeAndName(workDetail));
                format.setColn2C232(workHourCode(workDetail));
                if (workDetail.isPresent()) {
                    format.setColn2C233(workDetail.get().getStartTime().isPresent() ? workDetail.get().getStartTime().get() : null);
                    format.setColn2C234(workDetail.get().getEndTime().isPresent() ? workDetail.get().getEndTime().get() : null);
                }
            }
            if (count == 2) {
                format.setColn3C21(getDate(dateInfo.getYmd(), false));
                format.setColn3C22(getEvenCompany(dateInfo));
                val workDetail = getWorkDetails(dateInfo, workInforDtoList);
                format.setColn3C231(workTypeCodeAndName(workDetail));
                format.setColn3C232(workHourCode(workDetail));
                if (workDetail.isPresent()) {
                    format.setColn3C233(workDetail.get().getStartTime().isPresent() ? workDetail.get().getStartTime().get() : null);
                    format.setColn3C234(workDetail.get().getEndTime().isPresent() ? workDetail.get().getEndTime().get() : null);
                }
            }
            if (count == 3) {
                format.setColn4C21(getDate(dateInfo.getYmd(), false));
                format.setColn4C22(getEvenCompany(dateInfo));
                val workDetail = getWorkDetails(dateInfo, workInforDtoList);
                format.setColn4C231(workTypeCodeAndName(workDetail));
                format.setColn4C232(workHourCode(workDetail));
                if (workDetail.isPresent()) {
                    format.setColn4C233(workDetail.get().getStartTime().isPresent() ? workDetail.get().getStartTime().get() : null);
                    format.setColn4C234(workDetail.get().getEndTime().isPresent() ? workDetail.get().getEndTime().get() : null);
                }
            }
            if (count == 4) {
                format.setColn5C21(getDate(dateInfo.getYmd(), false));
                format.setColn5C22(getEvenCompany(dateInfo));
                val workDetail = getWorkDetails(dateInfo, workInforDtoList);
                format.setColn5C231(workTypeCodeAndName(workDetail));
                format.setColn5C232(workHourCode(workDetail));
                if (workDetail.isPresent()) {
                    format.setColn5C233(workDetail.get().getStartTime().isPresent() ? workDetail.get().getStartTime().get() : null);
                    format.setColn5C234(workDetail.get().getEndTime().isPresent() ? workDetail.get().getEndTime().get() : null);
                }
            }
            if (count == 5) {
                format.setColn6C21(getDate(dateInfo.getYmd(), false));
                format.setColn6C22(getEvenCompany(dateInfo));
                val workDetail = getWorkDetails(dateInfo, workInforDtoList);
                format.setColn6C231(workTypeCodeAndName(workDetail));
                format.setColn6C232(workHourCode(workDetail));
                if (workDetail.isPresent()) {
                    format.setColn6C233(workDetail.get().getStartTime().isPresent() ? workDetail.get().getStartTime().get() : null);
                    format.setColn6C234(workDetail.get().getEndTime().isPresent() ? workDetail.get().getEndTime().get() : null);
                }
            }
            if (count == 6) {
                format.setColn7C21(getDate(dateInfo.getYmd(), false));
                format.setColn7C22(getEvenCompany(dateInfo));
                val workDetail = getWorkDetails(dateInfo, workInforDtoList);
                format.setColn7C231(workTypeCodeAndName(workDetail));
                format.setColn7C232(workHourCode(workDetail));
                if (workDetail.isPresent()) {
                    format.setColn7C233(workDetail.get().getStartTime().isPresent() ? workDetail.get().getStartTime().get() : null);
                    format.setColn7C234(workDetail.get().getEndTime().isPresent() ? workDetail.get().getEndTime().get() : null);
                }
            }
            count++;
            if (count > 6) {
                count = 0;
                weekCount++;
                Optional<WeeklyAgreegateResult> weekTotal = weekTotal(weeklyAgreegateResults, weekCount);
                if (weekTotal.isPresent()) {
                    format.setD27(weekTotal.get().getHolidays());
                    format.setD23(weekTotal.get().getWorkingHours());
                }
                if (!legalWorktime.isPresent()) {
                    format.setD25("0");
                }
                if (legalWorktime.isPresent()) {
                    if (legalWorktime.get().getWeeklyEstimateTime().isPresent()) {
                        format.setD25(legalWorktime.get().getWeeklyEstimateTime().get().hour() + ":" + legalWorktime.get().getWeeklyEstimateTime().get().minute());
                    }
                }
                format.setD11(d11);
                format.setD12(d12);
                format.setD21(d21);
                format.setD22(d22);
                format.setD22(d26);
                format.setD24(d26);
                format.setFromTo(divider);
                dataList.add(format);
                format = new PersonalScheduleByIndividualFormat();
            }
        }
        return dataList;
    }

    private Optional<WeeklyAgreegateResult> weekTotal(List<WeeklyAgreegateResult> weeklyAgreegateResults, int weekCount) {
        return weeklyAgreegateResults.stream().filter(x -> x.getWeek() == weekCount).findFirst();
    }


    private String getDate(GeneralDate date, boolean isMdFormat) {
        if (date.day() == 1 || isMdFormat) {
            return (date.month() + 1) + "/" + date.day();
        }
        return String.valueOf(date.day());
    }

    private String getEvenCompany(DateInformation dateInformation) {
        if (dateInformation.getOptCompanyEventName().isPresent()) {
            return dateInformation.getOptCompanyEventName().get().v();
        }
        if (dateInformation.getOptWorkplaceEventName().isPresent()) {
            return dateInformation.getOptWorkplaceEventName().get().v();
        }
        return "";
    }

    private Optional<WorkScheduleWorkInforDto> getWorkDetails(DateInformation dateInformation,
                                                              List<WorkScheduleWorkInforDto> workInforDtos) {
        return workInforDtos.stream().filter(x -> x.getDate().equals(dateInformation.getYmd())).findFirst();
    }

    private String workTypeCodeAndName(Optional<WorkScheduleWorkInforDto> workInforDto) {
        if (!workInforDto.isPresent()) {
            return "";
        }
        val inforDto = workInforDto.get();
        if (inforDto.getWorkTypeCode().isPresent() &&
                (inforDto.getWorkTypeName().isPresent() && inforDto.getWorkTypeName().get().isEmpty())) {
            return inforDto.getWorkTypeCode().get() + "" + getText("KSU002_79");
        }
        if (inforDto.getWorkTypeCode().isPresent() &&
                (inforDto.getWorkTypeName().isPresent() && !inforDto.getWorkTypeName().get().isEmpty())) {
            return inforDto.getWorkTypeName().get();
        }
        return "";
    }

    private String workHourCode(Optional<WorkScheduleWorkInforDto> workInforDto) {
        if (!workInforDto.isPresent()) {
            return "";
        }
        val inforDto = workInforDto.get();
        if (inforDto.getWorkingHoursCode().isPresent() &&
                (inforDto.getWorkingHoursCode().isPresent() && inforDto.getWorkingHoursName().get().isEmpty())) {
            return inforDto.getWorkingHoursCode().get() + "" + getText("KSU002_79");
        }
        if (inforDto.getWorkingHoursCode().isPresent() &&
                (inforDto.getWorkingHoursName().isPresent() && !inforDto.getWorkingHoursName().get().isEmpty())) {
            return inforDto.getWorkingHoursName().get();
        }
        return "";
    }


    private void setHeaderStyle(Cell cell, DateInformation dateInfo, boolean isDate) {
        Style style = cell.getStyle();
        style.setHorizontalAlignment(TextAlignmentType.CENTER);
        style.setVerticalAlignment(TextAlignmentType.CENTER);
        if (isDate) {
            style.setTextWrapped(true);
        } else {
            style.setShrinkToFit(true);
        }
        style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.THIN);
        style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.THIN);
        style.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.THIN);
        style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.THIN);
        style.setPattern(BackgroundType.SOLID);
        if (dateInfo.isSpecificDay()) {
            style.setForegroundColor(Color.fromArgb(BG_COLOR_SPECIFIC_DAY));
            style.getFont().setColor(Color.fromArgb(TEXT_COLOR_SUNDAY));
        }
        style.getFont().setName(FONT_NAME);
        style.getFont().setSize(9);
        cell.setStyle(style);
    }

    private void setPersonalStyle(Cells cells, int row, int column, boolean isFirstRow) {
        Style style = cells.get(row, column).getStyle();
        style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.THIN);
        style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.THIN);
        style.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.THIN);
        style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.THIN);
        style.getFont().setName(FONT_NAME);
        style.getFont().setSize(9);
        style.setShrinkToFit(true);
        style.setPattern(BackgroundType.SOLID);
        style.setVerticalAlignment(TextAlignmentType.CENTER);
        if (isFirstRow) style.setForegroundColor(Color.fromArgb(221, 235, 247));
        cells.get(row, column).setStyle(style);
    }

    private String getText(String resourceId) {
        return TextResource.localize(resourceId);
    }

    private void removeTemplate(Worksheet worksheet) {
        removeFirstShapes(worksheet);
        Cells cells = worksheet.getCells();
        cells.deleteRows(0, NUMBER_ROW_OF_PAGE);
    }

    private void removeFirstShapes(Worksheet worksheet) {
        if (worksheet.getShapes().getCount() > 0) {
            worksheet.getShapes().removeAt(0);
        }
    }

    private void setTopBorderStyle(Cell cell) {
        Style style = cell.getStyle();
        style.setBorder(BorderType.TOP_BORDER, CellBorderType.MEDIUM, Color.getBlack());
        cell.setStyle(style);
    }

    private void setBottomBorderStyle(Cell cell) {
        Style style = cell.getStyle();
        style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.MEDIUM, Color.getBlack());
        cell.setStyle(style);
    }

    private void setBackgroundGray(Cell cell) {
        Style style = cell.getStyle();
        style.setForegroundColor(Color.getGray());
        cell.setStyle(style);
    }

    private void setCurrentMonthBackground(Cell cell) {
        Style style = cell.getStyle();
        style.setForegroundColor(Color.fromArgb(197, 217, 241));
        cell.setStyle(style);
    }

    private void setForegroundRed(Cell cell) {
        Style style = cell.getStyle();
        style.getFont().setColor(Color.getRed());
        cell.setStyle(style);
    }
}
