package nts.uk.ctx.at.request.infra.repository.setting.company.applicationapprovalsetting.substituteapplicationsetting;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.substituteapplicationsetting.SubstituteHdWorkAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.substituteapplicationsetting.SubstituteHdWorkAppSetRepository;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.substituteapplicationsetting.KrqmtAppHdsubRec;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.substituteworkapplication.SubstituteWorkAppReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.substituteworkapplication.SubstituteWorkAppReflectRepository;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.subleaveapp.SubstituteLeaveAppReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.subleaveapp.SubLeaveAppReflectRepository;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaSubHdWorkAppSetRepository extends JpaRepository implements SubstituteHdWorkAppSetRepository, SubLeaveAppReflectRepository, SubstituteWorkAppReflectRepository {
    @Override
    public Optional<SubstituteHdWorkAppSet> findSettingByCompany(String companyId) {
        return this.queryProxy().find(companyId, KrqmtAppHdsubRec.class).map(KrqmtAppHdsubRec::toSetting);
    }

    @Override
    public Optional<SubstituteWorkAppReflect> findSubWorkAppReflectByCompany(String companyId) {
        return this.queryProxy().find(companyId, KrqmtAppHdsubRec.class).map(KrqmtAppHdsubRec::toSubstituteWorkAppReflect);
    }

    @Override
    public Optional<SubstituteLeaveAppReflect> findSubLeaveAppReflectByCompany(String companyId) {
        return this.queryProxy().find(companyId, KrqmtAppHdsubRec.class).map(KrqmtAppHdsubRec::toSubstituteLeaveAppReflect);
    }

    @Override
    public void save(SubstituteHdWorkAppSet setting, SubstituteLeaveAppReflect subLeaveReflect, SubstituteWorkAppReflect subWorkReflect) {
        Optional<KrqmtAppHdsubRec> optEntity = this.queryProxy().find(setting.getCompanyId(), KrqmtAppHdsubRec.class);
        if (optEntity.isPresent()) {
            KrqmtAppHdsubRec entity = optEntity.get();
            entity.updateSetting(setting);
            entity.updateSubLeaveReflect(subLeaveReflect);
            entity.updateSubWorkReflect(subWorkReflect);
            this.commandProxy().update(entity);
        } else {
            KrqmtAppHdsubRec entity = KrqmtAppHdsubRec.create(setting, subLeaveReflect, subWorkReflect);
            this.commandProxy().insert(entity);
        }
    }
}
