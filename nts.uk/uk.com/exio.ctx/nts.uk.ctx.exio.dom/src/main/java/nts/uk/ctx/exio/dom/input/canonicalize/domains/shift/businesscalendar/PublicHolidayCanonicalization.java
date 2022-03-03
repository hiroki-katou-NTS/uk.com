package nts.uk.ctx.exio.dom.input.canonicalize.domains.shift.businesscalendar;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.ItemNoMap;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.IndependentCanonicalization;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;

/**
 * 祝日の正準化処理 
 */
public class PublicHolidayCanonicalization extends  IndependentCanonicalization{

	@Override
	public ItemNoMap getItemNoMap() {
		return ItemNoMap.reflection(Items.class);
	}

	public static class Items {
		public static final int 年月日 = 1;
		public static final int 名称 = 2;
		public static final int CID = 9998;
		public static final int CONTRACT_CD = 9999;
	}

	@Override
	protected String getParentTableName() {
		return "KSCMT_PUBLIC_HOLIDAY";
	}

	@Override
	protected List<String> getChildTableNames() {
		return Collections.emptyList();
	}

	@Override
	protected List<DomainDataColumn> getDomainDataKeys() {
		return Arrays.asList(DomainDataColumn.CID,
								new DomainDataColumn(Items.年月日, "YMD_K", DataType.DATE))	;
	}
}
