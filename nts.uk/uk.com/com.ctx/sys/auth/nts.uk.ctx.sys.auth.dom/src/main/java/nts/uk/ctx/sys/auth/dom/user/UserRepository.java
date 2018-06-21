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
	
	void addNewUser(User newUser);
	
	void update (User user);
	
	// add function 21.06.2018 thanhpv
	List<User> searchByKey(GeneralDate systemDate, int special, int multi, String key);	
	// add function 19.06.2018 thanhpv for CAS013
	List<User> searchUserMultiCondition(GeneralDate systemDate, int special, int multi, String key, List<String> employeePersonIdFindName, List<String> employeePersonId);

}