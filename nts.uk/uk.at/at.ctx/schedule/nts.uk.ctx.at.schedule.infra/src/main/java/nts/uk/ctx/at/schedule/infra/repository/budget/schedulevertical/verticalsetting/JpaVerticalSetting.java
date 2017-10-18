package nts.uk.ctx.at.schedule.infra.repository.budget.schedulevertical.verticalsetting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalCalItem;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalCalSet;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalSettingRepository;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscstVerticalCalSet;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting.KscstVerticalCalSetPK;

/**
 * 
 * @author tanlv
 *
 */

@Stateless
public class JpaVerticalSetting extends JpaRepository implements VerticalSettingRepository {
	
	private static final String SELECT_ALL_VERTICAL_CAL_SET;
	
	private static final String DELETE_VERTICAL_CAL_SET;
	
	static {

		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscstVerticalCalSet e");
		builderString.append(" WHERE e.kscstVerticalCalSetPK.companyId = :companyId");
		SELECT_ALL_VERTICAL_CAL_SET = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("DELETE FROM KscstVerticalCalSet e");
		builderString.append(" WHERE e.kscstVerticalCalSetPK.companyId = :companyId");
		builderString.append(" AND e.kscstVerticalCalSetPK.verticalCalCd = :verticalCalCd");
		DELETE_VERTICAL_CAL_SET = builderString.toString();
	}

	/**
	 * Find all Vertical Cal Set
	 */
	@Override
	public List<VerticalCalSet> findAllVerticalCalSet(String companyId) {
		return this.queryProxy().query(SELECT_ALL_VERTICAL_CAL_SET, KscstVerticalCalSet.class).setParameter("companyId", companyId)
				.getList(c -> convertToDomainVcs(c));
	}

	/**
	 * Convert to Domain Vertical Cal Set
	 * @param kscstVerticalCalSet
	 * @return
	 */
	private VerticalCalSet convertToDomainVcs(KscstVerticalCalSet kscstVerticalCalSet) {
		VerticalCalSet verticalCalSet = VerticalCalSet.createFromJavaType(
				kscstVerticalCalSet.kscstVerticalCalSetPK.companyId,
				kscstVerticalCalSet.kscstVerticalCalSetPK.verticalCalCd, 
				kscstVerticalCalSet.verticalCalName,
				kscstVerticalCalSet.unit,
				kscstVerticalCalSet.useAtr,
				kscstVerticalCalSet.assistanceTabulationAtr);
		
		return verticalCalSet;
	}

	/**
	 * Find Vertical Cal Set by Cd
	 */
	@Override
	public Optional<VerticalCalSet> getVerticalCalSetByCode(String companyId, String verticalCalCd) {
		KscstVerticalCalSetPK primaryKey = new KscstVerticalCalSetPK(companyId, verticalCalCd);
		
		return this.queryProxy().find(primaryKey, KscstVerticalCalSet.class)
				.map(x -> convertToDomainVcs(x));
	}

	/**
	 * Convert to Vertical Cal Set Db Type
	 * @param verticalCalSet
	 * @return
	 */
	private KscstVerticalCalSet convertToDbTypeVcs(VerticalCalSet verticalCalSet) {
		KscstVerticalCalSet kscstVerticalCalSet = new KscstVerticalCalSet();
		
		KscstVerticalCalSetPK kscstVerticalCalSetPK = new KscstVerticalCalSetPK(
				verticalCalSet.getCompanyId(),
				verticalCalSet.getVerticalCalCd());
		
		kscstVerticalCalSet.verticalCalName = verticalCalSet.getVerticalCalName();
		kscstVerticalCalSet.unit = verticalCalSet.getUnit().value;
		kscstVerticalCalSet.useAtr = verticalCalSet.getUseAtr().value;
		kscstVerticalCalSet.assistanceTabulationAtr = verticalCalSet.getAssistanceTabulationAtr().value;
		
		kscstVerticalCalSet.kscstVerticalCalSetPK = kscstVerticalCalSetPK;
		
		return kscstVerticalCalSet;
	}
	
	/**
	 * Add Vertical Cal Set
	 */
	@Override
	public void addVerticalCalSet(VerticalCalSet verticalCalSet) {
		this.commandProxy().insert(convertToDbTypeVcs(verticalCalSet));	
	}

	/**
	 * Update Vertical Cal Set
	 */
	@Override
	public void updateVerticalCalSet(VerticalCalSet verticalCalSet) {
		KscstVerticalCalSetPK kscstVerticalCalSetPK = new KscstVerticalCalSetPK(verticalCalSet.getCompanyId(), verticalCalSet.getVerticalCalCd());
		KscstVerticalCalSet kscstVerticalCalSet = this.queryProxy().find(kscstVerticalCalSetPK, KscstVerticalCalSet.class).get();
		
		kscstVerticalCalSet.verticalCalName = verticalCalSet.getVerticalCalName();
		kscstVerticalCalSet.unit = verticalCalSet.getUnit().value;
		kscstVerticalCalSet.useAtr = verticalCalSet.getUseAtr().value;
		kscstVerticalCalSet.assistanceTabulationAtr = verticalCalSet.getAssistanceTabulationAtr().value;
		
		this.commandProxy().update(kscstVerticalCalSet);
	}

	/**
	 * Remove Vertical Cal Set
	 */
	@Override
	public void deleteVerticalCalSet(String companyId, String verticalCalCd) {
		this.getEntityManager().createQuery(DELETE_VERTICAL_CAL_SET)
		.setParameter("companyId", companyId).setParameter("verticalCalCd", verticalCalCd)
		.executeUpdate();
	}

	@Override
	public List<VerticalCalItem> findAllVerticalCalItem(String companyId, String verticalCalCd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteVerticalCalItems(String companyId, String verticalCalCd) {
		// TODO Auto-generated method stub
		
	}
}
