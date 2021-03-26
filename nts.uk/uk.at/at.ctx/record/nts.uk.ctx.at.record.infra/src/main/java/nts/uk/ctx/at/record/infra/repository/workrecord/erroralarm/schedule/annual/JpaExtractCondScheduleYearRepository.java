package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.schedule.annual;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareRange;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareSingleValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual.ExtractionCondScheduleYear;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual.ExtractionCondScheduleYearRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual.TimeCheckCond;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual.YearCheckItemType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual.DayCheckCond;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareRange;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareRangePK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareSingle;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareSinglePK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlSingleFixed;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlSingleFixedPK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.annual.KscdtScheAnyCondYear;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.annual.KscdtScheAnyCondYearPk;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.monthly.KscdtScheAnyCondMonth;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.monthly.KscdtScheAnyCondMonthPk;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaExtractCondScheduleYearRepository extends JpaRepository implements ExtractionCondScheduleYearRepository {
	private static final String SELECT_BASIC = "SELECT a FROM KscdtScheAnyCondYear a";
	private static final String BY_CONTRACT_COMPANY = " WHERE a.pk.cid = :companyId AND a.contractCd = :contractCode";
	private static final String BY_ERAL_CHECK_ID = " AND a.pk.cid = :companyId AND a.contractCd = :contractCode AND a.pk.checkId = :eralCheckIds";
	private static final String ORDER_BY_NO = " ORDER BY a.pk.sortBy";
	private static final String SELECT_COMPARE_RANGE = "SELECT a FROM KrcstErAlCompareRange a WHERE a.krcstEralCompareRangePK.conditionGroupId = :checkId";
	private static final String SELECT_COMPARE_RANGE_SINGLE = "SELECT a FROM KrcstErAlCompareSingle a WHERE a.krcstEralCompareSinglePK.conditionGroupId = :checkId";
	private static final String SELECT_COMPARE_RANGE_SINGLE_FIXED = "SELECT a FROM KrcstErAlSingleFixed a WHERE a.krcstEralSingleFixedPK.conditionGroupId = :checkId";
	private static final String BY_COMPARE_RANGE_NO = " AND a.krcstEralCompareRangePK.atdItemConNo = :atdItemConNo ";
	private static final String BY_COMPARE_RANGE_SINGLE_NO = " AND a.krcstEralCompareSinglePK.atdItemConNo = :atdItemConNo ";
	private static final String BY_COMPARE_RANGE_SINGLE_FIXED_NO = " AND a.krcstEralSingleFixedPK.atdItemConNo = :atdItemConNo ";
	
    @Override
    public List<ExtractionCondScheduleYear> getAll() {
        List<KscdtScheAnyCondYear> entities = new ArrayList<>();
        return null;
    }

	@Override
	public List<ExtractionCondScheduleYear> getScheAnyCond(String contractCode, String companyId, String eralCheckIds) {
		List<KscdtScheAnyCondYear> entities = this.queryProxy().query(SELECT_BASIC + BY_CONTRACT_COMPANY + BY_ERAL_CHECK_ID + ORDER_BY_NO, KscdtScheAnyCondYear.class)
				.setParameter("contractCode", contractCode)
				.setParameter("companyId", companyId)
				.setParameter("eralCheckIds", eralCheckIds)
				.getList();
        List<ExtractionCondScheduleYear> domain = new ArrayList<>();
    	for(KscdtScheAnyCondYear item: entities) {
    		Optional<KrcstErAlCompareSingle> single = this.queryProxy().find(new KrcstErAlCompareSinglePK(eralCheckIds, item.pk.sortBy), KrcstErAlCompareSingle.class);
    		Optional<KrcstErAlSingleFixed> singleFixed = this.queryProxy().find(new KrcstErAlSingleFixedPK(eralCheckIds, item.pk.sortBy), KrcstErAlSingleFixed.class);
    		Optional<KrcstErAlCompareRange> range = this.queryProxy().find(new KrcstErAlCompareRangePK(eralCheckIds, item.pk.sortBy), KrcstErAlCompareRange.class);
    		domain.add(item.toDomain(
    				single.isPresent() ? single.get() : null, 
					singleFixed.isPresent() ? singleFixed.get() : null, 
    				range.isPresent() ? range.get() : null));
        }
    	
    	return domain;
	}

	@Override
	public void add(String contractCode, String companyId, ExtractionCondScheduleYear domain) {
		KscdtScheAnyCondYear entity = fromDomain(contractCode, companyId, domain);
		
		updateByCheckCondition(contractCode, companyId, domain, entity);
		
		this.commandProxy().insert(entity);
	}

	@Override
	public void update(String contractCode, String companyId, ExtractionCondScheduleYear domain) {
		KscdtScheAnyCondYearPk pk = new KscdtScheAnyCondYearPk(companyId, domain.getErrorAlarmId(), domain.getSortOrder());
		Optional<KscdtScheAnyCondYear> entityOpt = this.queryProxy().find(pk, KscdtScheAnyCondYear.class);
		
		KscdtScheAnyCondYear entity = entityOpt.get();
		
		// remove all condition if change check item type
		if (entity.condType != domain.getCheckItemType().value) {
			removeCheckCondition(contractCode, companyId, entity.pk.checkId, entity.pk.sortBy);
		}
				
		entity.condName = domain.getName() != null ? domain.getName().v() : "";
		entity.condMsg = domain.getErrorAlarmMessage() != null && domain.getErrorAlarmMessage().isPresent() ? domain.getErrorAlarmMessage().get().v() : null;
		entity.useAtr = domain.isUse();
		entity.condType = domain.getCheckItemType().value;
		
		updateByCheckCondition(contractCode, companyId, domain, entity);
		
		this.commandProxy().update(entity);
	}

	@Override
	public void delete(String contractCode, String companyId, String erAlCheckIds, int alarmNo) {
		removeCheckCondition(contractCode, companyId, erAlCheckIds, alarmNo);
		
		KscdtScheAnyCondYearPk pk = new KscdtScheAnyCondYearPk(companyId, erAlCheckIds, alarmNo);
		Optional<KscdtScheAnyCondYear> entityOpt = this.queryProxy().find(pk, KscdtScheAnyCondYear.class);
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
		
		List<KscdtScheAnyCondYear> entities = this.queryProxy().query(SELECT_BASIC + BY_CONTRACT_COMPANY + BY_ERAL_CHECK_ID, KscdtScheAnyCondYear.class)
				.setParameter("contractCode", contractCode)
				.setParameter("companyId", companyId)
				.setParameter("eralCheckIds", checkId)
				.getList();
		if (!entities.isEmpty()) {
			this.commandProxy().removeAll(entities);
		}
	}
	
	private KscdtScheAnyCondYear fromDomain(String contractCode, String companyId, ExtractionCondScheduleYear domain) {
		KscdtScheAnyCondYearPk pk = new KscdtScheAnyCondYearPk(companyId, domain.getErrorAlarmId(), domain.getSortOrder());
		KscdtScheAnyCondYear entity = new KscdtScheAnyCondYear(
				pk, domain.isUse(), domain.getName().v(), 
				domain.getCheckItemType().value,
				0,
				domain.getErrorAlarmMessage() != null ? domain.getErrorAlarmMessage().get().v() : "");
		entity.setContractCd(contractCode);
		
		return entity;
	}
	
	private void updateByCheckCondition(String contractCode, String companyId, ExtractionCondScheduleYear domain, KscdtScheAnyCondYear entity) {
		YearCheckItemType checkItemType = domain.getCheckItemType();
		switch(checkItemType) {
			case TIME:
				TimeCheckCond publicHolidayCheckCond = (TimeCheckCond)domain.getScheCheckConditions();
				entity.checkType = publicHolidayCheckCond.getTypeOfTime().value;
				break;
			case DAY_NUMBER:
				DayCheckCond timeCheckCond = (DayCheckCond)domain.getScheCheckConditions();
				entity.checkType = timeCheckCond.getTypeOfDays().value;
				break;
			default:
				break;
		}
		
		updateErAlCompare(contractCode, companyId, domain);
	}
	
	/**
	 * The update for MonCheckItemType=Contrast
	 */
	private void updateErAlCompare(String contractCode, String companyId, ExtractionCondScheduleYear domain) {		
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
	}
	
	private void saveOrUpdate(Object entity, boolean isUpdate) {
		if (isUpdate) {
			this.commandProxy().update(entity);
		} else {
			this.commandProxy().insert(entity);
		}
	}
}
