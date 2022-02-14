package nts.uk.file.at.infra.form9;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.aspose.cells.BackgroundType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.CellsHelper;
import com.aspose.cells.Color;
import com.aspose.cells.PageSetup;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.layer.app.file.storage.FileStorage;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.layer.infra.file.storage.StoredFileInfoRepository;
import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.arc.system.ServerSystemProperties;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.DayOfWeek;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.clock.ClockHourMinute;
import nts.uk.ctx.at.aggregation.dom.common.ScheRecAtr;
import nts.uk.ctx.at.aggregation.dom.form9.Form9Cover;
import nts.uk.ctx.at.aggregation.dom.form9.Form9OutputEmployeeInfo;
import nts.uk.ctx.at.aggregation.dom.form9.Form9OutputMedicalTime;
import nts.uk.ctx.at.aggregation.dom.form9.Form9TimeRoundingSetting;
import nts.uk.ctx.at.aggregation.dom.form9.MedicalTimeOfEmployee;
import nts.uk.ctx.at.aggregation.dom.form9.RoundingUnit;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.LicenseClassification;
import nts.uk.ctx.sys.portal.dom.enums.MenuAtr;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenuRepository;
import nts.uk.file.at.app.export.form9.Form9ExcelByFormatDataSource;
import nts.uk.file.at.app.export.form9.Form9ExcelByFormatExportGenerator;
import nts.uk.file.at.app.export.form9.Form9ExcelByFormatQuery;
import nts.uk.file.at.app.export.form9.dto.DisplayInfoRelatedToWorkplaceGroupDto;
import nts.uk.file.at.app.export.form9.dto.Form9ColorSettingsDTO;
import nts.uk.file.at.app.export.form9.dto.Form9DisplayTarget;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposeForm9ExcelByFormatExportGenerator extends AsposeCellsReportGenerator implements Form9ExcelByFormatExportGenerator {
    private static final String SYSTEM_TEMPLATE_PATH = "report/form9/";
    private static final String USER_TEMPLATE_PATH = ServerSystemProperties.fileStoragePath();
    private static final String EXCEL_EXT = ".xlsx";
    private final String EMPTY = "";
    private static int MINUTES_IN_AN_HOUR = 60;
    private static final String HOUR = "時";
    private static final String MINUTE = "分";
    private static final String DAY = "日";
    private static final String MONTH = "月";
    private static final String YEAR = "年";

    @Inject
    private StandardMenuRepository standardMenuRepo;

    @Inject
    private StoredFileInfoRepository storedFileInfoRepo;

    @Inject
    private FileStorage fileStorage;

    @Inject
    private StoredFileStreamService fileStreamService;

    @Override
    public void generate(FileGeneratorContext context, Form9ExcelByFormatDataSource dataSource, Form9ExcelByFormatQuery query) {
        try {
            val menus = standardMenuRepo.findAll(AppContexts.user().companyId());
            val menuDisplayName = menus.stream().filter(i -> i.getSystem().value == 1 && i.getMenuAtr() == MenuAtr.Menu && i.getProgramId().equals("KSU008"))
                    .findFirst().map(i -> i.getDisplayName().v()).orElse(TextResource.localize("KSU008_1"));

            AsposeCellsReportContext reportContext = null;
            if (dataSource.getForm9Layout().isSystemFixed()) {
                if (StringUtils.isNotEmpty(dataSource.getFileName())) {
                    reportContext = createContext(SYSTEM_TEMPLATE_PATH + dataSource.getFileName());
                }
            } else {
                if (dataSource.getForm9Layout().getTemplateFileId().isPresent()) {
                    InputStream inputStream = this.fileStreamService.takeOutFromFileId(dataSource.getForm9Layout().getTemplateFileId().get());
                    reportContext = new AsposeCellsReportContext(inputStream);
                }
            }
            if (reportContext == null) return;
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();

            val infoRelatedWkpGroups = dataSource.getInfoRelatedWkpGroups().stream().sorted(Comparator.comparing(DisplayInfoRelatedToWorkplaceGroupDto::getWkpGroupCode))
                    .collect(Collectors.toList());
            for (int i = 0; i < infoRelatedWkpGroups.size(); i++) {
                if (i > 0) {
                    // Copy the first sheet of the first book with in the workbook for next sheet
                    worksheets.addCopy(0);
                }
            }

            for (int i = 0, infoRelatedWkpGroupsSize = infoRelatedWkpGroups.size(); i < infoRelatedWkpGroupsSize; i++) {
                DisplayInfoRelatedToWorkplaceGroupDto wkpGroupInfo = infoRelatedWkpGroups.get(i);

                Worksheet worksheet = worksheets.get(i);
                worksheet.setName(wkpGroupInfo.getWkpGroupCode() + "　" + wkpGroupInfo.getWkpGroupName());

                this.pageSetting(worksheet);
                this.printHeader(worksheet, dataSource, query, wkpGroupInfo.getWkpGroupId());
                this.printContent(worksheet, dataSource, query, wkpGroupInfo.getWkpGroupId());
            }

            worksheets.setActiveSheetIndex(0);
            reportContext.processDesigner();

            // Save as excel file
            reportContext.saveAsExcel(createNewFile(context, getReportName(menuDisplayName + EXCEL_EXT)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void pageSetting(Worksheet worksheet) {
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setHeader(0, "&9&\"ＭＳ ゴシック\"" + "");
        pageSetup.setHeader(1, "&16&\"ＭＳ ゴシック\"" + getText("KSU008_1"));
        pageSetup.setHeader(1, "&16&\"ＭＳ ゴシック\"" + "");
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm", Locale.JAPAN);
        pageSetup.setHeader(2, "&9&\"ＭＳ ゴシック\"" + LocalDateTime.now().format(fullDateTimeFormatter) + "\npage &P");
    }

    private void printHeader(Worksheet worksheet, Form9ExcelByFormatDataSource dataSource, Form9ExcelByFormatQuery query, String wkpGroupId) {
        if (dataSource.getForm9Layout() == null) return;

        val wkpGroupInfoOpt = dataSource.getInfoRelatedWkpGroups().stream().filter(x -> x.getWkpGroupId().equals(wkpGroupId)).findFirst();
        Form9Cover cellCover = dataSource.getForm9Layout().getCover();
        Cells cells = worksheet.getCells();

        Form9CellIndexDto header = this.getCellIndex(cellCover.getCellYear().get().v());
        cells.clearContents(header.getRowIndex(), 0, header.getRowIndex(), cells.getMaxColumn());

        // A1_1
        if (cellCover.getCellYear().isPresent()) {
            cells.get(cellCover.getCellYear().get().v()).setValue(query.getStartDate().year());
            setStyle(cells.get(cellCover.getCellYear().get().v()), TextAlignmentType.CENTER, TextAlignmentType.LEFT, false);
        }

        // A1_2
        if (cellCover.getCellMonth().isPresent()) {
            cells.get(cellCover.getCellMonth().get().v()).setValue(query.getStartDate().month());
            setStyle(cells.get(cellCover.getCellMonth().get().v()), TextAlignmentType.CENTER, TextAlignmentType.LEFT, false);
        }

        if (wkpGroupInfoOpt.isPresent()) {
            if (wkpGroupInfoOpt.get().getHospitalBusinessOffice().isPresent()) {
                if (wkpGroupInfoOpt.get().getHospitalBusinessOffice().get().getNightShiftOpeRule().getShiftTime().isPresent()) {
                    // A1_3
                    if (cellCover.getCellStartTime().isPresent()) {
                        ClockHourMinute clockStart = wkpGroupInfoOpt.get().getHospitalBusinessOffice().get().getNightShiftOpeRule().getShiftTime().get().start();
                        cells.get(cellCover.getCellStartTime().get().v()).setValue(this.convertClockToStr(clockStart));
                    }

                    // A1_4
                    if (cellCover.getCellEndTime().isPresent()) {
                        ClockHourMinute clockEnd = wkpGroupInfoOpt.get().getHospitalBusinessOffice().get().getNightShiftOpeRule().getShiftTime().get().end();
                        cells.get(cellCover.getCellEndTime().get().v()).setValue(this.convertClockToStr(clockEnd));
                    }
                }
            }

            // A1_5
            if (cellCover.getCellTitle().isPresent()) {
                cells.get(cellCover.getCellTitle().get().v()).setValue(this.getTitleDisplay(query, wkpGroupInfoOpt.get()));
            }
        }

        // A1_6
        if (cellCover.getCellPrintPeriod().isPresent()) {
            cells.get(cellCover.getCellPrintPeriod().get().v()).setValue(this.getPeriodDisplay(query.getStartDate(), query.getEndDate()));
        }

        // Nurse header: B1_1, B1_2
        Integer rowB11 = dataSource.getForm9Layout().getNursingTable().getDetailSetting().getRowDate().v();
        String columnB11 = dataSource.getForm9Layout().getNursingTable().getDay1StartColumn().v();
        Integer rowB12 = dataSource.getForm9Layout().getNursingTable().getDetailSetting().getRowDayOfWeek().v();
        String columnB12 = dataSource.getForm9Layout().getNursingTable().getDay1StartColumn().v();
        String cellB1 = columnB11 + rowB11;
        String cellB2 = columnB12 + rowB12;
        this.printNursingTableHeader(cells, cellB1, cellB2, new DatePeriod(query.getStartDate(), query.getEndDate()));

        // Nursing assistant header: D1_1, D1_2
        Integer rowD11 = dataSource.getForm9Layout().getNursingAideTable().getDetailSetting().getRowDate().v();
        String columnD11 = dataSource.getForm9Layout().getNursingAideTable().getDay1StartColumn().v();
        Integer rowD12 = dataSource.getForm9Layout().getNursingAideTable().getDetailSetting().getRowDayOfWeek().v();
        String columnD12 = dataSource.getForm9Layout().getNursingAideTable().getDay1StartColumn().v();
        String cellD1 = columnD11 + rowD11;
        String cellD2 = columnD12 + rowD12;
        this.printNursingTableHeader(cells, cellD1, cellD2, new DatePeriod(query.getStartDate(), query.getEndDate()));
    }

    private void printNursingTableHeader(Cells cells, String cellName1, String cellName2, DatePeriod datePeriod) {
        val cell1 = this.getCellIndex(cellName1);
        val cell2 = this.getCellIndex(cellName2);
        int column1Start = cell1.getColumnIndex();
        int column2Start = cell2.getColumnIndex();

        cells.clearContents(cell1.getRowIndex(), column1Start, cell1.getRowIndex() + 1, cells.getMaxColumn());

        for (GeneralDate date : datePeriod.datesBetween()) {
            // date
            cells.get(cell1.getRowIndex(), column1Start).setValue(date.day() + DAY);
            setStyle(cells.get(cell1.getRowIndex(), column1Start), TextAlignmentType.CENTER, TextAlignmentType.LEFT, false);

            // dayOfWeek
            cells.get(cell2.getRowIndex(), column2Start).setValue(this.getDayOfWeek(date.dayOfWeekEnum()));
            setStyle(cells.get(cell2.getRowIndex(), column2Start), TextAlignmentType.CENTER, TextAlignmentType.LEFT, false);

            // increment index
            column1Start++;
            column2Start++;
        }
    }

    private void printContent(Worksheet worksheet, Form9ExcelByFormatDataSource dataSource, Form9ExcelByFormatQuery query, String wkpGroupId) {
        Cells cells = worksheet.getCells();

        // Prepare data
        val wkpGroupInfoOpt = dataSource.getInfoRelatedWkpGroups().stream().filter(x -> x.getWkpGroupId().equals(wkpGroupId)).findFirst();

        val dateList = new DatePeriod(query.getStartDate(), query.getEndDate()).datesBetween();
        val timeRoundingSetting = dataSource.getDetailOutputSetting().getTimeRoundingSetting();
        boolean isAttributeBlankIfZero = dataSource.getDetailOutputSetting().isAllZeroIsAttributeBlank();

        /**
         * C Area: Nursing
         */
        // Row
        int rowC1 = dataSource.getForm9Layout().getNursingTable().getDetailSetting().getBodyStartRow().v();
        // Column for left content
        val columnC11 = dataSource.getForm9Layout().getNursingTable().getLicense();
        val columnC12 = dataSource.getForm9Layout().getNursingTable().getHospitalWardName();
        val columnC13 = dataSource.getForm9Layout().getNursingTable().getFullName().v();
        val columnC14 = dataSource.getForm9Layout().getNursingTable().getFullTime();
        val columnC15 = dataSource.getForm9Layout().getNursingTable().getShortTime();
        val columnC16 = dataSource.getForm9Layout().getNursingTable().getPartTime();
        val columnC17 = dataSource.getForm9Layout().getNursingTable().getConcurrentPost();
        val columnC18 = dataSource.getForm9Layout().getNursingTable().getNightShiftOnly();

        // Column for right content
        // Row C2 == Row C1
        String columnStartC2 = dataSource.getForm9Layout().getNursingTable().getDay1StartColumn().v();

        // max display number of employee
        int maxEmpNursing = dataSource.getForm9Layout().getNursingTable().getDetailSetting().getMaxNumerOfPeople().v();

        if (columnC11.isPresent()) {
            val cellIndexC11 = this.getCellIndex(columnC11.get().v() + rowC1);
            cells.clearContents(rowC1 - 1,
                    cellIndexC11.getColumnIndex(),
                    ((maxEmpNursing * 3) + rowC1 - 1) - 1,
                    cellIndexC11.getColumnIndex());
        }

        // Clear content left
        if (columnC12.isPresent() && columnC18.isPresent()) {
            val cellIndexStart = this.getCellIndex(columnC12.get().v() + rowC1);
            val cellIndexEnd = this.getCellIndex(columnC18.get().v() + rowC1);
            cells.clearContents(
                    rowC1 - 1,
                    cellIndexStart.getColumnIndex(),
                    ((maxEmpNursing * 3) + rowC1 - 1) - 1,
                    cellIndexEnd.getColumnIndex()
            );
        }

        // Clear content right
        if (columnStartC2 != null) {
            val cell = this.getCellIndex(columnStartC2 + rowC1);
            int columnStart = cell.getColumnIndex();
            cells.clearContents(rowC1 - 1,
                    columnStart,
                    ((maxEmpNursing * 3) + rowC1 - 1) - 1,
                    cells.getMaxColumn());
        }

        val nurseEmpInfoList = wkpGroupInfoOpt.get().getForm9OutputEmpInfos().stream().filter(x -> x.getLicense().value == LicenseClassification.NURSE.value ||
                x.getLicense().value == LicenseClassification.NURSE_ASSOCIATE.value).collect(Collectors.toList());
        for (Form9OutputEmployeeInfo empInfo : nurseEmpInfoList) {
            Map<GeneralDate, MedicalTimeOfEmployee> medicalTimeOfEmpMap = wkpGroupInfoOpt.get().getMedicalTimeOfEmpMap().entrySet().stream()
                    .filter(x -> x.getKey().getEmployeeId().equals(empInfo.getEmployeeId()))
                    .collect(Collectors.toMap(x -> x.getKey().getYmd(), Map.Entry::getValue));

            if (columnC11.isPresent()) {
                val cellIndex = this.getCellIndex(columnC11.get().v() + rowC1);
                cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()).setValue(empInfo.getLicense().nameId);
                setStyle(cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()), TextAlignmentType.TOP, TextAlignmentType.LEFT, true);
            }

            if (columnC12.isPresent()) {
                val cellIndex = this.getCellIndex(columnC12.get().v() + rowC1);
                cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()).setValue(wkpGroupInfoOpt.get().getWkpGroupName());
                setStyle(cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()), TextAlignmentType.TOP, TextAlignmentType.LEFT, true);
            }

            if (columnC13 != null) {
                val cellIndex = this.getCellIndex(columnC13 + rowC1);
                cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()).setValue(empInfo.getFullName());
                setStyle(cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()), TextAlignmentType.TOP, TextAlignmentType.LEFT, true);
            }

            if ((!medicalTimeOfEmpMap.isEmpty() && isAttributeBlankIfZero) || (!medicalTimeOfEmpMap.isEmpty() && !isAttributeBlankIfZero)
                    || (medicalTimeOfEmpMap.isEmpty() && !isAttributeBlankIfZero)) {
                if (columnC14.isPresent()) {
                    val cellIndex = this.getCellIndex(columnC14.get().v() + rowC1);
                    cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()).setValue(empInfo.isFullTime() ? 1 : EMPTY);
                    setStyle(cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()), TextAlignmentType.TOP, TextAlignmentType.LEFT, true);
                }

                if (columnC15.isPresent()) {
                    val cellIndex = this.getCellIndex(columnC15.get().v() + rowC1);
                    cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()).setValue(empInfo.isShortTime() ? 1 : EMPTY);
                    setStyle(cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()), TextAlignmentType.TOP, TextAlignmentType.LEFT, true);
                }

                if (columnC16.isPresent()) {
                    val cellIndex = this.getCellIndex(columnC16.get().v() + rowC1);
                    cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()).setValue(empInfo.isPartTime() ? 1 : EMPTY);
                    setStyle(cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()), TextAlignmentType.TOP, TextAlignmentType.LEFT, true);
                }

                if (columnC17.isPresent()) {
                    val cellIndex = this.getCellIndex(columnC17.get().v() + rowC1);
                    cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()).setValue(empInfo.isConcurrentPost() ? 1 : EMPTY);
                    setStyle(cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()), TextAlignmentType.TOP, TextAlignmentType.LEFT, true);
                }

                if (columnC18.isPresent()) {
                    val cellIndex = this.getCellIndex(columnC18.get().v() + rowC1);
                    cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()).setValue(empInfo.isNightShiftOnly() ? 1 : EMPTY);
                    setStyle(cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()), TextAlignmentType.TOP, TextAlignmentType.LEFT, true);
                }
            }

            this.printDataOfEmpByDates(cells, columnStartC2 + rowC1, dateList, medicalTimeOfEmpMap, query.getColorSetting(), timeRoundingSetting);

            rowC1 += 3;
        }

        /**
         * E Area: Nursing aide
         */
        // Row
        int rowE1 = dataSource.getForm9Layout().getNursingAideTable().getDetailSetting().getBodyStartRow().v();
        // Column for left content
        val columnE11 = dataSource.getForm9Layout().getNursingAideTable().getHospitalWardName();
        val columnE12 = dataSource.getForm9Layout().getNursingAideTable().getFullName();
        val columnE13 = dataSource.getForm9Layout().getNursingAideTable().getFullTime();
        val columnE14 = dataSource.getForm9Layout().getNursingAideTable().getShortTime();
        val columnE15 = dataSource.getForm9Layout().getNursingAideTable().getPartTime();
        val columnE16 = dataSource.getForm9Layout().getNursingAideTable().getConcurrentPost();
        val columnE17 = dataSource.getForm9Layout().getNursingAideTable().getOfficeWork();
        val columnE18 = dataSource.getForm9Layout().getNursingAideTable().getNightShiftOnly();

        // Column for right content
        String columnStartE2 = dataSource.getForm9Layout().getNursingAideTable().getDay1StartColumn().v();

        // max display number of employee
        int maxEmpNursingAide = dataSource.getForm9Layout().getNursingAideTable().getDetailSetting().getMaxNumerOfPeople().v();

        // Clear content left
        if (columnE11.isPresent() && columnE18.isPresent()) {
            val cellIndexStart = this.getCellIndex(columnE11.get().v() + rowC1);
            val cellIndexEnd = this.getCellIndex(columnE18.get().v() + rowC1);
            cells.clearContents(
                    rowE1 - 1,
                    cellIndexStart.getColumnIndex(),
                    ((maxEmpNursingAide * 3) + rowE1 - 1) - 1,
                    cellIndexEnd.getColumnIndex()
            );
        }

        // Clear content right
        if (columnStartE2 != null) {
            val cell = this.getCellIndex(columnStartE2 + rowE1);
            int columnStart = cell.getColumnIndex();
            cells.clearContents(rowE1 - 1,
                    columnStart,
                    ((maxEmpNursingAide * 3) + rowE1 - 1) - 1,
                    cells.getMaxColumn());
        }

        val nurseAssistEmpInfoList = wkpGroupInfoOpt.get().getForm9OutputEmpInfos().stream().filter(x -> x.getLicense().value == LicenseClassification.NURSE_ASSIST.value)
                .collect(Collectors.toList());
        for (Form9OutputEmployeeInfo nursingAideEmpInfo : nurseAssistEmpInfoList) {
            Map<GeneralDate, MedicalTimeOfEmployee> medicalTimeOfEmpMap = wkpGroupInfoOpt.get().getMedicalTimeOfEmpMap().entrySet().stream()
                    .filter(x -> x.getKey().getEmployeeId().equals(nursingAideEmpInfo.getEmployeeId()))
                    .collect(Collectors.toMap(x -> x.getKey().getYmd(), Map.Entry::getValue));
            if (columnE11.isPresent()) {
                val cellIndex = this.getCellIndex(columnE11.get().v() + rowE1);
                cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()).setValue(wkpGroupInfoOpt.get().getWkpGroupName());
                setStyle(cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()), TextAlignmentType.TOP, TextAlignmentType.LEFT, true);
            }

            if (columnE12 != null) {
                val cellIndex = this.getCellIndex(columnE12.v() + rowE1);
                cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()).setValue(nursingAideEmpInfo.getFullName());
                setStyle(cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()), TextAlignmentType.TOP, TextAlignmentType.LEFT, true);
            }

            if ((!medicalTimeOfEmpMap.isEmpty() && isAttributeBlankIfZero) || (!medicalTimeOfEmpMap.isEmpty() && !isAttributeBlankIfZero)
                    || (medicalTimeOfEmpMap.isEmpty() && !isAttributeBlankIfZero)) {
                if (columnE13.isPresent()) {
                    val cellIndex = this.getCellIndex(columnE13.get().v() + rowE1);
                    cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()).setValue(nursingAideEmpInfo.isFullTime() ? 1 : EMPTY);
                    setStyle(cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()), TextAlignmentType.TOP, TextAlignmentType.LEFT, true);
                }

                if (columnE14.isPresent()) {
                    val cellIndex = this.getCellIndex(columnE14.get().v() + rowE1);
                    cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()).setValue(nursingAideEmpInfo.isShortTime() ? 1 : EMPTY);
                    setStyle(cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()), TextAlignmentType.TOP, TextAlignmentType.LEFT, true);
                }

                if (columnE15.isPresent()) {
                    val cellIndex = this.getCellIndex(columnE15.get().v() + rowE1);
                    cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()).setValue(nursingAideEmpInfo.isPartTime() ? 1 : EMPTY);
                    setStyle(cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()), TextAlignmentType.TOP, TextAlignmentType.LEFT, true);
                }

                if (columnE16.isPresent()) {
                    val cellIndex = this.getCellIndex(columnE16.get().v() + rowE1);
                    cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()).setValue(nursingAideEmpInfo.isConcurrentPost() ? 1 : EMPTY);
                    setStyle(cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()), TextAlignmentType.TOP, TextAlignmentType.LEFT, true);
                }

                if (columnE17.isPresent()) {
                    val cellIndex = this.getCellIndex(columnE17.get().v() + rowE1);
                    cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()).setValue(nursingAideEmpInfo.isOfficeWorker() ? 1 : EMPTY);
                    setStyle(cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()), TextAlignmentType.TOP, TextAlignmentType.LEFT, true);
                }

                if (columnE18.isPresent()) {
                    val cellIndex = this.getCellIndex(columnE18.get().v() + rowE1);
                    cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()).setValue(nursingAideEmpInfo.isNightShiftOnly() ? 1 : EMPTY);
                    setStyle(cells.get(cellIndex.getRowIndex(), cellIndex.getColumnIndex()), TextAlignmentType.TOP, TextAlignmentType.LEFT, true);
                }
            }

            this.printDataOfEmpByDates(cells, columnStartE2 + rowE1, dateList, medicalTimeOfEmpMap, query.getColorSetting(), timeRoundingSetting);

            rowE1 += 3;
        }
    }

    private void printDataOfEmpByDates(Cells cells, String cellName, List<GeneralDate> dateList, Map<GeneralDate, MedicalTimeOfEmployee> medicalTimeOfEmpMap,
                                       Form9ColorSettingsDTO colorSetting, Form9TimeRoundingSetting roundingSetting) {
        val cell = this.getCellIndex(cellName);
        int columnStart = cell.getColumnIndex();

        for (GeneralDate date : dateList) {
            Optional<MedicalTimeOfEmployee> medicalTime = medicalTimeOfEmpMap.entrySet().stream().filter(x -> x.getKey().equals(date)).map(Map.Entry::getValue).findFirst();
            if (!medicalTime.isPresent()) continue;

            val roundingUnit = roundingSetting.getRoundingUnit();
            val roundingMode = roundingSetting.getRoundingMethod();

            /** Day shift */
            if (medicalTime.get().getDayShiftHours() != null) {
                cells.get(cell.getRowIndex(), columnStart).setValue(rounding(roundingMode, roundingUnit, medicalTime.get().getDayShiftHours().getTime().v()));
                val colorDayShift = this.getCellColorSetting(colorSetting, medicalTime.get().getScheRecAtr(), medicalTime.get().getDayShiftHours());
                if (StringUtils.isNotEmpty(colorDayShift.getTextColor())) {
                    this.setTextColor(cells.get(cell.getRowIndex(), columnStart), Color.fromArgb(Integer.parseInt(colorDayShift.getTextColor(), 16)));
                }
                if (StringUtils.isNotEmpty(colorDayShift.getBgColor())) {
                    this.setBgColor(cells.get(cell.getRowIndex(), columnStart), Color.fromArgb(Integer.parseInt(colorDayShift.getBgColor(), 16)));
                }
            }

            /** Night shift */
            if (medicalTime.get().getNightShiftHours() != null) {
                cells.get(cell.getRowIndex() + 1, columnStart).setValue(rounding(roundingMode, roundingUnit, medicalTime.get().getNightShiftHours().getTime().v()));
                val color = this.getCellColorSetting(colorSetting, medicalTime.get().getScheRecAtr(), medicalTime.get().getNightShiftHours());
                if (StringUtils.isNotEmpty(color.getTextColor())) {
                    this.setTextColor(cells.get(cell.getRowIndex() + 1, columnStart), Color.fromArgb(Integer.parseInt(color.getTextColor(), 16)));
                }
                if (StringUtils.isNotEmpty(color.getBgColor())) {
                    this.setBgColor(cells.get(cell.getRowIndex() + 1, columnStart), Color.fromArgb(Integer.parseInt(color.getBgColor(), 16)));
                }
            }

            /** Total night shift */
            if (medicalTime.get().getTotalNightShiftHours() != null) {
                cells.get(cell.getRowIndex() + 2, columnStart).setValue(rounding(roundingMode, roundingUnit, medicalTime.get().getTotalNightShiftHours().getTime().v()));
                val color = this.getCellColorSetting(colorSetting, medicalTime.get().getScheRecAtr(), medicalTime.get().getTotalNightShiftHours());
                if (StringUtils.isNotEmpty(color.getTextColor())) {
                    this.setTextColor(cells.get(cell.getRowIndex() + 2, columnStart), Color.fromArgb(Integer.parseInt(color.getTextColor(), 16)));
                }
                if (StringUtils.isNotEmpty(color.getBgColor())) {
                    this.setBgColor(cells.get(cell.getRowIndex() + 2, columnStart), Color.fromArgb(Integer.parseInt(color.getBgColor(), 16)));
                }
            }

            columnStart++;
        }
    }

    private Form9CellColorDto getCellColorSetting(Form9ColorSettingsDTO colorSetting, ScheRecAtr scheRec, Form9OutputMedicalTime value) {
        String cellTextColor = null;
        String cellBgColor = null;
        if (colorSetting.getDeliveryTimeDeductionDate().isUse()) {
            if (colorSetting.getDeliveryTimeDeductionDate().getDisplayTarget() == Form9DisplayTarget.TEXT_COLOR.value) {
                /** ドメインモデルの社員の出力医療時間.申し送り時間か==trueの時に、以下の色処理を実行する */
                if (value.isDeductionDateFromDeliveryTime()) {
                    /** セルの文字色＝色設定.申し送り時間控除日.色 */
                    cellTextColor = colorSetting.getDeliveryTimeDeductionDate().getColor().replace("#", "");
                }
            }

            if (colorSetting.getDeliveryTimeDeductionDate().getDisplayTarget() == Form9DisplayTarget.BACKGROUND_COLOR.value) {
                /** ドメインモデルの社員の出力医療時間.申し送り時間か==trueの時に、以下の色処理を実行する */
                if (value.isDeductionDateFromDeliveryTime()) {
                    cellBgColor = colorSetting.getDeliveryTimeDeductionDate().getColor().replace("#", "");
                }
            }
        }

        if (colorSetting.getWorkingHours().isUse()) {
            if (colorSetting.getWorkingHours().getDisplayTarget() == Form9DisplayTarget.TEXT_COLOR.value) {
                if (scheRec == ScheRecAtr.SCHEDULE)
                    cellTextColor = colorSetting.getWorkingHours().getScheduleColor().replace("#", "");
                if (scheRec == ScheRecAtr.RECORD)
                    cellTextColor = colorSetting.getWorkingHours().getActualColor().replace("#", "");
            }

            if (colorSetting.getWorkingHours().getDisplayTarget() == Form9DisplayTarget.BACKGROUND_COLOR.value) {
                if (scheRec == ScheRecAtr.SCHEDULE)
                    cellBgColor = colorSetting.getWorkingHours().getScheduleColor().replace("#", "");
                if (scheRec == ScheRecAtr.RECORD)
                    cellBgColor = colorSetting.getWorkingHours().getActualColor().replace("#", "");
            }
        }

        return new Form9CellColorDto(cellTextColor, cellBgColor);
    }

    private Form9CellIndexDto getCellIndex(String cellName) {
        int[] cell1Indices = CellsHelper.cellNameToIndex(cellName);
        return new Form9CellIndexDto(cell1Indices[0], cell1Indices[1]);
    }

    private String getText(String resourceId) {
        return TextResource.localize(resourceId);
    }

    private String getTitleDisplay(Form9ExcelByFormatQuery query, DisplayInfoRelatedToWorkplaceGroupDto infoRelatedWkpGroup) {
        String acquireText = "";
        switch (query.getAcquireTarget()) {
            case 0:
                acquireText = getText("KSU008_18");
                break;
            case 1:
                acquireText = getText("KSU008_19");
                break;
            case 2:
                acquireText = getText("KSU008_20");
                break;
        }

        return TextResource.localize("KSU008_180", String.valueOf(query.getStartDate().month()), acquireText,
                infoRelatedWkpGroup.getWkpGroupCode(), infoRelatedWkpGroup.getWkpGroupName());
    }

    private String getDayOfWeek(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY:
                return "月曜";
            case TUESDAY:
                return "火曜";
            case WEDNESDAY:
                return "水曜";
            case THURSDAY:
                return "木曜";
            case FRIDAY:
                return "金曜";
            case SATURDAY:
                return "土曜";
            case SUNDAY:
                return "日曜";
            default:
                return "";
        }
    }

    private String convertClockToStr(ClockHourMinute clock) {
        String hour = clock.hour() >= 10 ? String.valueOf(clock.hour()) : "0" + clock.hour();
        String minute = clock.minute() >= 10 ? String.valueOf(clock.minute()) : "0" + clock.minute();
        return hour + HOUR + minute + MINUTE;
    }

    private String toYmdStr(GeneralDate value) {
        return String.valueOf(value.year()) + YEAR + value.month() + MONTH + value.day() + DAY;
    }

    private String getPeriodDisplay(GeneralDate startDate, GeneralDate endDate) {
        return TextResource.localize("KSU008_181", toYmdStr(startDate), toYmdStr(endDate));
    }

    private BigDecimal rounding(Rounding roundingMethod, RoundingUnit roundingUnit, int number) {
        BigDecimal value = BigDecimal.valueOf(number / 60);
        BigDecimal rounded = null;
        switch (roundingMethod) {
            case ROUNDING_UP:
                rounded = value.round(new MathContext(roundingUnit.value + 1, RoundingMode.UP));
                break;
            case ROUNDING_DOWN:
                rounded = value.round(new MathContext(roundingUnit.value + 1, RoundingMode.DOWN));
                break;
            case ROUNDING_DOWN_OVER:
                rounded = value.round(new MathContext(roundingUnit.value + 1, RoundingMode.HALF_UP));
                break;
        }
        return rounded;
    }

    private String convertNumberToTime(Integer totalMinute) {
        if (totalMinute == null) return "0:00";
        int hour = totalMinute / MINUTES_IN_AN_HOUR;
        int minute = totalMinute % MINUTES_IN_AN_HOUR;

        return (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute);
    }

    private String convertTimeToString(String timeValue) {
        StringBuilder builder = new StringBuilder();
        String[] hourMinutes = timeValue.trim().split(":");
        return builder.append(hourMinutes[0]).append(HOUR).append(hourMinutes[1]).append(MINUTE).toString();
    }

    private String convertNumberToTimeString(Integer value) {
        return this.convertTimeToString(this.convertNumberToTime(value));
    }

    private void setTextColor(Cell cell, Color color) {
        Style style = cell.getStyle();
        style.getFont().setColor(color);
        cell.setStyle(style);
    }

    private void setBgColor(Cell cell, Color color) {
        Style style = cell.getStyle();
        style.setPattern(BackgroundType.SOLID);
        style.setForegroundColor(color);
        cell.setStyle(style);
    }

    private void setStyle(Cell cell, int vertical, int horizontal, boolean isShrinkToFit) {
        Style style = cell.getStyle();
        style.setVerticalAlignment(vertical);
        style.setHorizontalAlignment(horizontal);
        style.setShrinkToFit(isShrinkToFit);
        cell.setStyle(style);
    }

    public InputStream getSystemTemplate(String fileName) {
        return this.getResourceAsStream(SYSTEM_TEMPLATE_PATH + fileName);
    }
}
