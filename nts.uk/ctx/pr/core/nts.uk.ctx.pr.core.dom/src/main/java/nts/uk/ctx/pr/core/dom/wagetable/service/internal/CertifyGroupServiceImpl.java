/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.service.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroup;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroupRepository;
import nts.uk.ctx.pr.core.dom.wagetable.service.CertifyGroupService;

/**
 * The Class CertifyGroupServiceImpl.
 */
@Stateless
public class CertifyGroupServiceImpl implements CertifyGroupService {

	/** The certify group repository. */
	@Inject
	private CertifyGroupRepository certifyGroupRepository;

}
