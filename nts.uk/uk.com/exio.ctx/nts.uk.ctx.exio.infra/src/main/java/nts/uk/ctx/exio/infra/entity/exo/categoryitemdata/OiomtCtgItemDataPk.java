package nts.uk.ctx.exio.infra.entity.exo.categoryitemdata;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 外部出力カテゴリ項目データ: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class OiomtCtgItemDataPk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
	/**
	 * カテゴリID
	 */
	@Basic(optional = false)
	@Column(name = "CATEGORY_ID")
	public int categoryId;
	
    /**
    * 項目NO
    */
    @Basic(optional = false)
    @Column(name = "ITEM_NO")
    public int itemNo;
}
