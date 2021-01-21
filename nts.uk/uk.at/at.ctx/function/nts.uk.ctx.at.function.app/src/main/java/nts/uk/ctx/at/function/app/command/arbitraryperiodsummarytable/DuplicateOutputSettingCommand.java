package nts.uk.ctx.at.function.app.command.arbitraryperiodsummarytable;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DuplicateOutputSettingCommand {
    private int settingCategory;
    private String dupSrcId;
    private String dupCode;
    private String dupName;
}
