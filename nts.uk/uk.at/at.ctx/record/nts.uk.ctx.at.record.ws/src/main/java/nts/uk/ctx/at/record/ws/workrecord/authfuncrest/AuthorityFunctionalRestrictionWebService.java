/**
 * 
 */
package nts.uk.ctx.at.record.ws.workrecord.authfuncrest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.error.BusinessException;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.workrecord.authfuncrest.AuthFuncRestrictionCommand;
import nts.uk.ctx.at.record.app.command.workrecord.authfuncrest.AuthFuncRestrictionCommandHandler;
import nts.uk.ctx.at.record.app.find.workrecord.authfuncrest.EmployeeRoleDto;
import nts.uk.ctx.at.record.app.find.workrecord.authfuncrest.FunctionalRestrictionWithAuthorityDto;
import nts.uk.ctx.at.record.dom.workrecord.authormanage.DailyPerformanceAuthority;
import nts.uk.ctx.at.record.dom.workrecord.authormanage.DailyPerformAuthorRepo;
import nts.uk.ctx.at.record.dom.workrecord.authormanage.DailyPerformanceFunction;
import nts.uk.ctx.at.record.dom.workrecord.authormanage.DailyPerformFuncRepo;
import nts.uk.ctx.at.record.dom.workrecord.emplrole.EmployeeRole;
import nts.uk.ctx.at.record.dom.workrecord.emplrole.EmployeeRoleRepoInterface;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author danpv
 *
 */
@Path("at/record/workrecord/authfuncrest/")
@Produces("application/json")
public class AuthorityFunctionalRestrictionWebService extends WebService {

	@Inject
	private EmployeeRoleRepoInterface emplRoleRepo;

	@Inject
	private DailyPerformFuncRepo dailyPerfFunctionRepo;

	@Inject
	private DailyPerformAuthorRepo dailyPerAuthRepo;

	@Inject
	private AuthFuncRestrictionCommandHandler authFuncRestHandler;

	@POST
	@Path("find-emp-roles")
	public List<EmployeeRoleDto> getEmployeeRoles() {
		String companyId = AppContexts.user().companyId();
		List<EmployeeRole> employeeRoles = emplRoleRepo.getEmployeeRoles(new CompanyId(companyId));
		if (employeeRoles.isEmpty()) {
			throw new BusinessException("Msg_398");
		}
		return employeeRoles.stream().map(er -> new EmployeeRoleDto(er.getRoleId(), er.getRoleName()))
				.collect(Collectors.toList());
	}

	@POST
	@Path("find/{roleId}")
	public List<FunctionalRestrictionWithAuthorityDto> findFuncRestWithAuthor(@PathParam("roleId") String roleId) {
		List<DailyPerformanceFunction> daiPerfFunctions = dailyPerfFunctionRepo.getAll();
		List<DailyPerformanceAuthority> daiPerAuthors = dailyPerAuthRepo.get(roleId);

		// function-No , function-name, function-description
		List<FunctionalRestrictionWithAuthorityDto> results = new ArrayList<>();
		daiPerfFunctions.forEach(
				dpFunc -> results.add(new FunctionalRestrictionWithAuthorityDto(dpFunc.getFunctionNo().v().intValue(),
						dpFunc.getDisplayName().v(), dpFunc.getDescription().v())));

		// function-availability
		results.forEach(dto -> {
			daiPerAuthors.forEach(dpAu -> {
				if (dto.getFunctionNo() == dpAu.getFunctionNo().v().intValue()) {
					dto.setAvailability(dpAu.isAvailability());
				}
			});
		});
		return results;
	}

	@POST
	@Path("register")
	public void registerAuthFuncRestriction(AuthFuncRestrictionCommand command) {
		authFuncRestHandler.handle(command);
	}

}
