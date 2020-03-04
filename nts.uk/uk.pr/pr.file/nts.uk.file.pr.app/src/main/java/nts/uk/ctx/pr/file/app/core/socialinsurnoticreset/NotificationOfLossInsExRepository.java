package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInfor;

import java.util.List;

public interface NotificationOfLossInsExRepository {
    List<InsLossDataExport> getHealthInsLoss(List<String> empIds, String cid, GeneralDate startDate, GeneralDate endDate);
    List<PensFundSubmissData> getHealthInsAssociation(List<String> empIds, String cid, GeneralDate startDate, GeneralDate endDate);

}
