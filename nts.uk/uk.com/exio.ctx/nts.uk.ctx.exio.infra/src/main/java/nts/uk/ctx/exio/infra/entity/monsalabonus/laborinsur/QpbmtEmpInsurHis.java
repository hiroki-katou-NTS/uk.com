package nts.uk.ctx.exio.infra.entity.monsalabonus.laborinsur;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.EmpInsurHis;
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

    public EmpInsurHis toDomain() {
        return new EmpInsurHis(this.empInsurHisPk.cid, this.empInsurHisPk.hisId, this.startDate, this.endDate);
    }
    public static QpbmtEmpInsurHis toEntity(EmpInsurHis domain) {
        return new QpbmtEmpInsurHis(new QpbmtEmpInsurHisPk(domain.getCid(), domain.getHisId()), domain.getStartDate(), domain.getEndDate());
    }

}
