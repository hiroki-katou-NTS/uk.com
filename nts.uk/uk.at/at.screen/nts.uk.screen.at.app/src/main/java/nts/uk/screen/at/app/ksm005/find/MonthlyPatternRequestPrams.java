package nts.uk.screen.at.app.ksm005.find;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MonthlyPatternRequestPrams {

    /** The monthly pattern code. */
    private String monthlyPatternCode;

    private DatePeriodDto datePeriodDto;

}
