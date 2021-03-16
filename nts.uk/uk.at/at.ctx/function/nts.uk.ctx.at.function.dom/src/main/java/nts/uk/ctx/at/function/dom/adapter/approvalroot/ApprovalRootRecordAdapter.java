package nts.uk.ctx.at.function.dom.adapter.approvalroot;

import java.util.List;
import java.util.Map;

import nts.arc.time.calendar.period.DatePeriod;

public interface ApprovalRootRecordAdapter {
	
	Map<String, List<String>> lstEmplUnregister(String cid, DatePeriod period, List<String> lstSid);

}
