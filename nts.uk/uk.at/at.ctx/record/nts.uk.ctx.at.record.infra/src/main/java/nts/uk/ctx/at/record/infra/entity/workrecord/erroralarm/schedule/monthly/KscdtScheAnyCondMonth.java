package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.schedule.monthly;

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
@Table(name = "KSCDT_SCHE_ANY_COND_MONTH")
public class KscdtScheAnyCondMonth extends ContractUkJpaEntity {
    @EmbeddedId
    public KscdtScheAnyCondMonthPk pk;

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

    /* 特別休暇コード */
    @Column(name = "SPE_VACATION_CD")
    public String speVacCd;

    @Override
    protected Object getKey() {
        return this.pk;
    }
}
