package nts.uk.ctx.pr.shared.infra.entity.employaverwage;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 社員平均賃金: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QqsmtEmployAverWagePk implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 社員ID
     */
    @Basic(optional = false)
    @Column(name = "SID")
    public String employeeId;

    /**
     * 対象年月
     */
    @Basic(optional = false)
    @Column(name = "TARGET_DATE")
    public int targetDate;
    
}
