package nts.uk.ctx.exio.dom.input.canonicalize.domains.recode.task;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.ItemNoMap;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.IndependentCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.WorkplaceCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.result.CanonicalItem;
import nts.uk.ctx.exio.dom.input.canonicalize.result.IntermediateResult;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportError;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;
import java.util.*;

/**
 * 職場別作業の絞込の正準化
 */
public class TaskAssignWkpCanonicalization extends IndependentCanonicalization {

	private final WorkplaceCodeCanonicalization workplaceCodeCanonicalization;

	public TaskAssignWkpCanonicalization() {
		workplaceCodeCanonicalization = new WorkplaceCodeCanonicalization(Items.基準日, Items.職場コード, Items.職場ID);
	}

	@Override
	public ItemNoMap getItemNoMap() {
		return ItemNoMap.reflection(Items.class);
	}

	public static class Items {
		public static final int 職場コード = 1;
		public static final int 作業枠NO = 2;
		public static final int 作業コード = 3;
		public static final int 職場ID = 101;
		public static final int 基準日= 102;
	}

	@Override
	protected Optional<IntermediateResult> canonicalizeExtends(DomainCanonicalization.RequireCanonicalize require, ExecutionContext context, IntermediateResult interm) {
		// 職場コードの正準化
		interm =  interm.optionalItem(CanonicalItem.of(Items.基準日, GeneralDate.today()));
		val either = workplaceCodeCanonicalization.canonicalize(require, interm, interm.getRowNo());
		if (either.isLeft()) {
			require.add(ExternalImportError.of(context.getDomainId(), either.getLeft()));
			return Optional.empty();
		}
		return Optional.of(either.getRight());
	}

	@Override
	protected String getParentTableName() {
		return "KSRMT_TASK_ASSIGN_WKP";
	}

	@Override
	protected List<String> getChildTableNames() {
		return Collections.emptyList();
	}

	@Override
	protected List<DomainDataColumn> getDomainDataKeys() {
		return Arrays.asList(
				new DomainDataColumn(Items.作業枠NO, "FRAME_NO", DataType.INT),
				new DomainDataColumn(Items.作業コード, "TASK_CD", DataType.STRING),
				new DomainDataColumn(Items.職場ID, "WKP_ID", DataType.STRING));
	}
}
