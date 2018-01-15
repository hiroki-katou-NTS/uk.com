package nts.uk.ctx.sys.auth.app.find._role;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleIndividualGrantDto {
	
	/** The user Id. */
	private String userId;

	/** The name. */
	private String name;
	
	private GeneralDate start;
	
	private GeneralDate end;
	
	public RoleIndividualGrantDto(String userId, String name, DatePeriod DatePeriod) {
		super();
		this.userId = userId;
		this.name = name;
		this.start = DatePeriod.start();
		this.end = DatePeriod.end();
	}
	
	public static RoleIndividualGrantDto fromDomain(RoleIndividualGrant domain, String name) {
		return new RoleIndividualGrantDto(domain.getUserId(), name, domain.getValidPeriod());
	}
	
}
