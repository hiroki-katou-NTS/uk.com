package nts.uk.ctx.exio.dom.input.canonicalize.groups;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.groups.generic.EmployeeContinuousHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;
import nts.uk.ctx.exio.dom.input.workspace.group.GroupWorkspace;

public class EmploymentHistoryCanonicalization {
	protected static EmployeeContinuousHistoryCanonicalization create(GroupWorkspace w) {
		return new EmployeeContinuousHistoryCanonicalization(w) {
			
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
				return Collections.emptyList();
			}
		};
	}
}
