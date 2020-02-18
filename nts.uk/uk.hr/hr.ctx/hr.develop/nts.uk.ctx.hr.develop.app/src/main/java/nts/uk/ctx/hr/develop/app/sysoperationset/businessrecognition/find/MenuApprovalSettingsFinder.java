package nts.uk.ctx.hr.develop.app.sysoperationset.businessrecognition.find;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.develop.app.sysoperationset.businessrecognition.dto.MenuApprovalSettingsInforDto;
import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.MenuApprovalSettingsRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class MenuApprovalSettingsFinder {

	@Inject
	private MenuApprovalSettingsRepository repo;
	
	public List<MenuApprovalSettingsInforDto> get() {
		String cid = AppContexts.user().companyId();
		return repo.getBusinessApprovalSettings(cid).stream().map(c -> new MenuApprovalSettingsInforDto(c)).collect(Collectors.toList());
	}
}
