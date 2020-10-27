/**
 * 
 */
package nts.uk.ctx.pereg.infra.entity.person.info.groupitem;

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
@Table(name = "PPEMT_GROUP_ITEM")
public class PpemtPInfoItemGroup extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public PpemtPInfoItemGroupPk ppemtGroupItemPk;

	@Basic(optional = false)
	@Column(name = "CID")
	public String companyId;

	@Basic(optional = false)
	@Column(name = "FIELD_GROUP_NAME")
	public String groupName;

	@Basic(optional = false)
	@Column(name = "DISPORDER")
	public int dispOrder;

	@Override
	protected Object getKey() {
		return this.ppemtGroupItemPk;
	}
}
