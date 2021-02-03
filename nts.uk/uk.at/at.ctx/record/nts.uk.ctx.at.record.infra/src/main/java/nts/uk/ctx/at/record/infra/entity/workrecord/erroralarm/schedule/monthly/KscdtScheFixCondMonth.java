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
@Table(name="KSCDT_SCHE_FIX_COND_MONTH")
public class KscdtScheFixCondMonth extends ContractUkJpaEntity {
    @EmbeddedId
    public KscdtScheFixCondMonthPk pk;

    /* 使用区分 */
    @Column(name = "USE_ATR")
    public boolean useAtr;

    /* メッセージ */
    @Column(name = "COND_MESSAGE")
    public String condMsg;

    @Override
    protected Object getKey() {
        return this.pk;
    }
}
