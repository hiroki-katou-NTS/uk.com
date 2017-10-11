package nts.uk.ctx.at.request.dom.application.common.service.other;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverRepresenterImport;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ObjApproverRepresenterOutput;
/**
 * 
 * @author tutk
 *
 */
@Stateless
public class DestinationJudgmentProcessImpl implements DestinationJudgmentProcess {


	@Override
	public List<String> getDestinationJudgmentProcessService(
			List<ApproverRepresenterImport> listApproverAndRepresenterSID) {
		List<String> listDestination = new ArrayList<>();
		if(listApproverAndRepresenterSID.size()==0) {
			return listDestination;
		}
		for(ApproverRepresenterImport approverRepresenterImport : listApproverAndRepresenterSID ) {
			if(approverRepresenterImport.getRepresenter() == "Empty") {
				listDestination.add(approverRepresenterImport.getApprover());
			}
			
			else if(approverRepresenterImport.getRepresenter() == "Pass") {
				// do nothing
			} else {
				listDestination.add(approverRepresenterImport.getApprover());
				listDestination.add(approverRepresenterImport.getRepresenter());
			}
		}
		// TODO Auto-generated method stub
		return listDestination;
	}

}
