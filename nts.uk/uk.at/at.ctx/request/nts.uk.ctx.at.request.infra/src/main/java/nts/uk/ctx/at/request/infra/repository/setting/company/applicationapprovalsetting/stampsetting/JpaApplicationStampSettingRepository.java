package nts.uk.ctx.at.request.infra.repository.setting.company.applicationapprovalsetting.stampsetting;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.AppStampSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.AppStampSettingRepository;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.stampsetting.KrqmtAppStamp;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaApplicationStampSettingRepository extends JpaRepository implements AppStampSettingRepository {
    @Override
    public Optional<AppStampSetting> findByCompanyId(String companyId) {
        return this.queryProxy().find(companyId, KrqmtAppStamp.class).map(KrqmtAppStamp::toSettingDomain);
    }
}
