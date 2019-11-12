package nts.uk.ctx.pr.shared.infra.entity.empinsqualifiinfo.employmentinsqualifiinfo;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * : 主キー情報
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable

public class QqsmtEmpInsLossInfoPk implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 社員ID
     */
    @Basic(optional = false)
    @Column(name = "SID")
    public String sid;

}
