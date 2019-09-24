package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empbenepenpeninfor;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealthInsLossInfo;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
* 健康保険喪失時情報
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSMT_HEALTH_INS_LOSS")
public class QqsmtHealthInsLoss extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QqsmtHealthInsLossPk healthInsLossPk;
    
    /**
    * その他
    */
    @Basic(optional = false)
    @Column(name = "OTHER")
    public int other;
    
    /**
    * その他理由
    */
    @Basic(optional = true)
    @Column(name = "OTHER_REASON")
    public String otherReason;
    
    /**
    * 保険証回収添付枚数
    */
    @Basic(optional = true)
    @Column(name = "CA_INSURANCE")
    public Integer caInsurance;
    
    /**
    * 保険証回収返不能枚数
    */
    @Basic(optional = true)
    @Column(name = "NUM_RECOVED")
    public Integer numRecoved;
    
    /**
    * 資格喪失原因
    */
    @Basic(optional = true)
    @Column(name = "CAUSE")
    public Integer cause;
    
    @Override
    protected Object getKey()
    {
        return healthInsLossPk;
    }

    public static QqsmtHealthInsLoss toEntity(HealthInsLossInfo healthInsLossInfo){
        return new QqsmtHealthInsLoss(new QqsmtHealthInsLossPk(healthInsLossInfo.getEmpId()),
                healthInsLossInfo.getOther(),
                healthInsLossInfo.getOtherReason().map(i -> i.v()).orElse(null),
                healthInsLossInfo.getCaInsurance().map(i -> i.v()).orElse(null),
                healthInsLossInfo.getNumRecoved().map(i -> i.v()).orElse(null),
                healthInsLossInfo.getCause().map(i -> i.value).orElse(null));

    }

    public HealthInsLossInfo toDomain(){
        return new HealthInsLossInfo(this.other, this.otherReason, this.caInsurance, this.numRecoved, this.cause);
    }

}
