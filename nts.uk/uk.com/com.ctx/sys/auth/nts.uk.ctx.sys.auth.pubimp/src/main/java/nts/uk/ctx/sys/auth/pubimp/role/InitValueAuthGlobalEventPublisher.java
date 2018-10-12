package nts.uk.ctx.sys.auth.pubimp.role;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.role.RoleRepository;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.ctx.sys.auth.pub.event.RoleByRoleTiesGlobalEvent;
import nts.uk.shr.com.context.AppContexts;

//Event：権限管理の初期値登録 - Tuy la event nhưng thực tế đang viết dạng publish
@Stateless
public class InitValueAuthGlobalEventPublisher {

	@Inject
	private RoleRepository roleRepo;

	public void initRole(String companyIDCopy) {
		// ドメインモデル「ロール」を取得する
		// (Lấy domain 「role」)
		// Role Type = companyManager
		List<Role> listRole = roleRepo.findByType(companyIDCopy, RoleType.COMPANY_MANAGER.value);
		if (listRole.isEmpty()) {
			// 会社管理者ロールを0会社からコピーして新規登録する
			// (copy Role của companyManager từ company0, rồi đăng ký mới)
			String zeroCompany = AppContexts.user().zeroCompanyIdInContract();
			List<Role> listRoleZeroCompany = this.roleRepo.findByType(zeroCompany, RoleType.COMPANY_MANAGER.value);
			List<Role> listRoleCopy = new ArrayList<>();
			List<String> listRoleTiesID = new ArrayList<>();

			for (Role role : listRoleZeroCompany) {
				String roleID = IdentifierUtil.randomUniqueId();
				Role initRole = new Role(roleID, role.getRoleCode(), role.getRoleType(),
						role.getEmployeeReferenceRange(), role.getName(), role.getContractCode(), role.getAssignAtr(),
						role.getCompanyId());
				listRoleCopy.add(initRole);
				listRoleTiesID.add(roleID);
				this.roleRepo.insert(initRole);
				RoleByRoleTiesGlobalEvent roleEvent = new RoleByRoleTiesGlobalEvent(roleID, role.getCompanyId());
				roleEvent.toBePublished();
			}
		}
	}
	
	
}
