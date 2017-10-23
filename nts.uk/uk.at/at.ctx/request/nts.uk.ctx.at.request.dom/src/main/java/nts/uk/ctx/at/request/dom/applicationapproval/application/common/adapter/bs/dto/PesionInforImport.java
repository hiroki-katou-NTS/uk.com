package nts.uk.ctx.at.request.dom.applicationapproval.application.common.adapter.bs.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PesionInforImport {
	/** The employee code. */
	private String employeeCode;

	/** The employee id. */
	private String employeeId;
	
	/** The employee name. */
	private String employeeName;

	
	/** The companyMail. */
	private String companyMail;
	
	/** The List Job Entry History. */
	private List<JobEntryHistoryImport> listJobEntryHist;
}
