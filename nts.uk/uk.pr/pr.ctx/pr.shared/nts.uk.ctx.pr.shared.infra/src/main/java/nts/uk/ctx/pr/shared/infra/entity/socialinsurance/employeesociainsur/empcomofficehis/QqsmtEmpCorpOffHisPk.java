package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empcomofficehis;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
* 社員社保事業所所属履歴: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QqsmtEmpCorpOffHisPk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * 社員ID
    */
    @Basic(optional = false)
    @Column(name = "SID")
    public String employeeId;
    
    /**
    * 履歴ID
    */
    @Basic(optional = false)
    @Column(name = "HIST_ID")
    public String historyId;
    
    /**
    * 社会保険事業所コード
    */
    @Basic(optional = false)
    @Column(name = "SYAHO_OFFICE_CD")
    public String socialInsuranceOfficeCd;
    
}
