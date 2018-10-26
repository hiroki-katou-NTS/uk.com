package nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
* 厚生年金保険区分: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtEmployeesPensionMonthlyInsuranceFeePk implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 会社ID
     */
    @Basic(optional = false)
    @Column(name = "CID")
    public String cid;

    /**
     * 社会保険事業所コード
     */
    @Basic(optional = false)
    @Column(name = "SOCIAL_INSURANCE_OFFICE_CD")
    public String socialInsuranceOfficeCd;

    /**
     * 履歴ID
     */
    @Basic(optional = false)
    @Column(name = "HISTORY_ID")
    public String historyId;
    
}
