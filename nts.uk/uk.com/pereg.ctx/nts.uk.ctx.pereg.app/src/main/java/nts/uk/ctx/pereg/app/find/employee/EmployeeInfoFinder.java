package nts.uk.ctx.pereg.app.find.employee;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.dom.employeeinfo.service.EmployeeBusinessService;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmployeeInfoFinder {

	@Inject
	private EmployeeDataMngInfoRepository empDataRepo;

	@Inject
	private EmployeeBusinessService employeeBusiness;

	@Inject
	private UserRepository userRepo;

	private static final String JP_SPACE = "　";
	
	public String getGenerateEmplCodeAndComId(String startLetters) {
		String ComId = AppContexts.user().companyId();
		String EmpCode = employeeBusiness.generateEmplCode(startLetters);
		return EmpCode == "" ? "" : ComId + EmpCode;
	}
	
	public String generateEmplCode(String startLetters) {
		return employeeBusiness.generateEmplCode(startLetters);
	}

	public String generateCardNo(String startLetters) {
		return employeeBusiness.generateCardNo(startLetters);
	}

	public void validateEmpInfo(EmpInfoDto empInfo) {
		Boolean isDuplicateEmpCode = !CollectionUtil.isEmpty(this.empDataRepo
				.getEmployeeNotDeleteInCompany(AppContexts.user().companyId(), empInfo.getEmployeeCode()));

		if (isDuplicateEmpCode) {
			throw new BusinessException("Msg_345");
		}
		
		String employeeName = empInfo.getEmployeeName();
		if (employeeName.startsWith(JP_SPACE) || employeeName.endsWith(JP_SPACE)
				|| !employeeName.contains(JP_SPACE)){
			throw new BusinessException("Msg_924");
		}
		// Boolean isDuplicateCardNo =
		// this.employeeRepository.isDuplicateCardNo(AppContexts.user().companyId(),
		// empInfo.getCardNo());
		//
		// if (isDuplicateCardNo) {
		// throw new BusinessException(new RawErrorMessage("Msg_346"));
		// }
		Boolean isDuplicateLoginId = !CollectionUtil.isEmpty(this.userRepo.getByLoginId(empInfo.getLoginId()));

		if (isDuplicateLoginId) {

			throw new BusinessException("Msg_757");

		}

	}

}
