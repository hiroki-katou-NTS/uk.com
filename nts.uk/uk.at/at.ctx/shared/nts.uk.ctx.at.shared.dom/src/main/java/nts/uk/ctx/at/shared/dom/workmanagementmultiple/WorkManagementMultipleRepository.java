package nts.uk.ctx.at.shared.dom.workmanagementmultiple;

import java.util.Optional;


public interface WorkManagementMultipleRepository {

	Optional<WorkManagementMultiple> findByCode(String companyID);
}
