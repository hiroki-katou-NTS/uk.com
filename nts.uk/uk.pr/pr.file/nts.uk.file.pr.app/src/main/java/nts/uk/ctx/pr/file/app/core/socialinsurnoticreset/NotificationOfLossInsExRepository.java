package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import nts.arc.time.GeneralDate;

import java.util.List;

public interface NotificationOfLossInsExRepository {

    List<InsLossDataExport> getWelfPenInsLoss(List<String> empIds, String cid, GeneralDate startDate, GeneralDate endDate);
    List<InsLossDataExport> getHealthInsLoss(List<String> empIds, String cid, GeneralDate startDate, GeneralDate endDate);
    CompanyInfor getCompanyInfor(String cid);

}
