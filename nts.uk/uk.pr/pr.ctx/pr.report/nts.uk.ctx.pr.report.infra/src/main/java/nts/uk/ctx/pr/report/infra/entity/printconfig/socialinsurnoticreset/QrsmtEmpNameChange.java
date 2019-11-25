package nts.uk.ctx.pr.report.infra.entity.printconfig.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpNameChangeNotiInfor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;



/**
* 社員氏名変更届情報
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QRSMT_EMP_NAME_CHANGE")
public class QrsmtEmpNameChange extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QrsmtEmpNameChangePk empNameChangePk;
    
    /**
    * 健康保険被保険者証不要
    */
    @Basic(optional = false)
    @Column(name = "HEAL_INSUR_PER_NONEED_ATR")
    public int healInsurPerNoneed;
    
    /**
    * その他
    */
    @Basic(optional = false)
    @Column(name = "OTHER_ATR")
    public int other;
    
    /**
    * その他備考
    */
    @Basic(optional = true)
    @Column(name = "OTHER_REMARKS")
    public String otherRemarks;
    
    @Override
    protected Object getKey()
    {
        return empNameChangePk;
    }

    public EmpNameChangeNotiInfor toDomain() {
        return new EmpNameChangeNotiInfor(this.empNameChangePk.employeeId, this.empNameChangePk.cid, this.healInsurPerNoneed, this.other, this.otherRemarks);
    }
    public static QrsmtEmpNameChange toEntity(EmpNameChangeNotiInfor domain) {
        return new QrsmtEmpNameChange(new QrsmtEmpNameChangePk(domain.getEmployeeId(), domain.getCompanyId()),domain.getHealInsurPersonNoNeed(), domain.getOther(), domain.getOtherRemarks().map(i->i.v()).orElse(null));
    }

}
