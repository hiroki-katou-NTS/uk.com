package nts.uk.screen.at.app.query.kdp.kdp003.f;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.role.RoleRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 打刻入力で社員のロールを取得する
 * UKDesign.UniversalK.就業.KDP_打刻.KDP003_打刻入力(氏名選択).F:打刻ログイン.メニューOCD.打刻入力で社員のロールを取得する.打刻入力で社員のロールを取得する
 * @author chungnt
 *
 */

@Stateless
public class EmployeeRoleStamping {

	@Inject
	private RoleRepository roleRepo;
	
	public RoleEmployeeStampingDto getRoleEmployee() {
		
		RoleEmployeeStampingDto result = new RoleEmployeeStampingDto();
		
		Optional<Role> optRole = this.roleRepo.findByRoleId(AppContexts.user().roles().forPersonalInfo());
		
		if (optRole.isPresent()) {
			Role role = optRole.get();
			result.setRoleId(role.getRoleId());
			result.setCompanyId(role.getCompanyId());
			result.setContractCode(role.getContractCode().v());
			result.setEmployeeReferenceRange(role.getEmployeeReferenceRange().value);
			result.setName(role.getName().v());
			result.setRoleCode(role.getRoleCode().v());
			result.setRoleType(role.getRoleType().value);
		}
		
		return result;
	}
}
