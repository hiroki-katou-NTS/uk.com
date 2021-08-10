
import static nts.uk.ctx.exio.dom.input.workspace.datatype.DataType.STRING;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.groups.generic.EmployeeContinuousHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.workspace.group.GroupWorkspace;

/**
 * 所属職位履歴グループの正準化用定義 
 */
public class AffJobTitleHistoryCanonicalization {
	
	protected static EmployeeContinuousHistoryCanonicalization create(GroupWorkspace w) {
		return new EmployeeContinuousHistoryCanonicalization(w) {
			
			@Override
			protected String getParentTableName() {
				return "BSYMT_AFF_JOB_HIST";
			}
			@Override
			protected List<String> getChildTableNames() {
				return Collections.emptyList();
			}
			
			@Override
			protected List<DomainDataColumn> getDomainDataKeys() {
				return Arrays.asList(
						new DomainDataColumn("HIST_ID", STRING));
			}
		};
	}
}
