/**
 * 
 */
package nts.uk.ctx.pereg.infra.entity.layout.cls.definition;

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
@Table(name = "PPEMT_LAYOUT_ITEM_CLS_DF")
@Entity
public class PpemtLayoutItemClsDf extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public PpemtLayoutItemClsDfPk ppemtLayoutItemClsDfPk;
	
	@Basic(optional = false)
	@Column(name = "PER_INFO_ITEM_DEF_ID")
	public String itemDfID;
	
	
	@Override
	protected Object getKey() {
		return this.ppemtLayoutItemClsDfPk;
	}
}
