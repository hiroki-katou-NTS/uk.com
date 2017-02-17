/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.Data;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.Certification;
import nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupSetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.MultipleTargetSetting;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;

/**
 * The Class CertifyGroupDto.
 */
@Data
public class CertifyGroupDto implements CertifyGroupSetMemento {

	/** The code. */
	private String code;

	/** The name. */
	private String name;

	/** The multi apply set. */
	private Integer multiApplySet;

	/** The certifies. */
	private List<CertificationDto> certifies;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupSetMemento#setCompanyCode(
	 * nts.uk.ctx.core.dom.company.CompanyCode)
	 */
	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCode(String code) {
		// TODO Auto-generated method stub
		this.code = code;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupSetMemento#setName(java.lang
	 * .String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupSetMemento#setMultiApplySet(
	 * nts.uk.ctx.pr.core.dom.wagetable.MultipleTargetSetting)
	 */
	@Override
	public void setMultiApplySet(MultipleTargetSetting multiApplySet) {
		this.multiApplySet = multiApplySet.value;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupSetMemento#setCertifies(java
	 * .util.Set)
	 */
	@Override
	public void setCertifies(Set<Certification> certifies) {
		this.certifies = new ArrayList<>();
		for (Certification certification : certifies) {
			CertificationDto certificationDto = new CertificationDto();
			certification.saveToMemento(certificationDto);
			this.certifies.add(certificationDto);
		}
	}

}
