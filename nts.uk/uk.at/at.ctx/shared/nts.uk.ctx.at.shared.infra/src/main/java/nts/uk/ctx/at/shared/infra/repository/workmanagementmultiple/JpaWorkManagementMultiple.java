package nts.uk.ctx.at.shared.infra.repository.workmanagementmultiple;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultiple;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultipleRepository;
import nts.uk.ctx.at.shared.infra.entity.workmanagementmultiple.Kshmt2workMngMultiple;

// TODO: Auto-generated Javadoc
/**
 * The Class JpaWorkManagementMultiple.
 */
@Stateless
public class JpaWorkManagementMultiple extends JpaRepository implements WorkManagementMultipleRepository {

	/** The select single. */
	private static final String SELECT_SINGLE = "SELECT c FROM Kshmt2workMngMultiple c"
			+ " WHERE c.companyID = :companyID";
	
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultipleRepository#findByCode(java.lang.String)
	 */
	@Override
	public Optional<WorkManagementMultiple> findByCode(String companyID) {
		return this.queryProxy()
				.query(SELECT_SINGLE, Kshmt2workMngMultiple.class)
				.setParameter("companyID", companyID)
				.getSingle(c -> toDomain(c));
	}

	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the work management multiple
	 */
	private WorkManagementMultiple toDomain(Kshmt2workMngMultiple entity) {
		
		return WorkManagementMultiple.createFromJavaType(
				entity.companyID,
				entity.useATR);
	}
	
	/**
	 * To db type.
	 *
	 * @param setting the setting
	 * @return the kshst work management multiple
	 */
	private Kshmt2workMngMultiple toDbType(WorkManagementMultiple setting) {
		Kshmt2workMngMultiple entity = new Kshmt2workMngMultiple();
		entity.companyID = setting.getCompanyID();
		entity.useATR = setting.getUseATR().value;
		return entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultipleRepository#insert(nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultiple)
	 */
	@Override
	public void insert(WorkManagementMultiple setting) {
		Kshmt2workMngMultiple entity = toDbType(setting);
		this.commandProxy().insert(entity);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultipleRepository#update(nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultiple)
	 */
	@Override
	public void update(WorkManagementMultiple setting) {
		Kshmt2workMngMultiple entity = toDbType(setting);
		this.commandProxy().update(entity);
	}

	
}
