/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.wagetable.inittable;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroup;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroupRepository;
import nts.uk.ctx.pr.core.ws.wagetable.inittable.dto.BaseDemensionOutModel;
import nts.uk.ctx.pr.core.ws.wagetable.inittable.dto.CertifyGroupDto;
import nts.uk.ctx.pr.core.ws.wagetable.inittable.dto.CertifyGroupOutModel;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class InitWtableWs.
 */
@Path("pr/proto/wagetable/sp")
@Produces(MediaType.APPLICATION_JSON)
public class InitWtableWs extends WebService {

	/** The find. */
	@Inject
	private CertifyGroupRepository certifyGroupRepo;

	/**
	 * Certify.
	 *
	 * @return the history model
	 */
	@POST
	@Path("certifies")
	public CertifyGroupOutModel certifies() {
		// Create model.
		CertifyGroupOutModel model = new CertifyGroupOutModel();

		// Get info login
		LoginUserContext loginUserContext = AppContexts.user();

		// Get all
		List<CertifyGroup> certifyGroups = this.certifyGroupRepo
				.findAll(loginUserContext.companyCode());

		// Mapping into dto.
		List<CertifyGroupDto> certifyGroupDtos = certifyGroups.stream().map(certifyGroup -> {
			CertifyGroupDto certifyGroupDto = new CertifyGroupDto();
			certifyGroupDto.fromDomain(certifyGroup);
			return certifyGroupDto;
		}).collect(Collectors.toList());

		model.setCertifyGroups(certifyGroupDtos);

		// Return
		return model;
	}

	/**
	 * Reference.
	 *
	 * @return the history model
	 */
	@POST
	@Path("reference")
	public BaseDemensionOutModel reference() {
		return null;
	}

}
