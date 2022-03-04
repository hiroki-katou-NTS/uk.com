package nts.uk.ctx.exio.dom.input.canonicalize.domains;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.IndependentCanonicalization;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;

public class EmploymentCanonicalization extends IndependentCanonicalization{

	@Override
	public ItemNoMap getItemNoMap() {
		return ItemNoMap.reflection(Items.class);
	}

	public static class Items {
		public static final int 雇用コード = 1;
		public static final int 雇用名称 = 2;
		public static final int 雇用外部コード = 3;
		public static final int メモ = 4;
		public static final int グループ会社共通マスタID = 5;
		public static final int グループ会社共通マスタ項目ID = 6;
		public static final int 会社ID = 9998;
	}

	@Override
	protected String getParentTableName() {
		return "BSYMT_EMPLOYMENT";
	}

	@Override
	protected List<String> getChildTableNames() {
		return Collections.emptyList();
	}

	@Override
	protected List<DomainDataColumn> getDomainDataKeys() {
		return Arrays.asList(DomainDataColumn.CID,
				new DomainDataColumn(Items.雇用コード, "CODE", DataType.STRING));
	}
}
