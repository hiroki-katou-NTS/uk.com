package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.Data;
import nts.arc.time.GeneralDate;

import java.util.List;

@Data
public class NotificationOfLossInsExportQuery {
    private GeneralDate startDate;
    private GeneralDate endDate;
    private List<String> empIds;
    private GeneralDate reference;
    private NotificationOfLossInsExport socialInsurNotiCreateSet;
}
