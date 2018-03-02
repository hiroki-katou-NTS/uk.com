package nts.uk.ctx.exio.infra.entity.exi.item;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 受入項目（定型）
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_STD_ACCEPT_ITEM")
public class OiomtStdAcceptItem extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Version
    @Column(name = "EXCLUS_VER")
    public Long version;
    
    /**
    * ID
    */
    @EmbeddedId
    public OiomtStdAcceptItemPk stdAcceptItemPk;
    
    /**
    * システム種類
    */
    @Basic(optional = false)
    @Column(name = "SYSTEM_TYPE")
    public int systemType;
    
    /**
    * CSV項目番号
    */
    @Basic(optional = false)
    @Column(name = "CSV_ITEM_NUMBER")
    public int csvItemNumber;
    
    /**
    * CSV項目名
    */
    @Basic(optional = false)
    @Column(name = "CSV_ITEM_NAME")
    public String csvItemName;
    
    /**
    * 項目型
    */
    @Basic(optional = false)
    @Column(name = "ITEM_TYPE")
    public int itemType;
    
    /**
    * カテゴリ項目NO
    */
    @Basic(optional = false)
    @Column(name = "CATEGORY_ITEM_NO")
    public int categoryItemNo;
    
    @Override
    protected Object getKey()
    {
        return stdAcceptItemPk;
    }
}
