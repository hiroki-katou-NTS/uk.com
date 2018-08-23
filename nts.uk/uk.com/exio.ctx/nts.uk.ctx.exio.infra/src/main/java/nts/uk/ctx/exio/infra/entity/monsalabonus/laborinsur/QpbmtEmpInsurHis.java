package nts.uk.ctx.exio.infra.entity.monsalabonus.laborinsur;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.EmpInsurHis;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

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
    * 
    */
    @Basic(optional = false)
    @Column(name = "START_DATE")
    public int startDate;
    
    /**
    * 
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
