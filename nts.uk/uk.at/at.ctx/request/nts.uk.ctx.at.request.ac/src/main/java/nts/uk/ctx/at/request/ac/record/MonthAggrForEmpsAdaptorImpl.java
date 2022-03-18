package nts.uk.ctx.at.request.ac.record;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.pub.monthly.MonthlyAggregateForEmployeesPub;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.MonthAggrForEmpsAdaptor;

@Stateless
public class MonthAggrForEmpsAdaptorImpl implements MonthAggrForEmpsAdaptor {
	
	@Inject
	private MonthlyAggregateForEmployeesPub monthlyAggregateForEmployeesPub;

	@Override
	public List<AtomTask> aggregate(CacheCarrier cache, String cid, List<String> sids, boolean canAggrWhenLock) {
		return monthlyAggregateForEmployeesPub.aggregate(cache, cid, sids, canAggrWhenLock);
	}

}
