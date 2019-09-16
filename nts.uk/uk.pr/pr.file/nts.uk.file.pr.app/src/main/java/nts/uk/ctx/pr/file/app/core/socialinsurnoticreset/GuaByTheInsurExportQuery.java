package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.Data;
import nts.arc.time.GeneralDate;

import java.util.List;


@Data
public class GuaByTheInsurExportQuery {
    int typeExport;
    List<String> empIds;
    GeneralDate startDate;
    GeneralDate endDate;
    SocialInsurNotiCreateSetQuery socialInsurNotiCreateSetQuery;
}