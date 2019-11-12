package nts.uk.ctx.pr.shared.infra.entity.empinsqualifiinfo.employmentinsqualifiinfo;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.io.Serializable;

public class QqsmtEmpInsGetInfoPk implements Serializable {
    /**
     * 社員ID
     */
    @Basic(optional = false)
    @Column(name = "SID")
    public String sid;
}
