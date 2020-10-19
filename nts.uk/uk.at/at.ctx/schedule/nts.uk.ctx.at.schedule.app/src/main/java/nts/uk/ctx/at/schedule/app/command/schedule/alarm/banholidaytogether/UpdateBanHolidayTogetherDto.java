package nts.uk.ctx.at.schedule.app.command.schedule.alarm.banholidaytogether;

import lombok.Getter;

import java.util.List;

@Getter
public class UpdateBanHolidayTogetherDto {
    /**
     * 対象組織情報.単位
     */
    private int unit;

    /**
     * 対象組織情報.職場ID
     */
    private String workplaceId;

    /**
     * 対象組織情報.職場グループID
     */
    private String workplaceGroupId;

    /**
     * 同日休日禁止.コード
     */
    private String banHolidayTogetherCode;

    /**
     * 同日休日禁止.名称
     */
    private String banHolidayTogetherName;

    /**
     * 同日出勤下限人数
     */
    private int minNumberOfEmployeeToWork;


    /**
     * 同日の休日取得を禁止する社員
     */
    private List<String> empsCanNotSameHolidays;
}
