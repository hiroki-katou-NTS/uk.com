package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.employee;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfIrgNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employee.ShainLaborDeforSetTemporaryGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

@Getter
@Setter
public class DeleteEmployeeLaborDeforSetTemporaryCommand  implements ShainLaborDeforSetTemporaryGetMemento{
	
	/** The company id. */
	/* 会社ID. */
	private CompanyId companyId;

	/** The employee id. */
	/* 社員ID. */
	private EmployeeId employeeId;

	/** The legal aggr set of irg new. */
	/* 変形労働時間勤務の法定内集計設定. */
	private LegalAggrSetOfIrgNew legalAggrSetOfIrgNew;
	
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
