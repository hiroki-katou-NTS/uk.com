package nts.uk.ctx.at.request.app.find.application.common.appapprovalphase;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.at.request.app.find.application.common.approvalframe.ApprovalFrameDto;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalForm;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
@Value
public class AppApprovalPhaseDto {
	/** 会社ID */
	private String companyID;

	/** 申請ID */
	private String appID;

	/** フェーズID */
	private String phaseID;

	/** 承認形態 */
	private ApprovalForm approvalForm;

	/** 順序 */
	private int dispOrder;

	/** 承認区分 */
	private ApprovalAtr approvalATR;
	
	private List<ApprovalFrameDto> listFrame;
	
	public static AppApprovalPhaseDto fromDomain (AppApprovalPhase domain){
		return new AppApprovalPhaseDto(
				domain.getCompanyID(),
				domain.getAppID(),
				domain.getPhaseID(),
				domain.getApprovalForm(),
				domain.getDispOrder(),
				domain.getApprovalATR(),
				domain.getListFrame() == null ? null :domain.getListFrame().stream().map(x->ApprovalFrameDto.fromDomain(x))
				.collect(Collectors.toList()));
	}
}
