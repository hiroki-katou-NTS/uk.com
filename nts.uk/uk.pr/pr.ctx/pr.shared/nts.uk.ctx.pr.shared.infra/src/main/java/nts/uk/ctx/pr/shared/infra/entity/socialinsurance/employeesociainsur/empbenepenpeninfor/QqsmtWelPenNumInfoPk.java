package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empbenepenpeninfor;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
* 厚生年金番号情報: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QqsmtWelPenNumInfoPk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * 厚年得喪期間履歴ID
    */
    @Basic(optional = false)
    @Column(name = "AFF_MOUR_PERIOD_HISID")
    public String affMourPeriodHisid;
    
}
