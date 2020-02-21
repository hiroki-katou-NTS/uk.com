package nts.uk.ctx.at.schedule.infra.entity.shift.management;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 組織別シフトパレットの組み合わせ明細
 * @author phongtq
 *
 */

@Entity
@Table(name = "KSCMT_PALETTE_ORG_COMBI_DTL")
@AllArgsConstructor
@NoArgsConstructor
public class KscmtPaletteOrgCombiDtl extends UkJpaEntity{
	
	@EmbeddedId
	public KscmtPaletteOrgCombiDtlPk pk;
	
	/** シフトマスタコード */
	@Column(name = "SHIFT_MASTER_CD")
	public String shiftMasterCd;

	@Override
	protected Object getKey() {
		return pk;
	}

}
