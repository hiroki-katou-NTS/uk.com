package nts.uk.ctx.at.schedule.app.command.schedule.alarm.banholidaytogether;

import lombok.Getter;

@Getter
public class DeleteBanHolidayTogetherDto {
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
}
