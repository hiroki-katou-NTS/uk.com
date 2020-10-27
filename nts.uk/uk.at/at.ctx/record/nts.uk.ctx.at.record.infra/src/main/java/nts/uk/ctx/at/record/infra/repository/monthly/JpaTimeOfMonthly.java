package nts.uk.ctx.at.record.infra.repository.monthly;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.SneakyThrows;
import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.monthly.TimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.TimeOfMonthlyRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.mergetable.KrcdtMonTimeAtd;
import nts.uk.ctx.at.record.infra.entity.monthly.mergetable.KrcdtMonTimeAtdPk;
import nts.uk.ctx.at.record.infra.entity.monthly.mergetable.KrcdtMonTimeSup;
import nts.uk.ctx.at.record.infra.entity.monthly.mergetable.KrcdtMonVerticalTotal;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * リポジトリ実装：月別実績の勤怠時間
 * @author shuichi_ishida
 */
@Stateless
public class JpaTimeOfMonthly extends JpaRepository implements TimeOfMonthlyRepository {

	private static final String SEL_NO_WHERE = "SELECT a FROM KrcdtMonTimeAtd a";
	private static final String FIND_BY_YEAR_MONTH = String.join(" ", SEL_NO_WHERE,
			"WHERE a.krcdtMonTimeAtdPk.employeeId =:employeeId",
			"AND   a.krcdtMonTimeAtdPk.yearMonth =:yearMonth",
			"ORDER BY a.startYmd");
	private static final String FIND_BY_YM_AND_CLOSURE_ID = String.join(" ", SEL_NO_WHERE,
			"WHERE a.krcdtMonTimeAtdPk.employeeId =:employeeId",
			"AND   a.krcdtMonTimeAtdPk.yearMonth =:yearMonth",
			"AND   a.krcdtMonTimeAtdPk.closureId =:closureId",
			"ORDER BY a.startYmd");
	private static final String FIND_BY_EMPLOYEES = String.join(" ", SEL_NO_WHERE,
			"WHERE a.krcdtMonTimeAtdPk.employeeId IN :employeeIds",
			"AND   a.krcdtMonTimeAtdPk.yearMonth =:yearMonth",
			"AND   a.krcdtMonTimeAtdPk.closureId =:closureId",
			"AND   a.krcdtMonTimeAtdPk.closureDay =:closureDay",
			"AND   a.krcdtMonTimeAtdPk.isLastDay =:isLastDay",
			"ORDER BY a.krcdtMonTimeAtdPk.employeeId");
	private static final String FIND_BY_EMPLOYEES_AND_CLOSURE = String.join(" ", SEL_NO_WHERE,
			"WHERE a.krcdtMonTimeAtdPk.employeeId IN :employeeIds",
			"AND   a.krcdtMonTimeAtdPk.yearMonth =:yearMonth",
			"AND   a.krcdtMonTimeAtdPk.closureId =:closureId",
			"ORDER BY a.krcdtMonTimeAtdPk.employeeId");
	private static final String FIND_BY_SIDS_AND_YEARMONTHS = String.join(" ", SEL_NO_WHERE,
			"WHERE a.krcdtMonTimeAtdPk.employeeId IN :employeeIds",
			"AND   a.krcdtMonTimeAtdPk.yearMonth IN :yearMonths",
			"ORDER BY a.krcdtMonTimeAtdPk.employeeId, a.krcdtMonTimeAtdPk.yearMonth, a.startYmd");
	private static final String FIND_BY_PERIOD = String.join(" ", SEL_NO_WHERE,
			 "WHERE a.krcdtMonTimeAtdPk.employeeId = :employeeId ",
			 "AND a.startYmd <= :endDate ",
			 "AND a.endYmd >= :startDate ");
	private static final String DELETE_BY_PK = String.join(" ", "DELETE FROM KrcdtMonTimeAtd a ",
			"WHERE  a.krcdtMonTimeAtdPk.employeeId = :employeeId ",
			"AND    a.krcdtMonTimeAtdPk.yearMonth = :yearMonth ",
			"AND    a.krcdtMonTimeAtdPk.closureId = :closureId",
			"AND    a.krcdtMonTimeAtdPk.closureDay = :closureDay",
			"AND    a.krcdtMonTimeAtdPk.isLastDay = :isLastDay");
	private static final String DELETE_OUEN_BY_PK = String.join(" ", "DELETE FROM KrcdtMonTimeSup a ",
			"WHERE  a.id.employeeId = :employeeId ",
			"AND    a.id.yearMonth = :yearMonth ",
			"AND    a.id.closureId = :closureId",
			"AND    a.id.closureDay = :closureDay",
			"AND    a.id.isLastDay = :isLastDay");
	private static final String DELETE_BY_YEAR_MONTH = String.join(" ", "DELETE FROM KrcdtMonTimeAtd a ",
			 "WHERE  a.krcdtMonTimeAtdPk.employeeId = :employeeId ",
			 "AND 	 a.krcdtMonTimeAtdPk.yearMonth = :yearMonth ");
	private static final String DELETE_OUEN_BY_YEAR_MONTH = String.join(" ", "DELETE FROM KrcdtMonTimeSup a ",
			 "WHERE  a.id.employeeId = :employeeId ",
			 "AND 	 a.id.yearMonth = :yearMonth ");	
	private static final String FIND_BY_PERIOD_INTO_END = "SELECT a FROM KrcdtMonTimeAtd a "
			+ "WHERE a.krcdtMonTimeAtdPk.employeeId = :employeeId "
			+ "AND a.endYmd >= :startDate "
			+ "AND a.endYmd <= :endDate "
			+ "ORDER BY a.startYmd ";

	/** 検索 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public Optional<TimeOfMonthly> find(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate) {
		val key = new KrcdtMonTimeAtdPk(
				employeeId,
				yearMonth.v(),
				closureId.value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));
		return this.queryProxy().find(key, KrcdtMonTimeAtd.class)
				.map(c -> toDomain(c, getOuen(c.krcdtMonTimeAtdPk), getVertical(c.krcdtMonTimeAtdPk)));
	}

	/** 検索　（年月） */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	@SneakyThrows
	public List<TimeOfMonthly> findByYearMonthOrderByStartYmd(String employeeId, YearMonth yearMonth) {

		String sql = "select * from KRCDT_MON_TIME_ATD"
				+ " where SID = ?"
				+ " and YM = ?"
				+ " order by START_YMD";
		try (val stmt = this.connection().prepareStatement(sql)) {
			stmt.setString(1, employeeId);
			stmt.setInt(2, yearMonth.v());
			
			List<KrcdtMonTimeAtd> entities = new NtsResultSet(stmt.executeQuery()).getList(rec -> KrcdtMonTimeAtd.MAPPER.toEntity(rec));
			return entities.stream().map(e -> toDomain(e, getOuen(e.krcdtMonTimeAtdPk), getVertical(e.krcdtMonTimeAtdPk)))
									.collect(Collectors.toList());
		}
	}
	
	/** 検索　（年月と締めID） */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<TimeOfMonthly> findByYMAndClosureIdOrderByStartYmd(String employeeId, YearMonth yearMonth,
			ClosureId closureId) {
		return this.queryProxy().query(FIND_BY_YM_AND_CLOSURE_ID, KrcdtMonTimeAtd.class)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.setParameter("closureId", closureId.value)
				.getList(c -> toDomain(c, getOuen(c.krcdtMonTimeAtdPk), getVertical(c.krcdtMonTimeAtdPk)));
	}

	/** 検索　（社員IDリスト） */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<TimeOfMonthly> findByEmployees(List<String> employeeIds, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate) {
		
		List<TimeOfMonthly> results = new ArrayList<>();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			results.addAll(this.queryProxy().query(FIND_BY_EMPLOYEES, KrcdtMonTimeAtd.class)
					.setParameter("employeeIds", splitData)
					.setParameter("yearMonth", yearMonth.v())
					.setParameter("closureId", closureId.value)
					.setParameter("closureDay", closureDate.getClosureDay().v())			
					.setParameter("isLastDay", (closureDate.getLastDayOfMonth() ? 1 : 0))
					.getList(c -> toDomain(c, getOuen(c.krcdtMonTimeAtdPk), getVertical(c.krcdtMonTimeAtdPk))));
		});
		results.sort(Comparator.comparing(TimeOfMonthly::getEmployeeId));
		return results;
	}
	
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<TimeOfMonthly> findByEmployeesAndClorure(List<String> employeeIds, YearMonth yearMonth,
			int closureId) {
		if(employeeIds.isEmpty())
			return Collections.emptyList();
		List<TimeOfMonthly> results = new ArrayList<>();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			results.addAll(this.queryProxy().query(FIND_BY_EMPLOYEES_AND_CLOSURE, KrcdtMonTimeAtd.class)
					.setParameter("employeeIds", splitData)
					.setParameter("yearMonth", yearMonth.v())
					.setParameter("closureId", closureId)
					.getList(c -> toDomain(c, getOuen(c.krcdtMonTimeAtdPk), getVertical(c.krcdtMonTimeAtdPk))));
		});
		results.sort(Comparator.comparing(TimeOfMonthly::getEmployeeId));
		return results;
	}
	
	/** 検索　（社員IDリストと年月リスト） */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<TimeOfMonthly> findBySidsAndYearMonths(List<String> employeeIds, List<YearMonth> yearMonths) {
		
		val yearMonthValues = yearMonths.stream().map(c -> c.v()).collect(Collectors.toList());
		
		String sql = "select * from KRCDT_MON_TIME_ATD"
				+ " where SID in @emps"
				+ " and YM in @yms";
		
		List<KrcdtMonTimeAtd> results = NtsStatement.In.split(employeeIds, emps -> {
			return new NtsStatement(sql, this.jdbcProxy())
					.paramString("emps", emps)
					.paramInt("yms", yearMonthValues)
					.getList(rec -> KrcdtMonTimeAtd.MAPPER.toEntity(rec));
		});
		
		results.sort((o1, o2) -> {
			int tmp = o1.getKrcdtMonTimeAtdPk().getEmployeeId().compareTo(o2.getKrcdtMonTimeAtdPk().getEmployeeId());
			if (tmp != 0) return tmp;
			tmp = o1.getKrcdtMonTimeAtdPk().getYearMonth() - o2.getKrcdtMonTimeAtdPk().getYearMonth();
			if (tmp != 0) return tmp;	
			return o1.getStartYmd().compareTo(o2.getStartYmd());
		});
		return results.stream().map(item -> toDomain(item, 
														getOuen(item.krcdtMonTimeAtdPk), 
														getVertical(item.krcdtMonTimeAtdPk)))
								.collect(Collectors.toList());
	}
		
	/** 検索　（基準日） */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override				
	public List<TimeOfMonthly> findByDate(String employeeId, GeneralDate criteriaDate) {
		return this.queryProxy().query(FIND_BY_PERIOD, KrcdtMonTimeAtd.class)
				.setParameter("employeeId", employeeId)
				.setParameter("startDate", criteriaDate)
				.setParameter("endDate", criteriaDate)
				.getList(c -> toDomain(c, getOuen(c.krcdtMonTimeAtdPk), getVertical(c.krcdtMonTimeAtdPk)));
	}
	
	/** 検索　（終了日を含む期間） */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<TimeOfMonthly> findByPeriodIntoEndYmd(String employeeId, DatePeriod period) {
    	
		return this.queryProxy().query(FIND_BY_PERIOD_INTO_END, KrcdtMonTimeAtd.class)
				.setParameter("employeeId", employeeId)
				.setParameter("startDate", period.start())
				.setParameter("endDate", period.end())
				.getList(c -> toDomain(c, getOuen(c.krcdtMonTimeAtdPk), getVertical(c.krcdtMonTimeAtdPk)));
	}
			
	/** 登録および更新 */
	@Override
	public void persistAndUpdate(TimeOfMonthly domain){

		// 締め日付
		val closureDate = domain.getClosureDate();
		
		// キー
		val key = new KrcdtMonTimeAtdPk(
				domain.getEmployeeId(),
				domain.getYearMonth().v(),
				domain.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));		
		
		// 登録・更新を判断　および　キー値設定
		boolean isNeedPersist = false;
		
		KrcdtMonTimeAtd entity = this.getEntityManager().find(KrcdtMonTimeAtd.class, key);
		KrcdtMonTimeSup ouenEntity = getOuen(key);
		KrcdtMonVerticalTotal verticalEntity = getVertical(key);
		if (entity == null){
			isNeedPersist = true;
			entity = new KrcdtMonTimeAtd();
			entity.krcdtMonTimeAtdPk = key;
			
			ouenEntity = new KrcdtMonTimeSup();
			ouenEntity.id = key;
			
			verticalEntity = new KrcdtMonVerticalTotal();
			verticalEntity.id = key;
		}
		
		if(domain.getAttendanceTime().isPresent()){
			entity.toEntityAttendanceTimeOfMonthly(domain.getAttendanceTime().get(), ouenEntity, verticalEntity);
		}
		if(domain.getAffiliation().isPresent()){
			entity.toEntityAffiliationInfoOfMonthly(domain.getAffiliation().get());
		}
		
		// 登録が必要な時、登録を実行
		if (isNeedPersist) {
			this.getEntityManager().persist(entity);
			this.getEntityManager().persist(ouenEntity);
		}
	}
	
	/** 削除 */
	@Override
	public void remove(String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate) {
		
		this.getEntityManager().createQuery(DELETE_BY_PK)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.setParameter("closureId", closureId.value)
				.setParameter("closureDay", closureDate.getClosureDay().v())
				.setParameter("isLastDay", (closureDate.getLastDayOfMonth() ? 1 : 0))
				.executeUpdate();
		

		this.getEntityManager().createQuery(DELETE_OUEN_BY_PK)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.setParameter("closureId", closureId.value)
				.setParameter("closureDay", closureDate.getClosureDay().v())
				.setParameter("isLastDay", (closureDate.getLastDayOfMonth() ? 1 : 0))
				.executeUpdate();
		
	}
		
	/** 削除　（年月） */
	@Override
	public void removeByYearMonth(String employeeId, YearMonth yearMonth) {
		
		this.getEntityManager().createQuery(DELETE_BY_YEAR_MONTH)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.executeUpdate();

		this.getEntityManager().createQuery(DELETE_OUEN_BY_YEAR_MONTH)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.executeUpdate();
		
	}
	
	@Override
	public void verShouldUp(String employeeId, YearMonth yearMonth, int closureId, int closureDate, boolean lastOfMonth) {
		
		// キー
		val key = new KrcdtMonTimeAtdPk(
				employeeId,
				yearMonth.v(),
				closureId,
				closureDate,
				lastOfMonth ? 1 : 0);		
		
		dirtying(() -> key);
	}
	
	public void verShouldUp(String employeeId, YearMonth yearMonth, int closureId, int closureDate, boolean lastOfMonth, long version) {
		
		// キー
		val key = new KrcdtMonTimeAtdPk(
				employeeId,
				yearMonth.v(),
				closureId,
				closureDate,
				lastOfMonth ? 1 : 0);		
		
		this.queryProxy().find(key, KrcdtMonTimeAtd.class).ifPresent(entity -> {
			entity.version = version;
			this.commandProxy().update(entity);
		});
	}
	
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	@SneakyThrows
	public long getVer(String employeeId, YearMonth yearMonth, int closureId, int closureDate, boolean lastOfMonth) {
		try (PreparedStatement stmtFindById = this.connection().prepareStatement(
				"SELECT EXCLUS_VER from KRCDT_MON_TIME_ATD"
				+ " WHERE SID = ? AND YM = ? AND CLOSURE_ID = ? AND CLOSURE_DAY = ? AND IS_LAST_DAY = ?")) {
			stmtFindById.setString(1, employeeId);
			stmtFindById.setInt(2, yearMonth.v());
			stmtFindById.setInt(3, closureId);
			stmtFindById.setInt(4, closureDate);
			stmtFindById.setInt(5, lastOfMonth ? 1 : 0);

			return new NtsResultSet(stmtFindById.executeQuery()).getSingle(rec -> {
				return rec.getLong(1);
			}).orElse(0L);
		}
	}

	private TimeOfMonthly toDomain(KrcdtMonTimeAtd c, KrcdtMonTimeSup ouen, KrcdtMonVerticalTotal vertical) {
		return new TimeOfMonthly(c.toDomainAttendanceTimeOfMonthly(ouen, vertical), 
									c.toDomainAffiliationInfoOfMonthly());
	}

	@Override
	public void removeAffiliation(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		
		// キー
		val key = new KrcdtMonTimeAtdPk(
				employeeId,
				yearMonth.v(),
				closureId.value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));		
		
		// 登録・更新を判断　および　キー値設定
		KrcdtMonTimeAtd entity = queryProxy().find(key, KrcdtMonTimeAtd.class).orElse(null);
		if (entity != null){
			entity.resetAffiliationInfo();
		}
	}

	@Override
	public void removeAttendanceTime(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		// キー
		val key = new KrcdtMonTimeAtdPk(
				employeeId,
				yearMonth.v(),
				closureId.value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));		
		
		// 登録・更新を判断　および　キー値設定
		KrcdtMonTimeAtd entity = queryProxy().find(key, KrcdtMonTimeAtd.class).orElse(null);
		if (entity != null){
			entity.resetAttendanceTime();
		}
		
	}

	@Override
	public void removeAffiliation(String employeeId, YearMonth yearMonth) {
		this.queryProxy().query(FIND_BY_YEAR_MONTH, KrcdtMonTimeAtd.class)
						.setParameter("employeeId", employeeId)
						.setParameter("yearMonth", yearMonth.v())
						.getList().stream().forEach(c -> c.resetAffiliationInfo());
	}

	@Override
	public void removeAttendanceTime(String employeeId, YearMonth yearMonth) {
		this.queryProxy().query(FIND_BY_YEAR_MONTH, KrcdtMonTimeAtd.class)
						.setParameter("employeeId", employeeId)
						.setParameter("yearMonth", yearMonth.v())
						.getList().stream().forEach(c -> c.resetAttendanceTime());
	}

	public void dirtying(Supplier<Object> getKey) {
		
		this.queryProxy().find(getKey.get(), KrcdtMonTimeAtd.class).ifPresent(entity -> {
			entity.dirtying();
			this.commandProxy().update(entity);
		});
	}

	private KrcdtMonTimeSup getOuen(KrcdtMonTimeAtdPk id) {
		KrcdtMonTimeSup entity = this.getEntityManager().find(KrcdtMonTimeSup.class, id);
		
		if (entity == null) {
			entity = new KrcdtMonTimeSup();
			entity.id = id;
		}
		return entity;
	}

	private KrcdtMonVerticalTotal getVertical(KrcdtMonTimeAtdPk id) {
		KrcdtMonVerticalTotal entity = this.getEntityManager().find(KrcdtMonVerticalTotal.class, id);
		
		if (entity == null) {
			entity = new KrcdtMonVerticalTotal();
			entity.id = id;
		}
		return entity;
	}
}
