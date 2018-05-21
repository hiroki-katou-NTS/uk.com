package nts.uk.ctx.at.record.app.find.remainingnumber.subhdmana.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveManagementData;

/**
 * @author hiep.ld
 *
 */
@AllArgsConstructor
@Data
public class LeaveDataDto {
	// ID
	private String ID;

	// 会社ID
	private String cID;

	// 社員ID
	private String sID;

	// 休出日
	private GeneralDate dayOffDate;

	// 使用期限日
	private GeneralDate expiredDate;

	// 発生日数
	private double occurredDays;

	// 発生時間数
	private int occurredTimes;

	// 未使用日数
	private double unUsedDays;

	// 未使用時間数
	private int unUsedTimes;

	// 代休消化区分
	private int subHDAtr;

	// １日相当時間
	private int fullDayTime;

	// 半日相当時間
	private int halfDayTime;

	// 消滅日
	private GeneralDate disapearDate;

	public static LeaveDataDto convertToDto(LeaveManagementData leaveData) {
		return new LeaveDataDto(leaveData.getID(), leaveData.getCID(), leaveData.getSID(),
				leaveData.getComDayOffDate().getDayoffDate().get(), leaveData.getExpiredDate(),
				leaveData.getOccurredDays().v().doubleValue(), leaveData.getOccurredTimes().v().intValue(),
				leaveData.getUnUsedDays().v().doubleValue(), leaveData.getUnUsedTimes().v().intValue(),
				leaveData.getSubHDAtr().value, leaveData.getFullDayTime().v().intValue(),
				leaveData.getHalfDayTime().v().intValue(), leaveData.getDisapearDate());
	}
}
