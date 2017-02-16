/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.Builder;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.Certification;
import nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupSetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.MultipleTargetSetting;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;

/**
 * The Class CertifyGroupDto.
 */
@Builder
public class CertifyGroupDto implements CertifyGroupSetMemento {

	/** The wage table code. */
	private String wageTableCode;

	/** The history id. */
	private String historyId;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupSetMemento#setWageTableCode(
	 * nts.uk.ctx.pr.core.dom.wagetable.WageTableCode)
	 */
	@Override
	public void setWageTableCode(WageTableCode wageTableCode) {
		// TODO Auto-generated method stub
		this.wageTableCode = wageTableCode.v();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupSetMemento#setHistoryId(java
	 * .lang.String)
	 */
	@Override
	public void setHistoryId(String historyId) {
		// TODO Auto-generated method stub
		this.historyId = historyId;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupSetMemento#setCode(java.lang
	 * .String)
	 */
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		this.certifies = new ArrayList<>();
		for (Certification certification : certifies) {
			CertificationDto certificationDto = new CertificationDto();
			certification.saveToMemento(certificationDto);
			this.certifies.add(certificationDto);
		}
	}

}
