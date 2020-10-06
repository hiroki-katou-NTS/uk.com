package nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.consecutiveattendance;

import lombok.Getter;

@Getter
public class RegisterConsecutiveAttendanceComDto {
    /**
     * 会社の連続出勤できる上限日数.日数.日数
     **/
    private int maxConsDays;
}
