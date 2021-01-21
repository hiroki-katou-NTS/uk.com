package nts.uk.ctx.pereg.infra.entity.person.info.ctg;

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
@Entity
@Table(name = "PPEMT_CTG_SORT")

public class PpemtCtgSort extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final JpaEntityMapper<PpemtCtgSort> MAPPER = new JpaEntityMapper<>(PpemtCtgSort.class);

	@EmbeddedId
	public PpemtPerInfoCtgPK ppemtPerInfoCtgPK;
	
	@Basic(optional = false)
	@Column(name = "CID")
	public String cid;
	
	@Basic(optional = false)
	@Column(name = "DISPORDER")
	public int disporder;

	@Override
	protected Object getKey() {
		return ppemtPerInfoCtgPK;
	}

	public PpemtCtgSort copy(String targetCompanyId, IdContainer.IdsMap idsMap) {
		
		val pk = new PpemtPerInfoCtgPK(idsMap.getFor(ppemtPerInfoCtgPK.perInfoCtgId));
		return new PpemtCtgSort(pk, targetCompanyId, disporder);
	}
}
