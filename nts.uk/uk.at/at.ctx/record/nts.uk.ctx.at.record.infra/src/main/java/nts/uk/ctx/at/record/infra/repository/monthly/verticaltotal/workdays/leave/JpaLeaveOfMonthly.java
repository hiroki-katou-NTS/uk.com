package nts.uk.ctx.at.record.infra.repository.monthly.verticaltotal.workdays.leave;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.leave.LeaveOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.leave.LeaveOfMonthlyRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.workdays.KrcdtMonLeave;
import nts.uk.ctx.at.shared.dom.worktype.CloseAtr;

/**
 * リポジトリ実装：月別実績の休業
 * @author shuichu_ishida
 */
@Stateless
public class JpaLeaveOfMonthly extends JpaRepository implements LeaveOfMonthlyRepository {

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey, LeaveOfMonthly leaveOfMonthly) {
		
		this.toUpdate(attendanceTimeOfMonthlyKey, leaveOfMonthly);
	}
	
	/**
	 * データ更新
	 * @param domainKey キー値：月別実績の勤怠時間
	 * @param domain ドメイン：月別実績の休業
	 */
	private void toUpdate(AttendanceTimeOfMonthlyKey domainKey, LeaveOfMonthly domain){

		// 締め日付
		val closureDate = domainKey.getClosureDate();
		
		// キー
		val key = new KrcdtMonAttendanceTimePK(
				domainKey.getEmployeeId(),
				domainKey.getYearMonth().v(),
				domainKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));
		
		KrcdtMonLeave entity = this.queryProxy().find(key, KrcdtMonLeave.class).get();
		if (entity == null) return;
		entity.prenatalLeaveDays = 0.0;
		entity.postpartumLeaveDays = 0.0;
		entity.childcareLeaveDays = 0.0;
		entity.careLeaveDays = 0.0;
		entity.injuryOrIllnessLeaveDays = 0.0;
		entity.anyLeaveDays01 = 0.0;
		entity.anyLeaveDays02 = 0.0;
		entity.anyLeaveDays03 = 0.0;
		entity.anyLeaveDays04 = 0.0;
		val fixLeaveDaysMap = domain.getFixLeaveDays();
		if (fixLeaveDaysMap.containsKey(CloseAtr.PRENATAL)){
			entity.prenatalLeaveDays = fixLeaveDaysMap.get(CloseAtr.PRENATAL).getDays().v();
		}
		if (fixLeaveDaysMap.containsKey(CloseAtr.POSTPARTUM)){
			entity.postpartumLeaveDays = fixLeaveDaysMap.get(CloseAtr.POSTPARTUM).getDays().v();
		}
		if (fixLeaveDaysMap.containsKey(CloseAtr.CHILD_CARE)){
			entity.childcareLeaveDays = fixLeaveDaysMap.get(CloseAtr.CHILD_CARE).getDays().v();
		}
		if (fixLeaveDaysMap.containsKey(CloseAtr.CARE)){
			entity.careLeaveDays = fixLeaveDaysMap.get(CloseAtr.CARE).getDays().v();
		}
		if (fixLeaveDaysMap.containsKey(CloseAtr.INJURY_OR_ILLNESS)){
			entity.injuryOrIllnessLeaveDays = fixLeaveDaysMap.get(CloseAtr.INJURY_OR_ILLNESS).getDays().v();
		}
		val anyLeaveDaysMap = domain.getAnyLeaveDays();
		if (anyLeaveDaysMap.containsKey(1)){
			entity.anyLeaveDays01 = anyLeaveDaysMap.get(1).getDays().v();
		}
		if (anyLeaveDaysMap.containsKey(2)){
			entity.anyLeaveDays02 = anyLeaveDaysMap.get(2).getDays().v();
		}
		if (anyLeaveDaysMap.containsKey(3)){
			entity.anyLeaveDays03 = anyLeaveDaysMap.get(3).getDays().v();
		}
		if (anyLeaveDaysMap.containsKey(4)){
			entity.anyLeaveDays04 = anyLeaveDaysMap.get(4).getDays().v();
		}
	}
}
