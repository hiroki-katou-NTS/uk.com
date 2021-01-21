package nts.uk.ctx.pereg.infra.entity.person.info.item;

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

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PPEMT_ITEM")
public class PpemtItem extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final JpaEntityMapper<PpemtItem> MAPPER = new JpaEntityMapper<>(PpemtItem.class);

	@EmbeddedId
	public PpemtPerInfoItemPK ppemtPerInfoItemPK;

	@Basic(optional = false)
	@Column(name = "PER_INFO_CTG_ID")
	public String perInfoCtgId;

	@Basic(optional = false)
	@Column(name = "ITEM_CD")
	public String itemCd;

	@Basic(optional = false)
	@Column(name = "ITEM_NAME")
	public String itemName;

	@Basic(optional = false)
	@Column(name = "ABOLITION_ATR")
	public int abolitionAtr;

	@Basic(optional = false)
	@Column(name = "REQUIRED_ATR")
	public int requiredAtr;
	
	@Column(name = "INIT_VALUE")
	public String initValue;
	
	@Override
	protected Object getKey() {
		return ppemtPerInfoItemPK;
	}

	/**
	 * 初期値コピー
	 * @param idContainer
	 * @return
	 */
	public PpemtItem copy(IdContainer.IdsMap categoryIds, IdContainer.IdGenerator itemIds) {
		
		String copiedItemId = itemIds.generateFor(ppemtPerInfoItemPK.perInfoItemDefId);
		String copiedCategoryId = categoryIds.getFor(perInfoCtgId);
		
		return new PpemtItem(
				new PpemtPerInfoItemPK(copiedItemId),
				copiedCategoryId,
				itemCd,
				itemName,
				abolitionAtr,
				requiredAtr,
				initValue);
	}
}
