/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.pubimp.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.security.hash.password.PasswordHash;
import nts.uk.ctx.sys.auth.dom.adapter.securitypolicy.PasswordPolicyAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.securitypolicy.PasswordPolicyImport;
import nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLog;
import nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLogRepository;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;
import nts.uk.ctx.sys.auth.pub.user.CheckBeforeChangePassOutput;
import nts.uk.ctx.sys.auth.pub.user.CheckBeforePasswordPublisher;
import nts.uk.ctx.sys.auth.pub.user.PasswordMessageObject;
import nts.uk.ctx.sys.auth.pub.user.PasswordSplitObject;

/**
 * The Class CheckBeforePasswordPublisherImpl.
 */
@Stateless
public class CheckBeforePasswordPublisherImpl implements CheckBeforePasswordPublisher {

	/** The user repo. */
	@Inject
	private UserRepository userRepo;

	/** The password change log repository. */
	@Inject
	private PasswordChangeLogRepository passwordChangeLogRepository;

	/** The password policy adap. */
	@Inject
	private PasswordPolicyAdapter passwordPolicyAdap;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.auth.pub.user.CheckBeforePasswordPublisher#
	 * checkBeforeChangePassword(java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public CheckBeforeChangePassOutput checkBeforeChangePassword(String userId, String currentPass, String newPass,
			String reNewPass) {
		List<PasswordMessageObject> messages = new ArrayList<>();
		// 螟画峩蜑阪メ繧ｧ繝�繧ｯ
		if (!newPass.equals(reNewPass)) {
			messages.add( new PasswordMessageObject("Msg_961"));
		}
		User user = this.userRepo.getByUserID(userId).get();
		if (user.getLoginID().v().trim().equals(newPass)) {
			messages.add(new PasswordMessageObject("Msg_989"));
		}
		String currentPassHash = PasswordHash.generate(currentPass, userId);
		if (!currentPassHash.equals(user.getPassword().v())) {
			messages.add(new PasswordMessageObject("Msg_302"));
		}
		if (!messages.isEmpty()) {
			return new CheckBeforeChangePassOutput(true, messages);
		}

		// 繝峨Γ繧､繝ｳ繝｢繝�繝ｫ縲後ヱ繧ｹ繝ｯ繝ｼ繝峨�昴Μ繧ｷ繝ｼ縲阪ｒ蜿門ｾ励☆繧�
		return this.passwordPolicyCheck(userId, reNewPass, user.getContractCode().v());
	}
	
	@Override
	public CheckBeforeChangePassOutput checkBeforeResetPassword(String userId, String newPass, String reNewPass) {
		
		List<PasswordMessageObject> messages = new ArrayList<>();
		// 螟画峩蜑阪メ繧ｧ繝�繧ｯ Check newPass and reNewPass
		if (!newPass.equals(reNewPass)) {
			messages.add(new PasswordMessageObject("Msg_961"));
		}
		//get domain User
		User user = this.userRepo.getByUserID(userId).get();
		
		//Check loginId
		if (user.getLoginID().v().equals(newPass)) {
			messages.add(new PasswordMessageObject("Msg_989"));
		}
		
		if (!messages.isEmpty()) {
			return new CheckBeforeChangePassOutput(true, messages);
		}

		// 繝峨Γ繧､繝ｳ繝｢繝�繝ｫ縲後ヱ繧ｹ繝ｯ繝ｼ繝峨�昴Μ繧ｷ繝ｼ縲阪ｒ蜿門ｾ励☆繧�
		return this.passwordPolicyCheck(userId, reNewPass, user.getContractCode().v());
	}

	/**
	 * Password policy check.
	 *
	 * @param userId
	 *            the user id
	 * @param newPass
	 *            the new pass
	 * @param contractCode
	 *            the contract code
	 * @return the check before change pass output
	 */
	// 繝代せ繝ｯ繝ｼ繝峨�昴Μ繧ｷ繝ｼ繝√ぉ繝�繧ｯ
	@Override
	public CheckBeforeChangePassOutput passwordPolicyCheck(String userId, String newPass, String contractCode) {
		//get PasswordPolicy
		PasswordPolicyImport passwordPolicyImport = this.passwordPolicyAdap.getPasswordPolicy(contractCode).get();

		List<PasswordMessageObject> messages = new ArrayList<>();
		PasswordPolicyCountChar countChar = this.getCountChar(newPass);
		
		PasswordSplitObject passSplit = new PasswordSplitObject();
		passSplit.setLengthPass(newPass.length());
		passSplit.setNumberOfDigits(countChar.getNumberOfDigits());
		passSplit.setAlphabetDigit(countChar.getAlphabetDigit());
		passSplit.setSymbolCharacters(countChar.getSymbolCharacters());

		if (passwordPolicyImport.isUse) {
			//check PassPolicy
			messages = this.checkPolicyChar(passwordPolicyImport, passSplit);
			
			//check historyCount
			PasswordMessageObject messHist = this.checkHistoyCount(passwordPolicyImport.getHistoryCount(), userId, newPass);
			
			if (messHist.getMessage() != null){
				messages.add(messHist);
			}
			
		}
		if (messages.isEmpty()) {
			return new CheckBeforeChangePassOutput(false, messages);
		} else {
			return new CheckBeforeChangePassOutput(true, messages);
		}
	}
	
	@Override
	public CheckBeforeChangePassOutput passwordPolicyCheckForSubmit(String userId, String newPass, String contractCode) {
		//get PasswordPolicy
		PasswordPolicyImport passwordPolicyImport = this.passwordPolicyAdap.getPasswordPolicy(contractCode).get();

		List<PasswordMessageObject> messages = new ArrayList<>();
		PasswordPolicyCountChar countChar = this.getCountChar(newPass);
		
		PasswordSplitObject passSplit = new PasswordSplitObject();
		passSplit.setLengthPass(newPass.length());
		passSplit.setNumberOfDigits(countChar.getNumberOfDigits());
		passSplit.setAlphabetDigit(countChar.getAlphabetDigit());
		passSplit.setSymbolCharacters(countChar.getSymbolCharacters());

		if (passwordPolicyImport.isUse) {
			//check PassPolicy
			messages = this.checkPolicyChar(passwordPolicyImport, passSplit);
		}
		if (messages.isEmpty()) {
			return new CheckBeforeChangePassOutput(false, messages);
		} else {
			return new CheckBeforeChangePassOutput(true, messages);
		}
	}
	
	private List<PasswordMessageObject> checkPolicyChar(PasswordPolicyImport passwordPolicyImport, PasswordSplitObject passSplit){
		//List message
		List<PasswordMessageObject> messages = new ArrayList<>();
		
		//check passpolicy
		if (passSplit.getLengthPass() < passwordPolicyImport.getLowestDigits()) {
			messages.add(new PasswordMessageObject("Msg_1186",passwordPolicyImport.getLowestDigits()));
		}
		if (passSplit.getAlphabetDigit() < passwordPolicyImport.getAlphabetDigit()) {
			messages.add(new PasswordMessageObject("Msg_1188", passwordPolicyImport.getAlphabetDigit()));
		}
		if (passSplit.getNumberOfDigits() < passwordPolicyImport.getNumberOfDigits()) {
			messages.add(new PasswordMessageObject("Msg_1189", passwordPolicyImport.getNumberOfDigits()));
		}
		if (passSplit.getSymbolCharacters() < passwordPolicyImport.getSymbolCharacters()) {
			messages.add( new PasswordMessageObject("Msg_1190", passwordPolicyImport.getSymbolCharacters()));
		}
		//return 
		return messages;
	}
	
	private PasswordMessageObject checkHistoyCount(Integer historyCount, String userId, String newPass){
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

	/**
	 * Checks if is history pass error.
	 *
	 * @param userId
	 *            the user id
	 * @param historyCount
	 *            the history count
	 * @param newPassHash
	 *            the new pass hash
	 * @return true, if is history pass error
	 */
	private boolean isHistoryPassError(String userId, int historyCount, String newPassHash) {
		// domain 繝代せ繝ｯ繝ｼ繝牙､画峩繝ｭ繧ｰ PasswordChangeLog
		List<PasswordChangeLog> listPasswordChangeLog = this.passwordChangeLogRepository.findByUserId(userId,
				historyCount + 1);
		Optional<PasswordChangeLog> duplicatePassword = listPasswordChangeLog.stream()
				.filter(item -> item.getPassword().v().equals(newPassHash)).findFirst();
		return duplicatePassword.isPresent();
	}

	/**
	 * Gets the count char.
	 *
	 * @param newPass
	 *            the new pass
	 * @return the count char
	 */
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

}
