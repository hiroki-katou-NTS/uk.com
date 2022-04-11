package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.weekly;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.*;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionAtr;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.*;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.ExtractionCondWeekly;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.ExtractionCondWeeklyRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.*;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.weekly.KrcdtWeekCondAlarm;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.weekly.KrcdtWeekCondAlarmPk;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class JpaExtractCondWeeklyRepository extends JpaRepository implements ExtractionCondWeeklyRepository {
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
	public List<ExtractionCondWeekly> getAnyCond(String contractCode, String companyId, String eralCheckIds) {
		List<KrcdtWeekCondAlarm> entities = this.queryProxy().query(SELECT_BASIC + BY_CONTRACT_COMPANY + BY_ERAL_CHECK_ID + ORDER_BY_NO, KrcdtWeekCondAlarm.class)
				.setParameter("contractCode", contractCode)
				.setParameter("companyId", companyId)
				.setParameter("eralCheckIds", eralCheckIds)
				.getList();
        List<ExtractionCondWeekly> domain = new ArrayList<>();
    	for(KrcdtWeekCondAlarm item: entities) {
    		ExtractionCondWeekly toDomain = item.toDomain("");
    		domain.add(toDomain);
        }
    	
    	return domain;
	}

	@Override
	public void add(String contractCode, String companyId, ExtractionCondWeekly domain) {
		KrcdtWeekCondAlarm entity = fromDomain(contractCode, companyId, domain);
		this.commandProxy().insert(entity);
	}

	@Override
	public void update(String contractCode, String companyId, ExtractionCondWeekly domain) {
		KrcdtWeekCondAlarmPk pk = new KrcdtWeekCondAlarmPk(companyId, domain.getErrorAlarmId(), domain.getSortOrder());
		Optional<KrcdtWeekCondAlarm> entityOpt = this.queryProxy().find(pk, KrcdtWeekCondAlarm.class);

		if(!entityOpt.isPresent()) return;
		KrcdtWeekCondAlarm tergetEntity = entityOpt.get();
		KrcdtWeekCondAlarm domainAfterConvert = fromDomain(contractCode, companyId, domain);

		// そのままdomainAfterConvertでupdateではダメなのか…？
		tergetEntity.condName = domainAfterConvert.condName;
		tergetEntity.condMsg = domainAfterConvert.condMsg;
		tergetEntity.useAtr = domainAfterConvert.useAtr;
		tergetEntity.checkType = domainAfterConvert.checkType;
		tergetEntity.conMonth = domainAfterConvert.conMonth;
		tergetEntity.krcstErAlConGroup1 = domainAfterConvert.krcstErAlConGroup1;
		tergetEntity.krcstErAlConGroup2 = domainAfterConvert.krcstErAlConGroup2;
		this.commandProxy().update(tergetEntity);
	}

	@Override
	public void delete(String contractCode, String companyId, String erAlCheckIds, int alarmNo) {
		KrcdtWeekCondAlarmPk pk = new KrcdtWeekCondAlarmPk(companyId, erAlCheckIds, alarmNo);
		Optional<KrcdtWeekCondAlarm> entityOpt = this.queryProxy().find(pk, KrcdtWeekCondAlarm.class);
		if (!entityOpt.isPresent()) {
			return;
		}
		this.commandProxy().remove(entityOpt.get());
	}

	@Override
	public void delete(String contractCode, String companyId, String checkId) {
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

		String atdItemConditionGroup1 = domain.getAtdItemCondition().getGroup1().getAtdItemConGroupId();
		String atdItemConditionGroup2 = domain.getAtdItemCondition().getGroup2().getAtdItemConGroupId();

		int conditionOperator1 = domain.getAtdItemCondition().getGroup1().getConditionOperator().value;
		int conditionOperator2 = domain.getAtdItemCondition().getGroup2().getConditionOperator().value;
		List<KrcmtEralstCndgrp> lstAtdItemCon1 = domain.getAtdItemCondition().getGroup1()
				.getLstErAlAtdItemCon().stream()
				.map(erAlAtdItemCon -> getKrcmtErAlAtdItemConFromDomain(atdItemConditionGroup1, erAlAtdItemCon))
				.collect(Collectors.toList());
		List<KrcmtEralstCndgrp> lstAtdItemCon2 = domain.getAtdItemCondition().getGroup2()
				.getLstErAlAtdItemCon().stream()
				.map(erAlAtdItemCon -> getKrcmtErAlAtdItemConFromDomain(atdItemConditionGroup2, erAlAtdItemCon))
				.collect(Collectors.toList());
		KrcmtEralstCndexpiptchk krcstErAlConGroup1 = new KrcmtEralstCndexpiptchk(atdItemConditionGroup1, conditionOperator1,
				lstAtdItemCon1);
		KrcmtEralstCndexpiptchk krcstErAlConGroup2 = new KrcmtEralstCndexpiptchk(atdItemConditionGroup2, conditionOperator2,
				lstAtdItemCon2);

		KrcdtWeekCondAlarm entity = new KrcdtWeekCondAlarm(
				pk,
				domain.getName().v(),
				domain.isUse(), 
				domain.getCheckItemType().value,
				domain.getContinuousPeriod() != null && domain.getContinuousPeriod().isPresent() ? domain.getContinuousPeriod().get().v() : null,
				domain.getErrorAlarmMessage() != null && domain.getErrorAlarmMessage().isPresent() ? domain.getErrorAlarmMessage().get().v() : "",
				domain.getAtdItemCondition().isUseGroup2() ? 1 : 0,
				domain.getAtdItemCondition().getGroup1().getAtdItemConGroupId(),
				domain.getAtdItemCondition().getGroup2().getAtdItemConGroupId(),
				domain.getAtdItemCondition().getOperatorBetweenGroups().value,
				krcstErAlConGroup1,
				krcstErAlConGroup2);
		entity.setContractCd(contractCode);
		
		return entity;
	}

	private static KrcmtEralstCndgrp getKrcmtErAlAtdItemConFromDomain(String atdItemConditionGroup1,
																	  ErAlAttendanceItemCondition<?> erAlAtdItemCon) {
		KrcmtErAlAtdItemConPK krcmtErAlAtdItemConPK = new KrcmtErAlAtdItemConPK(atdItemConditionGroup1,
				(erAlAtdItemCon.getTargetNO()));
		List<KrcmtEralstCndexprange> lstAtdItemTarget = new ArrayList<>();
		if (erAlAtdItemCon.getConditionAtr() == ConditionAtr.TIME_WITH_DAY) {
			lstAtdItemTarget.add(new KrcmtEralstCndexprange(new KrcstErAlAtdTargetPK(atdItemConditionGroup1,
					(erAlAtdItemCon.getTargetNO()), (erAlAtdItemCon.getUncountableTarget().getAttendanceItem())), 2));
		} else {
			List<KrcmtEralstCndexprange> lstAtdItemTargetAdd = erAlAtdItemCon.getCountableTarget()
					.getAddSubAttendanceItems().getAdditionAttendanceItems().stream()
					.map(atdItemId -> new KrcmtEralstCndexprange(new KrcstErAlAtdTargetPK(atdItemConditionGroup1,
							(erAlAtdItemCon.getTargetNO()), (atdItemId)), 0))
					.collect(Collectors.toList());
			List<KrcmtEralstCndexprange> lstAtdItemTargetSub = erAlAtdItemCon.getCountableTarget()
					.getAddSubAttendanceItems().getSubstractionAttendanceItems().stream()
					.map(atdItemId -> new KrcmtEralstCndexprange(new KrcstErAlAtdTargetPK(atdItemConditionGroup1,
							(erAlAtdItemCon.getTargetNO()), (atdItemId)), 1))
					.collect(Collectors.toList());
			lstAtdItemTarget.addAll(lstAtdItemTargetAdd);
			lstAtdItemTarget.addAll(lstAtdItemTargetSub);
		}
		int compareAtr = 0;
		int conditionType = 0;
		double startValue = 0;
		double endValue = 0;
		KrcstErAlCompareSingle erAlCompareSingle = null;
		KrcstErAlCompareRange erAlCompareRange = null;
		KrcstErAlInputCheck erAlInputCheck = null;
		KrcstErAlSingleFixed erAlSingleFixed = null;
		List<KrcstErAlSingleAtd> erAlSingleAtd = new ArrayList<>();
		if (erAlAtdItemCon.getCompareRange() != null) {
			compareAtr = erAlAtdItemCon.getCompareRange().getCompareOperator().value;
			if (erAlAtdItemCon.getConditionAtr() == ConditionAtr.AMOUNT_VALUE) {
				startValue = ((CheckedAmountValue) erAlAtdItemCon.getCompareRange().getStartValue()).v();
				endValue = ((CheckedAmountValue) erAlAtdItemCon.getCompareRange().getEndValue()).v();
			} else if (erAlAtdItemCon.getConditionAtr() == ConditionAtr.TIME_DURATION) {
				startValue = ((CheckedTimeDuration) erAlAtdItemCon.getCompareRange().getStartValue()).v();
				endValue = ((CheckedTimeDuration) erAlAtdItemCon.getCompareRange().getEndValue()).v();
			} else if (erAlAtdItemCon.getConditionAtr() == ConditionAtr.TIME_WITH_DAY) {
				startValue = ((TimeWithDayAttr) erAlAtdItemCon.getCompareRange().getStartValue()).v();
				endValue = ((TimeWithDayAttr) erAlAtdItemCon.getCompareRange().getEndValue()).v();
			} else if (erAlAtdItemCon.getConditionAtr() == ConditionAtr.TIMES) {
				startValue = ((CheckedTimesValue) erAlAtdItemCon.getCompareRange().getStartValue()).v();
				endValue = ((CheckedTimesValue) erAlAtdItemCon.getCompareRange().getEndValue()).v();
			} else if (erAlAtdItemCon.getConditionAtr() == ConditionAtr.DAYS) {
				startValue = ((CheckedTimesValueDay) erAlAtdItemCon.getCompareRange().getStartValue()).v();
				endValue = ((CheckedTimesValueDay) erAlAtdItemCon.getCompareRange().getEndValue()).v();
			}
			erAlCompareRange = new KrcstErAlCompareRange(
					new KrcstErAlCompareRangePK(atdItemConditionGroup1, (erAlAtdItemCon.getTargetNO())), compareAtr,
					startValue, endValue);
		} else if (erAlAtdItemCon.getCompareSingleValue() != null) {
			compareAtr = erAlAtdItemCon.getCompareSingleValue().getCompareOpertor().value;
			conditionType = erAlAtdItemCon.getCompareSingleValue().getConditionType().value;
			erAlCompareSingle = new KrcstErAlCompareSingle(
					new KrcstErAlCompareSinglePK(atdItemConditionGroup1, erAlAtdItemCon.getTargetNO()), compareAtr,
					conditionType);
			if (erAlAtdItemCon.getCompareSingleValue().getConditionType() == ConditionType.FIXED_VALUE) {
				double fixedValue = 0;
				if (erAlAtdItemCon.getConditionAtr() == ConditionAtr.AMOUNT_VALUE) {
					fixedValue = ((CheckedAmountValue) erAlAtdItemCon.getCompareSingleValue().getValue()).v();
				} else if (erAlAtdItemCon.getConditionAtr() == ConditionAtr.TIME_DURATION) {
					fixedValue = ((CheckedTimeDuration) erAlAtdItemCon.getCompareSingleValue().getValue()).v();
				} else if (erAlAtdItemCon.getConditionAtr() == ConditionAtr.TIME_WITH_DAY) {
					fixedValue = ((TimeWithDayAttr) erAlAtdItemCon.getCompareSingleValue().getValue()).v();
				} else if (erAlAtdItemCon.getConditionAtr() == ConditionAtr.TIMES) {
					fixedValue = ((CheckedTimesValue) erAlAtdItemCon.getCompareSingleValue().getValue()).v();
				} else if (erAlAtdItemCon.getConditionAtr() == ConditionAtr.DAYS) {
					fixedValue = ((CheckedTimesValueDay) erAlAtdItemCon.getCompareSingleValue().getValue()).v();
				}
				erAlSingleFixed = new KrcstErAlSingleFixed(
						new KrcstErAlSingleFixedPK(atdItemConditionGroup1, (erAlAtdItemCon.getTargetNO())), fixedValue);
			} else {
				erAlSingleAtd
						.add(new KrcstErAlSingleAtd(
								new KrcstErAlSingleAtdPK(atdItemConditionGroup1, (erAlAtdItemCon.getTargetNO()),
										(((AttendanceItemId) erAlAtdItemCon.getCompareSingleValue().getValue()).v())),
								2));
			}
		} else if (erAlAtdItemCon.getCompareSingleValue() != null) {
			erAlInputCheck = new KrcstErAlInputCheck(
					new KrcstErAlInputCheckPK(atdItemConditionGroup1, erAlAtdItemCon.getTargetNO()),
					erAlAtdItemCon.getInputCheck().getInputCheckCondition().value);
		}
		return new KrcmtEralstCndgrp(krcmtErAlAtdItemConPK, erAlAtdItemCon.getConditionAtr().value,
				erAlAtdItemCon.isUse() ? 1 : 0, erAlAtdItemCon.getType().value, lstAtdItemTarget, erAlCompareSingle,
				erAlCompareRange, erAlInputCheck, erAlSingleFixed, erAlSingleAtd);
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
	private void removeCondition(String contractCode, String companyId, String erAlCheckIds, int alarmNo) {
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
