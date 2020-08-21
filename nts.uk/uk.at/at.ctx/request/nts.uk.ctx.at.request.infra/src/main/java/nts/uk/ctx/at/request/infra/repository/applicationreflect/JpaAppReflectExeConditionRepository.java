package nts.uk.ctx.at.request.infra.repository.applicationreflect;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.applicationreflect.AppReflectExeConditionRepository;
import nts.uk.ctx.at.request.dom.applicationreflect.AppReflectExecutionCondition;
import nts.uk.ctx.at.request.infra.entity.applicationreflect.KrqmtAppReflectCnd;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaAppReflectExeConditionRepository extends JpaRepository implements AppReflectExeConditionRepository {
    @Override
    public Optional<AppReflectExecutionCondition> findByCompanyId(String companyId) {
        return this.queryProxy().find(companyId, KrqmtAppReflectCnd.class).map(KrqmtAppReflectCnd::toDomain);
    }

    @Override
    public void save(AppReflectExecutionCondition domain) {
        Optional<KrqmtAppReflectCnd> optEntity = this.queryProxy().find(domain.getCompanyId(), KrqmtAppReflectCnd.class);
        if (optEntity.isPresent()) {
            KrqmtAppReflectCnd oldEntity = optEntity.get();
            KrqmtAppReflectCnd newEntity = KrqmtAppReflectCnd.fromDomain(domain);
            newEntity.setContractCd(oldEntity.getContractCd());
            this.commandProxy().update(newEntity);
        } else {
            this.commandProxy().insert(KrqmtAppReflectCnd.fromDomain(domain));
        }
    }
}
