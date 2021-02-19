package nts.uk.ctx.at.schedule.ws.schedule.alarm.banholidaytogether;

import lombok.Getter;

@Getter
public class GetAllBanHolidayTogether {
    /**
     * 対象組織.単位
     **/
    private int unit;

    /**
     * 対象組織.職場ID
     **/
    private String workplaceId;

    /**
     * 対象組織.職場グループID
     **/
    private String workplaceGroupId;
}
