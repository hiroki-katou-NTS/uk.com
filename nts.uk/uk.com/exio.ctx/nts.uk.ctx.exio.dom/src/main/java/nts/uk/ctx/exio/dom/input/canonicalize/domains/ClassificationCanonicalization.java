package nts.uk.ctx.exio.dom.input.canonicalize.domains;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.IndependentCanonicalization;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;

public class ClassificationCanonicalization extends IndependentCanonicalization{

	@Override
	public ItemNoMap getItemNoMap() {
		return ItemNoMap.reflection(Items.class);
	}

	public static class Items {
		public static final int 分類コード = 1;
		public static final int 分類名称 = 2;
		public static final int メモ = 3;
		public static final int 会社ID = 9998;
	}

    @Override
    protected String getParentTableName() {
        return "BSYMT_CLASSIFICATION";
    }

    @Override
    protected List<String> getChildTableNames() {
        return Collections.emptyList();
    }

    @Override
    protected List<DomainDataColumn> getDomainDataKeys() {
        return Arrays.asList(
				new DomainDataColumn(Items.会社ID, "CID", DataType.STRING),
				new DomainDataColumn(Items.分類コード, "CLSCD", DataType.STRING));
    }


}
