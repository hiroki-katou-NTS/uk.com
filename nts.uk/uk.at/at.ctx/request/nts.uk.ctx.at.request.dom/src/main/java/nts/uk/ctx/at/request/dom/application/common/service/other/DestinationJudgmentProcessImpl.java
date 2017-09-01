package nts.uk.ctx.at.request.dom.application.common.service.other;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.request.dom.application.common.service.other.output.ObjApproverRepresenterOutput;
/**
 * 3-2.送信先の判断処理
 * @author tutk
 *
 */
@Stateless
public class DestinationJudgmentProcessImpl implements DestinationJudgmentProcess {


	@Override
	public List<String> getDestinationJudgmentProcessService(
			List<ObjApproverRepresenterOutput> listApproverAndRepresenterSID) {
		List<String> listDestination = new ArrayList<>();
		if(listApproverAndRepresenterSID.size()==0) {
			return listDestination;
		}
		for(ObjApproverRepresenterOutput objApproverRepresenter : listApproverAndRepresenterSID ) {
			if(objApproverRepresenter.getRepresenter() == "Empty") {
				listDestination.add(objApproverRepresenter.getApprover());
			}
			
			else if(objApproverRepresenter.getRepresenter() == "Pass") {
				// do nothing
			} else {
				listDestination.add(objApproverRepresenter.getApprover());
				listDestination.add(objApproverRepresenter.getRepresenter());
			}
		}
		// TODO Auto-generated method stub
		return listDestination;
	}

}
