package nts.uk.ctx.at.schedule.dom.shift.management;

/**
 * 組織別シフトパレットを複写する
 * @author phongtq
 *
 */

public class ShiftPalletsOrgService {

	
//	public static Optional<AtomTask> modify(Require require, ShiftPalletsOrg shiftPalletsOrg, TargetOrgIdenInfor shiftTargeOrg, boolean overWrite){
//		
//		// 1: 複写先リスト=get*(複写先組織):組織別シフトパレット
//		
//		ShiftPalletsOrg shiftPalletsNew = require.getShiftPalletOrg(shiftPalletsOrg.getTargeOrg().getUnit().value, shiftPalletsOrg.getTargeOrg().getWorkplaceId().get(), shiftPalletsOrg.getTargeOrg().getWorkplaceGroupId().get());
//		
//		// 2: 
//		//修正する( シフトパレット)
//		//複写先リスト
//		
//		// 3: 複写する(複写先組織): 組織別シフトパレット
//		ShiftPalletsOrg shiftPalletsOrgNew = shiftPalletsOrg.copy(shiftTargeOrg);
//		// insert
//		require.add(shiftPalletsOrgNew);
//		
//		return Optional.of(AtomTask.of(() -> {
//			// 4: persist
//			require.add(null);
//			require.update(null);
//		}));
//		
//	} 

	public static interface Require {
		
		void add(ShiftPalletsOrg shiftPalletsOrg) ;
		
		void update(ShiftPalletsOrg shiftPalletsOrg);
		
		public ShiftPalletsOrg getShiftPalletOrg(int targetUnit, String targetId, String workplaceGroupId);
		
	}
}
