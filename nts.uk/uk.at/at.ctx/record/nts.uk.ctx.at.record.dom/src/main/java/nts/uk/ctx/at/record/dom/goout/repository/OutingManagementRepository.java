package nts.uk.ctx.at.record.dom.goout.repository;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.goout.OutingManagement;

public interface OutingManagementRepository {
	
	Optional<OutingManagement> findByKey(String companyId);

}
