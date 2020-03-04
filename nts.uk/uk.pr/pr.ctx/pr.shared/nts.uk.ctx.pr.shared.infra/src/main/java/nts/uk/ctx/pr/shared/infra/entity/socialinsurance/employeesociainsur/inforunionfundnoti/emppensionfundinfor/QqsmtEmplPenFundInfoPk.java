package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.inforunionfundnoti.emppensionfundinfor;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
* 社員厚生年金基金情報: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QqsmtEmplPenFundInfoPk implements Serializable
{
    private static final long serialVersionUID = 1L;
    /**
     * 社員ID
     */
    @Basic(optional = true)
    @Column(name = "SID")
    public String employeeId;

    /**
     * 会社ID
     */
    @Basic(optional = true)
    @Column(name = "CID")
    public String cid;
}
