package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealInsurNumberInfor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
* 健保番号情報
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSMT_HEAL_INS_NUM_INFO")
public class QqsmtHealInsNumInfo extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QqsmtHealInsNumInfoPk healInsNumInfoPk;
    
    /**
    * 介護保険番号
    */
    @Basic(optional = true)
    @Column(name = "CARE_IS_NUMBER")
    public String careIsNumber;
    
    /**
    * 健康保険番号
    */
    @Basic(optional = true)
    @Column(name = "HEAL_INSUR_NUMBER")
    public String healInsurNumber;
    
    @Override
    protected Object getKey()
    {
        return healInsNumInfoPk;
    }

    public HealInsurNumberInfor toDomain() {
        return new HealInsurNumberInfor(this.healInsNumInfoPk.historyId, this.careIsNumber, this.healInsurNumber);
    }
    public static QqsmtHealInsNumInfo toEntity(HealInsurNumberInfor domain) {
        return new QqsmtHealInsNumInfo(new QqsmtHealInsNumInfoPk(domain.getHistoryId()),domain.getCareInsurNumber().map(i->i.v()).orElse(null), domain.getHealInsNumber().map(i->i.v()).orElse(null));
    }

}
