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

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PPEMT_CTG_SORT")

public class PpemtCtgSort extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public PpemtCtgPK ppemtCtgPK;
	
	@Basic(optional = false)
	@Column(name = "CID")
	public String cid;
	
	@Basic(optional = false)
	@Column(name = "DISPORDER")
	public int disporder;

	@Override
	protected Object getKey() {
		return ppemtCtgPK;
	}

}
