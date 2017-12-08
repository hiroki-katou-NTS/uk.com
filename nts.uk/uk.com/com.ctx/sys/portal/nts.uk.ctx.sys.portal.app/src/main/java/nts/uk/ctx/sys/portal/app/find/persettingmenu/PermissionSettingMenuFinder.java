package nts.uk.ctx.sys.portal.app.find.persettingmenu;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.sys.portal.dom.permissionmenu.PermissionSettingMenu;
import nts.uk.ctx.sys.portal.dom.permissionmenu.PermissionSettingMenuRepository;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenu;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenuRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class PermissionSettingMenuFinder {
	@Inject
	private PermissionSettingMenuRepository perSettingMenuRepo;
	@Inject
	private StandardMenuRepository standardMenuRepo;

	public List<PermissionSettingMenuDto> findByRoleType(int roleType) {
		List<PermissionSettingMenuDto> result = new ArrayList<PermissionSettingMenuDto>();
		List<PermissionSettingMenu> perMenues = perSettingMenuRepo.findbyRoleType(roleType);
		PermissionSettingMenuDto dto = new PermissionSettingMenuDto();
		if (perMenues != null && !perMenues.isEmpty()) {
			perMenues.forEach(m -> {
				StandardMenu standardMenu = standardMenuRepo.getStandardMenubyCode(
						AppContexts.user().companyId(), m.getMenuCode().toString(), m.getSystem().value,
						m.getClassification().value).get();
				
				dto.setCode(standardMenu.getCode());
				dto.setDisplayName(standardMenu.getDisplayName());
				dto.setProgramId(standardMenu.getProgramId()+"");
				dto.setScreenId(standardMenu.getScreenId()+"");
				dto.setQueryString(standardMenu.getUrl());
				result.add(dto);
			});
		}
		return result;
	}

}
