package nts.uk.ctx.at.schedule.infra.repository.calendar;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.calendar.CalendarClass;
import nts.uk.ctx.at.schedule.dom.calendar.CalendarCompany;
import nts.uk.ctx.at.schedule.dom.calendar.CalendarCompanyRepository;
import nts.uk.ctx.at.schedule.dom.calendar.CalendarWorkplace;
import nts.uk.ctx.at.schedule.infra.entity.calendar.KsmmtCalendarClass;
import nts.uk.ctx.at.schedule.infra.entity.calendar.KsmmtCalendarClassPK;
import nts.uk.ctx.at.schedule.infra.entity.calendar.KsmmtCalendarCompany;
import nts.uk.ctx.at.schedule.infra.entity.calendar.KsmmtCalendarCompanyPK;
import nts.uk.ctx.at.schedule.infra.entity.calendar.KsmmtCalendarWorkplace;
import nts.uk.ctx.at.schedule.infra.entity.calendar.KsmmtCalendarWorkplacePK;

@Stateless
public class JpaCalendarCompanyRepository  extends JpaRepository implements CalendarCompanyRepository {
	
	private final String SELECT_FROM_COMPANY = "select c from KsmmtCalendarCompany c";
	private final String SELECT_ALL_COMPANY = SELECT_FROM_COMPANY
			+ " where c.ksmmtCalendarCompanyPK.companyId = :companyId";
	private final String SELECT_COMPANY_BY_DATE = SELECT_ALL_COMPANY
			+ " and c.ksmmtCalendarCompanyPK.dateId = :dateId";
	private final String SELECT_BY_YEAR_MONTH = SELECT_ALL_COMPANY + " and c.ksmmtCalendarCompanyPK.dateId >= :startYM and c.ksmmtCalendarCompanyPK.dateId <= :endYM";
	private final String DELETE_BY_YEAR_MONTH = "delete from KsmmtCalendarCompany c where c.ksmmtCalendarCompanyPK.companyId = :companyId"
			+ " and c.ksmmtCalendarCompanyPK.dateId >= :startYM and c.ksmmtCalendarCompanyPK.dateId <= :endYM";
	
	/**
	 * company
	 * @param entity
	 * @return
	 */
	
	// toDomanin calendar company
	private static CalendarCompany toDomainCalendarCompany(KsmmtCalendarCompany entity){
		val domain = CalendarCompany.createFromJavaType(
				entity.ksmmtCalendarCompanyPK.companyId,
				entity.ksmmtCalendarCompanyPK.dateId, 
				entity.workingDayAtr);
		return domain;
	}
	//toEntity calendar Company
	private static KsmmtCalendarCompany toEntityCalendarCompany(CalendarCompany domain){
		val entity = new KsmmtCalendarCompany();
		entity.ksmmtCalendarCompanyPK = new KsmmtCalendarCompanyPK(
												domain.getCompanyId(),
												domain.getDateId());
		entity.workingDayAtr = domain.getWorkingDayAtr().value;
		return entity;
	}
	
	/**
	 *  Calendar Company
	 */
	
	//get all calendar company
	@Override
	public List<CalendarCompany> getAllCalendarCompany(String companyId) {
		return this.queryProxy().query(SELECT_ALL_COMPANY,KsmmtCalendarCompany.class)
				.setParameter("companyId", companyId)
				.getList(c -> toDomainCalendarCompany(c));
	}
	//add calendar company
	@Override
	public void addCalendarCompany(CalendarCompany calendarCompany) {
		this.commandProxy().insert(toEntityCalendarCompany(calendarCompany));
		
	}
	//update calendar company
	@Override
	public void updateCalendarCompany(CalendarCompany calendarCompany) {
		KsmmtCalendarCompany calendarCom = toEntityCalendarCompany(calendarCompany);
		KsmmtCalendarCompany companyUpdate = this.queryProxy()
				.find(calendarCom.ksmmtCalendarCompanyPK, KsmmtCalendarCompany.class).get();
		companyUpdate.workingDayAtr = calendarCompany.getWorkingDayAtr().value;
		this.commandProxy().update(companyUpdate);
		
	}
	//delete calendar company
	@Override
	public void deleteCalendarCompany(String companyId,BigDecimal dateId) {
		KsmmtCalendarCompanyPK ksmmtCalendarCompanyPK = new KsmmtCalendarCompanyPK(
														companyId,dateId);
		this.commandProxy().remove(KsmmtCalendarCompany.class,ksmmtCalendarCompanyPK);
	}
	//find clendar company by dateId
	@Override
	public Optional<CalendarCompany> findCalendarCompanyByDate(String companyId, BigDecimal date) {
		return this.queryProxy().query(SELECT_COMPANY_BY_DATE,KsmmtCalendarCompany.class)
				.setParameter("companyId", companyId)
				.setParameter("dateId", date)
				.getSingle(c->toDomainCalendarCompany(c));
	}
	@Override
	public List<CalendarCompany> getCalendarCompanyByYearMonth(String companyId, String yearMonth) {
		return this.queryProxy().query(SELECT_BY_YEAR_MONTH, KsmmtCalendarCompany.class)
				.setParameter("companyId", companyId)
				.setParameter("startYM", Integer.valueOf(yearMonth+"01"))
				.setParameter("endYM", Integer.valueOf(yearMonth+"31"))
				.getList(x -> toDomainCalendarCompany(x));
	}
	@Override
	public void deleteCalendarCompanyByYearMonth(String companyId, String yearMonth) {
		this.getEntityManager().createQuery(DELETE_BY_YEAR_MONTH)
			.setParameter("companyId", companyId)
			.setParameter("startYM", Integer.valueOf(yearMonth+"01"))
			.setParameter("endYM", Integer.valueOf(yearMonth+"31")).executeUpdate();
	}

}
