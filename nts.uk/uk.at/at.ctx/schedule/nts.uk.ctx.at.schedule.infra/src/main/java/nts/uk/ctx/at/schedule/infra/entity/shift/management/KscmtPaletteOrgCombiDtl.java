package nts.uk.ctx.at.schedule.infra.entity.shift.management;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.shift.management.Combinations;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 組織別シフトパレットの組み合わせ明細
 * @author phongtq
 *
 */

@Entity
@Table(name = "KSCMT_PALETTE_ORG_COMBI_DTL")
@AllArgsConstructor
@NoArgsConstructor
public class KscmtPaletteOrgCombiDtl extends ContractUkJpaEntity{
	
	@EmbeddedId
	public KscmtPaletteOrgCombiDtlPk pk;
	
	/** シフトマスタコード */
	@Column(name = "SHIFT_MASTER_CD")
	public String shiftMasterCd;

	@ManyToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
    	@PrimaryKeyJoinColumn(name = "TARGET_UNIT", referencedColumnName = "TARGET_UNIT"),
    	@PrimaryKeyJoinColumn(name = "TARGET_ID", referencedColumnName = "TARGET_ID"),
    	@PrimaryKeyJoinColumn(name = "PAGE", referencedColumnName = "PAGE"),
    	@PrimaryKeyJoinColumn(name = "POSITION", referencedColumnName = "POSITION")
    })
	public KscmtPaletteOrgCombi kscmtPaletteOrgCombi;
	@Override
	protected Object getKey() {
		return pk;
	}
	public static KscmtPaletteOrgCombiDtl fromDomain(Combinations combinations, KscmtPaletteOrgCombiPk orgCombiPk) {
		// TODO Auto-generated method stub
		KscmtPaletteOrgCombiDtlPk combiDtlPk = new KscmtPaletteOrgCombiDtlPk(AppContexts.user().companyId(),
				orgCombiPk.targetUnit, orgCombiPk.targetId, orgCombiPk.page, orgCombiPk.position, combinations.getOrder());
				
		return new KscmtPaletteOrgCombiDtl(combiDtlPk, combinations.getShiftCode().v(), null);
	}
	public Combinations toDomain() {
		return new Combinations(pk.positionOrder, new ShiftMasterCode(shiftMasterCd));
	}

	public static KscmtPaletteOrgCombiDtl fromOneDomain(int targetUnit, String targetId, int page, int position, int positionOrder, String shiftMasterCd) {
		// TODO Auto-generated method stub
		KscmtPaletteOrgCombiDtlPk combiDtlPk = new KscmtPaletteOrgCombiDtlPk(AppContexts.user().companyId(),
				targetUnit, targetId, page, position, positionOrder);
		return new KscmtPaletteOrgCombiDtl(combiDtlPk, shiftMasterCd, null);
	}

	public void toEntity(Combinations combinations) {
		this.shiftMasterCd = combinations.getShiftCode().v();
	}
}
