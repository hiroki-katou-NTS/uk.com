package nts.uk.ctx.at.aggregation.app.command.form9;

import lombok.Getter;

@Getter
public class DuplicateItemOutputSettingInfoCommand {
    private String originalCode;

    private String destinationCode;

    private String destinationName;

    private boolean isOverwrite;
}
