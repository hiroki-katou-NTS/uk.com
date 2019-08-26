package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInfor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;


/**
* 社員基礎年金番号情報
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSMT_EMP_BA_PEN_NUM")
public class QqsmtEmpBaPenNum extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QqsmtEmpBaPenNumPk empBaPenNumPk;
    
    /**
    * 基礎年金番号
    */
    @Basic(optional = true)
    @Column(name = "BASIC_PEN_NUMBER")
    public String basicPenNumber;
    
    @Override
    protected Object getKey()
    {
        return empBaPenNumPk;
    }

    public EmpBasicPenNumInfor toDomain() {
        return new EmpBasicPenNumInfor(this.empBaPenNumPk.employeeId, this.basicPenNumber);
    }
    public static QqsmtEmpBaPenNum toEntity(EmpBasicPenNumInfor domain) {
        return new QqsmtEmpBaPenNum(new QqsmtEmpBaPenNumPk(domain.getEmployeeId()), domain.getBasicPenNumber().map(i -> i.v()).orElse(null));
    }

}
