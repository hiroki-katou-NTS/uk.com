package nts.uk.ctx.exio.infra.entity.exo.outcnddetail;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 出力条件詳細項目: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class OiomtOutCndDetailItemPk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * カテゴリID
    */
    @Basic(optional = false)
    @Column(name = "CATEGORY_ID")
    public String categoryId;
    
    /**
    * カテゴリ項目NO
    */
    @Basic(optional = false)
    @Column(name = "CATEGORY_ITEM_NO")
    public int categoryItemNo;
    
}