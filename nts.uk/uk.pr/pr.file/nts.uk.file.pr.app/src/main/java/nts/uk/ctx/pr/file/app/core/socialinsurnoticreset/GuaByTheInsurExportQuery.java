package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.Data;

import java.util.List;


@Data
public class GuaByTheInsurExportQuery {
    int typeExport;
    List<String> empIds;
    String startDate;
    String endDate;
    SocialInsurNotiCreateSetQuery socialInsurNotiCreateSetQuery;
}