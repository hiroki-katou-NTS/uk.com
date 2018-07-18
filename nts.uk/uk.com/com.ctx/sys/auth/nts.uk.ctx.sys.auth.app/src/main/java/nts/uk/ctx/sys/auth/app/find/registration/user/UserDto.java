package nts.uk.ctx.sys.auth.app.find.registration.user;

import lombok.Value;
import nts.uk.ctx.sys.auth.dom.user.SearchUser;
import nts.uk.ctx.sys.auth.dom.user.User;

@Value
public class UserDto {
	 
	 private String loginID;
	 
	 private String userName;

	 
	 public static UserDto fromDomain(User domain){
		 return new  UserDto (
				 domain.getLoginID().toString(),
				 domain.getUserName().toString());
	 }

	 public static UserDto objDomain(SearchUser domain){
		 return new  UserDto (
				 domain.getLoginID().toString(),
				 domain.getUserName().toString());
	 }
}
