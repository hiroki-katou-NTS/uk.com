package nts.uk.ctx.at.shared.infra.repository.workmanagementmultiple;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultiple;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultipleRepository;
import nts.uk.ctx.at.shared.infra.entity.workmanagementmultiple.KshstWorkManagementMultiple;

@Stateless
public class JpaWorkManagementMultiple extends JpaRepository implements WorkManagementMultipleRepository {

	private final String SELECT_SINGLE = "SELECT c FROM KshstWorkManagementMultiple c"
			+ " WHERE c.companyID = :companyID";
	
	
	@Override
	public Optional<WorkManagementMultiple> findByCode(String companyID) {
		return this.queryProxy()
				.query(SELECT_SINGLE, KshstWorkManagementMultiple.class)
				.setParameter("companyID", companyID)
				.getSingle(c -> toDomain(c));
	}

	private WorkManagementMultiple toDomain(KshstWorkManagementMultiple entity) {
		
		return WorkManagementMultiple.createFromJavaType(
				entity.companyID,
				entity.useATR);
	}

	
}
