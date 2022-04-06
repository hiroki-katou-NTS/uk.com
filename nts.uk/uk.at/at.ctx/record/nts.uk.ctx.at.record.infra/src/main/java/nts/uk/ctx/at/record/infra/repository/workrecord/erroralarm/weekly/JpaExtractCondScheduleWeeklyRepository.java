package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.weekly;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.AddSubAttendanceItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareRange;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareSingleValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CountableTarget;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.ExtractionCondWeekly;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.ExtractionCondScheduleWeeklyRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcmtEralstCndexprange;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlAtdTargetPK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareRange;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareRangePK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareSingle;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareSinglePK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlSingleFixed;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlSingleFixedPK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.weekly.KrcdtWeekCondAlarm;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.weekly.KrcdtWeekCondAlarmPk;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;

@Stateless
public class JpaExtractCondScheduleWeeklyRepository extends JpaRepository implements ExtractionCondScheduleWeeklyRepository {
	private static final String SELECT_BASIC = "SELECT a FROM KrcdtWeekCondAlarm a";
	private static final String BY_CONTRACT_COMPANY = " WHERE a.pk.cid = :companyId AND a.contractCd = :contractCode";
	private static final String BY_ERAL_CHECK_ID = " AND a.pk.checkId = :eralCheckIds";
	private static final String ORDER_BY_NO = " ORDER BY a.pk.condNo";
	private static final String SELECT_COMPARE_RANGE = "SELECT a FROM KrcstErAlCompareRange a WHERE a.krcstEralCompareRangePK.conditionGroupId = :checkId";
	private static final String SELECT_COMPARE_RANGE_SINGLE = "SELECT a FROM KrcstErAlCompareSingle a WHERE a.krcstEralCompareSinglePK.conditionGroupId = :checkId";
	private static final String SELECT_COMPARE_RANGE_SINGLE_FIXED = "SELECT a FROM KrcstErAlSingleFixed a WHERE a.krcstEralSingleFixedPK.conditionGroupId = :checkId";
	private static final String SELECT_CNDEXP_RANGE = "SELECT a FROM KrcmtEralstCndexprange a WHERE a.krcstErAlAtdTargetPK.conditionGroupId = :checkId";
	private static final String CNDEXP_RANGE_BY_NO = " AND a.krcstErAlAtdTargetPK.atdItemConNo = :itemNo";
	private static final String CNDEXP_RANGE_BY_TARGET_ATR = " AND a.targetAtr = :targetAtr";
	private static final String BY_COMPARE_RANGE_NO = " AND a.krcstEralCompareRangePK.atdItemConNo = :atdItemConNo ";
	private static final String BY_COMPARE_RANGE_SINGLE_NO = " AND a.krcstEralCompareSinglePK.atdItemConNo = :atdItemConNo ";
	private static final String BY_COMPARE_RANGE_SINGLE_FIXED_NO = " AND a.krcstEralSingleFixedPK.atdItemConNo = :atdItemConNo ";
	
    @Override
    public List<ExtractionCondWeekly> getAll() {
        List<KrcdtWeekCondAlarm> entities = new ArrayList<>();
        return null;
    }

	@Override
	public List<ExtractionCondWeekly> getScheAnyCond(String contractCode, String companyId,
                                                     String eralCheckIds) {
		List<KrcdtWeekCondAlarm> entities = this.queryProxy().query(SELECT_BASIC + BY_CONTRACT_COMPANY + BY_ERAL_CHECK_ID + ORDER_BY_NO, KrcdtWeekCondAlarm.class)
				.setParameter("contractCode", contractCode)
				.setParameter("companyId", companyId)
				.setParameter("eralCheckIds", eralCheckIds)
				.getList();
        List<ExtractionCondWeekly> domain = new ArrayList<>();
    	for(KrcdtWeekCondAlarm item: entities) {
    		Optional<KrcstErAlCompareSingle> single = this.queryProxy().find(new KrcstErAlCompareSinglePK(eralCheckIds, item.pk.condNo), KrcstErAlCompareSingle.class);
    		Optional<KrcstErAlSingleFixed> singleFixed = this.queryProxy().find(new KrcstErAlSingleFixedPK(eralCheckIds, item.pk.condNo), KrcstErAlSingleFixed.class);
    		Optional<KrcstErAlCompareRange> range = this.queryProxy().find(new KrcstErAlCompareRangePK(eralCheckIds, item.pk.condNo), KrcstErAlCompareRange.class);
    		ExtractionCondWeekly toDomain = item.toDomain(
    				single.isPresent() ? single.get() : null, 
					singleFixed.isPresent() ? singleFixed.get() : null, 
    				range.isPresent() ? range.get() : null);
    		
    		List<KrcmtEralstCndexprange> eralstCndexpranges = this.queryProxy().query(SELECT_CNDEXP_RANGE + CNDEXP_RANGE_BY_NO, KrcmtEralstCndexprange.class)
    				.setParameter("checkId", item.pk.checkId)
    				.setParameter("itemNo", item.pk.condNo)
    				.getList();
    		if (!eralstCndexpranges.isEmpty()) {
    			List<Integer> additionAttendanceItems = eralstCndexpranges.stream()
    					.filter(r -> r.targetAtr == 0)
    					.map(r -> r.krcstErAlAtdTargetPK.attendanceItemId).collect(Collectors.toList());
    			List<Integer> substractionAttendanceItems = eralstCndexpranges.stream()
    					.filter(r -> r.targetAtr == 1)
    					.map(r -> r.krcstErAlAtdTargetPK.attendanceItemId).collect(Collectors.toList());
    			CountableTarget checkedTarget = new CountableTarget(new AddSubAttendanceItems(additionAttendanceItems, substractionAttendanceItems));
    			toDomain.setCheckedTarget(Optional.of(checkedTarget));
    		}
    		
    		domain.add(toDomain);
        }
    	
    	return domain;
	}

	@Override
	public void add(String contractCode, String companyId, ExtractionCondWeekly domain) {
		KrcdtWeekCondAlarm entity = fromDomain(contractCode, companyId, domain);
		
		updateErAlCompare(contractCode, companyId, domain);
		
		this.commandProxy().insert(entity);
	}

	@Override
	public void update(String contractCode, String companyId, ExtractionCondWeekly domain) {
		KrcdtWeekCondAlarmPk pk = new KrcdtWeekCondAlarmPk(companyId, domain.getErrorAlarmId(), domain.getSortOrder());
		Optional<KrcdtWeekCondAlarm> entityOpt = this.queryProxy().find(pk, KrcdtWeekCondAlarm.class);
		
		KrcdtWeekCondAlarm entity = entityOpt.get();
		
		// remove all condition if change check item type
		if (entity.checkType != domain.getCheckItemType().value) {
			removeCheckCondition(contractCode, companyId, entity.pk.checkId, entity.pk.condNo);
		}
				
		entity.condName = domain.getName().v();
		entity.condMsg = domain.getErrorAlarmMessage() != null ? domain.getErrorAlarmMessage().get().v() : null;
		entity.useAtr = domain.isUse();
		entity.checkType = domain.getCheckItemType().value;
		entity.conMonth = domain.getContinuousPeriod() != null && domain.getContinuousPeriod().isPresent() ? domain.getContinuousPeriod().get().v() : null;
		
		updateErAlCompare(contractCode, companyId, domain);
		
		this.commandProxy().update(entity);
	}

	@Override
	public void delete(String contractCode, String companyId, String erAlCheckIds, int alarmNo) {
		removeCheckCondition(contractCode, companyId, erAlCheckIds, alarmNo);
		
		KrcdtWeekCondAlarmPk pk = new KrcdtWeekCondAlarmPk(companyId, erAlCheckIds, alarmNo);
		Optional<KrcdtWeekCondAlarm> entityOpt = this.queryProxy().find(pk, KrcdtWeekCondAlarm.class);
		if (!entityOpt.isPresent()) {
			return;
		}
		this.commandProxy().remove(entityOpt.get());
	}

	@Override
	public void delete(String contractCode, String companyId, String checkId) {
		List<KrcstErAlCompareRange> ranges = this.queryProxy().query(SELECT_COMPARE_RANGE, KrcstErAlCompareRange.class)
				.setParameter("checkId", checkId)
				.getList();
		if (!ranges.isEmpty()) {
			this.commandProxy().removeAll(ranges);
		}
		
		List<KrcstErAlCompareSingle> singleRanges = this.queryProxy().query(SELECT_COMPARE_RANGE_SINGLE, KrcstErAlCompareSingle.class)
				.setParameter("checkId", checkId)
				.getList();
		if (!singleRanges.isEmpty()) {
			this.commandProxy().removeAll(singleRanges);
		}
		
		List<KrcstErAlSingleFixed> singleRangeFixeds = this.queryProxy().query(SELECT_COMPARE_RANGE_SINGLE_FIXED, KrcstErAlSingleFixed.class)
				.setParameter("checkId", checkId)
				.getList();
		if (!singleRangeFixeds.isEmpty()) {
			this.commandProxy().removeAll(singleRangeFixeds);
		}
		
		List<KrcmtEralstCndexprange> eralstCndexpranges = this.queryProxy().query(SELECT_CNDEXP_RANGE, KrcmtEralstCndexprange.class)
				.setParameter("checkId", checkId)
				.getList();
		if (!eralstCndexpranges.isEmpty()) {
			this.commandProxy().removeAll(eralstCndexpranges);
		}
		
		List<KrcdtWeekCondAlarm> entities = this.queryProxy().query(SELECT_BASIC + BY_CONTRACT_COMPANY + BY_ERAL_CHECK_ID, KrcdtWeekCondAlarm.class)
				.setParameter("contractCode", contractCode)
				.setParameter("companyId", companyId)
				.setParameter("eralCheckIds", checkId)
				.getList();
		if (!entities.isEmpty()) {
			this.commandProxy().removeAll(entities);
		}
	}
	
	private KrcdtWeekCondAlarm fromDomain(String contractCode, String companyId, ExtractionCondWeekly domain) {
		KrcdtWeekCondAlarmPk pk = new KrcdtWeekCondAlarmPk(companyId, domain.getErrorAlarmId(), domain.getSortOrder());
		KrcdtWeekCondAlarm entity = new KrcdtWeekCondAlarm(
				pk, domain.getName().v(), 
				domain.isUse(), 
				domain.getCheckItemType().value,
				domain.getContinuousPeriod() != null && domain.getContinuousPeriod().isPresent() ? domain.getContinuousPeriod().get().v() : null,
				domain.getErrorAlarmMessage() != null && domain.getErrorAlarmMessage().isPresent() ? domain.getErrorAlarmMessage().get().v() : "");
		entity.setContractCd(contractCode);
		
		return entity;
	}
	
	/**
	 * The update for MonCheckItemType=Contrast
	 */
	private void updateErAlCompare(String contractCode, String companyId, ExtractionCondWeekly domain) {
		if (domain.getCheckConditions() == null) {
			removeCheckCondition(contractCode, companyId, domain.getErrorAlarmId(), domain.getSortOrder());
			return;
		}

		KrcstErAlCompareRangePK compareRangePK = new KrcstErAlCompareRangePK(domain.getErrorAlarmId(), domain.getSortOrder());
		Optional<KrcstErAlCompareRange> entityCompareRangeOpt = this.queryProxy().find(compareRangePK, KrcstErAlCompareRange.class);
		
		KrcstErAlCompareSinglePK compareSinglePK = new KrcstErAlCompareSinglePK(domain.getErrorAlarmId(), domain.getSortOrder());
		Optional<KrcstErAlCompareSingle> entityCompareRangeSingleOpt = this.queryProxy().find(compareSinglePK, KrcstErAlCompareSingle.class);
		KrcstErAlSingleFixedPK singleFixedPK = new KrcstErAlSingleFixedPK(domain.getErrorAlarmId(), domain.getSortOrder());
		Optional<KrcstErAlSingleFixed> erAlSingleFixedOpt = this.queryProxy().find(singleFixedPK, KrcstErAlSingleFixed.class);
		
		if (domain.getCheckedTarget().isPresent()) {
			val value =  (CountableTarget) domain.getCheckedTarget().get();
            List<Integer> listAdd = value.getAddSubAttendanceItems().getAdditionAttendanceItems();
            List<Integer> listSub = value.getAddSubAttendanceItems().getSubstractionAttendanceItems();
            
            saveEralstCndexprange(domain.getErrorAlarmId(), domain.getSortOrder(), listAdd, 0);
			saveEralstCndexprange(domain.getErrorAlarmId(), domain.getSortOrder(), listSub, 1);
		}
		
		if (domain.getCheckConditions() instanceof CompareRange) {
			CompareRange compareRange = (CompareRange)domain.getCheckConditions();
			
			KrcstErAlCompareRange entityCompareRange = new KrcstErAlCompareRange(compareRangePK);
			if (entityCompareRangeOpt.isPresent()) {
				entityCompareRange = entityCompareRangeOpt.get();
			}
			
			entityCompareRange.compareAtr = compareRange.getCompareOperator().value;
			entityCompareRange.startValue = ((Double)compareRange.getStartValue()).doubleValue();
			entityCompareRange.endValue = ((Double)compareRange.getEndValue()).doubleValue();
			saveOrUpdate(entityCompareRange, entityCompareRangeOpt.isPresent());
			
			// remove compare range single if exist
			if (entityCompareRangeSingleOpt.isPresent()) {
				this.commandProxy().remove(entityCompareRangeSingleOpt.get());
			}
			if (erAlSingleFixedOpt.isPresent()) {
				this.commandProxy().remove(erAlSingleFixedOpt.get());
			}
		} else {
			CompareSingleValue compareSingleRange = (CompareSingleValue)domain.getCheckConditions();
			
			KrcstErAlCompareSingle entityCompareRangeSingle = new KrcstErAlCompareSingle(compareSinglePK);
			if (entityCompareRangeSingleOpt.isPresent()) {
				entityCompareRangeSingle = entityCompareRangeSingleOpt.get();
			}
			
			entityCompareRangeSingle.compareAtr = compareSingleRange.getCompareOpertor().value;
			saveOrUpdate(entityCompareRangeSingle, entityCompareRangeSingleOpt.isPresent());
			
			KrcstErAlSingleFixed erAlSingleFixed = new KrcstErAlSingleFixed(singleFixedPK);
			if (erAlSingleFixedOpt.isPresent()) {
				erAlSingleFixed = erAlSingleFixedOpt.get();
			}
			
			erAlSingleFixed.fixedValue = ((Double)compareSingleRange.getValue()).doubleValue();
			saveOrUpdate(erAlSingleFixed, erAlSingleFixedOpt.isPresent());
			
			// remove compare range if exist
			if (entityCompareRangeOpt.isPresent()) {
				this.commandProxy().remove(entityCompareRangeOpt.get());
			}
		}
	}
	
	/**
	 * 
	 * @param listItem list item add or sub
	 * @param targetAtr add=0, sub=1
	 */
	private void saveEralstCndexprange(String errorAlarmId, int itemNo, List<Integer> listItem, int targetAtr) {
		List<KrcmtEralstCndexprange> ranges = getCndexpRange(errorAlarmId, itemNo, targetAtr);
		
		List<KrcmtEralstCndexprange> targetListAdd = new ArrayList<>();
		for(Integer id: listItem) {
			KrcstErAlAtdTargetPK key = new KrcstErAlAtdTargetPK(errorAlarmId, itemNo, id.intValue());
			Optional<KrcmtEralstCndexprange> rangeOpt = this.queryProxy().find(key, KrcmtEralstCndexprange.class);
			if (!rangeOpt.isPresent()) {
				targetListAdd.add(new KrcmtEralstCndexprange(key, targetAtr));
			}
		}
		
		List<KrcmtEralstCndexprange> targetListRemove = new ArrayList<>();
		for(KrcmtEralstCndexprange item: ranges) {
			if (!listItem.contains(item.krcstErAlAtdTargetPK.attendanceItemId)) {
				targetListRemove.add(item);
			}
		}
		
		if (!targetListAdd.isEmpty()) {
			this.commandProxy().insertAll(targetListAdd);
		}
		
		if (!targetListRemove.isEmpty()) {
			this.commandProxy().removeAll(targetListRemove);
		}
	}
	
	private List<KrcmtEralstCndexprange> getCndexpRange(String errorAlarmId, int itemNo, int targetAtr) {
		return this.queryProxy().query(SELECT_CNDEXP_RANGE + CNDEXP_RANGE_BY_NO + CNDEXP_RANGE_BY_TARGET_ATR, KrcmtEralstCndexprange.class)
				.setParameter("checkId", errorAlarmId)
				.setParameter("itemNo", itemNo)
				.setParameter("targetAtr", targetAtr)
				.getList();
	}
	
	/**
	 * Remove check condition when change check type item or remove item
	 * @param contractCode
	 * @param companyId
	 * @param erAlCheckIds
	 * @param alarmNo
	 */
	private void removeCheckCondition(String contractCode, String companyId, String erAlCheckIds, int alarmNo) {
		List<KrcstErAlCompareRange> ranges = this.queryProxy().query(SELECT_COMPARE_RANGE + BY_COMPARE_RANGE_NO, KrcstErAlCompareRange.class)
				.setParameter("checkId", erAlCheckIds)
				.setParameter("atdItemConNo", alarmNo)
				.getList();
		if (!ranges.isEmpty()) {
			this.commandProxy().removeAll(ranges);
		}
		
		List<KrcstErAlCompareSingle> singleRanges = this.queryProxy().query(SELECT_COMPARE_RANGE_SINGLE + BY_COMPARE_RANGE_SINGLE_NO, KrcstErAlCompareSingle.class)
				.setParameter("checkId", erAlCheckIds)
				.setParameter("atdItemConNo", alarmNo)
				.getList();
		if (!singleRanges.isEmpty()) {
			this.commandProxy().removeAll(singleRanges);
		}
		
		List<KrcstErAlSingleFixed> singleRangeFixeds = this.queryProxy().query(SELECT_COMPARE_RANGE_SINGLE_FIXED + BY_COMPARE_RANGE_SINGLE_FIXED_NO, KrcstErAlSingleFixed.class)
				.setParameter("checkId", erAlCheckIds)
				.setParameter("atdItemConNo", alarmNo)
				.getList();
		if (!singleRangeFixeds.isEmpty()) {
			this.commandProxy().removeAll(singleRangeFixeds);
		}
		
		List<KrcmtEralstCndexprange> eralstCndexpranges = this.queryProxy().query(SELECT_CNDEXP_RANGE + CNDEXP_RANGE_BY_NO, KrcmtEralstCndexprange.class)
				.setParameter("checkId", erAlCheckIds)
				.setParameter("itemNo", alarmNo)
				.getList();
		if (!eralstCndexpranges.isEmpty()) {
			this.commandProxy().removeAll(eralstCndexpranges);
		}
	}
	
	private void saveOrUpdate(Object entity, boolean isUpdate) {
		if (isUpdate) {
			this.commandProxy().update(entity);
		} else {
			this.commandProxy().insert(entity);
		}
	}
}
