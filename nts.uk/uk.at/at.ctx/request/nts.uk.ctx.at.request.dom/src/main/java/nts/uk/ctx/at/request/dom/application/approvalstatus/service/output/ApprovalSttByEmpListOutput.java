package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * 
 * @author Anh.BD
 *
 */
@Value
@AllArgsConstructor
public class ApprovalSttByEmpListOutput {
	List<DailyStatusOutput> listDailyStt;
	List<ApprovalStatusEmployeeOutput> listAppSttEmp;
}
