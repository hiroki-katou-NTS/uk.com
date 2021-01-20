package nts.uk.ctx.at.function.app.command.arbitraryperiodsummarytable;


import lombok.Data;

@Data
public class DuplicateOutputSettingCommand {
    private int settingCategory;
    private String dupSrcId;
    private String dupCode;
    private String dupName;
}
