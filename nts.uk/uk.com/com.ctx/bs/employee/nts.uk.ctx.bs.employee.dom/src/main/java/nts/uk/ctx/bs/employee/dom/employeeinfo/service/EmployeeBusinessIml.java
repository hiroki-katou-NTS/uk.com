package nts.uk.ctx.bs.employee.dom.employeeinfo.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;

@Stateless
public class EmployeeBusinessIml implements EmployeeBusiness{

	@Inject
	private EmployeeRepository employeeRepository;
	
	@Override
	public String generateEmplCode(String startLetters) {
		String returnString = "";
		String lastEmployeeCode =  employeeRepository.findLastEml(startLetters);
		if(lastEmployeeCode.equals(""))
			throw new BusinessException("Msg_505");
		else{
			lastEmployeeCode = lastEmployeeCode.trim();
			int length = lastEmployeeCode.length();
			//neeus chuỗi toàn là z hoặc Z
			if(lastEmployeeCode.matches("[zZ]+"))
				throw new BusinessException("Msg_505");
			//nếu chuỗi toàn là 9
			else if(lastEmployeeCode.matches("[9]+")){
				String standard = "A";
				for(int i = 1; i < length - 1; i++)
					standard += "0";
				standard += "1";
				returnString = standard;
			}else{
			    String increaseLetter = "", lastLetter = "";
			    int descIndex = 1;
			    boolean canContinue = true;
			    do{
			      lastLetter = Character.toString(lastEmployeeCode.charAt(length - descIndex));
			      // nếu ký tự tăng là 9
			      if(lastLetter.equals("9")) increaseLetter = "0";
			      // nếu ký tự tăng là z
			      else if(lastLetter.equals("z")) increaseLetter = "a";
			        // nếu ký tự tăng là Z
			        else if(lastLetter.equals("Z")) increaseLetter = "A";
			        else increaseLetter = String.valueOf((char)(((int)lastLetter.charAt(0))+1));
			        String preSub = (length - descIndex) >= 0 ?lastEmployeeCode.substring(0, length - descIndex) 
			        : lastEmployeeCode.substring(0);
			        String posSub = descIndex > 1  ? lastEmployeeCode.substring(length - descIndex+1) : 
			        	"";
			        lastEmployeeCode = preSub + increaseLetter + posSub;
			      if(lastLetter.equals("9") || lastLetter.equals("z") || lastLetter.equals("Z")){
			        descIndex += 1;
			        canContinue = true;
			      }else canContinue = false;
			    }while(canContinue);
			    returnString = lastEmployeeCode;
			}
		}
		return returnString;
	}

	@Override
	public String generateCardNo(String startLetters) {
		// TODO Auto-generated method stub
		return null;
	}

}
