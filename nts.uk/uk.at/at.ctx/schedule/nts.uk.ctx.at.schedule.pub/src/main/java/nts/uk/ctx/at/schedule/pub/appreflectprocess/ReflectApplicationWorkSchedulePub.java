package nts.uk.ctx.at.schedule.pub.appreflectprocess;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;

public interface ReflectApplicationWorkSchedulePub {

	//[RQ666]申請を勤務予定へ反映する
	public Pair<Object, AtomTask> process(Object application, GeneralDate date, Object reflectStatus,
			int preAppWorkScheReflectAttr);
	
}
