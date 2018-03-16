package nts.uk.ctx.at.record.app.find.workrecord.monthlyresult.employee;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfIrgNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employee.ShainLaborDeforSetTemporarySetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

@Getter
public class EmployeeLaborDeforSetTemporaryDto implements ShainLaborDeforSetTemporarySetMemento{
	
	/** The company id. */
	/** 会社ID. */
	private CompanyId companyId;

	/** The employee id. */
	/** 社員ID. */
	private EmployeeId employeeId;

	/** The legal aggr set of irg new. */
	/** 変形労働時間勤務の法定内集計設定. */
	private LegalAggrSetOfIrgNew legalAggrSetOfIrgNew;
	
	@Override
	public void setCompanyId(CompanyId companyId) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setEmployeeId(EmployeeId employeeId) {
	  this.employeeId = employeeId;	
	}

	@Override
	public void setLegalAggrSetOfIrgNew(LegalAggrSetOfIrgNew legalAggrSetOfIrgNew) {
		this.legalAggrSetOfIrgNew = legalAggrSetOfIrgNew;	
	}

}
