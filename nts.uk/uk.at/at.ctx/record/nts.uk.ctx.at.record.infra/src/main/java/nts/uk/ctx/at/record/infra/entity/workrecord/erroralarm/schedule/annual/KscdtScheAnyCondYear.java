package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.annual;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CheckedCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareRange;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareSingleValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual.*;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.NameAlarmExtractCond;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.TypeOfDays;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.TypeOfTime;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareRange;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareSingle;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlSingleFixed;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmMessage;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.*;
import java.util.Optional;

/**
 * スケジュール年間の任意抽出条件 Entity
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KSCDT_SCHE_ANY_COND_YEAR")
public class KscdtScheAnyCondYear extends ContractUkJpaEntity {

    @EmbeddedId
    public KscdtScheAnyCondYearPk pk;

    /* 使用区分 */
    @Column(name = "USE_ATR")
    public boolean useAtr;

    /* 名称 */
    @Column(name = "COND_NAME")
    public String condName;

    /* チェック項目種類 */
    @Column(name = "COND_TYPE")
    public int condType;

    /* スケジュール年間チェック条件 */
    @Column(name = "CHECK_TYPE")
    public int checkType;

    /* メッセージ */
    @Column(name = "COND_MESSAGE")
    public String condMsg;

    @Override
    protected Object getKey() {
        return this.pk;
    }

    public ExtractionCondScheduleYear toDomain(KrcstErAlCompareSingle single, KrcstErAlSingleFixed singleFixed, KrcstErAlCompareRange range){

        CheckedCondition checkedCondition = null;
        if (single != null) {
            checkedCondition = new CompareSingleValue<>(single.compareAtr, single.conditionType);
            ((CompareSingleValue) checkedCondition).setValue(singleFixed.fixedValue);
        } else if (range != null) {
            checkedCondition = new CompareRange<>(range.compareAtr);
            ((CompareRange) checkedCondition).setStartValue(range.startValue);
            ((CompareRange) checkedCondition).setEndValue(range.endValue);
        }

        ScheduleYearCheckCond scheduleYearCheckCond = null;
        if (condType == YearCheckItemType.DAY_NUMBER.value){
            scheduleYearCheckCond = new DayCheckCond(EnumAdaptor.valueOf(checkType, TypeOfDays.class));
        } else {
            scheduleYearCheckCond = new TimeCheckCond(EnumAdaptor.valueOf(checkType, TypeOfTime.class));
        }
        return new ExtractionCondScheduleYear(pk.checkId,scheduleYearCheckCond,checkedCondition, EnumAdaptor.valueOf(condType, YearCheckItemType.class),pk.sortBy,useAtr,new NameAlarmExtractCond(condName), Optional.ofNullable(condMsg == null ? null : new ErrorAlarmMessage(condMsg)));
    }
}
