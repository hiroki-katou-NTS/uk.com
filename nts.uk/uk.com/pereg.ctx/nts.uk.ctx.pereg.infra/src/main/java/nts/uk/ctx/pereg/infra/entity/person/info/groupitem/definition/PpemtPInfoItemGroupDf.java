/**
 * 
 */
package nts.uk.ctx.pereg.infra.entity.person.info.groupitem.definition;

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

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PPEMT_GROUP_ITEM_DF")
public class PpemtPInfoItemGroupDf extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final JpaEntityMapper<PpemtPInfoItemGroupDf> MAPPER = new JpaEntityMapper<>(PpemtPInfoItemGroupDf.class);
	
	@EmbeddedId
	public PpemtPInfoItemGroupDfPk ppemtPInfoItemGroupDfPk;

	@Basic(optional = false)
	@Column(name = "CID")
	public String companyId;


	@Override
	protected Object getKey() {
		return this.ppemtPInfoItemGroupDfPk;
	}
	
	/**
	 * 初期値コピー
	 * @param targetCompanyId
	 * @return
	 */
	public PpemtPInfoItemGroupDf copy(String targetCompanyId, IdContainer.IdsMap groupItemIds, IdContainer idContainer) {
		
		String copiedGroupItemId = groupItemIds.getFor(ppemtPInfoItemGroupDfPk.groupItemId);
		String copiedItemId = idContainer.getItemIds().getFor(ppemtPInfoItemGroupDfPk.itemDefId);
		
		return new PpemtPInfoItemGroupDf(
				new PpemtPInfoItemGroupDfPk(copiedGroupItemId, copiedItemId),
				targetCompanyId);
	}
}
