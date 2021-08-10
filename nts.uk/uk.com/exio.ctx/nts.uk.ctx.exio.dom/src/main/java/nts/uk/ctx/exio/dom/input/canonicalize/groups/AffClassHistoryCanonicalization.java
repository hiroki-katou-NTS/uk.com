import static nts.uk.ctx.exio.dom.input.workspace.datatype.DataType.STRING;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.groups.generic.EmployeeContinuousHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.workspace.group.GroupWorkspace;

/**
 *分類履歴グループの正準化用定義 
 */
public class AffClassHistoryCanonicalization {
	protected static EmployeeContinuousHistoryCanonicalization create(GroupWorkspace w) {
		return new EmployeeContinuousHistoryCanonicalization(w) {
			
			@Override
			protected String getParentTableName() {
				return "BSYMT_AFF_CLASS_HIST";
			}
			@Override
			protected List<String> getChildTableNames() {
				return Collections.emptyList();
			}
			
			@Override
			protected List<DomainDataColumn> getDomainDataKeys() {
				return Arrays.asList(
						new DomainDataColumn("PID", STRING),
						DomainDataColumn.SID,
						new DomainDataColumn("HIST_ID", STRING));
			}
		};
	}
}
