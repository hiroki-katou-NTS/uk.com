package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.daily;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ContinuousPeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.*;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * スケジュール日次の任意抽出条件 Entity
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KSCDT_SCHE_ANY_COND_DAY")
public class KscdtScheAnyCondDay extends ContractUkJpaEntity {
    @EmbeddedId
    public KscdtScheAnyCondDayPk pk;

    /* 名称 */
    @Column(name = "COND_NAME")
    public String condName;

    /* 使用区分 */
    @Column(name = "USE_ATR")
    public boolean useAtr;

    /* チェック項目種類 */
    @Column(name = "CHECK_TYPE")
    public int checkType;

    /* 対象とする勤務種類 */
    @Column(name = "WORKTYPE_COND_ATR")
    public int wrkTypeCondAtr;

    /* 連続期間 */
    @Column(name = "CONTINUOUS_PERIOD")
    public Integer conPeriod;

    /* 時間のチェック項目 */
    @Column(name = "TIME_CHECK_ITEM")
    public Integer timeCheckItem;

    /* 対象とする就業時間帯 */
    @Column(name = "WORKTIME_COND_ATR")
    public Integer wrkTimeCondAtr;

    @Override
    protected Object getKey() {
        return this.pk;
    }

    public ExtractionCondScheduleDay toDomain(List<KscdtScheConDayWt> wrkType, List<KscdtScheConDayWtime> wtime){

        // KrcstErAlCompareSingle
        // KrcstErAlCompareRange
        // 連続勤務種類の抽出条件
        CondContinuousWrkType wType = new CondContinuousWrkType(wrkType.stream().map(i->i.pk.wrkTypeCd).collect(Collectors.toList()), new ContinuousPeriod(conPeriod));
        // 連続時間帯の抽出条件
        CondContinuousTimeZone timeZone = new CondContinuousTimeZone(EnumAdaptor.valueOf(wrkTimeCondAtr, TimeZoneTargetRange.class),wrkType.stream().map(i->i.pk.wrkTypeCd).collect(Collectors.toList()),wtime.stream().map(i->i.pk.wrkTimeCd).collect(Collectors.toList()), new ContinuousPeriod(conPeriod));

        // 連続時間のチェック条件
        CondContinuousTime continousTime = new CondContinuousTime();



        // 時間のチェック条件
        CondTime condTime = new CondTime();


        val result = new ExtractionCondScheduleDay(pk.checkId,null,null,pk.sortBy,useAtr,new NameAlarmExtractCond(condName), EnumAdaptor.valueOf(wrkTypeCondAtr, RangeToCheck.class), Optional.ofNullable(null));

        return result;
    }
}
