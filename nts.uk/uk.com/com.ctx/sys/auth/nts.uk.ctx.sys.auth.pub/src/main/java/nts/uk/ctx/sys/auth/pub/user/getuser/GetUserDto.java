package nts.uk.ctx.sys.auth.pub.user.getuser;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDto {
	
	private String userId;
	
	private String loginId;
	
	private String userName;
	
	private String associatedPersonID;
	
	private String mailAddress;
	
	private String password;
	
}
