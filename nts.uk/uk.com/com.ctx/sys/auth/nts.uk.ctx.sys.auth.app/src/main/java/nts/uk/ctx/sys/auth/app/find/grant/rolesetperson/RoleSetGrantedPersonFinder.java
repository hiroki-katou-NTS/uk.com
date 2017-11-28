package nts.uk.ctx.sys.auth.app.find.grant.rolesetperson;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.sys.auth.app.find.grant.rolesetjob.RoleSetDto;
import nts.uk.ctx.sys.auth.dom.adapter.employee.EmployeeAdapter;
import nts.uk.ctx.sys.auth.dom.employee.dto.EmployeeImport;
import nts.uk.ctx.sys.auth.dom.grant.rolesetperson.RoleSetGrantedPerson;
import nts.uk.ctx.sys.auth.dom.grant.rolesetperson.RoleSetGrantedPersonRepository;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class RoleSetGrantedPersonFinder {

	@Inject
	private RoleSetRepository roleSetRepo;

	@Inject
	private RoleSetGrantedPersonRepository roleSetPersonRepo;

	@Inject
	private EmployeeAdapter employeeAdapter;

	public List<RoleSetDto> getAllRoleSet() {
		String companyId = AppContexts.user().companyId();
		if (companyId == null)
			return null;
		// get Role Set by companyId, sort ASC
		// List<RoleSetDto> listRoleSet =
		// roleSetRepo.findByCompanyId(companyId).stream()
		// .map(item -> new RoleSetDto(item.getRoleSetCd().v(),
		// item.getRoleSetName().v()))
		// .collect(Collectors.toList());
		//
		// if (listRoleSet == null || listRoleSet.isEmpty()) {
		// throw new BusinessException("Msg_713");
		// }
		//
		// listRoleSet.sort((rs1, rs2) ->
		// rs1.getCode().compareTo(rs2.getCode()));
		// return listRoleSet;

		// dummy code
		List<RoleSetDto> listRS = new ArrayList<>();
		for (int i = 1; i < 10; i++) {
			listRS.add(new RoleSetDto(i < 10 ? "0" + i : "10", i < 10 ? "role set 0" + i : "role set 10"));
		}
		return listRS;

	}

	public List<RoleSetGrantedPersonDto> getAllRoleSetGrantedPersonByRoleSetCd(String roleSetCd) {
		String companyId = AppContexts.user().companyId();
		if (companyId == null)
			return null;
		List<RoleSetGrantedPerson> listRoleSetPerson = roleSetPersonRepo.getAll(roleSetCd, companyId);

		if (listRoleSetPerson != null && !listRoleSetPerson.isEmpty()) {
			List<RoleSetGrantedPersonDto> listRoleSetPersonDto = new ArrayList<>();
			for (RoleSetGrantedPerson rp : listRoleSetPerson) {
				EmployeeImport empInfo = new EmployeeImport("XXXX YYYY", rp.getEmployeeID(), "99999999");//employeeAdapter.findByEmpId(rp.getEmployeeID());
				RoleSetGrantedPersonDto dto = new RoleSetGrantedPersonDto(rp.getRoleSetCd().v(), rp.getEmployeeID(),
						empInfo.getEmployeeCode(), empInfo.getPersonalName(), rp.getValidPeriod().start(),
						rp.getValidPeriod().end());
				listRoleSetPersonDto.add(dto);
			}
			// sort by empCd (SCD) asc
			listRoleSetPersonDto.sort((rsp1, rsp2) -> rsp1.getEmployeeCd().compareTo(rsp2.getEmployeeCd()));
			return listRoleSetPersonDto;
		} else
			return null;

		// dummy code
		// List<RoleSetGrantedPersonDto> listRS = new ArrayList<>();
		// for (int i = 1; i < 10; i++) {
		// listRS.add(new RoleSetGrantedPersonDto(roleSetCd,
		// IdentifierUtil.randomUniqueId(), roleSetCd + "00" + i,
		// "employee name " + i, GeneralDate.ymd(2017, 11, i),
		// GeneralDate.ymd(2017, 12, i)));
		// }
		// return listRS;
	}

	public EmployeeImport getEmployeeInfo(String employeeId) {
		//return employeeAdapter.findByEmpId(employeeId);
		// dummy code
		return new EmployeeImport("XXXX YYYY", employeeId, "99999999");
	}
}
