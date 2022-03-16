package nts.uk.ctx.exio.dom.input.canonicalize.domains;

import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.IndependentCanonicalization;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WorkLocationCanonicalization extends IndependentCanonicalization {

	@Override
	public ItemNoMap getItemNoMap() {
		return ItemNoMap.reflection(Items.class);
	}

	public static class Items {
		public static final int 勤務場所コード = 1;
		public static final int 勤務場所名称 = 2;
		public static final int 緯度 = 101;
		public static final int 経度 = 102;
		public static final int 半径 = 103;
		public static final int 地域コード = 104;
		public static final int 契約コード = 9999;
	}

	@Override
	protected String getParentTableName() {
		return "KRCMT_WORK_LOCATION";
	}

	@Override
	protected List<String> getChildTableNames() {
		return Collections.emptyList();
	}

	@Override
	protected List<DomainDataColumn> getDomainDataKeys() {
		return Arrays.asList(
				new DomainDataColumn(Items.契約コード, "CONTRACT_CD", DataType.STRING),
				new DomainDataColumn(Items.勤務場所コード, "WK_LOCATION_CD", DataType.STRING));
	}
}
