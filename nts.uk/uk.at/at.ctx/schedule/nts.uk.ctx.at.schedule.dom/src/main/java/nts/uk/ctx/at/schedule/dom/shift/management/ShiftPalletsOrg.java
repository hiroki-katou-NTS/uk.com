package nts.uk.ctx.at.schedule.dom.shift.management;

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

public class ShiftPalletsOrg implements DomainAggregate {

	/** 対象組織 */
	@Getter
	private final TargetOrgIdenInfor targeOrg;

	/** ページ */
	@Getter
	private final int page;

	/** シフトパレット */
	@Getter
	private ShiftPallet shiftPallet;

	public ShiftPalletsOrg(TargetOrgIdenInfor targeOrg, int page, ShiftPallet shiftPallet) {

		// inv-1 1 <= @ページ <= 10
		if (!(1 <= page && page <= 10)) {
			throw new BusinessException("Msg_1615");
		}

		// シフトパレット.組み合わせ.シフト組み合わせの順番を整頓する()
		shiftPallet.getCombinations().sort((p1, p2) -> p1.getPositionNumber() - p2.getPositionNumber());

		this.targeOrg = targeOrg;
		this.page = page;
		this.shiftPallet = shiftPallet;
	}

	/**
	 * [1] 修正する
	 * 
	 * @param require
	 * @param shiftPallet
	 */
	public void modifyShiftPallets(ShiftPallet shiftPallet) {
		// シフトパレット.組み合わせ.シフト組み合わせの順番を整頓する()
		shiftPallet.getCombinations().sort((p1, p2) -> p1.getPositionNumber() - p2.getPositionNumber());

		this.shiftPallet = shiftPallet;
	}

	public static interface Require {
		// 会社別シフトパレットRepository.Update(会社別シフトパレット)
		void update(ShiftPalletsOrg shiftPalletsOrg);

	}
}
