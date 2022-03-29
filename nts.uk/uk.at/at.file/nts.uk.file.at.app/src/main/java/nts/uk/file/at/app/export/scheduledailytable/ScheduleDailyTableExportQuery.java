package nts.uk.file.at.app.export.scheduledailytable;

import lombok.Value;
import nts.arc.time.GeneralDate;

import java.util.List;

@Value
public class ScheduleDailyTableExportQuery {
    private List<String> workplaceGroupIds;
    private GeneralDate periodStart;
    private GeneralDate periodEnd;
    private String outputItemCode;
    private int printTarget;
    private boolean displayBothWhenDiffOnly;
}
