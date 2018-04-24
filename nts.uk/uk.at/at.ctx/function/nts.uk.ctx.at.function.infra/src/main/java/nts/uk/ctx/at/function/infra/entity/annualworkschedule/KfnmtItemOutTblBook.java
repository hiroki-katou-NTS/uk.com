package nts.uk.ctx.at.function.infra.entity.annualworkschedule;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.annualworkschedule.ItemOutTblBookMaster;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 帳表に出力する項目-Master
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_ITEM_OUT_TBL_BOOK")
public class KfnmtItemOutTblBook extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public KfnmtItemOutTblBookPk itemOutTblBookPk;
    
    /**
    * 並び順
    */
    @Basic(optional = false)
    @Column(name = "SORT_BY")
    public int sortBy;
    
    /**
    * 見出し名称
    */
    @Basic(optional = false)
    @Column(name = "HEADING_NAME")
    public String headingName;
    
    @Override
    protected Object getKey()
    {
        return itemOutTblBookPk;
    }

    public ItemOutTblBookMaster toDomain() {
        return new ItemOutTblBookMaster(this.itemOutTblBookPk.cid, this.itemOutTblBookPk.cd, this.sortBy, this.headingName);
    }
    public static KfnmtItemOutTblBook toEntity(ItemOutTblBookMaster domain) {
        return new KfnmtItemOutTblBook(new KfnmtItemOutTblBookPk(domain.getCid(), domain.getCd()), domain.getSortBy(), domain.getHeadingName());
    }

}
