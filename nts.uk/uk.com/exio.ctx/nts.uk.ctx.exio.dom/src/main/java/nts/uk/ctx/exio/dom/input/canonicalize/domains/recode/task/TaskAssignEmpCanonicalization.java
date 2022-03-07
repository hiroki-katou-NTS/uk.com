package nts.uk.ctx.exio.dom.input.canonicalize.domains.recode.task;

import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.ItemNoMap;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.EmployeeIndependentCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.result.IntermediateResult;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 社員別作業の絞込の正準化
 */
public class TaskAssignEmpCanonicalization extends EmployeeIndependentCanonicalization {
	@Override
	public ItemNoMap getItemNoMap() {
		return ItemNoMap.reflection(Items.class);
	}

	public static class Items {
		public static final int 社員コード = 1;
		public static final int 作業枠NO = 2;
		public static final int 作業コード = 3;
		public static final int SID = 101;
	}

	@Override
	protected Optional<IntermediateResult> canonicalizeExtends(DomainCanonicalization.RequireCanonicalize require, ExecutionContext context, IntermediateResult interm) {
		// 個別の正準化はない
		return Optional.of(interm);
	}

	@Override
	protected String getParentTableName() {
		return "KSRMT_TASK_ASSIGN_SYA";
	}

	@Override
	protected List<String> getChildTableNames() {
		return Collections.emptyList();
	}

	@Override
	protected List<DomainDataColumn> getDomainDataKeys() {
		return Arrays.asList(
				new DomainDataColumn(Items.SID, "SID", DataType.STRING),
				new DomainDataColumn(Items.作業枠NO, "FRAME_NO", DataType.INT),
				new DomainDataColumn(Items.作業コード, "TASK_CD", DataType.STRING));
	}
}
