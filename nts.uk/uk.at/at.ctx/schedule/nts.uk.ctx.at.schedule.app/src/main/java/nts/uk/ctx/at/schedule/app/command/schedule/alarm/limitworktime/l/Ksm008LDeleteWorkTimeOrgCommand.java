package nts.uk.ctx.at.schedule.app.command.schedule.alarm.limitworktime.l;

import lombok.Data;

@Data
public class Ksm008LDeleteWorkTimeOrgCommand {
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
