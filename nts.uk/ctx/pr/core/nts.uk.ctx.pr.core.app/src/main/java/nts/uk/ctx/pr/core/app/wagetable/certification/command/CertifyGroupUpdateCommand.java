/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.certification.command;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.wagetable.command.dto.CertificationDto;
import nts.uk.ctx.pr.core.app.wagetable.command.dto.CertifyGroupDto;
import nts.uk.ctx.pr.core.dom.wagetable.certification.Certification;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertificationGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroup;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroupGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.certification.MultipleTargetSetting;

/**
 * The Class CertifyGroupAddCommand.
 */
@Setter
@Getter
public class CertifyGroupUpdateCommand implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The certify group dto. */
	private CertifyGroupDto certifyGroupDto;

	/**
	 * To domain.
	 *
	 * @return the certify group
	 */
	public CertifyGroup toDomain(String companyCode) {
		return certifyGroupDto.toDomain(companyCode);
	}

}
