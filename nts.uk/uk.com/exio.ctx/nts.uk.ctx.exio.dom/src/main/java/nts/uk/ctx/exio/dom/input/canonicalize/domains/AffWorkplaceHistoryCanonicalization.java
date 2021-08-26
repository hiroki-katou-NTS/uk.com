package nts.uk.ctx.exio.dom.input.canonicalize.domains;

import java.util.Arrays;
import java.util.List;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizeUtil;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.EmployeeHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.WorkplaceCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToDelete;
import nts.uk.ctx.exio.dom.input.canonicalize.history.HistoryType;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.EmployeeCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportError;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;

/**
 * 所属職場履歴の正準化
 */
public class AffWorkplaceHistoryCanonicalization extends EmployeeHistoryCanonicalization implements  DomainCanonicalization {

	private final EmployeeCodeCanonicalization employeeCodeCanonicalization;
	private final WorkplaceCodeCanonicalization workplaceCodeCanonicalization;
		
	public AffWorkplaceHistoryCanonicalization(DomainWorkspace workspace) {
		super(workspace,HistoryType.PERSISTENERESIDENT);
		workplaceCodeCanonicalization = new WorkplaceCodeCanonicalization(workspace);
		employeeCodeCanonicalization = new EmployeeCodeCanonicalization(workspace);
	}
	
	public static DomainCanonicalization create(DomainWorkspace workspace) {
		return new AffWorkplaceHistoryCanonicalization(workspace);
	}

	@Override
	public void canonicalize(DomainCanonicalization.RequireCanonicalize require, ExecutionContext context) {
		CanonicalizeUtil.forEachEmployee(require, context, employeeCodeCanonicalization, interm -> {
			
			val results = canonicalizeHistory(require, context, interm);
			
			results.forEach(result -> {
				workplaceCodeCanonicalization.canonicalize(require, result, result.getRowNo())
						.ifRight(canonicalized -> require.save(context, canonicalized.complete()))
						.ifLeft(error -> require.add(context, ExternalImportError.of(error)));
			});
		});
	}

	@Override
	protected String getParentTableName() {
		return "BSYMT_AFF_WKP_HIST";
	}
	@Override
	protected List<String> getChildTableNames() {
		return Arrays.asList("BSYMT_AFF_WKP_HIST_ITEM");
	}
	
	@Override
	protected List<DomainDataColumn> getDomainDataKeys() {
		return Arrays.asList(
				new DomainDataColumn("社員コード", DataType.STRING),
				new DomainDataColumn("開始日", DataType.DATE),
				new DomainDataColumn("終了日", DataType.DATE)
		);
	}

	@Override
	public AtomTask adjust(
			RequireAdjsut require,
			List<AnyRecordToChange> recordsToChange,
			List<AnyRecordToDelete> recordsToDelete) {
		return super.adjust(require, recordsToChange, recordsToDelete);
	}
	
	@Override
	public ImportingDataMeta appendMeta(ImportingDataMeta source) {
		return super.appendMeta(source)
				.addItem("WORKPLACE_ID");
	}

	public interface RequireCanonicalize extends WorkplaceCodeCanonicalization.Require{
	}
	public interface RequireAdjust extends EmployeeHistoryCanonicalization.RequireAdjust{
	}


}
