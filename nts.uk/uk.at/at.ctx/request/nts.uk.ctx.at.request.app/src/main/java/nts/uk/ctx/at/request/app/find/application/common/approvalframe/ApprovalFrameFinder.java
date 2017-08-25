package nts.uk.ctx.at.request.app.find.application.common.approvalframe;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrameRepository;

/**
 * 
 * @author hieult
 *
 */
@Stateless
public class ApprovalFrameFinder {

	@Inject
	private ApprovalFrameRepository approvalFrameRepository;
	
	
	
}
