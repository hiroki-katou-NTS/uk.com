package nts.uk.ctx.pereg.app.find.employee;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.dom.employeeinfo.service.EmployeeBusiness;
import nts.uk.ctx.sys.gateway.dom.login.UserRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmployeeInfoFinder {

	@Inject
	private EmployeeRepository employeeRepository;

	@Inject
	private EmployeeBusiness employeeBusiness;

//	@Inject
//	private UserRepository userRepo;

	public String getGenerateEmplCodeAndComId(String startLetters) {
		String ComId = AppContexts.user().companyId();
		String EmpCode = employeeBusiness.generateEmplCode(startLetters);
		return EmpCode == "" ? "" : ComId + EmpCode;
	}

	public void validateEmpInfo(EmpInfoDto empInfo) {

		Boolean isDuplicateEmpCode = this.employeeRepository.isDuplicateEmpCode(AppContexts.user().companyId(),
				empInfo.getEmployeeCode());

		if (isDuplicateEmpCode) {
			throw new BusinessException(new RawErrorMessage("Msg_345"));
		}
		// Boolean isDuplicateCardNo =
		// this.employeeRepository.isDuplicateCardNo(AppContexts.user().companyId(),
		// empInfo.getCardNo());
		//
		// if (isDuplicateCardNo) {
		// throw new BusinessException(new RawErrorMessage("Msg_346"));
		// }
//		Boolean isDuplicateLoginId = this.userRepo.getByLoginId(empInfo.getLoginId()).isPresent();

//		if (isDuplicateLoginId) {
//
//			throw new BusinessException(new RawErrorMessage("Msg_757"));
//
//		}

	}
}
