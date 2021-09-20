package nts.uk.file.at.infra.schedule.personalschedulebyindividual;

import com.aspose.cells.BackgroundType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.PageOrientationType;
import com.aspose.cells.PageSetup;
import com.aspose.cells.PaperSizeType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Worksheet;
import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.schedule.dom.shift.management.DateInformation;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.LegalWorkTimeOfEmployee;
import nts.uk.file.at.app.export.schedule.personalscheduleindividual.PersonalScheduleByIndividualExportGenerator;
import nts.uk.file.at.app.export.schedule.personalscheduleindividual.PersonalScheduleIndividualDataSource;
import nts.uk.file.at.app.export.schedule.personalscheduleindividual.WeeklyAgreegateResult;
import nts.uk.file.at.app.export.schedule.personalscheduleindividual.dto.WorkScheduleWorkInforDto;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Stateless
public class AsposePersonalScheduleByIndividualExportGenerator extends AsposeCellsReportGenerator implements PersonalScheduleByIndividualExportGenerator {
    private final String FONT_NAME = "ＭＳ ゴシック";
    private final String EXCEL_EXT = ".xlsx";
    private static final String TEMPLATE_FILE = "report/KSU002B.xlsx";
    private static final int NUMBER_ROW_OF_PAGE = 37;
    private final String SPACE = "　";
    private final String COLON = "　：　";
    private static final String PGID = "KNR001";
    private static final String PG = "就業情報端末の登録";
    private static final String SHEET_NAME = "マスタリスト";
    private static final String COMPANY_ERROR = "Company is not found!!!!";

    private final int BG_COLOR_SPECIFIC_DAY = Integer.parseInt("ffc0cb", 16);
    private final int TEXT_COLOR_SUNDAY = Integer.parseInt("ff0000", 16);


    @Override
    public void generate(FileGeneratorContext context, PersonalScheduleIndividualDataSource dataSource) {

        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
            Worksheet worksheet = reportContext.getWorkbook().getWorksheets().get(0);

            // printHeader(worksheet, reportContext);

            // set data source named "item"
            reportContext.setDataSource("item", this.buildData(dataSource));
            // process data binginds in template
            reportContext.processDesigner();

            //merge if isEmpty(ipAddress) == true
            // mergeMacAndIp(worksheet, dataSource);
            //delete empty row if no data
            // deleteTemplateRow(worksheet, dataSource);

            // save as Excel file
            GeneralDateTime dateNow = GeneralDateTime.now();
            String dateTime = dateNow.toString("yyyyMMddHHmmss");
            String fileName = PGID + PG + "_" + dateTime + ".xlsx";
            OutputStream outputStream = this.createNewFile(context, fileName);
            reportContext.saveAsExcel(outputStream);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void pageSetting(Worksheet worksheet, PersonalScheduleIndividualDataSource dataSource) {
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setPaperSize(PaperSizeType.PAPER_A_4);
        pageSetup.setOrientation(PageOrientationType.LANDSCAPE);
        String a11 = dataSource.getCompanyName();
        val a12 = TextResource.localize("KSU002_56");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        val a13 = formatter.format(new Date());
//        pageSetup.setFitToPagesTall(0);
//        pageSetup.setFitToPagesWide(1);
//        pageSetup.setTopMarginInch(0.98);
//        pageSetup.setBottomMarginInch(0.39);
//        pageSetup.setLeftMarginInch(0.39);
//        pageSetup.setRightMarginInch(0.39);
//        pageSetup.setHeaderMarginInch(0.39);
//        pageSetup.setFooterMarginInch(0.31);
//        pageSetup.setCenterHorizontally(true);
        pageSetup.setHeader(0, "&9&\"MS ゴシック\"" + "Company_Name");
        pageSetup.setHeader(1, "&16&\"MS ゴシック\"" + "title");
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm", Locale.JAPAN);
        pageSetup.setHeader(2, "&9&\"MS ゴシック\"" + LocalDateTime.now().format(fullDateTimeFormatter) + "\npage &P");
    }

    private void printHeader(Worksheet worksheet, PersonalScheduleIndividualDataSource dataSource) {
        String b11 = TextResource.localize("KSU002_57");

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
