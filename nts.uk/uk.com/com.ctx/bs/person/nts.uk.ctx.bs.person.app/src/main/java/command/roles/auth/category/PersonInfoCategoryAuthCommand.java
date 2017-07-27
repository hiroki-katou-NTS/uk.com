package command.roles.auth.category;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersonInfoCategoryAuthCommand {
	private String roleId;
	
	private String personInfoCategoryAuthId;
		
	private int allowPersonRef;
	
	private int allowOtherRef;
	
	private int allowOtherCompanytRef;
	
	private int selfPastHisAuth;
	
	private int selfFutureHisAuth;
	
	private int selfAllowAddHis;
	
	private int selfAllowDelHis;
	
	private int otherPastHisAuth;

	private int otherFutureHisAuth;

	private int otherAllowAddHis;

	private int otherAllowDelHis;

	private int selfAllowAddMulti;

	private int selfAllowDelMulti;

	private int otherAllowAddMulti;

	private int otherAllowDelMulti;

}
