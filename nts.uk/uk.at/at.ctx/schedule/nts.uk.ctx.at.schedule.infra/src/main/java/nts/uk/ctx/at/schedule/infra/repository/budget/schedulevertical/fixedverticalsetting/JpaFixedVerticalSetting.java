package nts.uk.ctx.at.schedule.infra.repository.budget.schedulevertical.fixedverticalsetting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.FixedVertical;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.FixedVerticalSettingRepository;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.VerticalCnt;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.VerticalTime;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.fixedverticalsetting.KscstFixedVerticalSet;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.fixedverticalsetting.KscstFixedVerticalSetPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.fixedverticalsetting.KscstVerticalCntSet;
import nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.fixedverticalsetting.KscstVerticalCntSetPK;
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
	
	private static final String REMOVE_TIME_BY_CID;
	
	private static final String REMOVE_COUNT_BY_CID;
	
	private static final String SELECT_CNT_BY_CID;
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
		builderString.append(" AND e.kscstVerticalTimeSetPK.fixedItemAtr = :fixedItemAtr");
		builderString.append(" ORDER BY e.startClock ASC");
		SELECT_TIME_BY_CID = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("DELETE FROM KscstVerticalTimeSet e");
		builderString.append(" WHERE e.kscstVerticalTimeSetPK.companyId = :companyId");
		builderString.append(" AND e.kscstVerticalTimeSetPK.fixedItemAtr = :fixedItemAtr");
		REMOVE_TIME_BY_CID = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("DELETE FROM KscstVerticalCntSet e");
		builderString.append(" WHERE e.kscstVerticalCntSetPK.companyId = :companyId");
		builderString.append(" AND e.kscstVerticalCntSetPK.fixedItemAtr = :fixedItemAtr");
		REMOVE_COUNT_BY_CID = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscstVerticalCntSet e");
		builderString.append(" WHERE e.kscstVerticalCntSetPK.companyId = :companyId");
		builderString.append(" AND e.kscstVerticalCntSetPK.fixedItemAtr = :fixedItemAtr");
		SELECT_CNT_BY_CID = builderString.toString();
	}

	/**
	 * Convert to Domain Fixed Vertical
	 * @param kFixedVerticalSet
	 * @return
	 */
	private FixedVertical convertToDomain(KscstFixedVerticalSet kFixedVerticalSet) {
		FixedVertical fixedVertical = FixedVertical.createFromJavaType(
				kFixedVerticalSet.kscstFixedVerticalSetPK.companyId,
				kFixedVerticalSet.kscstFixedVerticalSetPK.fixedItemAtr, 
				kFixedVerticalSet.useAtr,
				kFixedVerticalSet.verticalDetailedSet);
		return fixedVertical;
	}

	/**
	 * Convert to Domain Vertical Time
	 * @param kVerticalTimeSet
	 * @return
	 */
	private VerticalTime convertToDomainTime(KscstVerticalTimeSet kVerticalTimeSet) {
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
	private VerticalCnt convertToDomainCnt(KscstVerticalCntSet kVerticalCntSet) {
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
		return this.queryProxy().query(SELECT_BY_CID, KscstFixedVerticalSet.class).setParameter("companyId", companyId)
				.getList(c -> convertToDomain(c));
	}
	
	/**
	 * Find all Fixed Vertical
	 */
	
	@Override
	public List<VerticalCnt> findAllCnt(String companyId, int fixedItemAtr) {
		return this.queryProxy().query(SELECT_CNT_BY_CID, KscstVerticalCntSet.class).setParameter("companyId", companyId)
				.setParameter("fixedItemAtr", fixedItemAtr)
				.getList(c -> convertToDomainCnt(c));
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
				fVertical.getFixedItemAtr().value);
				kVerticalSet.useAtr = fVertical.getUseAtr().value;
				kVerticalSet.verticalDetailedSet = fVertical.getVerticalDetailedSettings().value;
				kVerticalSet.kscstFixedVerticalSetPK = kVerticalSetPK;
		return kVerticalSet;
	}
	
	@Override
	public Optional<FixedVertical> find(String companyId, int fixedItemAtr) {
		KscstFixedVerticalSetPK primaryKey = new KscstFixedVerticalSetPK(companyId,fixedItemAtr);
		return this.queryProxy().find(primaryKey, KscstFixedVerticalSet.class)
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
		KscstFixedVerticalSetPK kVerticalSetPK = new KscstFixedVerticalSetPK(fixedVertical.getCompanyId(), fixedVertical.getFixedItemAtr().value);
		KscstFixedVerticalSet kVerticalSet = this.queryProxy().find(kVerticalSetPK, KscstFixedVerticalSet.class).get();
		kVerticalSet.useAtr = fixedVertical.getUseAtr().value;
		kVerticalSet.verticalDetailedSet = fixedVertical.getVerticalDetailedSettings().value;
		this.commandProxy().update(kVerticalSet);	
	}

	/**
	 * Find all Vertical Time
	 */
	@Override
	public List<VerticalTime> findAllVerticalTime(String companyId, int fixedItemAtr) {
		return this.queryProxy().query(SELECT_TIME_BY_CID, KscstVerticalTimeSet.class)
				.setParameter("companyId", companyId).setParameter("fixedItemAtr", fixedItemAtr)
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
	private KscstVerticalCntSet convertToDbTypeCnt(VerticalCnt verticalCnt) {
		KscstVerticalCntSet kCntSet= new KscstVerticalCntSet();
		KscstVerticalCntSetPK kVerticalSetPK = new KscstVerticalCntSetPK(
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
		KscstVerticalTimeSetPK kVerticalSetPK = new KscstVerticalTimeSetPK(verticalTime.getCompanyId(), verticalTime.getFixedItemAtr().value,verticalTime.getVerticalTimeNo());
		KscstVerticalTimeSet kTimeSet = this.queryProxy().find(kVerticalSetPK, KscstVerticalTimeSet.class).get();
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
