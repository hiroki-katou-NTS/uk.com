package nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataDaysAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataHours;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;

/**
 * 休出管理データ
 * @author HopNT
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LeaveManagementData extends AggregateRoot {
	
	// ID
	private String ID;
	
	// 会社ID
	private String cID;
	
	// 社員ID
	private String sID;
	
	// 休出日
	private CompensatoryDayoffDate ComDayOffDate;
	
	// 使用期限日
	private GeneralDate expiredDate;
	
	// 発生日数
	private ManagementDataDaysAtr occurredDays;
	
	// 発生時間数
	private ManagementDataHours occurredTimes;
	
	// 未使用日数
	private ManagementDataRemainUnit unUsedDays;
	
	// 未使用時間数
	private ManagementDataHours unUsedTimes;
	
	// 代休消化区分r
	private DigestionAtr subHDAtr;
	
	// １日相当時間
	private AttendanceTime fullDayTime;
	
	// 半日相当時間
	private AttendanceTime halfDayTime;
	
	// 消滅日
	public Optional<GeneralDate> disapearDate;
	
	public LeaveManagementData(String id, String cid, String sid, boolean unknowDate, GeneralDate dayoffDate, GeneralDate expiredDate,
			Double occurredDays, int occurredTimes, Double unUsedDays, int unUsedTimes, int subHDAtr, int equivalentADay, int equivalentHalfDay){
		this.ID = id;
		this.cID = cid;
		this.sID = sid;
		this.ComDayOffDate = new CompensatoryDayoffDate(unknowDate, Optional.ofNullable(dayoffDate));
		this.expiredDate = expiredDate;
		this.occurredDays = new ManagementDataDaysAtr(occurredDays);
		this.occurredTimes = new ManagementDataHours(occurredTimes);
		this.unUsedDays = new ManagementDataRemainUnit(unUsedDays);
		this.unUsedTimes = new ManagementDataHours(unUsedTimes);
		this.subHDAtr = EnumAdaptor.valueOf(subHDAtr, DigestionAtr.class);
		this.fullDayTime = new AttendanceTime(equivalentADay);
		this.halfDayTime = new AttendanceTime(equivalentHalfDay);
		this.disapearDate = Optional.empty();
	}
	
	
	public LeaveManagementData(String id, String cid, String sid, boolean unknowDate, GeneralDate dayoffDate, GeneralDate expiredDate,
			Double occurredDays, int occurredTimes, Double unUsedDays, int unUsedTimes, int subHDAtr, int equivalentADay, int equivalentHalfDay, GeneralDate disapearDate){
		this.ID = id;
		this.cID = cid;
		this.sID = sid;
		this.ComDayOffDate = new CompensatoryDayoffDate(unknowDate, Optional.ofNullable(dayoffDate));
		this.expiredDate = expiredDate;
		this.occurredDays = new ManagementDataDaysAtr(occurredDays);
		this.occurredTimes = new ManagementDataHours(occurredTimes);
		this.unUsedDays = new ManagementDataRemainUnit(unUsedDays);
		this.unUsedTimes = new ManagementDataHours(unUsedTimes);
		this.subHDAtr = EnumAdaptor.valueOf(subHDAtr, DigestionAtr.class);
		this.fullDayTime = new AttendanceTime(equivalentADay);
		this.halfDayTime = new AttendanceTime(equivalentHalfDay);
		this.disapearDate = Optional.ofNullable(disapearDate);
		
	}


	public LeaveManagementData(String id2, String cid2, String sid2, boolean unknowDate, GeneralDate dayoffDate,
			GeneralDate expiredDate2, ManagementDataDaysAtr occurredDays2, ManagementDataHours occurredTimes2,
			Double unUsedDay, int unUsedTimes2, int subHDAtr2, AttendanceTime fullDayTime2,
			AttendanceTime halfDayTime2, Optional<GeneralDate> disapearDate2) {
		this.ID = id2;
		this.cID = cid2;
		this.sID = sid2;
		this.ComDayOffDate = new CompensatoryDayoffDate(unknowDate, Optional.ofNullable(dayoffDate));
		this.expiredDate = expiredDate2;
		this.occurredDays = occurredDays2;
		this.occurredTimes = occurredTimes2;
		this.unUsedDays = new ManagementDataRemainUnit(unUsedDay);
		this.unUsedTimes = new ManagementDataHours(unUsedTimes2);
		this.subHDAtr = EnumAdaptor.valueOf(subHDAtr2, DigestionAtr.class);
		this.fullDayTime = fullDayTime2;
		this.halfDayTime = halfDayTime2;
		this.disapearDate = disapearDate2;
	}
	
	
}
