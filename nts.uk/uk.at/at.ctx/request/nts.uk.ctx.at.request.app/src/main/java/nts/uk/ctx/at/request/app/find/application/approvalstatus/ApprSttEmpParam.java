package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprSttEmpParam {
	private String wkpID;
	
	private String startDate;
	
	private String endDate;
	
	private List<EmpPeriodParam> empPeriodLst;
}
