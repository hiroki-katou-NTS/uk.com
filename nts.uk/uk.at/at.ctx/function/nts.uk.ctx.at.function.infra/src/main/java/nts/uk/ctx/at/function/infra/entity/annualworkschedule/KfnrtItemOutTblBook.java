package nts.uk.ctx.at.function.infra.entity.annualworkschedule;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 帳表に出力する項目
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNRT_ITEM_OUT_TBL_BOOK")
public class KfnrtItemOutTblBook extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public KfnrtItemOutTblBookPk itemOutTblBookPk;
    
    /**
    * コード
    */
    @Basic(optional = false)
    @Column(name = "SET_OUT_CD")
    public String setOutCd;
    
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
    
    /**
    * 使用区分
    */
    @Basic(optional = false)
    @Column(name = "USE_CLASS")
    public int useClass;
    
    /**
    * 値の出力形式
    */
    @Basic(optional = false)
    @Column(name = "VAL_OUT_FORMAT")
    public int valOutFormat;
    
    @Override
    protected Object getKey()
    {
        return itemOutTblBookPk;
    }

    public ItemOutTblBook toDomain() {
        return new ItemOutTblBook(this.itemOutTblBookPk.cid, this.itemOutTblBookPk.cd, this.setOutCd, this.sortBy, this.headingName, this.useClass, this.valOutFormat);
    }
    public static KfnrtItemOutTblBook toEntity(ItemOutTblBook domain) {
        return new KfnrtItemOutTblBook(new KfnrtItemOutTblBookPk(domain.getCid(), domain.getCd()), domain.getSetOutCd(), domain.getSortBy(), domain.getHeadingName(), domain.getUseClass(), domain.getValOutFormat());
    }

}
