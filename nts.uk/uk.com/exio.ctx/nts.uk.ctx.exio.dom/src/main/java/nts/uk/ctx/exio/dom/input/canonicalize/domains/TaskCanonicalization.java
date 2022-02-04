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
import nts.uk.ctx.exio.dom.input.canonicalize.result.CanonicalItem;
import nts.uk.ctx.exio.dom.input.canonicalize.result.IntermediateResult;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportError;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;

/**
 * 作業の正準化
 */
public class TaskCanonicalization extends IndependentCanonicalization{

    @Override
    public ItemNoMap getItemNoMap() {
        return ItemNoMap.reflection(Items.class);
    }
    
    public static class Items {
    	public static final int 作業枠NO = 1;
        public static final int 作業コード = 2;
        public static final int 作業名称 = 3;
        public static final int 作業略名 = 4;
        public static final int 開始日 = 5;
        public static final int 終了日 = 6;
        public static final int 外部コード1 = 7;
        public static final int 外部コード2 = 8;
        public static final int 外部コード3 = 9;
        public static final int 外部コード4 = 10;
        public static final int 外部コード5 = 11;
        public static final int カラーコード = 12;
        public static final int 備考 = 13;
    }

    @Override
    protected String getParentTableName() {
        return "KSRMT_TASK_MASTER";
    }

    @Override
    protected List<String> getChildTableNames() {
        return Collections.emptyList();
    }

    @Override
    protected List<DomainDataColumn> getDomainDataKeys() {
        return Arrays.asList(new DomainDataColumn("FRAME_NO", DataType.INT),
                						new DomainDataColumn("CD", DataType.STRING),
                                         DomainDataColumn.CID);
    }

    @Override
    protected Optional<IntermediateResult> canonicalizeExtends(DomainCanonicalization.RequireCanonicalize require, ExecutionContext context, IntermediateResult targetResult) {
    	if(checkReversedDate(require, context, targetResult)) {
    		return Optional.empty();
    	}
        return Optional.of(replaceColorCode(targetResult));
    }

    /**
     * 開始日・終了日が逆転してないかチェック
     */
    private boolean checkReversedDate(DomainCanonicalization.RequireCanonicalize require, ExecutionContext context,  IntermediateResult targetResult) {
    	val result = new DatePeriodCanonicalization(Items.開始日,Items.終了日)
    				.getPeriod(targetResult);
    	if(result.isLeft()) {
    		require.add(ExternalImportError.record(targetResult.getRowNo(), context.getDomainId(), result.getLeft().getText()));
    		return true;
    	}
    	return false;
	}

	private IntermediateResult replaceColorCode(IntermediateResult targetResult) {
		
        val frameNo = targetResult.getItemByNo(Items.作業枠NO).get().getInt().intValue();
        
        if(frameNo == 1) {
        	return targetResult.optionalItem(new CanonicalItem(Items.カラーコード, "#000000"));
        }
        //枠No1以外はカラーコードは空
        return targetResult.addCanonicalized(new CanonicalItem(Items.カラーコード, null));
    }
}
