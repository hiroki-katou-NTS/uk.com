package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author Anh.BD
 *
 */
@Value
@AllArgsConstructor
public class ApprovalSttByEmpListOutput {
	String empId;
	String empName;
	List<DailyStatus> listDaily;
	private GeneralDate startDate;
	private GeneralDate endDate;
}
