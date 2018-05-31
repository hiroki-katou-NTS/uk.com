package nts.uk.ctx.bs.employee.dom.employeeinfo.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.error.BusinessException;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmployeeBusinessService {

	@Inject
	private EmployeeDataMngInfoRepository employeeRepository;

	@Inject
	private PersonRepository personRepository;

	public String generateEmplCode(String startLetters) {
		String companyId = AppContexts.user().companyId();
		
		Optional<String> lastEmployeeCode = employeeRepository.findLastEml(companyId, startLetters);
		if (!lastEmployeeCode.isPresent()) {
			throw new BusinessException("Msg_505");
		}
		String returnString = generateCode(lastEmployeeCode.get());
		
		int maxLength = 6;
		while (returnString.length() < maxLength) {
			returnString += " ";
		}
		return returnString;
	}

	public String generateCardNo(String startLetters) {
		String returnString = "";
		String companyId = AppContexts.user().companyId();
		String lastCardNo = personRepository.getLastCardNo(companyId, startLetters);
		returnString = generateCode(lastCardNo);
		return returnString;
	}

	private String generateCode(String value) {
		int mixLength = value.length();
		boolean spaceAtBefore = checkSpaceAtBefore(value);
		boolean spaceAtAfter = checkSpaceAtAfter(value);
		String returnValue = null;
		value = value.trim();
		if (StringUtils.isNumeric(value)) {
			returnValue = doWithNumeric(value);
			
		} else {
			returnValue = doWithCharacter(value);
		}
		return restoreSpace(returnValue, mixLength, spaceAtBefore, spaceAtAfter);
		
	}
	
	private boolean checkSpaceAtBefore(String value) {
		if(value.charAt(0) == ' ') {
			return true;
		}
		return false;
	}
	
	private boolean checkSpaceAtAfter(String value) {
		if(value.charAt(value.length() -1) == ' ') {
			return true;
		}
		return false;
	}
	
	private String restoreSpace(String value, int mixLengt, boolean spaceAtBefore, boolean spaceAtAfter) {
		if (value.length() >= mixLengt) {
			return value;
		}
		if (spaceAtBefore){
			while(value.length() < mixLengt) {
				value = " " + value;
			}
		} else if (spaceAtAfter) {
			while(value.length() < mixLengt) {
				value += " ";
			}
		}
		return value;
	}
	
	private String doWithNumeric(String value) {
		int length = value.length();
		// value is all 999...
		if (value.matches("[9]+")) {
			String standard = "A";
			while (standard.length() < length - 1) {
				standard += "0";
			}
			standard += "1";
			return standard;
		} 
			
		int numbericValue = Integer.valueOf(value);
		numbericValue += 1;
		String upperValue = String.valueOf(numbericValue);
		while (upperValue.length() < value.length()) {
			upperValue = "0" + upperValue;
		}
		return upperValue;
		
	}
	
	private String doWithCharacter(String value) {
		// value is all z or Z
		if (value.matches("[zZ]+")) {
			throw new BusinessException("Msg_505");
		} 
		
		char increaseLetter = ' ';
		char[] valueAsArray = value.toCharArray();
		char[] resultArr = new char[valueAsArray.length];
		boolean canContinue = true;
		boolean beforeLast = false;
		for (int i = valueAsArray.length - 1; i >= 0; i--) {
			char currentChar = valueAsArray[i];
			if (canContinue) {
				canContinue = false;
				switch (currentChar) {
				case ' ':
					increaseLetter = ' ';
					canContinue = true;
					break;
				case '9':
					increaseLetter = '0';
					beforeLast = true;
					canContinue = true;
					break;
				// nếu ký tự tăng là z
				case 'z':
					increaseLetter = 'a';
					beforeLast = true;
					canContinue = true;
					break;
				// nếu ký tự tăng là Z
				case 'Z':
					increaseLetter = 'A';
					beforeLast = true;
					canContinue = true;
					break;
				default:
					increaseLetter = (char) ((int) currentChar + 1);
					break;
				}
				
				if (i != 0) {
					if (beforeLast && increaseLetter == ' ') {
						increaseLetter = '1';
						beforeLast = false;
						canContinue = false;
					}
					resultArr[i] = increaseLetter;
				} else {
					if (canContinue) {
						resultArr[i] = increaseLetter;
						for (int j = resultArr.length - 2; j > 0; j--) {
							char c = resultArr[j - 1];
							resultArr[j] = c;
						}
						if (beforeLast)
							resultArr[0] = '1';
					} else
						resultArr[i] = increaseLetter;
				}
			} else {
				resultArr[i] = valueAsArray[i];
			}

		}
		return String.valueOf(resultArr);
	}
	
}
