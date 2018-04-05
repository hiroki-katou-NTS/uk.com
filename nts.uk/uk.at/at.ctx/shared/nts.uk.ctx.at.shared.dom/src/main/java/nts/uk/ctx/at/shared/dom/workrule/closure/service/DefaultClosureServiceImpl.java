/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrule.closure.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDay;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
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
	// <<Public>> 当月の期間を算出する
	public DatePeriod getClosurePeriod(int closureId, YearMonth processingYm) {
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		//
		Optional<ClosureHistory> optClosureHistory = this.closureRepository.findBySelectedYearMonth(companyId,
				closureId, processingYm.v());

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

		GeneralDate endDate = this.getExpectionDate(isLastDayOfMonth, processingYm.year(), processingYm.month(),
				closureDay.v(), false);

		return new DatePeriod(startDate, endDate);
	}

	/**
	 * Gets the expection date.
	 *
	 * @param lastDayOfMonth the last day of month
	 * @param year the year
	 * @param month the month
	 * @param day the day
	 * @param isStartDate the is start date
	 * @return the expection date
	 */
	// 日付の存在チェック
	private GeneralDate getExpectionDate(Boolean lastDayOfMonth, int year, int month, int day, Boolean isStartDate) {

		if (month == 0) {
			month = MONTH_OF_YEAR;
			year = year - 1;
		}

		if (lastDayOfMonth && isStartDate) {
			return GeneralDate.ymd(year, month, day);
		}

		return (lastDayOfMonth || !this.isDateOfMonth(year, month, day)) ? this.getLastDateOfMonth(year, month)
				: GeneralDate.ymd(year, month, day);
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
	 * @param year the year
	 * @param month the month
	 * @param dayOfMonth the day of month
	 * @return true, if is date of month
	 */
	private boolean isDateOfMonth(int year, int month, int dayOfMonth) {
		GeneralDate baseDate = this.getLastDateOfMonth(year, month);
		return dayOfMonth <= baseDate.day();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService#getClosureInfo()
	 */
	// 全締めの当月と期間を取得する
	public List<ClosureInfor> getClosureInfo() {
		String companyId = AppContexts.user().companyId();
		
		List<ClosureInfor> closureInfor = new ArrayList<>();

		List<Closure> closureList = this.closureRepository.getClosureList(companyId);

		closureList.forEach(item -> {
			// <<Public>> 当月の期間を算出する
			DatePeriod period = this.getClosurePeriod(item.getClosureId().value,
					item.getClosureMonth().getProcessingYm());
			
			//Check ClosureClassification 
			switch (item.getClosureMonth().getClosureClassification().get()) {
			case ClassificationClosingBefore:
				item.setClosureHistories(item.getClosureHistories().stream()
						.filter(i -> i.getStartYearMonth().lessThanOrEqualTo(item.getClosureMonth().getProcessingYm())
								&& i.getEndYearMonth().greaterThanOrEqualTo(item.getClosureMonth().getProcessingYm()))
						.collect(Collectors.toList()));
				break;
			case ClassificationClosingAfter:
				item.setClosureHistories(item.getClosureHistories().stream()
						.filter(i -> item.getClosureMonth().getProcessingYm().v() == i.getStartYearMonth().v() - 1)
						.collect(Collectors.toList()));
				break;
			default:
				item.setClosureHistories(item.getClosureHistories().stream()
						.filter(i -> i.getStartYearMonth().lessThanOrEqualTo(item.getClosureMonth().getProcessingYm())
								&& i.getEndYearMonth().greaterThanOrEqualTo(item.getClosureMonth().getProcessingYm()))
						.collect(Collectors.toList()));
				break;
			}
			//insert for Param 
			item.getClosureHistories().forEach(closure -> {
				closureInfor.add(ClosureInfor.builder().closureId(closure.getClosureId()).closureName(closure.getClosureName())
						.closureDate(closure.getClosureDate()).closureMonth(item.getClosureMonth())
						.period(period).build());
			});
		});
		
		if (CollectionUtil.isEmpty(closureInfor)){
			return null;
		}
		
		//return List ClosureList
		return closureInfor;
	}
}
