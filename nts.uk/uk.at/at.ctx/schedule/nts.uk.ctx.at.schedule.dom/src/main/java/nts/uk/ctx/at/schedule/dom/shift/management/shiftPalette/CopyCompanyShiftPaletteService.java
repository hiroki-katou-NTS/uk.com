package nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette;

import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;

/**
 * DS : 会社別シフトパレットを複製する
 * @author tutk
 *
 */
public class CopyCompanyShiftPaletteService {
	/**
	 * [1] 複製する																							
	 * @param require
	 * @param shiftPalletsCom 複製元のシフトパレット
	 * @param page 複製先のページ
	 * @param shiftPalletName 複製先の名称
	 * @param overwrite 上書きするか
	 * @return
	 */
	public static AtomTask duplicate(Require require,ShiftPaletteCom shiftPalletsCom,
			int page,ShiftPaletteName shiftPalletName,boolean overwrite) {
		boolean checkExists = require.exists(shiftPalletsCom.getCompanyId(), page);
		if(checkExists && !overwrite) {
			throw new BusinessException("Msg_1712",page+"");
		}
		return AtomTask.of(() -> {
			if(checkExists) {
				require.deleteByPage(shiftPalletsCom.getCompanyId(), page);
			}
			ShiftPaletteCom shiftPalletsComNew = shiftPalletsCom.reproduct(page, shiftPalletName);
			require.add(shiftPalletsComNew);
		});
	}
	
	public static interface Require {
		/**
		 * [R-1] 会社別シフトパレットを存在するか  ShiftPalletsComRepository
		 * @param companyID
		 * @param page
		 * @return
		 */
		boolean exists(String companyID,int page);
		
		/**
		 * [R-2]会社別シフトパレットを削除する  ShiftPalletsComRepository		 																					
		 * @param companyID
		 * @param page
		 */
		void deleteByPage(String companyID,int page);
		
		/**
		 * [R-3] 会社別シフトパレットを登録する  ShiftPalletsComRepository																							
		 * @param shiftPalletsCom
		 */
		void add(ShiftPaletteCom shiftPalletsCom);
		
	}
}
