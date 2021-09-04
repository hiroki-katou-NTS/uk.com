package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee;

import static nts.uk.ctx.exio.dom.input.workspace.datatype.DataType.STRING;

import java.util.Arrays;
import java.util.List;

import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.EmployeeHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.history.HistoryType;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;

/**
 *分類履歴グループの正準化用定義 
 */
public class AffClassHistoryCanonicalization extends EmployeeHistoryCanonicalization{
	
	@Override
	protected String getParentTableName() {
		return "BSYMT_AFF_CLASS_HIST";
	}

	@Override
	protected List<String> getChildTableNames() {
		return Arrays.asList("BSYMT_AFF_CLASS_HIST_ITEM");
	}

	@Override
	protected List<DomainDataColumn> getDomainDataKeys() {
		return Arrays.asList(
				new DomainDataColumn("HIST_ID", STRING));
	}

	public AffClassHistoryCanonicalization(DomainWorkspace workspace) {
		super(workspace, HistoryType.PERSISTENERESIDENT);
	}
}
