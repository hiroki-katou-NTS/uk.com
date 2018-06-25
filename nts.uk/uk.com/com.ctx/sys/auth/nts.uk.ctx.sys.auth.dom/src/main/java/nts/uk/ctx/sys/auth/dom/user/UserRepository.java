package nts.uk.ctx.sys.auth.dom.user;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface UserRepository {

	Optional<User> getByContractAndLoginId(String contractCode, String loginId);

	Optional<User> getByAssociatedPersonId(String associatedPersonId);

	Optional<User> getByUserID(String userID);
	
	List<User> getByLoginId(String loginId);

	List<User> searchBySpecialAndMulti(GeneralDate systemDate, int special, int multi);

	List<SearchUser> searchUser(String userIDName, GeneralDate date);

	List<User> getByListUser(List<String> userID);

	List<User> getAllUser();
	
	List<User> getListUserByListAsID(List<String> listAssociatePersonId);

	Optional<User> getListUserByDefUser(String userID , int defUser ,GeneralDate  expirationDate);
	
	Optional<User> getByUserIDAndDate(String userID , GeneralDate systemDate);

	void addNewUser(User newUser);
	
	void update (User user);
	
	

}