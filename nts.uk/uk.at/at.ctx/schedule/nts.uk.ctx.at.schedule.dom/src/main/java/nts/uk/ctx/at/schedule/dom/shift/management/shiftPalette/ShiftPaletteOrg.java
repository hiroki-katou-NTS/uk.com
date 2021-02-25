package nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 組織別シフトパレット
 * 
 * @author phongtq
 *
 */
@AllArgsConstructor
public class ShiftPaletteOrg implements DomainAggregate {

	/** 対象組織 */
	@Getter
	private final TargetOrgIdenInfor targeOrg;

	/** ページ */
	@Getter
	private final int page;

	/** シフトパレット */
	@Getter
	private ShiftPalette shiftPallet;

	public static ShiftPaletteOrg create(TargetOrgIdenInfor targeOrg, int page, ShiftPalette shiftPallet) {

		// inv-1 1 <= @ページ <= 10
		if (!(1 <= page && page <= 10)) {
			throw new BusinessException("Msg_1615");
		}

		// シフトパレット.組み合わせ.シフト組み合わせの順番を整頓する()
		shiftPallet.getCombinations().sort((p1, p2) -> p1.getPositionNumber() - p2.getPositionNumber());

		return new ShiftPaletteOrg(targeOrg, page, shiftPallet);
	}

	/**
	 * [1] 修正する
	 * 
	 * @param require
	 * @param shiftPallet
	 */
	public void modifyShiftPallets(ShiftPalette shiftPallet) {
		// シフトパレット.組み合わせ.シフト組み合わせの順番を整頓する()
		shiftPallet.getCombinations().sort((p1, p2) -> p1.getPositionNumber() - p2.getPositionNumber());

		this.shiftPallet = shiftPallet;
	}
	
	/**
	 * [2] 複製する
	 * @author tutk
	 *
	 */
	public ShiftPaletteOrg reproduct(int destinationPage, ShiftPaletteName shiftPalletName){
		ShiftPalette shiftPallet = this.shiftPallet.reproduct(shiftPalletName);
		return new ShiftPaletteOrg(this.targeOrg, destinationPage, shiftPallet);
	}

	public static interface Require {
		// 会社別シフトパレットRepository.Update(会社別シフトパレット)
		void update(ShiftPaletteOrg shiftPalletsOrg);

	}
}
