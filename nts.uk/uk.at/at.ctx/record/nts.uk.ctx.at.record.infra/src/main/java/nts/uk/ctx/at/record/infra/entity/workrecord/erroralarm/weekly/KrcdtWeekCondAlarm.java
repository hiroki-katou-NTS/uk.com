package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.weekly;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CheckedCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareRange;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareSingleValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.ContinuousPeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.ExtractionCondWeekly;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.WeeklyCheckItemType;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareRange;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareSingle;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlSingleFixed;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmMessage;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Optional;

/**
 * 週別実績の任意抽出条件 Entity
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KRCDT_WEEK_COND_ALARM")
public class KrcdtWeekCondAlarm extends ContractUkJpaEntity {
    @EmbeddedId
    public KrcdtWeekCondAlarmPk pk;

    /* アラームリスト抽出条件の名称 */
    @Column(name = "COND_NAME")
    public String condName;

    /* 使用区分 */
    @Column(name = "USE_ATR")
    public boolean useAtr;

    /* 複数月チェック種類 */
    @Column(name = "TYPE_CHECK_ITEM")
    public int checkType;

    /* 連続期間 */
    @Column(name = "CONTINUOUS_MONTHS")
    public Integer conMonth;

    /* メッセージ */
    @Column(name = "COND_MESSAGE")
    public String condMsg;

    @Override
    protected Object getKey() {
        return this.pk;
    }

    public ExtractionCondWeekly toDomain(KrcstErAlCompareSingle single, KrcstErAlSingleFixed singleFixed, KrcstErAlCompareRange range){
        CheckedCondition checkedCondition = null;
        if (single != null) {
            checkedCondition = new CompareSingleValue<>(single.compareAtr, single.conditionType);
            ((CompareSingleValue) checkedCondition).setValue(singleFixed.fixedValue);
        } else if (range != null) {
            checkedCondition = new CompareRange<>(range.compareAtr);
            ((CompareRange) checkedCondition).setStartValue(range.startValue);
            ((CompareRange) checkedCondition).setEndValue(range.endValue);
        }
        return new ExtractionCondWeekly(
        		pk.checkId, checkedCondition, 
        		EnumAdaptor.valueOf(checkType, WeeklyCheckItemType.class),
        		pk.condNo, useAtr,
        		new ErrorAlarmWorkRecordName(condName), 
        		Optional.ofNullable(condMsg == null ? null : new ErrorAlarmMessage(condMsg)),
        		Optional.ofNullable(conMonth == null ? null : new ContinuousPeriod(conMonth)),
        		null);
    }
}
