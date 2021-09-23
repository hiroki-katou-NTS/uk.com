package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.occurence.substitute;

import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalItem;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.occurence.OccurenceHolidayCanonicalizationBase;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.IntermediateResult;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;

/**
 * 振休管理データの正準化
 */
public class SubstituteHolidayCanonicalization extends OccurenceHolidayCanonicalizationBase {
	
	public SubstituteHolidayCanonicalization(DomainWorkspace workspace) {
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
		
		void deleteAllSubstitutionOfHDManagementData(String employeeId);
	}


	@Override
	protected void deleteExistingData(OccurenceHolidayCanonicalizationBase.RequireAdjust require, String employeeId) {
		require.deleteAllSubstitutionOfHDManagementData(employeeId);
	}
	
	@Override
	public ImportingDataMeta appendMeta(ImportingDataMeta source) {
		return super.appendMeta(source).addItem("振休データID");
	}
}
