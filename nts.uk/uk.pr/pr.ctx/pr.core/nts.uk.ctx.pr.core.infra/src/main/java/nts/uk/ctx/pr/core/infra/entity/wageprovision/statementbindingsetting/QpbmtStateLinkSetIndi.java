package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingIndividual;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StatementCode;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 明細書紐付け設定（個人）
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_STATE_LINK_SET_INDI")
public class QpbmtStateLinkSetIndi extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtStateLinkSetIndiPk stateLinkSetIndiPk;
    
    /**
    * 給与明細書
    */
    @Basic(optional = true)
    @Column(name = "SALARY")
    public String salary;
    
    /**
    * 賞与明細書
    */
    @Basic(optional = true)
    @Column(name = "BONUS")
    public String bonus;
    
    @Override
    protected Object getKey()
    {
        return stateLinkSetIndiPk;
    }

    public StateLinkSettingIndividual toDomain() {
        return new StateLinkSettingIndividual(this.stateLinkSetIndiPk.hisId, new StatementCode(this.salary), new StatementCode(this.bonus));
    }
    public static QpbmtStateLinkSetIndi toEntity(StateLinkSettingIndividual domain) {
        return new QpbmtStateLinkSetIndi(new QpbmtStateLinkSetIndiPk(domain.getHistoryID()),domain.getSalaryCode().get().v(), domain.getBonusCode().get().v());
    }

}
