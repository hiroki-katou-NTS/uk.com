package nts.uk.ctx.at.function.app.command.outputworkanualsetting;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class DuplicateAnualWorkledgerSettingCommand {

    private int settingCategory;
    private String settingId;
    private String settingCode;
    private String settingName;

}
