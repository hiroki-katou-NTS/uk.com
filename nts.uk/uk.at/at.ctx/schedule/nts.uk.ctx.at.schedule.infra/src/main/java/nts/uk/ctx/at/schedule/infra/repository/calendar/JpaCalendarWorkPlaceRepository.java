package nts.uk.ctx.at.schedule.infra.repository.calendar;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.calendar.CalendarWorkPlaceRepository;
import nts.uk.ctx.at.schedule.dom.calendar.CalendarWorkplace;
import nts.uk.ctx.at.schedule.infra.entity.calendar.KsmmtCalendarCompany;
import nts.uk.ctx.at.schedule.infra.entity.calendar.KsmmtCalendarWorkplace;
import nts.uk.ctx.at.schedule.infra.entity.calendar.KsmmtCalendarWorkplacePK;

@Stateless
public class JpaCalendarWorkPlaceRepository extends JpaRepository implements CalendarWorkPlaceRepository {
	private	final String SELECT_FROM_WORKPLACE = "select w from KsmmtCalendarWorkplace w";
	private final String SELECT_ALL_WORKPLACE = SELECT_FROM_WORKPLACE
			+ " where w.ksmmtCalendarWorkplacePK.workPlaceId = :workPlaceId ";
	private final String SELECT_WORKPLACE_BY_DATE = SELECT_ALL_WORKPLACE
			+ " and w.ksmmtCalendarWorkplacePK.dateId = :dateId";
	private final String SELECT_BY_YEAR_MONTH = SELECT_ALL_WORKPLACE + " and w.ksmmtCalendarWorkplacePK.dateId >= :startYM and w.ksmmtCalendarWorkplacePK.dateId <= :endYM";
	private final String DELETE_BY_YEAR_MONTH = "delete from KsmmtCalendarWorkplace w where w.ksmmtCalendarWorkplacePK.workPlaceId = :workPlaceId"
			+" and w.ksmmtCalendarWorkplacePK.dateId >= :startYM and w.ksmmtCalendarWorkplacePK.dateId <= :endYM";
	
	/**
	 * workplace
	 * @param entity
	 * @return
	 */
	//toDomain calendar workplace
	private static CalendarWorkplace toDomainCalendarWorkplace(KsmmtCalendarWorkplace entity){
		val domain = CalendarWorkplace.createFromJavaType(
				entity.ksmmtCalendarWorkplacePK.workPlaceId,
				entity.ksmmtCalendarWorkplacePK.dateId,
				entity.workingDayAtr);
		return domain;
	}
	//toEntity calendar workplace
	private static KsmmtCalendarWorkplace toEntityCalendarWorkplace(CalendarWorkplace domain){
		val entity = new  KsmmtCalendarWorkplace();
		entity.ksmmtCalendarWorkplacePK = new KsmmtCalendarWorkplacePK(
															domain.getWorkPlaceId(),
															domain.getDateId());
		entity.workingDayAtr = domain.getWorkingDayAtr().value;
		return entity;
	}
	
	@Override
	public List<CalendarWorkplace> getAllCalendarWorkplace(String workPlaceId) {
		return this.queryProxy().query(SELECT_ALL_WORKPLACE,KsmmtCalendarWorkplace.class)
				.setParameter("workPlaceId", workPlaceId)
				.getList(c->toDomainCalendarWorkplace(c));
	}

	
	@Override
	public void addCalendarWorkplace(CalendarWorkplace calendarWorkplace) {
		this.commandProxy().insert(toEntityCalendarWorkplace(calendarWorkplace));
		
	}

	@Override
	public void updateCalendarWorkplace(CalendarWorkplace calendarWorkplace) {
		KsmmtCalendarWorkplace clendarWork = toEntityCalendarWorkplace(calendarWorkplace);
		KsmmtCalendarWorkplace workplaceUpdate = this.queryProxy()
				.find(clendarWork.ksmmtCalendarWorkplacePK, KsmmtCalendarWorkplace.class).get();
		workplaceUpdate.workingDayAtr = calendarWorkplace.getWorkingDayAtr().value;
		this.commandProxy().update(workplaceUpdate);
		
	}

	@Override
	public void deleteCalendarWorkplace(String workPlaceId,BigDecimal dateId) {
		KsmmtCalendarWorkplacePK ksmmtCalendarWorkplacePK = new KsmmtCalendarWorkplacePK(
																		workPlaceId,
																		dateId);
		this.commandProxy().remove(KsmmtCalendarWorkplace.class,ksmmtCalendarWorkplacePK);
		
	}

	@Override
	public Optional<CalendarWorkplace> findCalendarWorkplaceByDate(String workPlaceId, BigDecimal dateId) {
		return this.queryProxy().query(SELECT_WORKPLACE_BY_DATE,KsmmtCalendarWorkplace.class)
				.setParameter("workPlaceId", workPlaceId )
				.setParameter("dateId", dateId)
				.getSingle(c->toDomainCalendarWorkplace(c));
	}
	@Override
	public List<CalendarWorkplace> getCalendarWorkPlaceByYearMonth(String workPlaceId, String yearMonth) {
		return this.queryProxy().query(SELECT_BY_YEAR_MONTH, KsmmtCalendarWorkplace.class)
				.setParameter("startYM", Integer.valueOf(yearMonth+"01"))
				.setParameter("endYM", Integer.valueOf(yearMonth+"31"))
				.getList(x -> toDomainCalendarWorkplace(x));
	}
	@Override
	public void deleteCalendarWorkPlaceByYearMonth(String workPlaceId, String yearMonth) {
		this.queryProxy().query(SELECT_BY_YEAR_MONTH, KsmmtCalendarWorkplace.class)
				.setParameter("startYM", Integer.valueOf(yearMonth+"01"))
				.setParameter("endYM", Integer.valueOf(yearMonth+"31"));
		
	}
}
