package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empcomofficehis;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHis;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;


/**
* 社員社保事業所所属履歴
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQBMT_EMP_CORP_OFF_HIS")
public class QqbmtEmpCorpOffHis extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QqbmtEmpCorpOffHisPk empCorpOffHisPk;
    
    /**
    * 開始日
    */
    @Basic(optional = false)
    @Column(name = "START_DATE")
    public GeneralDate startDate;
    
    /**
    * 終了日
    */
    @Basic(optional = false)
    @Column(name = "END_DATE")
    public GeneralDate endDate;
    
    @Override
    protected Object getKey()
    {
        return empCorpOffHisPk;
    }

    public EmpCorpHealthOffHis toDomain() {
        return null;
    }
    public static QqbmtEmpCorpOffHis toEntity(EmpCorpHealthOffHis domain) {
        return null;
    }

}
