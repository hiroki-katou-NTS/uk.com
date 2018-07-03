package nts.uk.ctx.at.shared.infra.repository.remainingnumber.specialholiday;

import java.util.List;

import javax.ejb.Stateless;

import lombok.experimental.var;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.ScheduleRecordAtr;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.specialholiday.interim.KrcmtInterimSpeHoliday;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.specialholiday.interim.KrcmtInterimSpeHolidayPK;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
public class JpaInterimSpecialHolidayMngRepo extends JpaRepository implements InterimSpecialHolidayMngRepository{

	private String DELETE_BY_ID = "DELETE FROM KrcmtInterimSpeHoliday c"
			+ " WHERE c.pk.specialHolidayId = :specialHolidayId";
	private String QUERY_BY_ID = "SELECT c FROM KrcmtInterimSpeHoliday c"
			+ " WHERE c.pk.specialHolidayId = :specialHolidayId";
	private InterimSpecialHolidayMng toDomain(KrcmtInterimSpeHoliday c) {
		return new InterimSpecialHolidayMng(
				c.pk.specialHolidayId,
				c.pk.specialHolidayCode, 
				EnumAdaptor.valueOf(c.scheRecordAtr, ScheduleRecordAtr.class), 
				new UseTime(c.useTimes), 
				new UseDay(c.useDays));
	}
	@Override
	public void persistAndUpdateInterimSpecialHoliday(InterimSpecialHolidayMng domain) {
		KrcmtInterimSpeHolidayPK key = new KrcmtInterimSpeHolidayPK(domain.getSpecialHolidayId(), domain.getSpecialHolidayCode());
		KrcmtInterimSpeHoliday entity = this.getEntityManager().find(KrcmtInterimSpeHoliday.class, key);
		if(entity == null) {
			entity = new KrcmtInterimSpeHoliday();
			entity.pk.specialHolidayId = domain.getSpecialHolidayId();
			entity.pk.specialHolidayCode = domain.getSpecialHolidayCode();
			entity.scheRecordAtr = domain.getScheRecordAtr().value;
			entity.useDays = domain.getUseDays().v();
			entity.useTimes = domain.getUseTimes().v();
			this.getEntityManager().persist(entity);
		} else {
			entity.scheRecordAtr = domain.getScheRecordAtr().value;
			entity.useDays = domain.getUseDays().v();
			entity.useTimes = domain.getUseTimes().v();
			this.commandProxy().update(entity);
		}
		
	}
	@Override
	public void deleteSpecialHoliday(String specialId) {
		this.getEntityManager().createQuery(DELETE_BY_ID).setParameter("specialHolidayId", specialId).executeUpdate();	
	}
	@Override
	public List<InterimSpecialHolidayMng> findById(String mngId) {
		return this.queryProxy().query(QUERY_BY_ID, KrcmtInterimSpeHoliday.class)
				.setParameter("specialHolidayId", mngId)
				.getList(c -> toDomain(c));
	}

}
