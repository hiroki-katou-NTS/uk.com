package nts.uk.ctx.exio.dom.input.canonicalize.domains;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.IndependentCanonicalization;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;

public class EquipmentClassiicationCanonicalization extends IndependentCanonicalization{

	@Override
	public ItemNoMap getItemNoMap() {
		return ItemNoMap.reflection(Items.class);
	}

	public static class Items {
		public static final int 設備分類コード = 1;
		public static final int 設備分類名称 = 2;
	}
	
	@Override
	protected String getParentTableName() {
		return "OFIDT_EQUIPMENT_CLS";
	}

	@Override
	protected List<String> getChildTableNames() {
		return Collections.emptyList();
	}

	@Override
	protected List<DomainDataColumn> getDomainDataKeys() {
		return Arrays.asList(new DomainDataColumn("CONTRACT_CD", DataType.STRING),
										 new DomainDataColumn("CODE", DataType.STRING)
				);
	}
}
