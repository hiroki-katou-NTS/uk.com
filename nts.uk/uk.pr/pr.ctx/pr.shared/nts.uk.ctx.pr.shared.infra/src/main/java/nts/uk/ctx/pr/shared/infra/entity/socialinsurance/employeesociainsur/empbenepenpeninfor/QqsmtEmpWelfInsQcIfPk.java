package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empbenepenpeninfor;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
* 社員厚生年金保険資格情報: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QqsmtEmpWelfInsQcIfPk implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 社員ID
     */
    @Basic(optional = false)
    @Column(name = "SID")
    public String employeeId;

    /**
     * 厚年得喪期間履歴ID
     */
    @Basic(optional = false)
    @Column(name = "HIST_ID")
    public String historyId;

    /**
     * 厚年得喪期間履歴ID
     */
    @Basic(optional = false)
    @Column(name = "CID")
    public String cid;


    
}
