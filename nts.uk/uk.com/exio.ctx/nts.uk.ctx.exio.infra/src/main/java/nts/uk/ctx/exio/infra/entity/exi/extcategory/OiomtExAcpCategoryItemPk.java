package nts.uk.ctx.exio.infra.entity.exi.extcategory;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class OiomtExAcpCategoryItemPk implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 外部受入カテゴリID
	 */
	@Basic(optional = false)
	@Column(name = "CATEGORY_ID")
	public int categoryId;

	/**カテゴリ項目NO	 */
	@Basic(optional = false)
	@Column(name = "ITEM_NO")
	public int itemNo;	

}
