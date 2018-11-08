package nts.uk.ctx.pr.core.infra.entity.wageprovision.organizationinformation.salaryclassification.salaryclasshistory;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
import javax.persistence.*;
import java.io.Serializable;

;

/**
 * 社員給与分類履歴
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_EMP_SALA_CATEGORY")
public class QpbmtEmpSalaCategory extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtEmpSalaCategoryPk empSalaCategoryPk;

    /**
     * 給与分類コード
     */
    @Basic(optional = false)
    @Column(name = "SALARY_CLASS_CODE")
    public String salaryClassCode;

    @Override
    protected Object getKey() {
        return empSalaCategoryPk;
    }



}
