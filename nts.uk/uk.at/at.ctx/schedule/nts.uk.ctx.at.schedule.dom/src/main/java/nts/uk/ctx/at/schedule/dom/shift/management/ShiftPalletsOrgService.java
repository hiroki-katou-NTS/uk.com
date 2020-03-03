package nts.uk.ctx.at.schedule.dom.shift.management;

import nts.arc.task.tran.AtomTask;

/**
 * 組織別シフトパレットを複写する
 * @author phongtq
 *
 */

public class ShiftPalletsOrgService {
	
	public static AtomTask modify(Require require, ShiftPalletsOrg shiftPalletsOrg, ShiftTargeOrg shiftTargeOrg, boolean overWrite){
		
		// 1
		
		
		return AtomTask.of(() -> {
			// 4: persist
			require.add(null);
			require.update(null);
		});
		
	} 

	public static interface Require {
		
		void add(ShiftPalletsOrg shiftPalletsOrg) ;
		
		void update(ShiftPalletsOrg shiftPalletsOrg);
		
	}
}
