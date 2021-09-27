package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.val;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.EmployeeHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.history.HistoryType;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.IntermediateResult;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.JobTitleCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportError;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;

/**
 * 所属職位履歴グループの正準化用定義 
 */
public class AffJobTitleHistoryCanonicalization extends EmployeeHistoryCanonicalization implements  DomainCanonicalization {
	private final JobTitleCodeCanonicalization jobTitleCodeCanonicalization;

	public AffJobTitleHistoryCanonicalization(DomainWorkspace workspace) {
		super(workspace, HistoryType.PERSISTENERESIDENT);
		jobTitleCodeCanonicalization = new JobTitleCodeCanonicalization(workspace);
	}

	@Override
	protected List<Container> canonicalizeExtends(
			DomainCanonicalization.RequireCanonicalize require,
			ExecutionContext context,
			String employeeId,
			List<Container> targetContainers) {

		List<Container> results = new ArrayList<>();
		for (val container : targetContainers) {
			IntermediateResult interm = container.getInterm();
			
			jobTitleCodeCanonicalization.canonicalize(require, interm, interm.getRowNo())
					.ifRight(canonicalized -> results.add(new Container(canonicalized, container.getAddingHistoryItem())))
					.ifLeft(error -> require.add(ExternalImportError.of(error)));
		}
		return results;
	}
			

	@Override
	protected String getParentTableName() {
		return "BSYMT_AFF_JOB_HIST";
	}
	@Override
	protected List<String> getChildTableNames() {
		return Arrays.asList("BSYMT_AFF_JOB_HIST_ITEM");
	}
	
	@Override
	protected List<DomainDataColumn> getDomainDataKeys() {
		// EmployeeHistoryCanonicalization.toDeleteのキーの順番と合わせる必要がある
		return Arrays.asList(
				new DomainDataColumn("HIST_ID", DataType.STRING),
				new DomainDataColumn("SID", DataType.STRING)
		);
	}

	@Override
	public ImportingDataMeta appendMeta(ImportingDataMeta source) {
		return super.appendMeta(source)
				.addItem("JOB_TITLE_ID");
	}

	public interface RequireCanonicalize extends JobTitleCodeCanonicalization.Require{
	}
	public interface RequireAdjust extends EmployeeHistoryCanonicalization.RequireAdjust{
	}
}
