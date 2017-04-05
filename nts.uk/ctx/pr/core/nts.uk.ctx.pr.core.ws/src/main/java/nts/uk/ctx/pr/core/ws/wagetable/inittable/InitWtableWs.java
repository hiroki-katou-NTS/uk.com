/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.wagetable.inittable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pr.core.dom.wagetable.certification.Certification;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertificationRepository;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroup;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroupCode;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroupGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroupName;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroupRepository;
import nts.uk.ctx.pr.core.dom.wagetable.certification.MultipleTargetSetting;
import nts.uk.ctx.pr.core.ws.wagetable.inittable.dto.BaseDemensionOutModel;
import nts.uk.ctx.pr.core.ws.wagetable.inittable.dto.CertifyGroupDto;
import nts.uk.ctx.pr.core.ws.wagetable.inittable.dto.CertifyGroupOutModel;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class InitWtableWs.
 */
@Path("pr/proto/wagetable/sp")
@Produces(MediaType.APPLICATION_JSON)
public class InitWtableWs extends WebService {

	/** The find. */
	@Inject
	private CertifyGroupRepository certifyGroupRepo;

	/** The certification repo. */
	@Inject
	private CertificationRepository certificationRepo;

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

		// Get the company login
		String companyCode = AppContexts.user().companyCode();

		// Get all
		List<CertifyGroup> certifyGroups = this.certifyGroupRepo.findAll(companyCode);

		List<Certification> certifyNoneGroupItems = certificationRepo
				.findAllNoneOfGroup(companyCode);

		// Check exist none group item.
		if (!CollectionUtil.isEmpty(certifyNoneGroupItems)) {
			// Add group of none group items.
			certifyGroups.add(new CertifyGroup(new CertifyGroupGetMemento() {

				@Override
				public CertifyGroupName getName() {
					return new CertifyGroupName("グループ なし");
				}

				@Override
				public MultipleTargetSetting getMultiApplySet() {
					return MultipleTargetSetting.TotalMethod;
				}

				@Override
				public String getCompanyCode() {
					return companyCode;
				}

				@Override
				public CertifyGroupCode getCode() {
					return new CertifyGroupCode("000");
				}

				@Override
				public Set<Certification> getCertifies() {
					return certifyNoneGroupItems.stream().collect(Collectors.toSet());
				}
			}));
		}

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
