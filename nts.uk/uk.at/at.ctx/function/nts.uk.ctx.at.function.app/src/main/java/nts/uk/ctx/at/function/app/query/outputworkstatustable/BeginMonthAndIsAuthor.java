package nts.uk.ctx.at.function.app.query.outputworkstatustable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class BeginMonthAndIsAuthor {
    /** The start month. */
    // 期首月
    private Integer startMonth;

    private boolean isAuthor;
}
