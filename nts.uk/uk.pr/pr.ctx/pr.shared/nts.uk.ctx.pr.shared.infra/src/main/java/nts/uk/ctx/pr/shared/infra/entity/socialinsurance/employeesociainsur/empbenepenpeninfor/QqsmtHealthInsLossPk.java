package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empbenepenpeninfor;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 健康保険喪失時情報: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QqsmtHealthInsLossPk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * 社員ID
    */
    @Basic(optional = false)
    @Column(name = "EMP_ID")
    public String empId;
    
}
