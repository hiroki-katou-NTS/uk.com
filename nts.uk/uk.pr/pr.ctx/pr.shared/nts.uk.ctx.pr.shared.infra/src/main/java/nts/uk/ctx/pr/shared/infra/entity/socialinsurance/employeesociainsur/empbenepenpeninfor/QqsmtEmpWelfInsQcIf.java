package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empbenepenpeninfor;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmpWelfarePenInsQualiInfor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
* 社員厚生年金保険資格情報
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSMT_EMP_WELF_INS_QC_IF")
public class QqsmtEmpWelfInsQcIf extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QqsmtEmpWelfInsQcIfPk empWelfInsQcIfPk;
    
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
        return empWelfInsQcIfPk;
    }

    public EmpWelfarePenInsQualiInfor toDomain() {
        return null;
    }
    public static QqsmtEmpWelfInsQcIf toEntity(EmpWelfarePenInsQualiInfor domain) {
        return null;
    }

}
