package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empbenepenpeninfor;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
* 厚生年金種別情報: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QqsmtEmpPenInsPk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * 社員ID
    */
    @Basic(optional = false)
    @Column(name = "EMPLOYEE_ID")
    public String employeeId;
    
    /**
    * 厚年得喪期間履歴ID
    */
    @Basic(optional = false)
    @Column(name = "HISTORY_ID")
    public String historyId;
    
}
