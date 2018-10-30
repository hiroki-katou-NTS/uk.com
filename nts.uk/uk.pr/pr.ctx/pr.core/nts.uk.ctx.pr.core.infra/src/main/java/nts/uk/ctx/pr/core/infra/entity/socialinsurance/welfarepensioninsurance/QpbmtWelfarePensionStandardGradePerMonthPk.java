package nts.uk.ctx.pr.core.infra.entity.socialinsurance.welfarepensioninsurance;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 等級毎標準月額: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtWelfarePensionStandardGradePerMonthPk implements Serializable
{
    private static final long serialVersionUID = 1L;

    
    /**
    * 厚生年金等級
    */
    @Basic(optional = false)
    @Column(name = "WELFARE_PENSION_GRADE")
    public int welfarePensionGrade;
    
}
