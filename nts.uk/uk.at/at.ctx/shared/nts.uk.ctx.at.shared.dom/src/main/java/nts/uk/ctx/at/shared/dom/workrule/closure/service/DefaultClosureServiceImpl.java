/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrule.closure.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDay;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class DefaultClosureServiceImpl.
 */
@Stateless
public class DefaultClosureServiceImpl implements ClosureService {

	/** The closure repository. */
	@Inject
	private ClosureRepository closureRepository;
	
	/** The Constant FIRST_DAY_OF_MONTH. */
	private final static int FIRST_DAY_OF_MONTH = 1; 
	
	/** The Constant MONTH_OF_YEAR. */
	private final static int MONTH_OF_YEAR = 12;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService#
	 * getClosurePeriod(int, nts.arc.time.YearMonth)
	 */
	@Override
	public DatePeriod getClosurePeriod(int closureId, YearMonth processingYm) {
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		//
		Optional<ClosureHistory> optClosureHistory = this.closureRepository
				.findBySelectedYearMonth(companyId, closureId, processingYm.v());

		// Check exist
		if (!optClosureHistory.isPresent()) {
			return null;
		}

		ClosureHistory closureHistory = optClosureHistory.get();

		ClosureDay closureDay = closureHistory.getClosureDate().getClosureDay();

		Boolean isLastDayOfMonth = closureHistory.getClosureDate().getLastDayOfMonth();

		GeneralDate startDate = this.getExpectionDate(isLastDayOfMonth, processingYm.year(),
				isLastDayOfMonth ? processingYm.month() : processingYm.month() - 1,
				isLastDayOfMonth ? FIRST_DAY_OF_MONTH : closureDay.v() + 1, true);

		GeneralDate endDate = this.getExpectionDate(isLastDayOfMonth, processingYm.year(),
				processingYm.month(), closureDay.v(), false);

		return new DatePeriod(startDate, endDate);
	}

	/**
	 * Gets the expection date.
	 *
	 * @param lastDayOfMonth the last day of month
	 * @param year the year
	 * @param month the month
	 * @param day the day
	 * @return the expection date
	 */
	private GeneralDate getExpectionDate(Boolean lastDayOfMonth, int year, int month, int day, Boolean isStartDate) {
		
		if(month == 0) {
			month =  MONTH_OF_YEAR;
			year = year - 1;
		}
	
		if(lastDayOfMonth && isStartDate) {
			return GeneralDate.ymd(year, month, day);
		}
		
		return  (lastDayOfMonth || !this.isDateOfMonth(year, month, day))
				? this.getLastDateOfMonth(year, month) : GeneralDate.ymd(year, month, day);
	}
	
	/**
	 * Gets the last date of month.
	 *
	 * @param year the year
	 * @param month the month
	 * @return the last date of month
	 */
	private GeneralDate getLastDateOfMonth(int year, int month) {
		GeneralDate baseDate = GeneralDate.ymd(year, month, 1);
		Calendar c = Calendar.getInstance();
		c.setTime(baseDate.date());
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return GeneralDate.legacyDate(c.getTime());
	}

	/**
	 * Checks if is date of month.
	 *
	 * @return true, if is date of month
	 */
	private boolean isDateOfMonth(int year, int month, int dayOfMonth) {
		GeneralDate baseDate = this.getLastDateOfMonth(year, month);
		return dayOfMonth <= baseDate.day();
	}
	
	/** 指定した年月日時点の締め期間を取得する */
	// 2018.3.11 add shuichi_ishida
	@Override
	public Optional<ClosurePeriod> getClosurePeriodByYmd(int closureId, GeneralDate ymd) {
		
		val loginUserContext = AppContexts.user();
		val companyId = loginUserContext.companyId();

		// 「締め」を取得
		val closureOpt = this.closureRepository.findById(companyId, closureId);
		if (!closureOpt.isPresent()) return Optional.empty();
		val closure = closureOpt.get();
		
		ClosurePeriod closurePeriod = null;
		
		// パラメータ「年月日」を年月に変換
		YearMonth yearMonth = YearMonth.of(ymd.year(), ymd.month());
		while (true){
			
			// 指定した年月の期間を全て取得する
			//*****（未）　取得するための処理の作成が必要か、既にあるかが不明。
			List<DatePeriod> periods = new ArrayList<>();
			if (periods.size() <= 0) return Optional.empty();
			int endState = 0;
			if (periods.size() > 1){
				
				// 締め日変更がある月の締め期間を作成する
				val periodBeforeChange = periods.get(0);
				val periodAfterChange = periods.get(1);
				if (ymd.before(periodBeforeChange.start())){
					endState = -1;
				}
				else if (periodBeforeChange.contains(ymd)){
					val closureHistoryOpt = closure.getHistoryByYearMonth(yearMonth);
					if (!closureHistoryOpt.isPresent()) return Optional.empty();
					closurePeriod = ClosurePeriod.of(
							EnumAdaptor.valueOf(closureId, ClosureId.class),
							closureHistoryOpt.get().getClosureDate(),
							yearMonth,
							periodBeforeChange);
					endState = 0;
				}
				else if (periodAfterChange.end().before(ymd)){
					endState = 1;
				}
				else {
					val closureHistoryOpt = closure.getHistoryByYearMonth(yearMonth);
					if (!closureHistoryOpt.isPresent()) return Optional.empty();
					closurePeriod = ClosurePeriod.of(
							EnumAdaptor.valueOf(closureId, ClosureId.class),
							closureHistoryOpt.get().getClosureDate(),
							yearMonth,
							periodAfterChange);
					endState = 0;
				}
			}
			else {
				
				// 締め日変更がない月の締め期間を作成する
				if (periods.get(0).contains(ymd)){
					val closureHistoryOpt = closure.getHistoryByYearMonth(yearMonth);
					if (!closureHistoryOpt.isPresent()) return Optional.empty();
					closurePeriod = ClosurePeriod.of(
							EnumAdaptor.valueOf(closureId, ClosureId.class),
							closureHistoryOpt.get().getClosureDate(),
							yearMonth,
							periods.get(0));
					endState = 0;
				}
				else if (ymd.before(periods.get(0).start())){
					endState = -1;
				}
				else {
					endState = 1;
				}
			}
			if (endState == 0) break;
			if (endState < 0) yearMonth = yearMonth.nextMonth();
			if (endState > 0) yearMonth = yearMonth.previousMonth();
		}
		return Optional.ofNullable(closurePeriod);
	}
}
