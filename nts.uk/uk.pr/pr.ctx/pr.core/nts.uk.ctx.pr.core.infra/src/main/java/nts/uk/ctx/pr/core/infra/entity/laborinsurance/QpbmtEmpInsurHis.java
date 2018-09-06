package nts.uk.ctx.pr.core.infra.entity.laborinsurance;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurHis;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
* 労災保険履歴
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_EMP_INSUR_HIS")
public class QpbmtEmpInsurHis extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;


    /**
     * ID
     */
    @EmbeddedId
    public QpbmtEmpInsurHisPk empInsurHisPk;

    /**
     * 年月期間
     */
    @Basic(optional = false)
    @Column(name = "START_DATE")
    public int startDate;

    /**
     * 年月期間
     */
    @Basic(optional = false)
    @Column(name = "END_DATE")
    public int endDate;

    @Override
    protected Object getKey()
    {
        return empInsurHisPk;
    }


    public static QpbmtEmpInsurHis toEntity(EmpInsurHis domain) {
        return null;
    }

}
