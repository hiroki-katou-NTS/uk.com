package nts.uk.ctx.sys.auth.pub.user;

import java.util.List;
import java.util.Optional;

public interface UserPublisher {

	Optional<UserDto> getUserInfo(String userId);
	
	/** Requestlist 222
	 * 契約コードとログインIDからユーザを取得する
	 */
	Optional<UserExport> getUserByContractAndLoginId(String contractCode, String loginId);
	
	/** Requestlist 220
	 * 紐付け先個人IDからユーザを取得する
	 */

	Optional<UserExport> getUserByAssociateId(String associatePersonId);
	
	List<UserExport> getListUserByListAsId(List<String> listAssociatePersonId);
	
	Optional<UserExport> getByUserId(String userId);
}
