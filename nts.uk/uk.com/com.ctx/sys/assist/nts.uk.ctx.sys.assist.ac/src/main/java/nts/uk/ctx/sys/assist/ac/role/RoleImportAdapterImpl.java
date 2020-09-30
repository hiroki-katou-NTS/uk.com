package nts.uk.ctx.sys.assist.ac.role;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.datarestoration.LoginPersonInCharge;
import nts.uk.ctx.sys.assist.dom.role.RoleImport;
import nts.uk.ctx.sys.assist.dom.role.RoleImportAdapter;
import nts.uk.ctx.sys.auth.pub.role.RoleExportRepo;
import nts.uk.ctx.sys.auth.pub.role.RoleWhetherLoginPubExport;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class RoleImportAdapterImpl implements RoleImportAdapter {

	@Inject
	private RoleExportRepo roleExportRepo;
	
	@Override
	public Optional<RoleImport> findByRoleId(String roleId) {
		return roleExportRepo.findByRoleId(roleId)
				.map(r -> new RoleImport(r.getRoleId(), r.getAssignAtr()));
	}

	@Override
	public LoginPersonInCharge getInChargeInfo() {
		LoginPersonInCharge pic = new LoginPersonInCharge();
		RoleWhetherLoginPubExport exp = roleExportRepo.getWhetherLoginerCharge();
		pic.setAttendance(exp.isEmployeeCharge());
		pic.setEmployeeInfo(exp.isPersonalInformation());
		pic.setOfficeHelper(exp.isOfficeHelperPersonne());
		pic.setPayroll(exp.isSalaryProfessional());
		pic.setPersonnel(exp.isHumanResOfficer());
		return pic;
	}
}
