package nts.uk.ctx.at.schedule.infra.entity.schedule.alarmsetting.alarmlist.daily;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

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
    public int conPeriod;

    /* 時間のチェック項目 */
    @Column(name = "TIME_CHECK_ITEM")
    public int timeCheckItem;

    /* 対象とする就業時間帯 */
    @Column(name = "WORKTIME_COND_ATR")
    public int wrkTimeCondAtr;

    @Override
    protected Object getKey() {
        return this.pk;
    }
}
