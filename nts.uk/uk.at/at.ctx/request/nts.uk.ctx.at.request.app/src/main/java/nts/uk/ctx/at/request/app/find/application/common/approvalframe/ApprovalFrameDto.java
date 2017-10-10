package nts.uk.ctx.at.request.app.find.application.common.approvalframe;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.common.ApproveAcceptedDto;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ConfirmAtr;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAccepted;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.Reason;

/**
 * 
 * @author hieult
 *
 */
@Data
@AllArgsConstructor
public class ApprovalFrameDto {

	private String companyID;
	private String frameID ;
	private int dispOrder;
	
	private List<ApproveAcceptedDto> listApproveAcceptedDto;
	
	private String nameAll;
	
	private String approveAll;
	
	private String reasonAll;
	
	
	public static ApprovalFrameDto fromDomain(ApprovalFrame domain){
		
		return new ApprovalFrameDto (
				domain.getCompanyID(),
				domain.getFrameID(),
				domain.getDispOrder(),
				domain.getListApproveAccepted() == null ? null :domain.getListApproveAccepted().stream().map(x->ApproveAcceptedDto.fromDomain(x))
				.collect(Collectors.toList())
				,null,null,null);
	}
}
