package nts.uk.ctx.at.schedule.infra.repository.shift.businesscalendar.daycalendar;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarWorkPlaceRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarWorkplace;
import nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.daycalendar.KscmtCalendarWkp;
import nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.daycalendar.KscmtCalendarWkpPK;

@Stateless
public class JpaCalendarWorkPlaceRepository extends JpaRepository implements CalendarWorkPlaceRepository {
	private	static final String SELECT_FROM_WORKPLACE = "SELECT w FROM KscmtCalendarWkp w";
	private static final String SELECT_ALL_WORKPLACE = SELECT_FROM_WORKPLACE
			+ " WHERE w.kscmtCalendarWkpPK.workPlaceId = :workPlaceId ";
	private static final String SELECT_WORKPLACE_BY_DATE = SELECT_ALL_WORKPLACE
			+ " AND w.kscmtCalendarWkpPK.date = :date";
	private static final String SELECT_BY_YEAR_MONTH = SELECT_ALL_WORKPLACE 
			+ " AND w.kscmtCalendarWkpPK.date >= :startYM "
			+ " AND w.kscmtCalendarWkpPK.date <= :endYM";
	private static final String DELETE_BY_YEAR_MONTH = "delete FROM KscmtCalendarWkp w "
			+" WHERE w.kscmtCalendarWkpPK.workPlaceId = :workPlaceId"
			+" AND w.kscmtCalendarWkpPK.date >= :startYM "
			+" AND w.kscmtCalendarWkpPK.date <= :endYM";
	private static final String GET_LIST_BY_DATE_ATR = "SELECT a FROM KscmtCalendarWkp a"
			+ " WHERE a.kscmtCalendarWkpPK.workPlaceId = :workPlaceId"
			+ " AND a.kscmtCalendarWkpPK.date >= :date"
			+ " AND a.workingDayAtr = :workingDayAtr"
			+ " ORDER BY a.kscmtCalendarWkpPK.date asc";
	
	/**
	 * toDomain calendar workplace
	 * @param entity
	 * @return
	 */
	private static CalendarWorkplace toDomainCalendarWorkplace(KscmtCalendarWkp entity){
		val domain = CalendarWorkplace.createFromJavaType(
				entity.kscmtCalendarWkpPK.workPlaceId,
				entity.kscmtCalendarWkpPK.date,
				entity.workingDayAtr);
		return domain;
	}
	/**
	 * toEntity calendar workplace
	 * @param domain
	 * @return
	 */
	private static KscmtCalendarWkp toEntityCalendarWorkplace(CalendarWorkplace domain){
		val entity = new  KscmtCalendarWkp();
		entity.kscmtCalendarWkpPK = new KscmtCalendarWkpPK(
															domain.getWorkPlaceId(),
															domain.getDate());
		entity.workingDayAtr = domain.getWorkDayDivision().value;
		return entity;
	}
	/**
	 * get all calendar workplace
	 */
	@Override
	public List<CalendarWorkplace> getAllCalendarWorkplace(String workPlaceId) {
		return this.queryProxy().query(SELECT_ALL_WORKPLACE,KscmtCalendarWkp.class)
				.setParameter("workPlaceId", workPlaceId)
				.getList(c->toDomainCalendarWorkplace(c));
	}
	
	@Override
	public List<Integer> getCalendarWorkPlaceSetByYear(String workPlaceId, String year) {
		List<Integer> monthSet =  new ArrayList<>();
		for(int i=1;i<=12;i++){
			List<CalendarWorkplace> result = getCalendarWorkPlaceByYearMonth(workPlaceId, String.format(year+"%02d",i));
			if(!result.isEmpty()){
				monthSet.add(i);
			}
		}
		return monthSet;
	}

	/**
	 * add calendar workplace
	 */
	@Override
	public void addCalendarWorkplace(CalendarWorkplace calendarWorkplace) {
		this.commandProxy().insert(toEntityCalendarWorkplace(calendarWorkplace));
		
	}
	/**
	 * update calendar workplace
	 */
	@Override
	public void updateCalendarWorkplace(CalendarWorkplace calendarWorkplace) {
		KscmtCalendarWkp clendarWork = toEntityCalendarWorkplace(calendarWorkplace);
		KscmtCalendarWkp workplaceUpdate = this.queryProxy()
				.find(clendarWork.kscmtCalendarWkpPK, KscmtCalendarWkp.class).get();
		workplaceUpdate.workingDayAtr = calendarWorkplace.getWorkDayDivision().value;
		this.commandProxy().update(workplaceUpdate);
		
	}
	/**
	 * delete calendar workplace
	 */
	@Override
	public void deleteCalendarWorkplace(String workPlaceId,GeneralDate date) {
		KscmtCalendarWkpPK kscmtCalendarWkpPK = new KscmtCalendarWkpPK(
																		workPlaceId,
																		date);
		this.commandProxy().remove(KscmtCalendarWkp.class,kscmtCalendarWkpPK);
		
	}
	/**
	 * get  calendar workplace by date
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Optional<CalendarWorkplace> findCalendarWorkplaceByDate(String workPlaceId, GeneralDate date) {
		return this.queryProxy().query(SELECT_WORKPLACE_BY_DATE,KscmtCalendarWkp.class)
				.setParameter("workPlaceId", workPlaceId )
				.setParameter("date", date)
				.getSingle(c->toDomainCalendarWorkplace(c));
	}
	/**
	 * get all  calendar workplace by yearmonth
	 */
	@Override
	public List<CalendarWorkplace> getCalendarWorkPlaceByYearMonth(String workPlaceId, String yearMonth) {
		return this.queryProxy().query(SELECT_BY_YEAR_MONTH, KscmtCalendarWkp.class)
				.setParameter("workPlaceId", workPlaceId )
				.setParameter("startYM", GeneralDate.fromString((String.format(Integer.parseInt(yearMonth)/100 +"/" +"%02d",Integer.parseInt(yearMonth)%100) +"/01"),"yyyy/MM/dd"))
				.setParameter("endYM", GeneralDate.fromString((String.format(Integer.parseInt(yearMonth)/100 +"/" +"%02d",Integer.parseInt(yearMonth)%100) +"/31"),"yyyy/MM/dd"))
				.getList(x -> toDomainCalendarWorkplace(x));
	}
	/**
	 * delete all  calendar workplace by yearmonth
	 */
	@Override
	public void deleteCalendarWorkPlaceByYearMonth(String workPlaceId, String yearMonth) {
		this.getEntityManager().createQuery(DELETE_BY_YEAR_MONTH)
				.setParameter("workPlaceId", workPlaceId )
				.setParameter("startYM", GeneralDate.fromString((String.format(Integer.parseInt(yearMonth)/100 +"/" +"%02d",Integer.parseInt(yearMonth)%100) +"/01"),"yyyy/MM/dd"))
				.setParameter("endYM", GeneralDate.fromString((String.format(Integer.parseInt(yearMonth)/100 +"/" +"%02d",Integer.parseInt(yearMonth)%100) +"/31"),"yyyy/MM/dd"))
				.executeUpdate();
		
	}
	@Override
	public List<CalendarWorkplace> getLstByDateWorkAtr(String workPlaceId, GeneralDate date, int workingDayAtr) {
		return this.queryProxy().query(GET_LIST_BY_DATE_ATR,KscmtCalendarWkp.class)
				.setParameter("workPlaceId", workPlaceId)
				.setParameter("date", date)
				.setParameter("workingDayAtr", workingDayAtr)
				.getList(c->toDomainCalendarWorkplace(c));
	}
}
