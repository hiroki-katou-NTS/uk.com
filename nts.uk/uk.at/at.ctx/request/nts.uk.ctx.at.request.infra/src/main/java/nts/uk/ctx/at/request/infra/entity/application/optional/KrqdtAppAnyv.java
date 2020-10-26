package nts.uk.ctx.at.request.infra.entity.application.optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Setter
@Getter
@Table(name = "KRQDT_APP_ANYV")
public class KrqdtAppAnyv extends ContractUkJpaEntity {

    @EmbeddedId
    private KrqdtAppAnyvPk KrqdtAppAnyvPk;

    /* 時間 */
    @Column(name = "TIME_VAL")
    public Integer time;
    /* 回数 */
    @Column(name = "COUNT_VAL")
    public BigDecimal times;
    /* 金額 */
    @Column(name = "MONEY_VAL")
    public Integer moneyValue;

    @Version
    @Column(name = "EXCLUS_VER")
    public long version;

    @Override
    protected Object getKey() {
        return KrqdtAppAnyvPk;
    }
}
