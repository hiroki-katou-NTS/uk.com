/**
 * 
 */
package nts.uk.ctx.pereg.infra.entity.layout.cls;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * @author laitv
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PPEMT_LAYOUT_ITEM_CLS")
public class PpemtLayoutItemCls extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public PpemtLayoutItemClsPk ppemtLayoutItemClsPk;

	@Basic(optional = false)
	@Column(name = "PER_INFO_CATEGORY_ID")
	public String categoryId;

	@Basic(optional = false)
	@Column(name = "LAYOUT_ITEM_TYPE")
	public int itemType;

	@Override
	protected Object getKey() {
		return this.ppemtLayoutItemClsPk;
	}
}
