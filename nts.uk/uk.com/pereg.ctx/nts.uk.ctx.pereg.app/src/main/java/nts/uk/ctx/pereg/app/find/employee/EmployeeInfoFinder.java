package nts.uk.ctx.pereg.app.find.employee;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.dom.employeeinfo.service.EmployeeBusiness;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmployeeInfoFinder {

	@Inject
	private EmployeeDataMngInfoRepository empDataRepo;

	@Inject
	private EmployeeBusiness employeeBusiness;

	@Inject
	private UserRepository userRepo;

	@Inject
	private EmployeeDataMngInfoRepository empDataMngInfoRepo;

	@Inject
	private PersonRepository personRepository;

	public String getGenerateEmplCodeAndComId(String startLetters) {
		String ComId = AppContexts.user().companyId();
		String EmpCode = employeeBusiness.generateEmplCode(startLetters);
		return EmpCode == "" ? "" : ComId + EmpCode;
	}

	public void validateEmpInfo(EmpInfoDto empInfo) {

		Boolean isDuplicateEmpCode = !CollectionUtil.isEmpty(this.empDataRepo
				.getEmployeeNotDeleteInCompany(AppContexts.user().companyId(), empInfo.getEmployeeCode()));

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
		Boolean isDuplicateLoginId = !CollectionUtil.isEmpty(this.userRepo.getByLoginId(empInfo.getLoginId()));

		if (isDuplicateLoginId) {

			throw new BusinessException(new RawErrorMessage("Msg_757"));

		}

	}

	public String generateEmplCode(String startLetters) {
		String returnString = "";
		String companyId = AppContexts.user().companyId();
		String lastEmployeeCode = empDataMngInfoRepo.findLastEml(companyId, startLetters);
		returnString = generateCode(lastEmployeeCode, "Msg_505");
		return returnString;

	}

	public String generateCardNo(String startLetters) {

		String returnString = "";
		String companyId = AppContexts.user().companyId();
		String lastCardNo = personRepository.getLastCardNo(companyId, startLetters);
		returnString = generateCode(lastCardNo, "Msg_505");
		return returnString;

	}

	private String generateCode(String value, String errMsgId) {
		String returnString = "";
		if (value.equals(""))
			throw new BusinessException(errMsgId);
		else {
			value = value.trim();
			int length = value.length();
			// neeus chuỗi toàn là z hoặc Z
			if (value.matches("[zZ]+"))
				throw new BusinessException(errMsgId);
			// nếu chuỗi toàn là 9
			else if (value.matches("[9]+")) {
				String standard = "A";
				for (int i = 1; i < length - 1; i++)
					standard += "0";
				standard += "1";
				returnString = standard;
			} else {
				String increaseLetter = "", lastLetter = "";
				int descIndex = 1;
				boolean canContinue = true;
				do {
					lastLetter = Character.toString(value.charAt(length - descIndex));
					// nếu ký tự tăng là 9
					if (lastLetter.equals("9"))
						increaseLetter = "0";
					// nếu ký tự tăng là z
					else if (lastLetter.equals("z"))
						increaseLetter = "a";
					// nếu ký tự tăng là Z
					else if (lastLetter.equals("Z"))
						increaseLetter = "A";
					else
						increaseLetter = String.valueOf((char) (((int) lastLetter.charAt(0)) + 1));
					String preSub = (length - descIndex) >= 0 ? value.substring(0, length - descIndex)
							: value.substring(0);
					String posSub = descIndex > 1 ? value.substring(length - descIndex + 1) : "";
					value = preSub + increaseLetter + posSub;
					if (lastLetter.equals("9") || lastLetter.equals("z") || lastLetter.equals("Z")) {
						descIndex += 1;
						canContinue = true;
					} else
						canContinue = false;
				} while (canContinue);
				returnString = value;
			}
		}
		return returnString;
	}
}
