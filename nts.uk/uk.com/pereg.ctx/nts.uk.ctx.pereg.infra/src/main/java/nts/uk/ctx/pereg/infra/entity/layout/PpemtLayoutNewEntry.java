package nts.uk.ctx.pereg.infra.entity.layout;

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

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PPEMT_LAYOUT_NEW_ENTRY")
public class PpemtLayoutNewEntry extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final JpaEntityMapper<PpemtLayoutNewEntry> MAPPER = new JpaEntityMapper<>(PpemtLayoutNewEntry.class);
	
	@EmbeddedId
	public PpemtNewLayoutPk ppemtNewLayoutPk;

	@Basic(optional = false)
	@Column(name = "CID")
	public String companyId;

	@Basic(optional = false)
	@Column(name = "LAYOUT_CD")
	public String layoutCode;

	@Basic(optional = false)
	@Column(name = "LAYOUT_NAME")
	public String layoutName;

	@Override
	protected Object getKey() {
		return this.ppemtNewLayoutPk;
	}
	
	/**
	 * 初期値コピー
	 * @param targetCompanyId
	 * @param layoutIds
	 * @return
	 */
	public PpemtLayoutNewEntry copy(String targetCompanyId, IdContainer.IdGenerator layoutIds) {
		
		String copiedLayoutId = layoutIds.generateFor(ppemtNewLayoutPk.layoutId);
		
		return new PpemtLayoutNewEntry(
				new PpemtNewLayoutPk(copiedLayoutId),
				targetCompanyId,
				layoutCode,
				layoutName);
	}
}
