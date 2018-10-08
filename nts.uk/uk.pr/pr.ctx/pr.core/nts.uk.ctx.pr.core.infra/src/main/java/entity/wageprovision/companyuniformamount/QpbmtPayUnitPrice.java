package entity.wageprovision.companyuniformamount;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPrice;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 給与会社単価
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_PAY_UNIT_PRICE")
public class QpbmtPayUnitPrice extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtPayUnitPricePk payUnitPricePk;
    
    /**
    * 名称
    */
    @Basic(optional = false)
    @Column(name = "NAME")
    public String name;
    
    @Override
    protected Object getKey()
    {
        return payUnitPricePk;
    }

    public PayrollUnitPrice toDomain() {
        return new PayrollUnitPrice(this.payUnitPricePk.cid,this.payUnitPricePk.code, this.name);
    }
    public static QpbmtPayUnitPrice toEntity(PayrollUnitPrice domain) {
        return new QpbmtPayUnitPrice(new QpbmtPayUnitPricePk( domain.getCId(),domain.getCode().v()),domain.getName().v());
    }

}
