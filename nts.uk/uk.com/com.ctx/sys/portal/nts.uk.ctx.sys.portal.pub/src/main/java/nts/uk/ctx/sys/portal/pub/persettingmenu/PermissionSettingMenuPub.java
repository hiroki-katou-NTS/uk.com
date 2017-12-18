package nts.uk.ctx.sys.portal.pub.persettingmenu;

import java.util.List;


/**
 * the interface permission setting menu
 *
 */
public interface PermissionSettingMenuPub {
	
	List<PermissionSettingMenuExport> findByRoleType(int roleType);
}
