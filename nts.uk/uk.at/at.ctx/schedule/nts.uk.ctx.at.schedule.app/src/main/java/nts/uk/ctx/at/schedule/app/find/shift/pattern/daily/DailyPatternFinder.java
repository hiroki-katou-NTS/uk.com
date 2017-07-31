/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.pattern.daily;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.daily.dto.DailyPatternDto;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.DailyPattern;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.DailyPatternRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DailyPatternFinder.
 */
@Stateless
public class DailyPatternFinder {

	/** The patt calendar repo. */
	@Inject
	private DailyPatternRepository pattCalendarRepo;

	/**
	 * Gets the all patt calendar.
	 *
	 * @return the all patt calendar
	 */
	public List<DailyPatternDto> getAllPattCalendar() {
		String companyId = AppContexts.user().companyId();
		List<DailyPattern> listSetting = this.pattCalendarRepo.getAllPattCalendar(companyId);
		if (CollectionUtil.isEmpty(listSetting)) {
			return null;
		}
		// Pattern Calendar

		return listSetting.stream().map(data -> {
			DailyPatternDto patternCalendarDto = new DailyPatternDto();
			data.saveToMemento(patternCalendarDto);
			return patternCalendarDto;
		}).collect(Collectors.toList());

	}

	/**
	 * Find pattern calendar by company id.
	 *
	 * @return the list
	 */
	public List<DailyPatternDto> findPatternCalendarByCompanyId() {
		String companyId = AppContexts.user().companyId();
		//Fake
		String patternCd = "1";
		List<DailyPattern> listSetting = this.pattCalendarRepo.findByCompanyId(companyId,patternCd);
		if (CollectionUtil.isEmpty(listSetting)) {
			return null;
		}
		// PATTERN
		return listSetting.stream().map(data -> {
			DailyPatternDto patternCalendarDto = new DailyPatternDto();
			data.saveToMemento(patternCalendarDto);
			return patternCalendarDto;
		}).collect(Collectors.toList());
	}
	
	
	public void deleted(String patternCd){
		String companyId = AppContexts.user().companyId();
		pattCalendarRepo.delete(companyId, patternCd);
	}

}
