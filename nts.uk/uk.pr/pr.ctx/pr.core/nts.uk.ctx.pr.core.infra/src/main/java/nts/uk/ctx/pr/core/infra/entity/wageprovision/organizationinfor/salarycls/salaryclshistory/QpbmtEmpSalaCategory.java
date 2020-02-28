package nts.uk.ctx.pr.core.infra.entity.wageprovision.organizationinfor.salarycls.salaryclshistory;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
import javax.persistence.*;
import java.io.Serializable;

;

/**
 * 社員給与分類項目
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
     * 開始年月
     */
    @Basic(optional = false)
    @Column(name = "START_YM")
    public int startYearMonth;

    /**
     * 終了年月
     */
    @Basic(optional = false)
    @Column(name = "END_YM")
    public int endYearMonth;


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
