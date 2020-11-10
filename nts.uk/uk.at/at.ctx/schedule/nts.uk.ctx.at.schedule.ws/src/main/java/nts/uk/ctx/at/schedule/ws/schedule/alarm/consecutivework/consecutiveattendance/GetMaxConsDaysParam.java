package nts.uk.ctx.at.schedule.ws.schedule.alarm.consecutivework.consecutiveattendance;

import lombok.Getter;

@Getter
public class GetMaxConsDaysParam {
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
}
