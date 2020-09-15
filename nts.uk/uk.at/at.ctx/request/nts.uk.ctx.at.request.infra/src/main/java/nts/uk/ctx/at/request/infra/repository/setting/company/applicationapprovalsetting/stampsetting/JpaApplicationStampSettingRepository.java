package nts.uk.ctx.at.request.infra.repository.setting.company.applicationapprovalsetting.stampsetting;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.AppStampSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.ApplicationStampSettingRepository;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.stampsetting.KrqmtAppStamp;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.stampapplication.StampAppReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.stampapplication.StampAppReflectRepository;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaApplicationStampSettingRepository extends JpaRepository implements ApplicationStampSettingRepository, StampAppReflectRepository {
    @Override
    public Optional<AppStampSetting> findSettingByCompanyId(String companyId) {
        return this.queryProxy().find(companyId, KrqmtAppStamp.class).map(KrqmtAppStamp::toSettingDomain);
    }

    @Override
    public Optional<StampAppReflect> findReflectByCompanyId(String companyId) {
        return this.queryProxy().find(companyId, KrqmtAppStamp.class).map(KrqmtAppStamp::toReflectDomain);
    }

    @Override
    public void save(String companyId, AppStampSetting setting, StampAppReflect reflect) {
        Optional<KrqmtAppStamp> optEntity = this.queryProxy().find(companyId, KrqmtAppStamp.class);
        if (optEntity.isPresent()) {
            KrqmtAppStamp entity = optEntity.get();
            entity.updateSetting(setting);
            entity.updateReflect(reflect);
        } else {
            KrqmtAppStamp entity = KrqmtAppStamp.create(companyId, setting, reflect);
            this.commandProxy().insert(entity);
        }
    }
}
