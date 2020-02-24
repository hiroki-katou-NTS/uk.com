package nts.uk.ctx.at.schedule.infra.entity.shift.management;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 組織別シフトパレットの組み合わせ
 * @author phongtq
 *
 */

@Entity
@Table(name = "KSCMT_PALETTE_ORG_COMBI")
@AllArgsConstructor
@NoArgsConstructor
public class KscmtPaletteOrgCombi extends UkJpaEntity{
	
	@EmbeddedId
	public KscmtPaletteOrgCombiPk pk;
	
	/** 位置番号 */
	@Column(name = "POSITION_NAME")
	public String positionName;

	@Override
	protected Object getKey() {
		return pk;
	}

}
