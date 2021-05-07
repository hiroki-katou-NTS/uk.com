package nts.uk.ctx.at.request.infra.repository.application.holidayshipment.print;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Worksheet;

import nts.arc.i18n.I18NText;
import nts.uk.ctx.at.request.dom.application.common.service.print.PrintContentOfApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.HolidayShipmentOutput;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.enumcommon.DayAttr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * @author anhnm
 *
 */
@Stateless
public class AsposeHolidayShipment {
    private final String EMPTY = "";
    private final String HALF_WIDTH_SPACE = " ";
    private final String fULL_WIDTH_SPACE = "　";
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy年 MM月 dd日 (E)", Locale.JAPAN);
    
    public int printHolidayShipmentContent(Worksheet worksheet, PrintContentOfApp printContentOfApp) {
        int deleteCnt = 0;
        
        Cells cells = worksheet.getCells();
        
        Optional<HolidayShipmentOutput> optHolidayShipmentOutputOptional = printContentOfApp.getOptHolidayShipment();
        if (!optHolidayShipmentOutputOptional.isPresent()) {
            throw new RuntimeException("No data to print");
        }
        HolidayShipmentOutput holidayShipment = optHolidayShipmentOutputOptional.get();
        boolean manageMultipleTime = holidayShipment.getAppDispInfoStartup().getAppDispInfoNoDateOutput().isManagementMultipleWorkCycles();
        
        /**
         * Get cells control
         */
        // A
        Cell cellB6 = cells.get("B6");
        Cell cellD6 = cells.get("D6");
        Cell cellB8 = cells.get("B8");
        Cell cellD8 = cells.get("D8");
        Cell cellD9 = cells.get("D9");
        Cell cellD10 = cells.get("D10");
        Cell cellD11 = cells.get("D11");
        Cell cellF8 = cells.get("F8");
        Cell cellF9 = cells.get("F9");
        Cell cellF10 = cells.get("F10");
        Cell cellF11 = cells.get("F11");
        
        // B
        Cell cellB12 = cells.get("B12");
        Cell cellD12 = cells.get("D12");
        Cell cellB13 = cells.get("B13");
        Cell cellD13 = cells.get("D13");
        Cell cellD14 = cells.get("D14");
        Cell cellD15 = cells.get("D15");
        Cell cellD16 = cells.get("D16");
        Cell cellF13 = cells.get("F13");
        Cell cellF14 = cells.get("F14");
        Cell cellF15 = cells.get("F15");
        Cell cellF16 = cells.get("F16");
        
        /**
                     *   振出内容
         * RecruitmentApp
         */
        if (holidayShipment.getRec().isPresent()) {
            // A1_1
            cellB6.setValue(I18NText.getText("KAF011_84"));
            
            // A1_2
            LocalDate localDateRec = holidayShipment.getRec().get().getAppDate().getApplicationDate().localDate();
            cellD6.setValue(dateTimeFormatter.format(localDateRec));
            
            // A1_3
            // Xử lý add sau cùng sau khi đã xóa data không sử dụng
            // cellB8.setValue(I18NText.getText("KAF011_85"));
            
            // A1_4
            cellD8.setValue(I18NText.getText("KAF011_28"));
            
            // A1_6
            cellD9.setValue(I18NText.getText("KAF011_30"));
            
            // A1_8
            cellD10.setValue(I18NText.getText("KAF011_31"));
            
            // A1_10
            cellD11.setValue(I18NText.getText("KAF011_34"));
            
            // A1_5
            String workTypeStringRec = EMPTY;
            
            WorkTypeCode workTypeCodeRec = holidayShipment.getRec().get().getWorkInformation().getWorkTypeCode();
            List<WorkType> workTypeListRec = holidayShipment.getApplicationForWorkingDay().getWorkTypeList();
            List<WorkType> workTypeListFilterRec = workTypeListRec.stream()
                    .filter(workType -> workType.getWorkTypeCode().equals(workTypeCodeRec)).collect(Collectors.toList());
            if (workTypeListFilterRec.size() == 0) {
                workTypeStringRec = workTypeCodeRec.v() + HALF_WIDTH_SPACE + I18NText.getText("KAF011_68");
            } else {
                if (workTypeListFilterRec.get(0).getName() == null 
                        || workTypeListFilterRec.get(0).getName().v().equals(EMPTY)) {
                    workTypeStringRec = workTypeCodeRec.v() + HALF_WIDTH_SPACE + I18NText.getText("KAF011_68");
                } else {
                    workTypeStringRec = workTypeListFilterRec.get(0).getName().v();
                }
            }
            
            cellF8.setValue(workTypeStringRec);
            
            // A1_7
            String workTimeStringRec = EMPTY;
            
            Optional<WorkTimeCode> optWorkTimeCodeRec = holidayShipment.getRec().get().getWorkInformation().getWorkTimeCodeNotNull();
            Optional<List<WorkTimeSetting>> opWorkTimeLstRec = holidayShipment.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpWorkTimeLst();
            if (optWorkTimeCodeRec.isPresent()) {
                WorkTimeCode workTimeCode = optWorkTimeCodeRec.get();
                if (opWorkTimeLstRec.isPresent()) {
                    List<WorkTimeSetting> workTimeLst = opWorkTimeLstRec.get();
                    List<WorkTimeSetting> workTimeLstFilter = workTimeLst.stream().filter(workTime -> workTime.getWorktimeCode().equals(workTimeCode)).collect(Collectors.toList());
                    if (workTimeLstFilter.size() == 0) {
                        workTimeStringRec = workTimeCode.v() + HALF_WIDTH_SPACE + I18NText.getText("KAF011_68");
                    } else {
                        if (workTimeLstFilter.get(0).getWorkTimeDisplayName() == null 
                                || workTimeLstFilter.get(0).getWorkTimeDisplayName().getWorkTimeName() == null 
                                || workTimeLstFilter.get(0).getWorkTimeDisplayName().getWorkTimeName().v().equals(EMPTY)) {
                            workTimeStringRec = workTimeCode.v() + HALF_WIDTH_SPACE + I18NText.getText("KAF011_68");
                        } else {
                            workTimeStringRec = workTimeLstFilter.get(0).getWorkTimeDisplayName().getWorkTimeName().v();
                        }
                    }
                } else {
                    workTimeStringRec = workTimeCode.v() + HALF_WIDTH_SPACE + I18NText.getText("KAF011_68");
                }
            }
            
            cellF9.setValue(workTimeStringRec);
            
            List<TimeZoneWithWorkNo> workingHoursRec = holidayShipment.getRec().get().getWorkingHours();
            // A1_9
            String time1StringRec = EMPTY;
            List<TimeZoneWithWorkNo> workingHours1FilterRec = workingHoursRec.stream().filter(time -> time.getWorkNo().v() == 1).collect(Collectors.toList());
            
            if (workingHours1FilterRec.size() > 0) {
                time1StringRec = formatTimeWithDayAttr(workingHours1FilterRec.get(0).getTimeZone().getStartTime()) 
                        + "～" 
                        + formatTimeWithDayAttr(workingHours1FilterRec.get(0).getTimeZone().getEndTime());
            }
            
            cellF10.setValue(time1StringRec);
            
            // A1_11
            String time2StringRec = EMPTY;
            List<TimeZoneWithWorkNo> workingHours2FilterRec = workingHoursRec.stream().filter(time -> time.getWorkNo().v() == 2).collect(Collectors.toList());
            
            if (workingHours2FilterRec.size() > 0) {
                time2StringRec = formatTimeWithDayAttr(workingHours2FilterRec.get(0).getTimeZone().getStartTime()) 
                        + "～" 
                        + formatTimeWithDayAttr(workingHours2FilterRec.get(0).getTimeZone().getEndTime());
            }
            
            cellF11.setValue(time2StringRec);
        }
        
        /**
                     *  振休申請
         * AbsenceLeaveApp
         */
        if (holidayShipment.getAbs().isPresent()) {
            // B1_1
            cellB12.setValue(I18NText.getText("KAF011_83"));
            
            // B1_2
            LocalDate localDateAbs = holidayShipment.getAbs().get().getAppDate().getApplicationDate().localDate();
            cellD12.setValue(dateTimeFormatter.format(localDateAbs));
            
            // B1_3
            // Xử lý add sau cùng sau khi đã xóa data không sử dụng
            // cellB13.setValue(I18NText.getText("KAF011_86"));
            
            // B1_4
            cellD13.setValue(I18NText.getText("KAF011_28"));
            
            // B1_6
            cellD14.setValue(I18NText.getText("KAF011_30"));
            
            // B1_8
            cellD15.setValue(I18NText.getText("KAF011_31"));
            
            // B1_10
            cellD16.setValue(I18NText.getText("KAF011_34"));
            
            // B1_5
            String workTypeStringAbs = EMPTY;
            
            WorkTypeCode workTypeCodeAbs = holidayShipment.getAbs().get().getWorkInformation().getWorkTypeCode();
            List<WorkType> workTypeListAbs = holidayShipment.getApplicationForHoliday().getWorkTypeList();
            List<WorkType> workTypeListFilterAbs = workTypeListAbs.stream()
                    .filter(workType -> workType.getWorkTypeCode().equals(workTypeCodeAbs)).collect(Collectors.toList());
            if (workTypeListFilterAbs.size() == 0) {
                workTypeStringAbs = workTypeCodeAbs.v() + HALF_WIDTH_SPACE + I18NText.getText("KAF011_68");
            } else {
                if (workTypeListFilterAbs.get(0).getName() == null 
                        || workTypeListFilterAbs.get(0).getName().v().equals(EMPTY)) {
                    workTypeStringAbs = workTypeCodeAbs.v() + HALF_WIDTH_SPACE + I18NText.getText("KAF011_68");
                } else {
                    workTypeStringAbs = workTypeListFilterAbs.get(0).getName().v();
                }
            }
            
            cellF13.setValue(workTypeStringAbs);
            
            // B1_7
            String workTimeStringAbs = EMPTY;
            
            Optional<WorkTimeCode> optWorkTimeCodeAbs = holidayShipment.getAbs().get().getWorkInformation().getWorkTimeCodeNotNull();
            Optional<List<WorkTimeSetting>> opWorkTimeLstAbs = holidayShipment.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpWorkTimeLst();
            if (optWorkTimeCodeAbs.isPresent()) {
                WorkTimeCode workTimeCode = optWorkTimeCodeAbs.get();
                if (opWorkTimeLstAbs.isPresent()) {
                    List<WorkTimeSetting> workTimeLst = opWorkTimeLstAbs.get();
                    List<WorkTimeSetting> workTimeLstFilter = workTimeLst.stream().filter(workTime -> workTime.getWorktimeCode().equals(workTimeCode)).collect(Collectors.toList());
                    if (workTimeLstFilter.size() == 0) {
                        workTimeStringAbs = workTimeCode.v() + HALF_WIDTH_SPACE + I18NText.getText("KAF011_68");
                    } else {
                        if (workTimeLstFilter.get(0).getWorkTimeDisplayName() == null 
                                || workTimeLstFilter.get(0).getWorkTimeDisplayName().getWorkTimeName() == null 
                                || workTimeLstFilter.get(0).getWorkTimeDisplayName().getWorkTimeName().v().equals(EMPTY)) {
                            workTimeStringAbs = workTimeCode.v() + HALF_WIDTH_SPACE + I18NText.getText("KAF011_68");
                        } else {
                            workTimeStringAbs = workTimeLstFilter.get(0).getWorkTimeDisplayName().getWorkTimeName().v();
                        }
                    }
                } else {
                    workTimeStringAbs = workTimeCode.v() + HALF_WIDTH_SPACE + I18NText.getText("KAF011_68");
                }
            }
            
            cellF14.setValue(workTimeStringAbs);
            
            List<TimeZoneWithWorkNo> workingHoursAbs = holidayShipment.getAbs().get().getWorkingHours();
            // B1_9
            String time1StringAbs = EMPTY;
            List<TimeZoneWithWorkNo> workingHours1FilterAbs = workingHoursAbs.stream().filter(time -> time.getWorkNo().v() == 1).collect(Collectors.toList());
            
            if (workingHours1FilterAbs.size() > 0) {
                time1StringAbs = formatTimeWithDayAttr(workingHours1FilterAbs.get(0).getTimeZone().getStartTime()) 
                        + "～" 
                        + formatTimeWithDayAttr(workingHours1FilterAbs.get(0).getTimeZone().getEndTime());
            }
            
            cellF15.setValue(time1StringAbs);
            
            // B1_11
            String time2StringAbs = EMPTY;
            List<TimeZoneWithWorkNo> workingHours2FilterAbs = workingHoursAbs.stream().filter(time -> time.getWorkNo().v() == 2).collect(Collectors.toList());
            
            if (workingHours2FilterAbs.size() > 0) {
                time2StringAbs = formatTimeWithDayAttr(workingHours2FilterAbs.get(0).getTimeZone().getStartTime()) 
                        + "～" 
                        + formatTimeWithDayAttr(workingHours2FilterAbs.get(0).getTimeZone().getEndTime());
            }
            
            cellF16.setValue(time2StringAbs);
            
            if (!holidayShipment.getRec().isPresent()) {
                cellB6.setValue(I18NText.getText("KAF011_83"));
                cellD6.setValue(dateTimeFormatter.format(localDateAbs));
                cellB8.setValue(I18NText.getText("KAF011_85"));
                cellD8.setValue(I18NText.getText("KAF011_28"));
                cellD9.setValue(I18NText.getText("KAF011_30"));
                cellD10.setValue(I18NText.getText("KAF011_31"));
                cellD11.setValue(I18NText.getText("KAF011_34"));
                cellF8.setValue(workTypeStringAbs);
                cellF9.setValue(workTimeStringAbs);
                cellF10.setValue(time1StringAbs);
                cellF11.setValue(time2StringAbs);
            }
        }
        
        /**
         * Delete rows with no data
         */
        if (!holidayShipment.getAbs().isPresent()) {
            cells.deleteRow(15);
            cells.deleteRow(14);
            cells.deleteRow(13);
            cells.deleteRow(12);
            cells.deleteRow(11);
            deleteCnt =+ 5;
            
            if (!manageMultipleTime || cellF11.getStringValue().equals(EMPTY)) {
                cells.deleteRow(10);
                deleteCnt++;
            }
            
            cellB8.setValue(I18NText.getText("KAF011_86"));
        } else {
            if (!manageMultipleTime) {
                cells.deleteRow(15);
                deleteCnt++;
            }
            
            if (holidayShipment.getRec().isPresent()) {
                if (!manageMultipleTime) {
                    cells.deleteRow(10);
                    deleteCnt++;
                }
                cells.get("B8").setValue(I18NText.getText("KAF011_86"));
                if (manageMultipleTime) {
                    cells.get("B13").setValue(I18NText.getText("KAF011_85"));
                } else {
                    cells.get("B12").setValue(I18NText.getText("KAF011_85"));
                }
            } else {
                cells.deleteRow(15);
                cells.deleteRow(14);
                cells.deleteRow(13);
                cells.deleteRow(12);
                cells.deleteRow(11);
                deleteCnt =+ 5;
                
                if (!manageMultipleTime) {
                  cells.deleteRow(10);
                  deleteCnt++;
              }
                
                cells.get("B8").setValue(I18NText.getText("KAF011_85"));
                if (manageMultipleTime) {
                    cells.get("B13").setValue(I18NText.getText("KAF011_85"));
                } else {
                    cells.get("B12").setValue(I18NText.getText("KAF011_85"));
                }
            }
        }
        
        return deleteCnt;
    }
    
    public String formatTimeWithDayAttr(TimeWithDayAttr timeWithDayAttr) {
        if(timeWithDayAttr.dayAttr().equals(DayAttr.THE_PRESENT_DAY)) {
            return timeWithDayAttr.getInDayTimeWithFormat();
        } else {
            return timeWithDayAttr.getFullText();
        }
    }
}
