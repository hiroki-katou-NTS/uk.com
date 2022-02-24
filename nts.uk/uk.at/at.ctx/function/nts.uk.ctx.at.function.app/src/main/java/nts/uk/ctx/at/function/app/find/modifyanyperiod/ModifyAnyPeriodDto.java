package nts.uk.ctx.at.function.app.find.modifyanyperiod;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModifyAnyPeriodDto {
    private String code;

    private String nam;

    private boolean isSetFormatToDefault;
}
