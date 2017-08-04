/**
 * 
 */
package entity.person.info.groupitem;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import entity.roles.PpemtPersonRolePk;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;


@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PPEMT_PINFO_ITEM_GROUP")
@Entity
public class PpemtPinfoItemGroup extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public PpemtPinfoItemGroupPk ppemtPinfoItemGroupPk;
	
	@Basic(optional = false)
	@Column(name = "CID")
	public String companyId;
	
	@Basic(optional = false)
	@Column(name = "FIELD_GROUP_NAME")
	public String groupName;
	
	@Basic(optional = false)
	@Column(name = "DISPORDER")
	public String dispOrder;

	@Override
	protected Object getKey() {
		return this.ppemtPinfoItemGroupPk;
	}
}
