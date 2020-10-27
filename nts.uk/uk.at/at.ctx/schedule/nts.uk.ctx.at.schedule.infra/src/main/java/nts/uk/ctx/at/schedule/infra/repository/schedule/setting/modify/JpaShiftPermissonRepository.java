package nts.uk.ctx.at.schedule.infra.repository.schedule.setting.modify;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.ShiftPermisson;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.ShiftPermissonRepository;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.modify.KscstScheShiftPermisson;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.modify.KscstScheShiftPermissonPK;


@Stateless
public class JpaShiftPermissonRepository extends JpaRepository implements ShiftPermissonRepository{
	private static final String SELECT_BY_CID;
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscstScheShiftPermisson e");
		builderString.append(" WHERE e.kscstScheShiftPermissonPK.companyId = :companyId");
		builderString.append(" AND e.kscstScheShiftPermissonPK.roleId = :roleId");
		SELECT_BY_CID = builderString.toString();
	}
	/**
	 * Convert to Domain 
	 * @param schemodifyDeadline
	 * @return
	 */
	private ShiftPermisson convertToDomain(KscstScheShiftPermisson kscmtScheAuthWkp) {
		ShiftPermisson persAuthority = ShiftPermisson.createFromJavaType(
				kscmtScheAuthWkp.kscstScheShiftPermissonPK.companyId, 
				kscmtScheAuthWkp.kscstScheShiftPermissonPK.roleId, 
				kscmtScheAuthWkp.availableShift,
				kscmtScheAuthWkp.kscstScheShiftPermissonPK.functionNoShift
				);
		return persAuthority;
	}
	
	/**
	 * Convert to Database Type
	 * @param schemodifyDeadline
	 * @return
	 */
	private KscstScheShiftPermisson convertToDbType(ShiftPermisson persAuthority) {
		KscstScheShiftPermisson schePersAuthority = new KscstScheShiftPermisson();
		KscstScheShiftPermissonPK scheDateAuthorityPK = new KscstScheShiftPermissonPK(persAuthority.getCompanyId(), persAuthority.getRoleId(), persAuthority.getFunctionNoShift());
		schePersAuthority.availableShift = persAuthority.getAvailableShift();
		schePersAuthority.kscstScheShiftPermissonPK = scheDateAuthorityPK;
		return schePersAuthority;
	}
	
	/**
	 * Find by Company Id
	 */
	@Override
	public List<ShiftPermisson> findByCompanyId(String companyId, String roleId) {
		return this.queryProxy().query(SELECT_BY_CID, KscstScheShiftPermisson.class).setParameter("companyId", companyId)
				.setParameter("roleId", roleId)
				.getList(c -> convertToDomain(c));
	}
	
	/**
	 * Add Schemodify Deadline
	 */
	@Override
	public void add(ShiftPermisson author) {
		this.commandProxy().insert(convertToDbType(author));
	}

	/**
	 * Update Schemodify Deadline
	 */
	@Override
	public void update(ShiftPermisson author) {
		KscstScheShiftPermissonPK primaryKey = new KscstScheShiftPermissonPK(author.getCompanyId(), author.getRoleId(), author.getFunctionNoShift());
		KscstScheShiftPermisson entity = this.queryProxy().find(primaryKey, KscstScheShiftPermisson.class).get();
				entity.availableShift = author.getAvailableShift();
				entity.kscstScheShiftPermissonPK = primaryKey;
		this.commandProxy().update(entity);
	}
	
	/**
	 * Find one Schemodify Deadline by companyId and roleId
	 */
	@Override
	public Optional<ShiftPermisson> findByCId(String companyId, String roleId, int functionNoShift) {
		return this.queryProxy().find(new KscstScheShiftPermissonPK(companyId, roleId, functionNoShift), KscstScheShiftPermisson.class)
				.map(c -> convertToDomain(c));
	}

}
