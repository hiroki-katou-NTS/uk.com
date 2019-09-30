package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurassocinfor;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealthCarePortInfor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;


/**
* 健保組合情報
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSDT_KNKUM_INFO")
public class QqbmtHealCarePorInfo extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QqbmtHealCarePorInfoPk healCarePorInfoPk;
    
    /**
    * 健康保険組合番号
    */
    @Basic(optional = false)
    @Column(name = "KNKUM_NUM")
    public int healInsurUnionNmber;
    
    @Override
    protected Object getKey()
    {
        return healCarePorInfoPk;
    }

    public HealthCarePortInfor toDomain() {
        return new HealthCarePortInfor(this.healCarePorInfoPk.hisId,String.valueOf(this.healInsurUnionNmber));
    }
    public static QqbmtHealCarePorInfo toEntity(HealthCarePortInfor domain) {
        return null;
    }

}
