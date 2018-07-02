package nts.uk.ctx.exio.infra.entity.exo.outitem;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.exo.dataformat.ItemType;
import nts.uk.ctx.exio.dom.exo.outitem.StdOutItem;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 出力項目(定型)
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_STD_OUT_ITEM")
public class OiomtStdOutItem extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public OiomtStdOutItemPk stdOutItemPk;
    
    /**
    * 出力項目名
    */
    @Basic(optional = false)
    @Column(name = "OUT_ITEM_NAME")
    public String outItemName;
    
    /**
    * 項目型
    */
    @Basic(optional = false)
    @Column(name = "ITEM_TYPE")
    public int itemType;
    
    @Override
    protected Object getKey()
    {
        return stdOutItemPk;
    }

    public StdOutItem toDomain() {
        return new StdOutItem(this.stdOutItemPk.cid, this.stdOutItemPk.outItemCd, this.stdOutItemPk.condSetCd, this.outItemName,
        		EnumAdaptor.valueOf(this.itemType, ItemType.class));
    }
    public static OiomtStdOutItem toEntity(StdOutItem domain) {
        return new OiomtStdOutItem(new OiomtStdOutItemPk(domain.getCid(), domain.getOutItemCd(), domain.getCondSetCd()), domain.getOutItemName(), domain.getItemType().value);
    }

}
