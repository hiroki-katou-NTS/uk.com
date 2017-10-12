package nts.uk.ctx.at.schedule.infra.repository.budget.schedulevertical.fixedverticalsetting;

import java.util.List;


import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.FixedVertical;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.FixedVerticalSettingRepository;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.VerticalTime;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.fixedverticalsetting.KscstFixedVerticalSet;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.fixedverticalsetting.KscstFixedVerticalSetPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.fixedverticalsetting.KscstVerticalTimeSet;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.fixedverticalsetting.KscstVerticalTimeSetPK;

/**
 * 
 * @author phongtq
 *
 */

@Stateless
public class JpaFixedVerticalSetting extends JpaRepository implements FixedVerticalSettingRepository {

	private static final String SELECT_BY_CID;

	private static final String SELECT_TIME_BY_CID;

	static {

		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscstFixedVerticalSet e");
		builderString.append(" WHERE e.kscstFixedVerticalSetPK.companyId = :companyId");
		SELECT_BY_CID = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscstVerticalTimeSet e");
		builderString.append(" WHERE e.kscstVerticalTimeSetPK.companyId = :companyId");
		builderString.append(" AND e.kscstVerticalTimeSetPK.fixedVerticalNo = :fixedVerticalNo");
		SELECT_TIME_BY_CID = builderString.toString();
	}

	/**
	 * Convert to Domain Fixed Vertical
	 * @param kFixedVerticalSet
	 * @return
	 */
	private FixedVertical convertToDomain(KscstFixedVerticalSet kFixedVerticalSet) {
		FixedVertical fixedVertical = FixedVertical.createFromJavaType(
				kFixedVerticalSet.kscstFixedVerticalSetPK.companyId,
				kFixedVerticalSet.kscstFixedVerticalSetPK.fixedVerticalNo, kFixedVerticalSet.useAtr,
				kFixedVerticalSet.fixedItemAtr, kFixedVerticalSet.verticalDetailedSet);
		return fixedVertical;
	}

	/**
	 * Convert to Domain Vertical Time
	 * @param kVerticalTimeSet
	 * @return
	 */
	private VerticalTime convertToDomainTime(KscstVerticalTimeSet kVerticalTimeSet) {
		VerticalTime verticalTime = VerticalTime.createFromJavaType(kVerticalTimeSet.kscstVerticalTimeSetPK.companyId,
				kVerticalTimeSet.kscstVerticalTimeSetPK.fixedVerticalNo, kVerticalTimeSet.displayAtr,
				kVerticalTimeSet.startClock);
		return verticalTime;
	}

	/**
	 * Find all Fixed Vertical
	 */
	@Override
	public List<FixedVertical> findAll(String companyId) {
		return this.queryProxy().query(SELECT_BY_CID, KscstFixedVerticalSet.class).setParameter("companyId", companyId)
				.getList(c -> convertToDomain(c));
	}

	/**
	 * Convert to Database Type Fixed Vertical
	 * @param fVertical
	 * @return
	 */
	private KscstFixedVerticalSet convertToDbType(FixedVertical fVertical) {
		KscstFixedVerticalSet kVerticalSet = new KscstFixedVerticalSet();
		KscstFixedVerticalSetPK kVerticalSetPK = new KscstFixedVerticalSetPK(
				fVertical.getCompanyId(),
				fVertical.getFixedVerticalNo());
				kVerticalSet.useAtr = fVertical.getUseAtr().value;
				kVerticalSet.fixedItemAtr = fVertical.getFixedItemAtr().value;
				kVerticalSet.verticalDetailedSet = fVertical.getVerticalDetailedSettings().value;
				kVerticalSet.kscstFixedVerticalSetPK = kVerticalSetPK;
		return kVerticalSet;
	}
	
	/**
	 * Add Fixed Vertical
	 */
	@Override
	public void addFixedVertical(FixedVertical fixedVertical) {
		this.commandProxy().insert(convertToDbType(fixedVertical));	
	}

	/**
	 * Update Fixed Vertical
	 */
	@Override
	public void updateFixedVertical(FixedVertical fixedVertical) {
		KscstFixedVerticalSetPK kVerticalSetPK = new KscstFixedVerticalSetPK(fixedVertical.getCompanyId(), fixedVertical.getFixedVerticalNo());
		KscstFixedVerticalSet kVerticalSet = this.queryProxy().find(kVerticalSetPK, KscstFixedVerticalSet.class).get();
		kVerticalSet.useAtr = fixedVertical.getUseAtr().value;
		kVerticalSet.fixedItemAtr = fixedVertical.getFixedItemAtr().value;
		kVerticalSet.verticalDetailedSet = fixedVertical.getVerticalDetailedSettings().value;
		this.commandProxy().update(kVerticalSet);	
	}

	/**
	 * Find all Vertical Time
	 */
	@Override
	public List<VerticalTime> findAllVerticalTime(String companyId, int fixedVerticalNo) {
		return this.queryProxy().query(SELECT_TIME_BY_CID, KscstVerticalTimeSet.class)
				.setParameter("companyId", companyId).setParameter("fixedVerticalNo", fixedVerticalNo)
				.getList(c -> convertToDomainTime(c));
	}

	/**
	 * Convert to Database Type Vertical Time
	 * @param verticalTime
	 * @return
	 */
	private KscstVerticalTimeSet convertToDbTypeTime(VerticalTime verticalTime) {
		KscstVerticalTimeSet kTimeSet = new KscstVerticalTimeSet();
		KscstVerticalTimeSetPK kVerticalSetPK = new KscstVerticalTimeSetPK(
				verticalTime.getCompanyId(),
				verticalTime.getFixedVerticalNo());
				kTimeSet.displayAtr = verticalTime.getDisplayAtr().value;
				kTimeSet.startClock = verticalTime.getStartClock().v();
				kTimeSet.kscstVerticalTimeSetPK = kVerticalSetPK;
		return kTimeSet;
	}
	
	/**
	 * Add Vertical Time
	 */
	@Override
	public void addVerticalTime(VerticalTime verticalTime) {
		this.commandProxy().insert(convertToDbTypeTime(verticalTime));	
	}

	/**
	 * Update Vertical Time
	 */
	@Override
	public void updateVerticalTime(VerticalTime verticalTime) {
		KscstVerticalTimeSetPK kVerticalSetPK = new KscstVerticalTimeSetPK(verticalTime.getCompanyId(), verticalTime.getFixedVerticalNo());
		KscstVerticalTimeSet kTimeSet = this.queryProxy().find(kVerticalSetPK, KscstVerticalTimeSet.class).get();
		kTimeSet.displayAtr = verticalTime.getDisplayAtr().value;
		kTimeSet.startClock = verticalTime.getStartClock().v();
		this.commandProxy().update(kTimeSet);	
	}
	
	/**
	 * Delete Vertical Time
	 */
	public void deleteVerticalTime(String companyId, int fixedVerticalNo){
		KscstVerticalTimeSetPK kVerticalSetPK = new KscstVerticalTimeSetPK(companyId, fixedVerticalNo);
		this.commandProxy().remove(KscstVerticalTimeSet.class, kVerticalSetPK);
	}
}
