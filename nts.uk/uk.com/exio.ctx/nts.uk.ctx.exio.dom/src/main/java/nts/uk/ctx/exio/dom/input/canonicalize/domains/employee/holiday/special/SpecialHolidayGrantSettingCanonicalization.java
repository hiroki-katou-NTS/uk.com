package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.special;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.val;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalItemList;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizeUtil;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.KeyValues;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.EmployeeIndependentCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.EmployeeCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.IntermediateResult;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportError;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;

/**
 * 社員の特別休暇付与設定の正準化
 */
public class SpecialHolidayGrantSettingCanonicalization extends EmployeeIndependentCanonicalization {
	
	private final EmployeeCodeCanonicalization employeeCodeCanonicalization;
	
	public SpecialHolidayGrantSettingCanonicalization(DomainWorkspace workspace) {
		super(workspace);
		this.employeeCodeCanonicalization = new EmployeeCodeCanonicalization(workspace);
	}
	
	@Override
	public void canonicalize(DomainCanonicalization.RequireCanonicalize require,ExecutionContext context) {
		// 受入データ内の重複チェック用
		Set<KeyValues> importingKeys = new HashSet<>();
		
		CanonicalizeUtil.forEachEmployee(require, context, employeeCodeCanonicalization, interms ->{
			for(val interm : interms) {
				val keyValue = getPrimaryKeys(interm, workspace);
				if (importingKeys.contains(keyValue)) {
					require.add(context, ExternalImportError.record(interm.getRowNo(), "受入データの中にキーの重複があります。"));
					return; // 次のレコードへ
				}
				importingKeys.add(keyValue);
				val addedInterm = interm.addCanonicalized(getFixedItems());
				super.canonicalize(require, context, addedInterm, keyValue);
			}
		});
	}
	
	@Override
	protected IntermediateResult canonicalizeExtends(
			DomainCanonicalization.RequireCanonicalize require,
			ExecutionContext context, IntermediateResult interm) {
		return interm;
	}
	
	private static CanonicalItemList getFixedItems() {
		return new CanonicalItemList()
			// 適用設定
			.add(102, 0);
	}
	
	private KeyValues getPrimaryKeys(IntermediateResult interm, DomainWorkspace workspace) {
		return new KeyValues(Arrays.asList(interm.getItemByNo(this.getItemNoOfEmployeeId()).get().getString()));
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
		return Arrays.asList(DomainDataColumn.SID);
	}
	
	@Override
	public ImportingDataMeta appendMeta(ImportingDataMeta source) {
		return super.appendMeta(source);
	}
}
