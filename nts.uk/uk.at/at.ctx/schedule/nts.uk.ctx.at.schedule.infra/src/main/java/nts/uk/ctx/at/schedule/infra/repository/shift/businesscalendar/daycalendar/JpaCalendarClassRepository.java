package nts.uk.ctx.at.schedule.infra.repository.shift.businesscalendar.daycalendar;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarClass;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarClassRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.daycalendar.KscmtCalendarCls;
import nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.daycalendar.KsmmtCalendarClassPK;

@Stateless
public class JpaCalendarClassRepository extends JpaRepository implements CalendarClassRepository {
	private static final String SELECT_FROM_CLASS = "select l from KscmtCalendarCls l";
	private static final String SELECT_ALL_CLASS = SELECT_FROM_CLASS
			+ " where l.ksmmtCalendarClassPK.companyId = :companyId "
			+ " and l.ksmmtCalendarClassPK.classId = :classId ";
	private static final String SELECT_CLASS_BY_DATE = SELECT_ALL_CLASS
			+ " and l.ksmmtCalendarClassPK.date = :date";
	private static final String SELECT_BY_YEAR_MONTH = SELECT_ALL_CLASS + " and l.ksmmtCalendarClassPK.date >= :startYM and l.ksmmtCalendarClassPK.date <= :endYM";
	private static final String DELETE_BY_YEAR_MONTH = "delete from KscmtCalendarCls l where l.ksmmtCalendarClassPK.companyId = :companyId"
			+ " and l.ksmmtCalendarClassPK.classId = :classId "
			+" and l.ksmmtCalendarClassPK.date >= :startYM and l.ksmmtCalendarClassPK.date <= :endYM";
	/**
	 * class
	 * @param entity
	 * @return
	 */
	// toDomain calendar class
	private static CalendarClass toDomainCalendarClass(KscmtCalendarCls entity){
		val domain = CalendarClass.createFromJavaType(
				entity.ksmmtCalendarClassPK.companyId,
				entity.ksmmtCalendarClassPK.classId,
				entity.ksmmtCalendarClassPK.date,
				entity.workingDayAtr);
		return domain;
	}
	//toEntity calendar Class
	private static KscmtCalendarCls toEntityCalendarClass(CalendarClass domain){
		val entity = new KscmtCalendarCls();
		entity.ksmmtCalendarClassPK = new KsmmtCalendarClassPK(
												domain.getCompanyId(),
												domain.getClassId().v(),
												domain.getDate());
		entity.workingDayAtr = domain.getWorkDayDivision().value;
		return entity;
	}
	
	/**
	 *  clendar Class
	 */
	//get all calendar Class
	@Override
	public List<CalendarClass> getAllCalendarClass(String companyId,String classId) {
		return this.queryProxy().query(SELECT_ALL_CLASS,KscmtCalendarCls.class)
				.setParameter("companyId", companyId)
				.setParameter("classId", classId)
				.getList(c->toDomainCalendarClass(c));
	}
	
	@Override
	public List<Integer> getCalendarClassSetByYear(String companyId, String classId, String year) {
		List<Integer> monthSet =  new ArrayList<>();
		for(int i=1;i<=12;i++){
			List<CalendarClass> result = getCalendarClassByYearMonth(companyId, classId, String.format(year+"%02d",i));
			if(!result.isEmpty()){
				monthSet.add(i);
			}
		}
		return monthSet;
	}

	@Override
	public void addCalendarClass(CalendarClass calendarClass) {
		this.commandProxy().insert(toEntityCalendarClass(calendarClass));
		
	}

	@Override
	public void updateCalendarClass(CalendarClass calendarClass) {
		KscmtCalendarCls clendarCla = toEntityCalendarClass(calendarClass);
		KscmtCalendarCls classUpdate = this.queryProxy()
				.find(clendarCla.ksmmtCalendarClassPK, KscmtCalendarCls.class).get();
		classUpdate.workingDayAtr = calendarClass.getWorkDayDivision().value;
		this.commandProxy().update(classUpdate);
		
	}

	@Override
	public void deleteCalendarClass(String companyId,String classId,GeneralDate date) {
		KsmmtCalendarClassPK ksmmtCalendarClassPK = new KsmmtCalendarClassPK(companyId,
														classId,
														date);  
		this.commandProxy().remove(KscmtCalendarCls.class,ksmmtCalendarClassPK);
	}

	@Override
	public Optional<CalendarClass> findCalendarClassByDate(String companyId,String classId,GeneralDate date) {
		return this.queryProxy().query(SELECT_CLASS_BY_DATE,KscmtCalendarCls.class)
				.setParameter("companyId",companyId)
				.setParameter("classId", classId)
				.setParameter("date", date)
				.getSingle(c->toDomainCalendarClass(c));
	}
	@Override
	public List<CalendarClass> getCalendarClassByYearMonth(String companyId, String classId, String yearMonth) {
		return this.queryProxy().query(SELECT_BY_YEAR_MONTH, KscmtCalendarCls.class)
				.setParameter("companyId", companyId)
				.setParameter("classId", classId)
				.setParameter("startYM", GeneralDate.fromString((String.format(Integer.parseInt(yearMonth)/100 +"/" +"%02d",Integer.parseInt(yearMonth)%100) +"/01"),"yyyy/MM/dd"))
				.setParameter("endYM", GeneralDate.fromString((String.format(Integer.parseInt(yearMonth)/100 +"/" +"%02d",Integer.parseInt(yearMonth)%100) +"/31"),"yyyy/MM/dd"))
				.getList(x -> toDomainCalendarClass(x));
	}
	@Override
	public void deleteCalendarClassByYearMonth(String companyId, String classId, String yearMonth) {
		this.getEntityManager().createQuery(DELETE_BY_YEAR_MONTH)
				.setParameter("companyId", companyId)
				.setParameter("classId", classId)
				.setParameter("startYM", GeneralDate.fromString((String.format(Integer.parseInt(yearMonth)/100 +"/" +"%02d",Integer.parseInt(yearMonth)%100) +"/01"),"yyyy/MM/dd"))
				.setParameter("endYM", GeneralDate.fromString((String.format(Integer.parseInt(yearMonth)/100 +"/" +"%02d",Integer.parseInt(yearMonth)%100) +"/31"),"yyyy/MM/dd"))
				.executeUpdate();
	}
	
}
