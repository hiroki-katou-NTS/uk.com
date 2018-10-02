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

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.ErAlAttendanceItemCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionAtr;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.AttendanceItemId;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedAmountValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimeDuration;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimesValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimesValueDay;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.multimonth.KrcmtMulMonCond;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.multimonth.KrcmtMulMonCondAvg;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.multimonth.KrcmtMulMonCondCont;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.multimonth.KrcmtMulMonCondCosp;
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
	@Column(name = "CONDITION_ATR")
	public int conditionAtr;

	@Basic(optional = false)
	@Column(name = "USE_ATR")
	public int useAtr;
	
	@Basic(optional = false)
	@Column(name = "CONDITION_TYPE")
	public int type;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CONDITION_GROUP_ID", referencedColumnName = "CONDITION_GROUP_ID", nullable = false),
			@JoinColumn(name = "ATD_ITEM_CON_NO", referencedColumnName = "ATD_ITEM_CON_NO", nullable = false) })
	public List<KrcstErAlAtdTarget> lstAtdItemTarget;

	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcmtErAlAtdItemCon", orphanRemoval=true)
	public KrcstErAlCompareSingle erAlCompareSingle;

	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcmtErAlAtdItemCon", orphanRemoval=true)
	public KrcstErAlCompareRange erAlCompareRange;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcmtErAlAtdItemCon", orphanRemoval=true)
	public KrcstErAlInputCheck erAlInputCheck;

	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcmtErAlAtdItemCon", orphanRemoval=true)
	public KrcstErAlSingleFixed erAlSingleFixed;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CONDITION_GROUP_ID", referencedColumnName = "CONDITION_GROUP_ID", nullable = false),
			@JoinColumn(name = "ATD_ITEM_CON_NO", referencedColumnName = "ATD_ITEM_CON_NO", nullable = false) })
	public List<KrcstErAlSingleAtd> erAlSingleAtd;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "CONDITION_GROUP_ID", referencedColumnName = "CONDITION_GROUP_ID", insertable = false, updatable = false) })
	public KrcstErAlConGroup krcstErAlConGroup;

	@OneToOne
	@JoinColumns({ @JoinColumn(name = "CONDITION_GROUP_ID", referencedColumnName = "ERAL_CHECK_ID", insertable = false, updatable = false) })
	public KrcmtMulMonCond krcmtMulMonCond;
	
	@OneToOne
	@JoinColumns({ @JoinColumn(name = "CONDITION_GROUP_ID", referencedColumnName = "ERAL_CHECK_ID", insertable = false, updatable = false) })
	public KrcmtMulMonCondAvg krcmtMulMonCondAvg;
	
	@OneToOne
	@JoinColumns({ @JoinColumn(name = "CONDITION_GROUP_ID", referencedColumnName = "ERAL_CHECK_ID", insertable = false, updatable = false) })
	public KrcmtMulMonCondCont krcmtMulMonCondCont;
	
	@OneToOne
	@JoinColumns({ @JoinColumn(name = "CONDITION_GROUP_ID", referencedColumnName = "ERAL_CHECK_ID", insertable = false, updatable = false) })
	public KrcmtMulMonCondCosp krcmtMulMonCondCosp;
	
	@Override
	protected Object getKey() {
		return this.krcmtErAlAtdItemConPK;
	}

	public KrcmtErAlAtdItemCon(KrcmtErAlAtdItemConPK krcmtErAlAtdItemConPK, int conditionAtr, int useAtr, int type,
			List<KrcstErAlAtdTarget> lstAtdItemTarget, KrcstErAlCompareSingle erAlCompareSingle,
			KrcstErAlCompareRange erAlCompareRange, KrcstErAlInputCheck erAlInputCheck,
			KrcstErAlSingleFixed erAlSingleFixed, List<KrcstErAlSingleAtd> erAlSingleAtd) {
		super();
		this.krcmtErAlAtdItemConPK = krcmtErAlAtdItemConPK;
		this.conditionAtr = conditionAtr;
		this.useAtr = useAtr;
		this.type = type;
		this.lstAtdItemTarget = lstAtdItemTarget;
		this.erAlCompareSingle = erAlCompareSingle;
		this.erAlCompareRange = erAlCompareRange;
		this.erAlInputCheck = erAlInputCheck;
		this.erAlSingleFixed = erAlSingleFixed;
		this.erAlSingleAtd = erAlSingleAtd;
	}
	
    
    public static KrcmtErAlAtdItemCon toEntity(String atdItemConditionGroup1,
            ErAlAttendanceItemCondition<?> erAlAtdItemCon, boolean isGroupOne) {
        KrcmtErAlAtdItemConPK krcmtErAlAtdItemConPK = new KrcmtErAlAtdItemConPK(atdItemConditionGroup1,
                erAlAtdItemCon.getTargetNO());
        List<KrcstErAlAtdTarget> lstAtdItemTarget = new ArrayList<>();
        if (erAlAtdItemCon.getConditionAtr() == ConditionAtr.TIME_WITH_DAY) {
            lstAtdItemTarget.add(new KrcstErAlAtdTarget(
                    new KrcstErAlAtdTargetPK(atdItemConditionGroup1, erAlAtdItemCon.getTargetNO(),
                            erAlAtdItemCon.getUncountableTarget().getAttendanceItem()),2));
        } else {
            List<KrcstErAlAtdTarget> lstAtdItemTargetAdd = erAlAtdItemCon.getCountableTarget()
                    .getAddSubAttendanceItems().getAdditionAttendanceItems().stream()
                    .map(atdItemId -> new KrcstErAlAtdTarget(
                            new KrcstErAlAtdTargetPK(atdItemConditionGroup1,
                                    erAlAtdItemCon.getTargetNO(), atdItemId),0))
                    .collect(Collectors.toList());
            List<KrcstErAlAtdTarget> lstAtdItemTargetSub = erAlAtdItemCon.getCountableTarget()
                    .getAddSubAttendanceItems().getSubstractionAttendanceItems().stream()
                    .map(atdItemId -> new KrcstErAlAtdTarget(
                            new KrcstErAlAtdTargetPK(atdItemConditionGroup1,
                                    erAlAtdItemCon.getTargetNO(),atdItemId),1))
                    .collect(Collectors.toList());
            lstAtdItemTarget.addAll(lstAtdItemTargetAdd);
            lstAtdItemTarget.addAll(lstAtdItemTargetSub);
        }
        int compareAtr =0;
        int conditionType = 0;
        int startValue = 0;
        int endValue = 0;
        KrcstErAlCompareSingle erAlCompareSingle = null;
        KrcstErAlCompareRange erAlCompareRange = null;
        KrcstErAlInputCheck erAlInputCheck = null;
        KrcstErAlSingleFixed erAlSingleFixed = null;
        List<KrcstErAlSingleAtd> erAlSingleAtd = new ArrayList<>();
        if (erAlAtdItemCon.getCompareRange() != null) {
            compareAtr = erAlAtdItemCon.getCompareRange().getCompareOperator().value;
            if (erAlAtdItemCon.getConditionAtr() == ConditionAtr.AMOUNT_VALUE) {
                startValue = 
                        ((CheckedAmountValue) erAlAtdItemCon.getCompareRange().getStartValue()).v();
                endValue = ((CheckedAmountValue) erAlAtdItemCon.getCompareRange().getEndValue()).v();
            } else if (erAlAtdItemCon.getConditionAtr() == ConditionAtr.TIME_DURATION) {
                startValue =
                        ((CheckedTimeDuration) erAlAtdItemCon.getCompareRange().getStartValue()).v();
                endValue = ((CheckedTimeDuration) erAlAtdItemCon.getCompareRange().getEndValue()).v();
            } else if (erAlAtdItemCon.getConditionAtr() == ConditionAtr.TIME_WITH_DAY) {
                startValue = ((TimeWithDayAttr) erAlAtdItemCon.getCompareRange().getStartValue()).v();
                endValue = ((TimeWithDayAttr) erAlAtdItemCon.getCompareRange().getEndValue()).v();
            } else if (erAlAtdItemCon.getConditionAtr() == ConditionAtr.TIMES || erAlAtdItemCon.getConditionAtr() == ConditionAtr.DAYS) {
                startValue = ((CheckedTimesValue) erAlAtdItemCon.getCompareRange().getStartValue()).v();
                endValue = ((CheckedTimesValue) erAlAtdItemCon.getCompareRange().getEndValue()).v();
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
                int fixedValue = 0;
                if (erAlAtdItemCon.getConditionAtr() == ConditionAtr.AMOUNT_VALUE) {
                    fixedValue = 
                            ((CheckedAmountValue) erAlAtdItemCon.getCompareSingleValue().getValue()).v();
                } else if (erAlAtdItemCon.getConditionAtr() == ConditionAtr.TIME_DURATION) {
                    fixedValue = 
                            ((CheckedTimeDuration) erAlAtdItemCon.getCompareSingleValue().getValue()).v();
                } else if (erAlAtdItemCon.getConditionAtr() == ConditionAtr.TIME_WITH_DAY) {
                    fixedValue = 
                            ((TimeWithDayAttr) erAlAtdItemCon.getCompareSingleValue().getValue()).v();
                } else if (erAlAtdItemCon.getConditionAtr() == ConditionAtr.TIMES||  erAlAtdItemCon.getConditionAtr() == ConditionAtr.DAYS) {
                    fixedValue =
                            ((CheckedTimesValue) erAlAtdItemCon.getCompareSingleValue().getValue()).v();
                }
                erAlSingleFixed = new KrcstErAlSingleFixed(new KrcstErAlSingleFixedPK(atdItemConditionGroup1,
                        erAlAtdItemCon.getTargetNO()), fixedValue);
            } else {
                erAlSingleAtd.add(new KrcstErAlSingleAtd(
                        new KrcstErAlSingleAtdPK(atdItemConditionGroup1, erAlAtdItemCon.getTargetNO(),
                                (((AttendanceItemId) erAlAtdItemCon.getCompareSingleValue().getValue()).v())),2));
            }
		} else if (erAlAtdItemCon.getInputCheck() != null) {
			erAlInputCheck = new KrcstErAlInputCheck(
					new KrcstErAlInputCheckPK(atdItemConditionGroup1, erAlAtdItemCon.getTargetNO()),
					erAlAtdItemCon.getInputCheck().getInputCheckCondition().value);
		}
		return new KrcmtErAlAtdItemCon(krcmtErAlAtdItemConPK, erAlAtdItemCon.getConditionAtr().value,
				isGroupOne == true ? 1 : erAlAtdItemCon.isUse() ? 1 : 0, erAlAtdItemCon.getType().value, lstAtdItemTarget, erAlCompareSingle,
				erAlCompareRange, erAlInputCheck, erAlSingleFixed, erAlSingleAtd);
    }
    
    @SuppressWarnings("unchecked")
    public <V> ErAlAttendanceItemCondition<V> toDomain(
            KrcmtErAlAtdItemCon atdItemCon, String companyId, String errorAlarmCode) {
		ErAlAttendanceItemCondition<V> atdItemConDomain = new ErAlAttendanceItemCondition<V>(companyId, errorAlarmCode,
				atdItemCon.krcmtErAlAtdItemConPK.atdItemConNo, atdItemCon.conditionAtr,
				atdItemCon.useAtr == 1 ? true : false, atdItemCon.type);
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
                } else if ( atdItemCon.conditionAtr == ConditionAtr.TIME_DURATION.value) {
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
        } else if (atdItemCon.erAlInputCheck != null) {
        	atdItemConDomain.setInputCheck(atdItemCon.erAlInputCheck.inputCheckCondition);
        }
        return atdItemConDomain;
    }

}
