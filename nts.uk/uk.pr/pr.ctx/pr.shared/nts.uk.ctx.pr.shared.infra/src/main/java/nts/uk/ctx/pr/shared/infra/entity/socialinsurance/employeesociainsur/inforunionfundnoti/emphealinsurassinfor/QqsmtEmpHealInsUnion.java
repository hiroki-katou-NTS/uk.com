package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.inforunionfundnoti.emphealinsurassinfor;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.inforunionfundnoti.emphealinsurassinfor.EmpHealthInsurUnion;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;


/**
* emphealinsurassinfor
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSMT_EMP_HEAL_INS_UNION")
public class QqsmtEmpHealInsUnion extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QqsmtEmpHealInsUnionPk empHealInsUnionPk;
    
    /**
    * 健保固有項目
    */
    @Basic(optional = false)
    @Column(name = "HEAL_INSUR_INHEREN_PR")
    public String healInsurInherenPr;
    
    @Override
    protected Object getKey()
    {
        return empHealInsUnionPk;
    }

    public EmpHealthInsurUnion toDomain() {
        return new EmpHealthInsurUnion(this.empHealInsUnionPk.employeeId, this.healInsurInherenPr);
    }
    public static QqsmtEmpHealInsUnion toEntity(EmpHealthInsurUnion domain) {
        return new QqsmtEmpHealInsUnion(new QqsmtEmpHealInsUnionPk(domain.getEmployeeId()),domain.getHealthInsurInherentProject().map(i->i.v()).orElse(null));
    }

}
