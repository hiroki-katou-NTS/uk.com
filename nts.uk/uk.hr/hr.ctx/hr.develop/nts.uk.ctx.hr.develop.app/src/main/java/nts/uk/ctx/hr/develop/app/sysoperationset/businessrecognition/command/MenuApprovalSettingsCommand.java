package nts.uk.ctx.hr.develop.app.sysoperationset.businessrecognition.command;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.develop.app.sysoperationset.businessrecognition.dto.MenuApprovalSettingsInforDto;
import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.MenuApprovalSettingsRepository;

@Stateless
public class MenuApprovalSettingsCommand {

	@Inject
	private MenuApprovalSettingsRepository repo;
	
	public void update(List<MenuApprovalSettingsInforDto> dto) {
		repo.update(dto.stream().map(c->c.toDomain()).collect(Collectors.toList()));
	}
}
