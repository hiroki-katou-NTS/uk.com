package nts.uk.ctx.at.shared.dom.scherec.event.attendanceitem;

import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.event.DomainEvent;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItem;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.DailyAttendanceAtr;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Getter
public class AttendanceItemDomainEvent extends DomainEvent {
	/**
	 * 実績区分
	 */
	private PerformanceAtr performanceAtr;

	/**
	 * 任意項目の属性
	 */
	private OptionalItemAtr optionalItemAtr;

	/**
	 * 任意項目NO
	 */
	private OptionalItemNo optionalItemNo;

	public AttendanceItemDomainEvent(PerformanceAtr performanceAtr) {
		super();
		this.performanceAtr = performanceAtr;
	}

	private void setOptionalItemAtr(DailyAttendanceAtr dailyAttendanceAtr) {
		OptionalItemAtr optionalItemAtr = null;
		switch (dailyAttendanceAtr) {
		case Time:
			optionalItemAtr = OptionalItemAtr.TIME;
			break;
		case NumberOfTime:
			optionalItemAtr = OptionalItemAtr.NUMBER;
			break;
		case AmountOfMoney:
			optionalItemAtr = OptionalItemAtr.AMOUNT;
			break;
		default:
			break;
		}
		this.optionalItemAtr = optionalItemAtr;
	}

	private void setOptionalItemAtr(MonthlyAttendanceItemAtr monthlyAttendanceAtr) {
		OptionalItemAtr optionalItemAtr = null;
		switch (monthlyAttendanceAtr) {
		case TIME:
			optionalItemAtr = OptionalItemAtr.TIME;
			break;
		case NUMBER:
			optionalItemAtr = OptionalItemAtr.NUMBER;
			break;
		case AMOUNT:
			optionalItemAtr = OptionalItemAtr.AMOUNT;
			break;
		default:
			break;
		}
		this.optionalItemAtr = optionalItemAtr;
	}

	/**
	 * 勤怠項目ID：Eventパラメータ「任意項目NO」に対応する勤怠項目ID
	 */
	private void setOptionalItemNo(int attendanceItemId) {
		if (this.performanceAtr.isDailyPerformance()) {
			Optional<DailyItemList> itemOtp = DailyItemList.getOption(attendanceItemId);
			if (itemOtp.isPresent()) {
				this.optionalItemNo = EnumAdaptor.valueOf(itemOtp.get().optionalItemNo, OptionalItemNo.class);
			} else {
				this.optionalItemNo = null;
			}
		} else if (this.performanceAtr.isMonthlyPerformance()) {
			Optional<MonthlyItemList> itemOtp = MonthlyItemList.getOption(attendanceItemId);
			if (itemOtp.isPresent()) {
				this.optionalItemNo = EnumAdaptor.valueOf(itemOtp.get().optionalItemNo, OptionalItemNo.class);
			} else {
				this.optionalItemNo = null;
			}
		}
	}

	public static AttendanceItemDomainEvent fromDomain(DailyAttendanceItem domain) {
		AttendanceItemDomainEvent itemEvent = new AttendanceItemDomainEvent(PerformanceAtr.DAILY_PERFORMANCE);
		itemEvent.setOptionalItemAtr(domain.getDailyAttendanceAtr());
		itemEvent.setOptionalItemNo(domain.getAttendanceItemId());
		return itemEvent;
	}

	public static AttendanceItemDomainEvent fromDomain(MonthlyAttendanceItem domain) {
		AttendanceItemDomainEvent itemEvent = new AttendanceItemDomainEvent(PerformanceAtr.MONTHLY_PERFORMANCE);
		itemEvent.setOptionalItemAtr(domain.getMonthlyAttendanceAtr());
		itemEvent.setOptionalItemNo(domain.getAttendanceItemId());
		return itemEvent;
	}
}
