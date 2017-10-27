/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.pattern.daily;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.daily.dto.DailyPatternDetailDto;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.daily.dto.DailyPatternItemDto;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.daily.dto.DailyPatternValDto;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.DailyPattern;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.DailyPatternRepository;
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

		List<DailyPattern> listSetting = this.dailyPatternRepo.getAllPattCalendar(companyId);

		if (CollectionUtil.isEmpty(listSetting)) {
			return null;
		}

		// Pattern Calendar
		return listSetting.stream().map(data -> new DailyPatternItemDto(data.getPatternCode().v(),
				data.getPatternName().v())).collect(Collectors.toList());
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
		Optional<DailyPattern> optDailyPattern = this.dailyPatternRepo.findByCode(companyId,
				patternCd);

		if (!optDailyPattern.isPresent()) {
			return null;
		}

		// PATTERN Val
		return optDailyPattern.get().getListDailyPatternVal().stream().map(data -> {
			DailyPatternValDto patternValDto = new DailyPatternValDto();
			data.saveToMemento(patternValDto);
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

		Optional<DailyPattern> optDailyPattern = this.dailyPatternRepo.findByCode(companyId,
				patternCd);

		if (!optDailyPattern.isPresent()) {
			return null;
		}

		// PATTERN
		DailyPatternDetailDto patternCalendarDto = new DailyPatternDetailDto();
		optDailyPattern.get().saveToMemento(patternCalendarDto);
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
