package nts.uk.ctx.at.schedule.infra.entity.shift.management;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.aspose.pdf.PKCS1;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPallet;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletDisplayInfor;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletName;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsCom;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftRemarks;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
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
@Table(name = "KSCMT_PALETTE_CMP")
public class KscmtPaletteCmp extends ContractUkJpaEntity {

	@EmbeddedId
	public KscmtPaletteCmpPk pk;

	/** 名称 */
	@Column(name = "PAGE_NAME")
	public String pageName;

	/** 使用区分 */
	@Column(name = "USE_ATR")
	public int useAtr;

	/** 備考 */
	@Column(name = "NOTE")
	public String note;

	@OneToMany(targetEntity = KscmtPaletteCmpCombi.class, mappedBy = "kscmtPaletteCmp", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "KSCMT_PALETTE_CMP_COMBI")
	public List<KscmtPaletteCmpCombi> cmpCombis;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public static KscmtPaletteCmp fromDomain(ShiftPalletsCom shiftPalletsCom) {
		KscmtPaletteCmpPk pk = new KscmtPaletteCmpPk(AppContexts.user().companyId(), shiftPalletsCom.getPage());
		return new KscmtPaletteCmp(pk, shiftPalletsCom.getShiftPallet().getDisplayInfor().getShiftPalletName().v(),
				shiftPalletsCom.getShiftPallet().getDisplayInfor().getShiftPalletAtr().value,
				shiftPalletsCom.getShiftPallet().getDisplayInfor().getRemarks().v(),
				shiftPalletsCom.getShiftPallet().getCombinations().stream()
						.map(x -> KscmtPaletteCmpCombi.fromDomain(x, pk)).collect(Collectors.toList()));

	}

	public void toEntity(ShiftPalletsCom shiftPalletsCom) {
		this.pageName = shiftPalletsCom.getShiftPallet().getDisplayInfor().getShiftPalletName().v();
		this.useAtr = shiftPalletsCom.getShiftPallet().getDisplayInfor().getShiftPalletAtr().value;
		this.note = shiftPalletsCom.getShiftPallet().getDisplayInfor().getRemarks().v();

		cmpCombis.stream().forEach(x -> {
			x.toEntity(shiftPalletsCom.getShiftPallet().getCombinations().stream()
					.filter(y -> x.pk.position == y.getPositionNumber()).findFirst().get());
		});

	}

	public ShiftPalletsCom toDomain() {
		// TODO Auto-generated method stub
		return new ShiftPalletsCom(AppContexts.user().companyId(), pk.page,
				new ShiftPallet(
						new ShiftPalletDisplayInfor(new ShiftPalletName(pageName),
								EnumAdaptor.valueOf(useAtr, NotUseAtr.class), new ShiftRemarks(note)),
						cmpCombis.stream().map(x -> x.toDomain()).collect(Collectors.toList())));
	}

}
