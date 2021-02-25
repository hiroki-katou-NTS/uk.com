package nts.uk.file.at.infra.worktime;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.aspose.cells.Cells;

import nts.uk.ctx.at.shared.app.find.workdayoff.frame.WorkdayoffFrameFindDto;
import nts.uk.ctx.at.shared.app.find.workdayoff.frame.WorkdayoffFrameFinder;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.EmTimeZoneSetDto;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.OverTimeOfTimeZoneSetDto;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.PrioritySettingDto;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.StampReflectTimezoneDto;
import nts.uk.ctx.at.shared.app.find.worktime.dto.WorkTimeSettingInfoDto;
import nts.uk.ctx.at.shared.app.find.worktime.fixedset.dto.FixHalfDayWorkTimezoneDto;
import nts.uk.ctx.at.shared.app.find.worktime.predset.dto.TimezoneDto;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.common.usecls.ApplyAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FontRearSection;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.MultiStampTimePiorityAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingSet;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingTimeUnit;
import nts.uk.ctx.at.shared.dom.worktime.common.StampPiorityAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.Superiority;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class WorkTimeReportService_New {
    private final int NORMAL = 0;
    private final int FLOW = 1;
    private final int FLEX = 2;
    
    @Inject
    private WorkdayoffFrameFinder finder;
    
    /**
     * 勤務形態 通常
     * @param data
     * @param cells
     * @param startIndex
     */
    public void insertDataOneLineNormal(WorkTimeSettingInfoDto data, Cells cells, int startIndex) {
        Integer displayMode = data.getDisplayMode().displayMode;
        
        // 1        タブグ:                所定
        this.printDataPrescribed(data, cells, startIndex, NORMAL);
        
        // 2        タブグ:                勤務時間帯
        
        Optional<FixHalfDayWorkTimezoneDto> fixHalfDayWorkOneDayOpt = data.getFixedWorkSetting().getLstHalfDayWorkTimezone()
                .stream().filter(x -> x.getDayAtr().equals(AmPmAtr.ONE_DAY.value)).findFirst();
        Optional<FixHalfDayWorkTimezoneDto> fixHalfDayWorkMorningOpt = data.getFixedWorkSetting().getLstHalfDayWorkTimezone()
                .stream().filter(x -> x.getDayAtr().equals(AmPmAtr.AM.value)).findFirst();
        Optional<FixHalfDayWorkTimezoneDto> fixHalfDayWorkAfternoonOpt = data.getFixedWorkSetting().getLstHalfDayWorkTimezone()
                .stream().filter(x -> x.getDayAtr().equals(AmPmAtr.PM.value)).findFirst();
        
        if (fixHalfDayWorkOneDayOpt.isPresent()) {
            List<EmTimeZoneSetDto> lstWorkingTimezone = fixHalfDayWorkOneDayOpt.get().getWorkTimezone().getLstWorkingTimezone();
            Collections.sort(lstWorkingTimezone, new Comparator<EmTimeZoneSetDto>() {
                public int compare(EmTimeZoneSetDto o1, EmTimeZoneSetDto o2) {
                    return o1.getEmploymentTimeFrameNo().compareTo(o2.getEmploymentTimeFrameNo());
                };
            });
            
            for (int i = 0; i < lstWorkingTimezone.size(); i++) {
                /*
                 * R4_86
                 * 1日勤務用.開始時間
                 */
                Integer startTime = lstWorkingTimezone.get(i).getTimezone().getStart();
                cells.get("Z" + ((startIndex + 1) + i)).setValue(getInDayTimeWithFormat(startTime));
                
                /*
                 * R4_87
                 * 1日勤務用.終了時間
                 */
                Integer endTime = lstWorkingTimezone.get(i).getTimezone().getEnd();
                cells.get("AA" + ((startIndex + 1) + i)).setValue(getInDayTimeWithFormat(endTime));
                
                /*
                 * R4_88
                 * 1日勤務用.丸め
                 */
                Integer unit = lstWorkingTimezone.get(i).getTimezone().getRounding().getRoundingTime();
                String unitString = Unit.valueOf(unit).nameId;
                cells.get("AB" + ((startIndex + 1) + i)).setValue(unitString);
                
                /*
                 * R4_89
                 * 1日勤務用.端数
                 */
                Integer rounding = lstWorkingTimezone.get(i).getTimezone().getRounding().getRounding();
                String roundingString = getRoundingEnum(rounding);
                cells.get("AC" + ((startIndex + 1) + i)).setValue(roundingString);
            }
        }
        
        // 3        タブグ:                残業時間帯
        
        List<WorkdayoffFrameFindDto> otFrameFind = this.finder.findAllUsed();
        
        Boolean useHalfDayShift = data.getFixedWorkSetting().getUseHalfDayShift();
        Integer legalOTSetting = data.getFixedWorkSetting().getLegalOTSetting();
        
        if (displayMode.equals(DisplayMode.DETAIL.value)) {
            /*
             * R4_261
             * 半日勤務を設定する
             */
            cells.get("AD" + (startIndex + 1)).setValue(useHalfDayShift ? "する" : "しない");
            
            /*
             * R4_98
             * 法定内残業自動計算
             */
            cells.get("AE" + (startIndex + 1)).setValue(legalOTSetting == 1 ? "する" : "しない");
            
            // One day
            if (fixHalfDayWorkOneDayOpt.isPresent()) {
                List<OverTimeOfTimeZoneSetDto> lstOTTimezone = fixHalfDayWorkOneDayOpt.get().getWorkTimezone().getLstOTTimezone();
                Collections.sort(lstOTTimezone, new Comparator<OverTimeOfTimeZoneSetDto>() {
                    public int compare(OverTimeOfTimeZoneSetDto o1, OverTimeOfTimeZoneSetDto o2) {
                        return o1.getOtFrameNo().compareTo(o2.getOtFrameNo());
                    };
                });
                
                for (int i = 0; i < lstOTTimezone.size(); i++) {
                    /*
                     * R4_99
                     * 1日勤務用.開始時間
                     */
                    cells.get("AF" + (startIndex + 1)).setValue(getInDayTimeWithFormat(lstOTTimezone.get(i).getTimezone().getStart()));
                    
                    /*
                     * R4_100
                     * 1日勤務用.終了時間
                     */
                    cells.get("AG" + (startIndex + 1)).setValue(getInDayTimeWithFormat(lstOTTimezone.get(i).getTimezone().getEnd()));
                    
                    /*
                     * R4_101
                     * 1日勤務用.丸め
                     */
                    cells.get("AH" + (startIndex + 1))
                    .setValue(Unit.valueOf(lstOTTimezone.get(i).getTimezone().getRounding().getRoundingTime()).nameId);
                    
                    /*
                     * R4_102
                     * 1日勤務用.端数
                     */
                    cells.get("AI" + (startIndex + 1))
                    .setValue(getRoundingEnum(lstOTTimezone.get(i).getTimezone().getRounding().getRounding()));
                    
                    Integer otFrameNo = fixHalfDayWorkOneDayOpt.get().getWorkTimezone().getLstOTTimezone().get(i).getOtFrameNo();
                    Optional<WorkdayoffFrameFindDto> otFrameOpt = otFrameFind.stream().filter(x -> x.getWorkdayoffFrNo() == otFrameNo).findFirst();
                    
                    /*
                     * R4_103
                     * 1日勤務用.残業枠
                     */
                    cells.get("AJ" + (startIndex + 1)).setValue(otFrameOpt.isPresent() ? otFrameOpt.get().getTransferFrName() : "");
                    
                    /*
                     * R4_104
                     * 1日勤務用.早出
                     */
                    boolean isEarlyOTUse = fixHalfDayWorkOneDayOpt.get().getWorkTimezone().getLstOTTimezone().get(i).isEarlyOTUse();
                    cells.get("AK" + (startIndex + 1)).setValue(isEarlyOTUse ?  "○" : "-");
                    
                    Integer legalOTframeNo = fixHalfDayWorkOneDayOpt.get().getWorkTimezone().getLstOTTimezone().get(i).getLegalOTframeNo();
                    Optional<WorkdayoffFrameFindDto> legalOTframeOpt = otFrameFind.stream().filter(x -> x.getWorkdayoffFrNo() == legalOTframeNo).findFirst();
                    
                    if (legalOTSetting.equals(LegalOTSetting.LEGAL_INTERNAL_TIME.value)) {
                        /*
                         * R4_105
                         * 1日勤務用.法定内残業枠
                         */
                        cells.get("AL" + (startIndex + 1)).setValue(legalOTframeOpt.isPresent() ? legalOTframeOpt.get().getWorkdayoffFrName() : "");
                        
                        /*
                         * R4_106
                         * 1日勤務用.積残順序
                         */
                        Integer settlementOrder = fixHalfDayWorkOneDayOpt.get().getWorkTimezone().getLstOTTimezone().get(i).getSettlementOrder();
                        cells.get("AM" + (startIndex + 1)).setValue(settlementOrder != null ? getSetlementEnum(settlementOrder) : "");
                    }
                }
            }
            
            if (useHalfDayShift) {
                
                // Morning
                if (fixHalfDayWorkMorningOpt.isPresent()) {
                    List<OverTimeOfTimeZoneSetDto> lstOTTimezone = fixHalfDayWorkMorningOpt.get().getWorkTimezone().getLstOTTimezone();
                    Collections.sort(lstOTTimezone, new Comparator<OverTimeOfTimeZoneSetDto>() {
                        public int compare(OverTimeOfTimeZoneSetDto o1, OverTimeOfTimeZoneSetDto o2) {
                            return o1.getOtFrameNo().compareTo(o2.getOtFrameNo());
                        };
                    });
                    
                    for (int i = 0; i < lstOTTimezone.size(); i++) {
                        /*
                         * R4_107
                         * 午前勤務用.開始時間
                         */
                        cells.get("AN" + (startIndex + 1)).setValue(getInDayTimeWithFormat(lstOTTimezone.get(i).getTimezone().getStart()));
                        
                        /*
                         * R4_108
                         * 午前勤務用.終了時間
                         */
                        cells.get("AO" + (startIndex + 1)).setValue(getInDayTimeWithFormat(lstOTTimezone.get(i).getTimezone().getEnd()));
                        
                        /*
                         * R4_109
                         * 午前勤務用.丸め
                         */
                        cells.get("AP" + (startIndex + 1))
                        .setValue(Unit.valueOf(lstOTTimezone.get(i).getTimezone().getRounding().getRoundingTime()).nameId);
                        
                        /*
                         * R4_110
                         * 午前勤務用.端数
                         */
                        cells.get("AQ" + (startIndex + 1))
                        .setValue(getRoundingEnum(lstOTTimezone.get(i).getTimezone().getRounding().getRounding()));
                        
                        Integer otFrameNo = fixHalfDayWorkMorningOpt.get().getWorkTimezone().getLstOTTimezone().get(i).getOtFrameNo();
                        Optional<WorkdayoffFrameFindDto> otFrameOpt = otFrameFind.stream().filter(x -> x.getWorkdayoffFrNo() == otFrameNo).findFirst();
                        
                        /*
                         * R4_111
                         * 午前勤務用.残業枠
                         */
                        cells.get("AR" + (startIndex + 1)).setValue(otFrameOpt.isPresent() ? otFrameOpt.get().getTransferFrName() : "");
                        
                        /*
                         * R4_112
                         * 午前勤務用.早出
                         */
                        boolean isEarlyOTUse = fixHalfDayWorkMorningOpt.get().getWorkTimezone().getLstOTTimezone().get(i).isEarlyOTUse();
                        cells.get("AS" + (startIndex + 1)).setValue(isEarlyOTUse ?  "○" : "-");
                        
                        Integer legalOTframeNo = fixHalfDayWorkMorningOpt.get().getWorkTimezone().getLstOTTimezone().get(i).getLegalOTframeNo();
                        Optional<WorkdayoffFrameFindDto> legalOTframeOpt = otFrameFind.stream().filter(x -> x.getWorkdayoffFrNo() == legalOTframeNo).findFirst();
                        
                        if (legalOTSetting.equals(LegalOTSetting.LEGAL_INTERNAL_TIME.value)) {
                            /*
                             * R4_113
                             * 午前勤務用.法定内残業枠
                             */
                            cells.get("AT" + (startIndex + 1)).setValue(legalOTframeOpt.isPresent() ? legalOTframeOpt.get().getWorkdayoffFrName() : "");
                            
                            /*
                             * R4_114
                             * 午前勤務用.積残順序
                             */
                            Integer settlementOrder = fixHalfDayWorkMorningOpt.get().getWorkTimezone().getLstOTTimezone().get(i).getSettlementOrder();
                            cells.get("AU" + (startIndex + 1)).setValue(settlementOrder != null ? getSetlementEnum(settlementOrder) : "");
                        }
                    }
                }
                
                // Afternoon
                if (fixHalfDayWorkAfternoonOpt.isPresent()) {
                    List<OverTimeOfTimeZoneSetDto> lstOTTimezone = fixHalfDayWorkAfternoonOpt.get().getWorkTimezone().getLstOTTimezone();
                    Collections.sort(lstOTTimezone, new Comparator<OverTimeOfTimeZoneSetDto>() {
                        public int compare(OverTimeOfTimeZoneSetDto o1, OverTimeOfTimeZoneSetDto o2) {
                            return o1.getOtFrameNo().compareTo(o2.getOtFrameNo());
                        };
                    });
                    
                    for (int i = 0; i < lstOTTimezone.size(); i++) {
                        /*
                         * R4_115
                         * 午後勤務用.開始時間
                         */
                        cells.get("AV" + (startIndex + 1)).setValue(getInDayTimeWithFormat(lstOTTimezone.get(i).getTimezone().getStart()));
                        
                        /*
                         * R4_116
                         * 午後勤務用.終了時間
                         */
                        cells.get("AW" + (startIndex + 1)).setValue(getInDayTimeWithFormat(lstOTTimezone.get(i).getTimezone().getEnd()));
                        
                        /*
                         * R4_117
                         * 午後勤務用.丸め
                         */
                        cells.get("AX" + (startIndex + 1))
                        .setValue(Unit.valueOf(lstOTTimezone.get(i).getTimezone().getRounding().getRoundingTime()).nameId);
                        
                        /*
                         * R4_118
                         * 午後勤務用.端数
                         */
                        cells.get("AY" + (startIndex + 1))
                        .setValue(getRoundingEnum(lstOTTimezone.get(i).getTimezone().getRounding().getRounding()));
                        
                        Integer otFrameNo = fixHalfDayWorkAfternoonOpt.get().getWorkTimezone().getLstOTTimezone().get(i).getOtFrameNo();
                        Optional<WorkdayoffFrameFindDto> otFrameOpt = otFrameFind.stream().filter(x -> x.getWorkdayoffFrNo() == otFrameNo).findFirst();
                        
                        /*
                         * R4_119
                         * 午後勤務用.残業枠
                         */
                        cells.get("AZ" + (startIndex + 1)).setValue(otFrameOpt.isPresent() ? otFrameOpt.get().getTransferFrName() : "");
                        
                        /*
                         * R4_120
                         * 午後勤務用.早出
                         */
                        boolean isEarlyOTUse = fixHalfDayWorkAfternoonOpt.get().getWorkTimezone().getLstOTTimezone().get(i).isEarlyOTUse();
                        cells.get("BA" + (startIndex + 1)).setValue(isEarlyOTUse ?  "○" : "-");
                        
                        Integer legalOTframeNo = fixHalfDayWorkAfternoonOpt.get().getWorkTimezone().getLstOTTimezone().get(i).getLegalOTframeNo();
                        Optional<WorkdayoffFrameFindDto> legalOTframeOpt = otFrameFind.stream().filter(x -> x.getWorkdayoffFrNo() == legalOTframeNo).findFirst();
                        
                        if (legalOTSetting.equals(LegalOTSetting.LEGAL_INTERNAL_TIME.value)) {
                            /*
                             * R4_121
                             * 午後勤務用.法定内残業枠
                             */
                            cells.get("BB" + (startIndex + 1)).setValue(legalOTframeOpt.isPresent() ? legalOTframeOpt.get().getWorkdayoffFrName() : "");
                            
                            /*
                             * R4_122
                             * 午後勤務用.積残順序
                             */
                            Integer settlementOrder = fixHalfDayWorkAfternoonOpt.get().getWorkTimezone().getLstOTTimezone().get(i).getSettlementOrder();
                            cells.get("BC" + (startIndex + 1)).setValue(settlementOrder != null ? getSetlementEnum(settlementOrder) : "");
                        }
                    }
                }
            } 
        } else {
            if (fixHalfDayWorkOneDayOpt.isPresent()) {
                List<OverTimeOfTimeZoneSetDto> lstOTTimezone = fixHalfDayWorkOneDayOpt.get().getWorkTimezone().getLstOTTimezone();
                Collections.sort(lstOTTimezone, new Comparator<OverTimeOfTimeZoneSetDto>() {
                    public int compare(OverTimeOfTimeZoneSetDto o1, OverTimeOfTimeZoneSetDto o2) {
                        return o1.getOtFrameNo().compareTo(o2.getOtFrameNo());
                    };
                });
                
                for (int i = 0; i < lstOTTimezone.size(); i++) {
                    /*
                     * R4_99
                     * 1日勤務用.開始時間
                     */
                    cells.get("AF" + (startIndex + 1)).setValue(getInDayTimeWithFormat(lstOTTimezone.get(i).getTimezone().getStart()));
                    
                    /*
                     * R4_100
                     * 1日勤務用.終了時間
                     */
                    cells.get("AG" + (startIndex + 1)).setValue(getInDayTimeWithFormat(lstOTTimezone.get(i).getTimezone().getEnd()));
                    
                    /*
                     * R4_101
                     * 1日勤務用.丸め
                     */
                    cells.get("AH" + (startIndex + 1))
                    .setValue(Unit.valueOf(lstOTTimezone.get(i).getTimezone().getRounding().getRoundingTime()).nameId);
                    
                    /*
                     * R4_102
                     * 1日勤務用.端数
                     */
                    cells.get("AI" + (startIndex + 1))
                    .setValue(getRoundingEnum(lstOTTimezone.get(i).getTimezone().getRounding().getRounding()));
                    
                    Integer otFrameNo = fixHalfDayWorkOneDayOpt.get().getWorkTimezone().getLstOTTimezone().get(i).getOtFrameNo();
                    Optional<WorkdayoffFrameFindDto> otFrameOpt = otFrameFind.stream().filter(x -> x.getWorkdayoffFrNo() == otFrameNo).findFirst();
                    
                    /*
                     * R4_103
                     * 1日勤務用.残業枠
                     */
                    cells.get("AJ" + (startIndex + 1)).setValue(otFrameOpt.isPresent() ? otFrameOpt.get().getTransferFrName() : "");
                    
                    /*
                     * R4_104
                     * 1日勤務用.早出
                     */
                    boolean isEarlyOTUse = fixHalfDayWorkOneDayOpt.get().getWorkTimezone().getLstOTTimezone().get(i).isEarlyOTUse();
                    cells.get("AK" + (startIndex + 1)).setValue(isEarlyOTUse ?  "○" : "-");
                }
            }
        }
        
        // 4        タブグ:                打刻時間帯
        
        List<PrioritySettingDto> prioritySets = data.getFixedWorkSetting().getCommonSetting().getStampSet().getPrioritySets();
        Optional<PrioritySettingDto> prioritySetGoingWorkOpt = prioritySets.stream()
                .filter(x -> x.getStampAtr().equals(StampPiorityAtr.GOING_WORK.value)).findFirst();
        Optional<PrioritySettingDto> prioritySetLeaveWorkOpt = prioritySets.stream()
                .filter(x -> x.getStampAtr().equals(StampPiorityAtr.LEAVE_WORK.value)).findFirst();
        Optional<PrioritySettingDto> prioritySetEnteringOpt = prioritySets.stream()
                .filter(x -> x.getStampAtr().equals(StampPiorityAtr.ENTERING.value)).findFirst();
        Optional<PrioritySettingDto> prioritySetExitOpt = prioritySets.stream()
                .filter(x -> x.getStampAtr().equals(StampPiorityAtr.EXIT.value)).findFirst();
        Optional<PrioritySettingDto> prioritySetPCLoginOpt = prioritySets.stream()
                .filter(x -> x.getStampAtr().equals(StampPiorityAtr.PCLOGIN.value)).findFirst();
        Optional<PrioritySettingDto> prioritySetPCLogoutOpt = prioritySets.stream()
                .filter(x -> x.getStampAtr().equals(StampPiorityAtr.PC_LOGOUT.value)).findFirst();
        if (displayMode.equals(DisplayMode.DETAIL.value)) {
            
            /*
             * R4_123
             * 優先設定.出勤
             */
            if (prioritySetGoingWorkOpt.isPresent()) {
                Integer priorityAtr = prioritySetGoingWorkOpt.get().getPriorityAtr();
                cells.get("BD" + (startIndex + 1)).setValue(priorityAtr.equals(MultiStampTimePiorityAtr.BEFORE_PIORITY.value) ? "前優先" : "後優先");
            }
            
            /*
             * R4_124
             * 優先設定.退勤
             */
            if (prioritySetLeaveWorkOpt.isPresent()) {
                Integer priorityAtr = prioritySetLeaveWorkOpt.get().getPriorityAtr();
                cells.get("BE" + (startIndex + 1)).setValue(priorityAtr.equals(MultiStampTimePiorityAtr.BEFORE_PIORITY.value) ? "前優先" : "後優先");
            }
        }
        
        List<RoundingSet> roundingSets = data.getFixedWorkSetting().getCommonSetting().getStampSet().getRoundingTime().getRoundingSets();
        Optional<RoundingSet> roundingAttendanceSetOpt = roundingSets.stream().filter(x -> x.getSection().equals(Superiority.ATTENDANCE)).findFirst();
        Optional<RoundingSet> roundingOfficeSetOpt = roundingSets.stream().filter(x -> x.getSection().equals(Superiority.OFFICE_WORK)).findFirst();
        Optional<RoundingSet> roundingGoOutSetOpt = roundingSets.stream().filter(x -> x.getSection().equals(Superiority.GO_OUT)).findFirst();
        Optional<RoundingSet> roundingTurnBackSetOpt = roundingSets.stream().filter(x -> x.getSection().equals(Superiority.TURN_BACK)).findFirst();

        if (roundingAttendanceSetOpt.isPresent()) {
            /*
             * R4_125
             * 打刻丸め.出勤
             */
            RoundingTimeUnit roundingTimeUnit = roundingAttendanceSetOpt.get().getRoundingSet().getRoundingTimeUnit();
            cells.get("BF" + (startIndex + 1)).setValue(getRoundingTimeUnitEnum(roundingTimeUnit.value));
            
            /*
             * R4_126
             * 打刻丸め.出勤前後設定
             */
            FontRearSection fontRearSection = roundingAttendanceSetOpt.get().getRoundingSet().getFontRearSection();
            cells.get("BG" + (startIndex + 1)).setValue(fontRearSection.description);
        }
        
        if (roundingOfficeSetOpt.isPresent()) {
            /*
             * R4_127
             * 打刻丸め.退勤
             */
            RoundingTimeUnit roundingTimeUnit = roundingOfficeSetOpt.get().getRoundingSet().getRoundingTimeUnit();
            cells.get("BH" + (startIndex + 1)).setValue(getRoundingTimeUnitEnum(roundingTimeUnit.value));
            
            /*
             * R4_128
             * 打刻丸め.退勤前後設定
             */
            FontRearSection fontRearSection = roundingOfficeSetOpt.get().getRoundingSet().getFontRearSection();
            cells.get("BI" + (startIndex + 1)).setValue(fontRearSection.description);
        }
        
        if (displayMode.equals(DisplayMode.DETAIL.value)) {
            List<StampReflectTimezoneDto> lstStampReflectTimezone = data.getFixedWorkSetting().getLstStampReflectTimezone();
            Optional<StampReflectTimezoneDto> lst1stGoWorkOpt = lstStampReflectTimezone.stream()
                .filter(x -> (x.getWorkNo() == 1 && x.getClassification().equals(GoLeavingWorkAtr.GO_WORK.value)))
                .findFirst();
            
            Optional<StampReflectTimezoneDto> lst2ndGoWorkOpt = lstStampReflectTimezone.stream()
                    .filter(x -> (x.getWorkNo() == 2 && x.getClassification().equals(GoLeavingWorkAtr.GO_WORK.value)))
                    .findFirst();
            
            Optional<StampReflectTimezoneDto> lst1stLeavingWorkOpt = lstStampReflectTimezone.stream()
                .filter(x -> (x.getWorkNo() == 1 && x.getClassification().equals(GoLeavingWorkAtr.LEAVING_WORK.value)))
                .findFirst();
            
            Optional<StampReflectTimezoneDto> lst2ndLeavingWorkOpt = lstStampReflectTimezone.stream()
                    .filter(x -> (x.getWorkNo() == 2 && x.getClassification().equals(GoLeavingWorkAtr.LEAVING_WORK.value)))
                    .findFirst();
            
            if (lst1stGoWorkOpt.isPresent()) {
                /*
                 * R4_129
                 * 出勤反映時間帯1回目.開始時刻
                 */
                Integer startTime = lst1stGoWorkOpt.get().getStartTime();
                cells.get("BJ" + (startIndex + 1)).setValue(startTime != null ? getInDayTimeWithFormat(startTime) : "");
                
                /*
                 * R4_130
                 * 出勤反映時間帯1回目.終了時刻
                 */
                Integer endTime = lst1stGoWorkOpt.get().getEndTime();
                cells.get("BK" + (startIndex + 1)).setValue(endTime != null ? getInDayTimeWithFormat(endTime) : "");
            }
            
            if (lst2ndGoWorkOpt.isPresent()) {
                /*
                 * R4_131
                 * 出勤反映時間帯2回目.開始時刻
                 */
                Integer startTime = lst2ndGoWorkOpt.get().getStartTime();
                cells.get("BL" + (startIndex + 1)).setValue(startTime != null ? getInDayTimeWithFormat(startTime) : "");
                
                /*
                 * R4_132
                 * 出勤反映時間帯2回目.終了時刻
                 */
                Integer endTime = lst2ndGoWorkOpt.get().getEndTime();
                cells.get("BM" + (startIndex + 1)).setValue(endTime != null ? getInDayTimeWithFormat(endTime) : "");
            }
            
            if (lst1stLeavingWorkOpt.isPresent()) {
                /*
                 * R4_133
                 * 退勤反映時間帯1回目.開始時刻
                 */
                Integer startTime = lst1stLeavingWorkOpt.get().getStartTime();
                cells.get("BN" + (startIndex + 1)).setValue(startTime != null ? getInDayTimeWithFormat(startTime) : "");
                
                /*
                 * R4_134
                 * 退勤反映時間帯1回目.終了時刻
                 */
                Integer endTime = lst1stLeavingWorkOpt.get().getEndTime();
                cells.get("BO" + (startIndex + 1)).setValue(endTime != null ? getInDayTimeWithFormat(endTime) : "");
            }
            
            if (lst2ndLeavingWorkOpt.isPresent()) {
                /*
                 * R4_135
                 * 退勤反映時間帯2回目.開始時刻
                 */
                Integer startTime = lst2ndLeavingWorkOpt.get().getStartTime();
                cells.get("BP" + (startIndex + 1)).setValue(startTime != null ? getInDayTimeWithFormat(startTime) : "");
                
                /*
                 * R4_136
                 * 退勤反映時間帯2回目.終了時刻
                 */
                Integer endTime = lst2ndLeavingWorkOpt.get().getEndTime();
                cells.get("BO" + (startIndex + 1)).setValue(endTime != null ? getInDayTimeWithFormat(endTime) : "");
            }
            
            /*
             * R4_137
             * 優先設定.入門
             */
            if (prioritySetEnteringOpt.isPresent()) {
                cells.get("BR" + (startIndex + 1)).setValue(getPrioritySetAtr(prioritySetEnteringOpt.get().getPriorityAtr()));
            }
            
            /*
             * R4_138
             * 優先設定.退門
             */
            if (prioritySetExitOpt.isPresent()) {
                cells.get("BS" + (startIndex + 1)).setValue(getPrioritySetAtr(prioritySetExitOpt.get().getPriorityAtr()));
            }
            
            /*
             * R4_139
             * 優先設定.PCログオン
             */
            if (prioritySetPCLoginOpt.isPresent()) {
                cells.get("BT" + (startIndex + 1)).setValue(getPrioritySetAtr(prioritySetPCLoginOpt.get().getPriorityAtr()));
            }
            
            /*
             * R4_140
             * 優先設定.PCログオフ
             */
            if (prioritySetPCLogoutOpt.isPresent()) {
                cells.get("BU" + (startIndex + 1)).setValue(getPrioritySetAtr(prioritySetPCLogoutOpt.get().getPriorityAtr()));
            }
            
            if (roundingGoOutSetOpt.isPresent()) {
                /*
                 * R4_141
                 * 打刻丸め.外出
                 */
                RoundingTimeUnit roundingTimeUnit = roundingGoOutSetOpt.get().getRoundingSet().getRoundingTimeUnit();
                cells.get("BV" + (startIndex + 1)).setValue(getRoundingTimeUnitEnum(roundingTimeUnit.value));
                
                /*
                 * R4_142
                 * 打刻丸め.外出前後ずらし
                 */
                FontRearSection fontRearSection = roundingGoOutSetOpt.get().getRoundingSet().getFontRearSection();
                cells.get("BW" + (startIndex + 1)).setValue(fontRearSection.value == 0 ? "前にずらす" : "後ろにずらす");
            }
            
            if (roundingTurnBackSetOpt.isPresent()) {
                /*
                 * R4_143
                 * 打刻丸め.戻り
                 */
                RoundingTimeUnit roundingTimeUnit = roundingTurnBackSetOpt.get().getRoundingSet().getRoundingTimeUnit();
                cells.get("BX" + (startIndex + 1)).setValue(getRoundingTimeUnitEnum(roundingTimeUnit.value));
                
                /*
                 * R4_144
                 * 打刻丸め.戻り前後ずらし
                 */
                FontRearSection fontRearSection = roundingTurnBackSetOpt.get().getRoundingSet().getFontRearSection();
                cells.get("BY" + (startIndex + 1)).setValue(fontRearSection.value == 0 ? "前にずらす" : "後ろにずらす");
            }
            
            /*
             * R4_264
             * 計算設定.出勤を1分後から計算する
             */
            NotUseAtr attendanceMinuteLaterCalculate = data.getFixedWorkSetting().getCommonSetting().getStampSet().getRoundingTime().getAttendanceMinuteLaterCalculate();
            cells.get("BZ" + (startIndex + 1)).setValue(attendanceMinuteLaterCalculate.equals(NotUseAtr.NOT_USE) ? "-" : "○");
            
            /*
             * R4_265
             * 計算設定.退勤を1分前まで計算する
             */
            NotUseAtr leaveWorkMinuteAgoCalculate = data.getFixedWorkSetting().getCommonSetting().getStampSet().getRoundingTime().getLeaveWorkMinuteAgoCalculate();
            cells.get("CA" + (startIndex + 1)).setValue(leaveWorkMinuteAgoCalculate.equals(NotUseAtr.NOT_USE) ? "-" : "○");
        }
        
        // 5        タブグ:                休憩時間帯
        
        /*
         * R4_262
         * 半日勤務を設定する
         */
        
    }
    
    /**
     * 勤務形態 流動
     * @param data
     * @param cell
     * @param startIndex
     */
    public void insertDataOneLineFlow(WorkTimeSettingInfoDto data, Cells cells, int startIndex) {
        Integer displayMode = data.getDisplayMode().displayMode;
        
        this.printDataPrescribed(data, cells, startIndex, FLOW);
    }
    
    /**
     * 勤務形態 フレックス
     * @param data
     * @param cell
     * @param startIndex
     */
    public void insertDataOneLineFlex(WorkTimeSettingInfoDto data, Cells cells, int startIndex) {
        Integer displayMode = data.getDisplayMode().displayMode;
        
        this.printDataPrescribed(data, cells, startIndex, FLEX);
    }
    
    /**
     * タブグ: 所定
     * @param data
     * @param cells
     * @param startIndex
     */
    private void printDataPrescribed(WorkTimeSettingInfoDto data, Cells cells, int startIndex, int type) {
        Integer displayMode = data.getDisplayMode().displayMode;
        int columnIndex = 0;
        
        /*
         * R4_59
         * R5_51
         * R6_61
         * コード
         */
        String workTimeCode = data.getWorktimeSetting().worktimeCode;
        cells.get(startIndex, columnIndex).setValue(workTimeCode);
        columnIndex++;
        
        /*
         * R4_60
         * R5_52
         * R6_62
         * 名称
         */
        String workTimeName = data.getWorktimeSetting().workTimeDisplayName.workTimeName;
        cells.get(startIndex, columnIndex).setValue(workTimeName);
        columnIndex++;
        
        /*
         * R4_61
         * R5_53
         * R6_63
         * 略名
         */
        String workTimeAbName = data.getWorktimeSetting().workTimeDisplayName.workTimeAbName;
        cells.get(startIndex, columnIndex).setValue(workTimeAbName);
        columnIndex++;
        
        /*
         * R4_63
         * R5_55
         * R6_65
         * 廃止
         */
        String isAbolish = data.getWorktimeSetting().isAbolish ?  "○" : "-";
        cells.get(startIndex, columnIndex).setValue(isAbolish);
        columnIndex++;
        
        /*
         * R4_64
         * R5_56
         * R6_66
         * 勤務形態
         */
        String workStyle = data.getWorktimeSetting().workTimeDivision.workTimeDailyAtr == WorkTimeDailyAtr.REGULAR_WORK.value ? 
                WorkTimeDailyAtr.REGULAR_WORK.description : "";
        cells.get(startIndex, columnIndex).setValue(workStyle);
        columnIndex++;
        
        /*
         * R4_65
         * R5_57
         * 設定方法
         */
        if (type != FLEX) {
            String settingMethod = data.getWorktimeSetting().workTimeDivision.workTimeMethodSet == WorkTimeMethodSet.FIXED_WORK.value ? 
                    WorkTimeMethodSet.FIXED_WORK.description : "";
            cells.get(startIndex, columnIndex).setValue(settingMethod);
            columnIndex++;
        }
        
        /*
         * R4_67
         * R5_58
         * R6_67
         * モード
         */
        String displayModeString = displayMode.equals(DisplayMode.SIMPLE.value) ? DisplayMode.SIMPLE.description : DisplayMode.DETAIL.description;
        cells.get(startIndex, columnIndex).setValue(displayModeString);
        columnIndex++;
        
        /*
         * R4_68
         * R5_60
         * R6_69
         * 備考
         */
        String note = data.getWorktimeSetting().note;
        cells.get(startIndex, columnIndex).setValue(note);
        columnIndex++;
        
        /*
         * R4_69
         * R5_61
         * R6_70
         * コメント
         */
        String memo = data.getWorktimeSetting().memo;
        cells.get(startIndex, columnIndex).setValue(memo);
        columnIndex++;
        
        /*
         * R4_70
         * R5_62
         * R6_71
         * 所定設定.1日の始まりの時刻
         */
        int timeOfDayStart = data.getPredseting().startDateClock;
        cells.get(startIndex, columnIndex).setValue(getInDayTimeWithFormat(timeOfDayStart));
        columnIndex++;
        
        /*
         * R4_71
         * R5_63
         * R6_72
         * 1日の範囲時間.時間
         */
        if (displayMode.equals(DisplayMode.DETAIL.value)) {
            int rangeTimeOfDay = data.getPredseting().getRangeTimeDay();
            cells.get(startIndex, columnIndex).setValue(rangeTimeOfDay);
            columnIndex++;
        }
        
        Optional<TimezoneDto> time = data.getPredseting().getPrescribedTimezoneSetting().getLstTimezone().stream()
                .filter(x -> x.workNo == 1).findFirst();
        if (time.isPresent()) {
            /*
             * R4_73
             * R5_65
             * R6_74
             * 就業時刻1回目.始業時刻
             */
            Integer startTimeInt = time.get().start;
            cells.get(startIndex, columnIndex).setValue(startTimeInt != null ? 
                    getInDayTimeWithFormat(startTimeInt) : "");
            columnIndex++;
            
            /*
             * R4_74
             * R5_66
             * R6_75
             * 就業時刻1回目.終業時刻
             */
            Integer endTimeInt = time.get().end;
            cells.get(startIndex, columnIndex).setValue(endTimeInt != null ? 
                    getInDayTimeWithFormat(endTimeInt) : "");
            columnIndex++;
        } else {
            columnIndex += 2;
        }
        
        /*
         * R4_75
         * 就業時刻1回目.残業を含めた所定時間帯を設定する
         */
        if (type == NORMAL) {
            if (displayMode.equals(DisplayMode.DETAIL.value)) {
                String predetermine = data.getPredseting().isPredetermine() ? "○" : "-";
                cells.get(startIndex, columnIndex).setValue(predetermine);
            }
            columnIndex++;
        }
        
        // 就業時刻2回目
        if (type != FLEX) {
            if (displayMode.equals(DisplayMode.DETAIL.value)) {
                Optional<TimezoneDto> time2 = data.getPredseting().getPrescribedTimezoneSetting().getLstTimezone().stream()
                        .filter(x -> x.workNo == 2).findFirst();
                if (time2.isPresent()) {
                    /*
                     * R4_4
                     * R5_4
                     * 就業時刻2回目.2回目を使用する
                     */
                    String useTime2 = time2.get().isUseAtr() ? "○" : "-";
                    cells.get(startIndex, columnIndex).setValue(useTime2);
                    columnIndex++;
                    
                    /*
                     * R4_76
                     * R5_69
                     * 就業時刻2回目.始業時刻
                     */
                    Integer startTime2 = time2.get().getStart();
                    cells.get(startIndex, columnIndex).setValue(startTime2 != null ? 
                            getInDayTimeWithFormat(startTime2) : "");
                    columnIndex++;
                    
                    /*
                     * R4_77
                     * R5_70
                     * 就業時刻2回目.終業時刻
                     */
                    Integer endTime2 = time2.get().getEnd();
                    cells.get(startIndex, columnIndex).setValue(endTime2 != null ? 
                            getInDayTimeWithFormat(endTime2) : "");
                    columnIndex++;
                } else {
                    columnIndex += 3;
                }
            } else {
                columnIndex += 3;
            }
        }
        
        // コアタイム時間帯
        if (type == FLEX) {
            /*
             * R6_4
             * 利用
             */
            Integer coreTimeZoneUsage = data.getFlexWorkSetting().getCoreTimeSetting().getTimesheet();
            String useCoreTimeZone = coreTimeZoneUsage.equals(ApplyAtr.USE.value) ? ApplyAtr.USE.nameId : ApplyAtr.NOT_USE.nameId;
            cells.get(startIndex, columnIndex).setValue(useCoreTimeZone);
            columnIndex++;
            
            /*
             * R6_77
             * 始業時刻
             */
            Integer coreTimeStart = data.getFlexWorkSetting().getCoreTimeSetting().getCoreTimeSheet().getStartTime();
            cells.get(startIndex, columnIndex).setValue(coreTimeStart != null ? getInDayTimeWithFormat(coreTimeStart) : "");
            columnIndex++;
            
            /*
             * R6_78
             * 終業時刻
             */
            Integer coreTimeEnd = data.getFlexWorkSetting().getCoreTimeSetting().getCoreTimeSheet().getEndTime();
            cells.get(startIndex, columnIndex).setValue(coreTimeEnd != null ? getInDayTimeWithFormat(coreTimeEnd) : "");
            columnIndex++;
            
            if (displayMode.equals(DisplayMode.DETAIL.value)) {
                /*
                 * R6_258
                 * コアタイム内と外の外出時間を分けて集計する
                 */
                boolean aggregateTime = true;
                cells.get(startIndex, columnIndex).setValue(aggregateTime ? "○" : "-");
                columnIndex++;
                
                /*
                 * R6_259
                 * コアタイム内の外出時間を就業時間から控除する
                 */
                boolean deductTime = true;
                cells.get(startIndex, columnIndex).setValue(deductTime ? "○" : "-");
                columnIndex++;
            } else {
                columnIndex += 2;
            }
        }
        
        /*
         * R4_78
         * R5_71
         * R6_80
         * 半日勤務.前半終了時刻
         */
        Integer firstHalfEnd = data.getPredseting().getPrescribedTimezoneSetting().getMorningEndTime();
        String firstHalfEndString = firstHalfEnd != null ? getInDayTimeWithFormat(firstHalfEnd) : "";
        cells.get(startIndex, columnIndex).setValue(firstHalfEndString);
        columnIndex++;
        
        /*
         * R4_79
         * R5_72
         * R6_81
         * 半日勤務.後半開始時刻
         */
        Integer secondHalfStart = data.getPredseting().getPrescribedTimezoneSetting().getAfternoonStartTime();
        String secondHalfStartString = secondHalfStart != null ? getInDayTimeWithFormat(secondHalfStart) : "";
        cells.get(startIndex, columnIndex).setValue(secondHalfStartString);
        columnIndex++;
        
        /*
         * R4_80
         * R5_73
         * R6_82
         * 所定時間.1日
         */
        Integer oneDayPredTime = data.getPredseting().getPredTime().getPredTime().getOneDay();
        cells.get(startIndex, columnIndex).setValue(oneDayPredTime != null ? getInDayTimeWithFormat(oneDayPredTime) : "");
        columnIndex++;
        
        /*
         * R4_81
         * R5_74
         * R6_83
         * 所定時間.午前
         */
        Integer morningPredTime = data.getPredseting().getPredTime().getPredTime().getMorning();
        cells.get(startIndex, columnIndex).setValue(morningPredTime != null ? getInDayTimeWithFormat(morningPredTime) : "");
        columnIndex++;
        
        /*
         * R4_82
         * R5_75
         * R6_84
         * 所定時間.午後
         */
        Integer afternoonPredTime = data.getPredseting().getPredTime().getPredTime().getAfternoon();
        cells.get(startIndex, columnIndex).setValue(afternoonPredTime != null ? getInDayTimeWithFormat(afternoonPredTime) : "");
        columnIndex++;
        
        if (displayMode.equals(DisplayMode.DETAIL.value)) {
            /*
             * R4_83
             * R5_76
             * R6_85
             * 休暇取得時加算時間.1日
             */
            Integer oneDayAddTime = data.getPredseting().getPredTime().getPredTime().getOneDay();
            cells.get(startIndex, columnIndex).setValue(oneDayAddTime != null ? getInDayTimeWithFormat(oneDayAddTime) : "");
            columnIndex++;
            
            /*
             * R4_84
             * R5_77
             * R6_86
             * 休暇取得時加算時間.午前
             */
            Integer morningAddTime = data.getPredseting().getPredTime().getPredTime().getOneDay();
            cells.get(startIndex, columnIndex).setValue(morningAddTime != null ? getInDayTimeWithFormat(morningAddTime) : "");
            columnIndex++;
            
            /*
             * R4_85
             * R5_78
             * R6_87
             * 休暇取得時加算時間.午後
             */
            Integer afternoonAddTime = data.getPredseting().getPredTime().getPredTime().getOneDay();
            cells.get(startIndex, columnIndex).setValue(afternoonAddTime != null ? getInDayTimeWithFormat(afternoonAddTime) : "");
            columnIndex++;
        }
    }
    
    /**
     * @param time
     * @return
     */
    private static String getInDayTimeWithFormat(int time) {
        return new TimeWithDayAttr(time).getInDayTimeWithFormat();
    }
    
    /**
     * Get Rounding Text by value
     * 切り捨て = 0
     * 切り上げ = 1
     * @param rounding
     * @return
     */
    private static String getRoundingEnum(int rounding) {
        if (rounding == 0) {
            return "切り捨て";
        }
        if (rounding == 1) {
            return "切り上げ";
        }
        
        return "";
    }
    
    /**
     * 積残順序
     * @param settlementOrder
     * @return
     */
    private static String getSetlementEnum(int settlementOrder) {
        String[] settlementOrderAtr = {"１", "２", "３", "４", "５", "６", "７", "８", "９", "１０"};
        for (int i = 1; i <= settlementOrderAtr.length; i++) {
            if (settlementOrder == i) {
                return settlementOrderAtr[i];
            }
        }
        
        return "";
    }
    
    /**
     * 時刻丸め単位
     * @param roundingTimeUnit
     * @return
     */
    private static String getRoundingTimeUnitEnum(int roundingTimeUnit) {
        String[] roundingTimeUnitAtr = {"1分", "5分", "6分", "10分", "15分", "20分", "30分", "60分"};
        for (int i = 1; i <= roundingTimeUnitAtr.length; i++) {
            if (roundingTimeUnit == i) {
                return roundingTimeUnitAtr[i];
            }
        }
        
        return "";
    }
    
    /**
     * 優先設定
     * @param priority
     * @return
     */
    private static String getPrioritySetAtr(int priority) {
        String[] priorityAtr = {"最初", "最後"};
        for (int i = 0; i <= priorityAtr.length; i++) {
            if (priority == i) {
                return priorityAtr[i];
            }
        }
        
        return "";
    }
}
