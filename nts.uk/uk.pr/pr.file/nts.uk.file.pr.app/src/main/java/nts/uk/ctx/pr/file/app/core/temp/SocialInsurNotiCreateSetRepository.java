package nts.uk.ctx.pr.file.app.core.temp;

import java.util.List;

public interface SocialInsurNotiCreateSetRepository {

    List<InsLossDataExport> getWelfPenInsLoss(List<String> empId);
    List<InsLossDataExport> getHealthInsLoss(List<String> empId);
    SocialInsurNotiCreateSetExport getSocialInsurNotiCreateSet(String userId);

}
