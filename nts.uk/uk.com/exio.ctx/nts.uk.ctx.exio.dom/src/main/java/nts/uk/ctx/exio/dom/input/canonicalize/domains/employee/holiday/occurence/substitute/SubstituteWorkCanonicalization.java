package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.occurence.substitute;

import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalItem;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.occurence.OccurenceHolidayCanonicalizationBase;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.IntermediateResult;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;

/**
 * 振出管理データの正準化
 */
public class SubstituteWorkCanonicalization extends OccurenceHolidayCanonicalizationBase {

	public SubstituteWorkCanonicalization(DomainWorkspace workspace) {
		super(workspace);
	}

	@Override
	protected IntermediateResult canonicalizeExtends(
			DomainCanonicalization.RequireCanonicalize require,
			ExecutionContext context, IntermediateResult interm) {
		
		return interm
				// 振休消化区分
				.addCanonicalized(CanonicalItem.of(103, 0))
				// 法定内外区分
				.addCanonicalized(CanonicalItem.of(105, 0));
	}
	
	public static interface RequireAdjust {
		void deleteAllPayoutManagementData(String employeeId);
	}

	@Override
	protected void deleteExistingData(OccurenceHolidayCanonicalizationBase.RequireAdjust require, String employeeId) {
		require.deleteAllPayoutManagementData(employeeId);
	}
	
	@Override
	public ImportingDataMeta appendMeta(ImportingDataMeta source) {
		return super.appendMeta(source).addItem("振出データID");
	}
}
