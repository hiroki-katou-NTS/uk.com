package nts.uk.ctx.pereg.app.find.layoutdef;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.auth.app.find.person.role.GetWhetherLoginerCharge;
import nts.uk.ctx.sys.auth.app.find.person.role.RoleWhetherLoginDto;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class CheckWhetherDisplayLicense {
	
	@Inject
	private GetWhetherLoginerCharge getWhether;
	public boolean checkDislay(){
		
		String sysAdminID = AppContexts.user().roles().forSystemAdmin();
		String groupAdminID = AppContexts.user().roles().forGroupCompaniesAdmin();
		String companyAdminID = AppContexts.user().roles().forCompanyAdmin();
			//システム管理者、グループ会社管理者、会社管理者のいずれかに該当する（ロールIDが設定されている）場合
			//(trong t/h thỏa mãn 1 trong: người quản lý system, người quản lý company group, người quản lý company)	
		if(!sysAdminID.isEmpty() || !groupAdminID.isEmpty() || !companyAdminID.isEmpty()){
			return true;
		}else{
			RoleWhetherLoginDto checkLogin = getWhether.getWhetherLoginerCharge();
			if(!checkLogin.checkRole() == false){
				return false;
			}
		}
		return true;
	}

}
