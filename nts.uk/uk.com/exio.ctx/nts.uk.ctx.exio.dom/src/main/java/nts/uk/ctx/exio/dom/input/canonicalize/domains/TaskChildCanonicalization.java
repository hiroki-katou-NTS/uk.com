package nts.uk.ctx.exio.dom.input.canonicalize.domains;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.IndependentCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.result.IntermediateResult;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportError;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;


public class TaskChildCanonicalization extends IndependentCanonicalization{

	@Override
	public ItemNoMap getItemNoMap() {
		return ItemNoMap.reflection(Items.class);
	}

	public static class Items {
		public static final int 作業枠NO = 1;
		public static final int 作業コード = 2;
		public static final int 子作業コード = 3;
	}


	@Override
	protected String getParentTableName() {
		return "KSRMT_TASK_CHILD";
	}

	@Override
	protected List<String> getChildTableNames() {
		return Collections.emptyList();
	}

	@Override
	protected List<DomainDataColumn> getDomainDataKeys() {
		return Arrays.asList(
				DomainDataColumn.CID,
				new DomainDataColumn("FRAME_NO", DataType.INT),
				new DomainDataColumn("CD", DataType.STRING),
				new DomainDataColumn("CHILD_CD", DataType.STRING));
	}
	
	@Override
	protected Optional<IntermediateResult> canonicalizeExtends(
			DomainCanonicalization.RequireCanonicalize require, 
			ExecutionContext context, 
			IntermediateResult targetResult) {
		
		if(checkFrameNoOver4(require, context, targetResult)) {;
			return Optional.empty();
		}
		return Optional.of(targetResult);
	}

	private boolean checkFrameNoOver4(
			DomainCanonicalization.RequireCanonicalize require,
			ExecutionContext context, IntermediateResult targetResult) {
		if(targetResult.getItemByNo(Items.作業枠NO).get().getInt().intValue() > 4) {
			require.add(ExternalImportError.record(targetResult.getRowNo(), context.getDomainId(), "作業枠No5に対して下位作業は設定できません。"));
			return true;
		}
		return false;
	}
}
