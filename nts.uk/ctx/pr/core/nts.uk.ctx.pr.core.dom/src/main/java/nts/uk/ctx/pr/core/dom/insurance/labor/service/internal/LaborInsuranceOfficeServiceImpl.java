/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.labor.service.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.gul.text.StringUtil;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository;
import nts.uk.ctx.pr.core.dom.insurance.labor.service.LaborInsuranceOfficeService;

/**
 * The Class LaborInsuranceOfficeServiceImpl.
 */
@Stateless
public class LaborInsuranceOfficeServiceImpl implements LaborInsuranceOfficeService {

	/** The labor insurance office repo. */
	@Inject
	private LaborInsuranceOfficeRepository laborInsuranceOfficeRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.service.
	 * LaborInsuranceOfficeService#validateRequiredItem(nts.uk.ctx.pr.core.dom.
	 * insurance.labor.LaborInsuranceOffice)
	 */
	@Override
	public void validateRequiredItem(LaborInsuranceOffice office) {
		if (office.getCode() == null || StringUtil.isNullOrEmpty(office.getCode().v(), true) || office.getName() == null
				|| StringUtil.isNullOrEmpty(office.getName().v(), true) || office.getPicPosition() == null
				|| StringUtil.isNullOrEmpty(office.getPicPosition().v(), true)) {
			throw new BusinessException("ER001");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.service.
	 * LaborInsuranceOfficeService#checkDuplicateCode(nts.uk.ctx.pr.core.dom.
	 * insurance.labor.LaborInsuranceOffice)
	 */
	@Override
	public void checkDuplicateCode(LaborInsuranceOffice office) {
		if (laborInsuranceOfficeRepo.isDuplicateCode(office.getCode())) {
			throw new BusinessException("ER005");
		}
	}

}
