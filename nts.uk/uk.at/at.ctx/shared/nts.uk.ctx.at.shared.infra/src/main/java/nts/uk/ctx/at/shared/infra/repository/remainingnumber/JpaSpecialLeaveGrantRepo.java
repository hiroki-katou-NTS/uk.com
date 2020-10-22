package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRepository;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.KrcmtSpecialLeaveReam;
import nts.uk.shr.com.context.AppContexts;
import nts.arc.time.calendar.period.DatePeriod;

@Stateless
public class JpaSpecialLeaveGrantRepo extends JpaRepository implements SpecialLeaveGrantRepository {

	private static final String GET_ALL_BY_SID_SPECIALCODE = "SELECT a FROM KrcmtSpecialLeaveReam a WHERE a.employeeId = :employeeId AND a.specialLeaCode = :specialLeaCode order by a.grantDate DESC";

	private static final String GET_ALL_BY_SID_SPECIALCODE_STATUS = "SELECT a FROM KrcmtSpecialLeaveReam a WHERE a.employeeId = :employeeId AND a.specialLeaCode = :specialLeaCode AND a.expStatus = :expStatus order by a.grantDate";
	private static final String GET_ALL_BY_SID_AND_GRANT_DATE = "SELECT a FROM KrcmtSpecialLeaveReam a WHERE a.employeeId = :sid AND a.grantDate =:grantDate AND a.specialLeaID !=:specialLeaID AND a.specialLeaCode =:specialLeaCode";
	@Override
	public List<SpecialLeaveGrantRemainingData> getAll(String employeeId, int specialCode) {
		List<KrcmtSpecialLeaveReam> entities = this.queryProxy()
				.query(GET_ALL_BY_SID_SPECIALCODE, KrcmtSpecialLeaveReam.class).setParameter("employeeId", employeeId)
				.setParameter("specialLeaCode", specialCode).getList();

		return entities.stream()
				.map(x -> SpecialLeaveGrantRemainingData.createFromJavaType(x.specialLeaID, x.cId, x.employeeId,
						x.specialLeaCode, x.grantDate, x.deadlineDate, x.expStatus, x.registerType, x.numberDayGrant,
						x.timeGrant, x.numberDayUse, x.timeUse, x.useSavingDays, x.numberOverDays, x.timeOver,
						x.numberDayRemain, x.timeRemain))
				.collect(Collectors.toList());
	}

	@Override
	public void add(SpecialLeaveGrantRemainingData data) {
		if (data != null)
			this.commandProxy().insert(toEntity(data));
	}

	@Override
	public void update(SpecialLeaveGrantRemainingData data) {
		if (data != null) {
			Optional<KrcmtSpecialLeaveReam> entityOpt = this.queryProxy().find(data.getSpecialId(),
					KrcmtSpecialLeaveReam.class);
			if (entityOpt.isPresent()) {
				KrcmtSpecialLeaveReam entity = entityOpt.get();
				updateDetail(entity, data);
				this.commandProxy().update(entity);
			}
		}
	}

	@Override
	public void delete(String specialid) {

		Optional<KrcmtSpecialLeaveReam> entityOpt = this.queryProxy().find(specialid, KrcmtSpecialLeaveReam.class);
		if (entityOpt.isPresent()) {
			KrcmtSpecialLeaveReam entity = entityOpt.get();
			this.commandProxy().remove(entity);
		}
	}
	@SneakyThrows
	@Override
	public Optional<SpecialLeaveGrantRemainingData> getBySpecialId(String specialId) {
		try (PreparedStatement sql = this.connection().prepareStatement(
				"SELECT * FROM KRCMT_SPEC_LEAVE_REMAIN"
				+ " WHERE SPECIAL_LEAVE_ID = ?")) {

			sql.setString(1, specialId);
			Optional<SpecialLeaveGrantRemainingData> entities = new NtsResultSet(sql.executeQuery())
					.getSingle(x -> toDomain(x));
			if(!entities.isPresent()) {
				return Optional.empty();
			}
			return entities;	
		}
			
	}
	
	private SpecialLeaveGrantRemainingData toDomain(NtsResultRecord  record) {
		return SpecialLeaveGrantRemainingData.createFromJavaType(record.getString("SPECIAL_LEAVE_ID"),
				record.getString("CID"),
				record.getString("SID"),
				record.getInt("SPECIAL_LEAVE_CD"),
				record.getGeneralDate("GRANT_DATE"),
				record.getGeneralDate("DEADLINE_DATE"),
				record.getInt("EXPIRED_STATE"),
				record.getInt("REGISTRATION_TYPE"),
				record.getBigDecimal("NUMBER_DAYS_GRANT") == null ? 0.0 : record.getBigDecimal("NUMBER_DAYS_GRANT").doubleValue(),
				record.getInt("TIME_GRANT"),
				record.getBigDecimal("NUMBER_DAYS_USE") == null ? 0.0 : record.getBigDecimal("NUMBER_DAYS_USE").doubleValue(),
				record.getInt("TIME_USE"),
				record.getBigDecimal("USED_SAVING_DAYS") == null ? 0.0 : record.getBigDecimal("USED_SAVING_DAYS").doubleValue(),
				record.getBigDecimal("NUMBER_OVER_DAYS") == null ? 0.0 : record.getBigDecimal("NUMBER_OVER_DAYS").doubleValue(),
				record.getInt("TIME_OVER"),
				record.getBigDecimal("NUMBER_DAYS_REMAIN") == null ? 0.0 : record.getBigDecimal("NUMBER_DAYS_REMAIN").doubleValue(),
				record.getInt("TIME_REMAIN"));
	}

	private void updateDetail(KrcmtSpecialLeaveReam entity, SpecialLeaveGrantRemainingData data) {
		entity.employeeId = data.getEmployeeId();
		entity.cId = data.getCId();
		entity.specialLeaCode = data.getSpecialLeaveCode().v();

		entity.grantDate = data.getGrantDate();
		entity.deadlineDate = data.getDeadlineDate();
		entity.expStatus = data.getExpirationStatus().value;
		entity.registerType = data.getRegisterType().value;
		// grant data
		entity.numberDayGrant = data.getDetails().getGrantNumber().getDayNumberOfGrant().v();
		entity.timeGrant = data.getDetails().getGrantNumber().getTimeOfGrant().isPresent()
				? data.getDetails().getGrantNumber().getTimeOfGrant().get().v()
				: 0;
		// remain data
		entity.numberDayRemain = data.getDetails().getRemainingNumber().getDayNumberOfRemain().v();
		entity.timeRemain = data.getDetails().getRemainingNumber().getTimeOfRemain().isPresent()
				? data.getDetails().getRemainingNumber().getTimeOfRemain().get().v()
				: 0;
		// use data
		entity.numberDayUse = data.getDetails().getUsedNumber().getDayNumberOfUse().v();
		entity.timeUse = data.getDetails().getUsedNumber().getTimeOfUse().isPresent()
				? data.getDetails().getUsedNumber().getTimeOfUse().get().v()
				: 0;
		// use Saving data(tai lieu đang bảo truyền null vào)
		// entity.useSavingDays =
		// data.getDetails().getUsedNumber().getUseSavingDays().isPresent()
		// ? data.getDetails().getUsedNumber().getUseSavingDays().get().v()
		// : 0;
		
		// Over
		if (data.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().isPresent()) {
			entity.numberOverDays = data.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().get()
					.getNumberOverDays().v();
			entity.timeOver = data.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().get().getTimeOver()
					.isPresent()
							? data.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().get().getTimeOver()
									.get().v()
							: 0;
		}

	}

	/**
	 * Convert to entity
	 * 
	 * @param domain
	 * @return
	 */
	private KrcmtSpecialLeaveReam toEntity(SpecialLeaveGrantRemainingData data) {
		KrcmtSpecialLeaveReam entity = new KrcmtSpecialLeaveReam();
		entity.cId = data.getCid();
		entity.specialLeaID = data.getLeaveID();
		entity.employeeId = data.getEmployeeId();
		entity.specialLeaCode = data.getSpecialLeaveCode();

		entity.expStatus = data.getExpirationStatus().value;
		entity.registerType = data.getRegisterType().value;

		entity.grantDate = data.getGrantDate();
		entity.deadlineDate = data.getDeadline();

		// grant data
		entity.numberDayGrant = data.getDetails().getGrantNumber().getDayNumberOfGrant().v();
		entity.timeGrant = data.getDetails().getGrantNumber().getTimeOfGrant().isPresent()
				? data.getDetails().getGrantNumber().getTimeOfGrant().get().v()
				: 0;
		// remain data
		entity.numberDayRemain = data.getDetails().getRemainingNumber().getDayNumberOfRemain().v();
		entity.timeRemain = data.getDetails().getRemainingNumber().getTimeOfRemain().isPresent()
				? data.getDetails().getRemainingNumber().getTimeOfRemain().get().v()
				: 0;
		// use data
		entity.numberDayUse = data.getDetails().getUsedNumber().getDayNumberOfUse().v();
		entity.timeUse = data.getDetails().getUsedNumber().getTimeOfUse().isPresent()
				? data.getDetails().getUsedNumber().getTimeOfUse().get().v()
				: 0;
		// use Saving data(tai lieu đang bảo truyền null vào)
		// entity.useSavingDays =
		// data.getDetails().getUsedNumber().getUseSavingDays().isPresent()
		// ? data.getDetails().getUsedNumber().getUseSavingDays().get().v()
		// : 0;
		entity.useSavingDays = 0d;
		// Over
		if (data.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().isPresent()) {
			entity.numberOverDays = data.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().get()
					.getNumberOverDays().v();
			entity.timeOver = data.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().get().getTimeOver()
					.isPresent()
							? data.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().get().getTimeOver()
									.get().v()
							: 0;
		}

		return entity;
	}

	@Override
	public List<SpecialLeaveGrantRemainingData> getAllByExpStatus(String employeeId, int specialCode,
			int expirationStatus) {
		List<KrcmtSpecialLeaveReam> entities = this.queryProxy()
				.query(GET_ALL_BY_SID_SPECIALCODE_STATUS, KrcmtSpecialLeaveReam.class)
				.setParameter("employeeId", employeeId).setParameter("specialLeaCode", specialCode)
				.setParameter("expStatus", expirationStatus).getList();

		return entities.stream()
				.map(x -> SpecialLeaveGrantRemainingData.createFromJavaType(x.specialLeaID, x.cId, x.employeeId,
						x.specialLeaCode, x.grantDate, x.deadlineDate, x.expStatus, x.registerType, x.numberDayGrant,
						x.timeGrant, x.numberDayUse, x.timeUse, x.useSavingDays, x.numberOverDays, x.timeOver,
						x.numberDayRemain, x.timeRemain))
				.collect(Collectors.toList());
	}
	@SneakyThrows
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<SpecialLeaveGrantRemainingData> getByPeriodStatus(String sid, int specialLeaveCode,
			LeaveExpirationStatus expirationStatus, GeneralDate grantDate, GeneralDate deadlineDate) {
			
		try (PreparedStatement sql = this.connection().prepareStatement("SELECT * FROM KRCMT_SPEC_LEAVE_REMAIN"
						+ " WHERE SID = ?"
						+ " AND SPECIAL_LEAVE_CD = ?"
						+ " AND GRANT_DATE <= ?"
						+ " AND DEADLINE_DATE >= ?"
						+ " AND EXPIRED_STATE = ?"
						+ " ORDER BY GRANT_DATE ASC")){

			sql.setString(1, sid);
			sql.setInt(2, specialLeaveCode);
			sql.setDate(3, Date.valueOf(grantDate.toLocalDate()));
			sql.setDate(4, Date.valueOf(deadlineDate.toLocalDate()));
			sql.setInt(5, expirationStatus.value);
			List<SpecialLeaveGrantRemainingData> entities = new NtsResultSet(sql.executeQuery())
					.getList(x -> toDomain(x));
			if(entities.isEmpty()) {
				return Collections.emptyList();
			}
			return entities;
		}
		
	}

	@Override
	public boolean isHasData(String sid, String specialId, GeneralDate grantDate, int specialLeaCode) {
		//GET_ALL_BY_SID_AND_GRANT_DATE
		List<KrcmtSpecialLeaveReam> specialLeave = this.queryProxy().query(GET_ALL_BY_SID_AND_GRANT_DATE, KrcmtSpecialLeaveReam.class)
				.setParameter("sid", sid)
				.setParameter("grantDate", grantDate)
				.setParameter("specialLeaID", specialId)
				.setParameter("specialLeaCode", specialLeaCode)
				.getList();
		if(specialLeave.size()> 0) {
			return true;
		}		
		return false;
	}
	@SneakyThrows
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<SpecialLeaveGrantRemainingData> getByNextDate(String sid, int speCode, DatePeriod datePriod,
			GeneralDate startDate, LeaveExpirationStatus expirationStatus) {
		try (PreparedStatement sql = this.connection().prepareStatement("SELECT * FROM KRCMT_SPEC_LEAVE_REMAIN"
				+ " WHERE SID = ?"
				+ " AND SPECIAL_LEAVE_CD = ?"
				+ " AND GRANT_DATE > ?"
				+ " AND GRANT_DATE <= ?"
				+ " AND DEADLINE_DATE >= ?"
				+ " AND EXPIRED_STATE = ?"
				+ " ORDER BY GRANT_DATE ASC")){

			sql.setString(1, sid);
			sql.setInt(2, speCode);
			sql.setDate(3, Date.valueOf(datePriod.start().toLocalDate()));
			sql.setDate(4, Date.valueOf(datePriod.end().toLocalDate()));
			sql.setDate(5, Date.valueOf(startDate.toLocalDate()));
			sql.setInt(6, expirationStatus.value);
			List<SpecialLeaveGrantRemainingData> entities = new NtsResultSet(sql.executeQuery())
					.getList(x -> toDomain(x));
			if(entities.isEmpty()) {
				return Collections.emptyList();
			}
			return entities;
		}
	}

	@Override
	public List<SpecialLeaveGrantRemainingData> getAllByExpStatus(String cid, List<String> sids , int specialCode, int expirationStatus){
		List<KrcmtSpecialLeaveReam> entities = new ArrayList<>();
		
		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String sql = "SELECT * FROM KRCMT_SPEC_LEAVE_REMAIN WHERE CID = ? AND SPECIAL_LEAVE_CD = ? AND EXPIRED_STATE = ? AND SID IN ("+ NtsStatement.In.createParamsString(subList) + ")";
			
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				stmt.setString( 1,  cid);
				stmt.setInt( 2,  specialCode);
				stmt.setInt( 3,  expirationStatus);
				for (int i = 0; i < subList.size(); i++) {
					stmt.setString( 4 + i, subList.get(i));
				}
				
				List<KrcmtSpecialLeaveReam> result = new NtsResultSet(stmt.executeQuery()).getList(rec -> {
					KrcmtSpecialLeaveReam entity = new KrcmtSpecialLeaveReam();
					entity.specialLeaID = rec.getString("SPECIAL_LEAVE_ID");
					entity.cId = rec.getString("CID");
					entity.employeeId = rec.getString("SID");
					entity.specialLeaCode = rec.getInt("SPECIAL_LEAVE_CD");
					entity.grantDate = rec.getGeneralDate("GRANT_DATE");
					entity.deadlineDate = rec.getGeneralDate("DEADLINE_DATE");
					entity.expStatus = rec.getInt("EXPIRED_STATE");
					entity.registerType = rec.getInt("REGISTRATION_TYPE");
					entity.numberDayGrant = rec.getDouble("NUMBER_DAYS_GRANT");
					entity.timeGrant = rec.getInt("TIME_GRANT");
					entity.numberDayRemain = rec.getDouble("NUMBER_DAYS_REMAIN");
					entity.timeRemain = rec.getInt("TIME_REMAIN");
					entity.numberDayUse = rec.getDouble("NUMBER_DAYS_USE");
					entity.timeUse = rec.getInt("TIME_USE");
					entity.useSavingDays = rec.getDouble("USED_SAVING_DAYS");
					entity.numberOverDays = rec.getDouble("NUMBER_OVER_DAYS");
					entity.timeOver = rec.getInt("TIME_OVER");
					return entity;
				});
				entities.addAll(result);
				
			}catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		
		return entities.stream()
				.map(x -> SpecialLeaveGrantRemainingData.createFromJavaType(x.specialLeaID, x.cId, x.employeeId,
						x.specialLeaCode, x.grantDate, x.deadlineDate, x.expStatus, x.registerType, x.numberDayGrant,
						x.timeGrant, x.numberDayUse, x.timeUse, x.useSavingDays, x.numberOverDays, x.timeOver,
						x.numberDayRemain, x.timeRemain))
				.collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRepository#getAllByListEmpID(java.util.List, int)
	 */
	@Override
	@SneakyThrows
	public List<SpecialLeaveGrantRemainingData> getAllByListEmpID(List<String> listEmpID, int specialLeaveCD) {
		List<KrcmtSpecialLeaveReam> entities = new ArrayList<>();
		CollectionUtil.split(listEmpID, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String sql = "SELECT * FROM KRCMT_SPEC_LEAVE_REMAIN WHERE  SPECIAL_LEAVE_CD = ? AND SID IN ("
					+ NtsStatement.In.createParamsString(subList) + ")";
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				stmt.setInt(1, specialLeaveCD);

				for (int i = 0; i < subList.size(); i++) {
					stmt.setString(2 + i, subList.get(i));
				}
				List<KrcmtSpecialLeaveReam> result = new NtsResultSet(stmt.executeQuery()).getList(rec -> {
					KrcmtSpecialLeaveReam entity = new KrcmtSpecialLeaveReam();
					entity.specialLeaID = rec.getString("SPECIAL_LEAVE_ID");
					entity.cId = rec.getString("CID");
					entity.employeeId = rec.getString("SID");
					entity.specialLeaCode = rec.getInt("SPECIAL_LEAVE_CD");
					entity.grantDate = rec.getGeneralDate("GRANT_DATE");
					entity.deadlineDate = rec.getGeneralDate("DEADLINE_DATE");
					entity.expStatus = rec.getInt("EXPIRED_STATE");
					entity.registerType = rec.getInt("REGISTRATION_TYPE");
					entity.numberDayGrant = rec.getDouble("NUMBER_DAYS_GRANT");
					entity.timeGrant = rec.getInt("TIME_GRANT");
					entity.numberDayRemain = rec.getDouble("NUMBER_DAYS_REMAIN");
					entity.timeRemain = rec.getInt("TIME_REMAIN");
					entity.numberDayUse = rec.getDouble("NUMBER_DAYS_USE");
					entity.timeUse = rec.getInt("TIME_USE");
					entity.useSavingDays = rec.getDouble("USED_SAVING_DAYS");
					entity.numberOverDays = rec.getDouble("NUMBER_OVER_DAYS");
					entity.timeOver = rec.getInt("TIME_OVER");
					return entity;
				});
				entities.addAll(result);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		return entities.stream()
				.map(x -> SpecialLeaveGrantRemainingData.createFromJavaType(x.specialLeaID, x.cId, x.employeeId,
						x.specialLeaCode, x.grantDate, x.deadlineDate, x.expStatus, x.registerType, x.numberDayGrant,
						x.timeGrant, x.numberDayUse, x.timeUse, x.useSavingDays, x.numberOverDays, x.timeOver,
						x.numberDayRemain, x.timeRemain))
				.collect(Collectors.toList());

	}

	@Override
	public void addAll(List<SpecialLeaveGrantRemainingData> domains) {
		String INS_SQL = "INSERT INTO KRCMT_SPEC_LEAVE_REMAIN (INS_DATE, INS_CCD , INS_SCD , INS_PG,"
				+ " UPD_DATE , UPD_CCD , UPD_SCD , UPD_PG," 
				+ " SPECIAL_LEAVE_ID, CID, SID, SPECIAL_LEAVE_CD, GRANT_DATE, DEADLINE_DATE,"
				+ " EXPIRED_STATE, REGISTRATION_TYPE, NUMBER_DAYS_GRANT, TIME_GRANT, NUMBER_DAYS_REMAIN,"
				+ " TIME_REMAIN, NUMBER_DAYS_USE, TIME_USE, USED_SAVING_DAYS, NUMBER_OVER_DAYS, TIME_OVER)"
				+ " VALUES (INS_DATE_VAL, INS_CCD_VAL, INS_SCD_VAL, INS_PG_VAL,"
				+ " UPD_DATE_VAL, UPD_CCD_VAL, UPD_SCD_VAL, UPD_PG_VAL,"
				+ " SPECIAL_LEAVE_ID_VAL, CID_VAL, SID_VAL, SPECIAL_LEAVE_CD_VAL, GRANT_DATE_VAL, DEADLINE_DATE_VAL,"
				+ " EXPIRED_STATE_VAL, REGISTRATION_TYPE_VAL, NUMBER_DAYS_GRANT_VAL, TIME_GRANT_VAL, NUMBER_DAYS_REMAIN_VAL,"
				+ " TIME_REMAIN_VAL, NUMBER_DAYS_USE_VAL, TIME_USE_VAL, USED_SAVING_DAYS_VAL, NUMBER_OVER_DAYS_VAL, TIME_OVER_VAL);";
		String insCcd = AppContexts.user().companyCode();
		String insScd = AppContexts.user().employeeCode();
		String insPg = AppContexts.programId();
		
		String updCcd = insCcd;
		String updScd = insScd;
		String updPg = insPg;
		StringBuilder sb = new StringBuilder();
		domains.stream().forEach(c -> {
			String sql = INS_SQL;
			sql = sql.replace("INS_DATE_VAL", "'" + GeneralDateTime.now() + "'");
			sql = sql.replace("INS_CCD_VAL", "'" + insCcd + "'");
			sql = sql.replace("INS_SCD_VAL", "'" + insScd + "'");
			sql = sql.replace("INS_PG_VAL", "'" + insPg + "'");

			sql = sql.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() + "'");
			sql = sql.replace("UPD_CCD_VAL", "'" + updCcd + "'");
			sql = sql.replace("UPD_SCD_VAL", "'" + updScd + "'");
			sql = sql.replace("UPD_PG_VAL", "'" + updPg + "'");

			sql = sql.replace("SPECIAL_LEAVE_ID_VAL", "'" +  c.getSpecialId() + "'");
			sql = sql.replace("CID_VAL", "'" + c.getCId() + "'");
			sql = sql.replace("SID_VAL", "'" + c.getEmployeeId() + "'");
			sql = sql.replace("SPECIAL_LEAVE_CD_VAL", ""+ c.getSpecialLeaveCode().v()+ "");
			
			sql = sql.replace("EXPIRED_STATE_VAL", ""+ c.getExpirationStatus().value +"");
			sql = sql.replace("REGISTRATION_TYPE_VAL", "" + c.getRegisterType().value + "");
			
			sql = sql.replace("GRANT_DATE_VAL",  "'" + c.getGrantDate() + "'");
			sql = sql.replace("DEADLINE_DATE_VAL", "'" + c.getDeadlineDate() + "'");
			
			// grant data
			sql = sql.replace("NUMBER_DAYS_GRANT", ""+ c.getDetails().getGrantNumber().getDayNumberOfGrant().v() +"");
			sql = sql.replace("TIME_GRANT", c.getDetails().getGrantNumber().getTimeOfGrant().isPresent()
					? ""+  c.getDetails().getGrantNumber().getTimeOfGrant().get().v() + "" : "0");
			
			// remain data
			sql = sql.replace("NUMBER_DAYS_REMAIN",  "" + c.getDetails().getRemainingNumber().getDayNumberOfRemain().v() + "");
			sql = sql.replace("TIME_REMAIN",  c.getDetails().getRemainingNumber().getTimeOfRemain().isPresent()
					? "" + c.getDetails().getRemainingNumber().getTimeOfRemain().get().v() + "" : "0");
			
			// use data
			sql = sql.replace("NUMBER_DAYS_USE",  "" + c.getDetails().getUsedNumber().getDayNumberOfUse().v() + "");
			sql = sql.replace("TIME_USE",  c.getDetails().getUsedNumber().getTimeOfUse().isPresent()
					? ""+ c.getDetails().getUsedNumber().getTimeOfUse().get().v() +"" : "0");
			
			// use Saving data(tai lieu đang bảo truyền null vào)
			// entity.useSavingDays =
			// data.getDetails().getUsedNumber().getUseSavingDays().isPresent()
			// ? data.getDetails().getUsedNumber().getUseSavingDays().get().v()
			// : 0;
			sql = sql.replace("USED_SAVING_DAYS",  "0d");
			// Over
			
			if (c.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().isPresent()) {
				sql = sql.replace("NUMBER_OVER_DAYS",  "" + c.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().get()
						.getNumberOverDays().v() + "");
				sql = sql.replace("TIME_OVER",  c.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().get().getTimeOver()
						.isPresent()
						? ""+ c.getDetails().getUsedNumber().getSpecialLeaveOverLimitNumber().get().getTimeOver()
								.get().v() + "" : "0");
			}
			sb.append(sql);
		});

		int records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
		System.out.println(records);
		
	}

	@Override
	public List<Object[]> getAllBySids(List<String> sids, int specialLeaveCD) {
		List<Object[]> entities = new ArrayList<>();
		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String sql = "SELECT * FROM KRCMT_SPEC_LEAVE_REMAIN WHERE  SPECIAL_LEAVE_CD = ? AND SID IN ("
					+ NtsStatement.In.createParamsString(subList) + ")";
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				stmt.setInt(1, specialLeaveCD);

				for (int i = 0; i < subList.size(); i++) {
					stmt.setString(2 + i, subList.get(i));
				}
				List<Object[]> result = new NtsResultSet(stmt.executeQuery()).getList(rec -> {
					Object[] object = new Object[] {rec.getString("SPECIAL_LEAVE_ID"),
							rec.getString("CID"), 
							rec.getString("SID"),
							rec.getInt("SPECIAL_LEAVE_CD"),
							rec.getGeneralDate("GRANT_DATE"),
							rec.getGeneralDate("DEADLINE_DATE"),
							rec.getInt("EXPIRED_STATE"),
							rec.getInt("REGISTRATION_TYPE"),
							rec.getDouble("NUMBER_DAYS_GRANT"),
							rec.getInt("TIME_GRANT"),
							rec.getDouble("NUMBER_DAYS_REMAIN"),
							rec.getInt("TIME_REMAIN"),
							rec.getDouble("NUMBER_DAYS_USE"),
							rec.getInt("TIME_USE"),
							rec.getDouble("USED_SAVING_DAYS"),
							rec.getDouble("NUMBER_OVER_DAYS"),
							rec.getInt("TIME_OVER")};
					return object;
				});
				entities.addAll(result);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		return entities;
	}

}
