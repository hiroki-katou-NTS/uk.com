package nts.uk.ctx.pr.core.infra.entity.wageprovision.taxexemptionlimit;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.taxexemptionlimit.TaxExemptionLimit;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 非課税限度額の登録
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_TAX_EXEMPT_LIMIT")
public class QpbmtTaxExemptLimit extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtTaxExemptLimitPk taxExemptLimitPk;
    
    /**
    * 非課税限度額名称
    */
    @Basic(optional = false)
    @Column(name = "TAX_EXEMPTION_NAME")
    public String taxExemptionName;
    
    /**
    * 非課税限度額
    */
    @Basic(optional = false)
    @Column(name = "TAX_EXEMPTION")
    public long taxExemption;
    
    @Override
    protected Object getKey()
    {
        return taxExemptLimitPk;
    }

    public TaxExemptionLimit toDomain() {
        return new TaxExemptionLimit(this.taxExemptLimitPk.cid, this.taxExemptLimitPk.taxFreeamountCode, this.taxExemptionName, this.taxExemption);
    }
    public static QpbmtTaxExemptLimit toEntity(TaxExemptionLimit domain) {
        return new QpbmtTaxExemptLimit(new QpbmtTaxExemptLimitPk(domain.getCid(), domain.getTaxFreeAmountCode().v()), 
        		domain.getTaxExemptionName().v(),
        		domain.getTaxExemption().v());
    }

}
