package nts.uk.ctx.at.record.dom.monthly.information.care;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.shared.dom.common.Day;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * @author phongtq
 * 介護休暇月別残数データ
 */
@Getter
@AllArgsConstructor
public class MonCareHdRemain extends AggregateRoot{
	
	/** 社員ID */
	private String employeeId;
	/** 年月 */
	private YearMonth yearMonth;
	/** 締めID */
	private ClosureId closureId;
	/** 締め日 */
	private Day closureDay;
	/** 末日とする */
	private int isLastDay;
	
	/** 締め処理状態 */
	private ClosureStatus closureStatus;
	/** 開始年月日 */
	private GeneralDate startDate;
	/** 終了年月日 */
	private GeneralDate endDate;
	/** 使用日数 */
	private MonCareHdNumber usedDays;
	/** 使用日数付与前 */
	private MonCareHdNumber usedDaysBefore;
	/** 使用日数付与後 */
	private MonCareHdNumber usedDaysAfter;
	/** 使用時間 */
	private MonCareHdMinutes usedMinutes;
	/** 使用時間付与前 */
	private MonCareHdMinutes usedMinutesBefore;
	/** 使用時間付与後 */
	private MonCareHdMinutes usedMinutesAfter;

	public MonCareHdRemain(
			String employeeId,
			YearMonth yearMonth,
			ClosureId closureId,
			Day closureDay,
			int isLastDay){
		
		this.employeeId = employeeId;
		this.yearMonth = yearMonth;
		this.closureId = closureId;
		this.closureDay = closureDay;
		this.isLastDay = isLastDay;
		
		this.closureStatus = ClosureStatus.UNTREATED;
		this.startDate = GeneralDate.min();
		this.endDate = GeneralDate.min();
		this.usedDays = new MonCareHdNumber(0.0);
		this.usedDaysBefore = new MonCareHdNumber(0.0);
		this.usedDaysAfter = new MonCareHdNumber(0.0);
		this.usedMinutes = new MonCareHdMinutes(0);
		this.usedMinutesBefore = new MonCareHdMinutes(0);
		this.usedMinutesAfter = new MonCareHdMinutes(0);
	}
}
