package nts.uk.ctx.sys.auth.dom.registration.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.gul.security.hash.password.PasswordHash;
import nts.uk.ctx.sys.auth.dom.adapter.securitypolicy.PasswordPolicyAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.securitypolicy.PasswordPolicyImport;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrantRepository;
import nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLog;
import nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLogRepository;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;

@Stateless
public class RegistrationUserServiceImp implements RegistrationUserService {

	@Inject
	UserRepository userRepo;

	@Inject
	RoleIndividualGrantRepository roleIndividualGrantRepository;

	@Inject
	private PasswordPolicyAdapter passwordPolicyAdap;

	@Inject
	private PasswordChangeLogRepository passwordChangeLogRepository;

	private final GeneralDate LAST_DATE = GeneralDate.fromString("9999/12/31", "yyyy/MM/dd");

	@Override
	public boolean checkSystemAdmin(String userID, GeneralDate validityPeriod) {
		if (validityPeriod.equals(LAST_DATE))
			return true;
		List<RoleIndividualGrant> roleIndiGrant = roleIndividualGrantRepository.findByUserAndRole(userID,
				RoleType.SYSTEM_MANAGER.value);
		if (roleIndiGrant.isEmpty())
			return true;
		// Create new List System Admin
		List<CheckSysAdmin> listCheckSysAdmin = new ArrayList<CheckSysAdmin>();
		for (RoleIndividualGrant item : roleIndiGrant) {
			if (item.getValidPeriod().start().beforeOrEquals(validityPeriod)) {
				listCheckSysAdmin
						.add(new CheckSysAdmin(userID, item.getValidPeriod().start(), item.getValidPeriod().end()));
			} else {
				listCheckSysAdmin.add(new CheckSysAdmin(userID, item.getValidPeriod().start(), validityPeriod));
			}
		}
		List<RoleIndividualGrant> listSysAdmin = roleIndividualGrantRepository
				.findByRoleType(RoleType.SYSTEM_MANAGER.value);
		List<RoleIndividualGrant> filterListRoleIndividualGrant = listSysAdmin.stream()
				.filter(c -> !c.getUserId().equals(userID) && c.getRoleType().equals(RoleType.SYSTEM_MANAGER))
				.collect(Collectors.toList());
		List<String> userIds = filterListRoleIndividualGrant.stream().map(c -> c.getUserId())
				.collect(Collectors.toList());

		List<User> users = new ArrayList<User>();
		if (!userIds.isEmpty())
			users = userRepo.getByListUser(userIds);

		for (RoleIndividualGrant roleIndividualGrant : filterListRoleIndividualGrant) {
			User user = users.stream().filter(c -> c.getUserID().equals(roleIndividualGrant.getUserId())).findFirst()
					.get();
			CheckSysAdmin checkSysAdmin = new CheckSysAdmin(userID, roleIndividualGrant.getValidPeriod().start(),
					roleIndividualGrant.getValidPeriod().end());

			if (roleIndividualGrant.getValidPeriod().end().after(user.getExpirationDate())) {
				checkSysAdmin.setEndDate(user.getExpirationDate());
			}
			listCheckSysAdmin.add(checkSysAdmin);
		}
		listCheckSysAdmin.sort((a, b) -> {
			return b.getEndDate().compareTo(a.getEndDate());
		});

		GeneralDate validStartDate = GeneralDate.max();
		GeneralDate validEndDate = GeneralDate.max();

		for (CheckSysAdmin checkSysAdmin : listCheckSysAdmin) {
			if (checkSysAdmin.getStartDate().before(validStartDate)
					&& checkSysAdmin.getEndDate().addDays(1).afterOrEquals(validEndDate)) {
				validStartDate = checkSysAdmin.getStartDate();
			}
		}

		if (validStartDate.beforeOrEquals(GeneralDate.today()) && validEndDate.equals(GeneralDate.max())) {
			return true;
		}

		return false;

	}

	public CheckBeforeChangePassOutput checkPasswordPolicy(String userId, String pass, String contractCode) {
		// get PasswordPolicy
		PasswordPolicyImport passwordPolicyImport = this.passwordPolicyAdap.getPasswordPolicy(contractCode).get();

		List<PasswordMessageObject> messages = new ArrayList<>();
		PasswordPolicyCountChar countChar = this.getCountChar(pass);

		PasswordSplitObject passSplit = new PasswordSplitObject();
		passSplit.setLengthPass(pass.length());
		passSplit.setNumberOfDigits(countChar.getNumberOfDigits());
		passSplit.setAlphabetDigit(countChar.getAlphabetDigit());
		passSplit.setSymbolCharacters(countChar.getSymbolCharacters());

		if (passwordPolicyImport.isUse) {
			// check PassPolicy
			messages = this.checkPolicyChar(passwordPolicyImport, passSplit);

			// check historyCount
			PasswordMessageObject messHist = this.checkHistoyCount(passwordPolicyImport.getHistoryCount(), userId,
					pass);

			if (messHist.getMessage() != null) {
				messages.add(messHist);
			}

		}
		if (messages.isEmpty()) {
			return new CheckBeforeChangePassOutput(false, messages);
		} else {
			return new CheckBeforeChangePassOutput(true, messages);
		}
	}

	private PasswordPolicyCountChar getCountChar(String newPass) {

		int countAlphabet = 0;
		int countDigits = 0;
		int countSymbol = 0;
		for (int i = 0; i < newPass.length(); i++) {
			if (Character.isLetter(newPass.charAt(i))) {
				countAlphabet++;
				continue;
			}
			if (Character.isDigit(newPass.charAt(i))) {
				countDigits++;
				continue;
			}
			countSymbol++;
		}
		return new PasswordPolicyCountChar(countDigits, countSymbol, countAlphabet);
	}

	private List<PasswordMessageObject> checkPolicyChar(PasswordPolicyImport passwordPolicyImport,
			PasswordSplitObject passSplit) {
		// List message
		List<PasswordMessageObject> messages = new ArrayList<>();

		// check passpolicy
		if (passSplit.getLengthPass() < passwordPolicyImport.getLowestDigits()) {
			messages.add(new PasswordMessageObject("Msg_1186", passwordPolicyImport.getLowestDigits()));
		}
		if (passSplit.getAlphabetDigit() < passwordPolicyImport.getAlphabetDigit()) {
			messages.add(new PasswordMessageObject("Msg_1188", passwordPolicyImport.getAlphabetDigit()));
		}
		if (passSplit.getNumberOfDigits() < passwordPolicyImport.getNumberOfDigits()) {
			messages.add(new PasswordMessageObject("Msg_1189", passwordPolicyImport.getNumberOfDigits()));
		}
		if (passSplit.getSymbolCharacters() < passwordPolicyImport.getSymbolCharacters()) {
			messages.add(new PasswordMessageObject("Msg_1190", passwordPolicyImport.getSymbolCharacters()));
		}
		// return
		return messages;
	}

	private PasswordMessageObject checkHistoyCount(Integer historyCount, String userId, String newPass) {
		if (historyCount > 0) {
			// Check password history
			String newPassHash = PasswordHash.generate(newPass, userId);
			if (this.isHistoryPassError(userId, historyCount, newPassHash)) {
				return new PasswordMessageObject("Msg_1187", historyCount);
			}

			return new PasswordMessageObject(null);
		}

		return new PasswordMessageObject(null);
	}

	private boolean isHistoryPassError(String userId, int historyCount, String newPassHash) {
		List<PasswordChangeLog> listPasswordChangeLog = this.passwordChangeLogRepository.findByUserId(userId,
				historyCount + 1);
		Optional<PasswordChangeLog> duplicatePassword = listPasswordChangeLog.stream()
				.filter(item -> item.getPassword().v().equals(newPassHash)).findFirst();
		return duplicatePassword.isPresent();
	}

	@Data
	@AllArgsConstructor
	protected class CheckSysAdmin {
		private String userID;
		private GeneralDate startDate;
		private GeneralDate endDate;
	}

	@AllArgsConstructor
	@Data
	protected class PasswordPolicyCountChar {
		private int numberOfDigits;
		private int symbolCharacters;
		private int alphabetDigit;
	}

	@AllArgsConstructor
	@Data
	protected class PasswordMessageObject {

		private String message;
		private String param;

		public PasswordMessageObject(String message, int param) {
			super();
			this.message = message;
			this.param = String.valueOf(param);
		}

		public PasswordMessageObject(String message) {
			super();
			this.message = message;
		}
	}

	@Getter
	@Setter
	protected class PasswordSplitObject {

		/** The length pass. */
		private Integer lengthPass;

		/** The number of digits. */
		private Integer numberOfDigits;

		/** The alphabet digit. */
		private Integer alphabetDigit;

		/** The symbol characters. */
		private Integer symbolCharacters;

	}
}
