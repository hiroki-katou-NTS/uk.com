package nts.uk.ctx.pereg.app.find.licence;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.employee.employeelicense.EmployeeLicense;
import nts.uk.ctx.bs.employee.dom.employee.employeelicense.EmployeeLicenseRepository;
import nts.uk.ctx.sys.auth.app.find.person.role.GetWhetherLoginerCharge;
import nts.uk.ctx.sys.auth.app.find.person.role.RoleWhetherLoginDto;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class LicenseCheckFinder {

	@Inject
	private GetWhetherLoginerCharge getWhether;

	@Inject
	private EmployeeLicenseRepository employeeLicenseRepo;

	public LicensenCheckDto checkLicense() {

		String contractCD = AppContexts.user().contractCode();

		Optional<EmployeeLicense> employeeLicense = employeeLicenseRepo.findByKey(contractCD);

		if (!employeeLicense.isPresent()) {
			throw new RuntimeException("Can't find Employee License with contracCD" + contractCD);
		}
		EmployeeLicense license = employeeLicense.get();

		return new LicensenCheckDto(this.checkDislay(), license.getMaxNumberLicenses().v(),
				license.getWarningNumberLicenses().v(), license.getLicenseKey().v());
	}

	private boolean checkDislay() {

		String sysAdminID = AppContexts.user().roles().forSystemAdmin();
		String groupAdminID = AppContexts.user().roles().forGroupCompaniesAdmin();
		String companyAdminID = AppContexts.user().roles().forCompanyAdmin();
		// システム管理者、グループ会社管理者、会社管理者のいずれかに該当する（ロールIDが設定されている）場合
		// (trong t/h thỏa mãn 1 trong: người quản lý system, người quản lý
		// company group, người quản lý company)
		if (!sysAdminID.isEmpty() || !groupAdminID.isEmpty() || !companyAdminID.isEmpty()) {
			return true;
		} else {
			RoleWhetherLoginDto checkLogin = getWhether.getWhetherLoginerCharge();
			if (!checkLogin.checkRole() == false) {
				return false;
			}
		}
		return true;
	}
}
