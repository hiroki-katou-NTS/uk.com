package nts.uk.ctx.at.request.dom.applicationreflect;

import nts.uk.ctx.at.request.dom.applicationreflect.service.AppReflectExecutionCondition;

import java.util.Optional;

public interface AppReflectExeConditionRepository {
    Optional<AppReflectExecutionCondition> findByCompanyId(String companyId);
}
