/**
 * 9:59:33 AM Jun 13, 2017
 */
package nts.uk.ctx.at.schedule.infra.repository.holiday;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.holiday.PublicHoliday;
import nts.uk.ctx.at.schedule.dom.holiday.PublicHolidayRepository;
import nts.uk.ctx.at.schedule.infra.entity.holiday.KsmmtPublicHoliday;

/**
 * @author hungnm
 *
 */
@Stateless
public class JpaPublicHolidayRepository extends JpaRepository implements PublicHolidayRepository {

	private final String SELECT_BY_LISTDATE = "SELECT a FROM KsmmtPublicHoliday a WHERE a.ksmmtPublicHolidayPK.companyId = :companyId AND a.ksmmtPublicHolidayPK.date IN :lstDate";

	@Override
	public List<PublicHoliday> getHolidaysByListDate(String companyId, List<BigDecimal> lstDate) {
		return this.queryProxy().query(SELECT_BY_LISTDATE, KsmmtPublicHoliday.class)
				.setParameter("companyId", companyId).setParameter("lstDate", lstDate).getList().stream()
				.map(entity -> toDomain(entity)).collect(Collectors.toList());
	}

	private PublicHoliday toDomain(KsmmtPublicHoliday entity) {
		return PublicHoliday.createFromJavaType(entity.ksmmtPublicHolidayPK.companyId, entity.ksmmtPublicHolidayPK.date,
				entity.holidayName);
	}
}
