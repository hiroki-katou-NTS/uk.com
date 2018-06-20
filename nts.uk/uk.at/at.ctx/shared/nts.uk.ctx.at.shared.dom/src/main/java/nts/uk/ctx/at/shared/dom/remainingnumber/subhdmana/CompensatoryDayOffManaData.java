package nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataDaysAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataHours;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;

/**
 * 代休管理データ
 * 
 * @author HopNT
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CompensatoryDayOffManaData extends AggregateRoot {

	// ID
	private String comDayOffID;

	// 社員ID
	private String sID;

	private String cID;

	// 代休日
	
	private CompensatoryDayoffDate dayOffDate;

	// 必要日数
	private ManagementDataDaysAtr requireDays;

	// 必要時間数
	private ManagementDataHours requiredTimes;

	// 未相殺日数
	@Setter
	private ManagementDataRemainUnit remainDays;

	// 未相殺時間数
	private ManagementDataHours remainTimes;

	public CompensatoryDayOffManaData(String comDayOffID, String cid, String sID, boolean unknowDate,
			GeneralDate dayoffDate, Double days, int time, Double remainDays, int remainTimes) {
		this.comDayOffID = comDayOffID;
		this.cID = cid;
		this.sID = sID;
		this.dayOffDate = new CompensatoryDayoffDate(unknowDate, Optional.ofNullable(dayoffDate));
		this.requireDays = new ManagementDataDaysAtr(days);
		this.requiredTimes = new ManagementDataHours(time);
		this.remainDays = new ManagementDataRemainUnit(remainDays);
		this.remainTimes = new ManagementDataHours(remainTimes);
	}

}
