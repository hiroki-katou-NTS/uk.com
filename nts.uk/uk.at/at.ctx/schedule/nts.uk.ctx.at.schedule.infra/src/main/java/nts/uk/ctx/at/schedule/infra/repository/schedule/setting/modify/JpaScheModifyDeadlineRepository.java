package nts.uk.ctx.at.schedule.infra.repository.schedule.setting.modify;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.ScheModifyDeadlineRepository;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.SchemodifyDeadline;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.modify.KscstSchemodifyDeadline;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.modify.KscstSchemodifyDeadlinePK;
/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class JpaScheModifyDeadlineRepository  extends JpaRepository implements ScheModifyDeadlineRepository{
	private static final String SELECT_BY_CID;
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscstSchemodifyDeadline e");
		builderString.append(" WHERE e.kscstSchemodifyDeadlinePK.companyId = :companyId");
		builderString.append(" AND e.kscstSchemodifyDeadlinePK.roleId = :roleId");
		SELECT_BY_CID = builderString.toString();
	}
	
	/**
	 * Convert to Domain 
	 * @param schemodifyDeadline
	 * @return
	 */
	private SchemodifyDeadline convertToDomain(KscstSchemodifyDeadline schemodifyDeadline) {
		SchemodifyDeadline deadline = SchemodifyDeadline.createFromJavaType(
				schemodifyDeadline.kscstSchemodifyDeadlinePK.companyId, 
				schemodifyDeadline.kscstSchemodifyDeadlinePK.roleId, 
				schemodifyDeadline.useCls, 
				schemodifyDeadline.correctDeadline);
		return deadline;
	}
	
	/**
	 * Convert to Database Type
	 * @param schemodifyDeadline
	 * @return
	 */
	private KscstSchemodifyDeadline convertToDbType(SchemodifyDeadline schemodifyDeadline) {
		KscstSchemodifyDeadline deadline = new KscstSchemodifyDeadline();
		KscstSchemodifyDeadlinePK deadlinePK = new KscstSchemodifyDeadlinePK(schemodifyDeadline.getCompanyId(), schemodifyDeadline.getRoleId());
				deadline.useCls = schemodifyDeadline.getUseCls().value;
				deadline.correctDeadline = schemodifyDeadline.getCorrectDeadline().v();
				deadline.kscstSchemodifyDeadlinePK = deadlinePK;
		return deadline;
	}
	
	/**
	 * Find by Company Id
	 */
	@Override
	public List<SchemodifyDeadline> findByCompanyId(String companyId, String roleId) {
		return this.queryProxy().query(SELECT_BY_CID, KscstSchemodifyDeadline.class).setParameter("companyId", companyId)
				.setParameter("roleId", roleId)
				.getList(c -> convertToDomain(c));
	}
	
	/**
	 * Add Schemodify Deadline
	 */
	@Override
	public void add(SchemodifyDeadline deadline) {
		this.commandProxy().insert(convertToDbType(deadline));
	}

	/**
	 * Update Schemodify Deadline
	 */
	@Override
	public void update(SchemodifyDeadline deadline) {
		KscstSchemodifyDeadlinePK primaryKey = new KscstSchemodifyDeadlinePK(deadline.getCompanyId(), deadline.getRoleId());
		KscstSchemodifyDeadline entity = this.queryProxy().find(primaryKey, KscstSchemodifyDeadline.class).get();
				entity.useCls = deadline.getUseCls().value;
				entity.correctDeadline = deadline.getCorrectDeadline().v();
				entity.kscstSchemodifyDeadlinePK = primaryKey;
		this.commandProxy().update(entity);
	}
	
	/**
	 * Find one Schemodify Deadline by companyId and roleId
	 */
	@Override
	public Optional<SchemodifyDeadline> findByCId(String companyId, String roleId) {
		return this.queryProxy().find(new KscstSchemodifyDeadlinePK(companyId, roleId), KscstSchemodifyDeadline.class)
				.map(c -> convertToDomain(c));
	}
}
