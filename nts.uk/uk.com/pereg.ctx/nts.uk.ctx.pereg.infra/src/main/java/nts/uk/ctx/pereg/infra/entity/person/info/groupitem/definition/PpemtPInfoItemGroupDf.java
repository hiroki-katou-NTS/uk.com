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

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PPEMT_GROUP_ITEM_DF")
public class PpemtPInfoItemGroupDf extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public PpemtPInfoItemGroupDfPk ppemtPInfoItemGroupDfPk;

	@Basic(optional = false)
	@Column(name = "CID")
	public String companyId;


	@Override
	protected Object getKey() {
		return this.ppemtPInfoItemGroupDfPk;
	}
}
