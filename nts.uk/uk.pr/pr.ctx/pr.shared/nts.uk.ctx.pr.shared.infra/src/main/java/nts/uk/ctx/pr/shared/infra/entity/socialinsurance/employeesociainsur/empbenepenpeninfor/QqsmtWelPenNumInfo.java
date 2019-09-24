package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empbenepenpeninfor;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfPenNumInformation;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;


/**
* 厚生年金番号情報
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSMT_WEL_PEN_NUM_INFO")
public class QqsmtWelPenNumInfo extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QqsmtWelPenNumInfoPk welPenNumInfoPk;
    
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
    
    @Override
    protected Object getKey()
    {
        return welPenNumInfoPk;
    }

    public WelfPenNumInformation toDomain() {
       return new WelfPenNumInformation(this.welPenNumInfoPk.affMourPeriodHisid,this.healInsurSameCtg,this.welPenNumber);
    }
    public static QqsmtWelPenNumInfo toEntity(WelfPenNumInformation domain) {
        return null;
    }

}
