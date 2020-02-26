package nts.uk.ctx.hr.develop.ws.interview;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ParamEmployee {
	
	String companyID;
	int interviewCate;
	List<String> listEmployeeID;
	boolean getSubInterview; 
	boolean getDepartment; 
	boolean getPosition; 
	boolean getEmployment;
	
}
