package nts.uk.ctx.at.function.infra.entity.annualworkschedule;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.annualworkschedule.ItemOutTblBook;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.ValueOuputFormat;
import nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue.ItemOutTblBookCode;
import nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue.ItemOutTblBookHeadingName;
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
        return new ItemOutTblBook(this.itemOutTblBookPk.cid, this.itemOutTblBookPk.setOutCd,
                                  new ItemOutTblBookCode(this.itemOutTblBookPk.cd), this.sortBy,
                                  new ItemOutTblBookHeadingName(this.headingName), this.useClass,
                                  EnumAdaptor.valueOf(this.valOutFormat, ValueOuputFormat.class));
    }
    public static KfnrtItemOutTblBook toEntity(ItemOutTblBook domain) {
        return new KfnrtItemOutTblBook(new KfnrtItemOutTblBookPk(domain.getCid(), domain.getSetOutCd(), domain.getCd().v()),
                                       domain.getSortBy(), domain.getHeadingName().v(),
                                       domain.getUseClass(), domain.getValOutFormat().value);
    }

}
