package nts.uk.ctx.sys.assist.ac.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.sys.assist.dom.status.adapter.ApplicaionInforAdapter;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ApplicaionInforAdapterImpl implements ApplicaionInforAdapter {

//	@Inject
//	private ApplicationPub applicationPub;
	
	@Override
	public Map<String, List<String>> getAppInfor(List<String> empIds, List<String> reflections, DatePeriod period) {
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		return result;
	}

}
