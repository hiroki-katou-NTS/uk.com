package nts.uk.ctx.pr.core.infra.entity.wageprovision.empsalunitprice;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 社員給与単価履歴: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtEmpSalPriHisPk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * 個人単価コード
    */
    @Basic(optional = false)
    @Column(name = "PERSONAL_UNIT_PRICE_CODE")
    public String personalUnitPriceCode;
    
    /**
    * 社員ID
    */
    @Basic(optional = false)
    @Column(name = "EMPLOYEE_ID")
    public String employeeId;
    
    /**
    * 履歴ID
    */
    @Basic(optional = false)
    @Column(name = "HISTORY_ID")
    public String historyId;
    
}
