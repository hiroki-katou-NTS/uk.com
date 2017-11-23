package nts.uk.ctx.at.request.app.command.application.workchange;

import java.util.List;
import java.util.stream.Collectors;

import nts.arc.enums.EnumAdaptor;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.app.command.application.common.appapprovalphase.AppApprovalPhaseCmd;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalForm;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAccepted;

public class ApprovalPhaseUtils {
	
	/**
	 * Convert Phase command list to Approve Phase list
	 * 
	 * @param List<AppApprovalPhaseCmd> : Phase list
	 * @param companyId: 会社ID
	 * @param appID: 申請ID
	 * @return
	 */
	public static List<AppApprovalPhase> convertToDomain(List<AppApprovalPhaseCmd> approvalPhaseCommand,
			String companyId, String appID) {
		List<AppApprovalPhase> listAppApprovalPhase = approvalPhaseCommand.stream()
				.map(appApprovalPhaseCmd -> new AppApprovalPhase(companyId, appID, IdentifierUtil.randomUniqueId(),
						EnumAdaptor.valueOf(appApprovalPhaseCmd.approvalForm, ApprovalForm.class),
						appApprovalPhaseCmd.dispOrder,
						EnumAdaptor.valueOf(appApprovalPhaseCmd.approvalATR, ApprovalAtr.class),
						appApprovalPhaseCmd.getListFrame().stream()
								.map(approvalFrame -> new ApprovalFrame(companyId, IdentifierUtil.randomUniqueId(),
										approvalFrame.dispOrder, approvalFrame.listApproveAccepted.stream()
												.map(approveAccepted -> ApproveAccepted.createFromJavaType(companyId,
														IdentifierUtil.randomUniqueId(), approveAccepted.approverSID,
														ApprovalAtr.UNAPPROVED.value, approveAccepted.confirmATR, null,
														approveAccepted.reason, approveAccepted.representerSID))
												.collect(Collectors.toList())))
								.collect(Collectors.toList())))
				.collect(Collectors.toList());
		//
		listAppApprovalPhase.forEach(appApprovalPhase -> {
			appApprovalPhase.setAppID(appID);
			String phaseID = appApprovalPhase.getPhaseID();
			appApprovalPhase.setPhaseID(phaseID);
			appApprovalPhase.getListFrame().forEach(approvalFrame -> {
				String frameID = approvalFrame.getFrameID();
				approvalFrame.setFrameID(frameID);
				approvalFrame.getListApproveAccepted().forEach(appAccepted -> {
					String appAcceptedID = appAccepted.getAppAcceptedID();
					appAccepted.setAppAcceptedID(appAcceptedID);
				});
			});
		});

		return listAppApprovalPhase;
	}
}
