package nts.uk.ctx.sys.auth.pubimp.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.security.hash.password.PasswordHash;
import nts.uk.ctx.sys.auth.dom.adapter.securitypolicy.PasswordPolicyAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.securitypolicy.PasswordPolicyImport;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;
import nts.uk.ctx.sys.auth.pub.user.CheckBeforeChangePassOutput;
import nts.uk.ctx.sys.auth.pub.user.CheckBeforePasswordPublisher;
@Stateless
public class CheckBeforePasswordPublisherImpl implements CheckBeforePasswordPublisher {
	@Inject
	private UserRepository userRepo;
	@Inject
	private PasswordPolicyAdapter passwordPolicyAdap;
	@Override
	public CheckBeforeChangePassOutput checkBeforeChangePassword(String userId, String currentPass, String newPass,
			String reNewPass) {
			List<String> messages = new ArrayList<String>();
		// 変更前チェック
		if (!newPass.equals(reNewPass)) {
			messages.add("#Msg_961");
			 return new CheckBeforeChangePassOutput(true, messages);
		}
		 User user = this.userRepo.getByUserID(userId).get();
		if(user.getLoginID().v().equals(newPass)){
			messages.add("#Msg_989");
			return new CheckBeforeChangePassOutput(true, messages);
		}
		String currentPassHash = PasswordHash.generate(currentPass, userId);
		if(!currentPassHash.equals(user.getPassword())){
			messages.add("#Msg_302");
			return new CheckBeforeChangePassOutput(true,messages);
		}
		//ドメインモデル「パスワードポリシー」を取得する
		Optional<PasswordPolicyImport> passwordPolicyOpt = this.passwordPolicyAdap.getPasswordPolicy(user.getContractCode().v());
		
		PasswordPolicyImport passwordPolicyImport = passwordPolicyOpt.get();
		
		return this.passwordPolicyCheck(userId, reNewPass, passwordPolicyImport);
	}
	
	
	//パスワードポリシーチェック
	private CheckBeforeChangePassOutput passwordPolicyCheck(String userId,String newPass,PasswordPolicyImport  passwordPolicyImport){
		List<String> messages = new ArrayList<String>();
		PasswordPolicyCountChar countChar = this.getCountChar(newPass);
		int lengthPass = newPass.length();
		int numberOfDigits = countChar.getNumberOfDigits();
		int alphabetDigit = countChar.getAlphabetDigit();
		int symbolCharacters = countChar.getSymbolCharacters();
		
		if(passwordPolicyImport.isUse){
			if(lengthPass<passwordPolicyImport.getLowestDigits()){
				messages.add("#Msg_1186"+","+passwordPolicyImport.getLowestDigits()) ;
			}
			if(alphabetDigit<passwordPolicyImport.getAlphabetDigit()){
				messages.add( "#Msg_1188"+","+passwordPolicyImport.getAlphabetDigit());
			}
			if(numberOfDigits<passwordPolicyImport.getNumberOfDigits()){
				messages.add( "#Msg_1189"+","+passwordPolicyImport.getNumberOfDigits());
			}
			if(symbolCharacters<passwordPolicyImport.getSymbolCharacters()){
				messages.add( "#Msg_1190"+","+passwordPolicyImport.getNumberOfDigits());
			}
			if(passwordPolicyImport.getHistoryCount()>0){
				String newPassHash = PasswordHash.generate(newPass, userId);
			}
			//TODO  パスワード履歴チェック
			//domain パスワード変更ログ PasswordChangeLog
		}
		if(messages.isEmpty()){
			return new CheckBeforeChangePassOutput(false, messages) ;
		}else{
			return new CheckBeforeChangePassOutput(true, messages) ;
		}
	}
	
	
	private PasswordPolicyCountChar getCountChar(String newPass){
		
		int countAlphabet =0;
		int countDigits =0;
		int countSymbol = 0;
		for (int i = 0; i < newPass.length(); i++) {
			if(Character.isLetter(newPass.charAt(i))){
				countAlphabet++;
				continue;
			}
			if(Character.isDigit(newPass.charAt(i))){
				countDigits++;
				continue;
			}
			countSymbol++;
		}
		return new PasswordPolicyCountChar(countDigits, countSymbol, countAlphabet);
	}
	
}
