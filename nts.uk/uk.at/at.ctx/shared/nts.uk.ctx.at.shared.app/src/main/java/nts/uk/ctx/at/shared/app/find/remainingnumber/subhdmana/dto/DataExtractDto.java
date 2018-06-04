package nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto;

import lombok.Data;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementData;

@Data
public class DataExtractDto {
	//Type
	private int type;
	
	// ID
	private String ID;

	// 会社ID
	private String cID;

	// 社員ID
	private String sID;

	// 休出日
	private int unknownDate;
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

	// ID
	private String comDayOffID;

	// 必要日数
	private double requireDays;

	// 必要時間数
	private int requiredTimes;

	// 未相殺日数
	private double remainDays;

	// 未相殺時間数
	private int remainTimes;
	
	@Setter
	private int linked;

	@Setter
	private double remain;
	@Setter
	private double expired;
	
	public static DataExtractDto convertFromLeaveDataToDto(int type, LeaveManagementData leaveData) {
		return new DataExtractDto(type, leaveData.getID(), leaveData.getCID(), leaveData.getSID(),
				leaveData.getComDayOffDate().isUnknownDate() ? 1 : 0,
				leaveData.getComDayOffDate().getDayoffDate().orElse(null), leaveData.getExpiredDate(),
				leaveData.getOccurredDays().v().doubleValue(), leaveData.getOccurredTimes().v().intValue(),
				leaveData.getUnUsedDays().v().doubleValue(), leaveData.getUnUsedTimes().v().intValue(),
				leaveData.getSubHDAtr().value, leaveData.getFullDayTime().v().intValue(),
				leaveData.getHalfDayTime().v().intValue(), leaveData.getDisapearDate());
	}
	
	public static DataExtractDto convertFromCompensatoryDataToDto(int type, CompensatoryDayOffManaData compensatoryData) {
		return new DataExtractDto(type, compensatoryData.getComDayOffID(), compensatoryData.getSID(), compensatoryData.getCID(),
				compensatoryData.getDayOffDate().isUnknownDate() ? 1 : 0, compensatoryData.getDayOffDate().getDayoffDate().orElse(null), compensatoryData.getRequireDays().v(),
				compensatoryData.getRequiredTimes().v(), compensatoryData.getRemainDays().v(),
				compensatoryData.getRemainTimes().v());
	}
	
	public DataExtractDto(int type, String iD, String cID, String sID, int unknownDate, GeneralDate dayOffDate,
			GeneralDate expiredDate, double occurredDays, int occurredTimes, double unUsedDays, int unUsedTimes,
			int subHDAtr, int fullDayTime, int halfDayTime, GeneralDate disapearDate) {
		this.type = type;
		this.ID = iD;
		this.cID = cID;
		this.sID = sID;
		this.unknownDate = unknownDate;
		this.dayOffDate = dayOffDate;
		this.expiredDate = expiredDate;
		this.occurredDays = occurredDays;
		this.occurredTimes = occurredTimes;
		this.unUsedDays = unUsedDays;
		this.unUsedTimes = unUsedTimes;
		this.subHDAtr = subHDAtr;
		this.fullDayTime = fullDayTime;
		this.halfDayTime = halfDayTime;
		this.disapearDate = disapearDate;
	}
	public DataExtractDto(int type, String comDayOffID, String sID, String cID, int unknownDate, GeneralDate dayOffDate,
			double requireDays, int requiredTimes, double remainDays, int remainTimes) {
		this.type = type;
		this.comDayOffID = comDayOffID;
		this.sID = sID;
		this.cID = cID;
		this.unknownDate = unknownDate;
		this.dayOffDate = dayOffDate;
		this.requireDays = requireDays;
		this.requiredTimes = requiredTimes;
		this.remainDays = remainDays;
		this.remainTimes = remainTimes;
	}
	
}
