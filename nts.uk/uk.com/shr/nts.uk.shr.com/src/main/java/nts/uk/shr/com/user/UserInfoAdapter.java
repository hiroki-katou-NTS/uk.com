package nts.uk.shr.com.user;

public interface UserInfoAdapter {

	default public String getUserName(String userId) {
		return userId;
	}
}
