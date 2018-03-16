package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.employment;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfIrgNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employee.ShainLaborDeforSetTemporaryGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

@Getter
@Setter
public class SaveEmploymentLaborDeforSetTemporaryCommand implements ShainLaborDeforSetTemporaryGetMemento{
	
	/** The company id. */
	/* 会社ID. */
	private CompanyId companyId;

	/** The employment code. */
	/* 雇用コード. */
	private EmploymentCode employmentCode;

	/** The legal aggr set of irg new. */
	/* 集計設定. */
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
