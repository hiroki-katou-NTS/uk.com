package nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * 会社別シフトパレット
 * @author phongtq
 *
 */
@AllArgsConstructor
public class ShiftPaletteCom implements DomainAggregate {

	/** 会社ID */
	@Getter
	private final String companyId;
	
	/** ページ */
	@Getter
	private final int page;
		
	/** シフトパレット */
	@Getter
	private ShiftPalette shiftPallet;

	/**
	 * [C-1] 作る
	 * @param companyId
	 * @param page
	 * @param shiftPallet
	 */
	// 	説明：新しい＜会社別シフトパレット＞を作る。
	public static ShiftPaletteCom create(String companyId, int page, ShiftPalette shiftPallet) {
		// case inv-1: Msg_1615	
		if(!(1 <= page && page <= 10)){
			throw new BusinessException("Msg_1615");
		}
		// シフトパレット.組み合わせ.シフト組み合わせの順番を整頓する()
		shiftPallet.getCombinations().sort((p1, p2) -> p1.getPositionNumber() - p2.getPositionNumber());
		
		 return new ShiftPaletteCom(companyId, page, shiftPallet);
	}
	/**
	 * [1] 修正する
	 * @param shiftPallet
	 */
	public void modifyShiftPallets(ShiftPalette shiftPallet){
		// シフトパレット.組み合わせ.シフト組み合わせの順番を整頓する()		
		shiftPallet.getCombinations().sort((p1, p2) -> p1.getPositionNumber() - p2.getPositionNumber());
		this.shiftPallet = shiftPallet;
	}
	/**
	 * [2] 複製する
	 * @author tutk
	 *
	 */
	public ShiftPaletteCom reproduct(int destinationPage, ShiftPaletteName shiftPalletName){
		ShiftPalette shiftPallet = this.shiftPallet.reproduct(shiftPalletName);
		return new ShiftPaletteCom(this.companyId, destinationPage, shiftPallet);
	}
	
	
	public static interface Require {
		// 会社別シフトパレットRepository.Update(会社別シフトパレット)
		void update(ShiftPaletteCom shiftPalletsCom);
	}
	
}
