package nts.uk.ctx.sys.auth.app.find.grant.roleindividual.dto;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;

@Value
public class RoleIndividualGrantDto {

	 private String companyID;
	
	 private int roleType;
	 
	 private String loginID;
	 
	 private String userID;
	 
	 private String userName;
	 
	 private GeneralDate startValidPeriod;
	 
	 private GeneralDate endValidPeriod;
	 
	 public static RoleIndividualGrantDto fromDomain(RoleIndividualGrant domain, String userName , String loginID) {
		 return new RoleIndividualGrantDto(
				 domain.getCompanyId(),
				 domain.getRoleType().value,
				 loginID,
				 domain.getUserId(),
				 userName,
				 domain.getValidPeriod().start(),
				 domain.getValidPeriod().end());
	 }
}
