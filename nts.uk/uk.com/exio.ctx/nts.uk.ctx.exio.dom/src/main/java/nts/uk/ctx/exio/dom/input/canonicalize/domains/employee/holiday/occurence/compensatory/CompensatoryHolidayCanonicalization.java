package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.occurence.compensatory;

import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.occurence.OccurenceHolidayCanonicalizationBase;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.IntermediateResult;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;

/**
 * 代休管理データの正準化
 */
public class CompensatoryHolidayCanonicalization extends OccurenceHolidayCanonicalizationBase {

	public CompensatoryHolidayCanonicalization(DomainWorkspace workspace) {
		super(workspace);
	}

	@Override
	protected IntermediateResult canonicalizeExtends(
			DomainCanonicalization.RequireCanonicalize require,
			ExecutionContext context, IntermediateResult interm) {

		// 追加処理不要
		return interm;
	}
	
	public static interface RequireAdjust {
		
		void deleteAllCompensatoryDayOffManaData(String employeeId);
	}

	@Override
	protected void deleteExistingData(OccurenceHolidayCanonicalizationBase.RequireAdjust require, String employeeId) {
		require.deleteAllCompensatoryDayOffManaData(employeeId);
	}

}
