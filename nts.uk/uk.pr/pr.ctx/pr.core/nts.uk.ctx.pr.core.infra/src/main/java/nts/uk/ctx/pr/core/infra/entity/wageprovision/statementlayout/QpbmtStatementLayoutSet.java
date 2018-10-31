package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
* 明細書レイアウト設定
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_STATE_LAYOUT_SET")
public class QpbmtStatementLayoutSet extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtStatementLayoutSetPk statementLayoutSetPk;

    /**
    * 明細書レイアウトパターン
    */
    @Basic(optional = false)
    @Column(name = "LAYOUT_PATTERN")
    public int layoutPattern;

    @JoinColumns({
            @JoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID", insertable = true, updatable = true),
            @JoinColumn(name="CTG_ATR",referencedColumnName = "CTG_ATR", insertable = true, updatable = true)})
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<QpbmtLineByLineSet> listLineByLineSet;

    @Override
    protected Object getKey()
    {
        return statementLayoutSetPk;
    }


}
