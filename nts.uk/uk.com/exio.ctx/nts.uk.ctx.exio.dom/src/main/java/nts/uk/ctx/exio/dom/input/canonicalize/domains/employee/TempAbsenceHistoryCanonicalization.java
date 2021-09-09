package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.val;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalItemList;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.EmployeeHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.history.HistoryType;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;

/**
 * 休職休業履歴の正準化
 */
public class TempAbsenceHistoryCanonicalization extends EmployeeHistoryCanonicalization  {
	
	public TempAbsenceHistoryCanonicalization(DomainWorkspace workspace) {
		super(workspace, HistoryType.UNDUPLICATABLE);
	}
	
	@Override
	protected List<Container> canonicalizeExtends(
			DomainCanonicalization.RequireCanonicalize require,
			ExecutionContext context,
			String employeeId,
			List<Container> targetContainers) {
		
		List<Container> canonicalizeds = new ArrayList<>();
		
		for (val container : targetContainers) {
			canonicalizeds.add(new Container(
					container.getInterm().addCanonicalized(getFixedItems()), 
					container.getAddingHistoryItem()));
		}
		
		return canonicalizeds;
	}
	
	private static CanonicalItemList getFixedItems() {
		return new CanonicalItemList()
			//　履歴ID
			.add(102, IdentifierUtil.randomUniqueId().toString());
	}
	
	@Override
	protected String getParentTableName() {
		return "BSYMT_TEMP_ABS_HIST";
	}
	
	@Override
	protected List<String> getChildTableNames() {
		return Arrays.asList("BSYMT_TEMP_ABS_HIST_ITEM");
	}
	
	@Override
	protected List<DomainDataColumn> getDomainDataKeys() {
		return Arrays.asList(
				DomainDataColumn.SID,
				DomainDataColumn.HIST_ID
		);
	}
	
	@Override
	public ImportingDataMeta appendMeta(ImportingDataMeta source) {
		return super.appendMeta(source);
	}
}
