package nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.consecutiveattendance;

import lombok.Getter;

@Getter
public class RegisterConsecutiveAttendanceOrgDto {
    /**
     * 対象組織情報.単位
     **/
    private int unit;

    /**
     * 対象組織情報.職場ID
     **/
    private String workplaceId;

    /**
     * 対象組織情報.職場グループID
     **/
    private String workplaceGroupId;

    /**
     * 組織の連続出勤できる上限日数.日数.日数
     **/
    private int maxConsDays;
}
