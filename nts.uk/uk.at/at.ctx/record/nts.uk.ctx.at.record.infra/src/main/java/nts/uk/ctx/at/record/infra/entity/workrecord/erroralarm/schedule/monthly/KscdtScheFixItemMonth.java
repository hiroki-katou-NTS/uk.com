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
public class KscdtScheFixItemMonth extends ContractUkJpaEntity {
    @EmbeddedId
    public KscdtScheFixCondMonthPk pk;

    /* 名称 */
    @Column(name = "ITEM_NAME")
    public String itemName;

    /* 区分 */
    @Column(name = "ITEM_ATR")
    public int itemAtr;

    /* 初期メッセージ */
    @Column(name = "ITEM_MESSAGE")
    public String itemMsg;

    @Override
    protected Object getKey() {
        return this.pk;
    }
}
