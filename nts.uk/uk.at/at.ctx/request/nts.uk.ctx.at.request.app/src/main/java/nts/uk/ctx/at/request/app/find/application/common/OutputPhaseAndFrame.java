package nts.uk.ctx.at.request.app.find.application.common;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.at.request.app.find.application.common.appapprovalphase.AppApprovalPhaseDto;
import nts.uk.ctx.at.request.app.find.application.common.approvalframe.ApprovalFrameDto;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.OutputPhaseFrame;

@Value
public class OutputPhaseAndFrame {
	//thoong tin phase
	AppApprovalPhaseDto appApprovalPhaseDto;
	//list frame
	List<ApprovalFrameDto> listApprovalFrameDto;
	
		public static OutputPhaseAndFrame fromDomain(OutputPhaseFrame outputPhaseFrame) {
			return new OutputPhaseAndFrame(
					AppApprovalPhaseDto.fromDomain(outputPhaseFrame.getAppApprovalPhase()),
					outputPhaseFrame.getListApprovalFrame().stream().map(c -> ApprovalFrameDto.fromDomain(c))
					.collect(Collectors.toList())
					);
			
		}
}
