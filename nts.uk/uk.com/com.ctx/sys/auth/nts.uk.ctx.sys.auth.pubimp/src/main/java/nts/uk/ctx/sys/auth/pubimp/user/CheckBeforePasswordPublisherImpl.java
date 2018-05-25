/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.pubimp.user;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.security.hash.password.PasswordHash;
import nts.uk.ctx.sys.auth.dom.adapter.securitypolicy.PasswordPolicyAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.securitypolicy.PasswordPolicyImport;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;
import nts.uk.ctx.sys.auth.pub.user.CheckBeforeChangePassOutput;
import nts.uk.ctx.sys.auth.pub.user.CheckBeforePasswordPublisher;

/**
 * The Class CheckBeforePasswordPublisherImpl.
 */
@Stateless
public class CheckBeforePasswordPublisherImpl implements CheckBeforePasswordPublisher {

	/** The user repo. */
	@Inject
	private UserRepository userRepo;

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
		List<String> messages = new ArrayList<>();
		// 変更前チェック
		if (!newPass.equals(reNewPass)) {
			messages.add("#Msg_961");
		}
		User user = this.userRepo.getByUserID(userId).get();
		if (user.getLoginID().v().equals(newPass)) {
			messages.add("#Msg_989");
		}
		String currentPassHash = PasswordHash.generate(currentPass, userId);
		if (!currentPassHash.equals(user.getPassword().v())) {
			messages.add("#Msg_302");
		}
		if (!messages.isEmpty()) {
			return new CheckBeforeChangePassOutput(true, messages);
		}

		// ドメインモデル「パスワードポリシー」を取得する
		PasswordPolicyImport passwordPolicyImport = this.passwordPolicyAdap
				.getPasswordPolicy(user.getContractCode().v()).get();

		return this.passwordPolicyCheck(userId, reNewPass, passwordPolicyImport);
	}

	/**
	 * Password policy check.
	 *
	 * @param userId
	 *            the user id
	 * @param newPass
	 *            the new pass
	 * @param passwordPolicyImport
	 *            the password policy import
	 * @return the check before change pass output
	 */
	// パスワードポリシーチェック
	private CheckBeforeChangePassOutput passwordPolicyCheck(String userId, String newPass,
			PasswordPolicyImport passwordPolicyImport) {
		
		List<String> messages = new ArrayList<>();
		PasswordPolicyCountChar countChar = this.getCountChar(newPass);
		int lengthPass = newPass.length();
		int numberOfDigits = countChar.getNumberOfDigits();
		int alphabetDigit = countChar.getAlphabetDigit();
		int symbolCharacters = countChar.getSymbolCharacters();

		if (passwordPolicyImport.isUse) {
			if (lengthPass < passwordPolicyImport.getLowestDigits()) {
				messages.add("#Msg_1186" + "," + passwordPolicyImport.getLowestDigits());
			}
			if (alphabetDigit < passwordPolicyImport.getAlphabetDigit()) {
				messages.add("#Msg_1188" + "," + passwordPolicyImport.getAlphabetDigit());
			}
			if (numberOfDigits < passwordPolicyImport.getNumberOfDigits()) {
				messages.add("#Msg_1189" + "," + passwordPolicyImport.getNumberOfDigits());
			}
			if (symbolCharacters < passwordPolicyImport.getSymbolCharacters()) {
				messages.add("#Msg_1190" + "," + passwordPolicyImport.getNumberOfDigits());
			}
			if (passwordPolicyImport.getHistoryCount() > 0) {
				String newPassHash = PasswordHash.generate(newPass, userId);
				// TODO Check pass history
				// TODO パスワード履歴チェック
				// domain パスワード変更ログ PasswordChangeLog
			}
			
		}
		if (messages.isEmpty()) {
			return new CheckBeforeChangePassOutput(false, messages);
		} else {
			return new CheckBeforeChangePassOutput(true, messages);
		}
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
