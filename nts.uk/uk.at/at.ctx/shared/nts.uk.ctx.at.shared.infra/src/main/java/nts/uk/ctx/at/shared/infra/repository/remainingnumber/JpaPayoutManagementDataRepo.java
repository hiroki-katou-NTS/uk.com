package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementDataRepository;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.paymana.KrcmtPayoutManaData;
import nts.uk.shr.com.context.AppContexts;


@Stateless
public class JpaPayoutManagementDataRepo extends JpaRepository implements PayoutManagementDataRepository {

	private static final String QUERY_BYSID = "SELECT p FROM KrcmtPayoutManaData p WHERE p.cID = :cid AND p.sID =:employeeId ORDER BY p.dayOff";
	
	private static final String QUERY_BY_SID_CID_DAYOFF = "SELECT p FROM KrcmtPayoutManaData p WHERE p.cID = :cid AND p.sID =:employeeId AND p.dayOff = :dayoffDate";

	private static final String QUERY_BY_SID_CID_LIST_DAYOFF = "SELECT p FROM KrcmtPayoutManaData p WHERE p.cID = :cid AND p.sID =:employeeId AND p.dayOff IN :listDayOff";
	
	private static final String QUERY_BYSID_WITH_COND = String.join(" ", QUERY_BYSID, "AND p.stateAtr = :state");

	private static final String QUERY_BY_SID_DATEPERIOD = "SELECT p FROM KrcmtPayoutManaData p WHERE p.sID =:sid "
			+ " AND (p.stateAtr = :state OR p.payoutId in (SELECT ps.krcmtPayoutSubOfHDManaPK.payoutId FROM KrcmtPayoutSubOfHDMana ps WHERE ps.krcmtPayoutSubOfHDManaPK.subOfHDID =:subOfHDID))";

	private static final String  QUERY_BY_SID_STATE_AND_IN_SUB = "SELECT p FROM KrcmtPayoutManaData p WHERE p.sID = :sid AND (p.stateAtr = 0 OR p.payoutId in "
			+ "(SELECT ps.krcmtPayoutSubOfHDManaPK.payoutId FROM KrcmtPayoutSubOfHDMana ps inner join KrcmtSubOfHDManaData s on s.subOfHDID = ps.krcmtPayoutSubOfHDManaPK.subOfHDID where s.sID =:sid AND s.remainDays <> 0)) ORDER BY p.unknownDate, p.dayOff";

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
	private String QUERY_UNUSER_STATE = "SELECT p FROM KrcmtPayoutManaData p "
			+ " WHERE p.cID = :cid"
			+ " AND p.sID =:employeeId"
			+ " AND (p.dayOff < :dayOff OR p.dayOff is null)"
			+ " AND p.unUsedDays > :unUsedDays"
			+ " AND p.stateAtr = :stateAtr";

	private static final String QUERY_BY_SID = "SELECT p FROM KrcmtPayoutManaData p WHERE p.sID =:employeeId ORDER BY p.unknownDate, p.dayOff";
	
	private static final String QUERY_BY_UNKNOWN_DATE = "SELECT p FROM KrcmtPayoutManaData p WHERE p.sID =:employeeId AND p.unknownDate IN :unknownDates ORDER BY p.unknownDate ASC";
	
	private static final String QUERY_BY_SID_AND_ATR = "SELECT p FROM KrcmtPayoutManaData p WHERE p.cID = :cid AND p.sID =:employeeId AND p.stateAtr = 0 ORDER BY p.dayOff";
	
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
	public List<PayoutManagementData> getBySidAndStateAtr(String cid, String sid) {
		List<KrcmtPayoutManaData> list = this.queryProxy().query(QUERY_BY_SID_AND_ATR, KrcmtPayoutManaData.class)
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
	
	public List<PayoutManagementData> getByListPayoutDate(String cID, String sID, List<GeneralDate> lstDayOffDate) {
		return this.queryProxy().
				query(QUERY_BY_SID_CID_LIST_DAYOFF, KrcmtPayoutManaData.class).setParameter("employeeId", sID)
				.setParameter("cid", cID).setParameter("listDayOff", lstDayOffDate).getList()
				.stream().map(i -> toDomain(i)).collect(Collectors.toList());
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

	@Override
	public List<PayoutManagementData> getByUnUseState(String cid, String sid, GeneralDate ymd, double unUse, DigestionAtr state) {
		List<KrcmtPayoutManaData> list = this.queryProxy().query(QUERY_UNUSER_STATE, KrcmtPayoutManaData.class)
				.setParameter("cid", cid)
				.setParameter("employeeId", sid)
				.setParameter("dayOff", ymd)
				.setParameter("unUsedDays", unUse)
				.setParameter("stateAtr", state.value).getList();
		return list.stream().map(i -> toDomain(i)).collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementDataRepository#getSidWithCod(java.lang.String, java.util.List, int)
	 */
	@Override
	public Map<String ,Double> getAllSidWithCod(String cid, List<String> sid, int state) {
		Map <String ,Double> result = new HashMap<>();
		CollectionUtil.split(sid, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String sql = "SELECT * FROM KRCMT_PAYOUT_MANA_DATA WHERE  CID = ? AND STATE_ATR = ?   AND SID IN ("
					+ NtsStatement.In.createParamsString(subList) + ")";
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				stmt.setString(1, cid);
				stmt.setInt(2, state);
				for (int i = 0; i < subList.size(); i++) {
					stmt.setString(3 + i, subList.get(i));
				}
				List<KrcmtPayoutManaData> data = new NtsResultSet(stmt.executeQuery()).getList(rec -> {
					KrcmtPayoutManaData entity = new KrcmtPayoutManaData();
					
					entity.cID = rec.getString("CID");
					entity.sID = rec.getString("SID");
					entity.unUsedDays = rec.getDouble("UNUSED_DAYS");
					return entity;
			
				});
				Map<String, List<KrcmtPayoutManaData>> dataMap = data.parallelStream().collect(Collectors.groupingBy(c -> c.sID));
				dataMap.entrySet().parallelStream().forEach(c ->{
					result.put(c.getKey(), c.getValue().parallelStream().mapToDouble(i -> i.unUsedDays).sum());
				});
			
			}
			catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		return result;
	}

	@Override
	public List<PayoutManagementData> getBySidsAndCid(String cid, List<String> sid) {
		List<PayoutManagementData> result = new ArrayList<>();
		CollectionUtil.split(sid, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String sql = "SELECT * FROM KRCMT_PAYOUT_MANA_DATA WHERE  CID = ?  AND SID IN ("
					+ NtsStatement.In.createParamsString(subList) + ")";
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				stmt.setString(1, cid);
				for (int i = 0; i < subList.size(); i++) {
					stmt.setString(2 + i, subList.get(i));
				}
				List<PayoutManagementData> data = new NtsResultSet(stmt.executeQuery()).getList(rec -> {
					KrcmtPayoutManaData entity = new KrcmtPayoutManaData();
					entity.payoutId = rec.getString("PAYOUT_ID");
					entity.cID = rec.getString("CID");
					entity.sID = rec.getString("SID");
					entity.unknownDate = rec.getBoolean("UNKNOWN_DATE");
					
					entity.dayOff = rec.getGeneralDate("DAYOFF_DATE");
					entity.expiredDate = rec.getGeneralDate("EXPIRED_DATE");
					entity.lawAtr = rec.getInt("LAW_ATR");
					
					entity.occurredDays = rec.getDouble("OCCURRENCE_DAYS");
					entity.unUsedDays = rec.getDouble("UNUSED_DAYS");
					
					entity.stateAtr = rec.getInt("STATE_ATR");
					entity.disapearDate = rec.getGeneralDate("DISAPEAR_DATE");
					return toDomain(entity);
			
				});
				result.addAll(data);
			}
			catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		return result;
	}

	@Override
	public void addAll(List<PayoutManagementData> domains) {
		String INS_SQL = "INSERT INTO KRCMT_PAYOUT_MANA_DATA (INS_DATE, INS_CCD , INS_SCD , INS_PG,"
				+ " UPD_DATE , UPD_CCD , UPD_SCD , UPD_PG," 
				+ " PAYOUT_ID, CID, SID, UNKNOWN_DATE, DAYOFF_DATE, EXPIRED_DATE, LAW_ATR, OCCURRENCE_DAYS,"
				+ " UNUSED_DAYS, STATE_ATR, DISAPEAR_DATE)"
				+ " VALUES (INS_DATE_VAL, INS_CCD_VAL, INS_SCD_VAL, INS_PG_VAL,"
				+ " UPD_DATE_VAL, UPD_CCD_VAL, UPD_SCD_VAL, UPD_PG_VAL,"
				+ " PAYOUT_ID_VAL, CID_VAL, SID_VAL, UNKNOWN_DATE_VAL, DAYOFF_DATE_VAL, EXPIRED_DATE_VAL, LAW_ATR_VAL, OCCURRENCE_DAYS_VAL,"
				+ " UNUSED_DAYS_VAL, STATE_ATR_VAL, DATE_VAL); ";
		String insCcd = AppContexts.user().companyCode();
		String insScd = AppContexts.user().employeeCode();
		String insPg = AppContexts.programId();
		
		String updCcd = insCcd;
		String updScd = insScd;
		String updPg = insPg;
		StringBuilder sb = new StringBuilder();
		domains.parallelStream().forEach(c -> {
			String sql = INS_SQL;
			sql = sql.replace("INS_DATE_VAL", "'" + GeneralDateTime.now() + "'");
			sql = sql.replace("INS_CCD_VAL", "'" + insCcd + "'");
			sql = sql.replace("INS_SCD_VAL", "'" + insScd + "'");
			sql = sql.replace("INS_PG_VAL", "'" + insPg + "'");

			sql = sql.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() + "'");
			sql = sql.replace("UPD_CCD_VAL", "'" + updCcd + "'");
			sql = sql.replace("UPD_SCD_VAL", "'" + updScd + "'");
			sql = sql.replace("UPD_PG_VAL", "'" + updPg + "'");

			sql = sql.replace("PAYOUT_ID_VAL", "'" + c.getPayoutId() + "'");
			sql = sql.replace("CID_VAL", "'" + c.getCID()+ "'");
			sql = sql.replace("SID_VAL", "'" + c.getSID()+ "'");
			
			sql = sql.replace("UNKNOWN_DATE_VAL", c.getPayoutDate().isUnknownDate() == true? "1":"0");
			sql = sql.replace("DAYOFF_DATE_VAL", c.getPayoutDate().getDayoffDate().isPresent()
					? "'" + c.getPayoutDate().getDayoffDate().get() +"'" : "null");
			sql = sql.replace("EXPIRED_DATE_VAL", c.getExpiredDate() == null? "null" : "'" + c.getExpiredDate() + "'");
			
			sql = sql.replace("LAW_ATR_VAL", ""+ c.getLawAtr().value +"");
			sql = sql.replace("OCCURRENCE_DAYS_VAL", c.getOccurredDays() == null? "null": ""+ c.getOccurredDays().v() + "");
			
			
			sql = sql.replace("UNUSED_DAYS_VAL", c.getUnUsedDays() == null? "null": "" + c.getUnUsedDays().v() +"");
			sql = sql.replace("STATE_ATR_VAL", "" + c.getStateAtr().value +"");
			sql = sql.replace("DATE_VAL", c.getDisapearDate() == null? "null": (c.getDisapearDate().isPresent()? "'"+ c.getDisapearDate().get()+"'": "null"));
			sb.append(sql);
		});

		int records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
		System.out.println(records);
		
	}
	@Override
	public List<PayoutManagementData> getAllBySid(String sid){
		List<KrcmtPayoutManaData> list = this.queryProxy().query(QUERY_BY_SID, KrcmtPayoutManaData.class)
				.setParameter("employeeId", sid)
				.getList();
		return list.stream().map(i -> toDomain(i)).collect(Collectors.toList());
	}
	
	@Override
	public List<PayoutManagementData> getAllByUnknownDate(String sid, List<String> unknownDates) {
		List<KrcmtPayoutManaData> list = this.queryProxy().query(QUERY_BY_UNKNOWN_DATE, KrcmtPayoutManaData.class)
				.setParameter("employeeId", sid)
				.setParameter("unknownDates", unknownDates)
				.getList();
		return list.stream().map(i -> toDomain(i)).collect(Collectors.toList());
	}

}
