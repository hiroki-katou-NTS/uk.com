package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.Data;
import nts.arc.time.GeneralDate;

import java.util.List;


@Data
public class GuaByTheInsurExportQuery {
    int typeExport;
    List<String> employeeIds;
    GeneralDate startDate;
    GeneralDate endDate;
    SocialInsurNotiCreateSetQuery socialInsurNotiCreateSetQuery;
}