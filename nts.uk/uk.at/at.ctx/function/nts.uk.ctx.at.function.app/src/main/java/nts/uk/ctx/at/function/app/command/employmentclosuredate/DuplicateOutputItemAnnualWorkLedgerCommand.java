package nts.uk.ctx.at.function.app.command.employmentclosuredate;

import lombok.Value;

@Value
public class DuplicateOutputItemAnnualWorkLedgerCommand {

    // 設定区分
    private boolean settingAtr;

    // 複製元の設定ID
    private String replicationSourceSettingId;

    // 複製先_コード
    private String replicationDestinationCode;

    // 複製先_名称
    private String replicationDestinationName;

}
