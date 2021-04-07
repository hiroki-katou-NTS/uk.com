package nts.uk.ctx.sys.gateway.app.command.login.password;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.uk.ctx.sys.gateway.app.command.login.LoginRequire;
import nts.uk.ctx.sys.gateway.app.command.login.password.PasswordAuthenticateCommandHandler.Require;
import nts.uk.ctx.sys.gateway.dom.login.password.authenticate.PasswordAuthenticationFailuresLog;
import nts.uk.ctx.sys.gateway.dom.login.password.authenticate.PasswordAuthenticationFailuresLogRepository;
import nts.uk.ctx.sys.gateway.dom.login.password.identification.PasswordAuthIdentificationFailureLog;
import nts.uk.ctx.sys.gateway.dom.login.password.identification.PasswordAuthIdentificationFailureLogRepository;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.AccountLockPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked.LockoutData;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicyRepository;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.changelog.PasswordChangeLog;
import nts.uk.ctx.sys.shared.dom.company.CompanyInformationAdapter;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeDataManageInfoAdapter;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeDataMngInfoImport;
import nts.uk.ctx.sys.shared.dom.user.User;
import nts.uk.ctx.sys.shared.dom.user.UserRepository;
import nts.uk.ctx.sys.shared.dom.user.builtin.BuiltInUser;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class PasswordAuthenticateCommandRequire {

	@Inject
	private LoginRequire loginRequire;

	@Inject
	private CompanyInformationAdapter companyInformationAdapter;

	@Inject
	private EmployeeDataManageInfoAdapter employeeDataManageInfoAdapter;
	
	@Inject
	private UserRepository userRepo;
	
	@Inject
	private PasswordPolicyRepository passwordPolicyRepository;
	
	@Inject
	private PasswordAuthenticationFailuresLogRepository passwordAuthenticationFailuresLogRepository;
	
	@Inject
	private PasswordAuthIdentificationFailureLogRepository passwordfailureLogRepository;

	public Require createRequire(String tenantCode) {

		val require = new RequireImpl();
		loginRequire.setup(require);
		return EmbedStopwatch.embed(require);

	}

	@RequiredArgsConstructor
	public class RequireImpl extends LoginRequire.BaseImpl implements Require {

		@Override
		public String createCompanyId(String tenantCode, String companyCode) {
			return companyInformationAdapter.createCompanyId(tenantCode, companyCode);
		}

		@Override
		public Optional<EmployeeDataMngInfoImport> getEmployeeDataMngInfoImportByEmployeeCode(String companyId,
				String employeeCode) {
			return employeeDataManageInfoAdapter.findByEmployeeCode(companyId, employeeCode);
		}

		@Override
		public BuiltInUser getBuiltInUser(String tenantCode, String companyId) {
			return new BuiltInUser();
		}

		@Override
		public Optional<User> getUserByPersonId(String personId) {
			return userRepo.getByAssociatedPersonId(personId);
		}

		@Override
		public Optional<PasswordPolicy> getPasswordPolicy(String tenantCode) {
			return passwordPolicyRepository.getPasswordPolicy(tenantCode);
		}

		@Override
		public Optional<AccountLockPolicy> getAccountLockPolicy(String contractCode) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void save(LockoutData lockOutData) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public PasswordChangeLog getPasswordChangeLog(String userId) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void addFailureLog(PasswordAuthIdentificationFailureLog failurLog) {
			passwordfailureLogRepository.insert(failurLog);
		}

		@Override
		public Optional<LockoutData> getLockOutData(String userId) {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

		@Override
		public void save(PasswordAuthenticationFailuresLog failuresLog) {
			return passwordAuthenticationFailuresLogRepository.insert(failuresLog);
		}
	}

}
