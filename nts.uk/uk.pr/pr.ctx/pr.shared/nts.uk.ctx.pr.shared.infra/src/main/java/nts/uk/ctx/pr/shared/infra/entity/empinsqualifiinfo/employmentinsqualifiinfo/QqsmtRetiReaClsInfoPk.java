package nts.uk.ctx.pr.shared.infra.entity.empinsqualifiinfo.employmentinsqualifiinfo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable

public class QqsmtRetiReaClsInfoPk {

    private static final long serialVersionUID = 1L;

    /**
     * 会社ID
     */
    @Basic(optional = false)
    @Column(name = "CID")
    public String cId;

    /**
     * 退職解雇理由区分コード
     */
    @Basic(optional = false)
    @Column(name = "RETI_RES_CLS_CD")
    public String retirementReasonClsCode;

}
