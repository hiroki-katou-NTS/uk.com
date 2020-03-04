package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 社員健康保険資格情報: 主キー情報
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class QqsmtEmpHealInsurQiPk implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 社員ID
     */
    @Basic(optional = false)
    @Column(name = "SID")
    public String employeeId;

    /**
     * 履歴ID
     */
    @Basic(optional = false)
    @Column(name = "HIST_ID")
    public String hisId;
    /**
     * 会社ID
     */
    @Basic(optional = false)
    @Column(name = "CID")
    public String cid;

}
