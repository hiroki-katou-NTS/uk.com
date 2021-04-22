package nts.uk.ctx.at.function.app.command.alarmworkplace.alarmlist;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class CategoryPeriodCommand {
    private int category;
    private GeneralDate startDate;
    private GeneralDate endDate;
    private Integer startYm;
    private Integer endYm;
    private Integer yearMonth;
}
