package nts.uk.ctx.bs.employee.pub.person;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * The Class PersonInfoDto. 
 * Dto by Request List #01
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonInfoExport {
	
	/** The employee code. */
	private String employeeCode;

	/** The employee id. */
	private String employeeId;
	
	/** The employee name. */
	private String employeeName;

	
	/** The companyMail. */
	private String companyMail;
	
	/** The List Job Entry History. */
	private List<JobEntryHistoryExport> listJobEntryHist;
}
