/**
 * 11:10:58 AM Mar 29, 2018
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.monthlycondition;

import java.io.Serializable;
import java.math.BigDecimal;
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
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcmtErAlAtdItemCon;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcmtErAlAtdItemConPK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlAtdTarget;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlAtdTargetPK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareRange;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareRangePK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareSingle;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareSinglePK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlConGroup;
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
	public BigDecimal operatorBetweenGroups;

	@Column(name = "GROUP2_USE_ATR")
	public BigDecimal group2UseAtr;

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
				.orElse(new KrcstErAlConGroup("", new BigDecimal(0), new ArrayList<>())).lstAtdItemCon.stream().map(
						atdItemCon -> convertKrcmtErAlAtdItemConToDomain(entity, atdItemCon, companyId, errorAlarmCode))
						.collect(Collectors.toList());
		List<ErAlAttendanceItemCondition<?>> conditionsGroup2 = Optional.ofNullable(entity.krcstErAlConGroup2)
				.orElse(new KrcstErAlConGroup("", new BigDecimal(0), new ArrayList<>())).lstAtdItemCon.stream().map(
						atdItemCon -> convertKrcmtErAlAtdItemConToDomain(entity, atdItemCon, companyId, errorAlarmCode))
						.collect(Collectors.toList());
		domain.createAttendanceItemCondition(
				(entity.operatorBetweenGroups == null ? 0 : entity.operatorBetweenGroups.intValue()),
				(entity.group2UseAtr != null && entity.group2UseAtr.intValue() == 1))
				.setAttendanceItemConditionGroup1(Optional.ofNullable(entity.krcstErAlConGroup1)
						.orElse(new KrcstErAlConGroup("", new BigDecimal(0), new ArrayList<>())).conditionOperator
								.intValue(),
						conditionsGroup1)
				.setAttendanceItemConditionGroup2(Optional.ofNullable(entity.krcstErAlConGroup2)
						.orElse(new KrcstErAlConGroup("", new BigDecimal(0), new ArrayList<>())).conditionOperator
								.intValue(),
						conditionsGroup2);
		return domain;
	}

	@SuppressWarnings("unchecked")
	private static <V> ErAlAttendanceItemCondition<V> convertKrcmtErAlAtdItemConToDomain(KrcmtTimeChkMonthly entity,
			KrcmtErAlAtdItemCon atdItemCon, String companyId, String errorAlarmCode) {
		ErAlAttendanceItemCondition<V> atdItemConDomain = new ErAlAttendanceItemCondition<V>(companyId, errorAlarmCode,
				(atdItemCon.krcmtErAlAtdItemConPK.atdItemConNo == null ? 0
						: atdItemCon.krcmtErAlAtdItemConPK.atdItemConNo.intValue()),
				(atdItemCon.conditionAtr == null ? 0 : atdItemCon.conditionAtr.intValue()),
				(atdItemCon.useAtr != null && atdItemCon.useAtr.intValue() == 1));
		// Set Target
		if (atdItemCon.conditionAtr != null && atdItemCon.conditionAtr.intValue() == ConditionAtr.TIME_WITH_DAY.value) {
			atdItemConDomain.setUncountableTarget(
					Optional.ofNullable(atdItemCon.lstAtdItemTarget).orElse(Collections.emptyList()).stream()
							.filter(atdItemTarget -> (atdItemTarget.targetAtr != null
									&& atdItemTarget.targetAtr.intValue() == 2))
							.findFirst().get().krcstErAlAtdTargetPK.attendanceItemId.intValue());
		} else {
			atdItemConDomain.setCountableTarget(
					Optional.ofNullable(atdItemCon.lstAtdItemTarget).orElse(Collections.emptyList()).stream()
							.filter(atdItemTarget -> atdItemTarget.targetAtr.intValue() == 0)
							.map(addItem -> addItem.krcstErAlAtdTargetPK.attendanceItemId.intValue())
							.collect(Collectors.toList()),
					Optional.ofNullable(atdItemCon.lstAtdItemTarget).orElse(Collections.emptyList()).stream()
							.filter(atdItemTarget -> atdItemTarget.targetAtr.intValue() == 1)
							.map(addItem -> addItem.krcstErAlAtdTargetPK.attendanceItemId.intValue())
							.collect(Collectors.toList()));
		}
		// Set Compare
		if (atdItemCon.erAlCompareRange != null) {
			if (atdItemCon.conditionAtr != null
					&& atdItemCon.conditionAtr.intValue() == ConditionAtr.AMOUNT_VALUE.value) {
				atdItemConDomain.setCompareRange(atdItemCon.erAlCompareRange.compareAtr.intValue(),
						(V) new CheckedAmountValue(atdItemCon.erAlCompareRange.startValue.intValue()),
						(V) new CheckedAmountValue(atdItemCon.erAlCompareRange.endValue.intValue()));
			} else if (atdItemCon.conditionAtr != null
					&& atdItemCon.conditionAtr.intValue() == ConditionAtr.TIME_DURATION.value) {
				atdItemConDomain.setCompareRange(atdItemCon.erAlCompareRange.compareAtr.intValue(),
						(V) new CheckedTimeDuration(atdItemCon.erAlCompareRange.startValue.intValue()),
						(V) new CheckedTimeDuration(atdItemCon.erAlCompareRange.endValue.intValue()));
			} else if (atdItemCon.conditionAtr != null
					&& atdItemCon.conditionAtr.intValue() == ConditionAtr.TIME_WITH_DAY.value) {
				atdItemConDomain.setCompareRange(atdItemCon.erAlCompareRange.compareAtr.intValue(),
						(V) new TimeWithDayAttr(atdItemCon.erAlCompareRange.startValue.intValue()),
						(V) new TimeWithDayAttr(atdItemCon.erAlCompareRange.endValue.intValue()));
			} else if (atdItemCon.conditionAtr != null
					&& atdItemCon.conditionAtr.intValue() == ConditionAtr.TIMES.value) {
				atdItemConDomain.setCompareRange(atdItemCon.erAlCompareRange.compareAtr.intValue(),
						(V) new CheckedTimesValue(atdItemCon.erAlCompareRange.startValue.intValue()),
						(V) new CheckedTimesValue(atdItemCon.erAlCompareRange.endValue.intValue()));
			}
		} else if (atdItemCon.erAlCompareSingle != null) {
			if (atdItemCon.erAlCompareSingle.conditionType != null
					&& atdItemCon.erAlCompareSingle.conditionType.intValue() == ConditionType.FIXED_VALUE.value) {
				if (atdItemCon.conditionAtr != null
						&& atdItemCon.conditionAtr.intValue() == ConditionAtr.AMOUNT_VALUE.value) {
					atdItemConDomain.setCompareSingleValue(atdItemCon.erAlCompareSingle.compareAtr.intValue(),
							atdItemCon.erAlCompareSingle.conditionType.intValue(),
							(V) new CheckedAmountValue(atdItemCon.erAlSingleFixed.fixedValue.intValue()));
				} else if (atdItemCon.conditionAtr != null
						&& atdItemCon.conditionAtr.intValue() == ConditionAtr.TIME_DURATION.value) {
					atdItemConDomain.setCompareSingleValue(
							atdItemCon.erAlCompareSingle.compareAtr == null ? 0
									: atdItemCon.erAlCompareSingle.compareAtr.intValue(),
							atdItemCon.erAlCompareSingle.conditionType == null ? 0
									: atdItemCon.erAlCompareSingle.conditionType.intValue(),
							(V) new CheckedTimeDuration(atdItemCon.erAlSingleFixed.fixedValue == null ? 0
									: atdItemCon.erAlSingleFixed.fixedValue.intValue()));
				} else if (atdItemCon.conditionAtr != null
						&& atdItemCon.conditionAtr.intValue() == ConditionAtr.TIME_WITH_DAY.value) {
					atdItemConDomain.setCompareSingleValue(
							atdItemCon.erAlCompareSingle.compareAtr == null ? 0
									: atdItemCon.erAlCompareSingle.compareAtr.intValue(),
							atdItemCon.erAlCompareSingle.conditionType == null ? 0
									: atdItemCon.erAlCompareSingle.conditionType.intValue(),
							(V) new TimeWithDayAttr(atdItemCon.erAlSingleFixed.fixedValue == null ? 0
									: atdItemCon.erAlSingleFixed.fixedValue.intValue()));
				} else if (atdItemCon.conditionAtr != null
						&& atdItemCon.conditionAtr.intValue() == ConditionAtr.TIMES.value) {
					atdItemConDomain.setCompareSingleValue(
							atdItemCon.erAlCompareSingle.compareAtr == null ? 0
									: atdItemCon.erAlCompareSingle.compareAtr.intValue(),
							atdItemCon.erAlCompareSingle.conditionType == null ? 0
									: atdItemCon.erAlCompareSingle.conditionType.intValue(),
							(V) new CheckedTimesValue(atdItemCon.erAlSingleFixed.fixedValue == null ? 0
									: atdItemCon.erAlSingleFixed.fixedValue.intValue()));
				}
			} else {
				atdItemConDomain.setCompareSingleValue(
						atdItemCon.erAlCompareSingle.compareAtr == null ? 0
								: atdItemCon.erAlCompareSingle.compareAtr.intValue(),
						atdItemCon.erAlCompareSingle.conditionType == null ? 0
								: atdItemCon.erAlCompareSingle.conditionType.intValue(),
						(V) new AttendanceItemId(
								atdItemCon.erAlSingleAtd.get(0).krcstEralSingleAtdPK.attendanceItemId));
			}
		}
		return atdItemConDomain;
	}

	public static KrcmtTimeChkMonthly fromDomain(TimeItemCheckMonthly domain) {
		String eralCheckId = domain.getErrorAlarmCheckID();
		String displayMessage = "";
		BigDecimal operatorBetweenGroups = new BigDecimal(
				domain.getAtdItemCondition().getOperatorBetweenGroups().value);
		BigDecimal group2UseAtr = new BigDecimal(domain.getAtdItemCondition().getGroup2UseAtr() ? 1 : 0);
		String atdItemConditionGroup1 = domain.getAtdItemCondition().getGroup1().getAtdItemConGroupId();
		String atdItemConditionGroup2 = domain.getAtdItemCondition().getGroup2().getAtdItemConGroupId();
		BigDecimal conditionOperator1 = new BigDecimal(
				domain.getAtdItemCondition().getGroup1().getConditionOperator().value);
		List<KrcmtErAlAtdItemCon> lstAtdItemCon1 = domain.getAtdItemCondition().getGroup1().getLstErAlAtdItemCon()
				.stream()
				.map(erAlAtdItemCon -> getKrcmtErAlAtdItemConFromDomain(atdItemConditionGroup1, erAlAtdItemCon))
				.collect(Collectors.toList());
		KrcstErAlConGroup krcstErAlConGroup1 = new KrcstErAlConGroup(atdItemConditionGroup1, conditionOperator1,
				lstAtdItemCon1);
		BigDecimal conditionOperator2 = new BigDecimal(
				domain.getAtdItemCondition().getGroup2().getConditionOperator().value);
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
				new BigDecimal(erAlAtdItemCon.getTargetNO()));
		List<KrcstErAlAtdTarget> lstAtdItemTarget = new ArrayList<>();
		if (erAlAtdItemCon.getConditionAtr() == ConditionAtr.TIME_WITH_DAY) {
			lstAtdItemTarget.add(new KrcstErAlAtdTarget(
					new KrcstErAlAtdTargetPK(atdItemConditionGroup1, new BigDecimal(erAlAtdItemCon.getTargetNO()),
							new BigDecimal(erAlAtdItemCon.getUncountableTarget().getAttendanceItem())),
					new BigDecimal(2)));
		} else {
			List<KrcstErAlAtdTarget> lstAtdItemTargetAdd = erAlAtdItemCon.getCountableTarget()
					.getAddSubAttendanceItems().getAdditionAttendanceItems().stream()
					.map(atdItemId -> new KrcstErAlAtdTarget(
							new KrcstErAlAtdTargetPK(atdItemConditionGroup1,
									new BigDecimal(erAlAtdItemCon.getTargetNO()), new BigDecimal(atdItemId)),
							new BigDecimal(0)))
					.collect(Collectors.toList());
			List<KrcstErAlAtdTarget> lstAtdItemTargetSub = erAlAtdItemCon.getCountableTarget()
					.getAddSubAttendanceItems().getSubstractionAttendanceItems().stream()
					.map(atdItemId -> new KrcstErAlAtdTarget(
							new KrcstErAlAtdTargetPK(atdItemConditionGroup1,
									new BigDecimal(erAlAtdItemCon.getTargetNO()), new BigDecimal(atdItemId)),
							new BigDecimal(1)))
					.collect(Collectors.toList());
			lstAtdItemTarget.addAll(lstAtdItemTargetAdd);
			lstAtdItemTarget.addAll(lstAtdItemTargetSub);
		}
		BigDecimal compareAtr = new BigDecimal(0);
		BigDecimal conditionType = new BigDecimal(0);
		BigDecimal startValue = new BigDecimal(0);
		BigDecimal endValue = new BigDecimal(0);
		KrcstErAlCompareSingle erAlCompareSingle = null;
		KrcstErAlCompareRange erAlCompareRange = null;
		KrcstErAlSingleFixed erAlSingleFixed = null;
		List<KrcstErAlSingleAtd> erAlSingleAtd = new ArrayList<>();
		if (erAlAtdItemCon.getCompareRange() != null) {
			compareAtr = new BigDecimal(erAlAtdItemCon.getCompareRange().getCompareOperator().value);
			if (erAlAtdItemCon.getConditionAtr() == ConditionAtr.AMOUNT_VALUE) {
				startValue = new BigDecimal(
						((CheckedAmountValue) erAlAtdItemCon.getCompareRange().getStartValue()).v());
				endValue = new BigDecimal(((CheckedAmountValue) erAlAtdItemCon.getCompareRange().getEndValue()).v());
			} else if (erAlAtdItemCon.getConditionAtr() == ConditionAtr.TIME_DURATION) {
				startValue = new BigDecimal(
						((CheckedTimeDuration) erAlAtdItemCon.getCompareRange().getStartValue()).v());
				endValue = new BigDecimal(((CheckedTimeDuration) erAlAtdItemCon.getCompareRange().getEndValue()).v());
			} else if (erAlAtdItemCon.getConditionAtr() == ConditionAtr.TIME_WITH_DAY) {
				startValue = new BigDecimal(((TimeWithDayAttr) erAlAtdItemCon.getCompareRange().getStartValue()).v());
				endValue = new BigDecimal(((TimeWithDayAttr) erAlAtdItemCon.getCompareRange().getEndValue()).v());
			} else if (erAlAtdItemCon.getConditionAtr() == ConditionAtr.TIMES) {
				startValue = new BigDecimal(((CheckedTimesValue) erAlAtdItemCon.getCompareRange().getStartValue()).v());
				endValue = new BigDecimal(((CheckedTimesValue) erAlAtdItemCon.getCompareRange().getEndValue()).v());
			}
			erAlCompareRange = new KrcstErAlCompareRange(
					new KrcstErAlCompareRangePK(atdItemConditionGroup1, new BigDecimal(erAlAtdItemCon.getTargetNO())),
					compareAtr, startValue, endValue);
		} else if (erAlAtdItemCon.getCompareSingleValue() != null) {
			compareAtr = new BigDecimal(erAlAtdItemCon.getCompareSingleValue().getCompareOpertor().value);
			conditionType = new BigDecimal(erAlAtdItemCon.getCompareSingleValue().getConditionType().value);
			erAlCompareSingle = new KrcstErAlCompareSingle(
					new KrcstErAlCompareSinglePK(atdItemConditionGroup1, new BigDecimal(erAlAtdItemCon.getTargetNO())),
					compareAtr, conditionType);
			if (erAlAtdItemCon.getCompareSingleValue().getConditionType() == ConditionType.FIXED_VALUE) {
				BigDecimal fixedValue = new BigDecimal(0);
				if (erAlAtdItemCon.getConditionAtr() == ConditionAtr.AMOUNT_VALUE) {
					fixedValue = new BigDecimal(
							((CheckedAmountValue) erAlAtdItemCon.getCompareSingleValue().getValue()).v());
				} else if (erAlAtdItemCon.getConditionAtr() == ConditionAtr.TIME_DURATION) {
					fixedValue = new BigDecimal(
							((CheckedTimeDuration) erAlAtdItemCon.getCompareSingleValue().getValue()).v());
				} else if (erAlAtdItemCon.getConditionAtr() == ConditionAtr.TIME_WITH_DAY) {
					fixedValue = new BigDecimal(
							((TimeWithDayAttr) erAlAtdItemCon.getCompareSingleValue().getValue()).v());
				} else if (erAlAtdItemCon.getConditionAtr() == ConditionAtr.TIMES) {
					fixedValue = new BigDecimal(
							((CheckedTimesValue) erAlAtdItemCon.getCompareSingleValue().getValue()).v());
				}
				erAlSingleFixed = new KrcstErAlSingleFixed(new KrcstErAlSingleFixedPK(atdItemConditionGroup1,
						new BigDecimal(erAlAtdItemCon.getTargetNO())), fixedValue);
			} else {
				erAlSingleAtd.add(new KrcstErAlSingleAtd(
						new KrcstErAlSingleAtdPK(atdItemConditionGroup1, new BigDecimal(erAlAtdItemCon.getTargetNO()),
								(((AttendanceItemId) erAlAtdItemCon.getCompareSingleValue().getValue()).v())),
						new BigDecimal(2)));
			}
		}
		return new KrcmtErAlAtdItemCon(krcmtErAlAtdItemConPK, new BigDecimal(erAlAtdItemCon.getConditionAtr().value),
				new BigDecimal(erAlAtdItemCon.getUseAtr() ? 1 : 0), lstAtdItemTarget, erAlCompareSingle,
				erAlCompareRange, erAlSingleFixed, erAlSingleAtd);
	}

	public KrcmtTimeChkMonthly(String eralCheckId, String messageDisplay, BigDecimal operatorBetweenGroups,
			BigDecimal group2UseAtr, String atdItemConditionGroup1, KrcstErAlConGroup krcstErAlConGroup1,
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
