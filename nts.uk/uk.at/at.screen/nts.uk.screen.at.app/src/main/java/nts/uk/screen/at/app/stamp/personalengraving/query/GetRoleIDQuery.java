/**
 * 
 */
package nts.uk.screen.at.app.stamp.personalengraving.query;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.auth.dom.adapter.role.RoleAdaptor;
import nts.uk.ctx.at.auth.dom.adapter.role.RoleInformationImport;
import nts.uk.ctx.at.auth.dom.employmentrole.RoleType;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeRecordImport;
import nts.uk.ctx.sys.auth.pub.user.UserExport;
import nts.uk.ctx.sys.auth.pub.user.UserPublisher;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 * 打刻結果を表示するためにロールIDを取得する
 */
@Stateless
public class GetRoleIDQuery {
	
	@Inject
	private EmpEmployeeAdapter sysCompanyAdapter;
	
	@Inject
	private UserPublisher userPublisher; 
	
	@Inject
	private RoleAdaptor roleAdaptor;

	/**
	 * 【input】
	 *  ・会社ID  @param cid
	 *  ・社員ID  @param sid
	 *  【output】
	 *   ・就業ロールID
	 */
	public String getRoleId(String cid, String sid) {
		
		// 1 Imported(就業)「社員」を取得する
		EmployeeRecordImport empInfor = sysCompanyAdapter.findByAllInforEmpId(sid);
		if (empInfor == null) {
			return null;
		}
		
		// 2 Imported(就業)「ユーザ」を取得する
		Optional<UserExport> useExport = userPublisher.getUserByAssociateId(empInfor.getPId());
		if (!useExport.isPresent()) {
			return null;
		}
		String userId = useExport.get().getUserID();
		
		// 3 ユーザIDからロールを区分を含めて取得する
		RoleInformationImport roleInfo =  roleAdaptor.getRoleIncludCategoryFromUserID(userId, RoleType.EMPLOYMENT.value, GeneralDate.today(), AppContexts.user().companyId());
		if (roleInfo == null) {
			return null;
		}
		return roleInfo.getRoleId();
	}
}
