package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.weekly;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErAlCategory;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.*;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionAtr;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.*;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.ExtractionCondWeekly;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.ExtractionCondWeeklyRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcmtEralCategoryCond;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcmtEralCategoryCondPK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class JpaExtractCondWeeklyRepository extends JpaRepository implements ExtractionCondWeeklyRepository {
	private static final String SELECT_BASIC = "SELECT a FROM KrcmtEralCategoryCond a";
	private static final String BY_COMPANY_CATEGORY = " WHERE a.pk.cid = :cid AND a.pk.category = :category";
	private static final String BY_CODE= " AND a.pk.code = :code";
	private static final String ORDER_BY_CODE = " ORDER BY a.pk.code";

	@Override
	public ExtractionCondWeekly getAnyCond(String cid, int category, String code) {
		KrcmtEralCategoryCondPK pk = new KrcmtEralCategoryCondPK(cid, ErAlCategory.WEEKLY.value, code);
		KrcmtEralCategoryCond entity = this.queryProxy().find(pk, KrcmtEralCategoryCond.class).get();
		return entity.toDomainWeekly();
	}

	@Override
	public void add(String contractCode, String companyId, ExtractionCondWeekly domain) {
		KrcmtEralCategoryCondPK pk = new KrcmtEralCategoryCondPK(companyId, ErAlCategory.WEEKLY.value, domain.getCode().v());
		KrcmtEralCategoryCond entity = fromDomain(contractCode, companyId, domain, pk);
		this.commandProxy().insert(entity);
	}

	@Override
	public void update(String contractCode, String companyId, ExtractionCondWeekly domain) {
		KrcmtEralCategoryCondPK pk = new KrcmtEralCategoryCondPK(companyId, ErAlCategory.WEEKLY.value, domain.getCode().v());
		Optional<KrcmtEralCategoryCond> entityOpt = this.queryProxy().find(pk, KrcmtEralCategoryCond.class);

		if(!entityOpt.isPresent()) return;
		KrcmtEralCategoryCond tergetEntity = entityOpt.get();
		KrcmtEralCategoryCond domainAfterConvert = fromDomain(contractCode, companyId, domain, pk);

		// そのままdomainAfterConvertでupdateではダメなのか…？
		tergetEntity.krcstErAlConGroup1 = domainAfterConvert.krcstErAlConGroup1;
		tergetEntity.krcstErAlConGroup2 = domainAfterConvert.krcstErAlConGroup2;
		this.commandProxy().update(tergetEntity);
	}

	@Override
	public void delete(String cid, int category, String code) {
		KrcmtEralCategoryCondPK pk = new KrcmtEralCategoryCondPK(cid, ErAlCategory.WEEKLY.value, code);
		Optional<KrcmtEralCategoryCond> entityOpt = this.queryProxy().find(pk, KrcmtEralCategoryCond.class);
		if (!entityOpt.isPresent()) {
			return;
		}
		this.commandProxy().remove(entityOpt.get());
	}

	private KrcmtEralCategoryCond fromDomain(String contractCode, String companyId, ExtractionCondWeekly domain, KrcmtEralCategoryCondPK pk) {

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

		KrcmtEralCategoryCond entity = new KrcmtEralCategoryCond(
				pk,
				domain.getName().v(),
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
}
