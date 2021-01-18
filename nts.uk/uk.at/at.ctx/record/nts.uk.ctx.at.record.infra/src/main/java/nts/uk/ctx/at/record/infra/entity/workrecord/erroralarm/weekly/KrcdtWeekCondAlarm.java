package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.weekly;

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
    public int conMonth;

    /* メッセージ */
    @Column(name = "COND_MESSAGE")
    public String condMsg;

    @Override
    protected Object getKey() {
        return this.pk;
    }
}
