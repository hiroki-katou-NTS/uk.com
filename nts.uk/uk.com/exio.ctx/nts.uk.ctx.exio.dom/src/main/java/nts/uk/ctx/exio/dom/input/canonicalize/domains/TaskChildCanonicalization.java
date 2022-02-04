package nts.uk.ctx.exio.dom.input.canonicalize.domains;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
		public static final int 作業枠No = 1;
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
		return Arrays.asList(DomainDataColumn.CID, 
				new DomainDataColumn("作業枠No", DataType.INT),
				new DomainDataColumn("作業コード", DataType.STRING),
				new DomainDataColumn("子作業コード", DataType.STRING));
	}
	
	@Override
	protected IntermediateResult canonicalizeExtends(
			DomainCanonicalization.RequireCanonicalize require, 
			ExecutionContext context, 
			IntermediateResult targetResult) {
		
		checkFrameNoOver4(require, context, targetResult);
		return targetResult;
	}

	private void checkFrameNoOver4(
			DomainCanonicalization.RequireCanonicalize require,
			ExecutionContext context, IntermediateResult targetResult) {
		if(targetResult.getItemByNo(Items.作業枠No).get().getInt().intValue() > 4) {
			require.add(ExternalImportError.record(targetResult.getRowNo(), context.getDomainId(), "作業枠No４以下のデータしか受入れられません。"));
		}
	}
}
