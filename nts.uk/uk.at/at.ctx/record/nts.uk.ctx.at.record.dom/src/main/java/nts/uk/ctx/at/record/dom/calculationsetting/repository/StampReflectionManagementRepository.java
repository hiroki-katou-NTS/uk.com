package nts.uk.ctx.at.record.dom.calculationsetting.repository;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.calculationsetting.StampReflectionManagement;

public interface StampReflectionManagementRepository {
	
	
	Optional<StampReflectionManagement> findByCid(String companyId);

	void update(StampReflectionManagement reflectionManagement);

	void add(StampReflectionManagement reflectionManagement);

}
