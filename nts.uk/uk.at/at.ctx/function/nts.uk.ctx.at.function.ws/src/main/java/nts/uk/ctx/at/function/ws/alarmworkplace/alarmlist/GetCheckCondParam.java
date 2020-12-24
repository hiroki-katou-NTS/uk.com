package nts.uk.ctx.at.function.ws.alarmworkplace.alarmlist;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class GetCheckCondParam {
    private String alarmPatternCode;
    private Integer processingYm;
    private GeneralDate closureStartDate;
    private GeneralDate closureEndDate;
}
