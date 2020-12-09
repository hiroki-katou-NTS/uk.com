package nts.uk.ctx.pereg.infra.entity.person.info.item;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.pereg.infra.repository.mastercopy.helper.IdContainer;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PPEMT_ITEM_SORT")
public class PpemtItemSort extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final JpaEntityMapper<PpemtItemSort> MAPPER = new JpaEntityMapper<>(PpemtItemSort.class);

	@EmbeddedId
	public PpemtPerInfoItemPK ppemtPerInfoItemPK;

	@Basic(optional = false)
	@NotNull
	@Column(name = "PER_INFO_CTG_ID")
	public String perInfoCtgId;

	@Basic(optional = false)
	@NotNull
	@Column(name = "DISPORDER")
	public int disporder;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "DISPLAY_ORDER")
	public int displayOrder;
	
	@Override
	protected Object getKey() {
		return ppemtPerInfoItemPK;
	}
	
	/**
	 * 初期値コピー
	 * @param idContainer
	 * @return
	 */
	public PpemtItemSort copy(IdContainer idContainer) {

		String copiedItemId = idContainer.getItemIds().getFor(ppemtPerInfoItemPK.perInfoItemDefId);
		String copiedCategoryId = idContainer.getCategoryIds().getFor(perInfoCtgId);
		
		return new PpemtItemSort(
				new PpemtPerInfoItemPK(copiedItemId),
				copiedCategoryId,
				disporder,
				displayOrder);
	}
}
