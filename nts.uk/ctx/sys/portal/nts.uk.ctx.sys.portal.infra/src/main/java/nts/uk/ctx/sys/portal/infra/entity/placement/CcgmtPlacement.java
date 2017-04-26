package nts.uk.ctx.sys.portal.infra.entity.placement;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author LamDT
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CCGMT_PLACEMENT")
public class CcgmtPlacement extends UkJpaEntity {

	@EmbeddedId
	public CcgmtPlacementPK ccgmtPlacementPK;

	@Column(name = "LAYOUT_ID")
	public String layoutID;

	@Column(name = "COLUMN")
	public int column;

	@Column(name = "ROW")
	public int row;

	@Column(name = "WIDTH")
	public int width;

	@Column(name = "HEIGHT")
	public int height;

	@Column(name = "EXTERNAL_URL")
	public String externalUrl;

	@Column(name = "TOPPAGE_PART_ID")
	public String topPagePartID;

	@Override
	protected Object getKey() {
		return ccgmtPlacementPK;
	}

}
