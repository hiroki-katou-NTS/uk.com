package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 明細書紐付け設定（マスタ基準日）
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_STATE_LINK_SET_DATE")
public class QpbmtStateLinkSetDate extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtStateLinkSetDatePk stateLinkSetDatePk;
    
    /**
    * マスタ基準日
    */
    @Basic(optional = false)
    @Column(name = "MASTER_BASE_DATE")
    public GeneralDate masterBaseDate;
    
    @Override
    protected Object getKey()
    {
        return stateLinkSetDatePk;
    }

    public StateLinkSettingDate toDomain() {
        return new StateLinkSettingDate(this.stateLinkSetDatePk.hisId, this.masterBaseDate);
    }
    public static QpbmtStateLinkSetDate toEntity(StateLinkSettingDate domain) {
        return new QpbmtStateLinkSetDate(new QpbmtStateLinkSetDatePk(domain.getHistoryID()),domain.getDate());
    }

}
