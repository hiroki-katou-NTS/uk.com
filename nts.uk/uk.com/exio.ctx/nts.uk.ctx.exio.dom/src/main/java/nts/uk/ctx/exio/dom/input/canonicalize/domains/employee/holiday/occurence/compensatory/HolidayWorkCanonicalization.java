package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.occurence.compensatory;

import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.ItemNoMap;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.occurence.OccurenceHolidayCanonicalizationBase;
import nts.uk.ctx.exio.dom.input.canonicalize.result.CanonicalItem;
import nts.uk.ctx.exio.dom.input.canonicalize.result.IntermediateResult;

/**
 * 休出管理データの正準化
 */
public class HolidayWorkCanonicalization extends OccurenceHolidayCanonicalizationBase {

	@Override
	public ItemNoMap getItemNoMap() {
		return ItemNoMap.reflection(Items.class);
	}
	
	public static class Items {
		public static final int 社員コード = 1;
		public static final int 休出日 = 2;
		public static final int 使用期限日 = 3;
		public static final int 休出日数 = 4;
		public static final int 休出時間数 = 5;
		public static final int 一日相当時間 = 6;
		public static final int 半日相当時間 = 7;
		public static final int 休出データID = 100;
		public static final int SID = 101;
		public static final int 日付不明 = 102;
		public static final int 代休消化区分 = 103;
		public static final int 消滅日 = 104;
	}

	@Override
	protected IntermediateResult canonicalizeExtends(
			DomainCanonicalization.RequireCanonicalize require,
			ExecutionContext context, IntermediateResult interm) {
		
		return interm
			    .addCanonicalized(CanonicalItem.of(Items.代休消化区分, 0));
	}
	
	public static interface RequireAdjust {
		void deleteAllLeaveManagementData(String employeeId);
	}

	@Override
	protected void deleteExistingData(OccurenceHolidayCanonicalizationBase.RequireAdjust require, String employeeId) {
		require.deleteAllLeaveManagementData(employeeId);
	}

}
