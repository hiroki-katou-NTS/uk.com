package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.MultiEmpWorkInfo;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;


/**
* 社員二以上事業所勤務情報
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSDT_SYAHO_MULTI_OFFICE")
public class QqsmtMultiEmpWorkIf extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QqsmtMultiEmpWorkIfPk multiEmpWorkIfPk;
    
    /**
    * 二以上事業所勤務者
    */
    @Basic(optional = false)
    @Column(name = "MULTI_OFFICE_ATR")
    public int isMoreEmp;
    
    @Override
    protected Object getKey()
    {
        return multiEmpWorkIfPk;
    }

    public MultiEmpWorkInfo toDomain() {
        return new MultiEmpWorkInfo(this.multiEmpWorkIfPk.employeeId, this.isMoreEmp);
    }
    public static QqsmtMultiEmpWorkIf toEntity(MultiEmpWorkInfo domain) {
        return new QqsmtMultiEmpWorkIf(new QqsmtMultiEmpWorkIfPk(domain.getEmpId(), AppContexts.user().companyId()),domain.getIsMoreEmp());
    }

}
