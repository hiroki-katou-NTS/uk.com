package nts.uk.ctx.pr.yearend.infra.yearendadjustment.insurancecompany;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.InsuranceType;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 保険種類情報
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QETMT_INSURANCE_TYPE")
public class QetmtInsuranceType extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QetmtInsuranceTypePk insuranceTypePk;
    
    /**
    * 名称
    */
    @Basic(optional = false)
    @Column(name = "INSURANCE_TYPE_NAME")
    public String insuranceTypeName;
    
    /**
    * 区分
    */
    @Basic(optional = false)
    @Column(name = "ATR_OF_INSURANCE_TYPE")
    public int atrOfInsuranceType;
    
    @Override
    protected Object getKey()
    {
        return insuranceTypePk;
    }

    public InsuranceType toDomain() {
        return new InsuranceType(this.insuranceTypePk.cid, this.insuranceTypePk.lifeInsuranceCode, this.insuranceTypePk.insuranceTypeCode, this.insuranceTypeName, this.atrOfInsuranceType);
    }
    public static QetmtInsuranceType toEntity(InsuranceType domain) {
        return new QetmtInsuranceType(new QetmtInsuranceTypePk(domain.getCId(), domain.getLifeInsuranceCode().v(), domain.getInsuranceTypeCode().v()),domain.getInsuranceTypeName().v(), domain.getAtrOfInsuranceType().value);
    }

}
