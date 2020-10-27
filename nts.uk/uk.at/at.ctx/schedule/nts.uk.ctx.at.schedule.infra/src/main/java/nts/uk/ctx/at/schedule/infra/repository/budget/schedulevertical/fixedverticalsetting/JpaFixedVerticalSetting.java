package nts.uk.ctx.at.schedule.infra.repository.budget.schedulevertical.fixedverticalsetting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.FixedVertical;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.FixedVerticalSettingRepository;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.VerticalCnt;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.VerticalTime;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.fixedverticalsetting.KscstFixedVertSet;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.fixedverticalsetting.KscstFixedVertSetPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.fixedverticalsetting.KscmtVerticalCntAgg;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.fixedverticalsetting.KscmtVerticalCntAggPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.fixedverticalsetting.KscmtVerticalTs;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.fixedverticalsetting.KscmtVerticalTsPK;


/**
 * 
 * @author phongtq
 *
 */

@Stateless
public class JpaFixedVerticalSetting extends JpaRepository implements FixedVerticalSettingRepository {

	private static final String SELECT_BY_CID;

	private static final String SELECT_TIME_BY_CID;
	
	private static final String REMOVE_TIME_BY_CID;
	
	private static final String REMOVE_COUNT_BY_CID;
	
	private static final String SELECT_CNT_BY_CID;
	static {

		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscstFixedVertSet e");
		builderString.append(" WHERE e.kscstFixedVerticalSetPK.companyId = :companyId");
		SELECT_BY_CID = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscmtVerticalTs e");
		builderString.append(" WHERE e.kscstVerticalTimeSetPK.companyId = :companyId");
		builderString.append(" AND e.kscstVerticalTimeSetPK.fixedItemAtr = :fixedItemAtr");
		builderString.append(" ORDER BY e.startClock ASC");
		SELECT_TIME_BY_CID = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("DELETE FROM KscmtVerticalTs e");
		builderString.append(" WHERE e.kscstVerticalTimeSetPK.companyId = :companyId");
		builderString.append(" AND e.kscstVerticalTimeSetPK.fixedItemAtr = :fixedItemAtr");
		REMOVE_TIME_BY_CID = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("DELETE FROM KscmtVerticalCntAgg e");
		builderString.append(" WHERE e.kscstVerticalCntSetPK.companyId = :companyId");
		builderString.append(" AND e.kscstVerticalCntSetPK.fixedItemAtr = :fixedItemAtr");
		REMOVE_COUNT_BY_CID = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscmtVerticalCntAgg e");
		builderString.append(" WHERE e.kscstVerticalCntSetPK.companyId = :companyId");
		builderString.append(" AND e.kscstVerticalCntSetPK.fixedItemAtr = :fixedItemAtr");
		SELECT_CNT_BY_CID = builderString.toString();
	}

	/**
	 * Convert to Domain Fixed Vertical
	 * @param kFixedVerticalSet
	 * @return
	 */
	private FixedVertical convertToDomain(KscstFixedVertSet kFixedVerticalSet) {
		FixedVertical fixedVertical = FixedVertical.createFromJavaType(
				kFixedVerticalSet.kscstFixedVerticalSetPK.companyId,
				kFixedVerticalSet.kscstFixedVerticalSetPK.fixedItemAtr, 
				kFixedVerticalSet.useAtr);
		return fixedVertical;
	}

	/**
	 * Convert to Domain Vertical Time
	 * @param kVerticalTimeSet
	 * @return
	 */
	private VerticalTime convertToDomainTime(KscmtVerticalTs kVerticalTimeSet) {
		VerticalTime verticalTime = VerticalTime.createFromJavaType(kVerticalTimeSet.kscstVerticalTimeSetPK.companyId,
				kVerticalTimeSet.kscstVerticalTimeSetPK.fixedItemAtr,
				kVerticalTimeSet.kscstVerticalTimeSetPK.verticalTimeNo,
				kVerticalTimeSet.displayAtr,
				kVerticalTimeSet.startClock);
		return verticalTime;
	}

	/**
	 * 
	 * @param kVerticalTimeSet
	 * @return
	 */
	private VerticalCnt convertToDomainCnt(KscmtVerticalCntAgg kVerticalCntSet) {
		VerticalCnt verticalCnt = VerticalCnt.createFromJavaType(kVerticalCntSet.kscstVerticalCntSetPK.companyId,
				kVerticalCntSet.kscstVerticalCntSetPK.fixedItemAtr,
				kVerticalCntSet.kscstVerticalCntSetPK.verticalCountNo);
		return verticalCnt;
	}
	
	/**
	 * Find all Fixed Vertical
	 */
	@Override
	public List<FixedVertical> findAll(String companyId) {
		return this.queryProxy().query(SELECT_BY_CID, KscstFixedVertSet.class).setParameter("companyId", companyId)
				.getList(c -> convertToDomain(c));
	}
	
	/**
	 * Find all Fixed Vertical
	 */
	
	@Override
	public List<VerticalCnt> findAllCnt(String companyId, int fixedItemAtr) {
		return this.queryProxy().query(SELECT_CNT_BY_CID, KscmtVerticalCntAgg.class).setParameter("companyId", companyId)
				.setParameter("fixedItemAtr", fixedItemAtr)
				.getList(c -> convertToDomainCnt(c));
	}

	/**
	 * Convert to Database Type Fixed Vertical
	 * @param fVertical
	 * @return
	 */
	private KscstFixedVertSet convertToDbType(FixedVertical fVertical) {
		KscstFixedVertSet kVerticalSet = new KscstFixedVertSet();
		KscstFixedVertSetPK kVerticalSetPK = new KscstFixedVertSetPK(
				fVertical.getCompanyId(),
				fVertical.getFixedItemAtr().value);
				kVerticalSet.useAtr = fVertical.getUseAtr().value;
				kVerticalSet.kscstFixedVerticalSetPK = kVerticalSetPK;
		return kVerticalSet;
	}
	
	@Override
	public Optional<FixedVertical> find(String companyId, int fixedItemAtr) {
		KscstFixedVertSetPK primaryKey = new KscstFixedVertSetPK(companyId,fixedItemAtr);
		return this.queryProxy().find(primaryKey, KscstFixedVertSet.class)
				.map(x -> convertToDomain(x));
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
		KscstFixedVertSetPK kVerticalSetPK = new KscstFixedVertSetPK(fixedVertical.getCompanyId(), fixedVertical.getFixedItemAtr().value);
		KscstFixedVertSet kVerticalSet = this.queryProxy().find(kVerticalSetPK, KscstFixedVertSet.class).get();
		kVerticalSet.useAtr = fixedVertical.getUseAtr().value;
		this.commandProxy().update(kVerticalSet);	
	}

	/**
	 * Find all Vertical Time
	 */
	@Override
	public List<VerticalTime> findAllVerticalTime(String companyId, int fixedItemAtr) {
		return this.queryProxy().query(SELECT_TIME_BY_CID, KscmtVerticalTs.class)
				.setParameter("companyId", companyId).setParameter("fixedItemAtr", fixedItemAtr)
				.getList(c -> convertToDomainTime(c));
	}

	/**
	 * Convert to Database Type Vertical Time
	 * @param verticalTime
	 * @return
	 */
	private KscmtVerticalTs convertToDbTypeTime(VerticalTime verticalTime) {
		KscmtVerticalTs kTimeSet = new KscmtVerticalTs();
		KscmtVerticalTsPK kVerticalSetPK = new KscmtVerticalTsPK(
				verticalTime.getCompanyId(),
				verticalTime.getFixedItemAtr().value,
				verticalTime.getVerticalTimeNo());
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
	 * 
	 * @param verticalCnt
	 * @return
	 */
	private KscmtVerticalCntAgg convertToDbTypeCnt(VerticalCnt verticalCnt) {
		KscmtVerticalCntAgg kCntSet= new KscmtVerticalCntAgg();
		KscmtVerticalCntAggPK kVerticalSetPK = new KscmtVerticalCntAggPK(
				verticalCnt.getCompanyId(),
				verticalCnt.getFixedItemAtr(),
				verticalCnt.getVerticalCountNo());
		kCntSet.kscstVerticalCntSetPK = kVerticalSetPK;
		return kCntSet;
	}
	
	/**
	 * Add vertical CNT Set
	 */
	@Override
	public void addVerticalCnt(VerticalCnt verticalCnt) {
		this.commandProxy().insert(convertToDbTypeCnt(verticalCnt));	
	}

	/**
	 * Update Vertical Time
	 */
	@Override
	public void updateVerticalTime(VerticalTime verticalTime) {
		KscmtVerticalTsPK kVerticalSetPK = new KscmtVerticalTsPK(verticalTime.getCompanyId(), verticalTime.getFixedItemAtr().value,verticalTime.getVerticalTimeNo());
		KscmtVerticalTs kTimeSet = this.queryProxy().find(kVerticalSetPK, KscmtVerticalTs.class).get();
		kTimeSet.displayAtr = verticalTime.getDisplayAtr().value;
		this.commandProxy().update(kTimeSet);	
	}
	

	/**
	 * Delete Vertical Time
	 */
	@Override
	public void deleteVerticalTime(String companyId, int fixedItemAtr) {
		this.getEntityManager().createQuery(REMOVE_TIME_BY_CID)
			.setParameter("companyId", companyId).setParameter("fixedItemAtr", fixedItemAtr)
			.executeUpdate();
	}
	
	/**
	 * Delete Vertical Cnt
	 */
	@Override
	public void deleteCount(String companyId, int fixedItemAtr) {
		this.getEntityManager().createQuery(REMOVE_COUNT_BY_CID)
			.setParameter("companyId", companyId).setParameter("fixedItemAtr", fixedItemAtr)
			.executeUpdate();
	}

	
}
