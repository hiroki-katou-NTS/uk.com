package nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpInsurStanDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 雇用保険基準日
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_EMP_INSUR_STAN_DATE")
public class QpbmtEmpInsurStanDate extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtEmpInsurStanDatePk empInsurStanDatePk;
    
    /**
    * 基準日
    */
    @Basic(optional = false)
    @Column(name = "REFE_DATE")
    public int refeDate;
    
    /**
    * 基準月
    */
    @Basic(optional = false)
    @Column(name = "BASE_MONTH")
    public int baseMonth;
    
    @Override
    protected Object getKey()
    {
        return empInsurStanDatePk;
    }

    public EmpInsurStanDate toDomain() {
		return new EmpInsurStanDate( this.refeDate, this.baseMonth);
    }
    public static QpbmtEmpInsurStanDate toEntity(EmpInsurStanDate domain) {
        return new QpbmtEmpInsurStanDate(new QpbmtEmpInsurStanDatePk(), domain.getEmpInsurRefeDate().value, domain.getEmpInsurBaseMonth().value);
    }

}
