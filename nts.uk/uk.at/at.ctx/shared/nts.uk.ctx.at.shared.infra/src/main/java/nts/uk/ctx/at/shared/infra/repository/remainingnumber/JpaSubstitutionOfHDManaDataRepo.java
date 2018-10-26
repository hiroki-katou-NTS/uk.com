package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.paymana.KrcmtSubOfHDManaData;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.subhdmana.KrcmtComDayoffMaData;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class JpaSubstitutionOfHDManaDataRepo extends JpaRepository implements SubstitutionOfHDManaDataRepository {
	
	private static final String QUERY_BY_SID_CID_HOLIDAYDATE = "SELECT p FROM KrcmtSubOfHDManaData p WHERE p.cID = :cid AND p.sID =:employeeId AND p.dayOff = :holidayDate";
	
	private static final String QUERY_BYSID = "SELECT s FROM KrcmtSubOfHDManaData s WHERE s.sID = :sid AND s.cID = :cid";

	private static final String QUERY_BYSID_REM_COD = String.join(" ", QUERY_BYSID, "AND s.remainDays > 0");

	private static final String QUERY_BY_SID_DATEPERIOD = "SELECT s FROM KrcmtSubOfHDManaData s WHERE s.sID = :sid "
			+ " AND (s.remainDays > :remainDays OR s.subOfHDID in "
			+ "(SELECT ps.krcmtPayoutSubOfHDManaPK.subOfHDID FROM KrcmtPayoutSubOfHDMana ps WHERE ps.krcmtPayoutSubOfHDManaPK.payoutId =:payoutID))";

	private static final String QUERY_BY_SID_REMAIN_AND_IN_PAYOUT = "SELECT s FROM KrcmtSubOfHDManaData s WHERE s.sID = :sid AND (s.remainDays <> 0 OR s.subOfHDID in "
			+ "(SELECT ps.krcmtPayoutSubOfHDManaPK.subOfHDID FROM KrcmtPayoutSubOfHDMana ps inner join KrcmtPayoutManaData p on p.payoutId = ps.krcmtPayoutSubOfHDManaPK.payoutId where p.sID = :sid AND p.stateAtr = 0))";

	private static final String QUERY_BY_SID_PERIOD_AND_IN_PAYOUT = "SELECT s FROM KrcmtSubOfHDManaData s WHERE s.sID = :sid AND ((s.dayOff >= :startDate AND s.dayOff <= :endDate) OR s.subOfHDID in "
			+ "(SELECT ps.krcmtPayoutSubOfHDManaPK.subOfHDID FROM KrcmtPayoutSubOfHDMana ps inner join KrcmtPayoutManaData p on p.payoutId = ps.krcmtPayoutSubOfHDManaPK.payoutId where p.sID = :sid AND p.dayOff >= :startDate AND p.dayOff <= :endDate))";
	
	private static final String DELETE_QUERY = "DELETE FROM KrcmtSubOfHDManaData a WHERE a.subOfHDID = :subOfHDID";
	
	private static final String QUERY_DELETE_SUB = "DELETE FROM KrcmtPayoutSubOfHDMana p WHERE p.krcmtPayoutSubOfHDManaPK.subOfHDID = :subOfHDID ";

	private static final String QUERY_BY_SID_DATE_PERIOD = "SELECT c FROM KrcmtSubOfHDManaData c"
			+ " WHERE c.sID = :sid"
			+ " AND c.dayOff >= :startDate"
			+ " AND c.dayOff <= :endDate";
	private static final String QUERY_BY_SID_REMAINDAYS = "SELECT c FROM KrcmtSubOfHDManaData c"
			+ " WHERE c.sID = :sid"
			+ " AND c.remainDays > :remainDays";
	private static final String QUERY_BY_SID_HOLIDAY = "SELECT c FROM KrcmtSubOfHDManaData c"
			+ " WHERE c.sID = :employeeId" + " AND c.unknownDate = :unknownDate" + " AND c.dayOff >= :startDate";
	private String QUERY_BYSID_DATE = QUERY_BYSID + " AND s.dayOff < :dayOff";
	private String QUERY_BYSID_UNOFFSET = "SELECT s FROM KrcmtSubOfHDManaData s WHERE  s.cID = :cid "
			+ " AND s.sID = :sid"
			+ " AND (s.dayOff < :dayOff OR s.dayOff is null)"
			+ " AND s.remainDays > :remainDays";
	@Override
	public List<SubstitutionOfHDManagementData> getBySidDate(String cid, String sid, GeneralDate ymd) {
		List<KrcmtSubOfHDManaData> list = this.queryProxy().query(QUERY_BYSID_DATE, KrcmtSubOfHDManaData.class)
				.setParameter("sid", sid)
				.setParameter("cid", cid)
				.setParameter("dayOff", ymd)
				.getList();
		return list.stream().map(i -> toDomain(i)).collect(Collectors.toList());
	}

	
	@Override
	public List<SubstitutionOfHDManagementData> getBysiD(String cid, String sid) {
		List<KrcmtSubOfHDManaData> list = this.queryProxy().query(QUERY_BYSID, KrcmtSubOfHDManaData.class)
				.setParameter("sid", sid).setParameter("cid", cid).getList();
		return list.stream().map(i -> toDomain(i)).collect(Collectors.toList());
	}

	@Override
	public List<SubstitutionOfHDManagementData> getBysiDRemCod(String cid, String sid) {
		List<KrcmtSubOfHDManaData> list = this.queryProxy().query(QUERY_BYSID_REM_COD, KrcmtSubOfHDManaData.class)
				.setParameter("sid", sid).setParameter("cid", cid).getList();
		return list.stream().map(i -> toDomain(i)).collect(Collectors.toList());
	}

	/**
	 * Convert to domain
	 * 
	 * @param entity
	 * @return
	 */
	private SubstitutionOfHDManagementData toDomain(KrcmtSubOfHDManaData entity) {
		return new SubstitutionOfHDManagementData(entity.subOfHDID, entity.cID, entity.sID, entity.unknownDate,
				entity.dayOff, entity.requiredDays, entity.remainDays);
	}

	@Override
	public void create(SubstitutionOfHDManagementData domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	private KrcmtSubOfHDManaData toEntity(SubstitutionOfHDManagementData domain) {
		KrcmtSubOfHDManaData entity = new KrcmtSubOfHDManaData();
		entity.subOfHDID = domain.getSubOfHDID();
		entity.sID = domain.getSID();
		entity.cID = domain.getCid();
		entity.unknownDate = domain.getHolidayDate().isUnknownDate();
		if (domain.getHolidayDate().getDayoffDate().isPresent()) {
			entity.dayOff = domain.getHolidayDate().getDayoffDate().get();
		}
		entity.requiredDays = domain.getRequiredDays().v();
		entity.remainDays = domain.getRemainDays().v();
		return entity;
	}

	@Override
	public void deletePayoutSubOfHDMana(String subOfHDID) {
		this.getEntityManager().createQuery(QUERY_DELETE_SUB).setParameter("subOfHDID", subOfHDID).executeUpdate();
	}

	@Override
	public void delete(String subOfHDID) {
		Optional<KrcmtSubOfHDManaData> entity = this.queryProxy().find(subOfHDID, KrcmtSubOfHDManaData.class);
		if(entity.isPresent()){
			this.getEntityManager().createQuery(DELETE_QUERY).setParameter("subOfHDID", subOfHDID).executeUpdate();
		}else{
			throw new  BusinessException("Msg_198");
		}
	}

	@Override
	public void update(SubstitutionOfHDManagementData domain) {
		this.commandProxy().update(toEntity(domain));

	}

	public Optional<SubstitutionOfHDManagementData> findByID(String Id) {
		String QUERY_BY_ID = "SELECT s FROM KrcmtSubOfHDManaData s WHERE s.subOfHDID = :subOfHDID";
		Optional<KrcmtSubOfHDManaData> entity = this.queryProxy().query(QUERY_BY_ID, KrcmtSubOfHDManaData.class).setParameter("subOfHDID", Id).getSingle();
		if (entity.isPresent()) {
			return Optional.ofNullable(toDomain(entity.get()));
		}
		return Optional.empty();
	}


	public List<SubstitutionOfHDManagementData> getBySidDatePeriod(String sid, String payoutID, Double remainDays) {
		List<KrcmtSubOfHDManaData> listSubOfHD = this.queryProxy()
				.query(QUERY_BY_SID_DATEPERIOD, KrcmtSubOfHDManaData.class).setParameter("sid", sid)
				.setParameter("remainDays", remainDays).setParameter("payoutID", payoutID).getList();
		return listSubOfHD.stream().map(i -> toDomain(i)).collect(Collectors.toList());
	}

	public List<SubstitutionOfHDManagementData> getBySidRemainDayAndInPayout(String sid) {
		List<KrcmtSubOfHDManaData> listSubOfHD = this.queryProxy()
				.query(QUERY_BY_SID_REMAIN_AND_IN_PAYOUT, KrcmtSubOfHDManaData.class).setParameter("sid", sid)
				.getList();
		return listSubOfHD.stream().map(i -> toDomain(i)).collect(Collectors.toList());
	}

	@Override
	public List<SubstitutionOfHDManagementData> getBySidPeriodAndInPayout(String sid, GeneralDate startDate,
			GeneralDate endDate) {
		List<KrcmtSubOfHDManaData> listSubOfHD = this.queryProxy()
				.query(QUERY_BY_SID_PERIOD_AND_IN_PAYOUT, KrcmtSubOfHDManaData.class).setParameter("sid", sid)
				.setParameter("startDate", startDate).setParameter("endDate", endDate).getList();
		return listSubOfHD.stream().map(i -> toDomain(i)).collect(Collectors.toList());
	}

	@Override
	public Optional<SubstitutionOfHDManagementData> find(String cID, String sID, GeneralDate holidayDate) {
		return this.queryProxy().query(QUERY_BY_SID_CID_HOLIDAYDATE, KrcmtSubOfHDManaData.class).setParameter("cid", cID)
				.setParameter("employeeId", sID).setParameter("holidayDate", holidayDate).getSingle().map(i -> toDomain(i));
	}

	@Override
	public List<SubstitutionOfHDManagementData> getBySidAndDatePeriod(String sid, DatePeriod dateData) {
		List<KrcmtSubOfHDManaData> list = this.queryProxy().query(QUERY_BY_SID_DATE_PERIOD, KrcmtSubOfHDManaData.class)
				.setParameter("sid", sid)
				.setParameter("startDate", dateData.start())
				.setParameter("endDate", dateData.end())
				.getList();
		return list.stream().map(i -> toDomain(i)).collect(Collectors.toList());
	}

	@Override
	public List<SubstitutionOfHDManagementData> getByRemainDays(String sid, double remainDays) {
		List<KrcmtSubOfHDManaData> list = this.queryProxy().query(QUERY_BY_SID_REMAINDAYS, KrcmtSubOfHDManaData.class)
				.setParameter("sid", sid)
				.setParameter("remainDays", remainDays)
				.getList();
		return list.stream().map(i -> toDomain(i)).collect(Collectors.toList());
	}

	@Override
	public List<SubstitutionOfHDManagementData> getByHoliday(String sid, Boolean unknownDate, DatePeriod dayoffDate) {
		List<KrcmtSubOfHDManaData> listLeaveData = this.queryProxy()
				.query(QUERY_BY_SID_HOLIDAY, KrcmtSubOfHDManaData.class)
				.setParameter("employeeId", sid)
				.setParameter("unknownDate", unknownDate)
				.setParameter("startDate", dayoffDate.start()).getList();
		return listLeaveData.stream().map(x -> toDomain(x)).collect(Collectors.toList());
	}

	@Override
	public void deleteById(List<String> subOfHDID) {
		this.commandProxy().removeAll(KrcmtSubOfHDManaData.class, subOfHDID);
	}


	@Override
	public List<SubstitutionOfHDManagementData> getByYmdUnOffset(String cid, String sid, GeneralDate ymd, double unOffseDays) {
		List<KrcmtSubOfHDManaData> list = this.queryProxy().query(QUERY_BYSID_UNOFFSET, KrcmtSubOfHDManaData.class)
				.setParameter("sid", sid)
				.setParameter("cid", cid)
				.setParameter("dayOff", ymd)
				.setParameter("remainDays", unOffseDays)
				.getList();
		return list.stream().map(i -> toDomain(i)).collect(Collectors.toList());
	}



}
