package nts.uk.ctx.at.schedule.infra.repository.calendar;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.calendar.CalendarClass;
import nts.uk.ctx.at.schedule.dom.calendar.CalendarCompany;
import nts.uk.ctx.at.schedule.dom.calendar.CalendarRepository;
import nts.uk.ctx.at.schedule.dom.calendar.CalendarWorkplace;
import nts.uk.ctx.at.schedule.infra.entity.calendar.KsmmtCalendarClass;
import nts.uk.ctx.at.schedule.infra.entity.calendar.KsmmtCalendarClassPK;
import nts.uk.ctx.at.schedule.infra.entity.calendar.KsmmtCalendarCompany;
import nts.uk.ctx.at.schedule.infra.entity.calendar.KsmmtCalendarCompanyPK;
import nts.uk.ctx.at.schedule.infra.entity.calendar.KsmmtCalendarWorkplace;
import nts.uk.ctx.at.schedule.infra.entity.calendar.KsmmtCalendarWorkplacePK;

@Stateless
public class JpaCalendarRepository  extends JpaRepository implements CalendarRepository {
	//query calendar company
	private final String SELECT_FROM_COMPANY = "select c from KsmmtCalendarCompany c";
	private final String SELECT_ALL_COMPANY = SELECT_FROM_COMPANY
			+ " where c.ksmmtCalendarCompanyPK.companyId = :companyId";
	private final String SELECT_COMPANY_BY_DATE = SELECT_ALL_COMPANY
			+ " and c.ksmmtCalendarCompanyPK.dateId = :dateId";
	//query calendar class
	private final String SELECT_FROM_CLASS = "select cl from KsmmtCalendarClass cl";
	private final String SELECT_ALL_CLASS = SELECT_FROM_CLASS
			+ " where cl.ksmmtCalendarClassPK.companyId = :companyId "
			+ " and cl.ksmmtClendarClassPK.classId = :classId ";
	private final String SELECT_CLASS_BY_DATE = SELECT_ALL_CLASS
			+ " and cl.ksmmtCalendarClassPK.dateId = :dateId";
	//query calendar Class 
	private	final String SELECT_FROM_WORKPLACE = "select w from KsmmtCalendarWorkplace w";
	private final String SELECT_ALL_WORKPLACE = SELECT_FROM_WORKPLACE
			+ " where w.ksmmtCalendarWorkplace.workPlaceId = :workPlaceId ";
	private final String SELECT_WORKPLACE_BY_DATE = SELECT_ALL_WORKPLACE
			+ " and w.ksmmtCalendarWorkplace.dateId = :dateId";
	
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
	 * class
	 * @param entity
	 * @return
	 */
	// toDomain calendar class
	private static CalendarClass toDomainCalendarClass(KsmmtCalendarClass entity){
		val domain = CalendarClass.createFromJavaType(
				entity.ksmmtCalendarClassPK.companyId,
				entity.ksmmtCalendarClassPK.classId,
				entity.ksmmtCalendarClassPK.dateId,
				entity.workingDayAtr);
		return domain;
	}
	//toEntity calendar Class
	private static KsmmtCalendarClass toEntityCalendarClass(CalendarClass domain){
		val entity = new KsmmtCalendarClass();
		entity.ksmmtCalendarClassPK = new KsmmtCalendarClassPK(
												domain.getCompanyId(),
												domain.getClassId().v(),
												domain.getDateId());
		entity.workingDayAtr = domain.getWorkingDayAtr().value;
		return entity;
	}
	
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
	/**
	 *  clendar Class
	 */
	//get all calendar Class
	@Override
	public List<CalendarClass> getAllCalendarClass(String companyId,String classId) {
		return this.queryProxy().query(SELECT_ALL_CLASS,KsmmtCalendarClass.class)
				.setParameter("companyId", companyId)
				.setParameter("classId", classId)
				.getList(c->toDomainCalendarClass(c));
	}

	@Override
	public void addCalendarClass(CalendarClass calendarClass) {
		this.commandProxy().insert(toEntityCalendarClass(calendarClass));
		
	}

	@Override
	public void updateCalendarClass(CalendarClass calendarClass) {
		KsmmtCalendarClass clendarCla = toEntityCalendarClass(calendarClass);
		KsmmtCalendarClass classUpdate = this.queryProxy()
				.find(clendarCla.ksmmtCalendarClassPK, KsmmtCalendarClass.class).get();
		classUpdate.workingDayAtr = calendarClass.getWorkingDayAtr().value;
		this.commandProxy().update(classUpdate);
		
	}

	@Override
	public void deleteCalendarClass(String companyId,String classId,BigDecimal dateId) {
		KsmmtCalendarClassPK ksmmtCalendarClassPK = new KsmmtCalendarClassPK(companyId,
														classId,
														dateId);  
		this.commandProxy().remove(KsmmtCalendarClass.class,ksmmtCalendarClassPK);
	}

	@Override
	public Optional<CalendarClass> findCalendarClassByDate(String companyId,String classId,BigDecimal dateId) {
		return this.queryProxy().query(SELECT_CLASS_BY_DATE,KsmmtCalendarClass.class)
				.setParameter("companyId",companyId)
				.setParameter("classId", classId)
				.setParameter("dateId", dateId)
				.getSingle(c->toDomainCalendarClass(c));
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

}
