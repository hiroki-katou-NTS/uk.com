package nts.uk.ctx.at.schedule.dom.shift.management;

import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * DS: 組織別シフトパレットを複製する
 * @author tutk
 *
 */
public class CopyShiftPaletteByOrgService {
	
	/**
	 * [1] 複製する																							
	 * @param require
	 * @param shiftPalletsOrg 複製元のシフトパレット
	 * @param page 複製先のページ
	 * @param shiftPalletName 複製先の名称
	 * @param overwrite 上書きするか
	 * @return
	 */
	public static AtomTask duplicate(Require require,ShiftPalletsOrg shiftPalletsOrg,
			int page,ShiftPalletName shiftPalletName,boolean overwrite) {
		boolean checkExists = require.exists(shiftPalletsOrg.getTargeOrg(), page);
		if(checkExists && !overwrite) {
			throw new BusinessException("Msg_1712",page+"");
		}
		return AtomTask.of(() -> {
			if(checkExists) {
				require.delete(shiftPalletsOrg.getTargeOrg(), page);
			}
			ShiftPalletsOrg shiftPalletsOrgNew = shiftPalletsOrg.reproduct(page, shiftPalletName);
			require.add(shiftPalletsOrgNew);
		});
	}

	public static interface Require {
		/**
		 * [R-1] 組織別シフトパレットを存在するか	ShiftPalletsOrgRepository	 																					
		 * @param targeOrg
		 * @param page
		 * @return
		 */
		 boolean exists(TargetOrgIdenInfor targeOrg,int page);
		 
		 /**
		  * [R-2] 組織別シフトパレットを削除する	ShiftPalletsOrgRepository
		  * @param targeOrg
		  * @param page
		  */
		 void delete(TargetOrgIdenInfor targeOrg,int page);
		 /**
		  * [R-3] 組織別シフトパレットを登録する	ShiftPalletsOrgRepository																						
		  * @param shiftPalletsOrg
		  */
		 void add(ShiftPalletsOrg shiftPalletsOrg);
		 
	}
}
