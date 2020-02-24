package nts.uk.ctx.at.schedule.infra.entity.shift.management;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscmtPaletteCmpCombiDtlPk {
	
	
	/** 会社ID */
	@Column(name = "CID")
	public String companyId;

	/** ページ */
	@Column(name = "PAGE")
	public int page;
	
	/** 位置番号 */
	@Column(name = "POSITION")
	public int position;
	
	/** 順番 */
	@Column(name = "POSITION_ORDER")
	public int positionOrder;
}
									
									
