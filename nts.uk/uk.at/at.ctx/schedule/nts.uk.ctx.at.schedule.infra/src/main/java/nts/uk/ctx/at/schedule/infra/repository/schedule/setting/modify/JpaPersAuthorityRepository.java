package nts.uk.ctx.at.schedule.infra.repository.schedule.setting.modify;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.PersAuthority;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.PersAuthorityRepository;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.modify.KscstSchePersAuthority;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.modify.KscstSchePersAuthorityPK;
@Stateless
public class JpaPersAuthorityRepository extends JpaRepository implements PersAuthorityRepository{
	private static final String SELECT_BY_CID;
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscstSchePersAuthority e");
		builderString.append(" WHERE e.kscstSchePersAuthorityPK.companyId = :companyId");
		builderString.append(" AND e.kscstSchePersAuthorityPK.roleId = :roleId");
		SELECT_BY_CID = builderString.toString();
	}
	/**
	 * Convert to Domain 
	 * @param schemodifyDeadline
	 * @return
	 */
	private PersAuthority convertToDomain(KscstSchePersAuthority kscstSchePersAuthority) {
		PersAuthority persAuthority = PersAuthority.createFromJavaType(
				kscstSchePersAuthority.kscstSchePersAuthorityPK.companyId, 
				kscstSchePersAuthority.kscstSchePersAuthorityPK.roleId,
				kscstSchePersAuthority.availablePers,
				kscstSchePersAuthority.kscstSchePersAuthorityPK.functionNoPers
				
				);
		return persAuthority;
	}
	
	/**
	 * Convert to Database Type
	 * @param schemodifyDeadline
	 * @return
	 */
	private KscstSchePersAuthority convertToDbType(PersAuthority persAuthority) {
		KscstSchePersAuthority schePersAuthority = new KscstSchePersAuthority();
		KscstSchePersAuthorityPK scheDateAuthorityPK = new KscstSchePersAuthorityPK(persAuthority.getCompanyId(), persAuthority.getRoleId(),persAuthority.getFunctionNoPers());
		schePersAuthority.availablePers = persAuthority.getAvailablePers();
		schePersAuthority.kscstSchePersAuthorityPK = scheDateAuthorityPK;
		return schePersAuthority;
	}
	
	/**
	 * Find by Company Id
	 */
	@Override
	public List<PersAuthority> findByCompanyId(String companyId, String roleId) {
		return this.queryProxy().query(SELECT_BY_CID, KscstSchePersAuthority.class).setParameter("companyId", companyId)
				.setParameter("roleId", roleId)
				.getList(c -> convertToDomain(c));
	}
	
	/**
	 * Add Schemodify Deadline
	 */
	@Override
	public void add(PersAuthority author) {
		this.commandProxy().insert(convertToDbType(author));
	}

	/**
	 * Update Schemodify Deadline
	 */
	@Override
	public void update(PersAuthority author) {
		KscstSchePersAuthorityPK primaryKey = new KscstSchePersAuthorityPK(author.getCompanyId(), author.getRoleId(), author.getFunctionNoPers());
		KscstSchePersAuthority entity = this.queryProxy().find(primaryKey, KscstSchePersAuthority.class).get();
				entity.availablePers = author.getAvailablePers();
				entity.kscstSchePersAuthorityPK = primaryKey;
		this.commandProxy().update(entity);
	}
	
	/**
	 * Find one Schemodify Deadline by companyId and roleId
	 */
	@Override
	public Optional<PersAuthority> findByCId(String companyId, String roleId, int functionNoPers) {
		return this.queryProxy().find(new KscstSchePersAuthorityPK(companyId, roleId, functionNoPers), KscstSchePersAuthority.class)
				.map(c -> convertToDomain(c));
	}

}
