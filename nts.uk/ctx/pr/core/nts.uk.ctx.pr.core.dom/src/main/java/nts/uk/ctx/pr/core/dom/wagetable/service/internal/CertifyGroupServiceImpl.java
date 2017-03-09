/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.service.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.gul.text.StringUtil;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertificationReponsitory;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroup;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroupCode;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroupRepository;
import nts.uk.ctx.pr.core.dom.wagetable.certification.service.CertifyGroupService;

/**
 * The Class CertifyGroupServiceImpl.
 */
@Stateless
public class CertifyGroupServiceImpl implements CertifyGroupService {

	/** The certify group repository. */
	@Inject
	private CertifyGroupRepository certifyGroupRepository;

	/** The certification reponsitory. */
	@Inject
	private CertificationReponsitory certificationReponsitory;

	@Override
	public void validateRequiredItem(CertifyGroup certifyGroup) {
		if (certifyGroup.getCode() == null || StringUtil.isNullOrEmpty(certifyGroup.getCode().v(), true)
				|| certifyGroup.getName() == null || StringUtil.isNullOrEmpty(certifyGroup.getName().v(), true)) {
			throw new BusinessException("ER001");
		}

	}

	@Override
	public void checkDuplicateCode(CertifyGroup certifyGroup) {
		if (certifyGroupRepository.isDuplicateCode(certifyGroup.getCompanyCode(), certifyGroup.getCode())) {
			throw new BusinessException("ER005");
		}

	}

	@Override
	public void checkDulicateCertification(CertifyGroup certifyGroup, CertifyGroupCode certifyGroupCode) {
		if (certificationReponsitory.isDulicateCertification(certifyGroup.getCompanyCode(), certifyGroup.getCertifies(), certifyGroupCode)) {
			throw new BusinessException("ER005");
		}

	}

}
