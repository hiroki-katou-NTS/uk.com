package nts.uk.file.at.infra.worktime;

import java.util.Optional;

import javax.ejb.Stateless;

import com.aspose.cells.Cells;

import nts.uk.ctx.at.shared.app.find.worktime.dto.WorkTimeSettingInfoDto;
import nts.uk.ctx.at.shared.app.find.worktime.predset.dto.TimezoneDto;
import nts.uk.ctx.at.shared.dom.common.usecls.ApplyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class WorkTimeReportService_New {
    private final int NORMAL = 0;
    private final int FLOW = 1;
    private final int FLEX = 2;
    
    /**
     * 勤務形態 通常
     * @param data
     * @param cells
     * @param startIndex
     */
    public void insertDataOneLineNormal(WorkTimeSettingInfoDto data, Cells cells, int startIndex) {
        Integer displayMode = data.getDisplayMode().displayMode;
        
        this.insertDataPrescribed(data, cells, startIndex, NORMAL);
    }
    
    /**
     * 勤務形態 流動
     * @param data
     * @param cell
     * @param startIndex
     */
    public void insertDataOneLineFlow(WorkTimeSettingInfoDto data, Cells cells, int startIndex) {
        Integer displayMode = data.getDisplayMode().displayMode;
        
        this.insertDataPrescribed(data, cells, startIndex, FLOW);
    }
    
    /**
     * 勤務形態 フレックス
     * @param data
     * @param cell
     * @param startIndex
     */
    public void insertDataOneLineFlex(WorkTimeSettingInfoDto data, Cells cells, int startIndex) {
        Integer displayMode = data.getDisplayMode().displayMode;
        
        this.insertDataPrescribed(data, cells, startIndex, FLEX);
    }
    
    /**
     * タブグ: 所定
     * @param data
     * @param cells
     * @param startIndex
     */
    private void insertDataPrescribed(WorkTimeSettingInfoDto data, Cells cells, int startIndex, int type) {
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
                    
                    /*
                     * R4_76
                     * R5_69
                     * 就業時刻2回目.始業時刻
                     */
                    Integer startTime2 = time2.get().getStart();
                    cells.get(startIndex, columnIndex).setValue(startTime2 != null ? 
                            getInDayTimeWithFormat(startTime2) : "");
                    
                    /*
                     * R4_77
                     * R5_70
                     * 就業時刻2回目.終業時刻
                     */
                    Integer endTime2 = time2.get().getEnd();
                    cells.get(startIndex, columnIndex).setValue(endTime2 != null ? 
                            getInDayTimeWithFormat(endTime2) : "");
                }
                
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
                
                /*
                 * R6_259
                 * コアタイム内の外出時間を就業時間から控除する
                 */
                boolean deductTime = true;
                cells.get(startIndex, columnIndex).setValue(deductTime ? "○" : "-");
            }
            columnIndex += 2;
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
    
    private static String getInDayTimeWithFormat(int time) {
        return new TimeWithDayAttr(time).getInDayTimeWithFormat();
    }
}
