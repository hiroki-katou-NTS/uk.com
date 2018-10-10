package nts.uk.ctx.at.record.infra.repository.monthly.remarks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.monthly.remarks.RemarksMonthlyRecord;
import nts.uk.ctx.at.record.dom.monthly.remarks.RemarksMonthlyRecordRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.remarks.KrcdtRemarksMonthlyRecord;
import nts.uk.ctx.at.record.infra.entity.monthly.remarks.KrcdtRemarksMonthlyRecordPK;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class JpaRemarksMonthlyRecordRepository extends JpaRepository implements RemarksMonthlyRecordRepository{
	
	private static final String FIND_BY_YEAR_MONTH = "SELECT a FROM KrcdtRemarksMonthlyRecord a "
			+ "WHERE a.recordPK.employeeId = :employeeId "
			+ "AND a.recordPK.yearMonth = :yearMonth "
			+ "ORDER BY a.startYmd ";

	private static final String FIND_BY_YM_AND_CLOSURE_ID = "SELECT a FROM KrcdtRemarksMonthlyRecord a "
			+ "WHERE a.recordPK.employeeId = :employeeId "
			+ "AND a.recordPK.yearMonth = :yearMonth "
			+ "AND a.recordPK.closureId = :closureId "
			+ "ORDER BY a.startYmd ";

	private static final String FIND_BY_EMPLOYEES = "SELECT a FROM KrcdtRemarksMonthlyRecord a "
			+ "WHERE a.recordPK.employeeId IN :employeeIds "
			+ "AND a.recordPK.yearMonth = :yearMonth "
			+ "AND a.recordPK.closureId = :closureId "
			+ "AND a.recordPK.closureDay = :closureDay "
			+ "AND a.recordPK.isLastDay = :isLastDay "
			+ "ORDER BY a.recordPK.employeeId ";

	private static final String FIND_BY_SIDS_AND_YEARMONTHS = "SELECT a FROM KrcdtRemarksMonthlyRecord a "
			+ "WHERE a.recordPK.employeeId IN :employeeIds "
			+ "AND a.recordPK.yearMonth IN :yearMonths "
			+ "ORDER BY a.recordPK.employeeId, a.startYmd ";
	
	private static final String FIND_BY_PERIOD = "SELECT a FROM KrcdtRemarksMonthlyRecord a "
			+ "WHERE a.recordPK.employeeId = :employeeId "
			+ "AND a.startYmd <= :endDate "
			+ "AND a.endYmd >= :startDate ";
	
	private static final String FIND_BY_PERIOD_INTO_END = "SELECT a FROM KrcdtRemarksMonthlyRecord a "
			+ "WHERE a.recordPK.employeeId = :employeeId "
			+ "AND a.endYmd >= :startDate "
			+ "AND a.endYmd <= :endDate "
			+ "ORDER BY a.startYmd ";
	
	private static final String DELETE_BY_PK = "DELETE FROM KrcdtRemarksMonthlyRecord a "
			+ "WHERE a.recordPK.employeeId = :employeeId "
			+ "AND a.recordPK.yearMonth = :yearMonth "
			+ "AND a.recordPK.closureId = :closureId "
			+ "AND a.recordPK.closureDay = :closureDay "
			+ "AND a.recordPK.isLastDay = :isLastDay ";
	
	private static final String DELETE_BY_YEAR_MONTH = "DELETE FROM KrcdtRemarksMonthlyRecord a "
			+ "WHERE a.recordPK.employeeId = :employeeId "
			+ "AND a.recordPK.yearMonth = :yearMonth ";

	@Override
	public Optional<RemarksMonthlyRecord> find(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		return this.queryProxy()
				.find(new KrcdtRemarksMonthlyRecordPK(
						employeeId,
						yearMonth.v(),
						closureId.value,
						closureDate.getClosureDay().v(),
						(closureDate.getLastDayOfMonth() ? 1 : 0)),
						KrcdtRemarksMonthlyRecord.class)
				.map(c -> c.toDomain());
	}

	@Override
	public List<RemarksMonthlyRecord> findByYearMonthOrderByStartYmd(String employeeId, YearMonth yearMonth) {
		return this.queryProxy().query(FIND_BY_YEAR_MONTH, KrcdtRemarksMonthlyRecord.class)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.getList(c -> c.toDomain());
	}

	@Override
	public List<RemarksMonthlyRecord> findByYMAndClosureIdOrderByStartYmd(String employeeId, YearMonth yearMonth,
			ClosureId closureId) {
		return this.queryProxy().query(FIND_BY_YM_AND_CLOSURE_ID, KrcdtRemarksMonthlyRecord.class)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.setParameter("closureId", closureId.value)
				.getList(c -> c.toDomain());
	}

	@Override
	public List<RemarksMonthlyRecord> findByEmployees(List<String> employeeIds, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate) {
		List<RemarksMonthlyRecord> results = new ArrayList<>();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			results.addAll(this.queryProxy().query(FIND_BY_EMPLOYEES, KrcdtRemarksMonthlyRecord.class)
					.setParameter("employeeIds", splitData)
					.setParameter("yearMonth", yearMonth.v())
					.setParameter("closureId", closureId.value)
					.setParameter("closureDay", closureDate.getClosureDay().v())
					.setParameter("isLastDay", (closureDate.getLastDayOfMonth() ? 1 : 0))
					.getList(c -> c.toDomain()));
		});
		return results;
	}

	@Override
	public List<RemarksMonthlyRecord> findBySidsAndYearMonths(List<String> employeeIds, List<YearMonth> yearMonths) {
		val yearMonthValues = yearMonths.stream().map(c -> c.v()).collect(Collectors.toList());
		
		List<RemarksMonthlyRecord> results = new ArrayList<>();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			CollectionUtil.split(yearMonthValues, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, lstYearMonth -> {
				results.addAll(this.queryProxy().query(FIND_BY_SIDS_AND_YEARMONTHS, KrcdtRemarksMonthlyRecord.class)
						.setParameter("employeeIds", splitData)
						.setParameter("yearMonths", lstYearMonth)
						.getList(c -> c.toDomain()));
			});
		});
		return results;
	}

	@Override
	public List<RemarksMonthlyRecord> findByDate(String employeeId, GeneralDate criteriaDate) {
		return this.queryProxy().query(FIND_BY_PERIOD, KrcdtRemarksMonthlyRecord.class)
				.setParameter("employeeId", employeeId)
				.setParameter("startDate", criteriaDate)
				.setParameter("endDate", criteriaDate)
				.getList(c -> c.toDomain());
	}

	@Override
	public List<RemarksMonthlyRecord> findByPeriodIntoEndYmd(String employeeId, DatePeriod period) {
		return this.queryProxy().query(FIND_BY_PERIOD_INTO_END, KrcdtRemarksMonthlyRecord.class)
				.setParameter("employeeId", employeeId)
				.setParameter("startDate", period.start())
				.setParameter("endDate", period.end())
				.getList(c -> c.toDomain());
	}

	@Override
	public void persistAndUpdate(RemarksMonthlyRecord remarksMonthlyRecord) {
				// キー
				val key = new KrcdtRemarksMonthlyRecordPK(
						remarksMonthlyRecord.getEmployeeId(),
						remarksMonthlyRecord.getClosureId().value,
						remarksMonthlyRecord.getRemarksNo(),
						remarksMonthlyRecord.getRemarksYM().v(),
						remarksMonthlyRecord.getClosureDate().getClosureDay().v());
				
				// 登録・更新
				KrcdtRemarksMonthlyRecord entity = this.getEntityManager().find(KrcdtRemarksMonthlyRecord.class, key);
				if (entity == null){
					entity = new KrcdtRemarksMonthlyRecord();
					entity.setRecordPK(key);
					entity.toEntityCareRemainData(remarksMonthlyRecord);
					this.getEntityManager().persist(entity);
				}
				else {
					entity.toEntityCareRemainData(remarksMonthlyRecord);
				}
	}

	@Override
	public void remove(String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate) {
		this.getEntityManager().createQuery(DELETE_BY_PK)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.setParameter("closureId", closureId.value)
				.setParameter("closureDay", closureDate.getClosureDay().v())
				.setParameter("isLastDay", (closureDate.getLastDayOfMonth() ? 1 : 0))
				.executeUpdate();
	}

	@Override
	public void removeByYearMonth(String employeeId, YearMonth yearMonth) {
		this.getEntityManager().createQuery(DELETE_BY_YEAR_MONTH)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.executeUpdate();
	}

}
