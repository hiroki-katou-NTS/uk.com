package nts.uk.ctx.exio.dom.exo.menu;

import java.util.ArrayList;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.adapter.sys.auth.RoleAtrImport;
import nts.uk.ctx.exio.dom.exo.adapter.sys.auth.RoleExportRepoAdapter;
import nts.uk.ctx.exio.dom.exo.adapter.sys.auth.RoleImport;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.loginuser.role.LoginUserRoles;

@Stateless
public class MenuService {

	@Inject
	private RoleExportRepoAdapter roleExportRepoAdapter;

	/**
	 * 外部出力ロール権限取得
	 */
	public RoleAuthority getRoleAuthority() {
		RoleAuthority roleAuth = new RoleAuthority(new ArrayList<>(), new ArrayList<>());

		// 実行時情報.権限情報を取得
		LoginUserRoles permission = AppContexts.user().roles();

		// 権限情報.就業のロールID
		this.checkRole(roleAuth, permission.forAttendance());

		// 権限情報.人事のロールID
		this.checkRole(roleAuth, permission.forPersonnel());

		// 権限情報.給与のロールID
		this.checkRole(roleAuth, permission.forPayroll());

		// 権限情報.オフィスヘルパのロールID
		this.checkRole(roleAuth, permission.forOfficeHelper());

		return roleAuth;
	}

	private void checkRole(RoleAuthority roleAuth, String roleId) {
		if (roleId == null || roleId.isEmpty())
			return;
		// imported(補助機能)「ロール」を取得する Get "role"
		Optional<RoleImport> roleOtp = roleExportRepoAdapter.findByRoleId(roleId);
		if (roleOtp.isPresent()) {
			roleAuth.getEmpRole().add(roleOtp.get().roleId);
			if (RoleAtrImport.INCHARGE.equals(roleOtp.get().getAssignAtr())) {
				roleAuth.getInChargeRole().add(roleOtp.get().roleId);
			}
		}
	}
}
