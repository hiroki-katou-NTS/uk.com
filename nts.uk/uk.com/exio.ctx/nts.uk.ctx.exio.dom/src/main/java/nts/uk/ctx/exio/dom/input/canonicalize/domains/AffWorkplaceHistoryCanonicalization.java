package nts.uk.ctx.exio.dom.input.canonicalize.domains;

import static nts.uk.ctx.exio.dom.input.workspace.datatype.DataType.*;

import java.util.Arrays;
import java.util.List;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.EmployeeContinuousHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToDelete;
import nts.uk.ctx.exio.dom.input.canonicalize.history.ExternalImportPersistentResidentHistory;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;

/**
 * 所属職場履歴の正準化
 */
public class AffWorkplaceHistoryCanonicalization extends EmployeeContinuousHistoryCanonicalization implements  DomainCanonicalization {
//	/** 職場コードの項目No */
//	private final int itemNoWorkplaceCode;
//	/** 通常職場コードの項目No */
//	private final int itemNoNormalWorkplaceCode;
//	/** 職場IDの項目No */
//	private final int itemNoWorkplaceId;
//	/** 通常職場IDの項目No */
//	private final int itemNoNormalWorkplaceId;
		
	public AffWorkplaceHistoryCanonicalization(DomainWorkspace workspace) {
		super(workspace);
//		itemNoWorkplaceCode = workspace.getItemByName("職場コード").getItemNo();
//		itemNoNormalWorkplaceCode = workspace.getItemByName("通常職場").getItemNo();
//		itemNoWorkplaceId = workspace.getItemByName("WORKPLACE_ID").getItemNo();
//		itemNoNormalWorkplaceId = workspace.getItemByName("NORMAL_WORKPLACE_ID").getItemNo();
	}
	
	public static DomainCanonicalization create(DomainWorkspace workspace) {
		return new AffWorkplaceHistoryCanonicalization(workspace);
	}

	@Override
	public void canonicalize(DomainCanonicalization.RequireCanonicalize require, ExecutionContext context) {
		super.canonicalize(require, context);
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
	protected Class<?> getHistoryClass() {
		return ExternalImportPersistentResidentHistory.class;
	}
	
	@Override
	protected List<DomainDataColumn> getDomainDataKeys() {
		return Arrays.asList(
				new DomainDataColumn("HIST_ID", STRING)
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
		return super.appendMeta(source);
	}

	public interface RequireCanonicalize extends EmployeeContinuousHistoryCanonicalization.RequireCanonicalize{
	}
	public interface RequireAdjust extends EmployeeContinuousHistoryCanonicalization.RequireAdjust{
	}
}
