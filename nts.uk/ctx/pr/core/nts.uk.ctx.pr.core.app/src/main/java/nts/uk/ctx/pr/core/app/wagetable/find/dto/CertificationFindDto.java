package nts.uk.ctx.pr.core.app.wagetable.find.dto;

import lombok.Data;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.CertificationSetMemento;

@Data
public class CertificationFindDto implements CertificationSetMemento {
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

}
