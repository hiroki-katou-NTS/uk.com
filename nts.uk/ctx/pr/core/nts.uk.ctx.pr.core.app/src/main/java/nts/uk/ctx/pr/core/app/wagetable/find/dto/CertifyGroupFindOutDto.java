/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.find.dto;

import java.util.Set;

import lombok.Data;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.Certification;
import nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupSetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.MultipleTargetSetting;

@Data
public class CertifyGroupFindOutDto implements CertifyGroupSetMemento {

	/** The code. */
	private String code;

	/** The name. */
	private String name;

	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCode(String code) {
		this.code = code;

	}

	@Override
	public void setName(String name) {
		this.name = name;

	}

	@Override
	public void setMultiApplySet(MultipleTargetSetting multiApplySet) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCertifies(Set<Certification> certifies) {
		// TODO Auto-generated method stub

	}

}
