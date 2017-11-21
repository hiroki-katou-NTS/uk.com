package nts.uk.ctx.sys.auth.app.find.grant;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.grant.RoleSetGrantedPerson;

/**
 * 
 * @author HungTT
 *
 */

@Data
public class RoleSetGrantedPersonDto {

	private String roleSetCd;
	private String companyId;
	private GeneralDate startDate;
	private GeneralDate endDate;
	private String employeeId;

	public RoleSetGrantedPersonDto(String roleSetCd, String companyId, GeneralDate startDate, GeneralDate endDate,
			String employeeId) {
		super();
		this.roleSetCd = roleSetCd;
		this.companyId = companyId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.employeeId = employeeId;
	}

	public static RoleSetGrantedPersonDto fromDomain(RoleSetGrantedPerson domain) {
		return new RoleSetGrantedPersonDto(domain.getRoleSetCd().v(), domain.getCompanyId(),
				domain.getValidPeriod().start(), domain.getValidPeriod().end(), domain.getEmployeeID());
	}

}
