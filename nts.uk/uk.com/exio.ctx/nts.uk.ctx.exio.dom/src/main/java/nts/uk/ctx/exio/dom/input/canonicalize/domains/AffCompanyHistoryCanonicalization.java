package nts.uk.ctx.exio.dom.input.canonicalize.domains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalItemList;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.EmployeeHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.history.HistoryType;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;
import nts.uk.shr.com.context.AppContexts;

/**
 * 所属会社履歴の正準化
 */
public class AffCompanyHistoryCanonicalization extends EmployeeHistoryCanonicalization{
	
	@Override
	protected String getParentTableName() {
		return "BSYMT_AFF_COM_HIST";
	}
	
	@Override
	protected List<DomainDataColumn> getDomainDataKeys() {
		return Arrays.asList(new DomainDataColumn("HIST_ID", DataType.STRING));
	}
	
	@Override
	protected List<String> getChildTableNames() {
		return Arrays.asList("BSYMT_AFF_COM_HIST_ITEM");
	}
	
	public static AffCompanyHistoryCanonicalization create(DomainWorkspace workspace) {
		return new AffCompanyHistoryCanonicalization(workspace, HistoryType.UNDUPLICATABLE);
	}
	
	private AffCompanyHistoryCanonicalization(DomainWorkspace workspace, HistoryType historyType) {
		super(workspace, historyType);
	}
	
	@Override
	protected List<Container> canonicalizeExtends(
			DomainCanonicalization.RequireCanonicalize require,
			ExecutionContext context,
			List<Container> targetContainers) {

		List<Container> results = new ArrayList<>();
		
		
		
		return results;
	}
	
	public static CanonicalItemList getFixedItems() {
		return new CanonicalItemList()
			.add(100, AppContexts.user().companyId()) // CID
			.add(104, 0) //出向先データである
			.add(105, "                                    ") //採用区分コード
			.addNull(106) //本採用年月日
			.addNull(107); //退職金計算開始日
	}
}
