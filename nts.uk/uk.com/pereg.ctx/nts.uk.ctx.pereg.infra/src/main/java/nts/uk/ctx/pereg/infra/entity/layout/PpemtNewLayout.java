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

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PPEMT_NEW_LAYOUT")
public class PpemtNewLayout extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
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
}
