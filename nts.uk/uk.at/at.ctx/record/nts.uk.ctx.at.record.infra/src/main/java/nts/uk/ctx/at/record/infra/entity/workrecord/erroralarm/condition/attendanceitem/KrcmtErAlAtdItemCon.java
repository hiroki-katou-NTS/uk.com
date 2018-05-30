/**
 * 5:25:15 PM Dec 5, 2017
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.ErAlAttendanceItemCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionAtr;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.AttendanceItemId;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedAmountValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimeDuration;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimesValue;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.monthlycondition.KrcmtTimeChkMonthly;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author hungnm
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_ER_AL_ATD_ITEM_CON")
public class KrcmtErAlAtdItemCon extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtErAlAtdItemConPK krcmtErAlAtdItemConPK;

	@Basic(optional = false)
	@NotNull
	@Column(name = "CONDITION_ATR")
	public int conditionAtr;

	@Basic(optional = false)
	@NotNull
	@Column(name = "USE_ATR")
	public int useAtr;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
	@JoinColumns({
			@JoinColumn(name = "CONDITION_GROUP_ID", referencedColumnName = "CONDITION_GROUP_ID", nullable = false),
			@JoinColumn(name = "ATD_ITEM_CON_NO", referencedColumnName = "ATD_ITEM_CON_NO", nullable = false) })
	public List<KrcstErAlAtdTarget> lstAtdItemTarget;

	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcmtErAlAtdItemCon", orphanRemoval=true)
	public KrcstErAlCompareSingle erAlCompareSingle;

	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcmtErAlAtdItemCon", orphanRemoval=true)
	public KrcstErAlCompareRange erAlCompareRange;

	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcmtErAlAtdItemCon", orphanRemoval=true)
	public KrcstErAlSingleFixed erAlSingleFixed;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
	@JoinColumns({
			@JoinColumn(name = "CONDITION_GROUP_ID", referencedColumnName = "CONDITION_GROUP_ID", nullable = false),
			@JoinColumn(name = "ATD_ITEM_CON_NO", referencedColumnName = "ATD_ITEM_CON_NO", nullable = false) })
	public List<KrcstErAlSingleAtd> erAlSingleAtd;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "CONDITION_GROUP_ID", referencedColumnName = "CONDITION_GROUP_ID", insertable = false, updatable = false) })
	public KrcstErAlConGroup krcstErAlConGroup;

	@Override
	protected Object getKey() {
		return this.krcmtErAlAtdItemConPK;
	}

	public KrcmtErAlAtdItemCon(KrcmtErAlAtdItemConPK krcmtErAlAtdItemConPK, int conditionAtr, int useAtr,
			List<KrcstErAlAtdTarget> lstAtdItemTarget, KrcstErAlCompareSingle erAlCompareSingle,
			KrcstErAlCompareRange erAlCompareRange, KrcstErAlSingleFixed erAlSingleFixed,
			List<KrcstErAlSingleAtd> erAlSingleAtd) {
		super();
		this.krcmtErAlAtdItemConPK = krcmtErAlAtdItemConPK;
		this.conditionAtr = conditionAtr;
		this.useAtr = useAtr;
		this.lstAtdItemTarget = lstAtdItemTarget;
		this.erAlCompareSingle = erAlCompareSingle;
		this.erAlCompareRange = erAlCompareRange;
		this.erAlSingleFixed = erAlSingleFixed;
		this.erAlSingleAtd = erAlSingleAtd;
	}
	
	public static KrcmtErAlAtdItemCon toEntity(String atdItemConditionGroup1,
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
	
	@SuppressWarnings("unchecked")
	public <V> ErAlAttendanceItemCondition<V> toDomain(
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

}
