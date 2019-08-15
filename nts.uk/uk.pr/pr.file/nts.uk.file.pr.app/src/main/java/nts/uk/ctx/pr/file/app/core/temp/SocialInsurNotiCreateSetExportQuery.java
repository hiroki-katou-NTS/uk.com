package nts.uk.ctx.pr.file.app.core.temp;

import lombok.Data;
import nts.arc.time.GeneralDate;

import java.util.List;

@Data
public class SocialInsurNotiCreateSetExportQuery {
    private GeneralDate startDate;
    private GeneralDate endDate;
    private List<String> userIds;
}
