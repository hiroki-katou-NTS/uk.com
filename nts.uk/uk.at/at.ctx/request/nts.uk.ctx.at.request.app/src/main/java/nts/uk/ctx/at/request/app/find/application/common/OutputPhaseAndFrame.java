package nts.uk.ctx.at.request.app.find.application.common;

import java.util.List;
import java.util.Optional;

import lombok.Value;
import nts.uk.ctx.at.request.app.find.application.common.appapprovalphase.AppApprovalPhaseDto;
import nts.uk.ctx.at.request.app.find.application.common.approvalframe.ApprovalFrameDto;

@Value
public class OutputPhaseAndFrame {
	//thoong tin phase
	AppApprovalPhaseDto appApprovalPhaseDto;
	//list frame
	List<ApprovalFrameDto> listApprovalFrameDto;
	//list ApproveAcceptedDto (nguoi da xac nhan)
	List<ApproveAcceptedDto> listApproveAcceptedDto;
}
