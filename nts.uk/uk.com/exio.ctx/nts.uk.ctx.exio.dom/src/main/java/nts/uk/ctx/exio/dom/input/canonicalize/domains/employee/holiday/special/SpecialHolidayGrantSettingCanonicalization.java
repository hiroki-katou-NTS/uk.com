package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.special;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalItemList;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.EmployeeIndependentCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.IntermediateResult;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;

/**
 * 社員の特別休暇付与設定の正準化
 */
public class SpecialHolidayGrantSettingCanonicalization extends EmployeeIndependentCanonicalization {
	
	public SpecialHolidayGrantSettingCanonicalization(DomainWorkspace workspace) {
		super(workspace);
	}
	
	@Override
	protected IntermediateResult canonicalizeExtends(
			DomainCanonicalization.RequireCanonicalize require,
			ExecutionContext context, IntermediateResult interm) {
		return interm.addCanonicalized(getFixedItems());
	}
	
	private static CanonicalItemList getFixedItems() {
		return new CanonicalItemList()
			// 適用設定
			.add(102, 0);
	}
	
	@Override
	protected List<Integer> getPrimaryKeyItemNos(DomainWorkspace workspace){
		return Arrays.asList(101);
	}
	
	@Override
	protected String getParentTableName() {
		return "KRCMT_HDSP_BASIC";
	}
	
	@Override
	protected List<String> getChildTableNames() {
		return Collections.emptyList();
	}
	
	@Override
	protected List<DomainDataColumn> getDomainDataKeys() {
		return Arrays.asList(
				DomainDataColumn.SID);
	}
	
	@Override
	public ImportingDataMeta appendMeta(ImportingDataMeta source) {
		return super.appendMeta(source);
	}
}
