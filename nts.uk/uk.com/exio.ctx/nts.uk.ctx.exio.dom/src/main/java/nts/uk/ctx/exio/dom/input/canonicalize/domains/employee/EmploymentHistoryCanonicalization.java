package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee;

import java.util.Arrays;
import java.util.List;

import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.ItemNoMap;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.EmployeeHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.history.HistoryType;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;

/**
 * 雇用履歴の正準化 
 */
public class EmploymentHistoryCanonicalization extends EmployeeHistoryCanonicalization{

	public EmploymentHistoryCanonicalization() {
		super(HistoryType.PERSISTENERESIDENT);
	}

	@Override
	protected ItemNoMap getItemNoMapExtends() {
		return new ItemNoMap();
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
}
