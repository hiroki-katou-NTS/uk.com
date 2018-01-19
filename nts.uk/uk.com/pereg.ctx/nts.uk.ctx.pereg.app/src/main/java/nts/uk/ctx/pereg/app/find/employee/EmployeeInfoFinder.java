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
		int maxLength = 6;
		String companyId = AppContexts.user().companyId();
		String lastEmployeeCode = empDataMngInfoRepo.findLastEml(companyId, startLetters);
		int length = lastEmployeeCode.length();
		if(length <maxLength){
			for(int i = 0; i < (maxLength - length); i++){
				lastEmployeeCode += " ";
			}
		}
		length = lastEmployeeCode.length();
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
				char increaseLetter = ' ';
				char [] arr = value.toCharArray();
				char [] resultArr = new char[arr.length];
				boolean con = true;
				boolean beforeLast = false;
				for(int i = arr.length -1; i >= 0; i--){
					char curChar = arr[i];
					if(con){
						con = false;
						switch(curChar){
							case ' ':
								increaseLetter = ' ';
								con = true;
								break;
							case '9':
								increaseLetter = '0';
								beforeLast = true;
								con = true;
								break;
							// nếu ký tự tăng là z
							case 'z':
								increaseLetter = 'a';
								beforeLast = true;
								con = true;
								break;
							// nếu ký tự tăng là Z
							case 'Z':
								increaseLetter = 'A';
								beforeLast = true;
								con = true;
								break;
							default:
								increaseLetter = (char)((int) curChar + 1);								
								break;
						}
						if(i != 0){
							if(beforeLast && increaseLetter == ' '){
								increaseLetter = '1';
								beforeLast = false;
								con = false;
							}
							resultArr[i] = increaseLetter;
						}
						else {
							if(con){
								resultArr[i] = increaseLetter;
								for(int j = resultArr.length - 2; j > 0; j--){
									char c = resultArr[j-1];
									resultArr[j] = c;
								}
								if(beforeLast)
									resultArr[0] = '1';
							}else resultArr[i] = increaseLetter;
						}
					}else {
						resultArr[i] = arr[i];
					}
					
				}
				returnString = String.valueOf(resultArr);
			}
		}
		return returnString;
	}
}
