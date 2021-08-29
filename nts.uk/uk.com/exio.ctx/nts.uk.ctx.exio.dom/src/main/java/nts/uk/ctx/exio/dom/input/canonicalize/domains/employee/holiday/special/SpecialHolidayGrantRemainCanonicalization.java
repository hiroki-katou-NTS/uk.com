package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.special;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalItem;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.EmployeeIndependentCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.EmployeeCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportError;
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
		
		List<String> employeeCodes = require.getStringsOfRevisedData(context, getItemNoOfEmployeeId());
		
		for (String employeeCode : employeeCodes) {
			
			employeeCodeCanonicalization.canonicalize(require, context, employeeCode)
				.ifLeft(errors -> {
					errors.forEach(error -> require.add(context, ExternalImportError.of(error)));
				})
				.ifRight(interms -> {
					interms.forEach(interm -> {
						interm.addCanonicalized(CanonicalItem.of(101, IdentifierUtil.randomUniqueId().toString()));
						interm.addCanonicalized(CanonicalItem.of(103, 0));
						interm.addCanonicalized(CanonicalItem.of(104, 0));
						interm.addCanonicalized(CanonicalItem.of(105, 0));
						
						require.save(context, interm.complete());
					});
				});
		}
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
