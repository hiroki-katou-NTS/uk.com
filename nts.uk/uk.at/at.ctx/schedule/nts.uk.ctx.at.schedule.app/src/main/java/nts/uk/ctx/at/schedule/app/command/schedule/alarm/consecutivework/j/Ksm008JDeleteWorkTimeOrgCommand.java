package nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.j;

import lombok.Data;

@Data
public class Ksm008JDeleteWorkTimeOrgCommand {
    /** 職場グループ */
    private int workPlaceUnit;

    /** ID */
    private String workPlaceId;

    /** 入力対象（項目コード） */
    private String workPlaceGroup;

    /**
     * コード
     */
    private String code;
}
