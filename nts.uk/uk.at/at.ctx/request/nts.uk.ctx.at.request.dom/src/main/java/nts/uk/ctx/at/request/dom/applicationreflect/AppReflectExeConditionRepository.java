package nts.uk.ctx.at.request.dom.applicationreflect;

import java.util.Optional;

public interface AppReflectExeConditionRepository {
    Optional<AppReflectExecutionCondition> findByCompanyId(String companyId);
    void save(AppReflectExecutionCondition domain);
}
