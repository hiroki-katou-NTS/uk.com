package nts.uk.ctx.at.record.infra.repository.monthly.agreement;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeBreakdown;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfManagePeriodRepository;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitOneMonth;
import nts.uk.ctx.at.record.infra.entity.monthly.agreement.KrcdtMonMngAgreTime;
import nts.uk.ctx.at.record.infra.entity.monthly.agreement.KrcdtMonMngAgreTimePK;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * リポジトリ実装：管理期間の36協定時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaAgreementTimeOfManagePeriod extends JpaRepository implements AgreementTimeOfManagePeriodRepository {

	private static final String FIND_BY_YEAR =
			"SELECT a FROM KrcdtMonMngAgreTime a "
			+ "WHERE a.PK.employeeId = :employeeId "
			+ "AND a.year = :year "
			+ "ORDER BY a.PK.yearMonth ";
	
	private static final String REMOVE_BY_YEAR =
			"DELETE FROM KrcdtMonMngAgreTime a "
			+ "WHERE a.PK.employeeId = :employeeId "
			+ "AND a.year = :year ";
	
	/** 検索 */
	@Override
	public Optional<AgreementTimeOfManagePeriod> find(String employeeId, YearMonth yearMonth) {

		return this.queryProxy()
				.find(new KrcdtMonMngAgreTimePK(
						employeeId,
						yearMonth.v()),
						KrcdtMonMngAgreTime.class)
				.map(c -> toDomain(c));
	}

	/** 検索　（年度） */
	@Override
	public List<AgreementTimeOfManagePeriod> findByYearOrderByYearMonth(String employeeId, Year year) {
		
		return this.queryProxy().query(FIND_BY_YEAR, KrcdtMonMngAgreTime.class)
				.setParameter("employeeId", employeeId)
				.setParameter("year", year.v())
				.getList(c -> toDomain(c));
	}
	
	/**
	 * エンティティ→ドメイン
	 * @param entity エンティティ：管理期間の36協定時間
	 * @return ドメイン：管理期間の36協定時間
	 */
	private static AgreementTimeOfManagePeriod toDomain(KrcdtMonMngAgreTime entity){
		
		// 月別実績の36協定時間
		val agreementTime = AgreementTimeOfMonthly.of(
				new AttendanceTimeMonth(entity.agreementTime),
				new LimitOneMonth(entity.limitErrorTime),
				new LimitOneMonth(entity.limitAlarmTime),
				(entity.exceptionLimitErrorTime == null ?
						Optional.empty() : Optional.of(new LimitOneMonth(entity.exceptionLimitErrorTime))),
				(entity.exceptionLimitAlarmTime == null ?
						Optional.empty() : Optional.of(new LimitOneMonth(entity.exceptionLimitAlarmTime))),
				EnumAdaptor.valueOf(entity.status, AgreementTimeStatusOfMonthly.class));
		
		// 36協定時間内訳
		val breakdown = AgreementTimeBreakdown.of(
				new AttendanceTimeMonth(entity.overTime),
				new AttendanceTimeMonth(entity.transferOverTime),
				new AttendanceTimeMonth(entity.holidayWorkTime),
				new AttendanceTimeMonth(entity.transferHolidayWorkTime),
				new AttendanceTimeMonth(entity.flexExcessTime),
				new AttendanceTimeMonth(entity.withinPresctibedPremiumTime),
				new AttendanceTimeMonth(entity.weeklyPremiumTime),
				new AttendanceTimeMonth(entity.monthlyPremiumTime));
		
		// 管理期間の36協定時間
		val domain = AgreementTimeOfManagePeriod.of(
				entity.PK.employeeId,
				new YearMonth(entity.PK.yearMonth),
				new Year(entity.year),
				agreementTime,
				breakdown);
		
		return domain;
	}

	/** 登録および更新 */
	@Override
	public void persistAndUpdate(AgreementTimeOfManagePeriod domain) {

		// キー
		val key = new KrcdtMonMngAgreTimePK(
				domain.getEmployeeId(),
				domain.getYearMonth().v());

		// 月別実績の36協定時間
		val agreementTime = domain.getAgreementTime();
		
		// 36協定時間内訳
		val breakdown = domain.getBreakdown();
		
		// 登録・更新を判断　および　キー値設定
		boolean isNeedPersist = false;
		KrcdtMonMngAgreTime entity = this.getEntityManager().find(KrcdtMonMngAgreTime.class, key);
		if (entity == null){
			isNeedPersist = true;
			entity = new KrcdtMonMngAgreTime();
			entity.PK = key;
		}
		
		// 登録・更新値の設定
		entity.year = domain.getYear().v();
		entity.agreementTime = agreementTime.getAgreementTime().v();
		entity.limitErrorTime = agreementTime.getLimitErrorTime().v();
		entity.limitAlarmTime = agreementTime.getLimitAlarmTime().v();
		entity.exceptionLimitErrorTime = (agreementTime.getExceptionLimitErrorTime().isPresent() ?
				agreementTime.getExceptionLimitErrorTime().get().v() : null);
		entity.exceptionLimitAlarmTime = (agreementTime.getExceptionLimitAlarmTime().isPresent() ?
				agreementTime.getExceptionLimitAlarmTime().get().v() : null);
		entity.status = agreementTime.getStatus().value;
		entity.withinPresctibedPremiumTime = breakdown.getWithinPrescribedPremiumTime().v();
		entity.overTime = breakdown.getOverTime().v();
		entity.transferOverTime = breakdown.getTransferOverTime().v();
		entity.holidayWorkTime = breakdown.getHolidayWorkTime().v();
		entity.transferHolidayWorkTime = breakdown.getTransferTime().v();
		entity.flexExcessTime = breakdown.getFlexExcessTime().v();
		entity.weeklyPremiumTime = breakdown.getWeeklyPremiumTime().v();
		entity.monthlyPremiumTime = breakdown.getMonthlyPremiumTime().v();
		
		// 登録が必要な時、登録を実行
		if (isNeedPersist) this.getEntityManager().persist(entity);
	}
	
	/** 削除 */
	@Override
	public void remove(String employeeId, YearMonth yearMonth) {

		this.commandProxy().remove(KrcdtMonMngAgreTime.class,
				new KrcdtMonMngAgreTimePK(employeeId, yearMonth.v()));
	}

	/** 削除　（年度） */
	@Override
	public void removeByYear(String employeeId, Year year) {

		this.getEntityManager().createQuery(REMOVE_BY_YEAR)
				.setParameter("employeeId", employeeId)
				.setParameter("year", year.v())
				.executeUpdate();
	}
}
