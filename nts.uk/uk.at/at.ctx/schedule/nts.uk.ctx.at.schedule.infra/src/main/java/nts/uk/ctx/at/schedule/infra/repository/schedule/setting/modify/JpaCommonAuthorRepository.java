package nts.uk.ctx.at.schedule.infra.repository.schedule.setting.modify;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.CommonAuthor;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.CommonAuthorRepository;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.modify.KscmtScheAuthCommon;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.modify.KscmtScheAuthCommonPK;
@Stateless
public class JpaCommonAuthorRepository extends JpaRepository implements CommonAuthorRepository{
	private static final String SELECT_BY_CID;
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscmtScheAuthCommon e");
		builderString.append(" WHERE e.kscmtScheAuthCommonPK.companyId = :companyId");
		builderString.append(" AND e.kscmtScheAuthCommonPK.roleId = :roleId");
		SELECT_BY_CID = builderString.toString();
	}
	/**
	 * Convert to Domain 
	 * @param schemodifyDeadline
	 * @return
	 */
	private CommonAuthor convertToDomain(KscmtScheAuthCommon author) {
		CommonAuthor commonAuthor = CommonAuthor.createFromJavaType(
				author.kscmtScheAuthCommonPK.companyId, 
				author.kscmtScheAuthCommonPK.roleId,
				author.availableCommon,
				author.kscmtScheAuthCommonPK.functionNoCommon
				
				);
		return commonAuthor;
	}
	
	/**
	 * Convert to Database Type
	 * @param schemodifyDeadline
	 * @return
	 */
	private KscmtScheAuthCommon convertToDbType(CommonAuthor author) {
		KscmtScheAuthCommon commonAuthor = new KscmtScheAuthCommon();
		KscmtScheAuthCommonPK commonAuthorPK = new KscmtScheAuthCommonPK(author.getCompanyId(), author.getRoleId(),author.getFunctionNoCommon());
				commonAuthor.availableCommon = author.getAvailableCommon();
				commonAuthor.kscmtScheAuthCommonPK = commonAuthorPK;
		return commonAuthor;
	}
	
	/**
	 * Find by Company Id
	 */
	@Override
	public List<CommonAuthor> findByCompanyId(String companyId, String roleId) {
		return this.queryProxy().query(SELECT_BY_CID, KscmtScheAuthCommon.class).setParameter("companyId", companyId)
				.setParameter("roleId", roleId)
				.getList(c -> convertToDomain(c));
	}
	
	/**
	 * Add Schemodify Deadline
	 */
	@Override
	public void add(CommonAuthor author) {
		this.commandProxy().insert(convertToDbType(author));
	}

	/**
	 * Update Schemodify Deadline
	 */
	@Override
	public void update(CommonAuthor author) {
		KscmtScheAuthCommonPK primaryKey = new KscmtScheAuthCommonPK(author.getCompanyId(), author.getRoleId(),author.getFunctionNoCommon());
		KscmtScheAuthCommon entity = this.queryProxy().find(primaryKey, KscmtScheAuthCommon.class).get();
				entity.availableCommon = author.getAvailableCommon();
				entity.kscmtScheAuthCommonPK = primaryKey;
		this.commandProxy().update(entity);
	}
	
	/**
	 * Find one Schemodify Deadline by companyId and roleId
	 */
	@Override
	public Optional<CommonAuthor> findByCId(String companyId, String roleId, int functionNoCommon) {
		return this.queryProxy().find(new KscmtScheAuthCommonPK(companyId, roleId,functionNoCommon), KscmtScheAuthCommon.class)
				.map(c -> convertToDomain(c));
	}
}
