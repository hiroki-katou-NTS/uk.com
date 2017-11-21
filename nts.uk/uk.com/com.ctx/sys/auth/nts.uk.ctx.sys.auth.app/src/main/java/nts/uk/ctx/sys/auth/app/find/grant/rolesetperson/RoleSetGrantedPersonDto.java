package nts.uk.ctx.sys.auth.app.find.grant.rolesetperson;


import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.grant.rolesetperson.RoleSetGrantedPerson;

/**
 * 
 * @author HungTT
 *
 */

@Data
public class RoleSetGrantedPersonDto {

	private String roleSetCd;
	private String employeeId;
	private String employeeCd;
	private String employeeName;
	private GeneralDate startDate;
	private GeneralDate endDate;

	public RoleSetGrantedPersonDto(String roleSetCd, String employeeId, String employeeCd, String employeeName, GeneralDate startDate, GeneralDate endDate) {
		super();
		this.roleSetCd = roleSetCd;
		this.employeeCd = employeeCd;
		this.employeeName = employeeName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.employeeId = employeeId;
	}

//	public static RoleSetGrantedPersonDto fromDomain(RoleSetGrantedPerson domain) {
//		return new RoleSetGrantedPersonDto(domain.getRoleSetCd().v(), domain.getCompanyId(),
//				domain.getValidPeriod().start(), domain.getValidPeriod().end(), domain.getEmployeeID());
//	}

}
