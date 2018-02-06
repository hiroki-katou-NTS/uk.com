package nts.uk.ctx.at.schedule.infra.repository.schedule.setting.modify;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.PerWorkplace;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.PerWorkplaceRepository;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.modify.KscstSchePerWorkplace;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.modify.KscstSchePerWorkplacePK;

@Stateless
public class JpaPerWorkplaceRepository extends JpaRepository implements PerWorkplaceRepository{
	private static final String SELECT_BY_CID;
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscstSchePerWorkplace e");
		builderString.append(" WHERE e.kscstSchePerWorkplacePK.companyId = :companyId");
		builderString.append(" AND e.kscstSchePerWorkplacePK.roleId = :roleId");
		SELECT_BY_CID = builderString.toString();
	}
	/**
	 * Convert to Domain 
	 * @param schemodifyDeadline
	 * @return
	 */
	private PerWorkplace convertToDomain(KscstSchePerWorkplace kscstSchePerWorkplace) {
		PerWorkplace persAuthority = PerWorkplace.createFromJavaType(
				kscstSchePerWorkplace.kscstSchePerWorkplacePK.companyId, 
				kscstSchePerWorkplace.kscstSchePerWorkplacePK.roleId, 
				kscstSchePerWorkplace.availableWorkplace,
				kscstSchePerWorkplace.kscstSchePerWorkplacePK.functionNoWorkplace
				
				);
		return persAuthority;
	}
	
	/**
	 * Convert to Database Type
	 * @param schemodifyDeadline
	 * @return
	 */
	private KscstSchePerWorkplace convertToDbType(PerWorkplace persAuthority) {
		KscstSchePerWorkplace schePersAuthority = new KscstSchePerWorkplace();
		KscstSchePerWorkplacePK scheDateAuthorityPK = new KscstSchePerWorkplacePK(persAuthority.getCompanyId(), persAuthority.getRoleId(), persAuthority.getFunctionNoWorkplace());
		schePersAuthority.availableWorkplace = persAuthority.getAvailableWorkplace();
		schePersAuthority.kscstSchePerWorkplacePK = scheDateAuthorityPK;
		return schePersAuthority;
	}
	
	/**
	 * Find by Company Id
	 */
	@Override
	public List<PerWorkplace> findByCompanyId(String companyId, String roleId) {
		return this.queryProxy().query(SELECT_BY_CID, KscstSchePerWorkplace.class).setParameter("companyId", companyId)
				.setParameter("roleId", roleId)
				.getList(c -> convertToDomain(c));
	}
	
	/**
	 * Add Schemodify Deadline
	 */
	@Override
	public void add(PerWorkplace author) {
		this.commandProxy().insert(convertToDbType(author));
	}

	/**
	 * Update Schemodify Deadline
	 */
	@Override
	public void update(PerWorkplace author) {
		KscstSchePerWorkplacePK primaryKey = new KscstSchePerWorkplacePK(author.getCompanyId(), author.getRoleId(), author.getFunctionNoWorkplace());
		KscstSchePerWorkplace entity = this.queryProxy().find(primaryKey, KscstSchePerWorkplace.class).get();
				entity.availableWorkplace = author.getAvailableWorkplace();
				entity.kscstSchePerWorkplacePK = primaryKey;
		this.commandProxy().update(entity);
	}
	
	/**
	 * Find one Schemodify Deadline by companyId and roleId
	 */
	@Override
	public Optional<PerWorkplace> findByCId(String companyId, String roleId, int functionNoWorkplace) {
		return this.queryProxy().find(new KscstSchePerWorkplacePK(companyId, roleId, functionNoWorkplace), KscstSchePerWorkplace.class)
				.map(c -> convertToDomain(c));
	}

}
