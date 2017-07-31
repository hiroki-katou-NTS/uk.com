package nts.uk.ctx.at.schedule.app.find.shift.pattern.daily;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.daily.dto.DailyPatternValDto;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.DailyPatternVal;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.DailyPatternValRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class DailyPatternValFinder.
 */
@Stateless
public class DailyPatternValFinder {

	/** The patt calendar repo. */
	@Inject
	private DailyPatternValRepository pattCalendarRepo;

	/**
	 * Find pattern val by pattern cd.
	 *
	 * @param patternCd the pattern cd
	 * @return the list
	 */
	public List<DailyPatternValDto> findPatternValByPatternCd(String patternCd) {
		LoginUserContext loginUserContext = AppContexts.user();
		String companyId = loginUserContext.companyId();
		List<DailyPatternVal> listSetting = this.pattCalendarRepo.findByPatternCd(companyId, patternCd);
		if (CollectionUtil.isEmpty(listSetting)) {
			return null;
		}
		// PATTERN Val
		return listSetting.stream().map(data -> {
			DailyPatternValDto patternValDto = new DailyPatternValDto();
			data.saveToMemento(patternValDto);
			return patternValDto;
		}).collect(Collectors.toList());
	}

}
