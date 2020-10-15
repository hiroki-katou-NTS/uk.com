package nts.uk.ctx.at.request.infra.repository.setting.company.vacationapplicationsetting;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HolidayApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HolidayApplicationSettingRepository;
import nts.uk.ctx.at.request.infra.entity.setting.company.vacationapplicationsetting.KrqmtAppHd;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.VacationApplicationReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.VacationApplicationReflectRepository;

import javax.ejb.Stateless;
import java.util.Optional;

/**
 * refactor 4 refactor4
 */
@Stateless
public class JpaHolidayApplicationSettingRepository extends JpaRepository implements HolidayApplicationSettingRepository, VacationApplicationReflectRepository {

    @Override
    public Optional<HolidayApplicationSetting> findSettingByCompanyId(String companyId) {
        return this.queryProxy().find(companyId, KrqmtAppHd.class).map(KrqmtAppHd::toHolidayApplicationSetting);
    }

    @Override
    public Optional<VacationApplicationReflect> findReflectByCompanyId(String companyId) {
        return this.queryProxy().find(companyId, KrqmtAppHd.class).map(KrqmtAppHd::toVacationApplicationReflect);
    }

    @Override
    public void save(String companyId, HolidayApplicationSetting setting, VacationApplicationReflect reflect) {
        Optional<KrqmtAppHd> optEntity = this.queryProxy().find(companyId, KrqmtAppHd.class);
        if (optEntity.isPresent()) {
            KrqmtAppHd entity = optEntity.get();
            entity.updateSetting(setting);
            entity.updateReflect(reflect);
            this.commandProxy().update(entity);
        } else {
            this.commandProxy().insert(KrqmtAppHd.create(setting, reflect));
        }
    }
}
