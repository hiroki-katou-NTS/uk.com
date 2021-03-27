package nts.uk.ctx.at.function.app.query.outputworkstatustable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class WorkStatusOutputDto {
    private String settingId;
    private String settingDisplayCode;
    private String settingName;
}
