package nts.uk.ctx.at.request.app.find.application.common.appapprovalphase;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.app.find.application.common.approvalframe.ApprovalFrameDto;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalForm;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppApprovalPhaseDto {
	/** 会社ID */
	private String companyID;

	/** 申請ID */
	private String appID;

	/** フェーズID */
	private String phaseID;

	/** 承認形態 */
	private int approvalForm;

	/** 順序 */
	private int dispOrder;

	/** 承認区分 */
	private int approvalATR;
	
	private List<ApprovalFrameDto> listFrame;
	
	public static AppApprovalPhaseDto fromDomain (AppApprovalPhase domain){
		return new AppApprovalPhaseDto(
				domain.getCompanyID(),
				domain.getAppID(),
				domain.getPhaseID(),
				domain.getApprovalForm().value,
				domain.getDispOrder(),
				domain.getApprovalATR().value,
				domain.getListFrame() == null ? null :domain.getListFrame().stream().map(x->ApprovalFrameDto.fromDomain(x))
				.collect(Collectors.toList()));
	}
	public static AppApprovalPhase toEntity(AppApprovalPhaseDto entity){
		return new AppApprovalPhase(
				entity.getCompanyID(),
				entity.getAppID(),
				entity.getPhaseID(),
				EnumAdaptor.valueOf(entity.getApprovalForm(), ApprovalForm.class) ,
				entity.getDispOrder(),
				EnumAdaptor.valueOf(entity.getApprovalATR(), ApprovalAtr.class) ,
				entity.getListFrame() == null ? null :entity.getListFrame().stream().map(x->ApprovalFrameDto.toEntity(x))
				.collect(Collectors.toList()));
	}
}
