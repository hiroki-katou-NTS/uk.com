package nts.uk.ctx.pr.core.infra.entity.wageprovision.organizationinformation.salaryclassification.salaryclasshistory;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
/**
 * 社員給与分類項目
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_EMP_SALA_CLASS_HIS")
public class QpbmtEmpSalaClassHis extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtEmpSalaClassHisPk empSalaClassHisPk;

    /**
     * 開始年月
     */
    @Basic(optional = false)
    @Column(name = "START_YEAR_MONTH")
    public int startYearMonth;

    /**
     * 終了年月
     */
    @Basic(optional = false)
    @Column(name = "END_YEAR_MONTH")
    public int endYearMonth;

    @Override
    protected Object getKey() {
        return empSalaClassHisPk;
    }





}
