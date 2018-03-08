package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition;

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
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.KwrmtErAlWorkRecord;
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
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author hungnm 勤務実績のエラーアラームチェック
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_ERAL_CONDITION")
public class KrcmtErAlCondition extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@NotNull
	@Column(name = "ERAL_CHECK_ID")
	public String eralCheckId;

	@Basic(optional = true)
	@Column(name = "MESSAGE_DISPLAY")
	public String messageDisplay;
	
	@Column(name = "CONTINUOUS_PERIOD")
	public BigDecimal continuousPeriod;

	@Column(name = "FILTER_BY_BUSINESS_TYPE")
	public BigDecimal filterByBusinessType;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumns({ @JoinColumn(name = "ERAL_CHECK_ID", referencedColumnName = "ERAL_CHECK_ID", nullable = true) })
	public List<KrcstErAlBusinessType> lstBusinessType;

	@Column(name = "FILTER_BY_JOB_TITLE")
	public BigDecimal filterByJobTitle;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumns({ @JoinColumn(name = "ERAL_CHECK_ID", referencedColumnName = "ERAL_CHECK_ID", nullable = true) })
	public List<KrcstErAlJobTitle> lstJobTitle;

	@Column(name = "FILTER_BY_EMPLOYMENT")
	public BigDecimal filterByEmployment;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumns({ @JoinColumn(name = "ERAL_CHECK_ID", referencedColumnName = "ERAL_CHECK_ID", nullable = true) })
	public List<KrcstErAlEmployment> lstEmployment;

	@Column(name = "FILTER_BY_CLASSIFICATION")
	public BigDecimal filterByClassification;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumns({ @JoinColumn(name = "ERAL_CHECK_ID", referencedColumnName = "ERAL_CHECK_ID", nullable = true) })
	public List<KrcstErAlClass> lstClassification;

	@Column(name = "WORKTYPE_USE_ATR")
	public BigDecimal workTypeUseAtr;

	@Column(name = "WT_PLAN_ACTUAL_OPERATOR")
	public BigDecimal wtPlanActualOperator;

	@Column(name = "WT_PLAN_FILTER_ATR")
	public BigDecimal wtPlanFilterAtr;

	@Column(name = "WT_ACTUAL_FILTER_ATR")
	public BigDecimal wtActualFilterAtr;

	@Column(name = "WT_COMPARE_ATR")
	public BigDecimal wtCompareAtr;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumns({ @JoinColumn(name = "ERAL_CHECK_ID", referencedColumnName = "ERAL_CHECK_ID", nullable = true) })
	public List<KrcstErAlWtActual> lstWtActual;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumns({ @JoinColumn(name = "ERAL_CHECK_ID", referencedColumnName = "ERAL_CHECK_ID", nullable = true) })
	public List<KrcstErAlWtPlan> lstWtPlan;

	@Column(name = "WORKING_HOURS_USE_ATR")
	public BigDecimal workingHoursUseAtr;

	@Column(name = "WH_PLAN_ACTUAL_OPERATOR")
	public BigDecimal whPlanActualOperator;

	@Column(name = "WH_PLAN_FILTER_ATR")
	public BigDecimal whPlanFilterAtr;

	@Column(name = "WH_ACTUAL_FILTER_ATR")
	public BigDecimal whActualFilterAtr;

	@Column(name = "WH_COMPARE_ATR")
	public BigDecimal whCompareAtr;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumns({ @JoinColumn(name = "ERAL_CHECK_ID", referencedColumnName = "ERAL_CHECK_ID", nullable = true) })
	public List<KrcstErAlWhActual> lstWhActual;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumns({ @JoinColumn(name = "ERAL_CHECK_ID", referencedColumnName = "ERAL_CHECK_ID", nullable = true) })
	public List<KrcstErAlWhPlan> lstWhPlan;

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

	@OneToOne(mappedBy = "krcmtErAlCondition")
	public KwrmtErAlWorkRecord kwrmtErAlWorkRecord;

	@Override
	protected Object getKey() {
		return this.eralCheckId;
	}

	public KrcmtErAlCondition(String eralCheckId, String messageDisplay, BigDecimal filterByBusinessType,
			List<KrcstErAlBusinessType> lstBusinessType, BigDecimal filterByJobTitle,
			List<KrcstErAlJobTitle> lstJobTitle, BigDecimal filterByEmployment, List<KrcstErAlEmployment> lstEmployment,
			BigDecimal filterByClassification, List<KrcstErAlClass> lstClassification, BigDecimal workTypeUseAtr,
			BigDecimal wtPlanActualOperator, BigDecimal wtPlanFilterAtr, BigDecimal wtActualFilterAtr,
			BigDecimal wtCompareAtr, List<KrcstErAlWtActual> lstWtActual, List<KrcstErAlWtPlan> lstWtPlan,
			BigDecimal workingHoursUseAtr, BigDecimal whPlanActualOperator, BigDecimal whPlanFilterAtr,
			BigDecimal whActualFilterAtr, BigDecimal whCompareAtr, List<KrcstErAlWhActual> lstWhActual,
			List<KrcstErAlWhPlan> lstWhPlan, BigDecimal operatorBetweenGroups, BigDecimal group2UseAtr,
			String atdItemConditionGroup1, KrcstErAlConGroup krcstErAlConGroup1, String atdItemConditionGroup2,
			KrcstErAlConGroup krcstErAlConGroup2, BigDecimal continuousPeriod) {
		super();
		this.eralCheckId = eralCheckId;
		this.messageDisplay = messageDisplay;
		this.filterByBusinessType = filterByBusinessType;
		this.lstBusinessType = lstBusinessType;
		this.filterByJobTitle = filterByJobTitle;
		this.lstJobTitle = lstJobTitle;
		this.filterByEmployment = filterByEmployment;
		this.lstEmployment = lstEmployment;
		this.filterByClassification = filterByClassification;
		this.lstClassification = lstClassification;
		this.workTypeUseAtr = workTypeUseAtr;
		this.wtPlanActualOperator = wtPlanActualOperator;
		this.wtPlanFilterAtr = wtPlanFilterAtr;
		this.wtActualFilterAtr = wtActualFilterAtr;
		this.wtCompareAtr = wtCompareAtr;
		this.lstWtActual = lstWtActual;
		this.lstWtPlan = lstWtPlan;
		this.workingHoursUseAtr = workingHoursUseAtr;
		this.whPlanActualOperator = whPlanActualOperator;
		this.whPlanFilterAtr = whPlanFilterAtr;
		this.whActualFilterAtr = whActualFilterAtr;
		this.whCompareAtr = whCompareAtr;
		this.lstWhActual = lstWhActual;
		this.lstWhPlan = lstWhPlan;
		this.operatorBetweenGroups = operatorBetweenGroups;
		this.group2UseAtr = group2UseAtr;
		this.atdItemConditionGroup1 = atdItemConditionGroup1;
		this.krcstErAlConGroup1 = krcstErAlConGroup1;
		this.atdItemConditionGroup2 = atdItemConditionGroup2;
		this.krcstErAlConGroup2 = krcstErAlConGroup2;
		this.continuousPeriod = continuousPeriod;
	}

	public static KrcmtErAlCondition fromDomain(ErrorAlarmCondition conditionDomain) {
		String eralCheckId = conditionDomain.getErrorAlarmCheckID();
		String displayMessage = conditionDomain.getDisplayMessage().v();
		KrcmtErAlCondition krcmtErAlCondition = new KrcmtErAlCondition(eralCheckId, displayMessage, new BigDecimal(0),
				Collections.emptyList(), new BigDecimal(0), Collections.emptyList(), new BigDecimal(0),
				Collections.emptyList(), new BigDecimal(0), Collections.emptyList(), new BigDecimal(0),
				new BigDecimal(0), null, null, null, Collections.emptyList(), Collections.emptyList(),
				new BigDecimal(0), new BigDecimal(0), null, null, null, Collections.emptyList(),
				Collections.emptyList(), new BigDecimal(0), new BigDecimal(0), "0", null, null, null, null);
			// Set Check target condition
		//if (!domain.getFixedAtr()) {
			BigDecimal filterByBusinessType = conditionDomain.getCheckTargetCondtion().getFilterByBusinessType()
					? new BigDecimal(1) : new BigDecimal(0);
			BigDecimal filterByJobTitle = new BigDecimal(
					conditionDomain.getCheckTargetCondtion().getFilterByJobTitle() ? 1 : 0);
			BigDecimal filterByEmployment = new BigDecimal(
					conditionDomain.getCheckTargetCondtion().getFilterByEmployment() ? 1 : 0);
			BigDecimal filterByClassification = new BigDecimal(
					conditionDomain.getCheckTargetCondtion().getFilterByClassification() ? 1 : 0);
			List<KrcstErAlBusinessType> lstBusinessType = conditionDomain.getCheckTargetCondtion()
					.getLstBusinessTypeCode().stream().map(businessTypeCd -> new KrcstErAlBusinessType(
							new KrcstErAlBusinessTypePK(eralCheckId, businessTypeCd.v())))
					.collect(Collectors.toList());
			List<KrcstErAlJobTitle> lstJobTitle = conditionDomain.getCheckTargetCondtion().getLstJobTitleId().stream()
					.map(jobTitleId -> new KrcstErAlJobTitle(new KrcstErAlJobTitlePK(eralCheckId, jobTitleId)))
					.collect(Collectors.toList());
			List<KrcstErAlEmployment> lstEmployment = conditionDomain.getCheckTargetCondtion().getLstEmploymentCode()
					.stream().map(emptCd -> new KrcstErAlEmployment(new KrcstErAlEmploymentPK(eralCheckId, emptCd.v())))
					.collect(Collectors.toList());
			List<KrcstErAlClass> lstClassification = conditionDomain.getCheckTargetCondtion().getLstClassificationCode()
					.stream().map(clssCd -> new KrcstErAlClass(new KrcstErAlClassPK(eralCheckId, clssCd.v())))
					.collect(Collectors.toList());
			// Set worktype condition
			BigDecimal workTypeUseAtr = new BigDecimal(conditionDomain.getWorkTypeCondition().getUseAtr() ? 1 : 0);
			BigDecimal wtCompareAtr = new BigDecimal(
					conditionDomain.getWorkTypeCondition().getComparePlanAndActual().value);
			BigDecimal wtPlanActualOperator = new BigDecimal(0);
			BigDecimal wtPlanFilterAtr = new BigDecimal(0);
			BigDecimal wtActualFilterAtr = new BigDecimal(0);
			List<KrcstErAlWtPlan> lstWtPlan = new ArrayList<>();
			List<KrcstErAlWtActual> lstWtActual = new ArrayList<>();
			if (wtCompareAtr.intValue() != FilterByCompare.EXTRACT_SAME.value) {
				PlanActualWorkType wtypeCondition = (PlanActualWorkType) conditionDomain.getWorkTypeCondition();
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
				SingleWorkType wtypeCondition = (SingleWorkType) conditionDomain.getWorkTypeCondition();
				wtPlanFilterAtr = new BigDecimal(wtypeCondition.getTargetWorkType().getFilterAtr() ? 1 : 0);
				lstWtPlan = wtypeCondition.getTargetWorkType().getLstWorkType().stream()
						.map(wtCode -> new KrcstErAlWtPlan(new KrcstErAlWtPlanActualPK(eralCheckId, wtCode.v())))
						.collect(Collectors.toList());
			}
			// Set worktime condition
			BigDecimal workingHoursUseAtr = new BigDecimal(conditionDomain.getWorkTimeCondition().getUseAtr() ? 1 : 0);
			BigDecimal whCompareAtr = new BigDecimal(
					conditionDomain.getWorkTimeCondition().getComparePlanAndActual().value);
			BigDecimal whPlanActualOperator = new BigDecimal(0);
			BigDecimal whPlanFilterAtr = new BigDecimal(0);
			BigDecimal whActualFilterAtr = new BigDecimal(0);
			List<KrcstErAlWhPlan> lstWhPlan = new ArrayList<>();
			List<KrcstErAlWhActual> lstWhActual = new ArrayList<>();
			if (whCompareAtr.intValue() != FilterByCompare.EXTRACT_SAME.value) {
				PlanActualWorkTime wtimeCondition = (PlanActualWorkTime) conditionDomain.getWorkTimeCondition();
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
				SingleWorkTime wtimeCondition = (SingleWorkTime) conditionDomain.getWorkTimeCondition();
				whPlanFilterAtr = new BigDecimal(wtimeCondition.getTargetWorkTime().getFilterAtr() ? 1 : 0);
				lstWhPlan = wtimeCondition.getTargetWorkTime().getLstWorkTime().stream()
						.map(wtCode -> new KrcstErAlWhPlan(new KrcstErAlWhPlanActualPK(eralCheckId, wtCode.v())))
						.collect(Collectors.toList());
			}
			// Set attendance item condition
			BigDecimal operatorBetweenGroups = new BigDecimal(
					conditionDomain.getAtdItemCondition().getOperatorBetweenGroups().value);
			BigDecimal group2UseAtr = new BigDecimal(conditionDomain.getAtdItemCondition().getGroup2UseAtr() ? 1 : 0);
			String atdItemConditionGroup1 = conditionDomain.getAtdItemCondition().getGroup1().getAtdItemConGroupId();
			String atdItemConditionGroup2 = conditionDomain.getAtdItemCondition().getGroup2().getAtdItemConGroupId();
			BigDecimal conditionOperator1 = new BigDecimal(
					conditionDomain.getAtdItemCondition().getGroup1().getConditionOperator().value);
			List<KrcmtErAlAtdItemCon> lstAtdItemCon1 = conditionDomain.getAtdItemCondition().getGroup1()
					.getLstErAlAtdItemCon().stream()
					.map(erAlAtdItemCon -> getKrcmtErAlAtdItemConFromDomain(atdItemConditionGroup1, erAlAtdItemCon))
					.collect(Collectors.toList());
			KrcstErAlConGroup krcstErAlConGroup1 = new KrcstErAlConGroup(atdItemConditionGroup1, conditionOperator1,
					lstAtdItemCon1);
			BigDecimal conditionOperator2 = new BigDecimal(
					conditionDomain.getAtdItemCondition().getGroup2().getConditionOperator().value);
			List<KrcmtErAlAtdItemCon> lstAtdItemCon2 = conditionDomain.getAtdItemCondition().getGroup2()
					.getLstErAlAtdItemCon().stream()
					.map(erAlAtdItemCon -> getKrcmtErAlAtdItemConFromDomain(atdItemConditionGroup2, erAlAtdItemCon))
					.collect(Collectors.toList());
			KrcstErAlConGroup krcstErAlConGroup2 = new KrcstErAlConGroup(atdItemConditionGroup2, conditionOperator2,
					lstAtdItemCon2);
			krcmtErAlCondition = new KrcmtErAlCondition(eralCheckId, displayMessage, filterByBusinessType,
					lstBusinessType, filterByJobTitle, lstJobTitle, filterByEmployment, lstEmployment,
					filterByClassification, lstClassification, workTypeUseAtr, wtPlanActualOperator, wtPlanFilterAtr,
					wtActualFilterAtr, wtCompareAtr, lstWtActual, lstWtPlan, workingHoursUseAtr, whPlanActualOperator,
					whPlanFilterAtr, whActualFilterAtr, whCompareAtr, lstWhActual, lstWhPlan, operatorBetweenGroups,
					group2UseAtr, atdItemConditionGroup1, krcstErAlConGroup1, atdItemConditionGroup2,
					krcstErAlConGroup2, conditionDomain.getContinuousPeriod() != null
							? new BigDecimal(conditionDomain.getContinuousPeriod().v()) : null);
		//}
		return krcmtErAlCondition;
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
	
	public static ErrorAlarmCondition toDomain(KrcmtErAlCondition entity, String companyId, String errorAlarmCode) {
		ErrorAlarmCondition condition = ErrorAlarmCondition.init();
		condition.setDisplayMessage(entity.messageDisplay);
		if (entity.continuousPeriod != null) {
			condition.setContinuousPeriod(entity.continuousPeriod.intValue());
		}
		//if (entity.fixedAtr.intValue() != 1) {
			// Set AlCheckTargetCondition
			condition.createAlCheckTargetCondition((entity.filterByBusinessType != null && entity.filterByBusinessType.intValue() == 1),
					(entity.filterByJobTitle != null && entity.filterByJobTitle.intValue() == 1),
					(entity.filterByEmployment != null && entity.filterByEmployment.intValue() == 1),
					(entity.filterByClassification != null && entity.filterByClassification.intValue() == 1),
					Optional.ofNullable(entity.lstBusinessType).orElse(Collections.emptyList())
							.stream().map(businessType -> businessType.krcstErAlBusinessTypePK.businessTypeCd)
							.collect(Collectors.toList()),
					Optional.ofNullable(entity.lstJobTitle).orElse(Collections.emptyList()).stream()
							.map(jobTitle -> jobTitle.krcstErAlJobTitlePK.jobId).collect(Collectors.toList()),
					Optional.ofNullable(entity.lstEmployment).orElse(Collections.emptyList())
							.stream().map(empt -> empt.krcstErAlEmploymentPK.emptcd).collect(Collectors.toList()),
					Optional.ofNullable(entity.lstClassification).orElse(Collections.emptyList())
							.stream().map(clss -> clss.krcstErAlClassPK.clscd).collect(Collectors.toList()));
			// Set WorkTypeCondition
			
			condition.createWorkTypeCondition((entity.workTypeUseAtr != null && entity.workTypeUseAtr.intValue() == 1),
					(entity.wtCompareAtr == null ? 0 : entity.wtCompareAtr.intValue()));
			if (entity.wtCompareAtr != null && entity.wtCompareAtr.intValue() != FilterByCompare.EXTRACT_SAME.value) {
				condition.setWorkTypePlan((entity.wtPlanFilterAtr != null && entity.wtPlanFilterAtr.intValue() == 1),
						Optional.ofNullable(entity.lstWtPlan).orElse(Collections.emptyList())
								.stream().map(wtype -> wtype.krcstErAlWtPlanPK.workTypeCode)
								.collect(Collectors.toList()));
				condition.setWorkTypeActual((entity.wtActualFilterAtr != null &&entity.wtActualFilterAtr.intValue() == 1),
						Optional.ofNullable(entity.lstWtActual).orElse(Collections.emptyList())
								.stream().map(wtype -> wtype.krcstErAlWtPlanActualPK.workTypeCode)
								.collect(Collectors.toList()));
				condition.chooseWorkTypeOperator(entity.wtPlanActualOperator == null ? 0 : entity.wtPlanActualOperator.intValue());
			} else {
				condition.setWorkTypeSingle((entity.wtPlanFilterAtr != null && entity.wtPlanFilterAtr.intValue() == 1),
						Optional.ofNullable(entity.lstWtPlan).orElse(Collections.emptyList())
								.stream().map(wtype -> wtype.krcstErAlWtPlanPK.workTypeCode)
								.collect(Collectors.toList()));
			}
			// Set WorkTimeCondtion
			condition.createWorkTimeCondition((entity.workingHoursUseAtr != null && entity.workingHoursUseAtr.intValue() == 1),
					entity.whCompareAtr == null ? 0 : entity.whCompareAtr.intValue());
			if (entity.whCompareAtr != null && entity.whCompareAtr.intValue() != FilterByCompare.EXTRACT_SAME.value) {
				condition.setWorkTimePlan((entity.whPlanFilterAtr != null && entity.whPlanFilterAtr.intValue() == 1),
						Optional.ofNullable(entity.lstWhPlan).orElse(Collections.emptyList())
								.stream().map(wtime -> wtime.krcstErAlWhPlanActualPK.workTimeCode)
								.collect(Collectors.toList()));
				condition.setWorkTimeActual((entity.whActualFilterAtr != null && entity.whActualFilterAtr.intValue() == 1),
						Optional.ofNullable(entity.lstWhActual).orElse(Collections.emptyList())
								.stream().map(wtime -> wtime.krcstErAlWhPlanActualPK.workTimeCode)
								.collect(Collectors.toList()));
				condition.chooseWorkTimeOperator(entity.whPlanActualOperator == null ? 0 : entity.whPlanActualOperator.intValue());
			} else {
				condition.setWorkTimeSingle((entity.whPlanFilterAtr != null && entity.whPlanFilterAtr.intValue() == 1),
						Optional.ofNullable(entity.lstWhPlan).orElse(Collections.emptyList())
								.stream().map(wtime -> wtime.krcstErAlWhPlanActualPK.workTimeCode)
								.collect(Collectors.toList()));
			}
			// Set AttendanceItemCondition
			List<ErAlAttendanceItemCondition<?>> conditionsGroup1 = Optional
					.ofNullable(entity.krcstErAlConGroup1)
					.orElse(new KrcstErAlConGroup("", new BigDecimal(0), new ArrayList<>())).lstAtdItemCon.stream()
							.map(atdItemCon -> convertKrcmtErAlAtdItemConToDomain(entity, atdItemCon, companyId, errorAlarmCode))
							.collect(Collectors.toList());
			List<ErAlAttendanceItemCondition<?>> conditionsGroup2 = Optional
					.ofNullable(entity.krcstErAlConGroup2)
					.orElse(new KrcstErAlConGroup("", new BigDecimal(0), new ArrayList<>())).lstAtdItemCon.stream()
							.map(atdItemCon -> convertKrcmtErAlAtdItemConToDomain(entity, atdItemCon, companyId, errorAlarmCode))
							.collect(Collectors.toList());
			condition
					.createAttendanceItemCondition((entity.operatorBetweenGroups == null ? 0 : entity.operatorBetweenGroups.intValue()),
							(entity.group2UseAtr != null && entity.group2UseAtr.intValue() == 1))
					.setAttendanceItemConditionGroup1(Optional.ofNullable(entity.krcstErAlConGroup1)
							.orElse(new KrcstErAlConGroup("", new BigDecimal(0), new ArrayList<>())).conditionOperator
									.intValue(),
							conditionsGroup1)
					.setAttendanceItemConditionGroup2(Optional.ofNullable(entity.krcstErAlConGroup2)
							.orElse(new KrcstErAlConGroup("", new BigDecimal(0), new ArrayList<>())).conditionOperator
									.intValue(),
							conditionsGroup2);
		//}
		condition.setCheckId(entity.eralCheckId);
		return condition;
	}
	private static ErAlAttendanceItemCondition<?> convertKrcmtErAlAtdItemConToDomain(KrcmtErAlCondition entity,
			KrcmtErAlAtdItemCon atdItemCon, String companyId, String errorAlarmCode) {
		ErAlAttendanceItemCondition<Object> atdItemConDomain = new ErAlAttendanceItemCondition<Object>(
				companyId, errorAlarmCode,
				(atdItemCon.krcmtErAlAtdItemConPK.atdItemConNo == null ? 0 : atdItemCon.krcmtErAlAtdItemConPK.atdItemConNo.intValue())
				, (atdItemCon.conditionAtr == null ? 0 : atdItemCon.conditionAtr.intValue())
				, (atdItemCon.useAtr != null && atdItemCon.useAtr.intValue() == 1));
		// Set Target
		if (atdItemCon.conditionAtr != null && atdItemCon.conditionAtr.intValue() == ConditionAtr.TIME_WITH_DAY.value) {
			atdItemConDomain.setUncountableTarget(
					Optional.ofNullable(atdItemCon.lstAtdItemTarget).orElse(Collections.emptyList()).stream()
							.filter(atdItemTarget -> (atdItemTarget.targetAtr != null && atdItemTarget.targetAtr.intValue() == 2)).findFirst()
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
			if (atdItemCon.conditionAtr != null && atdItemCon.conditionAtr.intValue() == ConditionAtr.AMOUNT_VALUE.value) {
				atdItemConDomain.setCompareRange(atdItemCon.erAlCompareRange.compareAtr.intValue(),
						new CheckedAmountValue(atdItemCon.erAlCompareRange.startValue.intValue()),
						new CheckedAmountValue(atdItemCon.erAlCompareRange.endValue.intValue()));
			} else if (atdItemCon.conditionAtr != null && atdItemCon.conditionAtr.intValue() == ConditionAtr.TIME_DURATION.value) {
				atdItemConDomain.setCompareRange(atdItemCon.erAlCompareRange.compareAtr.intValue(),
						new CheckedTimeDuration(atdItemCon.erAlCompareRange.startValue.intValue()),
						new CheckedTimeDuration(atdItemCon.erAlCompareRange.endValue.intValue()));
			} else if (atdItemCon.conditionAtr != null && atdItemCon.conditionAtr.intValue() == ConditionAtr.TIME_WITH_DAY.value) {
				atdItemConDomain.setCompareRange(atdItemCon.erAlCompareRange.compareAtr.intValue(),
						new TimeWithDayAttr(atdItemCon.erAlCompareRange.startValue.intValue()),
						new TimeWithDayAttr(atdItemCon.erAlCompareRange.endValue.intValue()));
			} else if (atdItemCon.conditionAtr != null && atdItemCon.conditionAtr.intValue() == ConditionAtr.TIMES.value) {
				atdItemConDomain.setCompareRange(atdItemCon.erAlCompareRange.compareAtr.intValue(),
						new CheckedTimesValue(atdItemCon.erAlCompareRange.startValue.intValue()),
						new CheckedTimesValue(atdItemCon.erAlCompareRange.endValue.intValue()));
			}
		} else if (atdItemCon.erAlCompareSingle != null) {
			if (atdItemCon.erAlCompareSingle.conditionType != null 
					&& atdItemCon.erAlCompareSingle.conditionType.intValue() == ConditionType.FIXED_VALUE.value) {
				if (atdItemCon.conditionAtr != null && atdItemCon.conditionAtr.intValue() == ConditionAtr.AMOUNT_VALUE.value) {
					atdItemConDomain.setCompareSingleValue(atdItemCon.erAlCompareSingle.compareAtr.intValue(),
							atdItemCon.erAlCompareSingle.conditionType.intValue(),
							new CheckedAmountValue(atdItemCon.erAlSingleFixed.fixedValue.intValue()));
				} else if (atdItemCon.conditionAtr != null && atdItemCon.conditionAtr.intValue() == ConditionAtr.TIME_DURATION.value) {
					atdItemConDomain.setCompareSingleValue(atdItemCon.erAlCompareSingle.compareAtr == null ? 0 : atdItemCon.erAlCompareSingle.compareAtr.intValue(),
							atdItemCon.erAlCompareSingle.conditionType == null ? 0 : atdItemCon.erAlCompareSingle.conditionType.intValue(),
							new CheckedTimeDuration(atdItemCon.erAlSingleFixed.fixedValue == null ? 0 : atdItemCon.erAlSingleFixed.fixedValue.intValue()));
				} else if (atdItemCon.conditionAtr != null && atdItemCon.conditionAtr.intValue() == ConditionAtr.TIME_WITH_DAY.value) {
					atdItemConDomain.setCompareSingleValue(atdItemCon.erAlCompareSingle.compareAtr == null ? 0 : atdItemCon.erAlCompareSingle.compareAtr.intValue(),
							atdItemCon.erAlCompareSingle.conditionType == null ? 0 : atdItemCon.erAlCompareSingle.conditionType.intValue(),
							new TimeWithDayAttr(atdItemCon.erAlSingleFixed.fixedValue == null ? 0 : atdItemCon.erAlSingleFixed.fixedValue.intValue()));
				} else if (atdItemCon.conditionAtr != null && atdItemCon.conditionAtr.intValue() == ConditionAtr.TIMES.value) {
					atdItemConDomain.setCompareSingleValue(atdItemCon.erAlCompareSingle.compareAtr == null ? 0 : atdItemCon.erAlCompareSingle.compareAtr.intValue(),
							atdItemCon.erAlCompareSingle.conditionType == null ? 0 : atdItemCon.erAlCompareSingle.conditionType.intValue(),
							new CheckedTimesValue(atdItemCon.erAlSingleFixed.fixedValue == null ? 0 : atdItemCon.erAlSingleFixed.fixedValue.intValue()));
				}
			} else {
				atdItemConDomain.setCompareSingleValue(atdItemCon.erAlCompareSingle.compareAtr == null ? 0 : atdItemCon.erAlCompareSingle.compareAtr.intValue(),
						atdItemCon.erAlCompareSingle.conditionType == null ? 0 : atdItemCon.erAlCompareSingle.conditionType.intValue(),
						atdItemCon.erAlSingleAtd.get(0).krcstEralSingleAtdPK.attendanceItemId.intValue());
			}
		}
		return atdItemConDomain;
	}
}
