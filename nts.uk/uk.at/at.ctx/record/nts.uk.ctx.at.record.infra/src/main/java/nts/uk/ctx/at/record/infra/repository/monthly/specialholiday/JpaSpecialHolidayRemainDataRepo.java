package nts.uk.ctx.at.record.infra.repository.monthly.specialholiday;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.ActualSpecialLeave;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialHolidayRemainData;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialHolidayRemainDataRepository;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialLeavaRemainTime;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialLeave;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialLeaveGrantUseDay;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialLeaveRemain;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialLeaveRemainDay;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialLeaveUnDigestion;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialLeaveUseDays;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialLeaveUseNumber;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialLeaveUseTimes;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.UseNumber;
import nts.uk.ctx.at.record.infra.entity.monthly.specialholiday.KrcdtMonSpRemain;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
public class JpaSpecialHolidayRemainDataRepo extends JpaRepository implements SpecialHolidayRemainDataRepository{
	private String SQL_BY_YM_STATUS = "SELECT c FROM KrcdtMonSpRemain c"
			+ " WHERE c.pk.sid = :sid"
			+ " AND c.pk.ym = :ym"
			+ " AND c.closureStatus = :status";
	@Override
	public List<SpecialHolidayRemainData> getByYmStatus(String sid, YearMonth ym, ClosureStatus status) {
		return this.queryProxy().query(SQL_BY_YM_STATUS, KrcdtMonSpRemain.class)
				.setParameter("sid", sid)
				.setParameter("ym", ym.v())
				.setParameter("status", status.value)				
				.getList(c -> toDomain(c));
	}
	private SpecialHolidayRemainData toDomain(KrcdtMonSpRemain c) {
		ActualSpecialLeave actualSpecial = new ActualSpecialLeave(new SpecialLeaveRemain(new SpecialLeaveRemainDay(c.factRemainDays), Optional.of(new SpecialLeavaRemainTime(c.factRemainTimes))), 
				new SpecialLeaveRemain(new SpecialLeaveRemainDay(c.beforeFactRemainDays), Optional.of(new SpecialLeavaRemainTime(c.beforeFactRemainTimes))), 
				new SpecialLeaveUseNumber(new SpecialLeaveUseDays(new SpecialLeaveRemainDay(c.factUseDays), new SpecialLeaveRemainDay(c.beforeFactUseDays), Optional.of(new SpecialLeaveRemainDay(c.afterFactUseDays))), 
						new SpecialLeaveUseTimes(new UseNumber(c.factUseNumber), new SpecialLeavaRemainTime(c.factUsetimes), new SpecialLeavaRemainTime(c.beforeFactUseTimes), Optional.of(new SpecialLeavaRemainTime(c.afterFactUseTimes)))), 
				Optional.of(new SpecialLeaveRemain(new SpecialLeaveRemainDay(c.afterFactRemainDays), Optional.of(new SpecialLeavaRemainTime(c.afterFactRemainTimes)))));
		SpecialLeave specialLeave = new SpecialLeave(new SpecialLeaveRemain(new SpecialLeaveRemainDay(c.remainDays), Optional.of(new SpecialLeavaRemainTime(c.remainTimes))),
				new SpecialLeaveRemain(new SpecialLeaveRemainDay(c.beforeRemainDays), Optional.of(new SpecialLeavaRemainTime(c.beforeRemainTimes))),
				new SpecialLeaveUseNumber(new SpecialLeaveUseDays(new SpecialLeaveRemainDay(c.useDays), new SpecialLeaveRemainDay(c.beforeUseDays), Optional.of(new SpecialLeaveRemainDay(c.afterUseDays))), 
						new SpecialLeaveUseTimes(new UseNumber(c.useNumber), new SpecialLeavaRemainTime(c.useTimes), new SpecialLeavaRemainTime(c.beforeUseTimes), Optional.of(new SpecialLeavaRemainTime(c.afterUseTimes)))), 
				new SpecialLeaveUnDigestion(new SpecialLeaveRemainDay(c.notUseDays), Optional.of(new SpecialLeavaRemainTime(c.notUseTime))), 
				Optional.of(new SpecialLeaveRemain(new SpecialLeaveRemainDay(c.afterRemainDays), Optional.of(new SpecialLeavaRemainTime(c.afterRemainTimes)))));
		return new SpecialHolidayRemainData(c.pk.sid,
				c.pk.closureId,
				new DatePeriod(c.getClosureStartDate(), c.getClosureEndDate()),
				EnumAdaptor.valueOf(c.getClosureStatus(), ClosureStatus.class),
				c.pk.closureDay,
				new YearMonth(c.pk.ym),
				c.pk.specialHolidayCd,
				actualSpecial,
				specialLeave,
				c.grantAtr == 1 ? true : false,
				Optional.of(new SpecialLeaveGrantUseDay(c.grantDays)));
	}

}
