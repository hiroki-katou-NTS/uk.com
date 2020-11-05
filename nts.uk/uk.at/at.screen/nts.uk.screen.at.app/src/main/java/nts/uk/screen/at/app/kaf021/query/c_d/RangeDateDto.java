package nts.uk.screen.at.app.kaf021.query.c_d;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
@AllArgsConstructor
public class RangeDateDto {
    private GeneralDate startDate;
    private GeneralDate endDate;
}
