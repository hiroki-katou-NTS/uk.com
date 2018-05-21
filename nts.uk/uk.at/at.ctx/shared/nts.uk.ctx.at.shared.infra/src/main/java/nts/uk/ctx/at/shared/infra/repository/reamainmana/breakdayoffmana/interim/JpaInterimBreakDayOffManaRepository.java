package nts.uk.ctx.at.shared.infra.repository.reamainmana.breakdayoffmana.interim;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.reamainmana.breakdayoffmana.interim.InterimBreakDayOffManaRepository;
import nts.uk.ctx.at.shared.dom.reamainmana.breakdayoffmana.interim.InterimBreakMana;
import nts.uk.ctx.at.shared.dom.reamainmana.breakdayoffmana.interim.InterimDayOffMana;
import nts.uk.ctx.at.shared.dom.reamainmana.interimremain.primitive.OccurrenceDay;
import nts.uk.ctx.at.shared.dom.reamainmana.interimremain.primitive.OccurrenceTime;
import nts.uk.ctx.at.shared.dom.reamainmana.interimremain.primitive.RequiredDay;
import nts.uk.ctx.at.shared.dom.reamainmana.interimremain.primitive.RequiredTime;
import nts.uk.ctx.at.shared.dom.reamainmana.interimremain.primitive.UnOffsetDay;
import nts.uk.ctx.at.shared.dom.reamainmana.interimremain.primitive.UnOffsetTime;
import nts.uk.ctx.at.shared.dom.reamainmana.interimremain.primitive.UnUsedDay;
import nts.uk.ctx.at.shared.dom.reamainmana.interimremain.primitive.UnUsedTime;
import nts.uk.ctx.at.shared.infra.entity.reamainmana.breakdayoff.interim.KrcmtInterimBreakMana;
import nts.uk.ctx.at.shared.infra.entity.reamainmana.breakdayoff.interim.KrcmtInterimDayOffMana;
@Stateless
public class JpaInterimBreakDayOffManaRepository extends JpaRepository implements InterimBreakDayOffManaRepository{
	@Override
	public Optional<InterimBreakMana> getBreakManaBybreakManaId(String breakManaId) {
		return this.queryProxy().find(breakManaId, KrcmtInterimBreakMana.class)
				.map(x -> toDomainBreakMana(x));
	}
	private InterimBreakMana toDomainBreakMana(KrcmtInterimBreakMana x) {		
		return new InterimBreakMana(x.breakManaId, 
				new AttendanceTime(x.occurrenceTimes),
				x.expirationDays,
				new OccurrenceTime(x.occurrenceTimes), 
				new OccurrenceDay(x.occurrenceDays),
				new AttendanceTime(x.haftDayEquiTime),
				new UnUsedTime(x.unUsedTimes),
				new UnUsedDay(x.unUsedDays));
	}
	@Override
	public Optional<InterimDayOffMana> getDayoffById(String dayOffManaId) {
		return this.queryProxy().find(dayOffManaId, KrcmtInterimDayOffMana.class)
				.map(x -> toDomainDayoffMana(x));
	}
	private InterimDayOffMana toDomainDayoffMana(KrcmtInterimDayOffMana x) {
		return new InterimDayOffMana(x.dayOffManaId, new RequiredTime(x.requiredTimes),
				new RequiredDay(x.requiredDays),
				new UnOffsetTime(x.unOffSetTimes),
				new UnOffsetDay(x.unOffsetDays));
	}

}
