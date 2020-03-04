package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empbenepenpeninfor;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealthInsLossInfo;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
* 健康保険喪失時情報
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSDT_KENHO_LOSS_INFO")
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
    @Column(name = "OTHER_ATR")
    public int other;
    
    /**
    * その他理由
    */
    @Basic(optional = true)
    @Column(name = "OTHER_REASONS")
    public String otherReason;
    
    /**
    * 保険証回収添付枚数
    */
    @Basic(optional = true)
    @Column(name = "NUM_OF_ATTACHED")
    public Integer caInsurance;
    
    /**
    * 保険証回収返不能枚数
    */
    @Basic(optional = true)
    @Column(name = "NUM_OF_UNCOLLECTABLE")
    public Integer numRecoved;
    
    /**
    * 資格喪失原因
    */
    @Basic(optional = true)
    @Column(name = "LOSS_CASE_ATR")
    public Integer cause;
    
    @Override
    protected Object getKey()
    {
        return healthInsLossPk;
    }

    public static QqsmtHealthInsLoss toEntity(HealthInsLossInfo healthInsLossInfo){
        return new QqsmtHealthInsLoss(new QqsmtHealthInsLossPk(healthInsLossInfo.getEmpId(), AppContexts.user().companyId()),
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
