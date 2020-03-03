package nts.uk.ctx.at.schedule.infra.entity.shift.management;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftCombinationName;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletCombinations;
import nts.uk.shr.com.context.AppContexts;
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
	
	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
    	@PrimaryKeyJoinColumn(name = "PAGE", referencedColumnName = "PAGE")
    })
	public KscmtPaletteOrg kscmtPaletteOrg;
	@OneToMany(targetEntity = KscmtPaletteOrgCombiDtl.class, mappedBy = "kscmtPaletteOrgCombi", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @JoinTable(name = "KSCMT_PALETTE_ORG_COMBI_DTL")
	public List<KscmtPaletteOrgCombiDtl> orgCombiDtls;
	
	@Override
	protected Object getKey() {
		return pk;
	}
	
	public ShiftPalletCombinations toDomain(){
		return new ShiftPalletCombinations(pk.position, new ShiftCombinationName(positionName), orgCombiDtls.stream().map(x -> x.toDomain()).collect(Collectors.toList()));
	}
	
	public static KscmtPaletteOrgCombi fromDomain(ShiftPalletCombinations shiftPalletCombinations, KscmtPaletteOrgPk pk) {
		// TODO Auto-generated method stub
		KscmtPaletteOrgCombiPk orgCombiPk = new KscmtPaletteOrgCombiPk(AppContexts.user().companyId(), pk.targetUnit, pk.targetId, pk.page, shiftPalletCombinations.getPositionNumber());
		return new KscmtPaletteOrgCombi(orgCombiPk, 
				shiftPalletCombinations.getCombinationName().v(), null, 
				shiftPalletCombinations.getCombinations().stream().map(x-> KscmtPaletteOrgCombiDtl.fromDomain(x,orgCombiPk)).collect(Collectors.toList()));
	}

}
