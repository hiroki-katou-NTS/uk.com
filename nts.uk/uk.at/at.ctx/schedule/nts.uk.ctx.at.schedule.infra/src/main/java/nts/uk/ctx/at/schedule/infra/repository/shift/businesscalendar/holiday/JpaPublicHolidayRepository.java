/**
 * 9:59:33 AM Jun 13, 2017
 */
package nts.uk.ctx.at.schedule.infra.repository.shift.businesscalendar.holiday;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHolidayRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.holiday.KscmtPublicHoliday;
import nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.holiday.KscmtPublicHolidayPK;


/**
 * @author hungnm
 *
 */
@Stateless
public class JpaPublicHolidayRepository extends JpaRepository implements PublicHolidayRepository {

	private static final String SELECT_BY_LISTDATE = "SELECT a FROM KscmtPublicHoliday a WHERE a.kscmtPublicHolidayPK.companyId = :companyId AND a.kscmtPublicHolidayPK.date IN :lstDate";
	private static final String SELECT_BY_DATE = "SELECT a FROM KscmtPublicHoliday a "
			+ " WHERE a.kscmtPublicHolidayPK.companyId = :companyId "
			+ " AND a.kscmtPublicHolidayPK.date = :date ";
	private static final String SELECT_ALL = "SELECT a FROM KscmtPublicHoliday a WHERE a.kscmtPublicHolidayPK.companyId = :companyId";
//	private static final String SELECT_SINGLE = "SELECT a FROM KscmtPublicHoliday a WHERE a.kscmtPublicHolidayPK.companyId = :companyID AND a.kscmtPublicHolidayPK.date = :date";
	private static final String SELECT_BY_SDATE_EDATE = "SELECT c FROM KscmtPublicHoliday c"
			+ " WHERE c.kscmtPublicHolidayPK.companyId = :companyId"
			+ " AND c.kscmtPublicHolidayPK.date >= :strDate"
			+ " AND c.kscmtPublicHolidayPK.date <= :endDate";

	@Override
	public List<PublicHoliday> getHolidaysByListDate(String companyId, List<GeneralDate> lstDate) {
		List<PublicHoliday> resultList = new ArrayList<>();
		CollectionUtil.split(lstDate, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(SELECT_BY_LISTDATE, KscmtPublicHoliday.class)
				.setParameter("companyId", companyId)
				.setParameter("lstDate", subList)
				.getList().stream()
				.map(entity -> toDomain(entity)).collect(Collectors.toList()));
		});
		return resultList;
	}

	@Override
	public List<PublicHoliday> getAllHolidays(String companyId) {
		return this.queryProxy().query(SELECT_ALL, KscmtPublicHoliday.class).setParameter("companyId", companyId)
				.getList().stream().map(entity -> toDomain(entity)).collect(Collectors.toList());
	}

	private PublicHoliday toDomain(KscmtPublicHoliday entity) {
		return PublicHoliday.createFromJavaType(entity.kscmtPublicHolidayPK.companyId, entity.kscmtPublicHolidayPK.date,
				entity.holidayName);
	}

	@Override
	public Optional<PublicHoliday> getHolidaysByDate(String companyID, GeneralDate date) {
		return this.queryProxy().query(SELECT_BY_DATE, KscmtPublicHoliday.class)
				.setParameter("companyId", companyID).setParameter("date", date).getSingle(c -> toDomain(c));
	}

	@Override
	public void remove(String companyID, GeneralDate date) {
		this.commandProxy().remove(KscmtPublicHoliday.class, new KscmtPublicHolidayPK(companyID, date));
		this.getEntityManager().flush();
	}

	@Override
	public void update(PublicHoliday publicHoliday) {
		KscmtPublicHoliday newEntity = toEntity(publicHoliday);
		KscmtPublicHoliday entity = this.queryProxy().find(newEntity.kscmtPublicHolidayPK, KscmtPublicHoliday.class)
				.get();
		entity.holidayName = newEntity.holidayName;

	}

	@Override
	public void add(PublicHoliday publicHoliday) {
		this.commandProxy().insert(toEntity(publicHoliday));

	}

	private KscmtPublicHoliday toEntity(PublicHoliday domain) {
		return new KscmtPublicHoliday(new KscmtPublicHolidayPK(domain.getCompanyId(), domain.getDate()),
				domain.getHolidayName().v());

	}
	/**
	 * get pHoliday While Date
	 * @param strDate
	 * @param companyId
	 * @param endDate
	 * @return
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<PublicHoliday> getpHolidayWhileDate(String companyId,GeneralDate strDate, GeneralDate endDate) {
		return this.queryProxy().query(SELECT_BY_SDATE_EDATE, KscmtPublicHoliday.class)
				.setParameter("companyId", companyId)
				.setParameter("strDate", strDate)
				.setParameter("endDate", endDate)
				.getList(c->toDomain(c));
	}
}
