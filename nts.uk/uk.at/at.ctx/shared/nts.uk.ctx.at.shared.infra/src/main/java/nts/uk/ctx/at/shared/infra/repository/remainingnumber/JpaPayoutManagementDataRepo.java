package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementData;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.paymana.KrcmtPayoutManaData;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.subhdmana.KrcmtComDayoffMaData;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.subhdmana.KrcmtLeaveManaData;
import nts.uk.shr.com.time.calendar.period.DatePeriod;


@Stateless
public class JpaPayoutManagementDataRepo extends JpaRepository implements PayoutManagementDataRepository {

	private static final String QUERY_BYSID = "SELECT p FROM KrcmtPayoutManaData p WHERE p.cID = :cid AND p.sID =:employeeId";
	
	private static final String QUERY_BY_SID_CID_DAYOFF = "SELECT p FROM KrcmtPayoutManaData p WHERE p.cID = :cid AND p.sID =:employeeId AND p.dayOff = :dayoffDate";

	private static final String QUERY_BYSID_WITH_COND = String.join(" ", QUERY_BYSID, "AND p.stateAtr = :state");

	private static final String QUERY_BY_SID_DATEPERIOD = "SELECT p FROM KrcmtPayoutManaData p WHERE p.sID =:sid "
			+ " AND (p.stateAtr = :state OR p.payoutId in (SELECT ps.krcmtPayoutSubOfHDManaPK.payoutId FROM KrcmtPayoutSubOfHDMana ps WHERE ps.krcmtPayoutSubOfHDManaPK.subOfHDID =:subOfHDID))";

	private static final String  QUERY_BY_SID_STATE_AND_IN_SUB = "SELECT p FROM KrcmtPayoutManaData p WHERE p.sID = :sid AND (p.stateAtr = 0 OR p.payoutId in "
			+ "(SELECT ps.krcmtPayoutSubOfHDManaPK.payoutId FROM KrcmtPayoutSubOfHDMana ps inner join KrcmtSubOfHDManaData s on s.subOfHDID = ps.krcmtPayoutSubOfHDManaPK.subOfHDID where s.sID =:sid AND s.remainDays <> 0))";

	private static final String  QUERY_BY_SID_PERIOD_AND_IN_SUB = "SELECT p FROM KrcmtPayoutManaData p WHERE p.sID = :sid AND ((p.dayOff >= :startDate AND p.dayOff <= :endDate) OR p.payoutId in "
			+ "(SELECT ps.krcmtPayoutSubOfHDManaPK.payoutId FROM KrcmtPayoutSubOfHDMana ps inner join KrcmtSubOfHDManaData s on s.subOfHDID = ps.krcmtPayoutSubOfHDManaPK.subOfHDID where s.sID =:sid AND s.dayOff >= :startDate AND s.dayOff <= :endDate))";
	
	private static final String DELETE_QUERY = "DELETE FROM KrcmtPayoutManaData a WHERE a.payoutId= :payoutId";

	private static final String QUERY_BY_SUBID = "SELECT p FROM KrcmtPayoutManaData p where p.payoutId IN (SELECT ps.krcmtPayoutSubOfHDManaPK.payoutId FROM KrcmtPayoutSubOfHDMana ps WHERE ps.krcmtPayoutSubOfHDManaPK.subOfHDID =:subOfHDID)";
	
	private static final String QUERY_DELETE_SUB = "DELETE FROM KrcmtPayoutSubOfHDMana p WHERE p.krcmtPayoutSubOfHDManaPK.payoutId = :payoutId ";
	
	private static final String QUERY_SID_DATE_PERIOD = "SELECT c FROM KrcmtPayoutManaData c"
			+ " WHERE c.sID = :sid"
			+ " AND c.dayOff >= :startDate"
			+ " AND c.dayOff <= :endDate";
	
	private static final String QUERY_BY_STATEATR = "SELECT c FROM KrcmtPayoutManaData c"
			+ " WHERE c.sID = :sid"
			+ " AND c.stateAtr = :stateAtr";
	private static final String QUERY_BY_EACH_PERIOD = QUERY_SID_DATE_PERIOD
			+ " AND (c.unUsedDays > :unUsedDays AND c.expiredDate >= :sDate AND c.expiredDate <= :eDate)"
			+ " OR (c.stateAtr = :stateAtr AND c.disapearDate >= :sDate AND c.disapearDate <= :eDate) ";
	private static final String QUERY_BY_SID_HOLIDAY = "SELECT c FROM KrcmtPayoutManaData c"
			+ " WHERE c.sID = :employeeId"
			+ " AND c.unknownDate = :unknownDate"
			+ " AND c.dayOff >= :startDate";
	private static final String QUERY_BYSID_COND_DATE = QUERY_BYSID_WITH_COND + " AND p.dayOff < :dayOff";


	@Override
	public List<PayoutManagementData> getSidWithCodDate(String cid, String sid, int state, GeneralDate ymd) {
		List<KrcmtPayoutManaData> list = this.queryProxy().query(QUERY_BYSID_COND_DATE, KrcmtPayoutManaData.class)
				.setParameter("cid", cid)
				.setParameter("employeeId", sid)
				.setParameter("state", state)
				.setParameter("dayOff", ymd)
				.getList();
		return list.stream().map(i -> toDomain(i)).collect(Collectors.toList());
	}

	@Override
	public List<PayoutManagementData> getSid(String cid, String sid) {
		List<KrcmtPayoutManaData> list = this.queryProxy().query(QUERY_BYSID, KrcmtPayoutManaData.class)
				.setParameter("cid", cid).setParameter("employeeId", sid).getList();
		return list.stream().map(i -> toDomain(i)).collect(Collectors.toList());
	}

	@Override
	public List<PayoutManagementData> getSidWithCod(String cid, String sid, int state) {
		List<KrcmtPayoutManaData> list = this.queryProxy().query(QUERY_BYSID_WITH_COND, KrcmtPayoutManaData.class)
				.setParameter("cid", cid).setParameter("employeeId", sid).setParameter("state", state).getList();
		return list.stream().map(i -> toDomain(i)).collect(Collectors.toList());
	}

	/**
	 * Convert to domain
	 * 
	 * @param entity
	 * @return
	 */
	private PayoutManagementData toDomain(KrcmtPayoutManaData entity) {
		return new PayoutManagementData(entity.payoutId, entity.cID, entity.sID, entity.unknownDate, entity.dayOff,
				entity.expiredDate, entity.lawAtr, entity.occurredDays, entity.unUsedDays, entity.stateAtr);
	}

	@Override
	public void create(PayoutManagementData domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	/**
	 * Convert to entity
	 * 
	 * @param domain
	 * @return
	 */
	private KrcmtPayoutManaData toEntity(PayoutManagementData domain) {
		KrcmtPayoutManaData entity = new KrcmtPayoutManaData();
		entity.payoutId = domain.getPayoutId();
		entity.sID = domain.getSID();
		entity.cID = domain.getCID();
		entity.unknownDate = domain.getPayoutDate().isUnknownDate();
		if (domain.getPayoutDate().getDayoffDate().isPresent()) {
			entity.dayOff = domain.getPayoutDate().getDayoffDate().get();
		}
		entity.expiredDate = domain.getExpiredDate();
		entity.lawAtr = domain.getLawAtr().value;
		entity.occurredDays = domain.getOccurredDays().v();
		entity.unUsedDays = domain.getUnUsedDays().v();
		entity.stateAtr = domain.getStateAtr().value;
		return entity;
	}

	@Override
	public void deletePayoutSubOfHDMana(String payoutId) {
		this.getEntityManager().createQuery(QUERY_DELETE_SUB).setParameter("payoutId", payoutId).executeUpdate();
	}

	@Override
	public void delete(String payoutId) {
		Optional<KrcmtPayoutManaData> entity = this.queryProxy().find(payoutId, KrcmtPayoutManaData.class);
		if (entity.isPresent()) {
			this.getEntityManager().createQuery(DELETE_QUERY).setParameter("payoutId", payoutId).executeUpdate();
		}else{
			throw new BusinessException("Msg_198");
		}
		
	}

	@Override
	public void update(PayoutManagementData domain) {
		this.commandProxy().update(toEntity(domain));
	}

	@Override
	public Optional<PayoutManagementData> findByID(String ID) {
		String QUERY_BY_ID = "SELECT s FROM KrcmtPayoutManaData s WHERE s.payoutId = :payoutId";
		Optional<KrcmtPayoutManaData> entity = this.queryProxy().query(QUERY_BY_ID, KrcmtPayoutManaData.class).setParameter("payoutId", ID).getSingle();
		if (entity.isPresent()) {
			return Optional.ofNullable(toDomain(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public List<PayoutManagementData> getBySidDatePeriod(String sid, String subOfHDID, int digestionAtr) {
		List<KrcmtPayoutManaData> listSubOfHD = this.queryProxy()
				.query(QUERY_BY_SID_DATEPERIOD, KrcmtPayoutManaData.class).setParameter("sid", sid)
				.setParameter("state", digestionAtr).setParameter("subOfHDID", subOfHDID).getList();
		return listSubOfHD.stream().map(i -> toDomain(i)).collect(Collectors.toList());
	}

	@Override
	public List<PayoutManagementData> getBySidStateAndInSub(String sid) {
		List<KrcmtPayoutManaData> listpayout = this.queryProxy()
				.query(QUERY_BY_SID_STATE_AND_IN_SUB, KrcmtPayoutManaData.class).setParameter("sid", sid).getList();
		return listpayout.stream().map(i -> toDomain(i)).collect(Collectors.toList());
	}

	public Optional<PayoutManagementData> find(String cID, String sID, GeneralDate dayoffDate) {
		return this.queryProxy().
				query(QUERY_BY_SID_CID_DAYOFF, KrcmtPayoutManaData.class).setParameter("employeeId", sID)
				.setParameter("cid", cID).setParameter("dayoffDate", dayoffDate).getSingle().map(i -> toDomain(i));
	}
	
	public List<PayoutManagementData> getBySidPeriodAndInSub(String sid, GeneralDate startDate, GeneralDate endDate) {
		List<KrcmtPayoutManaData> listpayout = this.queryProxy()
				.query(QUERY_BY_SID_PERIOD_AND_IN_SUB, KrcmtPayoutManaData.class).setParameter("sid", sid)
				.setParameter("startDate", startDate).setParameter("endDate", endDate).getList();
		return listpayout.stream().map(i -> toDomain(i)).collect(Collectors.toList());
	}

	@Override
	public List<PayoutManagementData> getDayoffDateBysubOfHDID(String subOfHDID) {
		List<KrcmtPayoutManaData> listpayout = this.queryProxy().query(QUERY_BY_SUBID, KrcmtPayoutManaData.class ).setParameter("subOfHDID", subOfHDID).getList(); 
		return listpayout.stream().map(i -> toDomain(i)).collect(Collectors.toList());
	}

	@Override
	public List<PayoutManagementData> getBySidAndDatePeriod(String sid, DatePeriod dateData) {
		List<KrcmtPayoutManaData> listpayout = this.queryProxy().query(QUERY_SID_DATE_PERIOD, KrcmtPayoutManaData.class )
				.setParameter("sid", sid)
				.setParameter("startDate", dateData.start())
				.setParameter("endDate", dateData.end())
				.getList(); 
		return listpayout.stream().map(i -> toDomain(i)).collect(Collectors.toList());
	}

	@Override
	public List<PayoutManagementData> getByStateAtr(String sid, DigestionAtr stateAtr) {
		List<KrcmtPayoutManaData> list = this.queryProxy().query(QUERY_BY_STATEATR, KrcmtPayoutManaData.class)
				.setParameter("sid", sid)
				.setParameter("stateAtr", stateAtr.value)
				.getList();
		return list.stream().map(i -> toDomain(i)).collect(Collectors.toList());
	}

	@Override
	public List<PayoutManagementData> getEachPeriod(String sid, DatePeriod dateTmp, DatePeriod dateData,
			Double unUseDays, DigestionAtr stateAtr) {
		List<KrcmtPayoutManaData> listpayout = this.queryProxy().query(QUERY_BY_EACH_PERIOD, KrcmtPayoutManaData.class )
				.setParameter("sid", sid)
				.setParameter("startDate", dateTmp.start())
				.setParameter("endDate", dateTmp.end())
				.setParameter("unUsedDays", unUseDays)
				.setParameter("sDate", dateData.start())
				.setParameter("eDate", dateData.end())
				.setParameter("stateAtr", stateAtr.value)
				.getList(); 
		return listpayout.stream().map(i -> toDomain(i)).collect(Collectors.toList());
	}

	@Override
	public List<PayoutManagementData> getByHoliday(String sid, Boolean unknownDate, DatePeriod dayOff) {
		List<KrcmtPayoutManaData> listLeaveData = this.queryProxy()
				.query(QUERY_BY_SID_HOLIDAY, KrcmtPayoutManaData.class)
				.setParameter("employeeId", sid)
				.setParameter("unknownDate", unknownDate)
				.setParameter("startDate", dayOff.start()).getList();
		return listLeaveData.stream().map(x -> toDomain(x)).collect(Collectors.toList());
	}
	@Override
	public void deleteById(List<String> payoutId) {
		this.commandProxy().removeAll(KrcmtPayoutManaData.class, payoutId);
	}

}
