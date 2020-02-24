package nts.uk.ctx.at.schedule.infra.entity.shift.management;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.shift.management.Combinations;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 会社別シフトパレット
 * 
 * @author phongtq
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_PALETTE_CMP_COMBI_DTL")
public class KscmtPaletteCmpCombiDtl extends UkJpaEntity{
	
	@EmbeddedId
	public KscmtPaletteCmpCombiDtlPk pk;
	
	/** シフトマスタコード */
	@Column(name = "SHIFT_MASTER_CD")
	public String shiftMasterCd;
	
	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
    	@PrimaryKeyJoinColumn(name = "POSITION", referencedColumnName = "POSITION")
    })
	public KscmtPaletteCmpCombi kscmtPaletteCmpCombi;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

	public static KscmtPaletteCmpCombiDtl fromDomain(Combinations combinations, KscmtPaletteCmpCombiPk cmpCombiPk) {
		// TODO Auto-generated method stub
		KscmtPaletteCmpCombiDtlPk combiDtlPk = new KscmtPaletteCmpCombiDtlPk(AppContexts.user().companyId(), cmpCombiPk.page, cmpCombiPk.position, combinations.getOrder());
		return new KscmtPaletteCmpCombiDtl(combiDtlPk, combinations.getShiftCode().v(), null);
	}

	public Combinations toDomain() {
		return new Combinations(pk.positionOrder, new ShiftPalletCode(shiftMasterCd));
	}

}
									
