package nts.uk.ctx.at.record.infra.repository.monthly.verticaltotal.worktime.medicaltime;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.medicaltime.MedicalTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.medicaltime.MedicalTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.worktime.KrcdtMonMedicalTime;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.worktime.KrcdtMonMedicalTimePK;

/**
 * リポジトリ実装：集計残業時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaMedicalTimeOfMonthly extends JpaRepository implements MedicalTimeOfMonthlyRepository {

	private static final String DELETE_BY_PARENT_PK = "DELETE FROM KrcdtMonMedicalTime a "
			+ "WHERE a.PK.employeeID = :employeeID "
			+ "AND a.PK.yearMonth = :yearMonth "
			+ "AND a.PK.closureId = :closureId "
			+ "AND a.PK.closureDay = :closureDay "
			+ "AND a.PK.isLastDay = :isLastDay ";
	
	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			MedicalTimeOfMonthly medicalTimeOfMonthly) {
		
		this.getEntityManager().persist(toEntity(attendanceTimeOfMonthlyKey, medicalTimeOfMonthly, false));
	}

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			MedicalTimeOfMonthly medicalTimeOfMonthly) {

		this.toEntity(attendanceTimeOfMonthlyKey, medicalTimeOfMonthly, true);
	}

	/** 削除　（親キー） */
	@Override
	public void removeByParentPK(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey) {
		
		// 締め日付
		val closureDate = attendanceTimeOfMonthlyKey.getClosureDate();
		
		this.getEntityManager().createQuery(DELETE_BY_PARENT_PK)
				.setParameter("employeeID", attendanceTimeOfMonthlyKey.getEmployeeId())
				.setParameter("yearMonth", attendanceTimeOfMonthlyKey.getYearMonth().v())
				.setParameter("closureId", attendanceTimeOfMonthlyKey.getClosureId().value)
				.setParameter("closureDay", closureDate.getClosureDay().v())
				.setParameter("isLastDay", (closureDate.getLastDayOfMonth() ? 1 : 0))
				.executeUpdate();
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param domainKey キー値：月別実績の勤怠時間
	 * @param domain ドメイン：月別実績の医療時間
	 * @param execUpdate 更新を実行する
	 * @return エンティティ：月別実績の医療時間
	 */
	private KrcdtMonMedicalTime toEntity(AttendanceTimeOfMonthlyKey domainKey,
			MedicalTimeOfMonthly domain, boolean execUpdate){

		// 締め日付
		val closureDate = domainKey.getClosureDate();
		
		// キー
		val key = new KrcdtMonMedicalTimePK(
				domainKey.getEmployeeId(),
				domainKey.getYearMonth().v(),
				domainKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0),
				domain.getDayNightAtr().value);
		
		KrcdtMonMedicalTime entity;
		if (execUpdate){
			entity = this.getEntityManager().find(KrcdtMonMedicalTime.class, key);
		}
		else {
			entity = new KrcdtMonMedicalTime();
			entity.PK = key;
		}
		entity.workTime = domain.getWorkTime().v();
		entity.deductionTime = domain.getDeducationTime().v();
		entity.takeOverTime = domain.getTakeOverTime().v();
		return entity;
	}
}
