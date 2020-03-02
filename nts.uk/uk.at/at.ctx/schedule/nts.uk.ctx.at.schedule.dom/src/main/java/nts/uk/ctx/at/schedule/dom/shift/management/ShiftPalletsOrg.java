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
	public void modifyShiftPallets(Require require, ShiftPallet shiftPallet) {

		// case inv-1: Msg_1615
		if (!(1 <= page && page <= 10)) {
			throw new BusinessException("Msg_1615");
		}
		// 会社別シフトパレット(最大20個)を修正する。
		if (shiftPallet.getCombinations().size() > 20) {
			throw new BusinessException("Msg_1615");
		}

		// シフトパレット.組み合わせ.シフト組み合わせの順番を整頓する()
		shiftPallet.getCombinations().sort((p1, p2) -> p1.getPositionNumber() - p2.getPositionNumber());
	}

	public ShiftPalletsOrg creAndCancelShiftPallet(TargetOrgIdenInfor targeOrg) {
		// 自分から、パラメータでもらう組織用の<組織別シフトパレット>を作って返す。
		return new ShiftPalletsOrg(targeOrg, page, shiftPallet);
	}

	public static interface Require {
		// 会社別シフトパレットRepository.Update(会社別シフトパレット)
		void update(ShiftPalletsOrg shiftPalletsOrg);

	}
	public ShiftPalletsOrg copy(TargetOrgIdenInfor targetOrg ) {
		return new ShiftPalletsOrg(targetOrg, this.page, this.shiftPallet);
	}
}
