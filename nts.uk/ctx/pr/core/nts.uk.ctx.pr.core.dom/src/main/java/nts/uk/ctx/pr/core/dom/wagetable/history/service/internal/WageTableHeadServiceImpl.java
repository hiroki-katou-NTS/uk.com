/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.history.service.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.gul.text.StringUtil;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableHead;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadRepository;
import nts.uk.ctx.pr.core.dom.wagetable.history.service.WageTableHeadService;

/**
 * The Class WageTableHeadServiceImpl.
 */
@Stateless
public class WageTableHeadServiceImpl extends WageTableHeadService {

	/** The wage table head repo. */
	@Inject
	private WageTableHeadRepository wageTableHeadRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.history.service.WageTableHeadService#
	 * validateRequiredItem(nts.uk.ctx.pr.core.dom.wagetable.WageTableHead)
	 */
	@Override
	public void validateRequiredItem(WageTableHead head) {
		// TODO: recode
		if (head.getCode() == null || StringUtil.isNullOrEmpty(head.getCode().v(), true)) {
			throw new BusinessException("ER001");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.history.service.WageTableHeadService#
	 * checkDuplicateCode(nts.uk.ctx.pr.core.dom.wagetable.WageTableHead)
	 */
	@Override
	public void checkDuplicateCode(WageTableHead head) {
		if (wageTableHeadRepo.isExistCode(head.getCompanyCode().v(), head.getCode().v())) {
			throw new BusinessException("ER005");
		}
	}

}
