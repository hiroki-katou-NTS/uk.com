package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.special;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

public class SpecialHolidayGrantSettingCanonicalization extends EmployeeIndependentCanonicalization implements DomainCanonicalization{
	
	private final EmployeeCodeCanonicalization employeeCodeCanonicalization;
	
	public static SpecialHolidayGrantSettingCanonicalization create(DomainWorkspace workspace) {
		return new SpecialHolidayGrantSettingCanonicalization(workspace);
	}
	
	public SpecialHolidayGrantSettingCanonicalization(DomainWorkspace workspace) {
		super(workspace);
		employeeCodeCanonicalization = new EmployeeCodeCanonicalization(workspace);
	}
	
	@Override
	public void canonicalize(DomainCanonicalization.RequireCanonicalize require, ExecutionContext context) {
		CanonicalizeUtil.forEachEmployee(require, context, employeeCodeCanonicalization, interms -> {
			for (val interm : interms) {
				interm.addCanonicalized(new CanonicalItemList().add(102, 0), 102);
				interm.addCanonicalized(new CanonicalItemList().addNull(103), 103);
				interm.addCanonicalized(new CanonicalItemList().addNull(104), 104);
				interm.addCanonicalized(new CanonicalItemList().addNull(105), 105);
				
				require.save(context, interm.complete());
			}
		});
	}
	
	@Override
	protected String getParentTableName() {
		return "KRCMT_HDSP_BASIC";
	}
	
	@Override
	protected List<String> getChildTableNames() {
		return Collections.emptyList();
	}
	
	@Override
	protected List<DomainDataColumn> getDomainDataKeys() {
		return Arrays.asList(
				DomainDataColumn.SID, 
				new DomainDataColumn("SPECIAL_LEAVE_CD", DataType.STRING));
	}
}
