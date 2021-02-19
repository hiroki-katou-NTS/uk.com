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
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftCombinationName;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteCombinations;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 会社別シフトパレット
 * 
 * @author phongtq
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_PALETTE_CMP_COMBI")
public class KscmtPaletteCmpCombi extends ContractUkJpaEntity {

	@EmbeddedId
	public KscmtPaletteCmpCombiPk pk;

	/** シフトパレット組み合わせの名称 */
	@Column(name = "POSITION_NAME")
	public String positionName;

	@ManyToOne
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
			@PrimaryKeyJoinColumn(name = "PAGE", referencedColumnName = "PAGE") })
	public KscmtPaletteCmp kscmtPaletteCmp;

	@OneToMany(targetEntity = KscmtPaletteCmpCombiDtl.class, mappedBy = "kscmtPaletteCmpCombi", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "KSCMT_PALETTE_CMP_COMBI_DTL")
	public List<KscmtPaletteCmpCombiDtl> cmpCombiDtls;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public ShiftPaletteCombinations toDomain() {
		return new ShiftPaletteCombinations(pk.position, new ShiftCombinationName(positionName),
				cmpCombiDtls.stream().map(x -> x.toDomain()).collect(Collectors.toList()));
	}

	public static KscmtPaletteCmpCombi fromDomain(ShiftPaletteCombinations shiftPalletCombinations,
			KscmtPaletteCmpPk pk) {
		KscmtPaletteCmpCombiPk cmpCombiPk = new KscmtPaletteCmpCombiPk(AppContexts.user().companyId(), pk.page,
				shiftPalletCombinations.getPositionNumber());
		return new KscmtPaletteCmpCombi(cmpCombiPk, shiftPalletCombinations.getCombinationName().v(), null,
				shiftPalletCombinations.getCombinations().stream()
						.map(x -> KscmtPaletteCmpCombiDtl.fromDomain(x, cmpCombiPk)).collect(Collectors.toList()));
	}
	
	public static KscmtPaletteCmpCombi fromOneDomain(ShiftPaletteCombinations shiftPalletCombinations,
			KscmtPaletteCmpPk pk) {
		KscmtPaletteCmpCombiPk cmpCombiPk = new KscmtPaletteCmpCombiPk(AppContexts.user().companyId(), pk.page,
				shiftPalletCombinations.getPositionNumber());
		return new KscmtPaletteCmpCombi(cmpCombiPk, shiftPalletCombinations.getCombinationName().v(), null,
				null);
	}

	public void toEntity(ShiftPaletteCombinations shiftPalletCombinations) {
		this.positionName = shiftPalletCombinations.getCombinationName().v();
		cmpCombiDtls.stream().forEach(x -> {
			if(shiftPalletCombinations.getCombinations().stream()
					.filter(y -> x.pk.positionOrder == y.getOrder()).findFirst().isPresent()) {
				x.toEntity(shiftPalletCombinations.getCombinations().stream()
						.filter(y -> x.pk.positionOrder == y.getOrder()).findFirst().get());
			}
		});
	}
}
