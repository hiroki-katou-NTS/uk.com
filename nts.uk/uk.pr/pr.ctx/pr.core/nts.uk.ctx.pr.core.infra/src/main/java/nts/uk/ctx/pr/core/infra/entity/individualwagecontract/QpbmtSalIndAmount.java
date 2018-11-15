package nts.uk.ctx.pr.core.infra.entity.individualwagecontract;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmount;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 給与個人別金額
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_SAL_IND_AMOUNT")
public class QpbmtSalIndAmount extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtSalIndAmountPk salIndAmountPk;
    
    /**
    * 金額
    */
    @Basic(optional = false)
    @Column(name = "AMOUNT_OF_MONEY")
    public long amountOfMoney;
    
    @Override
    protected Object getKey()
    {
        return salIndAmountPk;
    }

    public SalIndAmount toDomain() {
        return new SalIndAmount(this.salIndAmountPk.historyId, this.amountOfMoney);
    }
    public static QpbmtSalIndAmount toEntity(SalIndAmount domain) {
        return new QpbmtSalIndAmount(new QpbmtSalIndAmountPk(domain.getHistoryId()),domain.getAmountOfMoney().v());
    }

}
