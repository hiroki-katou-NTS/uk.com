package nts.uk.ctx.at.function.ac.approvalroot;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.approvalroot.ApprovalRootRecordAdapter;
import nts.uk.ctx.workflow.pub.approvalroot.ApprovalRootPub;

@Stateless
public class ApprovalRootRecordAdapterImpl implements ApprovalRootRecordAdapter {
	@Inject
	private ApprovalRootPub rootPub;
	
	@Override
	public Map<String, List<String>> lstEmplUnregister(String cid, DatePeriod period, List<String> lstSid) {
		return rootPub.lstEmplUnregister(cid, period, lstSid);
	}

}
