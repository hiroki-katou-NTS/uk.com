package nts.uk.ctx.at.shared.dom.scherec.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItem;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemAtr;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.DailyAttendanceAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.PrimitiveValueOfAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttendanceItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.event.OptionalItemAtr;
import nts.uk.ctx.at.shared.dom.scherec.event.OptionalItemAtrExport;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AttendanceAtrServiceImpl implements AttendanceAtrService {

	@Inject
	private DailyAttendanceItemRepository dailyAttendanceItemRepository;

	@Inject
	private MonthlyAttendanceItemRepository monthlyAttendanceItemRepository;

	@Override
	public void updateAttendanceAtr(OptionalItemAtrExport domainEvent) {
		String companyId = AppContexts.user().companyId();
		// ドメインモデル「日次の勤怠項目」を更新する
		int dailyAttId = this.getDailyAttId(domainEvent.getOptionalItemNo().v());
		Optional<DailyAttendanceItem> dailyItemOtp = dailyAttendanceItemRepository.getDailyAttendanceItem(companyId,
				dailyAttId);
		if (dailyItemOtp.isPresent()) {
			DailyAttendanceItem dailyItem = dailyItemOtp.get();
			dailyItem.setDailyAttendanceAtr(this.getDailyAttendanceAtr(domainEvent.getOptionalItemAtr()));
			dailyItem.setPrimitiveValue(this.getDailyPrimitiveValue(domainEvent.getOptionalItemAtr()));
			dailyAttendanceItemRepository.update(dailyItem);
		}

		int monthlyAttId = this.getMonthlyAttId(domainEvent.getOptionalItemNo().v());
		Optional<MonthlyAttendanceItem> monthlyItemOtp = monthlyAttendanceItemRepository
				.findByAttendanceItemId(companyId, monthlyAttId);
		if (monthlyItemOtp.isPresent()) {
			MonthlyAttendanceItem monthlyItem = monthlyItemOtp.get();
			monthlyItem.setMonthlyAttendanceAtr(this.getMonthlyAttendanceAtr(domainEvent.getOptionalItemAtr()));
			monthlyItem.setPrimitiveValue(this.getMonthlyPrimitiveValue(domainEvent.getOptionalItemAtr()));
			monthlyAttendanceItemRepository.update(monthlyItem);
		}
	}

	private DailyAttendanceAtr getDailyAttendanceAtr(OptionalItemAtr optionalItemAtr) {
		DailyAttendanceAtr dailyAttendanceAtr = null;

		switch (optionalItemAtr) {
		case TIME:
			dailyAttendanceAtr = DailyAttendanceAtr.Time;
			break;
		case NUMBER:
			dailyAttendanceAtr = DailyAttendanceAtr.NumberOfTime;
			break;
		case AMOUNT:
			dailyAttendanceAtr = DailyAttendanceAtr.AmountOfMoney;
			break;
		default:
			break;
		}
		return dailyAttendanceAtr;
	}

	private MonthlyAttendanceItemAtr getMonthlyAttendanceAtr(OptionalItemAtr optionalItemAtr) {
		MonthlyAttendanceItemAtr monthlyAttendanceItemAtr = null;
		switch (optionalItemAtr) {
		case TIME:
			monthlyAttendanceItemAtr = MonthlyAttendanceItemAtr.TIME;
			break;
		case NUMBER:
			monthlyAttendanceItemAtr = MonthlyAttendanceItemAtr.NUMBER;
			break;
		case AMOUNT:
			monthlyAttendanceItemAtr = MonthlyAttendanceItemAtr.AMOUNT;
			break;
		default:
			break;
		}
		return monthlyAttendanceItemAtr;
	}

	private int getDailyAttId(Integer optionalItemNo) {
		int attId = 0;
		Optional<DailyItemList> itemOtp = DailyItemList.getOption(optionalItemNo);
		if (itemOtp.isPresent()) {
			attId = itemOtp.get().itemId;
		}
		return attId;
	}

	private int getMonthlyAttId(Integer optionalItemNo) {
		int attId = 0;
		Optional<MonthlyItemList> itemOtp = MonthlyItemList.getOption(optionalItemNo);
		if (itemOtp.isPresent()) {
			attId = itemOtp.get().itemId;
		}
		return attId;
	}

	private Optional<PrimitiveValueOfAttendanceItem> getDailyPrimitiveValue(OptionalItemAtr optionalItemAtr) {
		PrimitiveValueOfAttendanceItem primitiveValueOfAttendanceItem = null;

		switch (optionalItemAtr) {
		case TIME:
			primitiveValueOfAttendanceItem = PrimitiveValueOfAttendanceItem.DAILY_ANY_TIME;
			break;
		case NUMBER:
			primitiveValueOfAttendanceItem = PrimitiveValueOfAttendanceItem.DAILY_ANY_NUM;
			break;
		case AMOUNT:
			primitiveValueOfAttendanceItem = PrimitiveValueOfAttendanceItem.DAILY_ANY_AMOUNT;
			break;
		default:
			return Optional.empty();
		}
		return Optional.ofNullable(primitiveValueOfAttendanceItem);
	}

	private Optional<PrimitiveValueOfAttendanceItem> getMonthlyPrimitiveValue(OptionalItemAtr optionalItemAtr) {
		PrimitiveValueOfAttendanceItem primitiveValueOfAttendanceItem = null;

		switch (optionalItemAtr) {
		case TIME:
			primitiveValueOfAttendanceItem = PrimitiveValueOfAttendanceItem.MONTHLY_ANY_TIME;
			break;
		case NUMBER:
			primitiveValueOfAttendanceItem = PrimitiveValueOfAttendanceItem.MONTHLY_ANY_NUM;
			break;
		case AMOUNT:
			primitiveValueOfAttendanceItem = PrimitiveValueOfAttendanceItem.MONTHLY_ANY_AMOUNT;
			break;
		default:
			return Optional.empty();
		}
		return Optional.ofNullable(primitiveValueOfAttendanceItem);
	}
}
