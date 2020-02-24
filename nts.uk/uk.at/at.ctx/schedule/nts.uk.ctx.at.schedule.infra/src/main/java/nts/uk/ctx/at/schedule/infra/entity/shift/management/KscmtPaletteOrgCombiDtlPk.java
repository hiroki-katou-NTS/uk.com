package nts.uk.ctx.at.schedule.infra.entity.shift.management;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 
 * @author phongtq
 *
 */

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscmtPaletteOrgCombiDtlPk {
	
	/** 会社ID */
	@Column(name = "CID")
	public String companyId;
	
	/** 対象組織の単位    0:職場    1:職場グループ */
	@Column(name = "TARGET_UNIT")
	public int targetUnit;

	/** 対象組織の単位に応じたID  0:職場ID  1:職場グループID */
	@Column(name = "TARGET_ID")
	public String targetId;

	/** ページ */
	@Column(name = "PAGE")
	public int page;
	
	/** ページ */
	@Column(name = "POSITION")
	public int position;
	
	/** ページ */
	@Column(name = "POSITION_ORDER")
	public int positionOrder;
	
	
}
