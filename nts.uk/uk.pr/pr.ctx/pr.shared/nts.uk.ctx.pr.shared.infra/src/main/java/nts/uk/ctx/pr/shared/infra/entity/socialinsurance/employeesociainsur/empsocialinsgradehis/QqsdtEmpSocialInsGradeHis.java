package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empsocialinsgradehis;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 社員社会保険等級履歴
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSDT_EMP_SOC_INS_GRA_HIS")
public class QqsdtEmpSocialInsGradeHis extends UkJpaEntity implements Serializable {

    @Override
    protected Object getKey() {
        return null;
    }
}
