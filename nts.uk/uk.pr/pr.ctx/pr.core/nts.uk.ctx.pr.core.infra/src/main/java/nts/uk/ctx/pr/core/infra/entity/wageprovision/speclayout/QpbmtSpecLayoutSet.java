package nts.uk.ctx.pr.core.infra.entity.wageprovision.speclayout;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.speclayout.SpecificationLayoutSet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

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
     * 開始日
     */
    @Basic(optional = false)
    @Column(name = "START_YEAR_MONTH")
    public int startYearMonth;

    /**
     * 終了日
     */
    @Basic(optional = false)
    @Column(name = "END_YEAR_MONTH")
    public int endYearMonth;
    /**
    * 明細書レイアウトパターン
    */
    @Basic(optional = false)
    @Column(name = "LAYOUT_PATTERN")
    public int layoutPattern;
    
    @Override
    protected Object getKey()
    {
        return specLayoutSetPk;
    }


}
