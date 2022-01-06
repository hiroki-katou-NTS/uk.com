package nts.uk.screen.at.app.kha002;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class Kha002InitDto {
    private GeneralDate startDate;
    private GeneralDate endDate;
}
