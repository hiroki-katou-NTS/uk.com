/**
 * 
 */
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

/**
 * @author laitv
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PPEMT_LAYOUT_MAINTENANCE")
@Entity
public class PpemtLayoutMaintenance extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public PpemtLayoutMaintenancePk ppemtLayoutMaintenancePk;
	
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
		return this.ppemtLayoutMaintenancePk;
	}
}
