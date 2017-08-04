/**
 * 
 */
package entity.person.itemclassification;

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

/**
 * @author laitv
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PPEMT_LAYOUT_ITEM_CLS")
@Entity
public class PpemtLayoutItemCls extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public PpemtLayoutItemClsPk ppemtLayoutItemClsPk;
	
	@Basic(optional = false)
	@Column(name = "PER_INFO_CATEGORY_ID")
	public String categoryId;
	
	@Basic(optional = false)
	@Column(name = "LAYOUT_ITEM_TYPE")
	public String itemType;
	
	@Override
	protected Object getKey() {
		return this.ppemtLayoutItemClsPk;
	}
}
