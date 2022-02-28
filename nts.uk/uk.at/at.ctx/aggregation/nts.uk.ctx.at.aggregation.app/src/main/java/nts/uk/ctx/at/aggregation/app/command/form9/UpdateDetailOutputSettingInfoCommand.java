package nts.uk.ctx.at.aggregation.app.command.form9;

import lombok.Getter;
import nts.uk.ctx.at.aggregation.dom.form9.Form9DetailOutputSetting;
import nts.uk.ctx.at.aggregation.dom.form9.Form9TimeRoundingSetting;
import nts.uk.ctx.at.aggregation.dom.form9.RoundingUnit;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;

@Getter
public class UpdateDetailOutputSettingInfoCommand {
    private int roundingUnit;

    private int roundingMode;

    private boolean attrBlankIfZero;

    public Form9DetailOutputSetting toDomain() {
        return new Form9DetailOutputSetting(
                new Form9TimeRoundingSetting(
                        RoundingUnit.of(roundingUnit),
                        Rounding.valueOf(roundingMode)
                ),
                attrBlankIfZero
        );
    }
}
