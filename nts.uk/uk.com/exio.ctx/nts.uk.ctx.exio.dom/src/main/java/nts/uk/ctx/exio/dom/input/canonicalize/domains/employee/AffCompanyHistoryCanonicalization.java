package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalItemList;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.EmployeeHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.history.HistoryType;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.IntermediateResult;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;

/**
 * 入社退職履歴の正準化
 */
public class AffCompanyHistoryCanonicalization extends EmployeeHistoryCanonicalization{
	
	@Override
	protected String getParentTableName() {
		return "BSYMT_AFF_COM_HIST";
	}
	
	@Override
	protected List<DomainDataColumn> getDomainDataKeys() {
		return Arrays.asList(new DomainDataColumn("HIST_ID", DataType.STRING));
	}
	
	@Override
	protected List<String> getChildTableNames() {
		return Arrays.asList("BSYMT_AFF_COM_HIST_ITEM");
	}
	
	public static AffCompanyHistoryCanonicalization create(DomainWorkspace workspace) {
		return new AffCompanyHistoryCanonicalization(workspace, HistoryType.UNDUPLICATABLE);
	}
	
	private AffCompanyHistoryCanonicalization(DomainWorkspace workspace, HistoryType historyType) {
		super(workspace, historyType);
	}
	
	@Override
	protected List<Container> canonicalizeExtends(
			DomainCanonicalization.RequireCanonicalize require,
			ExecutionContext context,
			String employeeId,
			List<Container> targetContainers) {

		List<Container> results = new ArrayList<>();
		
		val employee = require.getEmployeeDataMngInfoByEmployeeId(employeeId).get();
		String personId = employee.getPersonId();
		
		for (val container : targetContainers) {
			
			IntermediateResult interm = container.getInterm();
			
			interm = interm.addCanonicalized(new CanonicalItemList()
					.add(103, personId) // 個人ID
					.add(104, 0) // 出向先データである
					.add(105, "                                    ") // 採用区分コード
					);
			
			results.add(new Container(interm, container.getAddingHistoryItem()));
		}
		
		return results;
	}
	
	public static interface RequireCanonicalizeExtends {
		Optional<EmployeeDataMngInfo> getEmployeeDataMngInfoByEmployeeId(String employeeId);
	}
}
