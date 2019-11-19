package nts.uk.ctx.pr.shared.infra.entity.empinsqualifiinfo.employmentinsqualifiinfo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QqsmtEmpInsGetInfoPk implements Serializable {
    /**
     * 会社ID
     */
    @Basic(optional = false)
    @Column(name = "CID")
    public  String cId;
    /**
     * 社員ID
     */
    @Basic(optional = false)
    @Column(name = "SID")
    public String sId;
}
