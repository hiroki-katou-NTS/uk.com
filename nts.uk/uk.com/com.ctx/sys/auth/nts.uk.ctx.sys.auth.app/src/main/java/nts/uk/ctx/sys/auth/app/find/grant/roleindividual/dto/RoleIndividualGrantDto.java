package nts.uk.ctx.sys.auth.app.find.grant.roleindividual.dto;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;

@Value
public class RoleIndividualGrantDto {

	 private String userID;
	 
	 private GeneralDate startValidPeriod;
	 
	 private GeneralDate endValidPeriod;
	 
	 public static RoleIndividualGrantDto fromDomain(RoleIndividualGrant domain){
		 return new RoleIndividualGrantDto(
				 domain.getUserId(),
				 domain.getValidPeriod().start(), domain.getValidPeriod().end());
	 }
}
