package nts.uk.ctx.pr.core.infra.entity.salary;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.salary.IncomTaxBaseYear;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 所得税基準年月日
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_INCOM_TAX_BASE_YEAR")
public class QpbmtIncomTaxBaseYear extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtIncomTaxBaseYearPk incomTaxBaseYearPk;
    
    /**
    * 基準日
    */
    @Basic(optional = false)
    @Column(name = "REFE_DATE")
    public int refeDate;
    
    /**
    * 基準年
    */
    @Basic(optional = false)
    @Column(name = "BASE_YEAR")
    public int baseYear;
    
    /**
    * 基準月
    */
    @Basic(optional = false)
    @Column(name = "BASE_MONTH")
    public int baseMonth;
    
    @Override
    protected Object getKey()
    {
        return incomTaxBaseYearPk;
    }

    public IncomTaxBaseYear toDomain() {
        return new IncomTaxBaseYear(this.incomTaxBaseYearPk.cid, this.incomTaxBaseYearPk.processCateNo, this.refeDate, this.baseYear, this.baseMonth);
    }
    public static QpbmtIncomTaxBaseYear toEntity(IncomTaxBaseYear domain) {
        return new QpbmtIncomTaxBaseYear(new QpbmtIncomTaxBaseYearPk(domain.getCid(), domain.getProcessCateNo()), domain.getRefeDate().value, domain.getBaseYear().value, domain.getBaseMonth().value);
    }

}
