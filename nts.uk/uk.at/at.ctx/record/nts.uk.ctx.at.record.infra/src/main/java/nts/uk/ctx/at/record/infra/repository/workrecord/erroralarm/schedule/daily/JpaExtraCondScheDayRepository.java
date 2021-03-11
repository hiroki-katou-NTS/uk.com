package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.schedule.daily;


import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareRange;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareSingleValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.CondContinuousTime;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.CondContinuousTimeZone;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.CondContinuousWrkType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.CondTime;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.DaiCheckItemType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.ExtraCondScheDayRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.ExtractionCondScheduleDay;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.FixedExtractionSDailyCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.RangeToCheck;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.ScheduleCheckCond;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareRange;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareRangePK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareSingle;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareSinglePK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlSingleFixed;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlSingleFixedPK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.daily.KscdtScheAnyCondDay;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.daily.KscdtScheAnyCondDayPk;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.daily.KscdtScheConDayWt;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.daily.KscdtScheConDayWtime;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.daily.KscdtScheCondDayWtPk;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.daily.KscdtScheCondDayWtimePk;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.daily.KscdtScheFixCondDay;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.daily.KscdtScheFixCondDayPk;

import javax.ejb.Stateless;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaExtraCondScheDayRepository extends JpaRepository implements ExtraCondScheDayRepository {
	private static final String SELECT_BASIC = "SELECT a FROM KscdtScheAnyCondDay a";
	private static final String BY_CONTRACT_COMPANY = " WHERE a.pk.cid = :companyId AND a.contractCd = :contractCode";
	private static final String BY_ERAL_CHECK_ID = " AND a.pk.cid = :companyId AND a.contractCd = :contractCode AND a.pk.checkId = :eralCheckIds";
	
    @Override
    public List<ExtractionCondScheduleDay> getAll(String cid) {
        List<KscdtScheAnyCondDay> entities = new ArrayList<>();

        return null;
    }
    
    @Override
	public List<ExtractionCondScheduleDay> getScheAnyCondDay(String contractCode, String companyId) {
    	List<KscdtScheAnyCondDay> entities = this.queryProxy().query(SELECT_BASIC + BY_CONTRACT_COMPANY, KscdtScheAnyCondDay.class)
				.setParameter("contractCode", contractCode)
				.setParameter("companyId", companyId)
				.getList();
//        return entities.stream().map(item -> item.toDomain()).collect(Collectors.toList());
    	return new ArrayList<>();
	}
    
    @Override
	public List<ExtractionCondScheduleDay> getScheAnyCondDay(String contractCode, String companyId, String eralCheckIds) {
    	List<KscdtScheAnyCondDay> entities = this.queryProxy().query(SELECT_BASIC + BY_CONTRACT_COMPANY + BY_ERAL_CHECK_ID, KscdtScheAnyCondDay.class)
				.setParameter("contractCode", contractCode)
				.setParameter("companyId", companyId)
				.setParameter("eralCheckIds", eralCheckIds)
				.getList();
        return entities.stream()
        		.map(item -> ExtractionCondScheduleDay.create(
        				item.pk.checkId, item.pk.sortBy, item.useAtr, item.condName, item.message, 
        				EnumAdaptor.valueOf(item.checkType, DaiCheckItemType.class),
        				EnumAdaptor.valueOf(item.wrkTypeCondAtr, RangeToCheck.class)))
        		.collect(Collectors.toList());
	}

	@Override
	public void add(String contractCode, String companyId, ExtractionCondScheduleDay domain) {
		KscdtScheAnyCondDay entity = fromDomain(contractCode, companyId, domain);
		
		// 勤務種類 - pattern D
		if (DaiCheckItemType.TIME == domain.getCheckItemType()) {
			// pattern D
			CondTime time = (CondTime)domain.getScheduleCheckCond();
			
			// 
			List<KscdtScheConDayWt> condWorkType = time.getWrkTypeCds().stream()
					.map(code -> new KscdtScheConDayWt(new KscdtScheCondDayWtPk(companyId, domain.getErrorAlarmId(), domain.getSortOrder(), code)))
					.collect(Collectors.toList());
			entity.conditionDayWorkTypes = condWorkType;
			
			// 
			if (time.getCheckedCondition() instanceof CompareRange) {
				CompareRange compareRange = (CompareRange)time.getCheckedCondition();
				KrcstErAlCompareRange entityCompareRange = new KrcstErAlCompareRange(
						new KrcstErAlCompareRangePK(domain.getErrorAlarmId(), domain.getSortOrder()),
						compareRange.getCompareOperator().value,
						((BigDecimal)compareRange.getStartValue()).doubleValue(),
						((BigDecimal)compareRange.getEndValue()).doubleValue());
				this.getEntityManager().persist(entityCompareRange);
			} else {
				CompareSingleValue compareSingleRange = (CompareSingleValue)time.getCheckedCondition();
				KrcstErAlCompareSingle entityCompareRange = new KrcstErAlCompareSingle(
						new KrcstErAlCompareSinglePK(domain.getErrorAlarmId(), domain.getSortOrder()),
						compareSingleRange.getCompareOpertor().value,
						compareSingleRange.getConditionType().value);
				this.getEntityManager().persist(entityCompareRange);
				
				KrcstErAlSingleFixed erAlSingleFixed = new KrcstErAlSingleFixed(
						new KrcstErAlSingleFixedPK(domain.getErrorAlarmId(), domain.getSortOrder()),
						((BigDecimal)compareSingleRange.getValue()).doubleValue());
				this.getEntityManager().persist(erAlSingleFixed);
			}
		}
		
		// pattern G
		if (DaiCheckItemType.CONTINUOUS_TIME == domain.getCheckItemType()) {
			CondContinuousTime continuousTime = (CondContinuousTime)domain.getScheduleCheckCond();
			
			//
			entity.conPeriod = continuousTime.getPeriod().v();
			
			// work type
			List<KscdtScheConDayWt> condWorkType = continuousTime.getWrkTypeCds().stream()
					.map(code -> new KscdtScheConDayWt(new KscdtScheCondDayWtPk(companyId, domain.getErrorAlarmId(), domain.getSortOrder(), code)))
					.collect(Collectors.toList());
			entity.conditionDayWorkTypes = condWorkType;
			
			// 
			if (continuousTime.getCheckedCondition() instanceof CompareRange) {
				CompareRange compareRange = (CompareRange)continuousTime.getCheckedCondition();
				KrcstErAlCompareRange entityCompareRange = new KrcstErAlCompareRange(
						new KrcstErAlCompareRangePK(domain.getErrorAlarmId(), domain.getSortOrder()),
						compareRange.getCompareOperator().value,
						((BigDecimal)compareRange.getStartValue()).doubleValue(),
						((BigDecimal)compareRange.getEndValue()).doubleValue());
				this.getEntityManager().persist(entityCompareRange);
			} else {
				CompareSingleValue compareSingleRange = (CompareSingleValue)continuousTime.getCheckedCondition();
				KrcstErAlCompareSingle entityCompareRange = new KrcstErAlCompareSingle(
						new KrcstErAlCompareSinglePK(domain.getErrorAlarmId(), domain.getSortOrder()),
						compareSingleRange.getCompareOpertor().value,
						compareSingleRange.getConditionType().value);
				this.getEntityManager().persist(entityCompareRange);
				
				KrcstErAlSingleFixed erAlSingleFixed = new KrcstErAlSingleFixed(
						new KrcstErAlSingleFixedPK(domain.getErrorAlarmId(), domain.getSortOrder()),
						((BigDecimal)compareSingleRange.getValue()).doubleValue());
				this.getEntityManager().persist(erAlSingleFixed);
			}
		}
		
		// 連続時間帯の場合
		if (DaiCheckItemType.CONTINUOUS_TIMEZONE == domain.getCheckItemType()) {
			CondContinuousTimeZone continuousTimeZone = (CondContinuousTimeZone)domain.getScheduleCheckCond();
			
			entity.conPeriod = continuousTimeZone.getPeriod().v();
			
			// work type
			List<KscdtScheConDayWt> condWorkType = continuousTimeZone.getWrkTypeCds().stream()
					.map(code -> new KscdtScheConDayWt(new KscdtScheCondDayWtPk(companyId, domain.getErrorAlarmId(), domain.getSortOrder(), code)))
					.collect(Collectors.toList());
			entity.conditionDayWorkTypes = condWorkType;
			
			// work time
			List<KscdtScheConDayWtime> condWorkTime = continuousTimeZone.getWrkTimeCds().stream()
					.map(code -> new KscdtScheConDayWtime(new KscdtScheCondDayWtimePk(companyId, domain.getErrorAlarmId(), domain.getSortOrder(), code)))
					.collect(Collectors.toList());
			entity.conditionDayWorkTimes = condWorkTime;
		}
		
		// 連続勤務の場合
		if (DaiCheckItemType.CONTINUOUS_WORK == domain.getCheckItemType()) {
			CondContinuousWrkType continuousWorkType = (CondContinuousWrkType)domain.getScheduleCheckCond();
			
			entity.conPeriod = continuousWorkType.getPeriod().v();
			
			// work type
			List<KscdtScheConDayWt> condWorkType = continuousWorkType.getWrkTypeCds().stream()
					.map(code -> new KscdtScheConDayWt(new KscdtScheCondDayWtPk(companyId, domain.getErrorAlarmId(), domain.getSortOrder(), code)))
					.collect(Collectors.toList());
			entity.conditionDayWorkTypes = condWorkType;
		}
		
				
		this.commandProxy().insert(entity);
	}

	@Override
	public void update(String contractCode, String companyId, ExtractionCondScheduleDay domain) {
		KscdtScheAnyCondDayPk pk = new KscdtScheAnyCondDayPk(companyId, domain.getErrorAlarmId(), domain.getSortOrder());
		Optional<KscdtScheAnyCondDay> entityOpt = this.queryProxy().find(pk, KscdtScheAnyCondDay.class);
		KscdtScheAnyCondDay entity = entityOpt.get();
		
		entity.useAtr = domain.isUse();
		entity.condName = domain.getName().v();
		if (domain.getErrorAlarmMessage() != null) {
			entity.message = domain.getErrorAlarmMessage().get().v();
		}
		entity.checkType = domain.getCheckItemType().value;
		entity.wrkTypeCondAtr = domain.getTargetWrkType().value;
		//TODO
		
		this.commandProxy().update(entity);
	}
	
	private KscdtScheAnyCondDay fromDomain(String contractCode, String companyId, ExtractionCondScheduleDay domain) {
		KscdtScheAnyCondDayPk pk = new KscdtScheAnyCondDayPk(companyId, domain.getErrorAlarmId(), domain.getSortOrder());
		KscdtScheAnyCondDay entity = new KscdtScheAnyCondDay(
				pk, domain.getName().v(), domain.isUse(), 
				domain.getCheckItemType().value, 
				domain.getTargetWrkType().value, 0, 0, 
				domain.getTimeZoneTargetRange().value, "");
		entity.setContractCd(contractCode);
		if (domain.getErrorAlarmMessage() != null) {
			entity.message = domain.getErrorAlarmMessage().get().v();
		}
		
		return entity;
	}

	@Override
	public void delete(String contractCode, String companyId, String erAlCheckIds) {
		List<KscdtScheAnyCondDay> entities = this.queryProxy().query(SELECT_BASIC + BY_CONTRACT_COMPANY + BY_ERAL_CHECK_ID, KscdtScheAnyCondDay.class)
				.setParameter("contractCode", contractCode)
				.setParameter("companyId", companyId)
				.setParameter("eralCheckIds", erAlCheckIds)
				.getList();
		this.commandProxy().removeAll(entities);
	}
}
