package nts.uk.ctx.at.request.dom.application.common.adapter.schedule.reflect;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;

public interface ReflectApplicationWorkScheduleAdapter {
	
	public Pair<Object, AtomTask> process(Object application, GeneralDate date, Object reflectStatus, int preAppWorkScheReflectAttr);
	
}
