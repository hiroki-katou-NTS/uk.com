package nts.uk.ctx.at.function.infra.entity.monthlycorrection.fixedformatmonthly;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
* 月別実績のグリッドの列幅
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_MON_GRID_COL_WIDTH")
public class KrcmtMonGridColWidth extends ContractUkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public KrcmtMonGridColWidthPk monGridColWidthPk;
    
    /**
    * 列幅
    */
    @Basic(optional = true)
    @Column(name = "COLUMN_WIDTH")
    public int columnWidth;
    
    @Override
    protected Object getKey()
    {
        return monGridColWidthPk;
    }

}
