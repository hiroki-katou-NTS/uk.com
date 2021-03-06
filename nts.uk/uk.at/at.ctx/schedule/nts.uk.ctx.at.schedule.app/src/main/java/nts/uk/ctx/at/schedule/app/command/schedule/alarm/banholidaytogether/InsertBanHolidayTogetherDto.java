package nts.uk.ctx.at.schedule.app.command.schedule.alarm.banholidaytogether;

import lombok.Getter;

import java.util.List;

@Getter
public class InsertBanHolidayTogetherDto {
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
     * 稼働日のみとする
     */
    private Boolean checkDayReference;

    /**
     * 稼働日の参照先
     */
    private Integer selectedWorkDayReference;

    /**
     * 同日出勤下限人数
     */
    private int minNumberOfEmployeeToWork;

    /** 職場ID */
    private String workplaceInfoId;

    /**
     * 分類コード
     * 職場コード
     */
    private String classificationOrWorkplaceCode;

    /**
     * 同日の休日取得を禁止する社員
     */
    private List<String> empsCanNotSameHolidays;
}
