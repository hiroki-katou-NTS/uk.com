package nts.uk.ctx.at.shared.infra.repository.remainmng.breakdayoffmng.interim;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainmng.breakdayoffmng.interim.InterimBreakDayOffMngRepository;
import nts.uk.ctx.at.shared.dom.remainmng.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainmng.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainmng.interimremain.primitive.OccurrenceDay;
import nts.uk.ctx.at.shared.dom.remainmng.interimremain.primitive.OccurrenceTime;
import nts.uk.ctx.at.shared.dom.remainmng.interimremain.primitive.RequiredDay;
import nts.uk.ctx.at.shared.dom.remainmng.interimremain.primitive.RequiredTime;
import nts.uk.ctx.at.shared.dom.remainmng.interimremain.primitive.UnOffsetDay;
import nts.uk.ctx.at.shared.dom.remainmng.interimremain.primitive.UnOffsetTime;
import nts.uk.ctx.at.shared.dom.remainmng.interimremain.primitive.UnUsedDay;
import nts.uk.ctx.at.shared.dom.remainmng.interimremain.primitive.UnUsedTime;
import nts.uk.ctx.at.shared.infra.entity.remainmng.breakdayoff.interim.KrcmtInterimBreakMng;
import nts.uk.ctx.at.shared.infra.entity.remainmng.breakdayoff.interim.KrcmtInterimDayOffMng;
@Stateless
public class JpaInterimBreakDayOffMngRepository extends JpaRepository implements InterimBreakDayOffMngRepository{
	@Override
	public Optional<InterimBreakMng> getBreakManaBybreakMngId(String breakManaId) {
		return this.queryProxy().find(breakManaId, KrcmtInterimBreakMng.class)
				.map(x -> toDomainBreakMng(x));
	}
	private InterimBreakMng toDomainBreakMng(KrcmtInterimBreakMng x) {		
		return new InterimBreakMng(x.breakMngId, 
				new AttendanceTime(x.occurrenceTimes),
				x.expirationDays,
				new OccurrenceTime(x.occurrenceTimes), 
				new OccurrenceDay(x.occurrenceDays),
				new AttendanceTime(x.haftDayEquiTime),
				new UnUsedTime(x.unUsedTimes),
				new UnUsedDay(x.unUsedDays));
	}
	@Override
	public Optional<InterimDayOffMng> getDayoffById(String dayOffManaId) {
		return this.queryProxy().find(dayOffManaId, KrcmtInterimDayOffMng.class)
				.map(x -> toDomainDayoffMng(x));
	}
	private InterimDayOffMng toDomainDayoffMng(KrcmtInterimDayOffMng x) {
		return new InterimDayOffMng(x.dayOffMngId, new RequiredTime(x.requiredTimes),
				new RequiredDay(x.requiredDays),
				new UnOffsetTime(x.unOffSetTimes),
				new UnOffsetDay(x.unOffsetDays));
	}

}
