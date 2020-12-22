package nts.uk.ctx.at.function.app.command.alarmworkplace.checkcondition;

import lombok.Data;

@Data
public class DeleteAlarmCheckCdtWkpCommand {

    // カテゴリ
    private int category;
    // コード
    private String code;

}
