package nts.uk.ctx.at.shared.infra.repository.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectRepository;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveApplicationReflect;
import nts.uk.ctx.at.shared.infra.entity.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.KrqmtAppTimeHd;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaTimeLeaveAppReflectRepository extends JpaRepository implements TimeLeaveAppReflectRepository {
    @Override
    public Optional<TimeLeaveApplicationReflect> findByCompany(String companyId) {
        return this.queryProxy().find(companyId, KrqmtAppTimeHd.class).map(KrqmtAppTimeHd::toDomain);
    }

    @Override
    public void save(TimeLeaveApplicationReflect domain) {
        Optional<KrqmtAppTimeHd> optEntity = this.queryProxy().find(domain.getCompanyId(), KrqmtAppTimeHd.class);
        if (optEntity.isPresent()) {
            KrqmtAppTimeHd entity = optEntity.get();
            entity.update(domain);
            this.commandProxy().update(entity);
        } else {
            this.commandProxy().insert(KrqmtAppTimeHd.fromDomain(domain));
        }
    }
}
