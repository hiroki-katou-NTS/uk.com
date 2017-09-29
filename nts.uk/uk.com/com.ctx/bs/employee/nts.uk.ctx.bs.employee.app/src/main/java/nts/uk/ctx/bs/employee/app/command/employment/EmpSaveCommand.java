/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.employment;

import lombok.Data;
import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.employment.EmpExternalCode;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentCode;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentGetMemento;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentName;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.primitive.Memo;

/**
 * Instantiates a new emp save command.
 */
@Data
public class EmpSaveCommand implements EmploymentGetMemento {
	
	
	/** The code. */
	private String employmentCode;
	
	/** The name. */
	private String employmentName;
	
	/** The emp external code. */
	private String empExternalCode;
	
	/** The memo. */
	private String memo;
	
	/** The mode. */
	private int mode;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.employment.EmploymentGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(AppContexts.user().companyId());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.employment.EmploymentGetMemento#getEmploymentCode()
	 */
	@Override
	public EmploymentCode getEmploymentCode() {
		return new EmploymentCode(this.employmentCode);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.employment.EmploymentGetMemento#getEmploymentName()
	 */
	@Override
	public EmploymentName getEmploymentName() {
		return new EmploymentName(this.employmentName);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.employment.EmploymentGetMemento#getEmpExternalcode()
	 */
	@Override
	public EmpExternalCode getEmpExternalcode() {
		return new EmpExternalCode(this.empExternalCode);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.employment.EmploymentGetMemento#getMemo()
	 */
	@Override
	public Memo getMemo() {
		return new Memo(this.memo);
	}

}
