package nts.uk.ctx.at.record.infra.repository.monthly.specialholiday;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.ActualSpecialLeave;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialHolidayRemainData;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialHolidayRemainDataRepository;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialLeavaRemainTime;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialLeave;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialLeaveGrantUseDay;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialLeaveRemain;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialLeaveRemainDay;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialLeaveUnDigestion;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialLeaveUseDays;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialLeaveUseNumber;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialLeaveUseTimes;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.UseNumber;
import nts.uk.ctx.at.record.infra.entity.monthly.specialholiday.KrcdtMonSpRemain;
import nts.uk.ctx.at.record.infra.entity.monthly.specialholiday.KrcdtMonSpRemainPK;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class JpaSpecialHolidayRemainDataRepo extends JpaRepository implements SpecialHolidayRemainDataRepository{
	
	private static final String SQL_BY_YM_STATUS = "SELECT c FROM KrcdtMonSpRemain c"
			+ " WHERE c.pk.sid = :sid"
			+ " AND c.pk.ym = :ym"
			+ " AND c.closureStatus = :status";
	
	private static final String FIND_BY_CLOSURE = "SELECT a FROM KrcdtMonSpRemain a "
			+ "WHERE a.pk.sid = :employeeId "
			+ "AND a.pk.ym = :yearMonth "
			+ "AND a.pk.closureId = :closureId "
			+ "AND a.pk.closureDay = :closureDay "
			+ "AND a.pk.chkLastDay = :chkLastDay "
			+ "ORDER BY a.closureStartDate, a.pk.specialHolidayCd ";
	
	private static final String FIND_BY_YEAR_MONTH = "SELECT a FROM KrcdtMonSpRemain a "
			+ "WHERE a.pk.sid = :employeeId "
			+ "AND a.pk.ym = :yearMonth "
			+ "ORDER BY a.closureStartDate, a.pk.specialHolidayCd ";

	private static final String FIND_BY_SIDS_AND_MONTHS = "SELECT a FROM KrcdtMonSpRemain a "
			+ "WHERE a.pk.sid IN :employeeIds "
			+ "AND a.pk.ym IN :yearMonths "
			+ "ORDER BY a.pk.sid, a.closureStartDate, a.pk.specialHolidayCd ";

	private static final String DELETE_BY_CLOSURE = "DELETE FROM KrcdtMonSpRemain a "
			+ "WHERE a.pk.sid = :employeeId "
			+ "AND a.pk.ym = :yearMonth "
			+ "AND a.pk.closureId = :closureId "
			+ "AND a.pk.closureDay = :closureDay "
			+ "AND a.pk.chkLastDay = :chkLastDay ";

	private static final String DELETE_BY_YEAR_MONTH = "DELETE FROM KrcdtMonSpRemain a "
			+ "WHERE a.pk.sid = :employeeId "
			+ "AND a.pk.ym = :yearMonth ";
	
	/** 取得 */
	@Override
	public List<SpecialHolidayRemainData> getByYmStatus(String sid, YearMonth ym, ClosureStatus status) {
		return this.queryProxy().query(SQL_BY_YM_STATUS, KrcdtMonSpRemain.class)
				.setParameter("sid", sid)
				.setParameter("ym", ym.v())
				.setParameter("status", status.value)				
				.getList(c -> toDomain(c));
	}
	
	/** 検索 */
	// add 2018.9.13 shuichi_ishida
	@Override
	public List<SpecialHolidayRemainData> find(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		
		return this.queryProxy().query(FIND_BY_CLOSURE, KrcdtMonSpRemain.class)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.setParameter("closureId", closureId.value)
				.setParameter("closureDay", closureDate.getClosureDay().v())
				.setParameter("chkLastDay", (closureDate.getLastDayOfMonth() ? 1 : 0))
				.getList(c -> toDomain(c));
	}
	
	/** 検索 */
	// add 2018.8.24 shuichi_ishida
	@Override
	public List<SpecialHolidayRemainData> findByYearMonthOrderByStartYmd(String employeeId, YearMonth yearMonth) {
		
		return this.queryProxy().query(FIND_BY_YEAR_MONTH, KrcdtMonSpRemain.class)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.getList(c -> toDomain(c));
	}
	
	/** 検索 */
	// add 2018.8.30 shuichi_ishida
	@Override
	public List<SpecialHolidayRemainData> findBySidsAndYearMonths(List<String> employeeIds, List<YearMonth> yearMonths) {
		
		val yearMonthValues = yearMonths.stream().map(c -> c.v()).collect(Collectors.toList());
		
		List<SpecialHolidayRemainData> results = new ArrayList<>();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			results.addAll(this.queryProxy().query(FIND_BY_SIDS_AND_MONTHS, KrcdtMonSpRemain.class)
					.setParameter("employeeIds", splitData)
					.setParameter("yearMonths", yearMonthValues)
					.getList(c -> toDomain(c)));
		});
		return results;
	}
	
	private SpecialHolidayRemainData toDomain(KrcdtMonSpRemain entity) {

		// 実特別休暇の各属性
		SpecialLeavaRemainTime valFactRemainTimes = null;
		if (entity.factRemainTimes != null){
			valFactRemainTimes = new SpecialLeavaRemainTime(entity.factRemainTimes);
		}
		SpecialLeavaRemainTime valBeforeFactRemainTimes = null;
		if (entity.beforeFactRemainTimes != null){
			valBeforeFactRemainTimes = new SpecialLeavaRemainTime(entity.beforeFactRemainTimes);
		}
		SpecialLeaveRemainDay valAfterFactUseDays = null;
		if (entity.afterFactUseDays != null){
			valAfterFactUseDays = new SpecialLeaveRemainDay(entity.afterFactUseDays);
		}
		SpecialLeaveUseTimes actualUseTime = null;
		if (entity.factUseNumber != null &&
			entity.factUsetimes != null &&
			entity.beforeFactUseTimes != null){
			SpecialLeavaRemainTime valAfterFactUseTimes = null;
			if (entity.afterFactUseTimes != null){
				valAfterFactUseTimes = new SpecialLeavaRemainTime(entity.afterFactUseTimes);
			}
			actualUseTime = new SpecialLeaveUseTimes(
					new UseNumber(entity.factUseNumber),
					new SpecialLeavaRemainTime(entity.factUsetimes),
					new SpecialLeavaRemainTime(entity.beforeFactUseTimes),
					Optional.ofNullable(valAfterFactUseTimes));
		}
		SpecialLeaveRemain actualAfterRemainGrant = null;
		if (entity.afterFactRemainDays != null){
			SpecialLeavaRemainTime valAfterFactRemainTimes = null;
			if (entity.afterFactRemainTimes != null){
				valAfterFactRemainTimes = new SpecialLeavaRemainTime(entity.afterFactRemainTimes);
			}
			actualAfterRemainGrant = new SpecialLeaveRemain(
					new SpecialLeaveRemainDay(entity.afterFactRemainDays),
					Optional.ofNullable(valAfterFactRemainTimes));
		}
		
		// 実特別休暇
		ActualSpecialLeave actualSpecial = new ActualSpecialLeave(
				new SpecialLeaveRemain(
						new SpecialLeaveRemainDay(entity.factRemainDays),
						Optional.ofNullable(valFactRemainTimes)),
				new SpecialLeaveRemain(
						new SpecialLeaveRemainDay(entity.beforeFactRemainDays),
						Optional.ofNullable(valBeforeFactRemainTimes)),
				new SpecialLeaveUseNumber(
						new SpecialLeaveUseDays(
								new SpecialLeaveRemainDay(entity.factUseDays),
								new SpecialLeaveRemainDay(entity.beforeFactUseDays),
								Optional.ofNullable(valAfterFactUseDays)),
						Optional.ofNullable(actualUseTime)),
				Optional.ofNullable(actualAfterRemainGrant));
		
		// 特別休暇の各属性
		SpecialLeavaRemainTime valRemainTimes = null;
		if (entity.remainTimes != null){
			valRemainTimes = new SpecialLeavaRemainTime(entity.remainTimes);
		}
		SpecialLeavaRemainTime valBeforeRemainTimes = null;
		if (entity.beforeRemainTimes != null){
			valBeforeRemainTimes = new SpecialLeavaRemainTime(entity.beforeRemainTimes);
		}
		SpecialLeaveRemainDay valAfterUseDays = null;
		if (entity.afterUseDays != null){
			valAfterUseDays = new SpecialLeaveRemainDay(entity.afterUseDays);
		}
		SpecialLeaveUseTimes specialUseTime = null;
		if (entity.useNumber != null &&
			entity.useTimes != null &&
			entity.beforeUseTimes != null){
			SpecialLeavaRemainTime valAfterUseTimes = null;
			if (entity.afterUseTimes != null){
				valAfterUseTimes = new SpecialLeavaRemainTime(entity.afterUseTimes);
			}
			specialUseTime = new SpecialLeaveUseTimes(
					new UseNumber(entity.useNumber),
					new SpecialLeavaRemainTime(entity.useTimes),
					new SpecialLeavaRemainTime(entity.beforeUseTimes),
					Optional.ofNullable(valAfterUseTimes));
		}
		SpecialLeavaRemainTime valNotUseTime = null;
		if (entity.notUseTime != null){
			valNotUseTime = new SpecialLeavaRemainTime(entity.notUseTime);
		}
		SpecialLeaveRemain specialAfterRemainGrant = null;
		if (entity.afterRemainDays != null){
			SpecialLeavaRemainTime valAfterRemainTimes = null;
			if (entity.afterRemainTimes != null){
				valAfterRemainTimes = new SpecialLeavaRemainTime(entity.afterRemainTimes);
			}
			specialAfterRemainGrant = new SpecialLeaveRemain(
					new SpecialLeaveRemainDay(entity.afterRemainDays),
					Optional.ofNullable(valAfterRemainTimes));
		}
		
		// 特別休暇
		SpecialLeave specialLeave = new SpecialLeave(
				new SpecialLeaveRemain(
						new SpecialLeaveRemainDay(entity.remainDays),
						Optional.ofNullable(valRemainTimes)),
				new SpecialLeaveRemain(
						new SpecialLeaveRemainDay(entity.beforeRemainDays),
						Optional.ofNullable(valBeforeRemainTimes)),
				new SpecialLeaveUseNumber(
						new SpecialLeaveUseDays(
								new SpecialLeaveRemainDay(entity.useDays),
								new SpecialLeaveRemainDay(entity.beforeUseDays),
								Optional.ofNullable(valAfterUseDays)),
						Optional.ofNullable(specialUseTime)),
				new SpecialLeaveUnDigestion(
						new SpecialLeaveRemainDay(entity.notUseDays),
						Optional.ofNullable(valNotUseTime)), 
				Optional.ofNullable(specialAfterRemainGrant));
		
		// 付与日数
		SpecialLeaveGrantUseDay valGrantDays = null;
		if (entity.grantDays != null){
			valGrantDays = new SpecialLeaveGrantUseDay(entity.grantDays);
		}
		
		// 特別休暇月別残数データ//
		return new SpecialHolidayRemainData(
				entity.pk.sid,
				new YearMonth(entity.pk.ym),
				entity.pk.closureId,
				new ClosureDate(entity.pk.closureDay, (entity.pk.chkLastDay == 1)),
				new DatePeriod(entity.getClosureStartDate(), entity.getClosureEndDate()),
				EnumAdaptor.valueOf(entity.getClosureStatus(), ClosureStatus.class),
				entity.pk.specialHolidayCd,
				actualSpecial,
				specialLeave,
				Optional.ofNullable(valGrantDays),
				(entity.grantAtr == 1));
	}

	/** 登録および更新 */
	// add 2018.8.22 shuichi_ishida
	@Override
	public void persistAndUpdate(SpecialHolidayRemainData domain) {
		
		// キー
		val key = new KrcdtMonSpRemainPK(
				domain.getSid(),
				domain.getYm().v(),
				domain.getClosureId(),
				domain.getClosureDate().getClosureDay().v(),
				(domain.getClosureDate().getLastDayOfMonth() ? 1 : 0),
				domain.getSpecialHolidayCd());
		
		// 登録・更新
		KrcdtMonSpRemain entity = this.getEntityManager().find(KrcdtMonSpRemain.class, key);
		if (entity == null){
			entity = new KrcdtMonSpRemain();
			entity.fromDomainForPersist(domain);
			this.getEntityManager().persist(entity);
		}
		else {
			entity.fromDomainForUpdate(domain);
		}
	}
	
	/** 削除 */
	// add 2018.8.22 shuichi_ishida
	@Override
	public void remove(String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate,
			int specialHolidayCode) {
		
		this.commandProxy().remove(KrcdtMonSpRemain.class,
				new KrcdtMonSpRemainPK(
						employeeId,
						yearMonth.v(),
						closureId.value,
						closureDate.getClosureDay().v(),
						(closureDate.getLastDayOfMonth() ? 1 : 0),
						specialHolidayCode));
	}
	
	/** 削除 */
	// add 2018.8.24 shuichi_ishida
	@Override
	public void remove(String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate) {
		
		this.getEntityManager().createQuery(DELETE_BY_CLOSURE)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.setParameter("closureId", closureId.value)
				.setParameter("closureDay", closureDate.getClosureDay().v())
				.setParameter("chkLastDay", (closureDate.getLastDayOfMonth() ? 1 : 0))
				.executeUpdate();
	}
	
	/** 削除　（年月） */
	// add 2018.8.22 shuichi_ishida
	@Override
	public void removeByYearMonth(String employeeId, YearMonth yearMonth) {
		
		this.getEntityManager().createQuery(DELETE_BY_YEAR_MONTH)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.executeUpdate();
	}
}
