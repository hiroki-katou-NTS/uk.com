package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.annualleave;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalItem;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.IndependentCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.EmployeeCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.IntermediateResult;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;

/**
 * 年休付与残数データ 
 */
public class AnnualLeaveRemainingCanonicalization extends IndependentCanonicalization{
	
	@Override
	protected String getParentTableName() {
		return "KRCDT_HDPAID_REM";
	}

	@Override
	protected List<String> getChildTableNames() {
		return Collections.emptyList();
	}

	@Override
	protected List<DomainDataColumn> getDomainDataKeys() {
		return Arrays.asList(DomainDataColumn.SID, DomainDataColumn.YMD);
	}
	
	private final EmployeeCodeCanonicalization employeeCodeCanonicalization;
	
	public AnnualLeaveRemainingCanonicalization(DomainWorkspace workspace) {
		super(workspace);
		this.employeeCodeCanonicalization = new EmployeeCodeCanonicalization(workspace);
	}

	@Override
	protected IntermediateResult canonicalizeExtends(IntermediateResult targertResult) {
		return addFixedItems(targertResult);
	}

	/**
	 *  受入時に固定の値を入れる物たち
	 */
	private IntermediateResult addFixedItems(IntermediateResult interm) {
		return interm.addCanonicalized(CanonicalItem.of(100,IdentifierUtil.randomUniqueId()))
				  .addCanonicalized(CanonicalItem.of(102,GrantRemainRegisterType.MANUAL.value))
				  .addCanonicalized(CanonicalItem.of(103,0))
				  .addCanonicalized(CanonicalItem.of(104,0))
				  .addCanonicalized(CanonicalItem.of(105,0))
				  .addCanonicalized(CanonicalItem.of(106,0))
				  .addCanonicalized(CanonicalItem.of(107,0))
				  .addCanonicalized(CanonicalItem.of(108,0));
	}
	
	@Override
	protected List<Integer> getPrimaryKeyItemNos(DomainWorkspace workspace) {
		return Arrays.asList(101);//SID
	}
	
	@Override
	public ImportingDataMeta appendMeta(ImportingDataMeta source) {
		return employeeCodeCanonicalization.appendMeta(source);
	}
}
