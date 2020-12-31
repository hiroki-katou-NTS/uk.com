package nts.uk.ctx.at.function.app.command.workledgeroutputitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DuplicateWorkLedgerSettingCommand {
    private int settingCategory;
    private String dupSrcId;
    private String dupCode;
    private String dupName;
}
