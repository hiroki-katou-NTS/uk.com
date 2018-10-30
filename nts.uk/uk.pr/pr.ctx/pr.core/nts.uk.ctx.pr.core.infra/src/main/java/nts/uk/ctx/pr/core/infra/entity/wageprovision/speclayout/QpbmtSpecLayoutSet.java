package nts.uk.ctx.pr.core.infra.entity.wageprovision.speclayout;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.speclayout.LineByLineSetting;
import nts.uk.ctx.pr.core.dom.wageprovision.speclayout.SpecificationLayoutSet;
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
@Table(name = "QPBMT_SPEC_LAYOUT_SET")
public class QpbmtSpecLayoutSet extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtSpecLayoutSetPk specLayoutSetPk;

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
    public List<LineByLineSetting> listLineByLineSet;

    @Override
    protected Object getKey()
    {
        return specLayoutSetPk;
    }


}
