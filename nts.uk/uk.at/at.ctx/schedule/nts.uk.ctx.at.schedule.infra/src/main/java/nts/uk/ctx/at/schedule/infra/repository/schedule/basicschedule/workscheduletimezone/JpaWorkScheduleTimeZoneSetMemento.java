package nts.uk.ctx.at.schedule.infra.repository.schedule.basicschedule.workscheduletimezone;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.BounceAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.WorkScheduleTimeZoneSetMemento;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.workscheduletimezone.KscdtWorkScheduleTimeZone;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.workscheduletimezone.KscdtWorkScheduleTimeZonePK;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class JpaWorkScheduleTimeZoneSetMemento implements WorkScheduleTimeZoneSetMemento {
	private KscdtWorkScheduleTimeZone entity;

	public JpaWorkScheduleTimeZoneSetMemento(KscdtWorkScheduleTimeZone entity, String employeeId,
			GeneralDate generalDate) {
		if (entity.kscdtWorkScheduleTimeZonePk == null) {
			entity.kscdtWorkScheduleTimeZonePk = new KscdtWorkScheduleTimeZonePK();
		}
		this.entity = entity;
		this.entity.kscdtWorkScheduleTimeZonePk.sId = employeeId;
		this.entity.kscdtWorkScheduleTimeZonePk.date = generalDate;
	}

	@Override
	public void setScheduleCnt(int scheduleCnt) {
		this.entity.kscdtWorkScheduleTimeZonePk.scheduleCnt = scheduleCnt;
	}

	@Override
	public void setScheduleStartClock(TimeWithDayAttr scheduleStartClock) {
		this.entity.scheduleStartClock = scheduleStartClock.valueAsMinutes();
	}

	@Override
	public void setScheduleEndClock(TimeWithDayAttr scheduleEndClock) {
		this.entity.scheduleEndClock = scheduleEndClock.valueAsMinutes();
	}

	@Override
	public void setBounceAtr(BounceAtr bounceAtr) {
		this.entity.bounceAtr = bounceAtr.value;
	}

}
