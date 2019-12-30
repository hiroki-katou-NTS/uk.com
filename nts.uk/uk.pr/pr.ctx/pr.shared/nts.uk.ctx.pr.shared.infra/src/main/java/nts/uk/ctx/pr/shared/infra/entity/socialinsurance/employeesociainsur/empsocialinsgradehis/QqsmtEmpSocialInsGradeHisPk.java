package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empsocialinsgradehis;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 社員社会保険等級履歴: 主キー情報
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QqsmtEmpSocialInsGradeHisPk implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 履歴ID
     */
    @Basic(optional = false)
    @Column(name = "HIST_ID")
    public String historyId;
}
