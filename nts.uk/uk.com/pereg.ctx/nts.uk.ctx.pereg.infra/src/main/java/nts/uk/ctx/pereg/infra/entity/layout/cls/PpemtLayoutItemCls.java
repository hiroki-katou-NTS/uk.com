/**
 * 
 */
package nts.uk.ctx.pereg.infra.entity.layout.cls;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.pereg.infra.repository.mastercopy.helper.IdContainer;

/**
 * @author laitv
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PPEMT_LAYOUT_ITEM_CLS")
public class PpemtLayoutItemCls extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final JpaEntityMapper<PpemtLayoutItemCls> MAPPER = new JpaEntityMapper<>(PpemtLayoutItemCls.class);
	
	@EmbeddedId
	public PpemtLayoutItemClsPk ppemtLayoutItemClsPk;

	@Basic(optional = false)
	@Column(name = "PER_INFO_CATEGORY_ID")
	public String categoryId;

	@Basic(optional = false)
	@Column(name = "LAYOUT_ITEM_TYPE")
	public int itemType;

	@Override
	protected Object getKey() {
		return this.ppemtLayoutItemClsPk;
	}
	
	/**
	 * 初期値コピー
	 * @param layoutIds
	 * @param ids
	 * @return
	 */
	public PpemtLayoutItemCls copy(IdContainer.IdsMap layoutIds, IdContainer ids) {
		
		String copiedLayoutId = layoutIds.getFor(ppemtLayoutItemClsPk.layoutId);
		String copiedCategoryId = ids.getCategoryIds().getFor(categoryId);
		
		return new PpemtLayoutItemCls(
				new PpemtLayoutItemClsPk(copiedLayoutId, ppemtLayoutItemClsPk.dispOrder),
				copiedCategoryId,
				itemType);
	}
}
