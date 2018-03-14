package nts.uk.pub.spr;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.pub.spr.output.UserSpr;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface UserSprService {
	
	/**
	 * 紐付け先個人IDからユーザを取得する
	 * @param personID 個人ID
	 * @return ユーザを取得する
	 */
	public Optional<UserSpr> getUserSpr(String personID);
	
	/**
	 * ユーザIDからロールを取得する
	 * @param userId ユーザーID
	 * @param roleType ロール種類
	 * @param baseDate 基準日
	 * @return ロールID
	 */
	String getRoleFromUserId(String userId, int roleType, GeneralDate baseDate);
	
}
