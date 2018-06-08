package nts.uk.ctx.at.shared.infra.repository.remainingnumber.breakdayoffmng.interim;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakDayOffMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.DataManagementAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.OccurrenceDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.OccurrenceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.SelectedAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnUsedDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnUsedTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseTime;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.breakdayoff.interim.KrcmtInterimBreakDayOff;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.breakdayoff.interim.KrcmtInterimBreakMng;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.breakdayoff.interim.KrcmtInterimDayOffMng;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
public class JpaInterimBreakDayOffMngRepository extends JpaRepository implements InterimBreakDayOffMngRepository{
	
	private String QUERY_BREAK_MNG = "SELECT c FROM KrcmtInterimBreakDayOff c"
			+ " WHERE c.breakDayOffKey.breakMngId = :mngId"
			+ " AND c.breakMngAtr = :mngAtr";
	private String QUERY_DAYOFF_MNG = "SELECT c FROM KrcmtInterimBreakDayOff c"
			+ " WHERE c.breakDayOffKey.dayOffMngId = :mngId"
			+ " AND c.dayOffMngAtr = :mngAtr";
	private String QUERY_BY_EXPIRATIONDATE = "SELECT c FROM KrcmtInterimBreakMng c"
			+ " WHERE c.breakMngId IN breakMngIds"
			+ " AND c.unUsedDays > :unUsedDays"
			+ " AND c.expirationDate >= :startDate"
			+ " AND c.expirationDate <= :endDate";
	@Override
	public Optional<InterimBreakMng> getBreakManaBybreakMngId(String breakManaId) {
		return this.queryProxy().find(breakManaId, KrcmtInterimBreakMng.class)
				.map(x -> toDomainBreakMng(x));
	}
	private InterimBreakMng toDomainBreakMng(KrcmtInterimBreakMng x) {		
		return new InterimBreakMng(x.breakMngId, 
				new AttendanceTime(x.occurrenceTimes),
				x.expirationDate,
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
	@Override
	public Optional<InterimBreakDayOffMng> getBreakDayOffMng(String mngId, boolean breakDay, DataManagementAtr mngAtr) {
		return this.queryProxy().query(breakDay ? QUERY_BREAK_MNG : QUERY_DAYOFF_MNG, KrcmtInterimBreakDayOff.class)
				.setParameter("mngId", mngId)
				.setParameter("mngAtr", mngAtr.values)
				.getSingle()
				.map(x -> toDomainBreakDayoffMng(x));
	}
	private InterimBreakDayOffMng toDomainBreakDayoffMng(KrcmtInterimBreakDayOff x) {		
		return new InterimBreakDayOffMng(x.breakDayOffKey.breakMngId,
				EnumAdaptor.valueOf(x.breakMngAtr, DataManagementAtr.class),
				x.breakDayOffKey.dayOffMngId,
				EnumAdaptor.valueOf(x.dayOffMngAtr, DataManagementAtr.class),
				new UseTime(x.userTimes),
				new UseDay(x.userDays),
				EnumAdaptor.valueOf(x.selectedAtr, SelectedAtr.class));
	}
	@Override
	public List<InterimBreakMng> getByPeriod(List<String> mngId, Double unUseDays, DatePeriod dateData) {
		if(mngId.isEmpty()) {
			return Collections.emptyList();
		}
		return this.queryProxy().query(QUERY_BY_EXPIRATIONDATE, KrcmtInterimBreakMng.class)
				.setParameter("breakMngIds", mngId)
				.setParameter("unUsedDays", unUseDays)
				.setParameter("startDate", dateData.start())
				.setParameter("endDate", dateData.end())
				.getList(c -> toDomainBreakMng(c));
	}

}
