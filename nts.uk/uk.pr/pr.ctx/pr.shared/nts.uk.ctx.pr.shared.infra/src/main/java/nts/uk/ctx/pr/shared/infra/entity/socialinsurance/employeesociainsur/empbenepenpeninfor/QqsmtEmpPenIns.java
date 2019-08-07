package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empbenepenpeninfor;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfarePenTypeInfor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;


/**
* 厚生年金種別情報
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSMT_EMP_PEN_INS")
public class QqsmtEmpPenIns extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QqsmtEmpPenInsPk empPenInsPk;
    
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
    * 坑内員区分
    */
    @Basic(optional = false)
    @Column(name = "HEAL_INSUR_SAME_CTG")
    public int healInsurSameCtg;
    
    /**
    * 厚生年金番号
    */
    @Basic(optional = true)
    @Column(name = "WEL_PEN_NUMBER")
    public String welPenNumber;
    
    /**
    * 坑内員区分
    */
    @Basic(optional = false)
    @Column(name = "UNDERGOUND_DIVISION")
    public int undergoundDivision;
    
    @Override
    protected Object getKey()
    {
        return empPenInsPk;
    }

    public WelfarePenTypeInfor toDomain() {
        return null; }
    public static QqsmtEmpPenIns toEntity(WelfarePenTypeInfor domain) {
        return null;
    }

}
