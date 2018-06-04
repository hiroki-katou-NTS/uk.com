package nts.uk.ctx.at.shared.infra.repository.remainmng.specialholiday;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.remainmng.interimremain.primitive.UseDay;
import nts.uk.ctx.at.shared.dom.remainmng.interimremain.primitive.UseTime;
import nts.uk.ctx.at.shared.dom.remainmng.specialholidaymng.interim.InterimSpecialHolidayMng;
import nts.uk.ctx.at.shared.dom.remainmng.specialholidaymng.interim.InterimSpecialHolidayMngRepository;
import nts.uk.ctx.at.shared.dom.remainmng.specialholidaymng.interim.ScheduleRecordAtr;
import nts.uk.ctx.at.shared.infra.entity.remainmng.specialholiday.interim.KrcmtInterimSpeHoliday;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
public class JpaInterimSpecialHolidayMngRepo extends JpaRepository implements InterimSpecialHolidayMngRepository{
	
	private String QUERY_BY_SID_PERIOD = "SELECT c FROM KrcmtInterimSpeHoliday c"
			+ " WHERE c.pk.sid = :sid"
			+ " AND c.pk.ymd >= :startDate"
			+ " AND c.pk.ymd <= :endDate"
			+ " ORDER BY c.pk.ymd ASC";
	@Override
	public List<InterimSpecialHolidayMng> findBySidPeriod(String sId, DatePeriod dateData) {
		return this.queryProxy().query(QUERY_BY_SID_PERIOD, KrcmtInterimSpeHoliday.class)
				.setParameter("sid", sId)
				.setParameter("startDate", dateData.start())
				.setParameter("endDate", dateData.end())
				.getList(c -> toDomain(c));
	}
	private InterimSpecialHolidayMng toDomain(KrcmtInterimSpeHoliday c) {
		return new InterimSpecialHolidayMng(c.pk.sid, c.pk.ymd, c.pk.specialHolidayCode, 
				EnumAdaptor.valueOf(c.scheRecordAtr, ScheduleRecordAtr.class), 
				new UseTime(c.useTimes), 
				new UseDay(c.useDays));
	}

}
