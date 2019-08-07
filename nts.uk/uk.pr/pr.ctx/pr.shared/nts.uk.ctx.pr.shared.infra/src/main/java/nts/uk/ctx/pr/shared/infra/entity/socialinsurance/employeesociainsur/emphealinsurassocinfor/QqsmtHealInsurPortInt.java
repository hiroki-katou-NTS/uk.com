package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurassocinfor;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealInsurPortPerIntell;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;


/**
* 健保組合加入期間情報
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSMT_HEAL_INSUR_PORT_INT")
public class QqsmtHealInsurPortInt extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QqsmtHealInsurPortIntPk healInsurPortIntPk;
    
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
    * 健康保険組合番号
    */
    @Basic(optional = false)
    @Column(name = "HEAL_INSUR_UNION_NMBER")
    public int healInsurUnionNmber;
    
    @Override
    protected Object getKey()
    {
        return healInsurPortIntPk;
    }

    public HealInsurPortPerIntell toDomain() {
        return null;
    }
    public static QqsmtHealInsurPortInt toEntity(HealInsurPortPerIntell domain) {
        return null;
    }

}
