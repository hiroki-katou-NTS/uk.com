package nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 厚生年金標準月額: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtWelfarePensionStandardMonthlyFeePk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * 対象期間
    */
    @Basic(optional = false)
    @Column(name = "TARGET_START_YM")
    public int targetStartYm;
    
    /**
    * 対象期間
    */
    @Basic(optional = false)
    @Column(name = "TARGET_END_YM")
    public int targetEndYm;
    
}
