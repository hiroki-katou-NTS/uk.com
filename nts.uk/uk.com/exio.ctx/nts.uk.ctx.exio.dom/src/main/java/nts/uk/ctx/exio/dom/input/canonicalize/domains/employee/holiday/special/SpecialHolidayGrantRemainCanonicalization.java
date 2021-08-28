package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.special;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import lombok.val;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalItemList;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizeUtil;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.EmployeeIndependentCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.EmployeeCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;

public class SpecialHolidayGrantRemainCanonicalization extends EmployeeIndependentCanonicalization implements DomainCanonicalization{
	
	private final EmployeeCodeCanonicalization employeeCodeCanonicalization;
	
	public static SpecialHolidayGrantRemainCanonicalization create(DomainWorkspace workspace) {
		return new SpecialHolidayGrantRemainCanonicalization(workspace);
	}
	
	public SpecialHolidayGrantRemainCanonicalization(DomainWorkspace workspace) {
		super(workspace);
		employeeCodeCanonicalization = new EmployeeCodeCanonicalization(workspace);
	}
	
	@Override
	public void canonicalize(DomainCanonicalization.RequireCanonicalize require, ExecutionContext context) {
		CanonicalizeUtil.forEachEmployee(require, context, employeeCodeCanonicalization, interms -> {
			for (val interm : interms) {
				interm.addCanonicalized(new CanonicalItemList().add(101, UUID.randomUUID().toString()), 101);
				interm.addCanonicalized(new CanonicalItemList().add(103, 0), 103);
				interm.addCanonicalized(new CanonicalItemList().add(104, 0), 104);
				interm.addCanonicalized(new CanonicalItemList().add(105, 0), 105);
				
				require.save(context, interm.complete());
			}
		});
	}
	
	@Override
	protected String getParentTableName() {
		return "KRCDT_HD_SP_REMAIN";
	}
	
	@Override
	protected List<String> getChildTableNames() {
		return Collections.emptyList();
	}
	
	@Override
	protected List<DomainDataColumn> getDomainDataKeys() {
		return Arrays.asList(
				DomainDataColumn.SID, 
				new DomainDataColumn("SPECIAL_LEAVE_CD", DataType.STRING), 
				new DomainDataColumn("GRANT_DATE", DataType.DATETIME));
	}

}
