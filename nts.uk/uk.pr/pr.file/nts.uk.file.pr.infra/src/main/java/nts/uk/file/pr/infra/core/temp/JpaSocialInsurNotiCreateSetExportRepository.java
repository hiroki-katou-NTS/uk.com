package nts.uk.file.pr.infra.core.temp;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.file.app.core.temp.InsLossDataExport;
import nts.uk.ctx.pr.file.app.core.temp.SocialInsurNotiCreateSetExport;
import nts.uk.ctx.pr.file.app.core.temp.SocialInsurNotiCreateSetRepository;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class JpaSocialInsurNotiCreateSetExportRepository extends JpaRepository implements SocialInsurNotiCreateSetRepository {


    @Override
    public List<InsLossDataExport> getWelfPenInsLoss(List<String> empId) {
        return null;
    }

    @Override
    public List<InsLossDataExport> getHealthInsLoss(List<String> empId) {
        return null;
    }

    @Override
    public SocialInsurNotiCreateSetExport getSocialInsurNotiCreateSet(String userId) {
        return null;
    }
}
