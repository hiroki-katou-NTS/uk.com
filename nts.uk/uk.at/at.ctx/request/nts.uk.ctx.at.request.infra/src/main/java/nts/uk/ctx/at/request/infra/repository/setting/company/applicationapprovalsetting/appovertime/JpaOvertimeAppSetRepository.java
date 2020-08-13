package nts.uk.ctx.at.request.infra.repository.setting.company.applicationapprovalsetting.appovertime;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSetRepository;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.appovertime.KrqstAppOvertime;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaOvertimeAppSetRepository extends JpaRepository implements OvertimeAppSetRepository {
    @Override
    public Optional<OvertimeAppSet> findByCompanyId(String companyId) {
        return this.queryProxy().find(companyId, KrqstAppOvertime.class).map(KrqstAppOvertime::toDomain);
    }
}
