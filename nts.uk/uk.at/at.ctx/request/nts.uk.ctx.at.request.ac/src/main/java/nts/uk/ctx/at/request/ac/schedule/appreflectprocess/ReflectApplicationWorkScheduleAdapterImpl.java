package nts.uk.ctx.at.request.ac.schedule.appreflectprocess;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.reflect.ReflectApplicationWorkScheduleAdapter;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.ReflectApplicationWorkSchedulePub;

@Stateless
public class ReflectApplicationWorkScheduleAdapterImpl implements ReflectApplicationWorkScheduleAdapter {

	@Inject
	private ReflectApplicationWorkSchedulePub reflectApplicationWorkSchedulePub;

	@Override
	public Pair<Object, AtomTask> process(Object application, GeneralDate date, Object reflectStatus, 
			int preAppWorkScheReflectAttr) {
		return reflectApplicationWorkSchedulePub.process(application, date, reflectStatus, preAppWorkScheReflectAttr);
	}

}
