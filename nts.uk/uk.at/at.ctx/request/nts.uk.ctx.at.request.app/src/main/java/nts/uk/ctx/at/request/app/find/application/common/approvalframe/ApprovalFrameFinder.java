package nts.uk.ctx.at.request.app.find.application.common.approvalframe;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.approvalframe.ApprovalFrameRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author hieult
 *
 */
@Stateless
public class ApprovalFrameFinder {

	@Inject
	private ApprovalFrameRepository approvalFrameRepository;
	
	public Optional<ApprovalFrameDto> findByCode() {
		String companyID = AppContexts.user().companyId();
		String phaseID = IdentifierUtil.randomUniqueId();
		String dispOrder = IdentifierUtil.randomUniqueId();
		return this.approvalFrameRepository.findByCode(companyID, phaseID,dispOrder  )
				.map(approvalFrame -> ApprovalFrameDto.fromDomain(approvalFrame));
	}
	public List<ApprovalFrameDto> getAllApproverByPhaseID(String phaseID ) {
		String companyID = AppContexts.user().companyId();
		return this.approvalFrameRepository.getAllApproverByPhaseID(companyID, phaseID)
				.stream().map(approvalFrame -> ApprovalFrameDto.fromDomain(approvalFrame)).collect(Collectors.toList());
	}
	
	public List<ApprovalFrameDto> getAllApproverByListPhaseID(List<String> listPhaseID ) {
		String companyID = AppContexts.user().companyId();
		return this.approvalFrameRepository.getListFrameByListPhase(companyID, listPhaseID)
				.stream().map(approvalFrame -> ApprovalFrameDto.fromDomain(approvalFrame)).collect(Collectors.toList());
	}
	
	
}
