package nts.uk.ctx.at.request.infra.repository.application.appabsence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Worksheet;

import nts.arc.i18n.I18NText;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave.ApplyforSpecialLeave;
import nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave.RelationshipCDPrimitive;
import nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave.RelationshipReasonPrimitive;
import nts.uk.ctx.at.request.dom.application.appabsence.apptimedigest.TimeDigestApplication;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.SpecAbsenceDispInfo;
import nts.uk.ctx.at.request.dom.application.common.service.print.PrintContentOfApp;
import nts.uk.ctx.at.request.dom.application.common.service.print.PrintContentOfApplyForLeave;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.service.DateSpecHdRelationOutput;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class AsposeApplyForLeave {

    private final String EMPTY = "";
    private final String HALF_WIDTH_SPACE = " ";
    private final String fULL_WIDTH_SPACE = "　";

    /**
     * @param worksheet
     * @param printContentOfApp
     */
    public int printApplyForLeaveContent(Worksheet worksheet, PrintContentOfApp printContentOfApp) {
        int deleteCnt = 0;
        
        Optional<PrintContentOfApplyForLeave> opPrintContentApplyForLeave = printContentOfApp.getOpPrintContentApplyForLeave();
        if (!opPrintContentApplyForLeave.isPresent()) {
            throw new RuntimeException("No data to print");
        }
        
        PrintContentOfApplyForLeave printContentOfApplyForLeave = opPrintContentApplyForLeave.get();
        
        Cells cells = worksheet.getCells();
        
        Cell cellB8 = cells.get("B8");
        Cell cellB9 = cells.get("B9");
        Cell cellB10 = cells.get("B10");
        Cell cellB11 = cells.get("B11");
        Cell cellB12 = cells.get("B12");
        Cell cellB13 = cells.get("B13");
        Cell cellB14 = cells.get("B14");
        Cell cellB15 = cells.get("B15");
        
        cellB8.setValue(I18NText.getText("KAF006_15"));
        cellB9.setValue(I18NText.getText("KAF006_16"));
        cellB10.setValue(I18NText.getText("KAF006_91"));
        cellB11.setValue(I18NText.getText("KAF006_19"));
        cellB12.setValue(I18NText.getText("KAF006_22"));
        cellB13.setValue(I18NText.getText("KAF006_23"));
        cellB14.setValue(I18NText.getText("KAF006_33"));
        cellB15.setValue(I18NText.getText("KAF006_43"));
        
        /**
         * A1_2
         */
        String holidayNameString = printContentOfApplyForLeave.getAppAbsenceStartInfoOutput().getHdAppSet().getHolidayApplicationTypeDisplayName()
            .stream().filter(item -> item.getHolidayApplicationType()
                    .equals(printContentOfApplyForLeave.getApplyForLeave().getVacationInfo().getHolidayApplicationType()))
            .collect(Collectors.toList()).get(0).getDisplayName().v();
        
        Cell cellD8 = cells.get("D8");
        cellD8.setValue(holidayNameString);
        
        /**
         * A2_2
         */
        String workTypeCDString = EMPTY;
        
        WorkTypeCode workTypeCode = printContentOfApplyForLeave.getApplyForLeave().getReflectFreeTimeApp().getWorkInfo().getWorkTypeCode();
        List<WorkType> workTypeLst = printContentOfApplyForLeave.getAppAbsenceStartInfoOutput().getWorkTypeLst();
        
        List<WorkType> workTypeLstFilterList = workTypeLst.stream().filter(workType -> workType.getWorkTypeCode().equals(workTypeCode)).collect(Collectors.toList());
        if (workTypeLstFilterList.size() == 0) {
            workTypeCDString = workTypeCode.v() + " " + I18NText.getText("KAF006_93");
        } else {
            if (workTypeLstFilterList.get(0).getName() == null || workTypeLstFilterList.get(0).getName().v().equals(EMPTY)) {
                workTypeCDString = workTypeCode.v() + " " + I18NText.getText("KAF006_93");
            } else {
                workTypeCDString = workTypeLstFilterList.get(0).getName().v();
            }
        }
        
        Cell cellD9 = cells.get("D9");
        cellD9.setValue(workTypeCDString);
        
        /**
         * A3_2
         */
        String timeString = EMPTY;
        Optional<TimeDigestApplication> timeDegestion = printContentOfApplyForLeave.getApplyForLeave().getReflectFreeTimeApp().getTimeDegestion();
        if (timeDegestion.isPresent()) {
            // 60H超休
            AttendanceTime over60H = timeDegestion.get().getOvertime60H();
            if (over60H != null && over60H.v() > 0) {
                timeString += new StringBuilder(I18NText.getText("Com_ExsessHoliday"))
                        .append(HALF_WIDTH_SPACE)
                        .append(new TimeWithDayAttr(over60H.v()).getRawTimeWithFormat())
                        .append(fULL_WIDTH_SPACE)
                        .append(fULL_WIDTH_SPACE)
                        .toString();
            }
            
            // 時間代休
            AttendanceTime timeOff = timeDegestion.get().getTimeOff();
            if (timeOff != null && timeOff.v() > 0) {
                timeString += new StringBuilder(I18NText.getText("KAF006_30"))
                        .append(HALF_WIDTH_SPACE)
                        .append(new TimeWithDayAttr(timeOff.v()).getRawTimeWithFormat())
                        .append(fULL_WIDTH_SPACE)
                        .append(fULL_WIDTH_SPACE)
                        .toString();
            }
            
            // 時間年休
            AttendanceTime timeAnualLeave = timeDegestion.get().getTimeAnnualLeave();
            if (timeAnualLeave != null && timeAnualLeave.v() > 0) {
                timeString += new StringBuilder(I18NText.getText("KAF006_29"))
                        .append(HALF_WIDTH_SPACE)
                        .append(new TimeWithDayAttr(timeAnualLeave.v()).getRawTimeWithFormat())
                        .append(fULL_WIDTH_SPACE)
                        .append(fULL_WIDTH_SPACE)
                        .toString();
            }
            
            // 子の看護時間
            AttendanceTime childNursingTime = timeDegestion.get().getChildTime();
            if (childNursingTime != null && childNursingTime.v() > 0) {
                timeString += new StringBuilder(I18NText.getText("Com_ChildNurseHoliday"))
                        .append(HALF_WIDTH_SPACE)
                        .append(new TimeWithDayAttr(childNursingTime.v()).getRawTimeWithFormat())
                        .append(fULL_WIDTH_SPACE)
                        .append(fULL_WIDTH_SPACE)
                        .toString();
            }
            
            // 介護時間
            AttendanceTime nursingTime = timeDegestion.get().getNursingTime();
            if (nursingTime != null && nursingTime.v() > 0) {
                timeString += new StringBuilder(I18NText.getText("Com_CareHoliday"))
                        .append(HALF_WIDTH_SPACE)
                        .append(new TimeWithDayAttr(nursingTime.v()).getRawTimeWithFormat())
                        .append(fULL_WIDTH_SPACE)
                        .append(fULL_WIDTH_SPACE)
                        .toString();
            }
        }
        
        Cell cellD10 = cells.get("D10");
        cellD10.setValue(timeString);
        
        /**
         * A4_2
         */
        String workTimeString = EMPTY;
        NotUseAtr workChangeUse = printContentOfApplyForLeave.getApplyForLeave().getReflectFreeTimeApp().getWorkChangeUse();
        
        if (workChangeUse.equals(NotUseAtr.NOT_USE)) {
            workTimeString = I18NText.getText("KAF006_94");
        } else {
            Optional<List<WorkTimeSetting>> opWorkTimeLst = printContentOfApplyForLeave.getAppAbsenceStartInfoOutput()
                    .getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpWorkTimeLst();
            Optional<WorkTimeCode> optWorktimeCode = printContentOfApplyForLeave.getApplyForLeave().getReflectFreeTimeApp().getWorkInfo().getWorkTimeCodeNotNull();
            
            if (optWorktimeCode.isPresent()) {
                if (opWorkTimeLst.isPresent()) {
                    List<WorkTimeSetting> workTimeLst = opWorkTimeLst.get();
                    List<WorkTimeSetting> workTimeLstFilter = workTimeLst.stream()
                            .filter(workTime -> workTime.getWorktimeCode().equals(optWorktimeCode.get()))
                            .collect(Collectors.toList());
                    if (workTimeLstFilter.size() == 0) {
                        workTimeString = optWorktimeCode.get().v() + HALF_WIDTH_SPACE + I18NText.getText("KAF006_93");
                    } else {
                        if (workTimeLstFilter.get(0).getWorkTimeDisplayName() == null 
                                || workTimeLstFilter.get(0).getWorkTimeDisplayName().getWorkTimeName() == null 
                                || workTimeLstFilter.get(0).getWorkTimeDisplayName().getWorkTimeName().v().equals(EMPTY)) {
                            workTimeString = optWorktimeCode.get().v() + HALF_WIDTH_SPACE + I18NText.getText("KAF006_93");
                        } else {
                            workTimeString = workTimeLstFilter.get(0).getWorkTimeDisplayName().getWorkTimeName().v();
                        }
                    }
                }
            }
        }
        
        Cell cellD11 = cells.get("D11");
        cellD11.setValue(workTimeString);
        
        /**
         * A5_2
         * A6_2
         */
        Optional<List<TimeZoneWithWorkNo>> optWorkingHours = printContentOfApplyForLeave.getApplyForLeave().getReflectFreeTimeApp().getWorkingHours();
        String time1String = EMPTY;
        String time2String = EMPTY;
        
        if (workChangeUse.equals(NotUseAtr.NOT_USE)) {
            time1String = I18NText.getText("KAF006_94");
            time2String = I18NText.getText("KAF006_94");
        } else {
            if (optWorkingHours.isPresent()) {
                List<TimeZoneWithWorkNo> workingHours = optWorkingHours.get();
                // Working Time 1
                List<TimeZoneWithWorkNo> workingHours1 = workingHours.stream().filter(item -> item.getWorkNo().v() == 1).collect(Collectors.toList());
                if (workingHours1.size() > 0) {
                    time1String = new StringBuilder()
                            .append(workingHours1.get(0).getTimeZone().getStartTime().getFullText())
                            .append(HALF_WIDTH_SPACE)
                            .append("～")
                            .append(HALF_WIDTH_SPACE)
                            .append(workingHours1.get(0).getTimeZone().getEndTime().getFullText())
                            .toString();
                }
                
                // Working Time 2
                List<TimeZoneWithWorkNo> workingHours2 = workingHours.stream().filter(item -> item.getWorkNo().v() == 2).collect(Collectors.toList());
                if (workingHours2.size() > 0) {
                    time2String = new StringBuilder()
                            .append(workingHours2.get(0).getTimeZone().getStartTime().getFullText())
                            .append(HALF_WIDTH_SPACE)
                            .append("～")
                            .append(HALF_WIDTH_SPACE)
                            .append(workingHours2.get(0).getTimeZone().getEndTime().getFullText())
                            .toString();
                }
            }
        }
        
        Cell cellD12 = cells.get("D12");
        Cell cellD13 = cells.get("D13");
        
        cellD12.setValue(time1String);
        cellD13.setValue(time2String);
        
        /**
         * A7_2
         * A7_3
         */
        String relationString = EMPTY;
        Optional<ApplyforSpecialLeave> optApplyForSpeLeaveOptional = printContentOfApplyForLeave.getApplyForLeave().getVacationInfo().getInfo().getApplyForSpeLeaveOptional();
        if (optApplyForSpeLeaveOptional.isPresent()) {
            Optional<RelationshipCDPrimitive> optRelationshipCD = optApplyForSpeLeaveOptional.get().getRelationshipCD();
            
            if (optRelationshipCD.isPresent()) {
                RelationshipCDPrimitive relationshipCD = optRelationshipCD.get();
                
                Optional<SpecAbsenceDispInfo> optSpecAbsenceDispInfo = printContentOfApplyForLeave.getAppAbsenceStartInfoOutput().getSpecAbsenceDispInfo();
                if (optSpecAbsenceDispInfo.isPresent()) {
                    SpecAbsenceDispInfo specAbsenceDispInfo = optSpecAbsenceDispInfo.get();
                    Optional<List<DateSpecHdRelationOutput>> optDateSpecHdRelationLst = specAbsenceDispInfo.getDateSpecHdRelationLst();
                    
                    if (optDateSpecHdRelationLst.isPresent()) {
                        List<DateSpecHdRelationOutput> dateSpecHdRelationLst = optDateSpecHdRelationLst.get();
                        List<DateSpecHdRelationOutput> dateSpecHdRelationFilterLst = dateSpecHdRelationLst.stream()
                                .filter(relation -> relation.getRelationCD().equals(relationshipCD.v()))
                                .collect(Collectors.toList());
                        
                        if (dateSpecHdRelationFilterLst.size() > 0) {
                            relationString = dateSpecHdRelationFilterLst.get(0).getRelationName();
                        } else {
                            relationString = new StringBuilder()
                                    .append(relationshipCD.v())
                                    .append(HALF_WIDTH_SPACE)
                                    .append(I18NText.getText("KAF006_93"))
                                    .toString();
                        }
                    }
                }
                
                else {
                    relationString = new StringBuilder()
                            .append(relationshipCD.v())
                            .append(HALF_WIDTH_SPACE)
                            .append(I18NText.getText("KAF006_93"))
                            .toString();
                }
            }
            
            boolean mournerFlag = optApplyForSpeLeaveOptional.get().isMournerFlag();
            if (mournerFlag) {
                relationString += (HALF_WIDTH_SPACE + I18NText.getText("KAF006_92"));
            }
        }
        
        Cell cellD14 = cells.get("D14");
        cellD14.setValue(relationString);
        
        /**
         * A8_2
         */
        String reasonString = EMPTY;
        if (optApplyForSpeLeaveOptional.isPresent()) {
            Optional<RelationshipReasonPrimitive> optRelationshipReason = optApplyForSpeLeaveOptional.get().getRelationshipReason();
            if (optRelationshipReason.isPresent()) {
                reasonString = optRelationshipReason.get().v();
            }
        }
        
        Cell cellD15 = cells.get("D15");
        cellD15.setValue(reasonString);
        
        
        // Delete line by visible condition
        if (!(printContentOfApplyForLeave.getApplyForLeave().getVacationInfo().getHolidayApplicationType()
                .equals(HolidayAppType.SPECIAL_HOLIDAY) 
                && printContentOfApplyForLeave.getAppAbsenceStartInfoOutput().getSpecAbsenceDispInfo().isPresent()
                && printContentOfApplyForLeave.getAppAbsenceStartInfoOutput().getSpecAbsenceDispInfo().get().isSpecHdForEventFlag())) {
            cells.deleteRow(14);
            deleteCnt++;
            cells.deleteRow(13);
            deleteCnt++;
        }
//        if (!printContentOfApplyForLeave.getAppAbsenceStartInfoOutput().getSpecAbsenceDispInfo().isPresent()) {
//            cells.deleteRow(14);
//            deleteCnt++;
//            cells.deleteRow(13);
//            deleteCnt++;
//        }
        
        if (!printContentOfApplyForLeave.getAppAbsenceStartInfoOutput().isWorkHoursDisp()) {
            
        	cells.deleteRow(12);
        	deleteCnt++;
            cells.deleteRow(11);
            deleteCnt++;
            cells.deleteRow(10);
            deleteCnt++;
        } else {   	
        	if (!printContentOfApplyForLeave.getAppAbsenceStartInfoOutput().getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().isManagementMultipleWorkCycles()) {
                cells.deleteRow(12);
                deleteCnt++;
            }
        }
        
        
        if (!printContentOfApplyForLeave.getApplyForLeave().getVacationInfo().getHolidayApplicationType()
                .equals(HolidayAppType.DIGESTION_TIME)) {
            cells.deleteRow(9);
            deleteCnt++;
        }
        
        return deleteCnt;
    }
}
