package nts.uk.ctx.at.schedule.infra.repository.schedule.setting.modify;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.CommonAuthor;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.CommonAuthorRepository;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.modify.KscstScheCommonAuthor;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.modify.KscstScheCommonAuthorPK;
@Stateless
public class JpaCommonAuthorRepository extends JpaRepository implements CommonAuthorRepository{
	private static final String SELECT_BY_CID;
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscstScheCommonAuthor e");
		builderString.append(" WHERE e.kscstScheCommonAuthorPK.companyId = :companyId");
		builderString.append(" AND e.kscstScheCommonAuthorPK.roleId = :roleId");
		SELECT_BY_CID = builderString.toString();
	}
	/**
	 * Convert to Domain 
	 * @param schemodifyDeadline
	 * @return
	 */
	private CommonAuthor convertToDomain(KscstScheCommonAuthor author) {
		CommonAuthor commonAuthor = CommonAuthor.createFromJavaType(
				author.kscstScheCommonAuthorPK.companyId, 
				author.kscstScheCommonAuthorPK.roleId,
				author.availableCommon,
				author.kscstScheCommonAuthorPK.functionNoCommon
				
				);
		return commonAuthor;
	}
	
	/**
	 * Convert to Database Type
	 * @param schemodifyDeadline
	 * @return
	 */
	private KscstScheCommonAuthor convertToDbType(CommonAuthor author) {
		KscstScheCommonAuthor commonAuthor = new KscstScheCommonAuthor();
		KscstScheCommonAuthorPK commonAuthorPK = new KscstScheCommonAuthorPK(author.getCompanyId(), author.getRoleId(),author.getFunctionNoCommon());
				commonAuthor.availableCommon = author.getAvailableCommon();
				commonAuthor.kscstScheCommonAuthorPK = commonAuthorPK;
		return commonAuthor;
	}
	
	/**
	 * Find by Company Id
	 */
	@Override
	public List<CommonAuthor> findByCompanyId(String companyId, String roleId) {
		return this.queryProxy().query(SELECT_BY_CID, KscstScheCommonAuthor.class).setParameter("companyId", companyId)
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
		KscstScheCommonAuthorPK primaryKey = new KscstScheCommonAuthorPK(author.getCompanyId(), author.getRoleId(),author.getFunctionNoCommon());
		KscstScheCommonAuthor entity = this.queryProxy().find(primaryKey, KscstScheCommonAuthor.class).get();
				entity.availableCommon = author.getAvailableCommon();
				entity.kscstScheCommonAuthorPK = primaryKey;
		this.commandProxy().update(entity);
	}
	
	/**
	 * Find one Schemodify Deadline by companyId and roleId
	 */
	@Override
	public Optional<CommonAuthor> findByCId(String companyId, String roleId, int functionNoCommon) {
		return this.queryProxy().find(new KscstScheCommonAuthorPK(companyId, roleId,functionNoCommon), KscstScheCommonAuthor.class)
				.map(c -> convertToDomain(c));
	}
}
