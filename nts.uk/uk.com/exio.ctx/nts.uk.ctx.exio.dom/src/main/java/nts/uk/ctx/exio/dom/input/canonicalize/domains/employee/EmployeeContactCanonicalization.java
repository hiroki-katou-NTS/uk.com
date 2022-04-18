package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.ItemNoMap;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.EmployeeIndependentCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.result.IntermediateResult;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;

public class EmployeeContactCanonicalization extends EmployeeIndependentCanonicalization{

	@Override
	public ItemNoMap getItemNoMap() {
		return ItemNoMap.reflection(Items.class);
	}

	public static class Items {
		public static final int 社員コード = 1;
		public static final int メールアドレス = 2;
		public static final int 携帯メールアドレス = 3;
		public static final int 携帯電話番号 = 4;
		public static final int 座席ダイヤルイン = 5;
		public static final int 座席内線番号 = 6;
		public static final int SID = 101;
	}

	@Override
	protected String getParentTableName() {
		return "BSYMT_CONTACT";
	}

	@Override
	protected List<String> getChildTableNames() {
		return Collections.emptyList();
	}

	@Override
	protected List<DomainDataColumn> getDomainDataKeys() {
		return Arrays.asList(new DomainDataColumn(Items.SID, "SID", DataType.STRING));
	}

	@Override
	protected Optional<IntermediateResult> canonicalizeExtends(
			DomainCanonicalization.RequireCanonicalize require,
			ExecutionContext context, IntermediateResult interm) {
		return Optional.of(interm);
	}
}
