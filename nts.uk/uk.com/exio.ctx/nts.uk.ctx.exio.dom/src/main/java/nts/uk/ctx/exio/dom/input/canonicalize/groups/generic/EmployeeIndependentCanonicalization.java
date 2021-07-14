package nts.uk.ctx.exio.dom.input.canonicalize.groups.generic;

import static java.util.stream.Collectors.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.val;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizeUtil;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.KeyValues;
import nts.uk.ctx.exio.dom.input.canonicalize.groups.GroupCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.IntermediateResult;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.employee.EmployeeCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.workspace.group.GroupWorkspace;

/**
 * IndependentCanonicalizationで処理できるが社員コードが加わったパターン
 */
public abstract class EmployeeIndependentCanonicalization extends IndependentCanonicalization {

	private final EmployeeCodeCanonicalization employeeCodeCanonicalization;
	
	public EmployeeIndependentCanonicalization(GroupWorkspace workspace) {
		super(workspace);
		this.employeeCodeCanonicalization = new EmployeeCodeCanonicalization(workspace);
	}

	@Override
	public void canonicalize(GroupCanonicalization.RequireCanonicalize require, ExecutionContext context) {

		// 受入データ内の重複チェック
		Set<List<Object>> importingKeys = new HashSet<>();
		
		CanonicalizeUtil.forEachEmployee(require, context, employeeCodeCanonicalization, interms -> {

			for (val interm : interms) {
				
				val key = getPrimaryKeys(interm, workspace);
				if (importingKeys.contains(key)) {
					throw new RuntimeException("重複データ" + key);
				}

				importingKeys.add(key);
				
				super.canonicalize(require, context, interm, new KeyValues(key));
			}
		});
	}
	
	private static List<Object> getPrimaryKeys(IntermediateResult record, GroupWorkspace workspace) {
		
		return workspace.getItemsPk().stream()
				.map(k -> record.getItemByNo(k.getItemNo()).get())
				.map(item -> item.getValue())
				.collect(toList());
	}
}
