package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.Data;
import nts.arc.time.GeneralDate;

import java.util.List;


@Data
public class GuaByTheInsurExportQuery {
    private int typeExport;
    private List<String> empIds;
    private GeneralDate startDate;
    private GeneralDate baseDate;
    private GeneralDate endDate;
    private SocialInsurNotiCreateSetQuery socialInsurNotiCreateSetQuery;
}