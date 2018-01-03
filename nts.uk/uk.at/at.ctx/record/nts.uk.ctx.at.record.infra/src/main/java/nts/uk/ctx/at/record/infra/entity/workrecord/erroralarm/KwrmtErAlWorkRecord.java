/**
 * 5:09:42 PM Jul 24, 2017
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.ErrorAlarmCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.ErAlAttendanceItemCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.worktime.PlanActualWorkTime;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.worktime.SingleWorkTime;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.worktype.PlanActualWorkType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.worktype.SingleWorkType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionAtr;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.FilterByCompare;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedAmountValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimeDuration;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimesValue;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcmtErAlCondition;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcstErAlApplication;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcstErAlApplicationPK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcstErAlBusinessType;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcstErAlBusinessTypePK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcstErAlClass;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcstErAlClassPK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcstErAlEmployment;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcstErAlEmploymentPK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcstErAlJobTitle;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcstErAlJobTitlePK;
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
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.worktime.KrcstErAlWhActual;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.worktime.KrcstErAlWhPlan;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.worktime.KrcstErAlWhPlanActualPK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.worktype.KrcstErAlWtActual;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.worktype.KrcstErAlWtPlan;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.worktype.KrcstErAlWtPlanActualPK;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author hungnm
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_ERAL_SET")
public class KwrmtErAlWorkRecord extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KwrmtErAlWorkRecordPK kwrmtErAlWorkRecordPK;

	@Column(name = "ERROR_ALARM_NAME")
	public String errorAlarmName;

	@Column(name = "FIXED_ATR")
	public BigDecimal fixedAtr;

	@Column(name = "USE_ATR")
	public BigDecimal useAtr;

	@Column(name = "ERAL_ATR")
	public BigDecimal typeAtr;

	@Column(name = "BOLD_ATR")
	public BigDecimal boldAtr;

	@Column(name = "MESSAGE_COLOR")
	public String messageColor;

	@Column(name = "CANCELABLE_ATR")
	public BigDecimal cancelableAtr;

	@Column(name = "ERROR_DISPLAY_ITEM")
	public BigDecimal errorDisplayItem;

	@Basic(optional = true)
	@Column(name = "ERAL_CHECK_ID")
	public String eralCheckId;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
	@JoinColumn(name = "ERAL_CHECK_ID", referencedColumnName = "ERAL_CHECK_ID", insertable = false, updatable = false)
	public KrcmtErAlCondition krcmtErAlCondition;

	@OneToMany(mappedBy = "kwrmtErAlWorkRecord", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	public List<KrcstErAlApplication> krcstErAlApplication;

	@Column(name = "CANCEL_ROLE_ID")
	public String cancelRoleId;

	@Override
	protected Object getKey() {
		return this.kwrmtErAlWorkRecordPK;
	}

	private static ErAlAttendanceItemCondition<?> convertKrcmtErAlAtdItemConToDomain(KwrmtErAlWorkRecord entity,
			KrcmtErAlAtdItemCon atdItemCon) {
		ErAlAttendanceItemCondition<Object> atdItemConDomain = new ErAlAttendanceItemCondition<Object>(
				entity.kwrmtErAlWorkRecordPK.companyId, entity.kwrmtErAlWorkRecordPK.errorAlarmCode,
				atdItemCon.krcmtErAlAtdItemConPK.atdItemConNo.intValue(), atdItemCon.conditionAtr.intValue(),
				atdItemCon.useAtr.intValue() == 1);
		// Set Target
		if (atdItemCon.conditionAtr.intValue() == ConditionAtr.TIME_WITH_DAY.value) {
			atdItemConDomain.setUncountableTarget(
					Optional.ofNullable(atdItemCon.lstAtdItemTarget).orElse(Collections.emptyList()).stream()
							.filter(atdItemTarget -> atdItemTarget.targetAtr.intValue() == 2).findFirst()
							.get().krcstErAlAtdTargetPK.attendanceItemId.intValue());
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
			if (atdItemCon.conditionAtr.intValue() == ConditionAtr.AMOUNT_VALUE.value) {
				atdItemConDomain.setCompareRange(atdItemCon.erAlCompareRange.compareAtr.intValue(),
						new CheckedAmountValue(atdItemCon.erAlCompareRange.startValue.intValue()),
						new CheckedAmountValue(atdItemCon.erAlCompareRange.endValue.intValue()));
			} else if (atdItemCon.conditionAtr.intValue() == ConditionAtr.TIME_DURATION.value) {
				atdItemConDomain.setCompareRange(atdItemCon.erAlCompareRange.compareAtr.intValue(),
						new CheckedTimeDuration(atdItemCon.erAlCompareRange.startValue.intValue()),
						new CheckedTimeDuration(atdItemCon.erAlCompareRange.endValue.intValue()));
			} else if (atdItemCon.conditionAtr.intValue() == ConditionAtr.TIME_WITH_DAY.value) {
				atdItemConDomain.setCompareRange(atdItemCon.erAlCompareRange.compareAtr.intValue(),
						new TimeWithDayAttr(atdItemCon.erAlCompareRange.startValue.intValue()),
						new TimeWithDayAttr(atdItemCon.erAlCompareRange.endValue.intValue()));
			} else if (atdItemCon.conditionAtr.intValue() == ConditionAtr.TIMES.value) {
				atdItemConDomain.setCompareRange(atdItemCon.erAlCompareRange.compareAtr.intValue(),
						new CheckedTimesValue(atdItemCon.erAlCompareRange.startValue.intValue()),
						new CheckedTimesValue(atdItemCon.erAlCompareRange.endValue.intValue()));
			}
		} else if (atdItemCon.erAlCompareSingle != null) {
			if (atdItemCon.erAlCompareSingle.conditionType.intValue() == ConditionType.FIXED_VALUE.value) {
				if (atdItemCon.conditionAtr.intValue() == ConditionAtr.AMOUNT_VALUE.value) {
					atdItemConDomain.setCompareSingleValue(atdItemCon.erAlCompareSingle.compareAtr.intValue(),
							atdItemCon.erAlCompareSingle.conditionType.intValue(),
							new CheckedAmountValue(atdItemCon.erAlSingleFixed.fixedValue.intValue()));
				} else if (atdItemCon.conditionAtr.intValue() == ConditionAtr.TIME_DURATION.value) {
					atdItemConDomain.setCompareSingleValue(atdItemCon.erAlCompareSingle.compareAtr.intValue(),
							atdItemCon.erAlCompareSingle.conditionType.intValue(),
							new CheckedTimeDuration(atdItemCon.erAlSingleFixed.fixedValue.intValue()));
				} else if (atdItemCon.conditionAtr.intValue() == ConditionAtr.TIME_WITH_DAY.value) {
					atdItemConDomain.setCompareSingleValue(atdItemCon.erAlCompareSingle.compareAtr.intValue(),
							atdItemCon.erAlCompareSingle.conditionType.intValue(),
							new TimeWithDayAttr(atdItemCon.erAlSingleFixed.fixedValue.intValue()));
				} else if (atdItemCon.conditionAtr.intValue() == ConditionAtr.TIMES.value) {
					atdItemConDomain.setCompareSingleValue(atdItemCon.erAlCompareSingle.compareAtr.intValue(),
							atdItemCon.erAlCompareSingle.conditionType.intValue(),
							new CheckedTimesValue(atdItemCon.erAlSingleFixed.fixedValue.intValue()));
				}
			} else {
				atdItemConDomain.setCompareSingleValue(atdItemCon.erAlCompareSingle.compareAtr.intValue(),
						atdItemCon.erAlCompareSingle.conditionType.intValue(),
						atdItemCon.erAlSingleAtd.get(0).krcstEralSingleAtdPK.attendanceItemId.intValue());
			}
		}
		return atdItemConDomain;
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
								new BigDecimal((Integer) erAlAtdItemCon.getCompareSingleValue().getValue())),
						new BigDecimal(2)));
			}
		}
		return new KrcmtErAlAtdItemCon(krcmtErAlAtdItemConPK, new BigDecimal(erAlAtdItemCon.getConditionAtr().value),
				new BigDecimal(erAlAtdItemCon.getUseAtr() ? 1 : 0), lstAtdItemTarget, erAlCompareSingle,
				erAlCompareRange, erAlSingleFixed, erAlSingleAtd);
	}

	public static KwrmtErAlWorkRecord fromDomain(ErrorAlarmWorkRecord domain) {
		// Set PK
		KwrmtErAlWorkRecordPK kwrmtErAlWorkRecordPK = new KwrmtErAlWorkRecordPK(AppContexts.user().companyId(),
				domain.getCode().v());
		// Set main data KwrmtErAlWorkRecord
		String errorAlarmName = domain.getName().v();
		BigDecimal fixedAtr = domain.getFixedAtr() ? new BigDecimal(1) : new BigDecimal(0);
		BigDecimal useAtr = domain.getUseAtr() ? new BigDecimal(1) : new BigDecimal(0);
		BigDecimal typeAtr = new BigDecimal(domain.getTypeAtr().value);
		BigDecimal boldAtr = domain.getMessage().getBoldAtr() ? new BigDecimal(1) : new BigDecimal(0);
		String messageColor = domain.getMessage().getMessageColor().v();
		BigDecimal cancelableAtr = domain.getCancelableAtr() ? new BigDecimal(1) : new BigDecimal(0);
		BigDecimal errorDisplayItem = domain.getErrorDisplayItem();
		String eralCheckId = domain.getErrorAlarmCheckID();
		List<KrcstErAlApplication> krcstErAlApplication = domain.getLstApplication().stream()
				.map(appTypeCd -> new KrcstErAlApplication(new KrcstErAlApplicationPK(AppContexts.user().companyId(),
						domain.getCode().v(), new BigDecimal(appTypeCd))))
				.collect(Collectors.toList());
		String cancelRoleId = domain.getCancelRoleId();
		String messageDisplay = domain.getErrorAlarmCondition().getDisplayMessage().v();
		KrcmtErAlCondition krcmtErAlCondition = new KrcmtErAlCondition(eralCheckId, messageDisplay, new BigDecimal(0),
				Collections.emptyList(), new BigDecimal(0), Collections.emptyList(), new BigDecimal(0),
				Collections.emptyList(), new BigDecimal(0), Collections.emptyList(), new BigDecimal(0),
				new BigDecimal(0), null, null, null, Collections.emptyList(), Collections.emptyList(),
				new BigDecimal(0), new BigDecimal(0), null, null, null, Collections.emptyList(),
				Collections.emptyList(), new BigDecimal(0), new BigDecimal(0), "0", null, null, null);
		if (!domain.getFixedAtr()) {
			// Set Check target condition
			BigDecimal filterByBusinessType = domain.getErrorAlarmCondition().getCheckTargetCondtion()
					.getFilterByBusinessType() ? new BigDecimal(1) : new BigDecimal(0);
			BigDecimal filterByJobTitle = new BigDecimal(
					domain.getErrorAlarmCondition().getCheckTargetCondtion().getFilterByJobTitle() ? 1 : 0);
			BigDecimal filterByEmployment = new BigDecimal(
					domain.getErrorAlarmCondition().getCheckTargetCondtion().getFilterByEmployment() ? 1 : 0);
			BigDecimal filterByClassification = new BigDecimal(
					domain.getErrorAlarmCondition().getCheckTargetCondtion().getFilterByClassification() ? 1 : 0);
			List<KrcstErAlBusinessType> lstBusinessType = domain.getErrorAlarmCondition().getCheckTargetCondtion()
					.getLstBusinessTypeCode().stream().map(businessTypeCd -> new KrcstErAlBusinessType(
							new KrcstErAlBusinessTypePK(eralCheckId, businessTypeCd.v())))
					.collect(Collectors.toList());
			List<KrcstErAlJobTitle> lstJobTitle = domain.getErrorAlarmCondition().getCheckTargetCondtion()
					.getLstJobTitleId().stream()
					.map(jobTitleId -> new KrcstErAlJobTitle(new KrcstErAlJobTitlePK(eralCheckId, jobTitleId)))
					.collect(Collectors.toList());
			List<KrcstErAlEmployment> lstEmployment = domain.getErrorAlarmCondition().getCheckTargetCondtion()
					.getLstEmploymentCode().stream()
					.map(emptCd -> new KrcstErAlEmployment(new KrcstErAlEmploymentPK(eralCheckId, emptCd.v())))
					.collect(Collectors.toList());
			List<KrcstErAlClass> lstClassification = domain.getErrorAlarmCondition().getCheckTargetCondtion()
					.getLstClassificationCode().stream()
					.map(clssCd -> new KrcstErAlClass(new KrcstErAlClassPK(eralCheckId, clssCd.v())))
					.collect(Collectors.toList());
			// Set worktype condition
			BigDecimal workTypeUseAtr = new BigDecimal(
					domain.getErrorAlarmCondition().getWorkTypeCondition().getUseAtr() ? 1 : 0);
			BigDecimal wtCompareAtr = new BigDecimal(
					domain.getErrorAlarmCondition().getWorkTypeCondition().getComparePlanAndActual().value);
			BigDecimal wtPlanActualOperator = new BigDecimal(0);
			BigDecimal wtPlanFilterAtr = new BigDecimal(0);
			BigDecimal wtActualFilterAtr = new BigDecimal(0);
			List<KrcstErAlWtPlan> lstWtPlan = new ArrayList<>();
			List<KrcstErAlWtActual> lstWtActual = new ArrayList<>();
			if (wtCompareAtr.intValue() != FilterByCompare.EXTRACT_SAME.value) {
				PlanActualWorkType wtypeCondition = (PlanActualWorkType) domain.getErrorAlarmCondition()
						.getWorkTypeCondition();
				wtPlanActualOperator = new BigDecimal(wtypeCondition.getOperatorBetweenPlanActual().value);
				wtPlanFilterAtr = new BigDecimal(wtypeCondition.getWorkTypePlan().getFilterAtr() ? 1 : 0);
				wtActualFilterAtr = new BigDecimal(wtypeCondition.getWorkTypeActual().getFilterAtr() ? 1 : 0);
				lstWtPlan = wtypeCondition.getWorkTypePlan().getLstWorkType().stream()
						.map(wtCode -> new KrcstErAlWtPlan(new KrcstErAlWtPlanActualPK(eralCheckId, wtCode.v())))
						.collect(Collectors.toList());
				lstWtActual = wtypeCondition.getWorkTypeActual().getLstWorkType().stream()
						.map(wtCode -> new KrcstErAlWtActual(new KrcstErAlWtPlanActualPK(eralCheckId, wtCode.v())))
						.collect(Collectors.toList());
			} else {
				SingleWorkType wtypeCondition = (SingleWorkType) domain.getErrorAlarmCondition().getWorkTypeCondition();
				wtPlanFilterAtr = new BigDecimal(wtypeCondition.getTargetWorkType().getFilterAtr() ? 1 : 0);
				lstWtPlan = wtypeCondition.getTargetWorkType().getLstWorkType().stream()
						.map(wtCode -> new KrcstErAlWtPlan(new KrcstErAlWtPlanActualPK(eralCheckId, wtCode.v())))
						.collect(Collectors.toList());
			}
			// Set worktime condition
			BigDecimal workingHoursUseAtr = new BigDecimal(
					domain.getErrorAlarmCondition().getWorkTimeCondition().getUseAtr() ? 1 : 0);
			BigDecimal whCompareAtr = new BigDecimal(
					domain.getErrorAlarmCondition().getWorkTimeCondition().getComparePlanAndActual().value);
			BigDecimal whPlanActualOperator = new BigDecimal(0);
			BigDecimal whPlanFilterAtr = new BigDecimal(0);
			BigDecimal whActualFilterAtr = new BigDecimal(0);
			List<KrcstErAlWhPlan> lstWhPlan = new ArrayList<>();
			List<KrcstErAlWhActual> lstWhActual = new ArrayList<>();
			if (whCompareAtr.intValue() != FilterByCompare.EXTRACT_SAME.value) {
				PlanActualWorkTime wtimeCondition = (PlanActualWorkTime) domain.getErrorAlarmCondition()
						.getWorkTimeCondition();
				whPlanActualOperator = new BigDecimal(wtimeCondition.getOperatorBetweenPlanActual().value);
				whPlanFilterAtr = new BigDecimal(wtimeCondition.getWorkTimePlan().getFilterAtr() ? 1 : 0);
				whActualFilterAtr = new BigDecimal(wtimeCondition.getWorkTimeActual().getFilterAtr() ? 1 : 0);
				lstWhPlan = wtimeCondition.getWorkTimePlan().getLstWorkTime().stream()
						.map(wtCode -> new KrcstErAlWhPlan(new KrcstErAlWhPlanActualPK(eralCheckId, wtCode.v())))
						.collect(Collectors.toList());
				lstWhActual = wtimeCondition.getWorkTimeActual().getLstWorkTime().stream()
						.map(wtCode -> new KrcstErAlWhActual(new KrcstErAlWhPlanActualPK(eralCheckId, wtCode.v())))
						.collect(Collectors.toList());
			} else {
				SingleWorkTime wtimeCondition = (SingleWorkTime) domain.getErrorAlarmCondition().getWorkTimeCondition();
				whPlanFilterAtr = new BigDecimal(wtimeCondition.getTargetWorkTime().getFilterAtr() ? 1 : 0);
				lstWhPlan = wtimeCondition.getTargetWorkTime().getLstWorkTime().stream()
						.map(wtCode -> new KrcstErAlWhPlan(new KrcstErAlWhPlanActualPK(eralCheckId, wtCode.v())))
						.collect(Collectors.toList());
			}
			// Set attendance item condition
			BigDecimal operatorBetweenGroups = new BigDecimal(
					domain.getErrorAlarmCondition().getAtdItemCondition().getOperatorBetweenGroups().value);
			BigDecimal group2UseAtr = new BigDecimal(
					domain.getErrorAlarmCondition().getAtdItemCondition().getGroup2UseAtr() ? 1 : 0);
			String atdItemConditionGroup1 = domain.getErrorAlarmCondition().getAtdItemCondition().getGroup1()
					.getAtdItemConGroupId();
			String atdItemConditionGroup2 = domain.getErrorAlarmCondition().getAtdItemCondition().getGroup2()
					.getAtdItemConGroupId();
			BigDecimal conditionOperator1 = new BigDecimal(
					domain.getErrorAlarmCondition().getAtdItemCondition().getGroup1().getConditionOperator().value);
			List<KrcmtErAlAtdItemCon> lstAtdItemCon1 = domain.getErrorAlarmCondition().getAtdItemCondition().getGroup1()
					.getLstErAlAtdItemCon().stream()
					.map(erAlAtdItemCon -> getKrcmtErAlAtdItemConFromDomain(atdItemConditionGroup1, erAlAtdItemCon))
					.collect(Collectors.toList());
			KrcstErAlConGroup krcstErAlConGroup1 = new KrcstErAlConGroup(atdItemConditionGroup1, conditionOperator1,
					lstAtdItemCon1);
			BigDecimal conditionOperator2 = new BigDecimal(
					domain.getErrorAlarmCondition().getAtdItemCondition().getGroup2().getConditionOperator().value);
			List<KrcmtErAlAtdItemCon> lstAtdItemCon2 = domain.getErrorAlarmCondition().getAtdItemCondition().getGroup2()
					.getLstErAlAtdItemCon().stream()
					.map(erAlAtdItemCon -> getKrcmtErAlAtdItemConFromDomain(atdItemConditionGroup2, erAlAtdItemCon))
					.collect(Collectors.toList());
			KrcstErAlConGroup krcstErAlConGroup2 = new KrcstErAlConGroup(atdItemConditionGroup2, conditionOperator2,
					lstAtdItemCon2);
			krcmtErAlCondition = new KrcmtErAlCondition(eralCheckId, messageDisplay, filterByBusinessType,
					lstBusinessType, filterByJobTitle, lstJobTitle, filterByEmployment, lstEmployment,
					filterByClassification, lstClassification, workTypeUseAtr, wtPlanActualOperator, wtPlanFilterAtr,
					wtActualFilterAtr, wtCompareAtr, lstWtActual, lstWtPlan, workingHoursUseAtr, whPlanActualOperator,
					whPlanFilterAtr, whActualFilterAtr, whCompareAtr, lstWhActual, lstWhPlan, operatorBetweenGroups,
					group2UseAtr, atdItemConditionGroup1, krcstErAlConGroup1, atdItemConditionGroup2,
					krcstErAlConGroup2);
		}
		KwrmtErAlWorkRecord entity = new KwrmtErAlWorkRecord(kwrmtErAlWorkRecordPK, errorAlarmName, fixedAtr, useAtr,
				typeAtr, boldAtr, messageColor.equals("") ? null : messageColor, cancelableAtr, errorDisplayItem,
				eralCheckId, krcmtErAlCondition, krcstErAlApplication, cancelRoleId);
		return entity;
	}

	public static ErrorAlarmWorkRecord toDomain(KwrmtErAlWorkRecord entity) {
		ErrorAlarmWorkRecord domain = ErrorAlarmWorkRecord.createFromJavaType(AppContexts.user().companyId(),
				entity.kwrmtErAlWorkRecordPK.errorAlarmCode, entity.errorAlarmName, entity.fixedAtr.intValue() == 1,
				entity.useAtr.intValue() == 1, entity.typeAtr.intValue(), entity.boldAtr.intValue() == 1,
				entity.messageColor, entity.cancelableAtr.intValue() == 1, entity.errorDisplayItem,
				Optional.ofNullable(entity.krcstErAlApplication).orElse(Collections.emptyList()).stream()
						.map(eralAppEntity -> eralAppEntity.krcstErAlApplicationPK.appTypeCd.intValue())
						.collect(Collectors.toList()),
				entity.eralCheckId);

		ErrorAlarmCondition condition = ErrorAlarmCondition.init();
		condition.setDisplayMessage(entity.krcmtErAlCondition.messageDisplay);
		if (entity.fixedAtr.intValue() != 1) {
			// Set AlCheckTargetCondition
			condition.createAlCheckTargetCondition(entity.krcmtErAlCondition.filterByBusinessType.intValue() == 1,
					entity.krcmtErAlCondition.filterByJobTitle.intValue() == 1,
					entity.krcmtErAlCondition.filterByEmployment.intValue() == 1,
					entity.krcmtErAlCondition.filterByClassification.intValue() == 1,
					Optional.ofNullable(entity.krcmtErAlCondition.lstBusinessType).orElse(Collections.emptyList())
							.stream().map(businessType -> businessType.krcstErAlBusinessTypePK.businessTypeCd)
							.collect(Collectors.toList()),
					Optional.ofNullable(entity.krcmtErAlCondition.lstJobTitle).orElse(Collections.emptyList()).stream()
							.map(jobTitle -> jobTitle.krcstErAlJobTitlePK.jobId).collect(Collectors.toList()),
					Optional.ofNullable(entity.krcmtErAlCondition.lstEmployment).orElse(Collections.emptyList())
							.stream().map(empt -> empt.krcstErAlEmploymentPK.emptcd).collect(Collectors.toList()),
					Optional.ofNullable(entity.krcmtErAlCondition.lstClassification).orElse(Collections.emptyList())
							.stream().map(clss -> clss.krcstErAlClassPK.clscd).collect(Collectors.toList()));
			// Set WorkTypeCondition
			condition.createWorkTypeCondition(entity.krcmtErAlCondition.workTypeUseAtr.intValue() == 1,
					entity.krcmtErAlCondition.wtCompareAtr.intValue());
			if (entity.krcmtErAlCondition.wtCompareAtr.intValue() != FilterByCompare.EXTRACT_SAME.value) {
				condition.setWorkTypePlan(entity.krcmtErAlCondition.wtPlanFilterAtr.intValue() == 1,
						Optional.ofNullable(entity.krcmtErAlCondition.lstWtPlan).orElse(Collections.emptyList())
								.stream().map(wtype -> wtype.krcstErAlWtPlanPK.workTypeCode)
								.collect(Collectors.toList()));
				condition.setWorkTypeActual(entity.krcmtErAlCondition.wtActualFilterAtr.intValue() == 1,
						Optional.ofNullable(entity.krcmtErAlCondition.lstWtActual).orElse(Collections.emptyList())
								.stream().map(wtype -> wtype.krcstErAlWtPlanActualPK.workTypeCode)
								.collect(Collectors.toList()));
				condition.chooseWorkTypeOperator(entity.krcmtErAlCondition.wtPlanActualOperator.intValue());
			} else {
				condition.setWorkTypeSingle(entity.krcmtErAlCondition.wtPlanFilterAtr.intValue() == 1,
						Optional.ofNullable(entity.krcmtErAlCondition.lstWtPlan).orElse(Collections.emptyList())
								.stream().map(wtype -> wtype.krcstErAlWtPlanPK.workTypeCode)
								.collect(Collectors.toList()));
			}
			// Set WorkTimeCondtion
			condition.createWorkTimeCondition(entity.krcmtErAlCondition.workingHoursUseAtr.intValue() == 1,
					entity.krcmtErAlCondition.whCompareAtr.intValue());
			if (entity.krcmtErAlCondition.whCompareAtr.intValue() != FilterByCompare.EXTRACT_SAME.value) {
				condition.setWorkTimePlan(entity.krcmtErAlCondition.whPlanFilterAtr.intValue() == 1,
						Optional.ofNullable(entity.krcmtErAlCondition.lstWhPlan).orElse(Collections.emptyList())
								.stream().map(wtime -> wtime.krcstErAlWhPlanActualPK.workTimeCode)
								.collect(Collectors.toList()));
				condition.setWorkTimeActual(entity.krcmtErAlCondition.whActualFilterAtr.intValue() == 1,
						Optional.ofNullable(entity.krcmtErAlCondition.lstWhActual).orElse(Collections.emptyList())
								.stream().map(wtime -> wtime.krcstErAlWhPlanActualPK.workTimeCode)
								.collect(Collectors.toList()));
				condition.chooseWorkTimeOperator(entity.krcmtErAlCondition.whPlanActualOperator.intValue());
			} else {
				condition.setWorkTimeSingle(entity.krcmtErAlCondition.whPlanFilterAtr.intValue() == 1,
						Optional.ofNullable(entity.krcmtErAlCondition.lstWhPlan).orElse(Collections.emptyList())
								.stream().map(wtime -> wtime.krcstErAlWhPlanActualPK.workTimeCode)
								.collect(Collectors.toList()));
			}
			// Set AttendanceItemCondition
			List<ErAlAttendanceItemCondition<?>> conditionsGroup1 = Optional
					.ofNullable(entity.krcmtErAlCondition.krcstErAlConGroup1)
					.orElse(new KrcstErAlConGroup("", new BigDecimal(0), new ArrayList<>())).lstAtdItemCon.stream()
							.map(atdItemCon -> convertKrcmtErAlAtdItemConToDomain(entity, atdItemCon))
							.collect(Collectors.toList());
			List<ErAlAttendanceItemCondition<?>> conditionsGroup2 = Optional
					.ofNullable(entity.krcmtErAlCondition.krcstErAlConGroup2)
					.orElse(new KrcstErAlConGroup("", new BigDecimal(0), new ArrayList<>())).lstAtdItemCon.stream()
							.map(atdItemCon -> convertKrcmtErAlAtdItemConToDomain(entity, atdItemCon))
							.collect(Collectors.toList());
			condition
					.createAttendanceItemCondition(entity.krcmtErAlCondition.operatorBetweenGroups.intValue(),
							entity.krcmtErAlCondition.group2UseAtr.intValue() == 1)
					.setAttendanceItemConditionGroup1(Optional.ofNullable(entity.krcmtErAlCondition.krcstErAlConGroup1)
							.orElse(new KrcstErAlConGroup("", new BigDecimal(0), new ArrayList<>())).conditionOperator
									.intValue(),
							conditionsGroup1)
					.setAttendanceItemConditionGroup2(Optional.ofNullable(entity.krcmtErAlCondition.krcstErAlConGroup2)
							.orElse(new KrcstErAlConGroup("", new BigDecimal(0), new ArrayList<>())).conditionOperator
									.intValue(),
							conditionsGroup2);
		}
		domain.setCondition(condition);
		return domain;
	}

}
