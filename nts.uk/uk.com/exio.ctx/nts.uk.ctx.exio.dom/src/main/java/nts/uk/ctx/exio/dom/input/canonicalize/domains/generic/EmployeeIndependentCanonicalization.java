package nts.uk.ctx.exio.dom.input.canonicalize.domains.generic;

import static java.util.stream.Collectors.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import lombok.val;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizeUtil;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.EmployeeCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.result.IntermediateResult;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportError;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;

/**
 * IndependentCanonicalizationで処理できるが社員コードが加わったパターン
 */
public abstract class EmployeeIndependentCanonicalization extends IndependentCanonicalization {

	private final EmployeeCodeCanonicalization employeeCodeCanonicalization;
	
	public EmployeeIndependentCanonicalization() {
		this.employeeCodeCanonicalization = new EmployeeCodeCanonicalization(getItemNoMap());
	}
	
	@Override
	public void canonicalize(DomainCanonicalization.RequireCanonicalize require, ExecutionContext context) {
		
		// 受入データ内の重複チェック
		Set<List<Object>> importingKeys = new HashSet<>();
		
		val workspace = require.getDomainWorkspace(context.getDomainId());
		
		CanonicalizeUtil.forEachEmployee(require, context, employeeCodeCanonicalization, interms -> {
			
			for (val interm : interms) {
				
				val key = getPrimaryKeys(interm, workspace);
				if (importingKeys.contains(key)) {
					require.add(ExternalImportError.record(interm.getRowNo(), context.getDomainId(),"受入データの中にキーの重複があります。"));
					return; // 次のレコードへ
				}
				
				importingKeys.add(key);
				
				super.canonicalize(require, context, interm);
			}
		});
	}
	
	public static interface RequireCanonicalize {
		DomainWorkspace getDomainWorkspace(ImportingDomainId domainId);
	}
	
	/**
	 * 派生クラス側で追加の正準化が必要ならOverrideする
	 * @param require
	 * @param context
	 * @param interm
	 * @return
	 */
	protected abstract Optional<IntermediateResult> canonicalizeExtends(
			DomainCanonicalization.RequireCanonicalize require,
			ExecutionContext context,
			IntermediateResult interm);
	
	private static List<Object> getPrimaryKeys(IntermediateResult record, DomainWorkspace workspace) {
		
		return workspace.getItemsPk().stream()
				.map(k -> record.getItemByNo(k.getItemNo()).get())
				.map(item -> item.getValue())
				.collect(toList());
	}
}
