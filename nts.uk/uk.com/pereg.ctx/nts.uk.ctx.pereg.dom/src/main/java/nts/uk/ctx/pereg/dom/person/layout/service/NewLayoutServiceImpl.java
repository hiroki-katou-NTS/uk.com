package nts.uk.ctx.pereg.dom.person.layout.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.shr.com.context.AppContexts;


@Stateless
public class NewLayoutServiceImpl implements NewLayOutService {
	
	@Inject
	private GetWhetherLoginerCharge a;				

	
	
	
	@Override
	public boolean checkDisplay() {
		String sysAdminRoleID = AppContexts.user().roles().forSystemAdmin();
		String groupComAdminID = AppContexts.user().roles().forGroupCompaniesAdmin();
		String companyAdminID = AppContexts.user().roles().forCompanyAdmin();
		// システム管理者、グループ会社管理者、会社管理者のいずれかに該当する（ロールIDが設定されている）場合
		// (trong t/h thỏa mãn 1 trong: người quản lý system, người quản lý
		// company group, người quản lý company)
		if (!sysAdminRoleID.isEmpty() || !groupComAdminID.isEmpty() || !companyAdminID.isEmpty()) {
			return true;
		} else {

		}
		return false;
	}

}
