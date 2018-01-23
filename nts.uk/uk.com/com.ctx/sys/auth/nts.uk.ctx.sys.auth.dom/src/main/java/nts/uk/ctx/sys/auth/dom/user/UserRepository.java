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

	List<User> searchUser(String userIDName, GeneralDate date);

	List<User> getByListUser(List<String> userID);

	List<User> getAllUser();

	void addNewUser(User newUser);

}