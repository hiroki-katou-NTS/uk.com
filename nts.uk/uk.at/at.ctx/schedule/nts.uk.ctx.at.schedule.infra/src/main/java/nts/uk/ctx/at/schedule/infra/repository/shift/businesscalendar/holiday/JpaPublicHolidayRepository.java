/**
 * 9:59:33 AM Jun 13, 2017
 */
package nts.uk.ctx.at.schedule.infra.repository.shift.businesscalendar.holiday;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHolidayRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.holiday.KsmmtPublicHoliday;

/**
 * @author hungnm
 *
 */
@Stateless
public class JpaPublicHolidayRepository extends JpaRepository implements PublicHolidayRepository {

	private final String SELECT_BY_LISTDATE = "SELECT a FROM KsmmtPublicHoliday a WHERE a.ksmmtPublicHolidayPK.companyId = :companyId AND a.ksmmtPublicHolidayPK.date IN :lstDate";
	private final String SELECT_ALL = "SELECT a FROM KsmmtPublicHoliday a WHERE a.ksmmtPublicHolidayPK.companyId = :companyId";
	
	@Override
	public List<PublicHoliday> getHolidaysByListDate(String companyId, List<BigDecimal> lstDate) {
		return this.queryProxy().query(SELECT_BY_LISTDATE, KsmmtPublicHoliday.class)
				.setParameter("companyId", companyId).setParameter("lstDate", lstDate).getList().stream()
				.map(entity -> toDomain(entity)).collect(Collectors.toList());
	}
	
	@Override
	public List<PublicHoliday> getAllHolidays(String companyId) {
		return this.queryProxy().query(SELECT_ALL, KsmmtPublicHoliday.class)
				.setParameter("companyId", companyId).getList().stream()
				.map(entity -> toDomain(entity)).collect(Collectors.toList());
	}

	private PublicHoliday toDomain(KsmmtPublicHoliday entity) {
		return PublicHoliday.createFromJavaType(entity.ksmmtPublicHolidayPK.companyId, entity.ksmmtPublicHolidayPK.date,
				entity.holidayName);
	}

}
