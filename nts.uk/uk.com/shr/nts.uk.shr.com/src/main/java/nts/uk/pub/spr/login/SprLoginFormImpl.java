package nts.uk.pub.spr.login;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.pub.spr.login.output.LoginUserContextSpr;
import nts.uk.pub.spr.login.output.UserSpr;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class SprLoginFormImpl implements SprLoginFormService {
	
	@Inject
	private UserSprService userSprInterface;
	
	@Override
	public void loginFromSpr(String companyID, String loginCD, String loginID, String targetID, String personID, Integer destScreen,
			Integer otherParam, GeneralDate date, String appID, GeneralDateTime startTime, GeneralDateTime endTime,
			Integer extractionTarget) {
		this.generateSession(loginCD, loginID, personID, date);
		// session storage
		this.screenTransition(companyID, loginID, targetID, personID, destScreen, 
				otherParam, date, appID, startTime, endTime, extractionTarget);
	}

	@Override
	public LoginUserContextSpr generateSession(String employeeCD, String employeeID, String personID, GeneralDate date) {
		// （権限管理Export）アルゴリズム「紐付け先個人IDからユーザを取得する」を実行する(thuc hien thuat toan 「紐付け先個人IDからユーザを取得する」 )
		Optional<UserSpr> opUserSpr = userSprInterface.getUserSpr(personID);
		if(!opUserSpr.isPresent()){
			throw new BusinessException("Msg_301");
		}
		UserSpr userSpr = opUserSpr.get();
		// 権限（ロール）情報を取得、設定する(lay thong tin quuyen han (role) roi setting)
		String roleID = this.getRoleInfo(userSpr.getUserID(), date);
		return new LoginUserContextSpr(
				userSpr.getUserID(), 
				"000000000000", // 固定
				"000000000000-0001", // 固定
				"0001", // 固定
				personID, 
				employeeID, 
				employeeCD, 
				roleID);
		
	}

	@Override
	public String getRoleInfo(String userID, GeneralDate date) {
		String role = null;
		String resultRole = null;
		
		// ロール種類＝就業
		resultRole = userSprInterface.getRoleFromUserId(userID, 3, date);
		if(Strings.isNotBlank(resultRole)){
			role = resultRole;
		}
		
		// ロール種類＝給与
		resultRole = userSprInterface.getRoleFromUserId(userID, 4, date);
		if(Strings.isNotBlank(resultRole)){
			role = resultRole;
		}
		
		// ロール種類＝人事
		resultRole = userSprInterface.getRoleFromUserId(userID, 5, date);
		if(Strings.isNotBlank(resultRole)){
			role = resultRole;
		}
		
		// ロール種類＝オフィスヘルパー
		resultRole = userSprInterface.getRoleFromUserId(userID, 6, date);
		if(Strings.isNotBlank(resultRole)){
			role = resultRole;
		}
		
		/*
		// ロール種類＝会計
		resultRole = userSprInterface.getRoleFromUserId(userID, roleType, date);
		if(Strings.isNotBlank(resultRole)){
			role = resultRole;
		}
		*/
		
		// ロール種類＝マイナンバー
		resultRole = userSprInterface.getRoleFromUserId(userID, 7, date);
		if(Strings.isNotBlank(resultRole)){
			role = resultRole;
		}
		
		// ロール種類＝グループ会社管理
		resultRole = userSprInterface.getRoleFromUserId(userID, 2, date);
		if(Strings.isNotBlank(resultRole)){
			role = resultRole;
		}
		
		// ロール種類＝会社管理者
		resultRole = userSprInterface.getRoleFromUserId(userID, 1, date);
		if(Strings.isNotBlank(resultRole)){
			role = resultRole;
		}
		
		// ロール種類＝システム管理者
		resultRole = userSprInterface.getRoleFromUserId(userID, 0, date);
		if(Strings.isNotBlank(resultRole)){
			role = resultRole;
		}
		
		// ロール種類＝個人情報
		resultRole = userSprInterface.getRoleFromUserId(userID, 8, date);
		if(Strings.isNotBlank(resultRole)){
			role = resultRole;
		}
		
		return role;
	}

	@Override
	public void screenTransition(String companyID, String loginID, String targetID, String personID, Integer destScreen,
			Integer otherParam, GeneralDate date, String appID, GeneralDateTime startTime, GeneralDateTime endTime,
			Integer extractionTarget) {
		switch (destScreen) {
		case 1:
			
			break;
		case 2:
			
			break;
		case 3:
			
			break;
		case 4:
			
			break;
		case 5:
			
			break;
		case 6:
			
			break;
		default:
			break;
		}
		
	}
	
}
