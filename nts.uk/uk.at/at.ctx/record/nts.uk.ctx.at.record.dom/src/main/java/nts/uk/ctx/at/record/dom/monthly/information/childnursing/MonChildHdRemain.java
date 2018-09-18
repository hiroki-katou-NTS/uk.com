package nts.uk.ctx.at.record.dom.monthly.information.childnursing;

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
 * 子の看護月別残数データ
 */
@Getter
@AllArgsConstructor
public class MonChildHdRemain extends AggregateRoot{
	
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
	private MonChildHdNumber usedDays;
	/** 使用日数付与前 */
	private MonChildHdNumber usedDaysBefore;
	/** 使用日数付与後 */
	private MonChildHdNumber usedDaysAfter;
	/** 使用時間 */
	private MonChildHdMinutes usedMinutes;
	/** 使用時間付与前 */
	private MonChildHdMinutes usedMinutesBefore;
	/** 使用時間付与後 */
	private MonChildHdMinutes usedMinutesAfter;
	
	public MonChildHdRemain(
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
		
		closureStatus = ClosureStatus.UNTREATED;
		startDate = GeneralDate.min();
		endDate = GeneralDate.min();
		usedDays = new MonChildHdNumber(0.0);
		usedDaysBefore = new MonChildHdNumber(0.0);
		usedDaysAfter = new MonChildHdNumber(0.0);
		usedMinutes = new MonChildHdMinutes(0);
		usedMinutesBefore = new MonChildHdMinutes(0);
		usedMinutesAfter = new MonChildHdMinutes(0);
	}
}
