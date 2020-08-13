package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.dispatchedinformation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InputDispatchedInformation {

	String contractCode;
	String companyId;
	GeneralDate baseDate;
	boolean employeeCode;
	boolean employeeName;
	boolean expirationDate;
	boolean nameSelectedMaster;
	boolean classification1;
	boolean classification2;
	boolean classification3;
	boolean nameCompany;
	boolean address;
	boolean addressKana;
	boolean include;
	List<String> employeeIds;
}