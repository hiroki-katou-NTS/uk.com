package nts.uk.ctx.at.request.infra.repository.setting.company.applicationapprovalsetting.hdworkapplicationsetting;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.HolidayWorkAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.HolidayWorkAppSetRepository;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.hdworkapplicationsetting.KrqmtAppHdWork;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.HdWorkAppReflectRepository;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.HdWorkAppReflect;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaHolidayWorkAppSetRepository extends JpaRepository implements HolidayWorkAppSetRepository, HdWorkAppReflectRepository {

    @Override
    public Optional<HolidayWorkAppSet> findSettingByCompany(String companyId) {
        return this.queryProxy().find(companyId, KrqmtAppHdWork.class).map(KrqmtAppHdWork::toHolidayWorkAppSetDomain);
    }

    @Override
    public Optional<HdWorkAppReflect> findReflectByCompany(String companyId) {
        return this.queryProxy().find(companyId, KrqmtAppHdWork.class).map(KrqmtAppHdWork::toHolidayWorkAppReflect);
    }

    @Override
    public void save(String companyId, HolidayWorkAppSet setting, HdWorkAppReflect reflect) {
        Optional<KrqmtAppHdWork> optEntity = this.queryProxy().find(companyId, KrqmtAppHdWork.class);
        if (optEntity.isPresent()) {
            KrqmtAppHdWork entity = optEntity.get();
            entity.updateSettng(setting);
            entity.updateReflect(reflect);
            this.commandProxy().update(entity);
        } else {
            this.commandProxy().insert(KrqmtAppHdWork.create(setting, reflect));
        }
    }
}
