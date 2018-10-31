package nts.uk.ctx.pr.yearend.infra.yearendadjustment.entity.insurancecompany.insuranceType;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 保険種類情報: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QetmtInsuranceTypePk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * 会社ID
    */
    @Basic(optional = false)
    @Column(name = "CID")
    public String cid;
    
    /**
    * 生命保険コード
    */
    @Basic(optional = false)
    @Column(name = "LIFE_INSURANCE_CODE")
    public String lifeInsuranceCode;
    
    /**
    * コード
    */
    @Basic(optional = false)
    @Column(name = "INSURANCE_TYPE_CODE")
    public String insuranceTypeCode;
    
}
