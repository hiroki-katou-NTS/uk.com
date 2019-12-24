package nts.uk.ctx.pr.shared.infra.entity.empinsqualifiinfo.empinsofficeinfo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
* 社員雇用保険事業所履歴
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSMT_EMP_INS_ESM_HIST")
public class QqsmtEmpInsEsmHist extends UkJpaEntity implements Serializable{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QqsmtEmpInsEsmHistPk empInsEsmHistPk;
    
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
    
    /**
    * 労働保険事業所コード
    */
    @Basic(optional = false)
    @Column(name = "LABOR_INS_CD")
    public String laborInsCd;
    
    @Override
    protected Object getKey()
    {
        return empInsEsmHistPk;
    }

}
