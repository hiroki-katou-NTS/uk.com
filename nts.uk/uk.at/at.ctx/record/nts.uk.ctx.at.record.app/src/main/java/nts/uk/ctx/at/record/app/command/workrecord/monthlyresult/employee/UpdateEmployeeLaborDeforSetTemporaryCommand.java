package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.employee;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfIrgNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employee.ShainLaborDeforSetTemporaryGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

@Getter
@Setter
public class UpdateEmployeeLaborDeforSetTemporaryCommand implements ShainLaborDeforSetTemporaryGetMemento{
	
	

	@Override
	public CompanyId getCompanyId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmployeeId getEmployeeId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LegalAggrSetOfIrgNew getLegalAggrSetOfIrgNew() {
		// TODO Auto-generated method stub
		return null;
	}

}
