package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApprovalRootStateImportDto;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttConfirmEmpMonthDay;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.DailyConfirmOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.PhaseApproverStt;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprSttConfirmEmpMonthDayDto {
	private boolean monthConfirm;
	private Integer monthApproval;
	private ApprovalRootStateImportDto approvalRootStateMonth;
	private List<PhaseApproverStt> monthApprovalLst;
	private List<DailyConfirmOutput> listDailyConfirm;
	private List<ApprovalRootStateImportDto> approvalRootStateDayLst;
	private Map<GeneralDate, List<PhaseApproverStt>> dayApprovalMap;
	
	public static ApprSttConfirmEmpMonthDayDto fromDomain(ApprSttConfirmEmpMonthDay apprSttConfirmEmpMonthDay) {
		return new ApprSttConfirmEmpMonthDayDto(
				apprSttConfirmEmpMonthDay.isMonthConfirm(), 
				apprSttConfirmEmpMonthDay.getMonthApproval(), 
				apprSttConfirmEmpMonthDay.getApprovalRootStateMonth() == null ? null : ApprovalRootStateImportDto.fromDomain(apprSttConfirmEmpMonthDay.getApprovalRootStateMonth()), 
				apprSttConfirmEmpMonthDay.getMonthApprovalLst(), 
				apprSttConfirmEmpMonthDay.getListDailyConfirm(), 
				apprSttConfirmEmpMonthDay.getApprovalRootStateDayLst().stream().map(x -> ApprovalRootStateImportDto.fromDomain(x)).collect(Collectors.toList()), 
				apprSttConfirmEmpMonthDay.getDayApprovalMap());
	}
}
