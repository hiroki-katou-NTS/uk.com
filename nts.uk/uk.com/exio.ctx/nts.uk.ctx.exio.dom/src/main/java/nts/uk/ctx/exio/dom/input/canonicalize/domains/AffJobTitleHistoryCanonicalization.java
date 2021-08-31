package nts.uk.ctx.exio.dom.input.canonicalize.domains;

import java.util.Arrays;
import java.util.List;

import lombok.val;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizeUtil;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.EmployeeHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.history.HistoryType;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.EmployeeCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.JobTitleCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportError;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;

/**
 * 所属職位履歴グループの正準化用定義 
 */
public class AffJobTitleHistoryCanonicalization extends EmployeeHistoryCanonicalization implements  DomainCanonicalization {
	private final EmployeeCodeCanonicalization employeeCodeCanonicalization;
	private final JobTitleCodeCanonicalization jobTitleCodeCanonicalization;

	public AffJobTitleHistoryCanonicalization(DomainWorkspace workspace) {
		super(workspace, HistoryType.PERSISTENERESIDENT);
		jobTitleCodeCanonicalization = new JobTitleCodeCanonicalization(workspace);
		employeeCodeCanonicalization = new EmployeeCodeCanonicalization(workspace);
	}
	
	protected static AffJobTitleHistoryCanonicalization create(DomainWorkspace w) {
		return new AffJobTitleHistoryCanonicalization(w);
	}	

	@Override
	public void canonicalize(DomainCanonicalization.RequireCanonicalize require, ExecutionContext context) {
		CanonicalizeUtil.forEachEmployee(require, context, employeeCodeCanonicalization, interm -> {
			
			val results = canonicalizeHistory(require, context, interm);
			
			results.forEach(result -> {
				jobTitleCodeCanonicalization.canonicalize(require, result, result.getRowNo())
						.ifRight(canonicalized -> require.save(context, canonicalized.complete()))
						.ifLeft(error -> require.add(context, ExternalImportError.of(error)));
			});
		});
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

//	@Override
//	public AtomTask adjust(
//			RequireAdjsut require,
//			List<AnyRecordToChange> recordsToChange,
//			List<AnyRecordToDelete> recordsToDelete) {
//		return super.adjust(require, recordsToChange, recordsToDelete);
//	}
	
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
