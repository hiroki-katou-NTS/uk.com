package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import java.util.List;

public interface NotificationOfLossInsExRepository {

    List<InsLossDataExport> getWelfPenInsLoss(List<String> empId);
    List<InsLossDataExport> getHealthInsLoss(List<String> empId);
    CompanyInfor getCompanyInfor(String cid);

}
