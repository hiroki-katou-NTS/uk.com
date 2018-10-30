package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateUseUnitSetting;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 明細書利用単位設定
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_STATE_USE_UNIT_SET")
public class QpbmtStateUseUnitSet extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtStateUseUnitSetPk stateUseUnitSetPk;
    
    /**
    * マスタ利用区分
    */
    @Basic(optional = false)
    @Column(name = "MASTER_USE_CLASS")
    public int masterUseClass;
    
    /**
    * 個人利用区分
    */
    @Basic(optional = false)
    @Column(name = "INDIVIDUAL_USE_CLASS")
    public int individualUseClass;
    
    /**
    * 利用マスタ
    */
    @Basic(optional = true)
    @Column(name = "USE_MASTER")
    public Integer useMaster;
    
    @Override
    protected Object getKey()
    {
        return stateUseUnitSetPk;
    }

    public StateUseUnitSetting toDomain() {
        return new StateUseUnitSetting(this.stateUseUnitSetPk.cid, this.masterUseClass, this.individualUseClass, this.useMaster);
    }
    public static QpbmtStateUseUnitSet toEntity(StateUseUnitSetting domain) {
        return new QpbmtStateUseUnitSet(new QpbmtStateUseUnitSetPk(domain.getCompanyID()),domain.getMasterUse().value, domain.getIndividualUse().value, domain.getUsageMaster().map(i->i.value).orElse(null));
    }

}
