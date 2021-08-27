package nts.uk.file.at.app.export.schedule.personalschedulebydate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.TimeSpanForCalcDto;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TimezoneToUseHourlyHoliday;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimeVacation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;
import nts.uk.screen.at.app.ksu003.start.dto.ChangeableWorkTimeDto;

import java.util.List;
import java.util.Map;

/**
 * 社員勤務予定・実績dto
 */
@AllArgsConstructor
@Getter
public class EmployeeWorkScheduleResultDto {
    //年月日
    private GeneralDate date;

    //社員ID
    private String employeeId;

    // List<休憩時間帯>
    private List<TimeSpanForCalcDto> timeSpanForCalcList;

    // List<実績休憩時間帯>
    @Setter
    private List<BreakTimeSheet> breakTimeSheetList;

    // List<残業時間帯>
//    private List<OverTimeSheet> overTimeSheetList;
    private List<ChangeableWorkTimeDto> overTimeSheetList;

    // List<育児介護短時間帯> : ★時間帯(実装コードなし/使用不可)
    private List<ShortWorkingTimeSheet> shortWorkingTimeList;

    // Map<時間休暇種類, 時間休暇>
    private Map<TimezoneToUseHourlyHoliday, TimeVacation> timeVacationMap;

    //コア開始時刻
    private Integer coreStartTime;

    //コア終了時刻
    private Integer coreEndTime;

    // 休憩時間合計
    private Integer totalBreakTime;

    // 勤務タイプ : 就業時間帯の勤務形態 (WorkTimeForm)
    private Integer workType;

    // 勤務種類コードf
    private String workTypeCode;

    // 勤務種類名称
    private String workTypeName;

    // 実績開始時刻１
    @Setter
    private Integer actualStartTime1;

    // 実績終了時刻１
    @Setter
    private Integer achievementEndTime1;

    // 実績開始時刻２
    @Setter
    private Integer actualStartTime2;

    // 実績終了時刻２
    @Setter
    private Integer achievementEndTime2;

    // 就業時間合計
    private Integer totalWorkingHours;

    // 就業時間帯コード
    private String workTimeCode;

    // 就業時間帯名称
    private String workTimeName;

    // 開始時刻１
    private Integer startTime1;

    // 開始時刻２
    private Integer startTime2;

    // 終了時刻１
    private Integer endTime1;

    // 終了時刻２
    private Integer endTime2;
}
