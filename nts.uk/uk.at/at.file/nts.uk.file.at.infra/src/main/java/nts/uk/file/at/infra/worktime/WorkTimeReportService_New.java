package nts.uk.file.at.infra.worktime;

import com.aspose.cells.Cells;
import nts.uk.ctx.at.shared.app.find.workdayoff.frame.WorkdayoffFrameFindDto;
import nts.uk.ctx.at.shared.app.find.workdayoff.frame.WorkdayoffFrameFinder;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.*;
import nts.uk.ctx.at.shared.app.find.worktime.dto.WorkTimeSettingInfoDto;
import nts.uk.ctx.at.shared.app.find.worktime.fixedset.dto.FixHalfDayWorkTimezoneDto;
import nts.uk.ctx.at.shared.app.find.worktime.predset.dto.TimezoneDto;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.common.usecls.ApplyAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.BPSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.worktime.common.*;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

import javax.ejb.Stateless;
import javax.inject.Inject;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Stateless
public class WorkTimeReportService_New {
    private final int NORMAL = 0;
    private final int FLOW = 1;
    private final int FLEX = 2;
    
    @Inject
    private WorkdayoffFrameFinder finder;
    
    @Inject
    private BPSettingRepository bpSettingRepository;
    
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
        
        Boolean useHalfDayShiftOverTime = data.getFixedWorkSetting().getUseHalfDayShift().isOverTime();
        Integer legalOTSetting = data.getFixedWorkSetting().getLegalOTSetting();
        
        if (displayMode.equals(DisplayMode.DETAIL.value)) {
            /*
             * R4_261
             * 半日勤務を設定する
             */
            cells.get("AD" + (startIndex + 1)).setValue(useHalfDayShiftOverTime ? "する" : "しない");
            
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
            
            if (useHalfDayShiftOverTime) {
                
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
                        cells.get("AN" + ((startIndex + 1) + i)).setValue(getInDayTimeWithFormat(lstOTTimezone.get(i).getTimezone().getStart()));
                        
                        /*
                         * R4_108
                         * 午前勤務用.終了時間
                         */
                        cells.get("AO" + ((startIndex + 1) + i)).setValue(getInDayTimeWithFormat(lstOTTimezone.get(i).getTimezone().getEnd()));
                        
                        /*
                         * R4_109
                         * 午前勤務用.丸め
                         */
                        cells.get("AP" + ((startIndex + 1) + i))
                        .setValue(Unit.valueOf(lstOTTimezone.get(i).getTimezone().getRounding().getRoundingTime()).nameId);
                        
                        /*
                         * R4_110
                         * 午前勤務用.端数
                         */
                        cells.get("AQ" + ((startIndex + 1) + i))
                        .setValue(getRoundingEnum(lstOTTimezone.get(i).getTimezone().getRounding().getRounding()));
                        
                        Integer otFrameNo = fixHalfDayWorkMorningOpt.get().getWorkTimezone().getLstOTTimezone().get(i).getOtFrameNo();
                        Optional<WorkdayoffFrameFindDto> otFrameOpt = otFrameFind.stream().filter(x -> x.getWorkdayoffFrNo() == otFrameNo).findFirst();
                        
                        /*
                         * R4_111
                         * 午前勤務用.残業枠
                         */
                        cells.get("AR" + ((startIndex + 1) + i)).setValue(otFrameOpt.isPresent() ? otFrameOpt.get().getTransferFrName() : "");
                        
                        /*
                         * R4_112
                         * 午前勤務用.早出
                         */
                        boolean isEarlyOTUse = fixHalfDayWorkMorningOpt.get().getWorkTimezone().getLstOTTimezone().get(i).isEarlyOTUse();
                        cells.get("AS" + ((startIndex + 1) + i)).setValue(isEarlyOTUse ?  "○" : "-");
                        
                        Integer legalOTframeNo = fixHalfDayWorkMorningOpt.get().getWorkTimezone().getLstOTTimezone().get(i).getLegalOTframeNo();
                        Optional<WorkdayoffFrameFindDto> legalOTframeOpt = otFrameFind.stream().filter(x -> x.getWorkdayoffFrNo() == legalOTframeNo).findFirst();
                        
                        if (legalOTSetting.equals(LegalOTSetting.LEGAL_INTERNAL_TIME.value)) {
                            /*
                             * R4_113
                             * 午前勤務用.法定内残業枠
                             */
                            cells.get("AT" + ((startIndex + 1) + i)).setValue(legalOTframeOpt.isPresent() ? legalOTframeOpt.get().getWorkdayoffFrName() : "");
                            
                            /*
                             * R4_114
                             * 午前勤務用.積残順序
                             */
                            Integer settlementOrder = fixHalfDayWorkMorningOpt.get().getWorkTimezone().getLstOTTimezone().get(i).getSettlementOrder();
                            cells.get("AU" + ((startIndex + 1) + i)).setValue(settlementOrder != null ? getSetlementEnum(settlementOrder) : "");
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
                        cells.get("AV" + ((startIndex + 1) + i)).setValue(getInDayTimeWithFormat(lstOTTimezone.get(i).getTimezone().getStart()));
                        
                        /*
                         * R4_116
                         * 午後勤務用.終了時間
                         */
                        cells.get("AW" + ((startIndex + 1) + i)).setValue(getInDayTimeWithFormat(lstOTTimezone.get(i).getTimezone().getEnd()));
                        
                        /*
                         * R4_117
                         * 午後勤務用.丸め
                         */
                        cells.get("AX" + ((startIndex + 1) + i))
                        .setValue(Unit.valueOf(lstOTTimezone.get(i).getTimezone().getRounding().getRoundingTime()).nameId);
                        
                        /*
                         * R4_118
                         * 午後勤務用.端数
                         */
                        cells.get("AY" + ((startIndex + 1) + i))
                        .setValue(getRoundingEnum(lstOTTimezone.get(i).getTimezone().getRounding().getRounding()));
                        
                        Integer otFrameNo = fixHalfDayWorkAfternoonOpt.get().getWorkTimezone().getLstOTTimezone().get(i).getOtFrameNo();
                        Optional<WorkdayoffFrameFindDto> otFrameOpt = otFrameFind.stream().filter(x -> x.getWorkdayoffFrNo() == otFrameNo).findFirst();
                        
                        /*
                         * R4_119
                         * 午後勤務用.残業枠
                         */
                        cells.get("AZ" + ((startIndex + 1) + i)).setValue(otFrameOpt.isPresent() ? otFrameOpt.get().getTransferFrName() : "");
                        
                        /*
                         * R4_120
                         * 午後勤務用.早出
                         */
                        boolean isEarlyOTUse = fixHalfDayWorkAfternoonOpt.get().getWorkTimezone().getLstOTTimezone().get(i).isEarlyOTUse();
                        cells.get("BA" + ((startIndex + 1) + i)).setValue(isEarlyOTUse ?  "○" : "-");
                        
                        Integer legalOTframeNo = fixHalfDayWorkAfternoonOpt.get().getWorkTimezone().getLstOTTimezone().get(i).getLegalOTframeNo();
                        Optional<WorkdayoffFrameFindDto> legalOTframeOpt = otFrameFind.stream().filter(x -> x.getWorkdayoffFrNo() == legalOTframeNo).findFirst();
                        
                        if (legalOTSetting.equals(LegalOTSetting.LEGAL_INTERNAL_TIME.value)) {
                            /*
                             * R4_121
                             * 午後勤務用.法定内残業枠
                             */
                            cells.get("BB" + ((startIndex + 1) + i)).setValue(legalOTframeOpt.isPresent() ? legalOTframeOpt.get().getWorkdayoffFrName() : "");
                            
                            /*
                             * R4_122
                             * 午後勤務用.積残順序
                             */
                            Integer settlementOrder = fixHalfDayWorkAfternoonOpt.get().getWorkTimezone().getLstOTTimezone().get(i).getSettlementOrder();
                            cells.get("BC" + ((startIndex + 1) + i)).setValue(settlementOrder != null ? getSetlementEnum(settlementOrder) : "");
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
                    cells.get("AF" + ((startIndex + 1) + i)).setValue(getInDayTimeWithFormat(lstOTTimezone.get(i).getTimezone().getStart()));
                    
                    /*
                     * R4_100
                     * 1日勤務用.終了時間
                     */
                    cells.get("AG" + ((startIndex + 1) + i)).setValue(getInDayTimeWithFormat(lstOTTimezone.get(i).getTimezone().getEnd()));
                    
                    /*
                     * R4_101
                     * 1日勤務用.丸め
                     */
                    cells.get("AH" + ((startIndex + 1) + i))
                    .setValue(Unit.valueOf(lstOTTimezone.get(i).getTimezone().getRounding().getRoundingTime()).nameId);
                    
                    /*
                     * R4_102
                     * 1日勤務用.端数
                     */
                    cells.get("AI" + ((startIndex + 1) + i))
                    .setValue(getRoundingEnum(lstOTTimezone.get(i).getTimezone().getRounding().getRounding()));
                    
                    Integer otFrameNo = fixHalfDayWorkOneDayOpt.get().getWorkTimezone().getLstOTTimezone().get(i).getOtFrameNo();
                    Optional<WorkdayoffFrameFindDto> otFrameOpt = otFrameFind.stream().filter(x -> x.getWorkdayoffFrNo() == otFrameNo).findFirst();
                    
                    /*
                     * R4_103
                     * 1日勤務用.残業枠
                     */
                    cells.get("AJ" + ((startIndex + 1) + i)).setValue(otFrameOpt.isPresent() ? otFrameOpt.get().getTransferFrName() : "");
                    
                    /*
                     * R4_104
                     * 1日勤務用.早出
                     */
                    boolean isEarlyOTUse = fixHalfDayWorkOneDayOpt.get().getWorkTimezone().getLstOTTimezone().get(i).isEarlyOTUse();
                    cells.get("AK" + ((startIndex + 1) + i)).setValue(isEarlyOTUse ?  "○" : "-");
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
        
        List<RoundingSetDto> roundingSets = data.getFixedWorkSetting().getCommonSetting().getStampSet().getRoundingTime().getRoundingSets();
        Optional<RoundingSetDto> roundingAttendanceSetOpt = roundingSets.stream().filter(x -> x.getSection() == Superiority.ATTENDANCE.value).findFirst();
        Optional<RoundingSetDto> roundingOfficeSetOpt = roundingSets.stream().filter(x -> x.getSection() == Superiority.OFFICE_WORK.value).findFirst();
        Optional<RoundingSetDto> roundingGoOutSetOpt = roundingSets.stream().filter(x -> x.getSection() == Superiority.GO_OUT.value).findFirst();
        Optional<RoundingSetDto> roundingTurnBackSetOpt = roundingSets.stream().filter(x -> x.getSection() == Superiority.TURN_BACK.value).findFirst();

        if (roundingAttendanceSetOpt.isPresent()) {
            /*
             * R4_125
             * 打刻丸め.出勤
             */
            Integer roundingTimeUnit = roundingAttendanceSetOpt.get().getRoundingSet().getRoundingTimeUnit();
            cells.get("BF" + (startIndex + 1)).setValue(getRoundingTimeUnitEnum(roundingTimeUnit));
            
            /*
             * R4_126
             * 打刻丸め.出勤前後設定
             */
            Integer fontRearSection = roundingAttendanceSetOpt.get().getRoundingSet().getFontRearSection();
            cells.get("BG" + (startIndex + 1)).setValue(FontRearSection.valueOf(fontRearSection).description);
        }
        
        if (roundingOfficeSetOpt.isPresent()) {
            /*
             * R4_127
             * 打刻丸め.退勤
             */
            Integer roundingTimeUnit = roundingOfficeSetOpt.get().getRoundingSet().getRoundingTimeUnit();
            cells.get("BH" + (startIndex + 1)).setValue(getRoundingTimeUnitEnum(roundingTimeUnit));
            
            /*
             * R4_128
             * 打刻丸め.退勤前後設定
             */
            Integer fontRearSection = roundingOfficeSetOpt.get().getRoundingSet().getFontRearSection();
            cells.get("BI" + (startIndex + 1)).setValue(FontRearSection.valueOf(fontRearSection).description);
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
                Integer roundingTimeUnit = roundingGoOutSetOpt.get().getRoundingSet().getRoundingTimeUnit();
                cells.get("BV" + (startIndex + 1)).setValue(getRoundingTimeUnitEnum(roundingTimeUnit));
                
                /*
                 * R4_142
                 * 打刻丸め.外出前後ずらし
                 */
                Integer fontRearSection = roundingGoOutSetOpt.get().getRoundingSet().getFontRearSection();
                cells.get("BW" + (startIndex + 1)).setValue(fontRearSection == 0 ? "前にずらす" : "後ろにずらす");
            }
            
            if (roundingTurnBackSetOpt.isPresent()) {
                /*
                 * R4_143
                 * 打刻丸め.戻り
                 */
                Integer roundingTimeUnit = roundingTurnBackSetOpt.get().getRoundingSet().getRoundingTimeUnit();
                cells.get("BX" + (startIndex + 1)).setValue(getRoundingTimeUnitEnum(roundingTimeUnit));
                
                /*
                 * R4_144
                 * 打刻丸め.戻り前後ずらし
                 */
                Integer fontRearSection = roundingTurnBackSetOpt.get().getRoundingSet().getFontRearSection();
                cells.get("BY" + (startIndex + 1)).setValue(fontRearSection == 0 ? "前にずらす" : "後ろにずらす");
            }
            
            /*
             * R4_264
             * 計算設定.出勤を1分後から計算する
             */
            Integer attendanceMinuteLaterCalculate = data.getFixedWorkSetting().getCommonSetting().getStampSet().getRoundingTime().getAttendanceMinuteLaterCalculate();
            cells.get("BZ" + (startIndex + 1)).setValue((attendanceMinuteLaterCalculate == 0) ? "-" : "○");
            
            /*
             * R4_265
             * 計算設定.退勤を1分前まで計算する
             */
            Integer leaveWorkMinuteAgoCalculate = data.getFixedWorkSetting().getCommonSetting().getStampSet().getRoundingTime().getLeaveWorkMinuteAgoCalculate();
            cells.get("CA" + (startIndex + 1)).setValue(leaveWorkMinuteAgoCalculate == 0 ? "-" : "○");
        }
        
        // 5        タブグ:                休憩時間帯
        
        Boolean useHalfDayShiftBreakTime = data.getFixedWorkSetting().getUseHalfDayShift().isBreakTime();
        if (displayMode.equals(DisplayMode.DETAIL.value)) {
            /*
             * R4_262
             * 半日勤務を設定する
             */
            cells.get("CB" + (startIndex + 1)).setValue(useHalfDayShiftBreakTime ? "する" : "しない");
        }
        
        List<FixHalfDayWorkTimezoneDto> lstHalfDayWorkTimezone = data.getFixedWorkSetting().getLstHalfDayWorkTimezone();
        Optional<FixHalfDayWorkTimezoneDto> halfDayWorkTimezoneOneDayOpt = lstHalfDayWorkTimezone.stream()
                .filter(x -> x.getDayAtr().equals(AmPmAtr.ONE_DAY.value)).findFirst();
        Optional<FixHalfDayWorkTimezoneDto> halfDayWorkTimezoneMorningOpt = lstHalfDayWorkTimezone.stream()
                .filter(x -> x.getDayAtr().equals(AmPmAtr.AM.value)).findFirst();
        Optional<FixHalfDayWorkTimezoneDto> halfDayWorkTimezoneAfternoonOpt = lstHalfDayWorkTimezone.stream()
                .filter(x -> x.getDayAtr().equals(AmPmAtr.PM.value)).findFirst();
        
        if (halfDayWorkTimezoneOneDayOpt.isPresent()) {
            List<DeductionTimeDto> deductionTimes = halfDayWorkTimezoneOneDayOpt.get().getRestTimezone().getTimezones();
            for (int i = 0; i < deductionTimes.size(); i++) {
                /*
                 * R4_145
                 * 1日勤務用.開始時間
                 */
                cells.get("CC" + ((startIndex + 1) + i)).setValue(getInDayTimeWithFormat(deductionTimes.get(i).getStart()));
                
                /*
                 * R4_146
                 * 1日勤務用.終了時間
                 */
                cells.get("CD" + ((startIndex + 1) + i)).setValue(getInDayTimeWithFormat(deductionTimes.get(i).getEnd()));
            }
        }
        
        if (displayMode.equals(DisplayMode.DETAIL.value)) {
            if (useHalfDayShiftBreakTime) {
                if (halfDayWorkTimezoneMorningOpt.isPresent()) {
                    List<DeductionTimeDto> deductionTimes = halfDayWorkTimezoneMorningOpt.get().getRestTimezone().getTimezones();
                    for (int i = 0; i < deductionTimes.size(); i++) {
                        /*
                         * R4_147
                         * 午前勤務用.開始時間
                         */
                        cells.get("CE" + ((startIndex + 1) + i)).setValue(getInDayTimeWithFormat(deductionTimes.get(i).getStart()));
                        
                        /*
                         * R4_148
                         * 午前勤務用.終了時間
                         */
                        cells.get("CF" + ((startIndex + 1) + i)).setValue(getInDayTimeWithFormat(deductionTimes.get(i).getEnd()));
                    }
                }
                
                if (halfDayWorkTimezoneAfternoonOpt.isPresent()) {
                    List<DeductionTimeDto> deductionTimes = halfDayWorkTimezoneAfternoonOpt.get().getRestTimezone().getTimezones();
                    for (int i = 0; i < deductionTimes.size(); i++) {
                        /*
                         * R4_149
                         * 午後勤務用.開始時間
                         */
                        cells.get("CG" + ((startIndex + 1) + i)).setValue(getInDayTimeWithFormat(deductionTimes.get(i).getStart()));
                        
                        /*
                         * R4_150
                         * 午後勤務用.終了時間
                         */
                        cells.get("CH" + ((startIndex + 1) + i)).setValue(getInDayTimeWithFormat(deductionTimes.get(i).getEnd()));
                    }
                }
            }
            
            /*
             * R4_152
             * 休憩計算設定.休憩中に退勤した場合の休憩時間の計算方法
             */
            Integer calculateMethod = data.getFixedWorkSetting().getCommonRestSet().getCalculateMethod();
            cells.get("CI" + (startIndex + 1)).setValue(getCalculatedMethodAtr(calculateMethod));
        }
        
        // 6        タブグ:                休出時間帯
        
        List<HDWorkTimeSheetSettingDto> lstWorkTimezone = data.getFixedWorkSetting().getOffdayWorkTimezone().getLstWorkTimezone();
        Collections.sort(lstWorkTimezone, new Comparator<HDWorkTimeSheetSettingDto>() {
            public int compare(HDWorkTimeSheetSettingDto o1, HDWorkTimeSheetSettingDto o2) {
                return o1.getWorkTimeNo().compareTo(o2.getWorkTimeNo());
            };
        });
        
        for (int i = 0; i < lstWorkTimezone.size(); i++) {
            final int index = i;
            
            /*
             * R4_153
             * 休出時間帯.開始時間
             */
            cells.get("CJ" + (startIndex + lstWorkTimezone.get(i).getWorkTimeNo()))
                .setValue(getInDayTimeWithFormat(lstWorkTimezone.get(i).getTimezone().getStart()));
            
            /*
             * R4_154
             * 休出時間帯.終了時間
             */
            cells.get("CK" + (startIndex + lstWorkTimezone.get(i).getWorkTimeNo()))
            .setValue(getInDayTimeWithFormat(lstWorkTimezone.get(i).getTimezone().getEnd()));
            
            Optional<WorkdayoffFrameFindDto> workDayOffFrameInLegalBreak = otFrameFind.stream()
                .filter(x -> BigDecimal.valueOf(x.getWorkdayoffFrNo()) == lstWorkTimezone.get(index).getInLegalBreakFrameNo())
                .findFirst();
            Optional<WorkdayoffFrameFindDto> workDayOffFrameOutLegalBreak = otFrameFind.stream()
                    .filter(x -> BigDecimal.valueOf(x.getWorkdayoffFrNo()) == lstWorkTimezone.get(index).getOutLegalBreakFrameNo())
                    .findFirst();
            Optional<WorkdayoffFrameFindDto> workDayOffFrameoutLegalPubHD = otFrameFind.stream()
                    .filter(x -> BigDecimal.valueOf(x.getWorkdayoffFrNo()) == lstWorkTimezone.get(index).getOutLegalPubHDFrameNo())
                    .findFirst();
            
            /*
             * R4_155
             * 休出時間帯.法定内休出枠
             */
            if (workDayOffFrameInLegalBreak.isPresent()) {
                cells.get("CL" + (startIndex + lstWorkTimezone.get(i).getWorkTimeNo()))
                    .setValue(workDayOffFrameInLegalBreak.get().getWorkdayoffFrName());
            }
            
            /*
             * R4_156
             * 休出時間帯.法定外休出枠
             */
            if (workDayOffFrameOutLegalBreak.isPresent()) {
                cells.get("CM" + (startIndex + lstWorkTimezone.get(i).getWorkTimeNo()))
                    .setValue(workDayOffFrameOutLegalBreak.get().getWorkdayoffFrName());
            }
            
            /*
             * R4_157
             * 休出時間帯.法定外休出枠（祝日）
             */
            if (workDayOffFrameoutLegalPubHD.isPresent()) {
                cells.get("CN" + (startIndex + lstWorkTimezone.get(i).getWorkTimeNo()))
                    .setValue(workDayOffFrameoutLegalPubHD.get().getWorkdayoffFrName());
            }
            
            /*
             * R4_158
             * 休出時間帯.丸め
             */
            cells.get("CO" + (startIndex + lstWorkTimezone.get(i).getWorkTimeNo()))
            .setValue(getRoundingTimeUnitEnum(lstWorkTimezone.get(i).getTimezone().getRounding().getRoundingTime()));
            
            /*
             * R4_159
             * 休出時間帯.端数処理
             */
            cells.get("CP" + (startIndex + lstWorkTimezone.get(i).getWorkTimeNo()))
            .setValue(getRoundingEnum(lstWorkTimezone.get(i).getTimezone().getRounding().getRounding()));
        }
        
        // 7        タブグ:                休出休憩
        
        List<DeductionTimeDto> timeZones = data.getFixedWorkSetting().getOffdayWorkTimezone().getRestTimezone().getTimezones();
        
        for (int i = 0; i < timeZones.size(); i++) {
            /*
             * R4_160
             * 休出休憩.開始時間
             */
            cells.get("CQ" + ((startIndex + 1) + i)).setValue(getInDayTimeWithFormat(timeZones.get(i).getStart()));
            
            /*
             * R4_161
             * 休出休憩.終了時間
             */
            cells.get("CR" + ((startIndex + 1) + i)).setValue(getInDayTimeWithFormat(timeZones.get(i).getEnd()));
        }
        
        // 8        タブグ:                外出
        
        if (displayMode.equals(DisplayMode.DETAIL.value)) {
            /*
             * R4_162
             * 外出丸め設定.同じ枠内での丸め設定
             */
            Integer setSameFrameRounding = data.getFixedWorkSetting().getCommonSetting().getGoOutSet().getTotalRoundingSet().getSetSameFrameRounding();
            cells.get("CS" + (startIndex + 1)).setValue(getFrameRoundingAtr(setSameFrameRounding));
            
            /*
             * R4_163
             * 外出丸め設定.枠を跨る場合の丸め設定
             */
            Integer frameStraddRoundingSet = data.getFixedWorkSetting().getCommonSetting().getGoOutSet().getTotalRoundingSet().getFrameStraddRoundingSet();
            cells.get("CT" + (startIndex + 1)).setValue(getFrameRoundingAtr(frameStraddRoundingSet));
            
            /*
             * R4_164
             * 私用・組合外出時間.就業時間帯
             */
            Integer roundingMethodAppro = data.getFixedWorkSetting().getCommonSetting().getGoOutSet()
                    .getDiffTimezoneSetting().getWorkTimezone().getPrivateUnionGoOut().getApproTimeRoundingSetting().getRoundingMethod();
            cells.get("CU" + (startIndex + 1)).setValue(getApproTimeRoundingAtr(roundingMethodAppro));
            
            /*
             * R4_165
             * 私用・組合外出時間.丸め設定
             */
            Integer unitAppro = data.getFixedWorkSetting().getCommonSetting().getGoOutSet()
                    .getDiffTimezoneSetting().getWorkTimezone().getPrivateUnionGoOut()
                    .getApproTimeRoundingSetting().getRoundingSetting().getRoundingTime();
            cells.get("CV" + (startIndex + 1)).setValue(getRoundingTimeUnitEnum(unitAppro));
            
            /*
             * R4_166
             * 私用・組合外出時間.丸め設定端数
             */
            Integer roundingAppro = data.getFixedWorkSetting().getCommonSetting().getGoOutSet()
                    .getDiffTimezoneSetting().getWorkTimezone().getPrivateUnionGoOut()
                    .getApproTimeRoundingSetting().getRoundingSetting().getRounding();
            cells.get("CW" + (startIndex + 1)).setValue(getRoundingEnum(roundingAppro));
            
            /*
             * R4_167
             * 私用・組合外出時間.残業時間帯
             */
            Integer roundingMethodApproOt = data.getFixedWorkSetting().getCommonSetting().getGoOutSet()
                    .getDiffTimezoneSetting().getOttimezone().getPrivateUnionGoOut().getApproTimeRoundingSetting().getRoundingMethod();
            cells.get("CX" + (startIndex + 1)).setValue(getApproTimeRoundingAtr(roundingMethodApproOt));
            /*
             * R4_168
             * 私用・組合外出時間.丸め設定
             */
            Integer unitApproOt = data.getFixedWorkSetting().getCommonSetting().getGoOutSet()
                    .getDiffTimezoneSetting().getOttimezone().getPrivateUnionGoOut()
                    .getApproTimeRoundingSetting().getRoundingSetting().getRoundingTime();
            cells.get("CY" + (startIndex + 1)).setValue(getRoundingTimeUnitEnum(unitApproOt));
            
            /*
             * R4_169
             * 私用・組合外出時間.丸め設定端数
             */
            Integer roundingApproOt = data.getFixedWorkSetting().getCommonSetting().getGoOutSet()
                    .getDiffTimezoneSetting().getOttimezone().getPrivateUnionGoOut()
                    .getApproTimeRoundingSetting().getRoundingSetting().getRounding();
            cells.get("CZ" + (startIndex + 1)).setValue(getRoundingEnum(roundingApproOt));
            
            /*
             * R4_170
             * 私用・組合外出時間.休出時間帯
             */
            Integer roundingMethodApproHol = data.getFixedWorkSetting().getCommonSetting().getGoOutSet()
                    .getDiffTimezoneSetting().getPubHolWorkTimezone().getPrivateUnionGoOut().getApproTimeRoundingSetting().getRoundingMethod();
            cells.get("DA" + (startIndex + 1)).setValue(getApproTimeRoundingAtr(roundingMethodApproHol));
            
            /*
             * R4_171
             * 私用・組合外出時間.丸め設定
             */
            Integer unitApproHol = data.getFixedWorkSetting().getCommonSetting().getGoOutSet()
                    .getDiffTimezoneSetting().getPubHolWorkTimezone().getPrivateUnionGoOut()
                    .getApproTimeRoundingSetting().getRoundingSetting().getRoundingTime();
            cells.get("DB" + (startIndex + 1)).setValue(getRoundingTimeUnitEnum(unitApproHol));
            
            /*
             * R4_172
             * 私用・組合外出時間.丸め設定端数
             */
            Integer roundingApproHol = data.getFixedWorkSetting().getCommonSetting().getGoOutSet()
                    .getDiffTimezoneSetting().getPubHolWorkTimezone().getPrivateUnionGoOut()
                    .getApproTimeRoundingSetting().getRoundingSetting().getRounding();
            cells.get("DC" + (startIndex + 1)).setValue(getRoundingEnum(roundingApproHol));
            
            /*
             * R4_173
             * 私用・組合外出控除時間.就業時間帯
             */
            Integer roundingMethodDeduct = data.getFixedWorkSetting().getCommonSetting().getGoOutSet().getDiffTimezoneSetting()
                    .getWorkTimezone().getPrivateUnionGoOut().getDeductTimeRoundingSetting().getRoundingMethod();
            cells.get("DD" + (startIndex + 1)).setValue(getApproTimeRoundingAtr(roundingMethodDeduct));
            
            /*
             * R4_174
             * 私用・組合外出控除時間.丸め設定
             */
            Integer unitDeduct = data.getFixedWorkSetting().getCommonSetting().getGoOutSet()
                    .getDiffTimezoneSetting().getWorkTimezone().getPrivateUnionGoOut()
                    .getDeductTimeRoundingSetting().getRoundingSetting().getRoundingTime();
            cells.get("DE" + (startIndex + 1)).setValue(getRoundingTimeUnitEnum(unitDeduct));
            
            /*
             * R4_175
             * 私用・組合外出控除時間.丸め設定端数
             */
            Integer roundingDeduct = data.getFixedWorkSetting().getCommonSetting().getGoOutSet()
                    .getDiffTimezoneSetting().getWorkTimezone().getPrivateUnionGoOut()
                    .getDeductTimeRoundingSetting().getRoundingSetting().getRounding();
            cells.get("DF" + (startIndex + 1)).setValue(getRoundingEnum(roundingDeduct));
            
            /*
             * R4_176
             * 私用・組合外出控除時間.残業時間帯
             */
            Integer roundingMethodDeductOt = data.getFixedWorkSetting().getCommonSetting().getGoOutSet()
                    .getDiffTimezoneSetting().getOttimezone().getPrivateUnionGoOut()
                    .getDeductTimeRoundingSetting().getRoundingMethod();
            cells.get("DG" + (startIndex + 1)).setValue(getApproTimeRoundingAtr(roundingMethodDeductOt));
            
            /*
             * R4_177
             * 私用・組合外出控除時間.丸め設定
             */
            Integer unitDeductOt = data.getFixedWorkSetting().getCommonSetting().getGoOutSet()
                    .getDiffTimezoneSetting().getOttimezone().getPrivateUnionGoOut()
                    .getDeductTimeRoundingSetting().getRoundingSetting().getRoundingTime();
            cells.get("DH" + (startIndex + 1)).setValue(getRoundingTimeUnitEnum(unitDeductOt));
            
            /*
             * R4_178
             * 私用・組合外出控除時間.丸め設定端数
             */
            Integer roundingDeductOt = data.getFixedWorkSetting().getCommonSetting().getGoOutSet()
                    .getDiffTimezoneSetting().getOttimezone().getPrivateUnionGoOut()
                    .getDeductTimeRoundingSetting().getRoundingSetting().getRounding();
            cells.get("DI" + (startIndex + 1)).setValue(getRoundingEnum(roundingDeductOt));
            
            /*
             * R4_179
             * 私用・組合外出控除時間.休出時間帯
             */
            Integer roundingMethodDeductHol = data.getFixedWorkSetting().getCommonSetting().getGoOutSet()
                    .getDiffTimezoneSetting().getPubHolWorkTimezone().getPrivateUnionGoOut()
                    .getDeductTimeRoundingSetting().getRoundingMethod();
            cells.get("DJ" + (startIndex + 1)).setValue(getApproTimeRoundingAtr(roundingMethodDeductHol));
            
            /*
             * R4_180
             * 私用・組合外出控除時間.丸め設定
             */
            Integer unitDeductHol = data.getFixedWorkSetting().getCommonSetting().getGoOutSet()
                    .getDiffTimezoneSetting().getPubHolWorkTimezone().getPrivateUnionGoOut()
                    .getDeductTimeRoundingSetting().getRoundingSetting().getRoundingTime();
            cells.get("DK" + (startIndex + 1)).setValue(getRoundingTimeUnitEnum(unitDeductHol));
            
            /*
             * R4_181
             * 私用・組合外出控除時間.丸め設定端数
             */
            Integer roundingDeductHol = data.getFixedWorkSetting().getCommonSetting().getGoOutSet()
                    .getDiffTimezoneSetting().getPubHolWorkTimezone().getPrivateUnionGoOut()
                    .getDeductTimeRoundingSetting().getRoundingSetting().getRounding();
            cells.get("DL" + (startIndex + 1)).setValue(getRoundingEnum(roundingDeductHol));
            
            /*
             * R4_182
             * 公用・有償外出時間.就業時間帯
             */
            Integer roundingMethodOfficalAppro = data.getFixedWorkSetting().getCommonSetting().getGoOutSet()
                    .getDiffTimezoneSetting().getWorkTimezone().getOfficalUseCompenGoOut()
                    .getApproTimeRoundingSetting().getRoundingMethod();
            cells.get("DM" + (startIndex + 1)).setValue(getApproTimeRoundingAtr(roundingMethodOfficalAppro));
            
            /*
             * R4_183
             * 公用・有償外出時間.丸め設定
             */
            Integer unitOfficalAppro = data.getFixedWorkSetting().getCommonSetting().getGoOutSet()
                    .getDiffTimezoneSetting().getWorkTimezone().getOfficalUseCompenGoOut()
                    .getApproTimeRoundingSetting().getRoundingSetting().getRoundingTime();
            cells.get("DN" + (startIndex + 1)).setValue(getRoundingTimeUnitEnum(unitOfficalAppro));
            
            /*
             * R4_184
             * 公用・有償外出時間.丸め設定端数
             */
            Integer roundingOfficalAppro = data.getFixedWorkSetting().getCommonSetting().getGoOutSet()
                    .getDiffTimezoneSetting().getWorkTimezone().getOfficalUseCompenGoOut()
                    .getApproTimeRoundingSetting().getRoundingSetting().getRounding();
            cells.get("DO" + (startIndex + 1)).setValue(getRoundingEnum(roundingOfficalAppro));
            
            /*
             * R4_185
             * 公用・有償外出時間.残業時間帯
             */
            Integer roundingMethodOfficlaOt = data.getFixedWorkSetting().getCommonSetting().getGoOutSet()
                    .getDiffTimezoneSetting().getOttimezone().getOfficalUseCompenGoOut()
                    .getApproTimeRoundingSetting().getRoundingMethod();
            cells.get("DP" + (startIndex + 1)).setValue(getApproTimeRoundingAtr(roundingMethodOfficlaOt));
            
            /*
             * R4_186
             * 公用・有償外出時間.丸め設定
             */
            Integer unitOfficalOt = data.getFixedWorkSetting().getCommonSetting().getGoOutSet()
                    .getDiffTimezoneSetting().getOttimezone().getOfficalUseCompenGoOut()
                    .getApproTimeRoundingSetting().getRoundingSetting().getRoundingTime();
            cells.get("DQ" + (startIndex + 1)).setValue(getRoundingTimeUnitEnum(unitOfficalOt));
            
            /*
             * R4_187
             * 公用・有償外出時間.丸め設定端数
             */
            Integer roundingOfficalOt = data.getFixedWorkSetting().getCommonSetting().getGoOutSet()
                    .getDiffTimezoneSetting().getOttimezone().getOfficalUseCompenGoOut()
                    .getApproTimeRoundingSetting().getRoundingSetting().getRounding();
            cells.get("DR" + (startIndex + 1)).setValue(getRoundingEnum(roundingOfficalOt));
            
            /*
             * R4_188
             * 公用・有償外出時間.休出時間帯
             */
            Integer roundingMethodOfficalHol = data.getFixedWorkSetting().getCommonSetting().getGoOutSet()
                    .getDiffTimezoneSetting().getPubHolWorkTimezone().getOfficalUseCompenGoOut()
                    .getApproTimeRoundingSetting().getRoundingMethod();
            cells.get("DS" + (startIndex + 1)).setValue(getApproTimeRoundingAtr(roundingMethodOfficalHol));
            
            /*
             * R4_189
             * 公用・有償外出時間.丸め設定
             */
            Integer unitOfficalHol = data.getFixedWorkSetting().getCommonSetting().getGoOutSet()
                    .getDiffTimezoneSetting().getPubHolWorkTimezone().getOfficalUseCompenGoOut()
                    .getApproTimeRoundingSetting().getRoundingSetting().getRoundingTime();
            cells.get("DT" + (startIndex + 1)).setValue(getRoundingTimeUnitEnum(unitOfficalHol));
            
            /*
             * R4_190
             * 公用・有償外出時間.丸め設定端数
             */
            Integer roundingOfficalHol = data.getFixedWorkSetting().getCommonSetting().getGoOutSet()
                    .getDiffTimezoneSetting().getPubHolWorkTimezone().getOfficalUseCompenGoOut()
                    .getApproTimeRoundingSetting().getRoundingSetting().getRounding();
            cells.get("DU" + (startIndex + 1)).setValue(getRoundingEnum(roundingOfficalHol));
        }
        
        // 9        タブグ:                遅刻早退
        
        List<OtherEmTimezoneLateEarlySetDto> otherClassSets = data.getFixedWorkSetting().getCommonSetting().getLateEarlySet().getOtherClassSets();
        Optional<OtherEmTimezoneLateEarlySetDto> otherLate = otherClassSets.stream()
                .filter(x -> x.getLateEarlyAtr().equals(LateEarlyAtr.LATE.value)).findFirst();
        Optional<OtherEmTimezoneLateEarlySetDto> otherEarly = otherClassSets.stream()
                .filter(x -> x.getLateEarlyAtr().equals(LateEarlyAtr.EARLY.value)).findFirst();
        
        if (otherLate.isPresent()) {
            /*
             * R4_191
             * 遅刻早退時間丸め.遅刻丸め
             */
            Integer unitRecordLate = otherLate.get().getRecordTimeRoundingSet().getRoundingTime();
            cells.get("DV" + (startIndex + 1)).setValue(getRoundingTimeUnitEnum(unitRecordLate));
            
            /*
             * R4_192
             * 遅刻早退時間丸め.遅刻端数
             */
            Integer roundingRecordLate = otherLate.get().getRecordTimeRoundingSet().getRounding();
            cells.get("DW" + (startIndex + 1)).setValue(getRoundingEnum(roundingRecordLate));
        }
        
        if (otherEarly.isPresent()) {
            /*
             * R4_193
             * 遅刻早退時間丸め.早退丸め
             */
            Integer unitRecordEarly = otherEarly.get().getRecordTimeRoundingSet().getRoundingTime();
            cells.get("DX" + (startIndex + 1)).setValue(getRoundingTimeUnitEnum(unitRecordEarly));
            
            /*
             * R4_194
             * 遅刻早退時間丸め.早退端数
             */
            Integer roundingRecordEarly = otherEarly.get().getRecordTimeRoundingSet().getRounding();
            cells.get("DY" + (startIndex + 1)).setValue(getRoundingEnum(roundingRecordEarly));
        }
        
        if (otherLate.isPresent()) {
            /*
             * R4_195
             * 遅刻早退控除時間丸め.遅刻丸め
             */
            Integer unitDelLate = otherLate.get().getDelTimeRoundingSet().getRoundingTime();
            cells.get("DZ" + (startIndex + 1)).setValue(getRoundingTimeUnitEnum(unitDelLate));
            
            /*
             * R4_196
             * 遅刻早退控除時間丸め.遅刻端数
             */
            Integer roundingDelLate = otherLate.get().getDelTimeRoundingSet().getRounding();
            cells.get("EA" + (startIndex + 1)).setValue(getRoundingEnum(roundingDelLate));
        }
        
        if (otherEarly.isPresent()) {
            /*
             * R4_197
             * 遅刻早退控除時間丸め.早退丸め
             */
            Integer unitDelEarly = otherEarly.get().getDelTimeRoundingSet().getRoundingTime();
            cells.get("EB" + (startIndex + 1)).setValue(getRoundingTimeUnitEnum(unitDelEarly));
            
            /*
             * R4_198
             * 遅刻早退控除時間丸め.早退端数
             */
            Integer roundingDelEarly = otherEarly.get().getDelTimeRoundingSet().getRoundingTime();
            cells.get("EC" + (startIndex + 1)).setValue(getRoundingEnum(roundingDelEarly));
        }
        
        if (displayMode.equals(DisplayMode.DETAIL.value)) {
            /*
             * R4_199
             * 遅刻早退詳細設定.控除時間.遅刻早退時間を就業時間から控除する
             */
            boolean isDelFromEmTime = data.getFixedWorkSetting().getCommonSetting().getLateEarlySet().getCommonSet().isDelFromEmTime();
            cells.get("ED").setValue(isDelFromEmTime ?  "○" : "-");
            
            if (otherLate.isPresent()) {
                /*
                 * R4_202
                 * 遅刻早退詳細設定.猶予時間.遅刻猶予時間
                 */
                Integer graceTime = otherLate.get().getGraceTimeSet().getGraceTime();
                cells.get("EE" + (startIndex + 1)).setValue(getInDayTimeWithFormat(graceTime));
                
                /*
                 * R4_203
                 * 遅刻早退詳細設定.猶予時間.遅刻猶予時間を就業時間に含める
                 */
                boolean includeWorkingHour = otherLate.get().getGraceTimeSet().isIncludeWorkingHour();
                cells.get("EF" + (startIndex + 1)).setValue(includeWorkingHour ? "○" : "-");
            }
            
            if (otherEarly.isPresent()) {
                /*
                 * R4_204
                 * 遅刻早退詳細設定.猶予時間.早退猶予時間
                 */
                Integer graceTime = otherEarly.get().getGraceTimeSet().getGraceTime();
                cells.get("EG" + (startIndex + 1)).setValue(getInDayTimeWithFormat(graceTime));
                
                /*
                 * R4_205
                 * 遅刻早退詳細設定.猶予時間.早退猶予時間を就業時間に含める
                 */
                boolean includeWorkingHour = otherEarly.get().getGraceTimeSet().isIncludeWorkingHour();
                cells.get("EH" + (startIndex + 1)).setValue(includeWorkingHour ? "○" : "-");
            }
        }
        
        // 10       タブグ:                加給
        
        /*
         * R4_206
         * コード
         */
        String raisingSalarySetCode = data.getFixedWorkSetting().getCommonSetting().getRaisingSalarySet();
        cells.get("EI" + (startIndex + 1)).setValue(raisingSalarySetCode != null ? raisingSalarySetCode : "");
        
        /*
         * R4_207
         * 名称
         */
        if (raisingSalarySetCode != null) {
            Optional<BonusPaySetting> bonusPaySettingOpt = this.bpSettingRepository
                    .getBonusPaySetting(AppContexts.user().companyId(), new BonusPaySettingCode(raisingSalarySetCode));
            if (bonusPaySettingOpt.isPresent()) {
                String raisingSalaryName = bonusPaySettingOpt.get().getName().v();
                cells.get("EJ" + (startIndex + 1)).setValue(raisingSalaryName);
            }
        }
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
    private static String getInDayTimeWithFormat(Integer time) {
        if (time == null) {
            return "";
        }
        return new TimeWithDayAttr(time).getInDayTimeWithFormat();
    }
    
    /**
     * Get Rounding Text by value
     * 切り捨て = 0
     * 切り上げ = 1
     * @param rounding
     * @return
     */
    private static String getRoundingEnum(Integer rounding) {
        if (rounding == null) {
            return "";
        }
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
    private static String getSetlementEnum(Integer settlementOrder) {
        if (settlementOrder == null) {
            return "";
        }
        
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
    private static String getRoundingTimeUnitEnum(Integer roundingTimeUnit) {
        if (roundingTimeUnit == null) {
            return "";
        }
        
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
    private static String getPrioritySetAtr(Integer priority) {
        if (priority == null) {
            return "";
        }
        
        String[] priorityAtr = {"最初", "最後"};
        for (int i = 0; i <= priorityAtr.length; i++) {
            if (priority == i) {
                return priorityAtr[i];
            }
        }
        
        return "";
    }
    
    /**
     * 固定勤務の休憩設定.休憩時間中に退勤した場合の計算方法
     * @param calculatedMethod
     * @return
     */
    private static String getCalculatedMethodAtr(Integer calculatedMethod) {
        if (calculatedMethod == null) {
            return "";
        }
        
        String[] calculatedMethodAtr = {"休憩を計算しない", "退勤までの休憩時間を計算する(丸めを適用する)", "退勤以降も含め休憩時間を全て計上する"};
        for (int i = 0; i <= calculatedMethodAtr.length; i++) {
            if (calculatedMethod == i) {
                return calculatedMethodAtr[i];
            }
        }
        
        return "";
    }
    
    /**
     * 外出丸め設定.枠を跨る場合の丸め設定
     * @param frameRoundingAtr
     * @return
     */
    private static String getFrameRoundingAtr(Integer frameRoundingAtr) {
        if (frameRoundingAtr == null) {
            return "";
        }
        
        String[] frameRoundings = {"合算した後に丸める", "休憩枠毎に丸める"};
        for (int i = 0; i <= frameRoundings.length; i++) {
            if (frameRoundingAtr == i) {
                return frameRoundings[i];
            }
        }
        
        return "";
    }
    
    /**
     * 私用・組合外出時間.就業時間帯
     * @param approTimeRoundingAtr
     * @return
     */
    private static String getApproTimeRoundingAtr(Integer approTimeRoundingAtr) {
        if (approTimeRoundingAtr == null) {
            return "";
        }
        
        String[] approTimeRoundings = {"時間帯の丸めを逆に適用する", "丸めを設定する"};
        for (int i = 0; i <= approTimeRoundings.length; i++) {
            if (approTimeRoundingAtr == i) {
                return approTimeRoundings[i];
            }
        }
        
        return "";
    }
}
