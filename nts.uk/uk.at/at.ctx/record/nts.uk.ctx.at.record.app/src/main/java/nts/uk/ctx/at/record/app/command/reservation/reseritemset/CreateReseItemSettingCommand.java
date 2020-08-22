package nts.uk.ctx.at.record.app.command.reservation.reseritemset;

import lombok.Data;

@Data
public class CreateReseItemSettingCommand {

    private String histId;

    // 枠番
    private int frameNo;

    // 弁当名
    private String benToName;

    // 勤務場所コード
    private String workLocationCode;

    // 金額１
    private int amount1;

    // 金額２
    private Integer amount2;

    // 単位
    private String Unit;

    // 締め時刻１で予約可能
    private boolean canBookClosesingTime1;

    // 締め時刻１で予約可能
    private boolean canBookClosesingTime2;
}
