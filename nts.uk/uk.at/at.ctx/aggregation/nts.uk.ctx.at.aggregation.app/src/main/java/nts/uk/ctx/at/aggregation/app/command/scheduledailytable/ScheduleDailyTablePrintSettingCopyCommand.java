package nts.uk.ctx.at.aggregation.app.command.scheduledailytable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ScheduleDailyTablePrintSettingCopyCommand {
    private String fromCode;
    private String toCode;
    private String toName;
    private boolean overwrite;
}
