package .infra.entity.ドメインモデル.nittsusystem.universalk.salary.shared.socialinsurance.employeesociainsur.empbenepenpeninfor;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import .dom.ドメインモデル.nittsusystem.universalk.salary.shared.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfarePenTypeInfor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

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
        return new WelfarePenTypeInfor(this.empPenInsPk.employeeId, this.empPenInsPk.historyId, this.startDate, this.endDate, this.healInsurSameCtg, this.welPenNumber, this.undergoundDivision);
    }
    public static QqsmtEmpPenIns toEntity(WelfarePenTypeInfor domain) {
        return new QqsmtEmpPenIns(new QqsmtEmpPenInsPk(domain.get(), domain.get()),domain.get(), domain.get(), domain.get(), domain.get(), domain.getUndergroundDivision().value);
    }

}
