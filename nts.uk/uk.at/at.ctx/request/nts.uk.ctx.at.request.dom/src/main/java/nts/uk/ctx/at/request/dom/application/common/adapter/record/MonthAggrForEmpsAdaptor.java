package nts.uk.ctx.at.request.dom.application.common.adapter.record;

import java.util.List;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.task.tran.AtomTask;

public interface MonthAggrForEmpsAdaptor {
	public List<AtomTask> aggregate(CacheCarrier cache, String cid, List<String> sids, boolean canAggrWhenLock);
}
