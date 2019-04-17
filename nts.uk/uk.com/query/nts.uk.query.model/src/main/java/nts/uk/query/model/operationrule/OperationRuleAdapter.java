package nts.uk.query.model.operationrule;

import java.util.Optional;

public interface OperationRuleAdapter {

    Optional<OperationRuleImport> getOperationRuleByCompanyId(String companyId);
}
