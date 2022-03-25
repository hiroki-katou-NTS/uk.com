package nts.uk.ctx.exio.dom.input.canonicalize.domains;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.IndependentCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.result.IntermediateResult;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportError;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;
import nts.uk.shr.com.net.Ipv4Address;

public class WorkLocationIpCanonicalization extends IndependentCanonicalization{

	@Override
	public ItemNoMap getItemNoMap() {
		return ItemNoMap.reflection(Items.class);
	}

	public static class Items {
		public static final int 勤務場所コード = 1;
		public static final int NET1 = 2;
		public static final int NET2 = 3;
		public static final int HOST1 = 4;
		public static final int HOST2 = 5;
	}

    @Override
    protected String getParentTableName() {
        return "KRCMT_IP4ADDRESS";
    }

    @Override
    protected List<String> getChildTableNames() {
        return Collections.emptyList();
    }

    @Override
    protected List<DomainDataColumn> getDomainDataKeys() {
        return Arrays.asList(
										new DomainDataColumn(Items.勤務場所コード, "WK_LOCATION_CD", DataType.STRING),
                						new DomainDataColumn(Items.NET1, "NET1", DataType.INT),
                						new DomainDataColumn(Items.NET2, "NET2", DataType.INT),
                						new DomainDataColumn(Items.HOST1, "HOST1", DataType.INT),
                						new DomainDataColumn(Items.HOST2, "HOST2", DataType.INT),
                						new DomainDataColumn(9999, "CONTRACT_CD", DataType.STRING));
    }

    @Override
    protected Optional<IntermediateResult> canonicalizeExtends(
    		DomainCanonicalization.RequireCanonicalize require, ExecutionContext context, IntermediateResult targetResult) {

    	return checkIpAddress(require, context, targetResult);

    }

    /**
     * 受け入れようとしているIPアドレスが適切かチェックする
     * @param require
     * @param context
     * @param targetResult
     * @return
     */
    private Optional<IntermediateResult> checkIpAddress(DomainCanonicalization.RequireCanonicalize require, ExecutionContext context, IntermediateResult targetResult) {

		String IpAddress = getIpAddress(targetResult);

    	try {
    		Ipv4Address.parse(IpAddress);
    		return Optional.of(targetResult);
    	}
    	catch (RuntimeException e) {
    		require.add(ExternalImportError.record(targetResult.getRowNo(), context.getDomainId(), e.getMessage()));
    		return Optional.empty();
    	}
    }
	/**
	 * 受け入れた項目からIPアドレスを作る
	 * @param targetResult
	 * @return
	 */
	private String getIpAddress(IntermediateResult targetResult) {
		val net1 = targetResult.getItemByNo(Items.NET1).get().getJavaInt().toString();
		val net2 = targetResult.getItemByNo(Items.NET2).get().getJavaInt().toString();
		val host1 = targetResult.getItemByNo(Items.HOST1).get().getJavaInt().toString();
		val host2 = targetResult.getItemByNo(Items.HOST2).get().getJavaInt().toString();

		return Arrays.asList(net1,net2,host1, host2).stream().collect(Collectors.joining("."));
	}


}
