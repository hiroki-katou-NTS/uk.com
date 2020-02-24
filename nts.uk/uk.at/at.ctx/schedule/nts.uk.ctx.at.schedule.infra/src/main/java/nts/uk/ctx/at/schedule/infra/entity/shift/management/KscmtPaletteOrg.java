package nts.uk.ctx.at.schedule.infra.entity.shift.management;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 組織別シフトパレット
 * @author phongtq
 *
 */

@Entity
@Table(name = "KSCMT_PALETTE_ORG")
@AllArgsConstructor
@NoArgsConstructor
public class KscmtPaletteOrg extends UkJpaEntity{
	
	@EmbeddedId
	public KscmtPaletteOrgPk pk;
	
	/** 名称 */
	@Column(name = "PAGE_NAME")
	public String pageName;
	
	/** 使用区分 */
	@Column(name = "USE_ATR")
	public int useAtr;
	
	/** 備考 */
	@Column(name = "NOTE")
	public String note;

	@Override
	protected Object getKey() {
		return pk;
	}

}
