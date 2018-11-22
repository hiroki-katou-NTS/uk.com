package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingCompany;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StatementCode;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 明細書紐付け設定（会社）
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_STATE_LINK_SET_COM")
public class QpbmtStateLinkSetCom extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtStateLinkSetComPk stateLinkSetComPk;
    
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
        return stateLinkSetComPk;
    }

    public StateLinkSettingCompany toDomain() {
        return new StateLinkSettingCompany(this.stateLinkSetComPk.hisId, new StatementCode(this.salary), new StatementCode(this.bonus));
    }
    public static QpbmtStateLinkSetCom toEntity(StateLinkSettingCompany domain) {
        return new QpbmtStateLinkSetCom(new QpbmtStateLinkSetComPk(domain.getHistoryID()),
                domain.getSalaryCode().isPresent() ? domain.getSalaryCode().get().v() : null,
                domain.getBonusCode().isPresent() ? domain.getBonusCode().get().v() : null);
    }

}
