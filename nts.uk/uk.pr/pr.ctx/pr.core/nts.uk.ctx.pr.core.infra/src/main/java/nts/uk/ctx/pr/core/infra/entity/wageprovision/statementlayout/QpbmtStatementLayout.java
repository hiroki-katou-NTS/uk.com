package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
* 明細書レイアウト
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_STATEMENT_LAYOUT")
public class QpbmtStatementLayout extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtStatementLayoutPk statementLayoutPk;
    
    /**
    * 明細書名称
    */
    @Basic(optional = false)
    @Column(name = "STATEMENT_NAME")
    public String specName;
    
    @Override
    protected Object getKey()
    {
        return statementLayoutPk;
    }

    public StatementLayout toDomain() {
        return new StatementLayout(this.statementLayoutPk.cid, this.statementLayoutPk.statementCd, this.specName);
    }
    public static QpbmtStatementLayout toEntity(StatementLayout domain) {
        return new QpbmtStatementLayout(new QpbmtStatementLayoutPk(domain.getCid(),domain.getStatementCode().v()),domain.getStatementName().v());
    }

}
