/**
 * 
 */
package nts.uk.ctx.pereg.infra.entity.layout.cls.definition;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
import lombok.val;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.pereg.infra.repository.mastercopy.helper.IdContainer;


@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PPEMT_LAYOUT_ITEM_CLS_DF")
@Entity
public class PpemtLayoutItemClsDf extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final JpaEntityMapper<PpemtLayoutItemClsDf> MAPPER = new JpaEntityMapper<>(PpemtLayoutItemClsDf.class);
	
	@EmbeddedId
	public PpemtLayoutItemClsDfPk ppemtLayoutItemClsDfPk;
	
	@Basic(optional = false)
	@Column(name = "PER_INFO_ITEM_DEF_ID")
	public String itemDfID;
	
	
	@Override
	protected Object getKey() {
		return this.ppemtLayoutItemClsDfPk;
	}
	
	/**
	 * 初期値コピー
	 * @param layoutIds
	 * @param ids
	 * @return
	 */
	public PpemtLayoutItemClsDf copy(IdContainer.IdsMap layoutIds, IdContainer ids) {
		
		String copiedLayoutId = layoutIds.getFor(ppemtLayoutItemClsDfPk.layoutId);
		String copiedItemId = ids.getItemIds().getFor(itemDfID);
		
		val pk = new PpemtLayoutItemClsDfPk(
				copiedLayoutId,
				ppemtLayoutItemClsDfPk.layoutDispOrder,
				ppemtLayoutItemClsDfPk.dispOrder);
		
		return new PpemtLayoutItemClsDf(pk, copiedItemId);
	}
}
