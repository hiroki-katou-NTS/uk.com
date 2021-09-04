package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee;

import java.util.Arrays;
import java.util.List;

import lombok.val;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalItemList;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizeUtil;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.EmployeeHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.history.HistoryType;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;
import nts.uk.shr.com.context.AppContexts;

/**
 * 雇用履歴の正準化 
 */
public class EmploymentHistoryCanonicalization extends EmployeeHistoryCanonicalization{

	public EmploymentHistoryCanonicalization(DomainWorkspace workspace) {
		super(workspace, HistoryType.PERSISTENERESIDENT);
	}
	
	@Override
	protected String getParentTableName() {
		return "BSYMT_AFF_EMP_HIST";
	}

	@Override
	protected List<String> getChildTableNames() {
		return Arrays.asList("BSYMT_AFF_EMP_HIST_ITEM");
	}

	@Override
	protected List<DomainDataColumn> getDomainDataKeys() {
		return Arrays.asList(new DomainDataColumn("HIST_ID", DataType.STRING));
	}
	
	@Override
	public void canonicalize(DomainCanonicalization.RequireCanonicalize require, ExecutionContext context) {
		CanonicalizeUtil.forEachEmployee(require, context, this.getEmployeeCodeCanonicalization(), interm -> {
			
			val results = super.canonicalizeHistory(require, context, interm);
			
			results.forEach(result -> {
				result.addCanonicalized(getFixedItems());
				require.save(context, result.complete());
			});
		});
	}
	
	public static CanonicalItemList getFixedItems() {
		return new CanonicalItemList()
			.add(100, AppContexts.user().companyId()) // CID
			.addNull(103); //給与区分
	}
}
