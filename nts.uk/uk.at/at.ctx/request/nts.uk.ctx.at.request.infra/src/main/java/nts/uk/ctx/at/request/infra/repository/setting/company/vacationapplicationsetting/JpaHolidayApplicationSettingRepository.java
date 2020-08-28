package nts.uk.ctx.at.request.infra.repository.setting.company.vacationapplicationsetting;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HolidayApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HolidayApplicationSettingRepository;
import nts.uk.ctx.at.request.infra.entity.setting.company.vacationapplicationsetting.KrqstAppHd;

import javax.ejb.Stateless;
import java.util.Optional;

/**
 * refactor 4 refactor4
 */
@Stateless
public class JpaHolidayApplicationSettingRepository extends JpaRepository implements HolidayApplicationSettingRepository {

    @Override
    public Optional<HolidayApplicationSetting> findByCompanyId(String companyId) {
        return this.queryProxy().find(companyId, KrqstAppHd.class).map(KrqstAppHd::toHolidayApplicationSetting);
    }
}
