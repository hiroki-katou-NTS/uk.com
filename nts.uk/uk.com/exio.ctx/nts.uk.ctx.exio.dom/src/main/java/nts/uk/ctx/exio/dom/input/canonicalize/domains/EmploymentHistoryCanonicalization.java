package nts.uk.ctx.exio.dom.input.canonicalize.domains;

import java.util.Arrays;
import java.util.List;

import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.EmployeeHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.history.HistoryType;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;

/**
 * 雇用履歴の正準化 
 */
public class EmploymentHistoryCanonicalization {
	protected static EmployeeHistoryCanonicalization create(DomainWorkspace w) {
		return new EmployeeHistoryCanonicalization(w, HistoryType.PERSISTENERESIDENT) {
			
			@Override
			protected String getParentTableName() {
				return "BSYMT_AFF_EMP_HIST";
			}
			
			@Override
			protected List<DomainDataColumn> getDomainDataKeys() {
				return Arrays.asList(new DomainDataColumn("HIST_ID", DataType.STRING));
			}
			
			@Override
			protected List<String> getChildTableNames() {
				return Arrays.asList("BSYMT_AFF_EMP_HIST_ITEM");
			}
		};
	}
}
