/**
 * 11:10:58 AM Mar 29, 2018
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.monthlycondition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.ErAlAttendanceItemCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionAtr;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycondition.TimeItemCheckMonthly;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.AttendanceItemId;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedAmountValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimeDuration;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimesValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimesValueDay;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcmtErAlAtdItemCon;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcmtErAlAtdItemConPK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlAtdTarget;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlAtdTargetPK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareRange;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareRangePK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareSingle;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareSinglePK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlConGroup;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlInputCheck;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlInputCheckPK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlSingleAtd;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlSingleAtdPK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlSingleFixed;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlSingleFixedPK;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author hungnm 月別実績の勤怠項目チェック
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_ERAL_CONDITION")
public class KrcmtTimeChkMonthly extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@NotNull
	@Column(name = "ERAL_CHECK_ID")
	public String eralCheckId;

	@Basic(optional = true)
	@Column(name = "MESSAGE_DISPLAY")
	public String messageDisplay;

	@Column(name = "OPERATOR_BETWEEN_GROUPS")
	public int operatorBetweenGroups;

	@Column(name = "GROUP2_USE_ATR")
	public int group2UseAtr;

	@Basic(optional = true)
	@Column(name = "ATD_ITEM_CONDITION_GROUP1")
	public String atdItemConditionGroup1;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
	@JoinColumn(name = "ATD_ITEM_CONDITION_GROUP1", referencedColumnName = "CONDITION_GROUP_ID", insertable = false, updatable = false)
	public KrcstErAlConGroup krcstErAlConGroup1;

	@Basic(optional = true)
	@Column(name = "ATD_ITEM_CONDITION_GROUP2")
	public String atdItemConditionGroup2;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
	@JoinColumn(name = "ATD_ITEM_CONDITION_GROUP2", referencedColumnName = "CONDITION_GROUP_ID", insertable = false, updatable = false)
	public KrcstErAlConGroup krcstErAlConGroup2;

	@OneToOne(mappedBy = "krcmtTimeChkMonthly")
	public KrcmtMonthlyCorrectCon krcmtMonthlyCorrectCon;

	@Override
	protected Object getKey() {
		return this.eralCheckId;
	}

	public static TimeItemCheckMonthly toDomain(KrcmtTimeChkMonthly entity, String companyId, String errorAlarmCode) {
		TimeItemCheckMonthly domain = TimeItemCheckMonthly.init();
		domain.setCheckId(entity.eralCheckId);
		domain.setDisplayMessage(entity.messageDisplay);
		// Set AttendanceItemCondition
		List<ErAlAttendanceItemCondition<?>> conditionsGroup1 = Optional.ofNullable(entity.krcstErAlConGroup1)
				.orElse(new KrcstErAlConGroup("", 0, new ArrayList<>())).lstAtdItemCon.stream().map(
						atdItemCon -> convertKrcmtErAlAtdItemConToDomain(entity, atdItemCon, companyId, errorAlarmCode))
						.collect(Collectors.toList());
		List<ErAlAttendanceItemCondition<?>> conditionsGroup2 = Optional.ofNullable(entity.krcstErAlConGroup2)
				.orElse(new KrcstErAlConGroup("", 0, new ArrayList<>())).lstAtdItemCon.stream().map(
						atdItemCon -> convertKrcmtErAlAtdItemConToDomain(entity, atdItemCon, companyId, errorAlarmCode))
						.collect(Collectors.toList());
		domain.createAttendanceItemCondition(entity.operatorBetweenGroups, entity.group2UseAtr == 1)
				.setAttendanceItemConditionGroup1(Optional.ofNullable(entity.krcstErAlConGroup1)
						.orElse(new KrcstErAlConGroup("", (0), new ArrayList<>())).conditionOperator,
						conditionsGroup1)
				.setAttendanceItemConditionGroup2(Optional.ofNullable(entity.krcstErAlConGroup2)
						.orElse(new KrcstErAlConGroup("", (0), new ArrayList<>())).conditionOperator,
						conditionsGroup2);
		return domain;
	}

	@SuppressWarnings("unchecked")
	private static <V> ErAlAttendanceItemCondition<V> convertKrcmtErAlAtdItemConToDomain(KrcmtTimeChkMonthly entity,
			KrcmtErAlAtdItemCon atdItemCon, String companyId, String errorAlarmCode) {
		ErAlAttendanceItemCondition<V> atdItemConDomain = new ErAlAttendanceItemCondition<V>(companyId, errorAlarmCode,
				(atdItemCon.krcmtErAlAtdItemConPK.atdItemConNo), (atdItemCon.conditionAtr), (atdItemCon.useAtr == 1),
				atdItemCon.type);
		// Set Target
		if (atdItemCon.conditionAtr == ConditionAtr.TIME_WITH_DAY.value) {
			atdItemConDomain.setUncountableTarget(
					Optional.ofNullable(atdItemCon.lstAtdItemTarget).orElse(Collections.emptyList()).stream()
							.filter(atdItemTarget -> (atdItemTarget.targetAtr == 2))
							.findFirst().get().krcstErAlAtdTargetPK.attendanceItemId);
		} else {
			atdItemConDomain.setCountableTarget(
					Optional.ofNullable(atdItemCon.lstAtdItemTarget).orElse(Collections.emptyList()).stream()
							.filter(atdItemTarget -> atdItemTarget.targetAtr == 0)
							.map(addItem -> addItem.krcstErAlAtdTargetPK.attendanceItemId)
							.collect(Collectors.toList()),
					Optional.ofNullable(atdItemCon.lstAtdItemTarget).orElse(Collections.emptyList()).stream()
							.filter(atdItemTarget -> atdItemTarget.targetAtr == 1)
							.map(addItem -> addItem.krcstErAlAtdTargetPK.attendanceItemId)
							.collect(Collectors.toList()));
		}
		// Set Compare
		if (atdItemCon.erAlCompareRange != null) {
			if (atdItemCon.conditionAtr == ConditionAtr.AMOUNT_VALUE.value) {
				atdItemConDomain.setCompareRange(atdItemCon.erAlCompareRange.compareAtr,
						(V) new CheckedAmountValue((int)atdItemCon.erAlCompareRange.startValue),
						(V) new CheckedAmountValue((int)atdItemCon.erAlCompareRange.endValue));
			} else if (atdItemCon.conditionAtr == ConditionAtr.TIME_DURATION.value) {
				atdItemConDomain.setCompareRange(atdItemCon.erAlCompareRange.compareAtr,
						(V) new CheckedTimeDuration((int)atdItemCon.erAlCompareRange.startValue),
						(V) new CheckedTimeDuration((int)atdItemCon.erAlCompareRange.endValue));
			} else if (atdItemCon.conditionAtr == ConditionAtr.TIME_WITH_DAY.value) {
				atdItemConDomain.setCompareRange(atdItemCon.erAlCompareRange.compareAtr,
						(V) new TimeWithDayAttr((int)atdItemCon.erAlCompareRange.startValue),
						(V) new TimeWithDayAttr((int)atdItemCon.erAlCompareRange.endValue));
			} else if (atdItemCon.conditionAtr == ConditionAtr.TIMES.value) {
				atdItemConDomain.setCompareRange(atdItemCon.erAlCompareRange.compareAtr,
						(V) new CheckedTimesValue((int)atdItemCon.erAlCompareRange.startValue),
						(V) new CheckedTimesValue((int)atdItemCon.erAlCompareRange.endValue));
			} else if (atdItemCon.conditionAtr == ConditionAtr.DAYS.value) {
				atdItemConDomain.setCompareRange(atdItemCon.erAlCompareRange.compareAtr,
						(V) new CheckedTimesValueDay(atdItemCon.erAlCompareRange.startValue),
						(V) new CheckedTimesValueDay(atdItemCon.erAlCompareRange.endValue));
			}
		} else if (atdItemCon.erAlCompareSingle != null) {
			if (atdItemCon.erAlCompareSingle.conditionType == ConditionType.FIXED_VALUE.value) {
				if (atdItemCon.conditionAtr == ConditionAtr.AMOUNT_VALUE.value) {
					atdItemConDomain.setCompareSingleValue(atdItemCon.erAlCompareSingle.compareAtr,
							atdItemCon.erAlCompareSingle.conditionType,
							(V) new CheckedAmountValue((int)atdItemCon.erAlSingleFixed.fixedValue));
				} else if (atdItemCon.conditionAtr == ConditionAtr.TIME_DURATION.value) {
					atdItemConDomain.setCompareSingleValue(
							atdItemCon.erAlCompareSingle.compareAtr,
							atdItemCon.erAlCompareSingle.conditionType,
							(V) new CheckedTimeDuration((int)atdItemCon.erAlSingleFixed.fixedValue));
				} else if (atdItemCon.conditionAtr == ConditionAtr.TIME_WITH_DAY.value) {
					atdItemConDomain.setCompareSingleValue(
							atdItemCon.erAlCompareSingle.compareAtr,
							atdItemCon.erAlCompareSingle.conditionType,
							(V) new TimeWithDayAttr((int)atdItemCon.erAlSingleFixed.fixedValue));
				} else if (atdItemCon.conditionAtr == ConditionAtr.TIMES.value) {
					atdItemConDomain.setCompareSingleValue(
							atdItemCon.erAlCompareSingle.compareAtr,
							atdItemCon.erAlCompareSingle.conditionType,
							(V) new CheckedTimesValue((int)atdItemCon.erAlSingleFixed.fixedValue));
				} else if (atdItemCon.conditionAtr == ConditionAtr.DAYS.value) {
					atdItemConDomain.setCompareSingleValue(
							atdItemCon.erAlCompareSingle.compareAtr,
							atdItemCon.erAlCompareSingle.conditionType,
							(V) new CheckedTimesValueDay(atdItemCon.erAlSingleFixed.fixedValue));
				}
			} else {
				atdItemConDomain.setCompareSingleValue(
						atdItemCon.erAlCompareSingle.compareAtr,
						atdItemCon.erAlCompareSingle.conditionType,
						(V) new AttendanceItemId(
								atdItemCon.erAlSingleAtd.get(0).krcstEralSingleAtdPK.attendanceItemId));
			}
		}
		return atdItemConDomain;
	}

	public static KrcmtTimeChkMonthly fromDomain(TimeItemCheckMonthly domain) {
		String eralCheckId = domain.getErrorAlarmCheckID();
		String displayMessage = "";
		int operatorBetweenGroups = domain.getAtdItemCondition().getOperatorBetweenGroups().value;
		int group2UseAtr = domain.getAtdItemCondition().isUseGroup2() ? 1 : 0;
		String atdItemConditionGroup1 = domain.getAtdItemCondition().getGroup1().getAtdItemConGroupId();
		String atdItemConditionGroup2 = domain.getAtdItemCondition().getGroup2().getAtdItemConGroupId();
		int conditionOperator1 = domain.getAtdItemCondition().getGroup1().getConditionOperator().value;
		List<KrcmtErAlAtdItemCon> lstAtdItemCon1 = domain.getAtdItemCondition().getGroup1().getLstErAlAtdItemCon()
				.stream()
				.map(erAlAtdItemCon -> getKrcmtErAlAtdItemConFromDomain(atdItemConditionGroup1, erAlAtdItemCon))
				.collect(Collectors.toList());
		KrcstErAlConGroup krcstErAlConGroup1 = new KrcstErAlConGroup(atdItemConditionGroup1, conditionOperator1,
				lstAtdItemCon1);
		int conditionOperator2 = domain.getAtdItemCondition().getGroup2().getConditionOperator().value;
		List<KrcmtErAlAtdItemCon> lstAtdItemCon2 = domain.getAtdItemCondition().getGroup2().getLstErAlAtdItemCon()
				.stream()
				.map(erAlAtdItemCon -> getKrcmtErAlAtdItemConFromDomain(atdItemConditionGroup2, erAlAtdItemCon))
				.collect(Collectors.toList());
		KrcstErAlConGroup krcstErAlConGroup2 = new KrcstErAlConGroup(atdItemConditionGroup2, conditionOperator2,
				lstAtdItemCon2);
		KrcmtTimeChkMonthly entity = new KrcmtTimeChkMonthly(eralCheckId, displayMessage, operatorBetweenGroups,
				group2UseAtr, atdItemConditionGroup1, krcstErAlConGroup1, atdItemConditionGroup2, krcstErAlConGroup2);
		return entity;
	}

	private static KrcmtErAlAtdItemCon getKrcmtErAlAtdItemConFromDomain(String atdItemConditionGroup1,
			ErAlAttendanceItemCondition<?> erAlAtdItemCon) {
		KrcmtErAlAtdItemConPK krcmtErAlAtdItemConPK = new KrcmtErAlAtdItemConPK(atdItemConditionGroup1,
				(erAlAtdItemCon.getTargetNO()));
		List<KrcstErAlAtdTarget> lstAtdItemTarget = new ArrayList<>();
		if (erAlAtdItemCon.getConditionAtr() == ConditionAtr.TIME_WITH_DAY) {
			lstAtdItemTarget.add(new KrcstErAlAtdTarget(
					new KrcstErAlAtdTargetPK(atdItemConditionGroup1, erAlAtdItemCon.getTargetNO(),
							erAlAtdItemCon.getUncountableTarget().getAttendanceItem()),
					2));
		} else {
			List<KrcstErAlAtdTarget> lstAtdItemTargetAdd = erAlAtdItemCon.getCountableTarget()
					.getAddSubAttendanceItems().getAdditionAttendanceItems().stream()
					.map(atdItemId -> new KrcstErAlAtdTarget(
							new KrcstErAlAtdTargetPK(atdItemConditionGroup1,
									erAlAtdItemCon.getTargetNO(), atdItemId),
							0))
					.collect(Collectors.toList());
			List<KrcstErAlAtdTarget> lstAtdItemTargetSub = erAlAtdItemCon.getCountableTarget()
					.getAddSubAttendanceItems().getSubstractionAttendanceItems().stream()
					.map(atdItemId -> new KrcstErAlAtdTarget(
							new KrcstErAlAtdTargetPK(atdItemConditionGroup1,
									erAlAtdItemCon.getTargetNO(), atdItemId),
							1))
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
					new KrcstErAlCompareRangePK(atdItemConditionGroup1, erAlAtdItemCon.getTargetNO()),
					compareAtr, startValue, endValue);
		} else if (erAlAtdItemCon.getCompareSingleValue() != null) {
			compareAtr = erAlAtdItemCon.getCompareSingleValue().getCompareOpertor().value;
			conditionType = erAlAtdItemCon.getCompareSingleValue().getConditionType().value;
			erAlCompareSingle = new KrcstErAlCompareSingle(
					new KrcstErAlCompareSinglePK(atdItemConditionGroup1, erAlAtdItemCon.getTargetNO()),
					compareAtr, conditionType);
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
				erAlSingleFixed = new KrcstErAlSingleFixed(new KrcstErAlSingleFixedPK(atdItemConditionGroup1,
						erAlAtdItemCon.getTargetNO()), fixedValue);
			} else {
				erAlSingleAtd.add(new KrcstErAlSingleAtd(
						new KrcstErAlSingleAtdPK(atdItemConditionGroup1, erAlAtdItemCon.getTargetNO(),
								((AttendanceItemId) erAlAtdItemCon.getCompareSingleValue().getValue()).v()),
						2));
			}
		} else if (erAlAtdItemCon.getCompareSingleValue() != null) {
			erAlInputCheck = new KrcstErAlInputCheck(
					new KrcstErAlInputCheckPK(atdItemConditionGroup1, erAlAtdItemCon.getTargetNO()),
					erAlAtdItemCon.getInputCheck().getInputCheckCondition().value);
		}
		return new KrcmtErAlAtdItemCon(krcmtErAlAtdItemConPK, erAlAtdItemCon.getConditionAtr().value,
				erAlAtdItemCon.isUse() ? 1 : 0, erAlAtdItemCon.getType().value, lstAtdItemTarget, erAlCompareSingle,
				erAlCompareRange, erAlInputCheck, erAlSingleFixed, erAlSingleAtd);
	}

	public KrcmtTimeChkMonthly(String eralCheckId, String messageDisplay, int operatorBetweenGroups,
			int group2UseAtr, String atdItemConditionGroup1, KrcstErAlConGroup krcstErAlConGroup1,
			String atdItemConditionGroup2, KrcstErAlConGroup krcstErAlConGroup2) {
		super();
		this.eralCheckId = eralCheckId;
		this.messageDisplay = messageDisplay;
		this.operatorBetweenGroups = operatorBetweenGroups;
		this.group2UseAtr = group2UseAtr;
		this.atdItemConditionGroup1 = atdItemConditionGroup1;
		this.krcstErAlConGroup1 = krcstErAlConGroup1;
		this.atdItemConditionGroup2 = atdItemConditionGroup2;
		this.krcstErAlConGroup2 = krcstErAlConGroup2;
	}
}
