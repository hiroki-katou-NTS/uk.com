package nts.uk.ctx.at.aggregation.app.command.form9;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DuplicateItemOutputSettingInfoCommand {
    private String originalCode;

    private String destinationCode;

    private String destinationName;

    private boolean overwrite;
}
