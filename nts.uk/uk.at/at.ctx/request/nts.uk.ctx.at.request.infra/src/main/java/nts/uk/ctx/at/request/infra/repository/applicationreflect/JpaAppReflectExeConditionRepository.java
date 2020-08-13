package nts.uk.ctx.at.request.infra.repository.applicationreflect;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.applicationreflect.AppReflectExeConditionRepository;
import nts.uk.ctx.at.request.dom.applicationreflect.service.AppReflectExecutionCondition;
import nts.uk.ctx.at.request.infra.entity.applicationreflect.KrqstAppReflectCnd;

import java.util.Optional;

public class JpaAppReflectExeConditionRepository extends JpaRepository implements AppReflectExeConditionRepository {
    @Override
    public Optional<AppReflectExecutionCondition> findByCompanyId(String companyId) {
        return this.queryProxy().find(companyId, KrqstAppReflectCnd.class).map(KrqstAppReflectCnd::toDomain);
    }
}
