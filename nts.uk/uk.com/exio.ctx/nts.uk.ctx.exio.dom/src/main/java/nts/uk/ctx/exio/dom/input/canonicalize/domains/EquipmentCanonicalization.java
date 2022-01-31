package nts.uk.ctx.exio.dom.input.canonicalize.domains;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.IndependentCanonicalization;
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
									new DomainDataColumn("設備コード", DataType.STRING));
	}

}
