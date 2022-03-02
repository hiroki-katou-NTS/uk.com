package nts.uk.ctx.exio.dom.input.canonicalize.domains;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.IndependentCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.DatePeriodCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.result.IntermediateResult;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportError;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;

public class EquipmentCanonicalization extends IndependentCanonicalization{

	@Override
	public ItemNoMap getItemNoMap() {
		return ItemNoMap.reflection(Items.class);
	}

	public static class Items {
		public static final int 設備コード = 1;
		public static final int 設備名称 = 2;
		public static final int 開始日 = 3;
		public static final int 終了日 = 4;
		public static final int 設備分類コード = 5;
		public static final int 備考 = 6;
	}

	@Override
	protected String getParentTableName() {
		return "OFIDT_EQUIPMENT";
	}

	@Override
	protected List<String> getChildTableNames() {
		return Collections.emptyList();
	}

	@Override
	protected List<DomainDataColumn> getDomainDataKeys() {
		return Arrays.asList(DomainDataColumn.CID,
								new DomainDataColumn("CODE", DataType.STRING));
	}

	@Override
	protected Optional<IntermediateResult> canonicalizeExtends(DomainCanonicalization.RequireCanonicalize require, 
			ExecutionContext context, 
			IntermediateResult targetResult) {
		if(checkRevisedDate(require, context, targetResult)) {
			return Optional.empty();
		}
		return Optional.of(targetResult);
	}
	
	private boolean checkRevisedDate(DomainCanonicalization.RequireCanonicalize require,
			ExecutionContext context, IntermediateResult targetResult) {
    	val result = new DatePeriodCanonicalization(Items.開始日,Items.終了日)
					.getPeriod(targetResult);
		if(result.isLeft()) {
			require.add(ExternalImportError.record(targetResult.getRowNo(), context.getDomainId(), result.getLeft().getText()));
			return true;
		}
		return false;
	}
}
