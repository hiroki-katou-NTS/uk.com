package nts.uk.ctx.bs.employee.dom.employeeinfo.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmployeeBusinessIml implements EmployeeBusiness {

	@Inject
	private EmployeeDataMngInfoRepository employeeRepository;

	@Inject
	private PersonRepository personRepository;

	@Override
	public String generateEmplCode(String startLetters) {
		String returnString = "";
		String companyId = AppContexts.user().companyId();
		String lastEmployeeCode = employeeRepository.findLastEml(companyId, startLetters);
		returnString = generateCode(lastEmployeeCode, "Msg_505");
		return returnString;
	}

	@Override
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
