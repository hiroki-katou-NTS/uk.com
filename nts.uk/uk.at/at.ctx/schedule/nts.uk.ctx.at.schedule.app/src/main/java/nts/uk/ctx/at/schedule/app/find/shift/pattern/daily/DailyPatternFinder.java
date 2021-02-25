/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.pattern.daily;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.daily.dto.DailyPatternDetailDto;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.daily.dto.DailyPatternItemDto;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.daily.dto.DailyPatternValDto;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.DailyPatternRepository;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class DailyPatternFinder.
 */
@Stateless
public class DailyPatternFinder {

	/** The daily pattern repo. */
	@Inject
	private DailyPatternRepository dailyPatternRepo;

	/**
	 * Gets the all patt calendar.
	 *
	 * @return the all patt calendar
	 */
	public List<DailyPatternItemDto> getAllPattCalendar() {
		String companyId = AppContexts.user().companyId();

		List<WorkCycle> listSetting = this.dailyPatternRepo.getByCid(companyId);

		if (CollectionUtil.isEmpty(listSetting)) {
			return null;
		}

		// Pattern Calendar
		return listSetting.stream().map(data -> new DailyPatternItemDto(data.getCode().v(),
				data.getName().v())).collect(Collectors.toList());
	}

	/**
	 * Find pattern val by pattern cd.
	 *
	 * @param patternCd
	 *            the pattern cd
	 * @return the list
	 */
	public List<DailyPatternValDto> findPatternValByPatternCd(String patternCd) {

		LoginUserContext loginUserContext = AppContexts.user();
		String companyId = loginUserContext.companyId();
		Optional<WorkCycle> optDailyPattern = this.dailyPatternRepo.getByCidAndCode(companyId,
				patternCd);

		if (!optDailyPattern.isPresent()) {
			return null;
		}
		AtomicInteger index = new AtomicInteger();
		// PATTERN Val
		return optDailyPattern.get().getInfos().stream().map(item -> {
			DailyPatternValDto patternValDto = new DailyPatternValDto(index.incrementAndGet(),
					item.getWorkInformation().getWorkTypeCode() != null? item.getWorkInformation().getWorkTypeCode().v(): null,
					item.getWorkInformation().getWorkTimeCode()!= null? item.getWorkInformation().getWorkTimeCode().v(): null,
					item.getDays()!= null? item.getDays().v():null);

			return patternValDto;
		}).collect(Collectors.toList());

	}

	/**
	 * Find by code.
	 *
	 * @param patternCd
	 *            the pattern cd
	 * @return the list
	 */
	public DailyPatternDetailDto findByCode(String patternCd) {
		String companyId = AppContexts.user().companyId();

		Optional<WorkCycle> optDailyPattern = this.dailyPatternRepo.getByCidAndCode(companyId,
				patternCd);

		if (!optDailyPattern.isPresent()) {
			return null;
		}

		// PATTERN

		AtomicInteger index = new AtomicInteger();
        val lstDetail = optDailyPattern.get().getInfos().stream().map(item -> new DailyPatternValDto(index.incrementAndGet(),
				item.getWorkInformation().getWorkTypeCode() != null? item.getWorkInformation().getWorkTypeCode().v(): null,
                item.getWorkInformation().getWorkTimeCode()!= null? item.getWorkInformation().getWorkTimeCode().v(): null,
				item.getDays()!= null? item.getDays().v():null)).collect(Collectors.toList());
        DailyPatternDetailDto patternCalendarDto = new DailyPatternDetailDto(optDailyPattern.get().getCode().v(),
                optDailyPattern.get().getName().v(),lstDetail);
		return patternCalendarDto;
	}

	/**
	 * Deleted.
	 *
	 * @param patternCd
	 *            the pattern cd
	 */
	public void deleteByCode(String patternCd) {
		String companyId = AppContexts.user().companyId();
		dailyPatternRepo.delete(companyId, patternCd);
	}

}
